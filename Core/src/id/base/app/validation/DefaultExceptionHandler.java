package id.base.app.validation;

import id.base.app.exception.ErrorHolder;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

	private static Logger LOGGER = LoggerFactory
			.getLogger(DefaultExceptionHandler.class);
	
	@Autowired
	private ResourceBundleMessageSource messageSource;

	@ExceptionHandler({ RuntimeException.class })
	protected ResponseEntity<Object> handleInvalidRequest(RuntimeException e,
			WebRequest request) throws JsonProcessingException {
		List<ErrorHolder> errorHolders = new LinkedList<ErrorHolder>();
		LOGGER.error("RuntimeError {}", e);
		errorHolders.add(new ErrorHolder(messageSource.getMessage("error.message.default",null,Locale.ENGLISH)));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(e, errorHolders, headers,
				HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
}
