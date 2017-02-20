package id.base.app.controller;

import id.base.app.LoginSession;
import id.base.app.exception.SystemException;
import id.base.app.rest.RestConstant;
import id.base.app.service.AuthenticationService;
import id.base.app.service.user.IUserService;
import id.base.app.util.StringFunction;
import id.base.app.valueobject.AppUser;
import id.base.app.valueobject.RuntimeUserLogin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value=RestConstant.RM_AUTHENTICATION)
public class AuthenticationController {
	
	@Autowired
	AuthenticationService<LoginSession> authenticationService;
	
	@Autowired
	private IUserService userService;
	
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
	
	@RequestMapping(method=RequestMethod.POST, value="/authenticateLogin")
	@ResponseBody
	public RuntimeUserLogin authenticateLogin(HttpServletRequest request, @RequestBody AppUser appUser, BindingResult bindingResult) {
		try {
			return userService.buildRuntimeUserLogin(appUser.getEmail(), appUser.getPassword(), StringFunction.getRemoteAddress(request));	
		} catch (SystemException e) {
			throw e;
		}
	}
	
}
