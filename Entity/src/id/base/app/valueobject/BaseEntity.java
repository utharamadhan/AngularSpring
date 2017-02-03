package id.base.app.valueobject;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@MappedSuperclass
@JsonIgnoreProperties(allowGetters=false,allowSetters=false,value={"createdBy","modifiedBy","creationTime","modificationTime"})
public class BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 7584899651889367138L;

	@Column(name="CREATED_BY", nullable=true, insertable=true, updatable=false)
	protected String createdBy;
	
	@Column(name="MODIFIED_BY", nullable=true, insertable=true, updatable=true)
	protected String modifiedBy;

	@Column(name="CREATION_TIME", nullable=true, insertable=true, updatable=false)
	protected Date creationTime;
	
	@Column(name="MODIFICATION_TIME", nullable=true, insertable=true, updatable=true)
	protected Date modificationTime;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}
}
