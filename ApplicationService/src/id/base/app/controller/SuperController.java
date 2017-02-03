package id.base.app.controller;

import id.base.app.SystemConstant;
import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.service.MaintenanceService;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.validation.InvalidRequestException;
import id.base.app.valueobject.CreateEntity;
import id.base.app.valueobject.UpdateEntity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

public abstract class SuperController<T> {
	
	protected static Logger LOGGER = LoggerFactory.getLogger(SuperController.class);
	
	protected ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	protected ResourceBundleMessageSource messageSource;
	
	public SuperController(){
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setDateFormat(new SimpleDateFormat(SystemConstant.SYSTEM_TIME_MASK));
		mapper.registerModule(new Hibernate4Module());
	}
	
	public abstract MaintenanceService<T> getMaintenanceService();
	
	public abstract T validate(T anObject) throws SystemException;
	
	public T preSaveProcess(T anObject){
		return anObject;
	}
	
	public T preCreate(T anObject) throws SystemException{
		LOGGER.warn("Calling Pre-Create without implementation: {}", this.getClass().getName());
		return anObject;
	}
	
	public T preUpdate(T anObject) throws SystemException{
		LOGGER.warn("Calling Pre-Update without implementation: {}", this.getClass().getName());
		return anObject;
	}
	
	public Long[] preDelete(Long[] objectPKs) throws SystemException{
		LOGGER.warn("Calling Pre-Delete without implementation: {}", this.getClass().getName());
		return objectPKs;
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/delete")
	@ResponseStatus( HttpStatus.OK )
	public void delete(@RequestParam(value="objectPKs") Long[] objectPKs) throws SystemException {
		getMaintenanceService().delete(preDelete(objectPKs));
	}
	
	@RequestMapping(method=RequestMethod.GET, value="{id}")
	@ResponseBody
	public T findById(@PathVariable( "id" ) Long id) throws SystemException {
		T anObject = getMaintenanceService().findById(id);
		try {
			LOGGER.debug(mapper.writeValueAsString(anObject));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return anObject;
	}
	
	@Deprecated
	@RequestMapping(method=RequestMethod.POST, value="/save")
	@ResponseStatus( HttpStatus.OK )
	public void saveOrUpdate(@RequestBody @Valid T anObject, BindingResult bindingResult) throws SystemException {
		if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid validation", bindingResult);
        }
	    getMaintenanceService().saveOrUpdate(preSaveProcess(anObject));
	}
	
	/**
	 * @param anObject
	 * @param bindingResult
	 * @throws SystemException
	 * Creating/Inserting new object, object sent must not have primary key, else error
	 */
	@RequestMapping(method=RequestMethod.POST, value="/create")
	@ResponseStatus( HttpStatus.CREATED )
	public void create(@RequestBody @Validated(CreateEntity.class) T anObject, BindingResult bindingResult) throws SystemException {
		if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid validation", bindingResult);
        }
	    getMaintenanceService().saveOrUpdate(preCreate(anObject));
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/update")
	@ResponseStatus( HttpStatus.OK )
	public void update(@RequestBody @Validated(UpdateEntity.class) T anObject, BindingResult bindingResult) throws SystemException {
		if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid validation", bindingResult);
        }
	    getMaintenanceService().saveOrUpdate(preUpdate(anObject));
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/listByIds")
	@ResponseBody
	public List<T> findObjects(@RequestParam(value="objectPKs") Long[] objectPKs) throws SystemException {
		return getMaintenanceService().findObjects(objectPKs);
	}

	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public PagingWrapper<T> findAllByFilter(
			@RequestParam(value="startNo",defaultValue="1") int startNo, 
			@RequestParam(value="offset",defaultValue="10") int offset,
			@RequestParam(value="filter", defaultValue="", required=false) String filterJson,
			@RequestParam(value="order", defaultValue="", required=false) String orderJson)
			throws SystemException {
		PagingWrapper<T> pw = new PagingWrapper<T>();
		try {
			List<SearchFilter> filter = new ArrayList<SearchFilter>();
			if(StringUtils.isNotEmpty(filterJson)){
				filter = mapper.readValue(filterJson, new TypeReference<List<SearchFilter>>(){});
			}
			List<SearchOrder> order = new ArrayList<SearchOrder>();
			if(StringUtils.isNotEmpty(orderJson)){
				order = mapper.readValue(orderJson, new TypeReference<List<SearchOrder>>(){});
			}
			pw = getMaintenanceService().findAllByFilter(startNo, offset, filter, order);
			LOGGER.debug(mapper.writeValueAsString(pw));
			return pw;
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("error finding your data",e);
			throw new SystemException(new ErrorHolder("error finding your data"));
		}
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/listAll")
	@ResponseBody
	public List<T> findAll(
			@RequestParam(value="filter", defaultValue="", required=false) String filterJson,
			@RequestParam(value="order", defaultValue="", required=false) String orderJson)
			throws SystemException {
		try {
			List<SearchFilter> filter = new ArrayList<SearchFilter>();
			if(StringUtils.isNotEmpty(filterJson)){
				filter = mapper.readValue(filterJson, new TypeReference<List<SearchFilter>>(){});
			}
			List<SearchOrder> order = new ArrayList<SearchOrder>();
			if(StringUtils.isNotEmpty(orderJson)){
				order = mapper.readValue(orderJson, new TypeReference<List<SearchOrder>>(){});
			}
			List<T> list = getMaintenanceService().findAll(filter, order);
			return list;
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("error finding your data",e);
			throw new SystemException(new ErrorHolder("error finding your data"));
		}
	}
	
}
