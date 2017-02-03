package id.base.app.controller.error;

import id.base.app.controller.SuperController;
import id.base.app.exception.SystemException;
import id.base.app.rest.RestConstant;
import id.base.app.service.MaintenanceService;
import id.base.app.valueobject.Error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestConstant.RM_ERROR)
public class ErrorController extends SuperController<Error> {

	@Autowired
	@Qualifier("errorService")
	private MaintenanceService<Error> maintenanceService;
	
	@Override
	public MaintenanceService<Error> getMaintenanceService() {
		return this.maintenanceService;
	}

	@Override
	public Error validate(Error anObject) throws SystemException {
		return null;
	}

}
