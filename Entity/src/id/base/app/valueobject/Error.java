package id.base.app.valueobject;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ERROR")
public class Error implements Serializable  {

	private static final long serialVersionUID = 4142566092172550277L;
	
	@Id
	@Column(name = "PK_ERROR", unique = true ,nullable = false)
	private Long pkError;
	
	@Column(name="ERROR_CODE")
	private String errorCode;
	
	@Column(name="error_descr")
	private String errorDescription;
	
	@Column(name="CREATED_BY", nullable=true, insertable=true, updatable=false)
	private String createdBy;

	@Column(name="CREATION_TIME", nullable=true, insertable=true, updatable=false)
	private Date creationTime;

	public Long getPkError() {
		return pkError;
	}

	public void setPkError(Long pkError) {
		this.pkError = pkError;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
}