package id.base.app.validation;

import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;

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
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SystemExceptionHandler extends ResponseEntityExceptionHandler {

	private static Logger LOGGER = LoggerFactory
			.getLogger(SystemExceptionHandler.class);

	@Autowired
	private ResourceBundleMessageSource messageSource;

	@ExceptionHandler({ SystemException.class })
	protected ResponseEntity<Object> handleInvalidRequest(RuntimeException e,
			WebRequest request) throws JsonProcessingException {
		SystemException se = (SystemException) e;
		List<ErrorHolder> errorHolders = new LinkedList<ErrorHolder>();

		List<ErrorHolder> rawErrors = se.getErrors();
		for (ErrorHolder error : rawErrors) {
			LOGGER.error(error.getError());
			errorHolders.add(new ErrorHolder(error.getValidatedField(), messageSource.getMessage(error.getError(), error.getParameter(), Locale.ENGLISH)));
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(e, errorHolders, headers,
				HttpStatus.BAD_REQUEST, request);
	}
}
