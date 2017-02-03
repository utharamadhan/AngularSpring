package id.base.app.controller.simple;

import id.base.app.SystemConstant;
import id.base.app.controller.SuperController;
import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.rest.RestConstant;
import id.base.app.service.MaintenanceService;
import id.base.app.service.simple.ISimpleObjectService;
import id.base.app.util.StringFunction;
import id.base.app.valueobject.simple.SimpleObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestConstant.RM_SIMPLE_OBJECT)
public class SimpleObjectController extends SuperController<SimpleObject>{
	
	@Autowired
	private ISimpleObjectService simpleObjectService;
	
	@Override
	public SimpleObject validate(SimpleObject anObject) throws SystemException {
		List<ErrorHolder> errorList = new ArrayList<>();
		if(StringFunction.isEmpty(anObject.getName())) {
			errorList.add(new ErrorHolder("name", messageSource.getMessage("error.mandatory", new String[]{"name"}, Locale.ENGLISH)));
		}
		if(errorList.size() > 0) {
			throw new SystemException(errorList);
		}
		return anObject;
	}
	
	@Override
	public MaintenanceService<SimpleObject> getMaintenanceService() {
		return simpleObjectService;
	}
	
	@Override
	public SimpleObject preCreate(SimpleObject anObject) throws SystemException{
		anObject.setStatus(SystemConstant.ValidFlag.VALID);
		return validate(anObject);
	}
	
	@Override
	public SimpleObject preUpdate(SimpleObject anObject) throws SystemException{
		anObject.setStatus(SystemConstant.ValidFlag.VALID);
		return validate(anObject);
	}
	
}
