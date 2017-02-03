package id.base.app.controller;

import id.base.app.LoginSession;
import id.base.app.SystemConstant;
import id.base.app.SystemParameter;
import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.rest.RestConstant;
import id.base.app.service.AuthenticationService;
import id.base.app.util.DateTimeFunction;
import id.base.app.valueobject.AppUser;
import id.base.app.valueobject.party.Party;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value=RestConstant.RM_AUTHENTICATION)
public class AuthenticationController {
	
	@Autowired
	AuthenticationService<LoginSession> authenticationService;
	
	private static final Map<String,String> AUTH_MAPPING = new HashMap<String, String>();
	static{
		AUTH_MAPPING.put("u", "userName");
		AUTH_MAPPING.put("e", "email");
		AUTH_MAPPING.put("s", "status");
		AUTH_MAPPING.put("l", "lock");
		AUTH_MAPPING.put("n", "name");
		AUTH_MAPPING.put("ut", "userType");
		AUTH_MAPPING.put("pk", "pkAppUser");
		AUTH_MAPPING.put("su", "superUser");
	}

	@RequestMapping(value="/authenticateLogin")
	@ResponseBody
	public LoginSession authenticateLogin(@RequestParam Map<String,String> paramWrapper) throws SystemException {
		AppUser appUser = new AppUser();
		for (Map.Entry<String, String> ent : AUTH_MAPPING.entrySet()) {
			if(paramWrapper.containsKey(ent.getKey())){
				try {
					if(ent.getValue().equalsIgnoreCase("name")){
						Party party = new Party();
						party.setName(paramWrapper.get(ent.getKey()));
						appUser.setParty(party);
					}else{
						BeanUtils.copyProperty(appUser, ent.getValue(), paramWrapper.get(ent.getKey()));
					}
				} catch (IllegalAccessException | InvocationTargetException e) {
					throw new SystemException(new ErrorHolder("Error Binding Parameter: "+ent.getKey()));
				}
			}else{
				throw new SystemException(new ErrorHolder("Required Parameter Not Found: "+ent.getKey()));
			}
		}
		return authenticationService.authenticateLogin(appUser);
	}
	
	@RequestMapping(value="/isNotificationPeriod/{expDate}")
	@ResponseBody
	public boolean isNotificationPeriod(@PathVariable(value="expDate") String expiredDate)throws SystemException {
		Calendar dt1 = Calendar.getInstance();
		Calendar dt2=Calendar.getInstance();
		dt1.setTime(new Date());
		dt2.setTime(DateTimeFunction.string2Date(expiredDate, SystemConstant.SYSTEM_DATE_MASK));
		Long milldiff=dt2.getTimeInMillis()-dt1.getTimeInMillis();
		long days=milldiff / (24 * 60 * 60 * 1000);
		if (days<=SystemParameter.PASSWORD_EXPIRE_INTERVAL){
			return true;
		}
		else
		{
			return false;
		}
	}
}
