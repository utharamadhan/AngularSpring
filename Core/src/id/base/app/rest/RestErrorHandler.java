package id.base.app.rest;

import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestErrorHandler extends DefaultResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return super.hasError(response);
	}
	
	private boolean checkError(ClientHttpResponse response) throws IOException{
		HttpStatus status = response.getStatusCode();
		if(status.equals(HttpStatus.UNPROCESSABLE_ENTITY) || status.equals(HttpStatus.INTERNAL_SERVER_ERROR) || status.equals(HttpStatus.BAD_REQUEST)){
			return true;
		}else{
			return false;
		}
	}

	private List<ErrorHolder> getErrorResponse(ClientHttpResponse response)
			throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<ErrorHolder> errors = mapper.readValue(response.getBody(), new TypeReference<List<ErrorHolder>>(){});
		return errors!=null?errors:new ArrayList<ErrorHolder>();
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		if(checkError(response)){
			List<ErrorHolder> errors = getErrorResponse(response);
			throw new SystemException(errors);
		}else{
			super.handleError(response);
		}
	}

}
