package id.base.app.controller;

import id.base.app.SystemConstant;
import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.rest.RestConstant;
import id.base.app.service.MaintenanceService;
import id.base.app.service.role.IRoleService;
import id.base.app.util.StringFunction;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.validation.InvalidRequestException;
import id.base.app.valueobject.AppRole;
import id.base.app.valueobject.CreateEntity;
import id.base.app.valueobject.UpdateEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(RestConstant.RM_ROLE)
public class AppRoleController extends SuperController<AppRole>{
	
	@Autowired
	@Qualifier("roleService")
	private MaintenanceService<AppRole> maintenanceService;
	
	@Autowired
	private IRoleService service;

	@RequestMapping(method=RequestMethod.GET, value="/findInternalRoles")
	@ResponseBody
	public List<AppRole> findInternalRoles() {
		return service.findInternalRoles();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findAllRoleCodeAndName")
	@ResponseBody
	public List<AppRole> findAllRoleCodeAndName() {
		return service.findAllRoleCodeAndName();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findExternalRoles")
	@ResponseBody
	public List<AppRole> findExternalRoles() {
		return service.findExternalRoles();
	}

	@RequestMapping(method=RequestMethod.GET, value="/listByIds")
	@ResponseBody
	public List<AppRole> findObjects(@RequestParam(value="objectPKs") Long[] objectPKs) throws SystemException {
		return maintenanceService.findObjects(objectPKs);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/list")
	@ResponseBody
	public List<AppRole> findAllRole() {
		return service.findAllRole();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/listAllByFilter")
	@ResponseBody
	public List<AppRole> findAllByFilter(
			@RequestParam(value="filter", defaultValue="", required=false) String filterJson, 
			@RequestParam(value="order", defaultValue="", required=false) String orderJson)
			throws SystemException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<SearchFilter> filter = mapper.readValue(filterJson, new TypeReference<List<SearchFilter>>(){});
			List<SearchOrder> order = mapper.readValue(orderJson, new TypeReference<List<SearchOrder>>(){});
			return service.findAllByFilter(filter, order);
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException(new ErrorHolder("error finding your data"));
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findByIdFetchUsers/{id}")
	@ResponseBody
	public AppRole findByIdFetchUsers(@PathVariable("id") Long pkAppRole) {
		return service.findByIdFetchUsers(pkAppRole);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/findAppRolesByAppUserId/{id}")
	@ResponseBody
	public List<AppRole> findAppRolesByAppUserId(@PathVariable("id") Long pkAppRole) {
		return service.findAppRolesByAppUserId(pkAppRole);
	}

	@RequestMapping(method=RequestMethod.POST, value="/saveNewUsers")
	@ResponseStatus( HttpStatus.OK )
	public void saveNewUsers(@RequestParam(value="userRolePK") Long userRolePK, @RequestParam(value="pkUsers") Long[] pkUsers) {
		service.saveNewUsers(userRolePK, pkUsers);
	}
	
	/**
	 * @param roles
	 */
	@RequestMapping(method=RequestMethod.GET, value="/saveRemoveAppRoles")
	@ResponseBody
	@Deprecated
	public Map<String, Object> saveAppRoles(@RequestParam(value="rolePks") Long[] rolePks) throws SystemException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> resultMap2 = new HashMap<String, Object>();
		List<ErrorHolder> errorHolders = new ArrayList<ErrorHolder>();
		List<AppRole> roles = findObjects(rolePks);
		String names="";
		for(AppRole role : roles){
			int size = resultMap.size();
			service.saveOrUpdateMap(role, "remove");
			if(size != resultMap.size()){
				errorHolders.addAll((Collection<? extends ErrorHolder>) resultMap.get(SystemConstant.ERROR_LIST));
				size = resultMap.size();
			}
			names = names+role.getName()+",";
		}
		if(errorHolders.size()>0){
			resultMap2.put(SystemConstant.ERROR_LIST, errorHolders);
		}
		names = names.substring(0, names.length()-1);
		resultMap2.put("name", names);
		return resultMap2;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/addRole")
	@ResponseStatus( HttpStatus.OK )
	public void addRole(@RequestBody @Validated(CreateEntity.class) AppRole anObject, BindingResult bindingResult) throws SystemException {
		if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid validation", bindingResult);
        }
		service.saveOrUpdateMap(preCreate(anObject), AppRole.OP_ADD);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/editRole")
	@ResponseStatus( HttpStatus.OK )
	public void editRole(@RequestBody @Validated(UpdateEntity.class) AppRole anObject, BindingResult bindingResult) throws SystemException {
		if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid validation", bindingResult);
        }
		service.saveOrUpdateMap(preUpdate(anObject), AppRole.OP_EDIT);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/removeRole")
	@ResponseStatus( HttpStatus.OK )
	public void removeRole(@RequestParam(value="objectPKs") Long[] rolePks) throws SystemException {
		List<AppRole> roles = findObjects(rolePks);
		for(AppRole role : roles){
			service.saveOrUpdateMap(role, AppRole.OP_REMOVE);
		}
	}
	
	@Override
	public AppRole validate(AppRole anObject) throws SystemException {
		List<ErrorHolder> errorHolders = new ArrayList<ErrorHolder>();
		if(StringFunction.isEmpty(anObject.getCode())){
			errorHolders.add(new ErrorHolder(messageSource.getMessage("error.message.user.role.code.mandatory", null, Locale.ENGLISH)));
		}
		if(StringFunction.isEmpty(anObject.getName())){
			errorHolders.add(new ErrorHolder(messageSource.getMessage("error.message.user.role.name.mandatory", null, Locale.ENGLISH)));
		}
		if(anObject.getType() == null || anObject.getType() == 0){
			errorHolders.add(new ErrorHolder(messageSource.getMessage("error.message.user.role.type.mandatory", null, Locale.ENGLISH)));
		}
		if(errorHolders.size()>0){
			throw new SystemException(errorHolders);
		}
		return anObject;
	}
	
	@Override
	public AppRole preUpdate(AppRole anObject) throws SystemException {
		return validate(anObject);
	}
	
	@Override
	public AppRole preCreate(AppRole anObject) throws SystemException {
		return validate(anObject);
	}

	@Override
	public MaintenanceService<AppRole> getMaintenanceService() {
		return this.maintenanceService;
	}
}
