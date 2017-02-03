package id.base.app.exception;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorHolder implements Serializable{

	private static final long serialVersionUID = -176523424639609289L;
	private String validatedField;
	private String error;
	private Object[] parameter;
	
	public ErrorHolder() {
	}
	
	public ErrorHolder(String validatedField, String error) {
		this.validatedField = validatedField;
		this.error = error;
	}
	
	public ErrorHolder(String error) {
		this.error = error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	public ErrorHolder(String error, Object...parameter) {
		this.error = error;
		this.parameter = parameter;
	}
	
	public String getError() {
		return error;
	}

	
	public Object[] getParameter() {
		return parameter;
	}

	public void setParameter(Object[] parameter) {
		this.parameter = parameter;
	}
	
	public String getValidatedField() {
		return validatedField;
	}
	
	public void setValidatedField(String validatedField) {
		this.validatedField = validatedField;
	}
	
}
