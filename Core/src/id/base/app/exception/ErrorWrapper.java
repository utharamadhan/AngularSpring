package id.base.app.exception;

import java.io.Serializable;
import java.util.List;


public class ErrorWrapper implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4796292139916573239L;
	
	public static ErrorWrapper getInstance(List<ErrorHolder> errors) {
		ErrorWrapper wrp = new ErrorWrapper();
			wrp.setErrors(errors);
		return wrp;
	}
	
	private List<ErrorHolder> errors;
	
	public void setErrors(List<ErrorHolder> errors) {
		this.errors = errors;
	}
	public List<ErrorHolder> getErrors() {
		return errors;
	}
	
}
