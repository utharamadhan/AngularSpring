package id.base.app.rest;

import id.base.app.paging.PagingWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class RestBase<T> {
	protected static Logger LOGGER = LoggerFactory.getLogger(RestBase.class);
	
	protected ObjectMapper mapper = new ObjectMapper();
	protected String restUrl = "";
	protected String baseUrl = "";
	protected Class baseClass;

	protected RestTemplate rt = new RestTemplate();
	
	protected List<T> resolveList(ResponseEntity<List> responseEntity){
		List<T> list = new ArrayList<T>();
		if(responseEntity!=null){
			String jsonObj;
		    try {
		    	jsonObj = mapper.writeValueAsString(responseEntity.getBody());
		    	TypeFactory t = TypeFactory.defaultInstance();
				list = mapper.readValue(jsonObj, t.constructCollectionType(ArrayList.class,baseClass));
			} catch (IOException e) {
				e.printStackTrace();
				LOGGER.error("error parsing value ", e);
			}
		}
		return list;
	}
	
	protected List<T> resolveList(ResponseEntity<List> responseEntity, Class collectionClass){
		List<T> list = new ArrayList<T>();
		if(responseEntity!=null){
			String jsonObj;
		    try {
		    	jsonObj = mapper.writeValueAsString(responseEntity.getBody());
		    	TypeFactory t = TypeFactory.defaultInstance();
				list = mapper.readValue(jsonObj, t.constructCollectionType(collectionClass,baseClass));
			} catch (IOException e) {
				e.printStackTrace();
				LOGGER.error("error parsing value ", e);
			}
		}
		return list;
	}
	
	protected PagingWrapper<T> resolvePagingWrapper(ResponseEntity<PagingWrapper> responseEntity){
		PagingWrapper<T> pw = new PagingWrapper<T>();
		if(responseEntity!=null){
			String jsonObj;
		    try {
		    	jsonObj = mapper.writeValueAsString(responseEntity.getBody().getResult());
		    	TypeFactory t = TypeFactory.defaultInstance();
				List<T> list = mapper.readValue(jsonObj, t.constructCollectionType(LinkedList.class,baseClass));
				pw.setResult(list);
				pw.setCurrentPage(responseEntity.getBody().getCurrentPage());
				pw.setMaxRecord(responseEntity.getBody().getMaxRecord());
				pw.setStartRecordIndex(responseEntity.getBody().getStartRecordIndex());
				pw.setRecordsPerPage(responseEntity.getBody().getRecordsPerPage());
				pw.setNoOfPage(responseEntity.getBody().getNoOfPage());
				pw.setCurrentRecord(responseEntity.getBody().getCurrentRecord());
			} catch (IOException e) {
				e.printStackTrace();
				LOGGER.error("error parsing value ", e);
			}
		}
		return pw;
	}
}
