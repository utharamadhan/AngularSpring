package id.base.app.controller;

import id.base.app.exception.SystemException;
import id.base.app.rest.RestConstant;
import id.base.app.service.MaintenanceService;
import id.base.app.service.parameter.IParameterService;
import id.base.app.valueobject.AppParameter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestConstant.RM_SECURITYPARAM)
public class SecurityParameterController extends SuperController<AppParameter>{

	@Autowired
	@Qualifier("securityParameterService")
	private MaintenanceService<AppParameter> maintenanceService;
	
	@Autowired
	@Qualifier("securityParameterService")
	private IParameterService service;
	
	@RequestMapping(method=RequestMethod.GET, value="/list")
	@ResponseBody
	public List<AppParameter> findAll() throws SystemException {
		return service.findAllAppParameter();
	}

	@Override
	public MaintenanceService<AppParameter> getMaintenanceService() {
		return this.maintenanceService;
	}

	@Override
	public AppParameter validate(AppParameter anObject) throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

}