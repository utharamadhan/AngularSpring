package id.base.app.exception;

import java.util.LinkedList;
import java.util.List;

public class BusinessException extends RuntimeException  {
	
	private static final long serialVersionUID = -4778630082226212138L;

	private String errorCode;
	
	private List<ErrorHolder> errors = new LinkedList<ErrorHolder>(); 
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public BusinessException() {
		super();
	}
	
	public BusinessException(String errCode) {
		this.errorCode = errCode;
	}

	public BusinessException(String errCode, String[] detailErrorCodes) {
		this.errorCode = errCode;
		if(detailErrorCodes != null){
			for(int i=0; i<detailErrorCodes.length; i++){
	    		addError(ErrorHolder.newInstance(errCode, detailErrorCodes[i]));
			}
		}
	}
	
	public BusinessException(String errCode, String message) {
		super(message);
		this.errorCode = errCode;
	}
	
	
	public BusinessException(Throwable cause) {
		super(cause);
	}
	
	public BusinessException(String errCode, Throwable cause) {
		super(cause);
		this.errorCode = errCode;
	}
	
	public BusinessException(String errCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errCode;
	}
	
	public List<ErrorHolder> getErrors() {
		return errors;
	}

	public void addError(ErrorHolder error) {
		getErrors().add(error);
	}
	
	public void addError(String errCode, String errorMessage) {
		getErrors().add(ErrorHolder.newInstance(errCode, errorMessage));
	}

	public Boolean isEmpty(){
		return getErrors().size() == 0 ;
	}
}
