package id.base.app.controller;

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
import id.base.app.validation.InvalidRequestException;
import id.base.app.valueobject.AppUser;
import id.base.app.valueobject.CreateEntity;
import id.base.app.valueobject.party.Party;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;

@RestController
@RequestMapping(RestConstant.RM_USER)
public class UserController extends SuperController<AppUser>{
	
	@Autowired
	private IUserService userService;
	@Autowired
	private ILookupService lookupService;
	@Autowired
	private IPartyService partyService;
	
	@Override
	public MaintenanceService<AppUser> getMaintenanceService() {
		return this.userService;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findByIdFetchRoles/{id}")
	public AppUser findByIdFetchRoles(@PathVariable( "id" ) Long pkUser) {
		return userService.findByIdFetchRoles(pkUser);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findExternalAppUserById/{id}")
	public AppUser findExternalAppUserById(@PathVariable( "id" ) Long pkUser) {
		return userService.findExternalAppUserById(pkUser);
	}

	@RequestMapping(method=RequestMethod.GET, value="/findByEmail/{email}")
	public AppUser findByEmail(@PathVariable("email") String email) throws SystemException {
		return userService.findByEmail(email);
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
			throw new SystemException(ErrorHolder.newInstance("errorCode", "error finding your data"));
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/deleteBulk")
	public void deleteBulk(@RequestBody String pkDelete){
		try{
			List<Long> pkDeletes = mapper.readValue(pkDelete, new TypeReference<List<Long>>(){});
			userService.delete(pkDeletes.toArray(new Long[pkDeletes.size()]));
		}catch(Exception e){
			
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/activate")
	@ResponseBody
	public void activate(@RequestBody AppUser anObject, BindingResult bindingResult) throws SystemException {
		if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid validation", bindingResult);
        }
		AppUser appUser = null;
		if (StringFunction.isEmpty(anObject.getEmail())) {
			throw new SystemException(ErrorHolder.newInstance("ERAC01", messageSource.getMessage("error.mandatory", new String[]{"email"}, Locale.ENGLISH)));
		}
		if (StringFunction.isEmpty(anObject.getActivationCode())) {
			throw new SystemException(ErrorHolder.newInstance("ERAC02", messageSource.getMessage("error.mandatory", new String[]{"activation code"}, Locale.ENGLISH)));
		}
		appUser = userService.findByEmailAndActivationCode(anObject.getEmail(), anObject.getActivationCode());
		if(appUser == null) {
			throw new SystemException(ErrorHolder.newInstance("ERAC03", messageSource.getMessage("error.user.activation.code", null, Locale.ENGLISH)));
		}
		userService.activate(appUser.getPkAppUser());
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/updateInitialWizard/{pkAppUser}/{initialWizardStep}")
	public void updateInitialWizard(@PathVariable(value="pkAppUser") final Long pkAppUser, @PathVariable(value="initialWizardStep") final Integer initialWizardStep) throws SystemException {
		userService.updateInitialWizard(pkAppUser, initialWizardStep);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/updateInitialWizard/{step}")
	public void updateInitialWizard(@PathVariable(value="step") Integer step, @RequestBody AppUser anObject, BindingResult bindingResult) throws SystemException {
		userService.updateInitialWizard(anObject.getPkAppUser(), step);
	}
	
	@Override
	public AppUser preCreate(AppUser anObject) throws SystemException {
		anObject.setSuperUser(Boolean.FALSE);
		anObject.setLoginFailed(0);
		anObject.setLock(Boolean.TRUE);
		anObject.setParty(Party.getInstance());
		return validate(anObject);
	}
	
	@Override
	public AppUser preUpdate(AppUser anObject) throws SystemException {
		if(anObject.getPkAppUser() == null){
			throw new SystemException(new ArrayList<ErrorHolder>(Arrays.asList(ErrorHolder.newInstance("appUser", "pkAppUser is required"))));
		}
		return validate(anObject);
	}
	
	@Override
	public AppUser validate(AppUser anObject) throws SystemException {
		List<ErrorHolder> errors = new ArrayList<>();
		
		if (StringFunction.isEmpty(anObject.getEmail())) {
			errors.add(ErrorHolder.newInstance("ERUC001", messageSource.getMessage("error.mandatory", new String[]{"email"}, Locale.ENGLISH)));
		} else if(!EmailFunction.isValid(anObject.getEmail())) {
			errors.add(ErrorHolder.newInstance("ERUC002", messageSource.getMessage("error.invalid", new String[]{anObject.getEmail(), "email"}, Locale.ENGLISH)));
		} if (userService.isEmailAlreadyInUsed(anObject.getPkAppUser(), anObject.getEmail())){
			errors.add(ErrorHolder.newInstance("ERUC003", messageSource.getMessage("error.user.email.already.inused", new String[]{anObject.getEmail()}, Locale.ENGLISH)));
		}
		
		if (StringFunction.isEmpty(anObject.getPassword())) {
			errors.add(ErrorHolder.newInstance(AppUser.PASSWORD, messageSource.getMessage("error.user.password.mandatory", null, Locale.ENGLISH)));
		}
		if (StringFunction.isEmpty(anObject.getPasswordConfirmation())) {
			errors.add(ErrorHolder.newInstance(AppUser.PASSWORD_CONFIRMATION, messageSource.getMessage("error.user.passwordConfirmation.mandatory", null, Locale.ENGLISH)));
		}
		if (StringFunction.isNotEmpty(anObject.getPassword()) && StringFunction.isNotEmpty(anObject.getPasswordConfirmation()) 
				&& !anObject.getPassword().equals(anObject.getPasswordConfirmation())) {
			errors.add(ErrorHolder.newInstance(AppUser.PASSWORD_CONFIRMATION, messageSource.getMessage("error.user.passwordConfirmation.invalid", null, Locale.ENGLISH)));
		}
		
		if(errors != null && errors.size() > 0){
			throw new SystemException(errors);
		}
		
		return anObject;
	}
	
}
