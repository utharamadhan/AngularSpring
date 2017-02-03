package id.base.app.valueobject;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LOOKUP_GROUP")
public class LookupGroup implements Serializable {

	private static final long serialVersionUID = 8191535273601509152L;
	
	public static final String NAME = "name";
	public static final String IS_UPDATABLE = "updatable";
	public static final String IS_VIEWABLE = "viewable";

	@Id
	@Column(name = "LOOKUP_GROUP", unique = true)
	private String name;
	
	@Column(name = "GROUP_DESCR")
	private String description;
	
	@Column(name = "IS_UPDATEABLE")
	private boolean updatable;
	
	@Column(name = "IS_VIEWABLE")
	private boolean viewable;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isUpdatable() {
		return updatable;
	}

	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}

	public boolean isViewable() {
		return viewable;
	}

	public void setViewable(boolean viewable) {
		this.viewable = viewable;
	}

}