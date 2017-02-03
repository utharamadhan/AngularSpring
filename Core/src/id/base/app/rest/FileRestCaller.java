package id.base.app.rest;

import id.base.app.SystemConstant;
import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.report.ReportUtils;
import id.base.app.util.StringFunction;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module.Feature;

public class FileRestCaller {
	private static Logger LOGGER = LoggerFactory.getLogger(FileRestCaller.class);
	
	protected ObjectMapper mapper = new ObjectMapper();
	protected boolean useCookie = false;
	private HttpHeaders headers = new HttpHeaders();
	
	protected String path;
	protected String filename;
	ByteArrayInputStream bias = null;
	public FileRestCaller() {
	}
	
	public FileRestCaller(String path, String filename) {
		this.path = path;
		this.filename = filename;
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

	private void export(final HttpServletResponse response, String extension)
			throws SystemException {
		if(StringFunction.isEmpty(path)){
			throw new SystemException(new ErrorHolder("FRC Properties not properly set"));
		}
		if(filename!=null){
			filename = ReportUtils.fixFileName(filename, extension);
		}
		try {
			RestTemplate rt = new RestTemplate();
			URI url;
			try {
				url = new URI(path);
				callFile(response, extension, rt, url);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
	    } catch (IOException ex) {
	    	throw new RuntimeException("IOError writing file to output stream");
	    }
	}
	
	private void export(final HttpServletRequest fileRequest, final HttpServletResponse response, String extension, List<SearchFilter> filters, List<SearchOrder> orders)
			throws SystemException {
		if(StringFunction.isEmpty(path)||StringFunction.isEmpty(filename)){
			throw new SystemException(new ErrorHolder("FRC Properties not properly set"));
		}
		filename = ReportUtils.fixFileName(filename, extension);
		try {
			RestTemplate rt = new RestTemplate();
			URI url;
			try {
				url = new URI(path);
				String filterJson = "";
				try {
					if(filters!=null){
						filterJson = mapper.writeValueAsString(filters);
					}
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					LOGGER.error("findAllByFilter error build filter{}", e);
				}
				String orderJson = "";
				try {
					if(orders!=null){
						orderJson = mapper.writeValueAsString(orders);
					}
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					LOGGER.error("findAllByFilter error build order{}", e);
				}
				UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url);
				if(filename!=null){
					builder.queryParam("filename", filename);
				}
				if(filters!=null){
					builder.queryParam("filter", filterJson);
				}
				if(orders!=null){
					builder.queryParam("order", orderJson);
				}
				callFile(fileRequest, response, extension, rt, builder.build().toUri());
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
	    } catch (IOException ex) {
	    	throw new RuntimeException("IOError writing file to output stream");
	    }
	}
	
	private void export(final HttpServletResponse response, String extension, List<SearchFilter> filters, List<SearchOrder> orders)
			throws SystemException {
		if(StringFunction.isEmpty(path)||StringFunction.isEmpty(filename)){
			throw new SystemException(new ErrorHolder("FRC Properties not properly set"));
		}
		filename = ReportUtils.fixFileName(filename, extension);
		try {
			RestTemplate rt = new RestTemplate();
			URI url;
			try {
				url = new URI(path);
				String filterJson = "";
				try {
					if(filters!=null){
						filterJson = mapper.writeValueAsString(filters);
					}
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					LOGGER.error("findAllByFilter error build filter{}", e);
				}
				String orderJson = "";
				try {
					if(orders!=null){
						orderJson = mapper.writeValueAsString(orders);
					}
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					LOGGER.error("findAllByFilter error build order{}", e);
				}
				UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url);
				if(filename!=null){
					builder.queryParam("filename", filename);
				}
				if(filters!=null){
					builder.queryParam("filter", filterJson);
				}
				if(orders!=null){
					builder.queryParam("order", orderJson);
				}
				callFile(response, extension, rt, builder.build().toUri());
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
	    } catch (IOException ex) {
	    	throw new RuntimeException("IOError writing file to output stream");
	    }
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	private void callFile(final HttpServletRequest fileRequest, final HttpServletResponse response, String extension,
			RestTemplate rt,
			URI url) throws IOException {
		HttpMessageConverterExtractor<byte[]> responseExtractor =
			       new HttpMessageConverterExtractor(byte[].class, rt.getMessageConverters());
		
		headers.setAccept(MediaType.parseMediaTypes(ReportUtils.mediaTypeMap.get(extension)));
		RequestCallback rcb = new RequestCallback() {
			
			@Override
			public void doWithRequest(ClientHttpRequest request) throws IOException {
				HttpHeaders httpHeaders = request.getHeaders();
				HttpHeaders requestHeaders = headers;
				if (!requestHeaders.isEmpty()) {
					httpHeaders.putAll(requestHeaders);
				}
				if (httpHeaders.getContentLength() == -1) {
					httpHeaders.setContentLength(0L);
				}
			}
		};
		byte[] bis = rt.execute(url, HttpMethod.GET, rcb, responseExtractor);
		response.setHeader("Content-Disposition", "attachment; filename=" + filename );
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		if(this.useCookie){
			response.setHeader("Set-Cookie", "fileDownload=true; path="+fileRequest.getContextPath());
		}
		response.setContentType(ReportUtils.mediaTypeMap.get(extension));
		if(bis!=null){
			ByteArrayInputStream bias = new ByteArrayInputStream(bis);
			// copy it to response's OutputStream
			IOUtils.copy(bias, response.getOutputStream());
		}
		response.flushBuffer();
	}

	@SuppressWarnings({ "rawtypes", "unchecked"})
	private void callFile(final HttpServletResponse response, String extension,
			RestTemplate rt,
			URI url) throws IOException {
		HttpMessageConverterExtractor<byte[]> responseExtractor =
			       new HttpMessageConverterExtractor(byte[].class, rt.getMessageConverters());
		
		headers.setAccept(MediaType.parseMediaTypes(ReportUtils.mediaTypeMap.get(extension)));
		byte[] bis = rt.execute(url, HttpMethod.GET, null, responseExtractor);
		response.setHeader("Content-Disposition", "attachment; filename=" + filename );
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setContentType(ReportUtils.mediaTypeMap.get(extension));
		if(bis!=null){
			ByteArrayInputStream bias = new ByteArrayInputStream(bis);
			// copy it to response's OutputStream
			IOUtils.copy(bias, response.getOutputStream());
		}
		response.flushBuffer();
	}
	
	public void exportTxtFile(final HttpServletResponse response) throws SystemException{
		export(response,ReportUtils.TXT_EXTENSION);
	}
	
	public void exportTxtFile(final HttpServletResponse response, final List<SearchFilter> filter, final List<SearchOrder> order) throws SystemException{
		export(response,ReportUtils.TXT_EXTENSION, filter, order);
	}
	
	public void exportXlsFile(final HttpServletResponse response) throws SystemException{
		export(response,ReportUtils.XLS_EXTENSION);
	}
	
	public void exportXlsFile(final HttpServletResponse response, final List<SearchFilter> filter, final List<SearchOrder> order) throws SystemException{
		export(response,ReportUtils.XLS_EXTENSION, filter, order);
	}
	
	public void exportXlsFile(final HttpServletRequest fileRequest, final HttpServletResponse response, final List<SearchFilter> filter, final List<SearchOrder> order) throws SystemException{
		export(fileRequest, response,ReportUtils.XLS_EXTENSION, filter, order);
	}
	
	public void exportPdfFile(final HttpServletResponse response) throws SystemException{
		export(response,ReportUtils.PDF_EXTENSION);
	}
	
	public void exportPdfFile(final HttpServletResponse response, final List<SearchFilter> filter, final List<SearchOrder> order) throws SystemException{
		export(response,ReportUtils.PDF_EXTENSION, filter, order);
	}
	
	public void exportManualFlush(final HttpServletResponse response, String extension)
			throws SystemException {
		if(StringFunction.isEmpty(path)){
			throw new SystemException(new ErrorHolder("FRC Properties not properly set"));
		}
		if(filename!=null){
			filename = ReportUtils.fixFileName(filename, extension);
		}
		try {
			RestTemplate rt = new RestTemplate();
			URI url;
			try {
				url = new URI(path);
				HttpMessageConverterExtractor<byte[]> responseExtractor =
					       new HttpMessageConverterExtractor(byte[].class, rt.getMessageConverters());
				headers.setAccept(MediaType.parseMediaTypes(ReportUtils.mediaTypeMap.get(extension)));
				byte[] bis = rt.execute(url, HttpMethod.GET, null, responseExtractor);
				response.setHeader("Content-Disposition", "attachment; filename=" + filename );
				response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setContentType(ReportUtils.mediaTypeMap.get(extension));
				if(bis!=null){
					ByteArrayInputStream bias = new ByteArrayInputStream(bis);
					IOUtils.copy(bias, response.getOutputStream());
				}
				// copy it to response's OutputStream
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
	    } catch (IOException ex) {
	    	throw new RuntimeException("IOError writing file to output stream");
	    }
	}
}
