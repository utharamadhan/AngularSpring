package id.base.app.controller;

import id.base.app.exception.SystemException;
import id.base.app.rest.RestConstant;
import id.base.app.service.MaintenanceService;
import id.base.app.service.login.LoginDirectoryService;
import id.base.app.valueobject.RuntimeUserLogin;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value=RestConstant.RM_USER_LOGIN)
public class RuntimeUserLoginController extends SuperController<RuntimeUserLogin>{
	
	@Autowired
	@Qualifier("loginService")
	private MaintenanceService<RuntimeUserLogin> maintenanceService;
	
	@Autowired
	private LoginDirectoryService loginService;

	@Override
	public MaintenanceService<RuntimeUserLogin> getMaintenanceService() {
		return maintenanceService;
	}

	@RequestMapping(method=RequestMethod.POST, value="/register")
	@ResponseStatus( HttpStatus.OK )
	public void register(@RequestBody RuntimeUserLogin userLogin) throws SystemException{
		loginService.register(userLogin);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/unregister")
	@ResponseStatus( HttpStatus.OK )
	public void unregister(@RequestBody Long userPK) throws SystemException{
		loginService.unregister(userPK);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findByAccessInfoId/{aaid}")
	@ResponseBody
	public RuntimeUserLogin findByAccessInfoId(@PathVariable(value="aaid") String accessInfoId) throws SystemException{
		return loginService.findByAccessInfoId(accessInfoId);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findByUserName/{userName}")
	@ResponseBody
	public RuntimeUserLogin findByUserName(@PathVariable(value="userName") String userName) throws SystemException{
		return loginService.findByUserName(userName);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findByUserNameParam")
	@ResponseBody
	public RuntimeUserLogin findByUserNameParam(@QueryParam(value="userName") String userName) throws SystemException{
		return loginService.findByUserName(userName);
	}

	@Override
	public RuntimeUserLogin validate(RuntimeUserLogin anObject)
			throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}
}
