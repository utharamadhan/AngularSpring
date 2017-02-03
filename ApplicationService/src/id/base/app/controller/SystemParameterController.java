package id.base.app.controller;

import id.base.app.SystemConstant;
import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.rest.RestConstant;
import id.base.app.service.MaintenanceService;
import id.base.app.service.parameter.IParameterService;
import id.base.app.util.IntegerFunction;
import id.base.app.util.StringFunction;
import id.base.app.valueobject.AppParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.validator.DateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestConstant.RM_SYSTEM_PARAMETER)
public class SystemParameterController extends SuperController<AppParameter>{
	
	@Autowired
	@Qualifier("systemParameterService")
	private MaintenanceService<AppParameter> maintenanceService;
	
	@Autowired
	@Qualifier("systemParameterService")
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
	
	@RequestMapping(method=RequestMethod.GET, value="/findAppParametersByNames")
	@ResponseBody
	public Map<String, Object> findAppParametersByNames(@RequestParam(value="names") List<String> names)
			throws SystemException {
		return service.findAppParametersByNames(names);
	}

	@Override
	public AppParameter preUpdate(AppParameter anObject) throws SystemException {
		AppParameter dbObject = maintenanceService.findById(anObject.getPkAppParameter());
			anObject.setIsViewable(dbObject.getIsViewable());
		return validate(anObject);
	}

	@Override
	public AppParameter validate(AppParameter anObject) {
		List<ErrorHolder> errorHolders = new ArrayList<ErrorHolder>();
		if(StringFunction.isEmpty(anObject.getName())){
			errorHolders.add(new ErrorHolder(messageSource.getMessage("error.message.parameter.name.mandatory", null, Locale.ENGLISH)));
		}
		if(StringFunction.isEmpty(anObject.getDescr())){
			errorHolders.add(new ErrorHolder(messageSource.getMessage("error.message.parameter.descr.mandatory", null, Locale.ENGLISH)));
		}
		if(StringFunction.isEmpty(anObject.getValue())){
			errorHolders.add(new ErrorHolder(messageSource.getMessage("error.message.parameter.value.mandatory", null, Locale.ENGLISH)));
		}else{
			if(anObject.getDatatype().equals(SystemConstant.FIELD_TYPE_INT) && !IntegerFunction.isInteger(anObject.getValue())){
				errorHolders.add(new ErrorHolder(AppParameter.VALUE, messageSource.getMessage("error.message.parameter.value.invalid.integer", null, Locale.ENGLISH)));
			}else if(anObject.getDatatype().equals(SystemConstant.FIELD_TYPE_BOOLEAN) && !StringFunction.isBoolean(anObject.getValue())){
				errorHolders.add(new ErrorHolder(AppParameter.VALUE, messageSource.getMessage("error.message.parameter.value.invalid.boolean", null, Locale.ENGLISH)));
			}else if(anObject.getDatatype().equals(SystemConstant.FIELD_TYPE_DATE)){
				if(!DateValidator.getInstance().isValid(anObject.getValue(), SystemConstant.SYSTEM_DATE_MASK_2, true)){
					errorHolders.add(new ErrorHolder(AppParameter.VALUE, messageSource.getMessage("error.message.parameter.value.invalid.date", null, Locale.ENGLISH)));
				}
			}else if(anObject.getDatatype().equals(SystemConstant.FIELD_TYPE_DOUBLE)){
				try {
					Double.parseDouble(anObject.getValue());
				} catch (NumberFormatException e) {
					errorHolders.add(new ErrorHolder(AppParameter.VALUE, messageSource.getMessage("error.message.parameter.value.invalid.double", null, Locale.ENGLISH)));
				}
			}else if(anObject.getDatatype().equals(SystemConstant.FIELD_TYPE_LONG)){
				try {
					Long.parseLong(anObject.getValue());
				} catch (NumberFormatException e) {
					errorHolders.add(new ErrorHolder(AppParameter.VALUE, messageSource.getMessage("error.message.parameter.value.invalid.long", null, Locale.ENGLISH)));
				}
			}else if(anObject.getDatatype().equals(SystemConstant.FIELD_TYPE_CRON)) {
				if(!org.quartz.CronExpression.isValidExpression(anObject.getValue())){
					errorHolders.add(new ErrorHolder(AppParameter.VALUE, messageSource.getMessage("error.message.parameter.value.invalid.cron", null, Locale.ENGLISH)));
				}
			}
		}
		if(errorHolders.size()>0){
			throw new SystemException(errorHolders);
		}
		return anObject;
	}
}
