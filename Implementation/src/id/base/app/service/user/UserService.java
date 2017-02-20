package id.base.app.service.user;

import id.base.app.SystemConstant;
import id.base.app.SystemParameter;
import id.base.app.dao.login.ILoginDAO;
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
import id.base.app.valueobject.RuntimeUserLogin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService implements MaintenanceService<AppUser>, IUserService, IUserNotificationService{

	@Autowired
	private IAppRoleDAO appRoleDAO;
	@Autowired
	private IStringDigester digester;
	@Autowired
	private IEmailTemplateService templateService;
	@Autowired
	private IPasswordHistoryDAO historyDAO;
	@Autowired
	private ILoginDAO loginDAO;
	@Autowired
	@Qualifier("SMTPMailService")
	private EmailAPI mailService;
	@Autowired
	@Qualifier("ShortMessageService")
	private ShortMessageServiceAPI shortMessageService;
	@Autowired
	private ResourceBundleMessageSource messageSource;
	@Autowired
	private IUserDAO userDao;
	
	public UserService(){}
	
	protected static Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	
	public UserService(IUserDAO userDao,IStringDigester digester, PasswordHistoryDAO historyDAO){
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
			appUser.setAppRoles(getRoles(SystemConstant.UserRole.WEB_USER));
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
		if (StringFunction.isNotEmpty(appUser.getEmail())) {
			try{
				mailService.sendMail(new ArrayList<>(Arrays.asList(appUser.getEmail())), SystemParameter.EMAIL_SENDER, "User Registration", resolveContent(appUser), null);
			}catch(Exception e){
				throw new SystemException (ErrorHolder.newInstance("errorCode", "Fail to send Activation Email"));
			}
		}
	}
	
	private String resolveContent(AppUser appUser) {
		return "your activation code is : " + appUser.getActivationCode();
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
		UserService ser = new UserService();
		System.out.println(ser.generateActivationCode("tes"));
	}
	
	private List<AppRole> populateAppRoles(AppUser appUser) {
		AppRole role = appRoleDAO.findAppRoleByCode(SystemConstant.UserRole.WEB_USER);
		if(role != null){
			return new ArrayList<AppRole>(Arrays.asList(role));
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
		}
		return userDao.findAllAppUserByFilter(startNo, offset,  filter, order);
	}

	public AppUser findByEmail(String email) throws SystemException {
		AppUser appUser = userDao.findAppUserByEmail(email); 
		if(appUser == null) {
			throw new SystemException(ErrorHolder.newInstance("errorCode", "error.user.not.found"));
		}
		return appUser;
	}
	
	@Override
	public RuntimeUserLogin buildRuntimeUserLogin(String email, String unencryptedPassword, String remoteAddress) throws SystemException {
		AppUser appUser = userDao.findAppUserByEmail(email);
		if(appUser == null) {
			throw new SystemException(ErrorHolder.newInstance("ERAU01", messageSource.getMessage("error.user.not.found", null, Locale.ENGLISH)));
		}else if(!matchPassword(unencryptedPassword, appUser.getPassword())){
			throw new SystemException(ErrorHolder.newInstance("ERAU02", messageSource.getMessage("error.user.invalid.password", null, Locale.ENGLISH)));
		}
		RuntimeUserLogin session = loginDAO.findByEmail(appUser.getEmail());
		if(session != null){
			session.setRemoteAddress(remoteAddress);
			session.setLoginTime(Calendar.getInstance().getTime());
			session.setUserId(appUser.getPkAppUser());
		}else{
			session = RuntimeUserLogin.newInstance(appUser.getPkAppUser(), appUser.getEmail(), remoteAddress);
		}
		loginDAO.saveOrUpdate(session);
		return session;
	}
	
	public AppUser findByEmailAndActivationCode(String email, String activationCode) throws SystemException {
		AppUser appUser = userDao.findByEmailAndActivationCode(email, activationCode);
		if(appUser == null) {
			throw new SystemException(ErrorHolder.newInstance("errorCode", "error.user.not.found"));
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
	
	public AppUser findByEmailAndType(String email, int type) throws SystemException {
		AppUser appUser = userDao.findAppUserByEmailAndType(email, type); 
		if(appUser == null) {
			throw new SystemException(ErrorHolder.newInstance("errorCode", "error.user.not.found"));
		}
		return appUser;
	}
	
	@Override
	public AppUser findByEmailTypeAndPassword(String email, int type, String unencryptedPassword) throws SystemException {
		AppUser appUser = userDao.findAppUserByEmailAndType(email, type); 
		if(appUser == null) {
			throw new SystemException(ErrorHolder.newInstance("errorCode", "error.user.not.found"));
		}else if(!matchPassword(unencryptedPassword, appUser.getPassword())){
			throw new SystemException(ErrorHolder.newInstance("errorCode", "error.user.not.found"));
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
	public List<AppUser> findSupervisor(Long partnerPkParty, Long pkLocationStructure, Long pkTitleStructure, Long pkOrgUnit) throws SystemException {
		return null;
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
	public void activate(String email, String activationCode) throws SystemException {
		AppUser appUser = userDao.findAppUserByEmailAndActivationCode(email, activationCode);
			appUser.setLock(Boolean.FALSE);
			appUser.setStatus(1);
		userDao.saveOrUpdate(appUser);
	}

	@Override
	public void updateInitialWizard(Long pkAppUser, Integer initialWizardStep) throws SystemException {
		userDao.updateInitialWizard(pkAppUser, initialWizardStep);
	}

	@Override
	public Boolean isEmailAlreadyInUsed(Long pkAppUser, String email) throws SystemException {
		return userDao.isEmailAlreadyInUsed(pkAppUser, email);
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
