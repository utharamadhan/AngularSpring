package id.base.app.rest;

import id.base.app.SystemConstant;
import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module.Feature;

public class RestCaller<T> extends RestBase<T>{
	protected static Logger LOGGER = LoggerFactory.getLogger(RestCaller.class);

	public static final Map<String,String> BASE_URL = new HashMap<String, String>();
	public static final Map<String,Class> BASE_CLASS = new HashMap<String, Class>();
	protected HttpHeaders headers = new HttpHeaders();
	
	private void configureRest(){
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(new MappingJackson2HttpMessageConverter());
		rt.setMessageConverters(list);
		rt.setErrorHandler(new RestErrorHandler());
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setDateFormat(new SimpleDateFormat(SystemConstant.SYSTEM_TIME_MASK));
		Hibernate4Module hbm = new Hibernate4Module();
        hbm.disable(Feature.USE_TRANSIENT_ANNOTATION);
        mapper.registerModule(hbm);
        try{
	        String userName = LoginSessionUtil.getLogin().getUserName();
	        if(StringUtils.isNotEmpty(userName)){
				headers.add(RestConstant.USER_CALLER, userName);
			}
        }catch(Exception e){
        	headers.add(RestConstant.USER_CALLER, SystemConstant.SYSTEM_USER);
        }
	}
	
	public RestCaller(){
		super();
		configureRest();
	}
	
	public RestCaller(String restUrl, String serviceName){
		this.restUrl = restUrl;
		baseUrl = BASE_URL.get(serviceName);
		baseClass = BASE_CLASS.get(serviceName);
		configureRest();
	}
	
	public RestCaller(String restUrl, String baseUrl, Class c){
		this.restUrl = restUrl;
		this.baseUrl = baseUrl;
		this.baseClass = c;
		configureRest();
	}
	
	public RestCaller(String restUrl, String serviceName, String userName){
		this.restUrl = restUrl;
		baseUrl = BASE_URL.get(serviceName);
		baseClass = BASE_CLASS.get(serviceName);
		configureRest();
		if(StringUtils.isNotEmpty(userName)){
			headers.add(RestConstant.USER_CALLER, userName);
		}
	}
	
	public RestCaller(String restUrl, String baseUrl, Class c, String userName){
		this.restUrl = restUrl;
		this.baseUrl = baseUrl;
		this.baseClass = c;
		configureRest();
		if(StringUtils.isNotEmpty(userName)){
			headers.add(RestConstant.USER_CALLER, userName);
		}
	}
	
	public T findById(Long id) throws SystemException {
		String url = restUrl+baseUrl+"/{id}";
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		Map<String, Long> vars = new HashMap<String, Long>();
		vars.put("id", id);
		ResponseEntity<T> response = rt.exchange(url,HttpMethod.GET, entity, baseClass, vars);
		return (T) response.getBody();
	}
	
	@Deprecated
	public void saveOrUpdate(T anObject) throws SystemException {
		URI url;
		try {
			url = new URI(restUrl+baseUrl+"/save");
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity<T> entity = new HttpEntity<T>(anObject, headers);

			rt.exchange(url, HttpMethod.POST, entity, anObject.getClass());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("findObjects rest error {}", e);
		}
	}
	
	public List<ErrorHolder> create(T anObject) throws SystemException {
		List<ErrorHolder> errors;
		URI url;
		try {
			url = new URI(restUrl+baseUrl+"/create");
			HttpEntity<T> entity = new HttpEntity<T>(anObject, headers);

			rt.exchange(url, HttpMethod.POST, entity, anObject.getClass());
			errors = new ArrayList<ErrorHolder>();
		} catch (URISyntaxException e) {
			errors = new LinkedList<ErrorHolder>();
			errors.add(new ErrorHolder("error.executing.rest.service", "create"));
			LOGGER.error("findObjects rest error {}", e);
		} catch (SystemException e) {
			errors = e.getErrors();
		}
		return errors;
	}
	
	public List<ErrorHolder> update(T anObject) throws SystemException {
		List<ErrorHolder> errors;
		URI url;
		try {
			url = new URI(restUrl+baseUrl+"/update");
			HttpEntity<T> entity = new HttpEntity<T>(anObject, headers);

			rt.exchange(url, HttpMethod.PUT, entity, anObject.getClass());
			errors = new ArrayList<ErrorHolder>();
		} catch (URISyntaxException e) {
			errors = new LinkedList<ErrorHolder>();
			errors.add(new ErrorHolder("error.executing.rest.service", "update"));
			LOGGER.error("findObjects rest error {}", e);
		} catch (SystemException e) {
			errors = e.getErrors();
		}
		return errors;
	}
	
	public void saveOrUpdate(Map<String,Object> multipleObjects, String path) throws SystemException {
		URI url;
		try {
			url = new URI(restUrl+baseUrl+path);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity<Map<String,Object>> entity = new HttpEntity<Map<String,Object>>(multipleObjects, headers);

			rt.exchange(url, HttpMethod.POST, entity, multipleObjects.getClass());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("findObjects rest error {}", e);
		}
	}
	
	public void saveList(List<T> objects) throws SystemException {
		URI url;
		try {
			url = new URI(restUrl+baseUrl+"/saveList");
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity<List<T>> entity = new HttpEntity<List<T>>(objects, headers);

			rt.exchange(url, HttpMethod.POST, entity, objects.getClass());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("findObjects rest error {}", e);
		}
	}
	
	public List<ErrorHolder> delete(Long[] objectPKs) throws SystemException {
		return delete(objectPKs,"/delete");
	}
	
	public List<ErrorHolder> delete(Long[] objectPKs, String path) throws SystemException {
		List<ErrorHolder> errors;
		URI url;
		try {
			url = new URI(restUrl+baseUrl+path);
			UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url).queryParam("objectPKs", objectPKs);
			HttpEntity<String> entity = new HttpEntity<String>(headers); 
			rt.exchange(builder.build().toString(), HttpMethod.DELETE, entity, String.class);
			errors = new ArrayList<ErrorHolder>();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			errors = new ArrayList<ErrorHolder>();
			errors.add(new ErrorHolder("error.executing.rest.service", "delete"));
			LOGGER.error("findObjects rest error {}", e);
		} catch (SystemException e) {
			errors = e.getErrors();
		}
		return errors;
	}
	
	public List<T> findObjects(Long[] objectPKs)throws SystemException{
		ResponseEntity<List> responseEntity = null;
		URI url;
		try {
			url = new URI(restUrl+baseUrl+"/listByIds");
			UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url).queryParam("objectPKs", objectPKs);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

			responseEntity = rt.exchange(builder.build().toUri(), HttpMethod.GET, entity, List.class);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("findObjects rest error {}", e);
		}
		return resolveList(responseEntity);
	}
	
	public PagingWrapper<T> findAllByFilter(String listURL, int startNo, int offset,List<SearchFilter> filter,List<SearchOrder> order) throws SystemException {
		ResponseEntity<PagingWrapper> responseEntity = null;
		URI url;
		try {
			url = new URI(restUrl+baseUrl+listURL);
			String filterJson = "";
			try {
				if(filter!=null){
					filterJson = mapper.writeValueAsString(filter);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				LOGGER.error("findAllByFilter error build filter{}", e);
			}
			String orderJson = "";
			try {
				if(order!=null){
					orderJson = mapper.writeValueAsString(order);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				LOGGER.error("findAllByFilter error build order{}", e);
			}
			UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url).queryParam("startNo", startNo).queryParam("offset", offset).queryParam("filter", filterJson).queryParam("order", orderJson);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

			responseEntity = rt.exchange(builder.build().toUri(), HttpMethod.GET, entity, PagingWrapper.class);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("findAllByFilter rest error {}", e);
		}
		return resolvePagingWrapper(responseEntity);
	}
	
	public PagingWrapper<T> findAllByFilter(int startNo, int offset,List<SearchFilter> filter,List<SearchOrder> order) throws SystemException {
		ResponseEntity<PagingWrapper> responseEntity = null;
		URI url;
		try {
			url = new URI(restUrl+baseUrl);
			String filterJson = "";
			try {
				if(filter!=null){
					filterJson = mapper.writeValueAsString(filter);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				LOGGER.error("findAllByFilter error build filter{}", e);
			}
			String orderJson = "";
			try {
				if(order!=null){
					orderJson = mapper.writeValueAsString(order);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				LOGGER.error("findAllByFilter error build order{}", e);
			}
			UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url).queryParam("startNo", startNo).queryParam("offset", offset).queryParam("filter", filterJson).queryParam("order", orderJson);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

			responseEntity = rt.exchange(builder.build().toUri(), HttpMethod.GET, entity, PagingWrapper.class);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("findAllByFilter rest error {}", e);
		}
		return resolvePagingWrapper(responseEntity);
	}
	
	public List<T> findAll(List<SearchFilter> filter,List<SearchOrder> order) throws SystemException {
		ResponseEntity<List> responseEntity = null;
		URI url;
		try {
			url = new URI(restUrl+baseUrl+"/listAll");
			String filterJson = "";
			try {
				if(filter!=null){
					filterJson = mapper.writeValueAsString(filter);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				LOGGER.error("findAllByFilter error build filter{}", e);
			}
			String orderJson = "";
			try {
				if(order!=null){
					orderJson = mapper.writeValueAsString(order);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				LOGGER.error("findAllByFilter error build order{}", e);
			}
			UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url).queryParam("filter", filterJson).queryParam("order", orderJson);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

			responseEntity = rt.exchange(builder.build().toUri(), HttpMethod.GET, entity, List.class);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("findAllByFilter rest error {}", e);
		}
		
		return resolveList(responseEntity);
	}
	
	public List<T> findAll(String path, List<SearchFilter> filter,List<SearchOrder> order) throws SystemException {
		ResponseEntity<List> responseEntity = null;
		URI url;
		try {
			url = new URI(restUrl+baseUrl+path);
			String filterJson = "";
			try {
				if(filter!=null){
					filterJson = mapper.writeValueAsString(filter);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				LOGGER.error("findAllByFilter error build filter{}", e);
			}
			String orderJson = "";
			try {
				if(order!=null){
					orderJson = mapper.writeValueAsString(order);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				LOGGER.error("findAllByFilter error build order{}", e);
			}
			UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url).queryParam("filter", filterJson).queryParam("order", orderJson);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

			responseEntity = rt.exchange(builder.build().toUri(), HttpMethod.GET, entity, List.class);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("findAllByFilter rest error {}", e);
		}
		
		return resolveList(responseEntity);
	}
}
