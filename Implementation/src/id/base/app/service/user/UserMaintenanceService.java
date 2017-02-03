package id.base.app.service.user;

import id.base.app.SystemConstant;
import id.base.app.SystemParameter;
import id.base.app.dao.passwordhistory.IPasswordHistoryDAO;
import id.base.app.dao.passwordhistory.PasswordHistoryDAO;
import id.base.app.dao.role.IAppRoleDAO;
import id.base.app.dao.user.IUserDAO;
import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.security.IStringDigester;
import id.base.app.service.MaintenanceService;
import id.base.app.service.email.IEmailTemplateService;
import id.base.app.service.mail.EmailAPI;
import id.base.app.service.message.ShortMessageServiceAPI;
import id.base.app.util.DateTimeFunction;
import id.base.app.util.StringFunction;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.util.dao.SearchOrder.Sort;
import id.base.app.valueobject.AppRole;
import id.base.app.valueobject.AppUser;
import id.base.app.valueobject.PasswordHistory;
import id.base.app.valueobject.party.PartyContact;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserMaintenanceService implements MaintenanceService<AppUser>, IUserService, IUserNotificationService{

	@Autowired
	private IAppRoleDAO appRoleDAO;
	@Autowired
	private IStringDigester digester;
	@Autowired
	private IEmailTemplateService templateService;
	@Autowired
	private IPasswordHistoryDAO historyDAO;
	@Autowired
	@Qualifier("SparkPostMailService")
	private EmailAPI mailService;
	@Autowired
	@Qualifier("ShortMessageService")
	private ShortMessageServiceAPI shortMessageService;
	@Autowired
	private ResourceBundleMessageSource messageSource;
	@Autowired
	private IUserDAO userDao;
	
	public UserMaintenanceService(){}
	
	protected static Logger LOGGER = LoggerFactory.getLogger(UserMaintenanceService.class);
	
	
	public static final String RESULT 			= "Result";
	public static final String VALID_CONTRACT 	= "validUser_";
	public static final String INVALID_CONTRACT	= "invalidUser_";
	public static final String SUMMARY			= "summary_";
	
	public UserMaintenanceService(IUserDAO userDao,IStringDigester digester, PasswordHistoryDAO historyDAO){
		this.userDao=userDao;
		this.digester=digester;
		this.historyDAO=historyDAO;
		
	}
	
	public void delete(Long[] objectPKs) throws SystemException {
		userDao.delete(objectPKs);
	}

	public PagingWrapper<AppUser> findAll(int startNo, int offset)
			throws SystemException {
		return userDao.findAllAppUser(startNo, offset);
	}

	public AppUser findById(Long id) throws SystemException {
		return  userDao.findAppUserById(id);
	}
	
	public AppUser findAuthorizedMerchantByUserId(Long userId) throws SystemException {
		AppUser appUser= userDao.findAuthorizedMerchantByUserId(userId);
		return appUser;
	}
	
	public List<AppRole> getRoles(String roleCode) throws SystemException {
		List<AppRole> roles = new ArrayList<>();
		try{
			AppRole role = appRoleDAO.findAppRoleByCode(roleCode);
			roles.add(role);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		return roles;
	}

	public void saveOrUpdate(AppUser appUser) throws SystemException {
		boolean isShouldUpdatePwdHistory = false;
		Calendar c = Calendar.getInstance();
		c.setTime(DateTimeFunction.getCurrentDate());
		c.add(Calendar.DATE, SystemParameter.PASSWORD_LIFETIME);
		if(isCreateMode(appUser)){
			appUser.setPassword(encryptPassword(appUser.getPassword()));
			appUser.setStatus(2);
			appUser.setAppRoles(populateAppRoles(appUser));
			appUser.setActivationCode(generateActivationCode(appUser.getPassword()));
			appUser.setUserType(2);
			appUser.setAppRoles(getRoles(SystemConstant.UserRole.TRANSACTION_MEMBER));
		}
		userDao.saveOrUpdate(appUser);
		if (isShouldUpdatePwdHistory) {
			List <PasswordHistory> listPassHist = historyDAO.getTotalPasswordByfkAppuser(appUser.getPkAppUser());
			PasswordHistory history = new PasswordHistory();
			if (listPassHist.size() >= SystemParameter.MAX_PASSWORD_HISTORY_CHECK)
				historyDAO.delete(listPassHist.get(0));
			history.setRecordedDate(DateTimeFunction.getCurrentDate());
			history.setFkAppUser(appUser.getPkAppUser());
			historyDAO.saveOrUpdate(history);
		}
		sendActivation(appUser);
	}
	
	private void sendActivation(AppUser appUser) throws SystemException {
		if(appUser.getActivationMethod().equals(SystemConstant.ActivationMethod.EMAIL)) {
			//send using email
			if (StringFunction.isNotEmpty(appUser.getEmail())) {
				try{
					mailService.sendMail(new ArrayList<>(Arrays.asList(appUser.getEmail())), SystemParameter.EMAIL_SENDER, "BASE - PENDAFTARAN USER", resolveContent(appUser), null);
				}catch(Exception e){
					throw new SystemException (new ErrorHolder("Fail to send Activation Email"));
				}
			}
		} else {
			//send using handphone
			String phoneNumber = extractPhoneNumber(appUser);
			if (StringFunction.isNotEmpty(phoneNumber)) {
				try{
					shortMessageService.sendMessage(phoneNumber, resolveSMSContent(appUser.getParty().getName(), appUser.getActivationCode()));
				}catch(Exception e){
					throw new SystemException (new ErrorHolder("Fail to send Activation Short Message"));
				}
			}
		}
	}
	
	private String extractPhoneNumber(AppUser appUser) {
		if(appUser.getParty() != null && appUser.getParty().getPartyContacts() != null && appUser.getParty().getPartyContacts().size() > 0) {
			for(PartyContact contact : appUser.getParty().getPartyContacts()) {
				return contact.getContact();
			}
		}
		return "";
	}
	
	private String resolveContent(AppUser appUser) {
		String activationLink = SystemConstant.ADMIN_URL + "/do/registration/activation/" + appUser.getActivationCode();
		return activationLink;
	}
	
	private String resolveSMSContent(String name, String activationCode) {
		return "Hallo, " + name + ". Selamat bergabung di BASE.ID. Kode aktivasi pendaftaran anda " + activationCode + ".";
	}
	
	private String generateActivationCode(String input) {
		String returnValue = "";
		Pattern p = Pattern.compile("\\w");
		Matcher m = p.matcher(input);
		returnValue = "";
		while(m.find()){
			returnValue += m.group();
			if(returnValue.length() >= 10) {
				break;
			}
		}	
		if(returnValue.length() >= 10) {
			returnValue = returnValue.substring(0, 10);
		} else {
			while(returnValue.length() < 10) {
				returnValue += Math.round(Math.random() * 10);
				if(returnValue.length() == 10){
					break;
				}
			}
		}
		return returnValue.toUpperCase();
	}
	
	public static void main(String[] args) {
		UserMaintenanceService ser = new UserMaintenanceService();
		System.out.println(ser.generateActivationCode("tes"));
	}
	
	private List<AppRole> populateAppRoles(AppUser appUser) {
		if(appUser.getRoleFlag() != null && appUser.getRoleFlag().equals(AppUser.USER_FRONTEND)){
			AppRole role = appRoleDAO.findAppRoleByCode(SystemConstant.UserRole.TRANSACTION_MEMBER);
			if(role != null){
				return new ArrayList<AppRole>(Arrays.asList(role));
			}
		}
		return null;
	}
	
	private boolean isCreateMode(AppUser appUser) {
		return (appUser.getPkAppUser() == null || appUser.getPkAppUser() == 0);
	}

	public PagingWrapper<AppUser> findAllByFilter(int startNo, int offset,
			List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		if (order == null) {
			order = new ArrayList<SearchOrder>();
			order.add(new SearchOrder(AppUser.USER_TYPE, Sort.ASC));
			order.add(new SearchOrder(AppUser.USER_NAME, Sort.ASC));
		}
		return userDao.findAllAppUserByFilter(startNo, offset,  filter, order);
	}

	public AppUser findByUserName(String username) throws SystemException {
		AppUser appUser = userDao.findAppUserByName(username); 
		if(appUser == null) {
			throw new SystemException(new ErrorHolder("error.user.not.found"));
		}
		/*for (Merchant merchant : appUser.getAuthorizedMerchants()) {
			 merchant.getMerchantPK();
		}*/
		return appUser;
	}
	
	public AppUser findByUserNameAndPassword(String username, String unencryptedPassword) throws SystemException {
		AppUser appUser = userDao.findAppUserByName(username);
		if(appUser == null) {
			throw new SystemException(new ErrorHolder("error.user.not.found"));
		}else if(!matchPassword(unencryptedPassword, appUser.getPassword())){
			throw new SystemException(new ErrorHolder("error.user.not.found"));
		}
		return appUser;
	}
	
	public AppUser findByUserNameAndActivationCode(String username, String activationCode) throws SystemException {
		AppUser appUser = userDao.findByUserNameAndActivationCode(username, activationCode);
		if(appUser == null) {
			throw new SystemException(new ErrorHolder("error.user.not.found"));
		}
		return appUser;
	}
	
	private String encryptPassword(String unencryptedPassword) {
		return digester.digest(unencryptedPassword);
	}
	
	private Boolean matchPassword(String password, String dbPassword) {
		return digester.matches(password, dbPassword);
	}
	
	@Override
	public Boolean validatePassword(Long pkAppUser, String unencryptedPassword) {
		String passwordDB = userDao.getStoredPassword(pkAppUser);
		if(matchPassword(unencryptedPassword, passwordDB)){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public AppUser findByUserNameAndType(String username, int type) throws SystemException {
		AppUser appUser = userDao.findAppUserByNameAndType(username, type); 
		if(appUser == null) {
			throw new SystemException(new ErrorHolder("error.user.not.found"));
		}
		return appUser;
	}
	
	public AppUser findByUserNameTypeAndPassword(String username, int type, String unencryptedPassword) throws SystemException {
		AppUser appUser = userDao.findAppUserByNameAndType(username, type); 
		if(appUser == null) {
			throw new SystemException(new ErrorHolder("error.user.not.found"));
		}else if(!matchPassword(unencryptedPassword, appUser.getPassword())){
			throw new SystemException(new ErrorHolder("error.user.not.found"));
		}
		return appUser;
	}
	
	@Override
	public List<AppUser> findObjects(Long[] objectPKs) throws SystemException {
		List<AppUser> users=new ArrayList<AppUser>();
		AppUser user=null;
		for(Long l:objectPKs){
			user=userDao.findAppUserById(l);
			users.add(user);
		}
		return users;
	}	
	
	public List<AppUser> findNotifiyingPasswordUser()throws SystemException{
		return userDao.findNotifiyingPasswordUser(SystemParameter.PASSWORD_EXPIRE_INTERVAL);		
	}
	
	public AppUser findByIdFetchRoles(Long pkUser) {
		return userDao.findByIdFetchRoles(pkUser);
	}

	@Override
	public void saveNewRoles(Long userPK, List<AppRole> newRoles) {
		AppUser appUser = userDao.findAppUserById(userPK);
		appUser.setAppRoles(newRoles);
	}
	
	public List<AppUser> findAllByFilter(List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		if (order == null) {
			order = new ArrayList<SearchOrder>();
			order.add(new SearchOrder(AppUser.USER_TYPE, Sort.ASC));
			order.add(new SearchOrder(AppUser.USER_NAME, Sort.ASC));
		}
		return userDao.findAllByFilter(filter, order, null);
	}
	
	@Override
	public void lockIdleUsers(Date paramDate) throws SystemException {
		userDao.lockIdleUsers(paramDate);
	}

	@Override
	public List<AppUser> findAll(List<SearchFilter> filter,
			List<SearchOrder> order) throws SystemException {
		return findAllByFilter(filter, order);
	}

	@Override
	public List<ErrorHolder> findUserId(String userName) throws SystemException {
		return userDao.findUserId(userName);
	}

	@Override
	public PagingWrapper<AppUser> findExternalByFilter(int startNo, int offset,
			List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		return userDao.findExternalAppUserByFilter(startNo, offset, filter, order);
	}

	@Override
	public AppUser findExternalAppUserById(Long id) throws SystemException {
		return userDao.findExternalAppUserById(id);
	}

	@Override
	public List<AppUser> findSupervisor(Long partnerPkParty,
			Long pkLocationStructure, Long pkTitleStructure, Long pkOrgUnit)
			throws SystemException {
		return null;
	}
//
	@Override
	public Map<String, Object> validateFile(String fileName, Long pkPartner) throws SystemException {
		Map<String, Object> resultMap = new HashMap<>();
		
		try{
			String localFile = fileName;
			String userDirectory = getUserDirectory();
			Map<Integer, String> fieldMap = getFieldMap();
			
			/*if(null != fieldMap && fieldMap.size() > 0){
				Partner partner = partnerDAO.findById(pkPartner);
				Map<Integer, List<LinkedList<Object>>> uploadMap = startValidateUserFile(fieldMap, userDirectory + localFile, POIUtil.getFileExtension(fileName), partner.getParty().getPkParty());
				resultMap = generateResultFile(uploadMap, partnerDAO.findById(pkPartner).getParty().getName(), userDirectory);
			}*/
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put(SystemConstant.ERROR_LIST, new ArrayList<ErrorHolder>(Arrays.asList(new ErrorHolder(e.getMessage()))));
		}
		return resultMap;
	}
	
	private Map<Integer, String> getFieldMap(){
		List<String> fieldList = new ArrayList<String>();
		fieldList.add("NIK");
		fieldList.add("Officer Name");
		fieldList.add("Officer Code");
		fieldList.add("Structure Date");
		fieldList.add("Join Date");
		fieldList.add("Level");
		fieldList.add("Direct Supervisor");
		fieldList.add("Branch Code");
		fieldList.add("Branch Name");
		fieldList.add("Area Code");
		fieldList.add("Area Name");
		fieldList.add("Region Code");
		fieldList.add("Region Name");
		fieldList.add("ID Number");
		fieldList.add("Address 1");
		fieldList.add("Address 2");
		fieldList.add("Address 3");
		fieldList.add("Account No");
		fieldList.add("NPWP");
		fieldList.add("AAJI");
		fieldList.add("AAJI Exp Date");
		fieldList.add("Email Address");
		fieldList.add("Mobile Phone");
		fieldList.add("Referral");
		fieldList.add("User ID");
		
		Map<Integer, String> fieldMap = new TreeMap<>();
		for(int i = 0; i<fieldList.size(); i++){
			fieldMap.put(i+1, fieldList.get(i));
		}
		return fieldMap;
	}
	
	private List<String> getFieldList(){
		List<String> fieldList = new ArrayList<String>();
		fieldList.add("NIK");
		fieldList.add("Officer Name");
		fieldList.add("Officer Code");
		fieldList.add("Structure Date");
		fieldList.add("Join Date");
		fieldList.add("Level");
		fieldList.add("Direct Supervisor");
		fieldList.add("Branch Code");
		fieldList.add("Branch Name");
		fieldList.add("Area Code");
		fieldList.add("Area Name");
		fieldList.add("Region Code");
		fieldList.add("Region Name");
		fieldList.add("ID Number");
		fieldList.add("Address 1");
		fieldList.add("Address 2");
		fieldList.add("Address 3");
		fieldList.add("Account No");
		fieldList.add("NPWP");
		fieldList.add("AAJI");
		fieldList.add("AAJI Exp Date");
		fieldList.add("Email Address");
		fieldList.add("Mobile Phone");
		fieldList.add("Referral");
		fieldList.add("User ID");
		return fieldList;
	}
	
	private int getDataType(int i){
		int numeric = 1;
		int string 	= 2;
		int date 	= 3;
		int llong	= 5;
		int email	= 6;
		if(i == 4 || i == 5 || i == 21){
			return date;
		}else if(i == 22){
			return email;
		}else if(i == 23){
			return numeric;
		}else{
			return string;
		}
	}
	
	private void setUser(AppUser user, Object obj, int temp, String level, Map<String,Long> employmentMapping, String field, Map<String,Date> employmentDates, Long partnerPkParty){
		
	}
	
	private String getUserDirectory(){
		return SystemConstant.LOCAL_TEMP_DIRECTORY_USER;
	}
	
	private void validateData(String obj, int temp, String level, Map<String, Long> pks, List<ErrorHolder> errorList, String field, boolean columnNotFound, Long partnerPkParty){
		
	}
	
	public static boolean isDateValid(String value){
		Date date = null;
		try {
		    SimpleDateFormat sdf = new SimpleDateFormat(SystemConstant.SYSTEM_DATE_MASK_2);
		    date = sdf.parse(value);
		    if (!value.equals(sdf.format(date))) {
		        date = null;
		    }
		} catch (ParseException ex) {
		    ex.printStackTrace();
		}
		if (date == null) {
		   return false;
		} else {
		    return true;
		}
	}
	
	private Map<Integer, List<LinkedList<Object>>> startValidateUserFile(Map<Integer, String> fieldMap, String pathFile, String fileType, Long partnerPkParty) throws Exception {
		return null;
	}
	
	private Boolean validateFieldSize(Cell cell, String field) throws Exception{
		String cellValue = null;
		try{
			if(field.equals("NIK") || field.equals("Officer Code") || field.equals("Direct Supervisor") || field.equals("Area Code")){
				cellValue = cell.getRichStringCellValue().getString().trim().replaceAll("\\s+", " ");
				return cellValue.length() <= 10 ? true : false;
			}else if(field.equals("Officer Name") || field.equals("Address 1") || field.equals("Address 2") || field.equals("Address 3") || field.equals("AAJI")){
				cellValue = cell.getRichStringCellValue().getString().trim().replaceAll("\\s+", " ");
				return cellValue.length() <= 60 ? true : false;
			}else if(field.equals("Structure Date") || field.equals("Join Date") || field.equals("AAJI Exp Date")){
				cellValue = cell.toString().trim().replaceAll("\\s+", " ");
				return cellValue.length() <= 10 ? true : false;
			}else if(field.equals("Branch Name") || field.equals("Area Name") || field.equals("Region Name") || field.equals("Email Address")){
				cellValue = cell.getRichStringCellValue().getString().trim().replaceAll("\\s+", " ");
				return cellValue.length() <= 100 ? true : false;
			}else if(field.equals("Region Code") || field.equals("Level")){
				cellValue = cell.getRichStringCellValue().getString().trim().replaceAll("\\s+", " ");
				return cellValue.length() <= 2 ? true : false;
			}else if(field.equals("Branch Code")){
				cellValue = cell.getRichStringCellValue().getString().trim().replaceAll("\\s+", " ");
				return cellValue.length() <= 5 ? true : false;
			}else if(field.equals("ID Number") || field.equals("NPWP")){
				cellValue = cell.getRichStringCellValue().getString().trim().replaceAll("\\s+", " ");
				return cellValue.length() <= 30 ? true : false;
			}else if(field.equals("Account No")){
				cellValue = cell.getRichStringCellValue().getString().trim().replaceAll("\\s+", " ");
				return cellValue.length() <= 20 ? true : false;
			}else if(field.equals("Mobile Phone")){
				cellValue = cell.getRichStringCellValue().getString().trim().replaceAll("\\s+", " ");
				return cellValue.length() <= 15 ? true : false;
			}else if(field.equals("Referral")){
				cellValue = cell.getRichStringCellValue().getString().trim().replaceAll("\\s+", " ");
				return cellValue.length() <= 1 ? true : false;
			}/*else if(field.equals("Level")){
				Long numericValue = ((Double) POIUtil.convertCellToObject(1, cell)).longValue();
				cellValue = "" + numericValue;
				return cellValue.length() <= 2 ? true : false;
			}*/else{
				return true;
			}
			/*else if(dataType == ILookupConstant.FieldDataType.NUMERIC){
			Long numericValue = ((Double) POIUtil.convertCellToObject(dataType, cell)).longValue();
			cellValue = "" + numericValue;
			return cellValue.length() <= fieldSize ? true : false;*/
		}catch(Exception e){
			throw e;
		}
	}
	
	private LinkedList<Object> getColumnHeader(Sheet sheet) throws Exception {
		LinkedList<Object> columnHeaderList = new LinkedList<>();
		
		Row row = sheet.getRow(0);
		if(null != row){
			Iterator<Cell> cellIterator = row.cellIterator();
			while(cellIterator.hasNext()){
				Cell cell = cellIterator.next();
				String headerValue = "";
				if(cell.getCellType()==Cell.CELL_TYPE_STRING){
					headerValue = cell.getRichStringCellValue().getString().trim().replaceAll("\\s+", " ");
					columnHeaderList.add(headerValue);
				}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
					headerValue = "" + cell.getNumericCellValue();
					columnHeaderList.add(headerValue);
				}
			}
		}else{
			throw new Exception(messageSource.getMessage("upload.user.template", new String[]{}, Locale.ENGLISH));
		}
		return columnHeaderList;
	}
	
	@Override
	public Map<String, Object> processFile(String fileName, Long pkPartner) throws SystemException {
		return null;
	}
	
	private Map<String, Object> fetchToUser(List<String> fieldList, String pathFile, String fileType, Long partnerPkParty) throws Exception {
		return null;
	}

	@Override
	public AppUser activateUserByActivationCode(String activationCode) throws SystemException {
		AppUser user = userDao.getAppUserByActivationCode(activationCode);
		if(user!=null){
			user.setStatus(1);
			user.setLock(Boolean.FALSE);
			userDao.saveOrUpdate(user);
		}
		return user;
	}

	@Override
	public void activate(Long pkAppUser) throws SystemException {
		AppUser appUser = userDao.findAppUserById(pkAppUser);
			appUser.setLock(Boolean.FALSE);
			appUser.setStatus(1);
		userDao.saveOrUpdate(appUser);
	}
	
	@Override
	public void activate(String userName, String activationCode) throws SystemException {
		AppUser appUser = userDao.findAppUserByUserNameAndActivationCode(userName, activationCode);
			appUser.setLock(Boolean.FALSE);
			appUser.setStatus(1);
		userDao.saveOrUpdate(appUser);
	}

	@Override
	public void updateInitialWizard(Long pkAppUser, Integer initialWizardStep) throws SystemException {
		userDao.updateInitialWizard(pkAppUser, initialWizardStep);
	}

	@Override
	public Boolean isEmailAlreadyInUsed(String email) throws SystemException {
		return userDao.isEmailAlreadyInUsed(email);
	}

	@Override
	public Boolean isPhoneAlreadyInUsed(String phoneNumber) throws SystemException {
		return userDao.isPhoneAlreadyInUsed(phoneNumber);
	}

	@Override
	public Boolean validateActivationCode(String userName, String activationCode) throws SystemException {
		return userDao.validateActivationCode(userName, activationCode);
	}

}
