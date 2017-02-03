package id.base.app.controller;

import id.base.app.SystemConstant;
import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.rest.RestConstant;
import id.base.app.service.MaintenanceService;
import id.base.app.service.lookup.ILookupAddressService;
import id.base.app.util.StringFunction;
import id.base.app.valueobject.LookupAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestConstant.RM_LOOKUP_ADDRESS)
public class LookupAddressController extends SuperController<LookupAddress>{
	
	@Autowired
	@Qualifier("lookupAddressService")
	private MaintenanceService<LookupAddress> maintenanceService;
	
	@Autowired
	@Qualifier("lookupAddressService")
	private ILookupAddressService service;
	
	@RequestMapping(method=RequestMethod.GET, value="/findByLookupAddressGroup/{lookupAddressGroup}")
	@ResponseBody
	public List<LookupAddress> findByLookupAddressGroup(@PathVariable("lookupAddressGroup") String lookupAddressGroup) throws SystemException {
		return service.findByLookupAddressGroup(lookupAddressGroup);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findByLookupAddressGroupOrderBy/{lookupAddressGroup}/{orderBy}/{desc}")
	@ResponseBody
	public List<LookupAddress> findByLookupAddressGroupOrderBy(@PathVariable("lookupAddressGroup") String lookupAddressGroup, @PathVariable("orderBy") String orderBy, @PathVariable("desc") boolean desc) throws SystemException {
		return service.findByGroupOrderBy(lookupAddressGroup, orderBy, desc);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findLookupAddressById/{id}")
	@ResponseBody
	public LookupAddress findLookupAddressById(@PathVariable("id") Long id) throws SystemException {
		return service.findById(id);
	}
	
	@Override
	public MaintenanceService<LookupAddress> getMaintenanceService() {
		return this.maintenanceService;
	}
	
	@Override
	public LookupAddress preUpdate(LookupAddress anObject) throws SystemException {
		return validate(anObject);
	}
	
	@Override
	public LookupAddress preCreate(LookupAddress anObject) throws SystemException {
		List<LookupAddress> list = service.findByLookupAddressGroup(anObject.getAddressGroup());
		anObject.setStatus(SystemConstant.ValidFlag.VALID);
		if (anObject.getOrderNo() == null) {
			anObject.setOrderNo(new Long(list.size()+1));
		}
		return validate(anObject);
	}
	
	@Override
	public Long[] preDelete(Long[] objectPKs) throws SystemException {
		return objectPKs;
	}
	
	@Override
	public LookupAddress validate(LookupAddress anObject){
		List<ErrorHolder> errorHolders = new ArrayList<ErrorHolder>();
		if(StringFunction.isEmpty(anObject.getName())){
			errorHolders.add(new ErrorHolder(messageSource.getMessage("error.lookup.name.mandatory", null, Locale.ENGLISH)));
		}
		if(errorHolders.size()>0){
			throw new SystemException(errorHolders);
		}
		return anObject;
	}
}