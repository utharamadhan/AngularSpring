package id.base.app.rest;

import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

import com.fasterxml.jackson.core.type.TypeReference;

public class MapRestCaller<T, V> extends RestCaller {
	
	protected static Logger LOGGER = LoggerFactory.getLogger(MapRestCaller.class);
	
	public MapRestCaller(String restUrl, String serviceName) {
		super(restUrl, serviceName);
	}
	
	public MapRestCaller(String restUrl, String baseUrl, Class c){
		super(restUrl, baseUrl, c);
	}
	
	public Map<T, V> executeGetMap(QueryParamInterfaceRestCaller interfaceRestCaller) throws SystemException {
		return executeGetMap(interfaceRestCaller, HashMap.class);
	}
	
	public Map<T, V> executePost(final String path, Object anObject) throws SystemException {
		Map<T, V> resultMap = null;
		URI url;
		try {
			url = new URI(restUrl + baseUrl + path);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity entity = new HttpEntity(anObject, headers);
			
			ResponseEntity<Map> responseEntity = rt.exchange(url, HttpMethod.POST, entity, Map.class);
			resultMap = responseEntity.getBody();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("saveObj rest error {}", e);
		}
		
		return resultMap;
	}
	
	public Map<T, V> deleteReturnMap(Long[] objectPKs, Class mapClass) throws SystemException {
		ResponseEntity<Map> responseEntity = null;
		URI url;
		try {
			url = new URI(restUrl+baseUrl+"/deleteReturnMap");
			UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url).queryParam("objectPKs", objectPKs);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

			responseEntity = rt.exchange(builder.build().toUriString(), HttpMethod.GET, entity, Map.class);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("findObjects rest error {}", e);
		}
		
		return resolveMap(responseEntity, mapClass); 
	}
	
	public Map<T, V> executeGetMap(QueryParamInterfaceRestCaller interfaceRestCaller, Class mapClass) throws SystemException {
		return executeMap(interfaceRestCaller, mapClass, HttpMethod.GET);
	}
	
	public Map<T, V> executePostMap(QueryParamInterfaceRestCaller interfaceRestCaller, Class mapClass) throws SystemException {
		return executeMap(interfaceRestCaller, mapClass, HttpMethod.POST);
	}
	
	public Map<T, V> executeMap(QueryParamInterfaceRestCaller interfaceRestCaller, Class mapClass, HttpMethod method) throws SystemException {
		ResponseEntity<Map> responseEntity = null;
		URI url;
		try {
			url = new URI(RestConstant.REST_SERVICE+baseUrl+interfaceRestCaller.getPath());
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url);
			
			if(interfaceRestCaller.getParameters()!=null){
				for (Entry<String,Object> sparam : interfaceRestCaller.getParameters().entrySet()) {
					if(sparam.getValue() instanceof Collection<?>){
						Collection col = (Collection) sparam.getValue();
						builder.queryParam(sparam.getKey(), (Object[])col.toArray(new Object[col.size()]));
					}else if(sparam.getValue() instanceof Object[]){
						builder.queryParam(sparam.getKey(), (Object[])sparam.getValue());
					}else{
						builder.queryParam(sparam.getKey(), sparam.getValue());
					}
				}
			}

			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

			responseEntity = rt.exchange(builder.build().toUriString(), method, entity, Map.class);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("executeMap rest error {}", e);
		} catch (SystemException e){
			LOGGER.error("executeMap rest system error {}", e);
			throw e;
		}
		return resolveMap(responseEntity, mapClass);
	}
	
	public Long executePostMapReturnId(QueryParamInterfaceRestCaller interfaceRestCaller) throws SystemException {
		List<ErrorHolder> errors;
		ResponseEntity<Long> responseEntity = null;
		URI url;
		Long id = null;
		try {
			url = new URI(RestConstant.REST_SERVICE+baseUrl+interfaceRestCaller.getPath());
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url);
			
			if(interfaceRestCaller.getParameters()!=null){
				for (Entry<String,Object> sparam : interfaceRestCaller.getParameters().entrySet()) {
					if(sparam.getValue() instanceof Collection<?>){
						Collection col = (Collection) sparam.getValue();
						builder.queryParam(sparam.getKey(), (Object[])col.toArray(new Object[col.size()]));
					}else if(sparam.getValue() instanceof Object[]){
						builder.queryParam(sparam.getKey(), (Object[])sparam.getValue());
					}else{
						builder.queryParam(sparam.getKey(), sparam.getValue());
					}
				}
			}

			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

			responseEntity = rt.exchange(builder.build().toUriString(), HttpMethod.POST, entity, Long.class);
			id = responseEntity.getBody();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			LOGGER.error("findObjects rest error {}", e);
			errors = new ArrayList<ErrorHolder>();
			errors.add(new ErrorHolder(e.getMessage()));
		} catch (SystemException e) {
			throw e;
		}
		return id;
	}
	
	private Map<T, V> resolveMap(ResponseEntity<Map> responseEntity, Class mapClass){
		Map<T, V> map = null;
		if(mapClass.equals(HashMap.class)){
			map = new HashMap<T, V>(); 
		}else if(mapClass.equals(LinkedHashMap.class)){
			map = new LinkedHashMap<T, V>();
		}
		if(responseEntity!=null){
			String jsonObj;
			try{
				jsonObj = mapper.writeValueAsString(responseEntity.getBody());
				map = mapper.readValue(jsonObj, new TypeReference<HashMap<T, V>>(){});
			}catch(IOException e){
				e.printStackTrace();
				LOGGER.error("error parsing value ", e);
			}
		}
		
		return map;
	}
}
