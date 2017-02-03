package id.base.app.controller;

import id.base.app.ILookupConstant;
import id.base.app.ILookupGroupConstant;
import id.base.app.SystemConstant;
import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.rest.RestConstant;
import id.base.app.service.MaintenanceService;
import id.base.app.service.lookup.ILookupService;
import id.base.app.service.party.IPartyService;
import id.base.app.service.user.IUserService;
import id.base.app.util.EmailFunction;
import id.base.app.util.StringFunction;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.AppUser;
import id.base.app.valueobject.party.Party;
import id.base.app.valueobject.party.PartyContact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.QueryParam;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;

@RestController
@RequestMapping(RestConstant.RM_USER)
public class UserController extends SuperController<AppUser>{
	
	@Autowired
	@Qualifier("userMaintenanceService")
	private MaintenanceService<AppUser> maintenanceService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ILookupService lookupService;
	@Autowired
	private IPartyService partyService;
	
	@RequestMapping(method=RequestMethod.GET, value="/findByIdFetchRoles/{id}")
	public AppUser findByIdFetchRoles(@PathVariable( "id" ) Long pkUser) {
		return userService.findByIdFetchRoles(pkUser);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findExternalAppUserById/{id}")
	public AppUser findExternalAppUserById(@PathVariable( "id" ) Long pkUser) {
		return userService.findExternalAppUserById(pkUser);
	}
	
	@Override
	public MaintenanceService<AppUser> getMaintenanceService() {
		return this.maintenanceService;
	}

	@RequestMapping(method=RequestMethod.GET, value="/findByUserName/{username}")
	public AppUser findByUserName(@PathVariable("username") String username) throws SystemException {
		return userService.findByUserName(username);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findByUserNameParam")
	@ResponseBody
	public AppUser findByUserNameParam(@QueryParam(value="userName") String userName) throws SystemException{
		return userService.findByUserName(userName);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findByUserNameAndPassword/{username}/{unencryptedPassword}")
	public AppUser findByUserName(@PathVariable("username") String username, @PathVariable("unencryptedPassword") String unencryptedPassword) throws SystemException {
		return userService.findByUserNameAndPassword(username, unencryptedPassword);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findByUserNameAndActivationCode/{username}/{activationCode}")
	public AppUser findByUserNameAndActivationCode(@PathVariable("username") String username, @PathVariable("activationCode") String activationCode) throws SystemException {
		return userService.findByUserNameAndActivationCode(username, activationCode);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findByUserName/{username}/{type}")
	public AppUser findByUserNameAndType(@PathVariable("username") String username, @PathVariable("type") int type) throws SystemException {
		return userService.findByUserNameAndType(username, type);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findByUserNameAndPassword/{username}/{type}/{unencryptedPassword}")
	public AppUser findByUserNameAndType(@PathVariable("username") String username, @PathVariable("type") int type, @PathVariable("unencryptedPassword") String unencryptedPassword) throws SystemException {
		return userService.findByUserNameTypeAndPassword(username, type, unencryptedPassword);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findUserId")
	@ResponseBody
	public List<ErrorHolder> findUserId(@RequestParam(value="userName") String userName){
		return userService.findUserId(userName);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findExternalByFilter")
	@ResponseBody
	public PagingWrapper<AppUser> findExternalByFilter(
			@RequestParam(value="startNo",defaultValue="1") int startNo, 
			@RequestParam(value="offset",defaultValue="10") int offset,
			@RequestParam(value="filter", defaultValue="", required=false) String filterJson,
			@RequestParam(value="order", defaultValue="", required=false) String orderJson)
			throws SystemException {
		PagingWrapper<AppUser> pw = new PagingWrapper<AppUser>();
		try {
			List<SearchFilter> filter = new ArrayList<SearchFilter>();
			if(StringUtils.isNotEmpty(filterJson)){
				filter = mapper.readValue(filterJson, new TypeReference<List<SearchFilter>>(){});
			}
			List<SearchOrder> order = new ArrayList<SearchOrder>();
			if(StringUtils.isNotEmpty(orderJson)){
				order = mapper.readValue(orderJson, new TypeReference<List<SearchOrder>>(){});
			}
			pw = userService.findExternalByFilter(startNo, offset, filter, order);
			LOGGER.debug(mapper.writeValueAsString(pw));
			return pw;
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("error finding your data",e);
			throw new SystemException(new ErrorHolder("error finding your data"));
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/deleteBulk")
	public void deleteBulk(@RequestBody String pkDelete){
		try{
			List<Long> pkDeletes = mapper.readValue(pkDelete, new TypeReference<List<Long>>(){});
			userService.delete(pkDeletes.toArray(new Long[pkDeletes.size()]));
		}catch(Exception e){
			
		}
		//userService.delete(pkDelete);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/activateUserByActivationCode/{activationCode}")
	@ResponseBody
	public AppUser activateUserByActivationCode(@PathVariable(value="activationCode") String activationCode) {
		return userService.activateUserByActivationCode(activationCode);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/activateUserByUserName/{username}")
	@ResponseBody
	public AppUser activateUserByUserName(@PathVariable(value="userName") String username) {
		return userService.findByUserName(username);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/updateInitialWizard/{pkAppUser}/{initialWizardStep}")
	public void updateInitialWizard(@PathVariable(value="pkAppUser") final Long pkAppUser, @PathVariable(value="initialWizardStep") final Integer initialWizardStep) throws SystemException {
		userService.updateInitialWizard(pkAppUser, initialWizardStep);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/activate")
	public void activate(@RequestBody AppUser anObject, BindingResult bindingResult) throws SystemException {
		userService.activate(validateActivation(anObject).getUserName(), anObject.getActivationCode());
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/updateInitialWizard/{step}")
	public void updateInitialWizard(@PathVariable(value="step") Integer step, @RequestBody AppUser anObject, BindingResult bindingResult) throws SystemException {
		userService.updateInitialWizard(anObject.getPkAppUser(), step);
	}
	
	private AppUser validateActivation(AppUser anObject) {
		List<ErrorHolder> errors = new ArrayList<>();
		
		if(StringFunction.isEmpty(anObject.getUserName())) {
			errors.add(new ErrorHolder(AppUser.USER_NAME, messageSource.getMessage("error.user.phoneNumber.mandatory", null, Locale.ENGLISH)));
		}
		
		if (StringFunction.isEmpty(anObject.getActivationCode())) {
			errors.add(new ErrorHolder(AppUser.ACTIVATION_CODE, messageSource.getMessage("error.user.activation.code.mandatory", null, Locale.ENGLISH)));
		}
		
		if (StringFunction.isNotEmpty(anObject.getUserName()) && StringFunction.isNotEmpty(anObject.getActivationCode())
				&& !userService.validateActivationCode(anObject.getUserName(), anObject.getActivationCode())) {
			errors.add(new ErrorHolder(AppUser.ACTIVATION_CODE, messageSource.getMessage("error.activation.code.mismatch", null, Locale.ENGLISH)));
		}
		
		if(errors != null && errors.size() > 0) {
			throw new SystemException(errors);
		}
		return anObject;
	}
	
	@Override
	public AppUser preCreate(AppUser anObject) throws SystemException {
		if(anObject.getUserName() != null){
			if (anObject.getParty() != null) {
				anObject.getParty().setName(anObject.getUserName());
				anObject.getParty().setStatus(SystemConstant.ValidFlag.VALID);
				if(anObject.getParty().getPartyContacts() != null && anObject.getParty().getPartyContacts().size() > 0) {
					for(PartyContact contact : anObject.getParty().getPartyContacts()) {
						contact.setParty(anObject.getParty());
						contact.setContactType(lookupService.findByCode(ILookupConstant.PartyContact.HANDPHONE, ILookupGroupConstant.CONTACT_TYPE));
					}
				}
			} else {
				if(anObject.getParty() == null) {					
					anObject.setParty(Party.getInstance(anObject.getUserName()));
				} else {
					anObject.getParty().setStatus(SystemConstant.ValidFlag.VALID);
				}
			}
		} else {
			if(anObject.getParty() == null) {
				anObject.setParty(Party.getInstance(anObject.getUserName()));
			} else {
				anObject.getParty().setStatus(SystemConstant.ValidFlag.VALID);
			}
		}
		anObject.setSuperUser(Boolean.FALSE);
		anObject.setUserType(1);
		anObject.setLoginFailed(0);
		anObject.setLock(Boolean.TRUE);
		return validate(anObject);
	}
	
	@Override
	public AppUser preUpdate(AppUser anObject) throws SystemException {
		if(anObject.getPkAppUser() == null){
			throw new SystemException(new ArrayList<ErrorHolder>(Arrays.asList(new ErrorHolder("pkAppUser is required"))));
		}
		AppUser appUser = userService.findByUserName(anObject.getUserName());
		anObject.setParty(appUser.getParty());
		anObject.setSuperUser(Boolean.FALSE);
		anObject.setUserType(1);
		anObject.setLoginFailed(0);
		return validate(anObject);
	}
	
	private Boolean isPhoneNumberNull (AppUser anObject) {
		if(anObject.getParty() == null || anObject.getParty().getPartyContacts() == null || anObject.getParty().getPartyContacts().size() < 1) {
			return true;
		} else {
			if (anObject.getParty() != null) {
				for(PartyContact contact : anObject.getParty().getPartyContacts()) {
					if(StringFunction.isEmpty(contact.getContact())){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private String getPhoneNumber (AppUser anObject) {
		if(anObject.getParty() != null && anObject.getParty().getPartyContacts() != null && anObject.getParty().getPartyContacts().size() > 0) {
			for(PartyContact contact : anObject.getParty().getPartyContacts()) {
				if(StringFunction.isNotEmpty(contact.getContact())){
					return contact.getContact();
				}
			}
		}
		return "";
	}

	@Override
	public AppUser validate(AppUser anObject) throws SystemException {
		List<ErrorHolder> errors = new ArrayList<>();
		
		if (StringFunction.isEmpty(anObject.getParty().getName())) {
			errors.add(new ErrorHolder(AppUser.PARTY_NAME, messageSource.getMessage("error.user.username.mandatory", null, Locale.ENGLISH)));
		}
		
		Boolean isUserNameUsingEmail = Boolean.FALSE;
		if(StringFunction.isEmpty(anObject.getEmail()) && isPhoneNumberNull(anObject)){
			errors.add(new ErrorHolder(AppUser.EMAIL, messageSource.getMessage("error.user.email.mandatory", null, Locale.ENGLISH)));
		} else {
			if(StringFunction.isNotEmpty(anObject.getEmail()) && !EmailFunction.isAddressValidRegex(anObject.getEmail())){
				errors.add(new ErrorHolder(AppUser.EMAIL, messageSource.getMessage("error.user.email.invalid", null, Locale.ENGLISH)));
			} else if(StringFunction.isNotEmpty(anObject.getEmail()) && userService.isEmailAlreadyInUsed(anObject.getEmail())) {
				errors.add(new ErrorHolder(AppUser.EMAIL, messageSource.getMessage("error.user.email.already.inused", null, Locale.ENGLISH)));
			} else {
				if (StringFunction.isNotEmpty(anObject.getEmail())) {
					anObject.setUserName(anObject.getEmail());
					isUserNameUsingEmail = Boolean.TRUE;
					anObject.setActivationMethod(SystemConstant.ActivationMethod.EMAIL);
				}
			}
		}
		if (anObject.getParty() == null || anObject.getParty().getPartyContacts() == null || anObject.getParty().getPartyContacts().size() < 1) {
			errors.add(new ErrorHolder(String.format(AppUser.PARTY_CONTACTS_CONTACT, 0), messageSource.getMessage("error.user.phoneNumber.mandatory", null, Locale.ENGLISH)));
		} else {
			if (isPhoneNumberNull(anObject)) {
				errors.add(new ErrorHolder(String.format(AppUser.PARTY_CONTACTS_CONTACT, 0), messageSource.getMessage("error.user.phoneNumber.mandatory", null, Locale.ENGLISH)));
			} else if ( userService.isPhoneAlreadyInUsed(getPhoneNumber(anObject)) ) {
				errors.add(new ErrorHolder(String.format(AppUser.PARTY_CONTACTS_CONTACT, 0), messageSource.getMessage("error.user.phoneNumber.inused", null, Locale.ENGLISH)));
			} else {
				if (!isUserNameUsingEmail) {
					anObject.setUserName(getPhoneNumber(anObject));
					anObject.setActivationMethod(SystemConstant.ActivationMethod.SHORT_MESSAGE_SERVICE);
				}
				for(PartyContact contact : anObject.getParty().getPartyContacts()) {
					contact.setParty(anObject.getParty());
				}
			}
		}
		if (StringFunction.isEmpty(anObject.getPassword())) {
			errors.add(new ErrorHolder(AppUser.PASSWORD, messageSource.getMessage("error.user.password.mandatory", null, Locale.ENGLISH)));
		}
		if (StringFunction.isEmpty(anObject.getPasswordConfirmation())) {
			errors.add(new ErrorHolder(AppUser.PASSWORD_CONFIRMATION, messageSource.getMessage("error.user.passwordConfirmation.mandatory", null, Locale.ENGLISH)));
		}
		if (StringFunction.isNotEmpty(anObject.getPassword()) && StringFunction.isNotEmpty(anObject.getPasswordConfirmation()) 
				&& !anObject.getPassword().equals(anObject.getPasswordConfirmation())) {
			errors.add(new ErrorHolder(AppUser.PASSWORD_CONFIRMATION, messageSource.getMessage("error.user.passwordConfirmation.invalid", null, Locale.ENGLISH)));
		}
		
		if(errors != null && errors.size() > 0){
			throw new SystemException(errors);
		}
		
		return anObject;
	}
	
}
