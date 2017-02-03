package id.base.app.rest;

import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;

public class SpecificRestCaller<T> extends RestCaller{	
	
	public SpecificRestCaller(String restUrl, String serviceName) {
		super(restUrl, serviceName);
	}
	
	public SpecificRestCaller(String restUrl, String baseUrl, Class c){
		super(restUrl, baseUrl, c);
	}
	
	public SpecificRestCaller(String restUrl, String serviceName, String userName) {
		super(restUrl, serviceName, userName);
	}
	
	public SpecificRestCaller(String restUrl, String baseUrl, Class c, String userName){
		super(restUrl, baseUrl, c, userName);
	}

	protected static Logger LOGGER = LoggerFactory.getLogger(SpecificRestCaller.class);
	
	public T findDetail(String path, Map<String,Object> params) throws SystemException{
		String url = restUrl+baseUrl+path;
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<T> responseEntity = rt.exchange(url, HttpMethod.GET, entity, baseClass, params);
		return (T) responseEntity.getBody();
	}
	
	public List<T> findList(String path) throws SystemException {
		ResponseEntity<List> responseEntity = null;
		URI url;
		try {
			url = new URI(restUrl+baseUrl+path);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

			responseEntity = rt.exchange(url, HttpMethod.GET, entity, List.class);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("findObjects rest error {}", e);
		}
		return resolveList(responseEntity);
	}
	
	public void delete(PathInterfaceRestCaller interfaceRestCaller) throws SystemException {
		URI url;
		try {
			url = new URI(restUrl+baseUrl+interfaceRestCaller.getPath());
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			rt.delete(url.toString(), interfaceRestCaller.getParameters());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("executePost rest error {}", e);
		} catch (SystemException e) {
			throw e;
		}
	}
	
	public void executePost(SpecificInterfaceRestCaller interfaceRestCaller) throws SystemException {
		URI url;
		try {
			url = new URI(restUrl+baseUrl+interfaceRestCaller.getPath());
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			String value = "";
			try {
				value = mapper.writeValueAsString(interfaceRestCaller.getParameters());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				LOGGER.error("construct json rest error {}", e);
			}

			HttpEntity entity = new HttpEntity(value, headers);

			rt.exchange(url, HttpMethod.POST, entity, String.class);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("executePost rest error {}", e);
		}
	}
	
	public void executePost(final String path, T anObject) throws SystemException {
		URI url;
		try {
			url = new URI(restUrl + baseUrl + path);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<T> entity = new HttpEntity<T>(anObject, headers);

			rt.exchange(url, HttpMethod.POST, entity, anObject.getClass());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("findObjects rest error {}", e);
		} catch (SystemException e) {
			LOGGER.error("findObjects system exception error {}", e);
			throw e;
		}
	}
	
	public List<ErrorHolder> performPost(final String path, T anObject) throws SystemException {
		return executeRest(path,anObject,HttpMethod.POST);
	}
	
	public List<ErrorHolder> performPut(final String path, T anObject) throws SystemException {
		return executeRest(path,anObject,HttpMethod.PUT);
	}
	
	private List<ErrorHolder> executeRest(final String path, T anObject, HttpMethod method) throws SystemException {
		URI url;
		List<ErrorHolder> errors = new ArrayList<ErrorHolder>();
		try {
			url = new URI(restUrl + baseUrl + path);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<T> requestEntity = new HttpEntity<T>(anObject, headers);
			rt.exchange(url, method, requestEntity, anObject.getClass());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("findObjects rest error {}", e);
			errors.add(new ErrorHolder("error.executing.rest.service", "executeRest:"+method.name()));
		} catch (SystemException e) {
			errors.addAll(e.getErrors());
		}
		return errors;
	}
	
	public Object performPostReturn(final String path, T anObject, Class returnClass) throws SystemException {
		return executeRestReturn(path,anObject,returnClass,HttpMethod.POST);
	}
	
	public Object performPutReturn(final String path, T anObject, Class returnClass) throws SystemException {
		return executeRestReturn(path,anObject,returnClass,HttpMethod.PUT);
	}
	
	private Object executeRestReturn(final String path, T anObject, Class returnClass, HttpMethod method) throws SystemException {
		URI url;
		Object returnObj = null;
		List<ErrorHolder> errors = new ArrayList<ErrorHolder>();
		try {
			url = new URI(restUrl + baseUrl + path);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<T> requestEntity = new HttpEntity<T>(anObject, headers);
			ResponseEntity<Object> response = rt.exchange(url, method, requestEntity, returnClass);
			returnObj = response.getBody();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("findObjects rest error {}", e);
			errors.add(new ErrorHolder("error.executing.rest.service", "executeRest:"+method.name()));
			throw new SystemException(errors);
		} catch (SystemException e) {
			throw e;
		}
		return returnObj;
	}
	
	public void executePost(String path, List<T> objects) throws SystemException {
		URI url;
		try {
			url = new URI(restUrl+baseUrl+path);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity entity = new HttpEntity(objects, headers);

			rt.exchange(url, HttpMethod.POST, entity, objects.getClass());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("findObjects rest error {}", e);
		}
	}
	
	public List<T> executeGetList(SpecificInterfaceRestCaller interfaceRestCaller) throws SystemException {
		ResponseEntity<List> responseEntity = null;
		URI url;
		try {
			url = new URI(restUrl+baseUrl+interfaceRestCaller.getPath());
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url);
			
			for (Entry<String, Object> entry : interfaceRestCaller.getParameters().entrySet()) {
				String values = "";
				try {
					values = mapper.writeValueAsString(entry.getValue());
				} catch (JsonProcessingException e) {
					LOGGER.error("construct json rest error {}", e);
					e.printStackTrace();
				}
				builder.queryParam(entry.getKey(), values);
			}

			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

			responseEntity = rt.exchange(builder.build().toUri(), HttpMethod.GET, entity, List.class);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("findObjects rest error {}", e);
		}
		return resolveList(responseEntity);
	}
	
	public List<T> executeGetList(QueryParamInterfaceRestCaller interfaceRestCaller) throws SystemException {
		ResponseEntity<List> responseEntity = null;
		URI url;
		try {
			url = new URI(restUrl+baseUrl+interfaceRestCaller.getPath());
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url);
			
			handleQueryParam(interfaceRestCaller, builder);

			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

			responseEntity = rt.exchange(builder.build().toUri(), HttpMethod.GET, entity, List.class);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("findObjects rest error {}", e);
		}
		return resolveList(responseEntity);
	}
	
	public List<T> executeGetList(PathInterfaceRestCaller interfaceRestCaller) throws SystemException {
		ResponseEntity<List> responseEntity = null;
		String url;
		url = restUrl+baseUrl+interfaceRestCaller.getPath();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		responseEntity = rt.exchange(builder.build().toUriString(), HttpMethod.GET, entity, List.class, interfaceRestCaller.getParameters());
		return resolveList(responseEntity);
	}
	
	public T executeGet(PathInterfaceRestCaller interfaceRestCaller) throws SystemException {
		String url = restUrl+baseUrl+interfaceRestCaller.getPath();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity<T> response = rt.exchange(url,HttpMethod.GET, entity, baseClass, interfaceRestCaller.getParameters());
		return (T) response.getBody();
	}
	
	public T executeGet(PathInterfaceRestCaller interfaceRestCaller, List<MediaType> acceptedMediaTypes) throws SystemException {
		String url = restUrl+baseUrl+interfaceRestCaller.getPath();
		headers.setAccept(acceptedMediaTypes);

		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
		ResponseEntity<T> response = rt.exchange(url,HttpMethod.GET, entity, baseClass, interfaceRestCaller.getParameters());
		return (T) response.getBody();
	}
	
	public T executeGet(QueryParamInterfaceRestCaller interfaceRestCaller) throws SystemException {
		URI url;
		try {
			url = new URI(restUrl+baseUrl+interfaceRestCaller.getPath());
			UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url);
			handleQueryParam(interfaceRestCaller, builder);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
			
			ResponseEntity<T> response = rt.exchange(builder.build().toUri(),HttpMethod.GET, entity, baseClass);
			return (T) response.getBody();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PagingWrapper<T> executeGetPagingWrapper(int startNo, int offset, QueryParamInterfaceRestCaller interfaceRestCaller) throws SystemException {
		ResponseEntity<PagingWrapper> responseEntity = null;
		URI url;
		try {
			url = new URI(restUrl+baseUrl+interfaceRestCaller.getPath());
			UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url).queryParam("startNo", startNo).queryParam("offset", offset);
			handleQueryParam(interfaceRestCaller, builder);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

			responseEntity = rt.exchange(builder.build().toUri(), HttpMethod.GET, entity, PagingWrapper.class);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("findAllByFilter rest error {}", e);
		}
		return resolvePagingWrapper(responseEntity);
	}

	private void handleQueryParam(
			QueryParamInterfaceRestCaller interfaceRestCaller,
			UriComponentsBuilder builder) {
		for (Entry<String,Object> sparam : interfaceRestCaller.getParameters().entrySet()) {
			if(sparam.getValue() instanceof Collection<?>){
				String objJson = "";
				try {
					if(sparam.getValue()!=null){
						objJson = mapper.writeValueAsString(sparam.getValue());
						builder.queryParam(sparam.getKey(), objJson);
					}
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					LOGGER.error("findAllByFilter error build filter{}", e);
				}
			}else if(sparam.getValue() instanceof Object[]){
				builder.queryParam(sparam.getKey(), (Object[])sparam.getValue());
			}else{
				builder.queryParam(sparam.getKey(), sparam.getValue());
			}
		}
	}
}
