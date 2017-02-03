package id.base.app.controller;

import id.base.app.exception.SystemException;
import id.base.app.rest.RestConstant;
import id.base.app.service.MaintenanceService;
import id.base.app.service.lookup.ILookupGroupService;
import id.base.app.valueobject.LookupGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestConstant.RM_LOOKUP_GROUP)
public class LookupGroupController extends SuperController<LookupGroup>{

	@Autowired
	@Qualifier("lookupGroupService")
	private MaintenanceService<LookupGroup> maintenanceService;
	
	@Autowired
	private ILookupGroupService lookupGroupService;
	
	@Override
	public MaintenanceService<LookupGroup> getMaintenanceService() {
		return this.maintenanceService;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findLookupGroupByName/{name}")
	public LookupGroup findLookupGroupByName(@PathVariable( "name" ) String name) {
		return lookupGroupService.findByName(name);
	}
	
	@Override
	public LookupGroup validate(LookupGroup anObject) throws SystemException {
		return null;
	}

}
