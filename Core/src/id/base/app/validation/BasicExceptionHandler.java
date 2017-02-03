package id.base.app.validation;

import id.base.app.exception.ErrorHolder;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BasicExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static Logger LOGGER = LoggerFactory.getLogger(BasicExceptionHandler.class);
	
	@ExceptionHandler({ InvalidRequestException.class })
    protected ResponseEntity<Object> handleInvalidRequest(RuntimeException e, WebRequest request) throws JsonProcessingException {
        InvalidRequestException ire = (InvalidRequestException) e;
        List<ErrorHolder> errorHolders = new LinkedList<ErrorHolder>();

        List<FieldError> fieldErrors = ire.getErrors().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
        	LOGGER.error(fieldError.getDefaultMessage());
            errorHolders.add(new ErrorHolder(fieldError.getDefaultMessage()));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(e, errorHolders, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
	}
}
