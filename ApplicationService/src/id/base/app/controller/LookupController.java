package id.base.app.controller;

import id.base.app.SystemConstant;
import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.rest.RestConstant;
import id.base.app.service.MaintenanceService;
import id.base.app.service.lookup.ILookupGroupService;
import id.base.app.service.lookup.ILookupService;
import id.base.app.util.StringFunction;
import id.base.app.util.dao.Operator;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.util.dao.SearchOrder.Sort;
import id.base.app.valueobject.Lookup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestConstant.RM_LOOKUP)
public class LookupController extends SuperController<Lookup>{
	
	@Autowired
	@Qualifier("lookupService")
	private MaintenanceService<Lookup> maintenanceService;
	
	@Autowired
	@Qualifier("lookupService")
	private ILookupService service;
	
	@Autowired
	@Qualifier("lookupGroupService")
	private ILookupGroupService lookupGroupService;

	@RequestMapping(method=RequestMethod.GET, value="/findByLookupGroup/{lookupGroup}")
	@ResponseBody
	public List<Lookup> findByLookupGroup(@PathVariable("lookupGroup") String lookupGroup) throws SystemException {
		return service.findByLookupGroup(lookupGroup);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findByLookupGroupAndUsage/{lookupGroup}/{usage}")
	@ResponseBody
	public List<Lookup> findByLookupGroupAndUsage(@PathVariable("lookupGroup") String lookupGroup, @PathVariable("usage") String usage) throws SystemException {
		return service.findByLookupGroupAndUsage(lookupGroup, usage);
	}
	
	
	
	@RequestMapping(method=RequestMethod.GET, value="/findByLookupGroups")
	@ResponseBody
	public List<Lookup> findByLookupGroup(@RequestParam(value="lg") List<String> lookupGroups) throws SystemException {
		List<Lookup> lookups = new LinkedList<Lookup>();
		List<SearchFilter> filter = new ArrayList<SearchFilter>();
		filter.add(new SearchFilter(Lookup.STATUS, Operator.EQUALS, SystemConstant.ValidFlag.VALID));
		filter.add(new SearchFilter(Lookup.LOOKUP_GROUP_STRING, Operator.IN, lookupGroups));
		List<SearchOrder> order = new ArrayList<SearchOrder>();
		order.add(new SearchOrder(Lookup.LOOKUP_GROUP_STRING, Sort.ASC));
		order.add(new SearchOrder(Lookup.ORDER_NO_STRING, Sort.ASC));
		lookups = maintenanceService.findAll(filter, order);
		return lookups;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findByLookupGroup/{lookupGroup}/{orderBy}/{desc}")
	@ResponseBody
	public List<Lookup> findByLookupGroupOrderBy(@PathVariable("lookupGroup") String lookupGroup, @PathVariable("orderBy") String orderBy, @PathVariable("desc") boolean desc) throws SystemException {
		return service.findByLookupGroupOrderBy(lookupGroup, orderBy, desc);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findById/{id}")
	@ResponseBody
	public Lookup findLookupById(@PathVariable("id") Long id) throws SystemException {
		return service.findById(id);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findLookupByCodeAndLookupGroup/{code}/{lookupGroup}")
	@ResponseBody
	public Lookup findLookupByCodeAndLookupGroup(@PathVariable("code") String code, @PathVariable("lookupGroup") String lookupGroup) throws SystemException {
		return service.findByCode(code, lookupGroup);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findByCodeAndLookupGroup/{lookupCode}/{lookupGroup}")
	@ResponseBody
	public Lookup findByCodeAndLookupGroup(@PathVariable("lookupCode") String code, @PathVariable("lookupGroup") String lookupGroup) throws SystemException {
		return service.findByCode(code, lookupGroup);
	}
	
	@Override
	public MaintenanceService<Lookup> getMaintenanceService() {
		return this.maintenanceService;
	}
	
	@Override
	public Lookup preUpdate(Lookup anObject) throws SystemException {
		Lookup dbObject = maintenanceService.findById(anObject.getPkLookup());
		//start set unchanged property of object, if massive set, please create private method
		anObject.setStatus(dbObject.getStatus());
		anObject.setLookupGroupString(dbObject.getLookupGroupString());
		anObject.setOrderNo(dbObject.getOrderNo());
		//finish
		return validate(anObject);
	}
	
	@Override
	public Lookup preCreate(Lookup anObject) throws SystemException {
		List<Lookup> lookups = service.findByLookupGroup(anObject.getLookupGroupString());
		//start set unchanged property of object, if massive set, please create private method
		anObject.setStatus(SystemConstant.ValidFlag.VALID);
		if (lookups != null) {
			anObject.setOrderNo(new Long(lookups.size()+1));
		} else {
			anObject.setOrderNo(1l);
		}
		//finish
		return validate(anObject);
	}
	
	@Override
	public Long[] preDelete(Long[] objectPKs) throws SystemException {
		List<ErrorHolder> errorHolders = new ArrayList<ErrorHolder>();
		for (Long objectPK : objectPKs) {
			if(!lookupGroupService.checkUpdatableByLookupPK(objectPK)){
				errorHolders.add(new ErrorHolder(messageSource.getMessage("error.lookup.is.not.modifiable", null, Locale.ENGLISH)));
				break;
			}
		}
		if(errorHolders.size()>0){
			throw new SystemException(errorHolders);
		}
		return objectPKs;
	}
	
	@Override
	public Lookup validate(Lookup anObject){
		List<ErrorHolder> errorHolders = new ArrayList<ErrorHolder>();
		if(!lookupGroupService.checkUpdatableByGroupName(anObject.getLookupGroupString())){
			errorHolders.add(new ErrorHolder(messageSource.getMessage("error.lookup.is.not.modifiable", null, Locale.ENGLISH)));
		}
		if(StringFunction.isEmpty(anObject.getCode())){
			errorHolders.add(new ErrorHolder(Lookup.CODE, messageSource.getMessage("error.lookup.code.mandatory", null, Locale.ENGLISH)));
		}
		if(StringFunction.isEmpty(anObject.getName())){
			errorHolders.add(new ErrorHolder(Lookup.NAME, messageSource.getMessage("error.lookup.name.mandatory", null, Locale.ENGLISH)));
		}
		if(StringFunction.isEmpty(anObject.getDescr())){
			errorHolders.add(new ErrorHolder(Lookup.DESCRIPTION, messageSource.getMessage("error.lookup.descr.mandatory", null, Locale.ENGLISH)));
		}
		if(errorHolders.size()>0){
			throw new SystemException(errorHolders);
		}
		return anObject;
	}

}
