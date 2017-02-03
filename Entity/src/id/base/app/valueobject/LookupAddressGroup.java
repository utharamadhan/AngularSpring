package id.base.app.valueobject;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LOOKUP_ADDRESS_GROUP")
public class LookupAddressGroup implements Serializable {

	private static final long serialVersionUID = 3727125658465430738L;
	
	public static final String NAME = "name";
	public static final String IS_UPDATABLE = "updatable";

	@Id
	@Column(name = "LOOKUP_ADDRESS_GROUP", unique = true)
	private String name;
	
	@Column(name = "IS_UPDATABLE")
	private boolean updatable;
	
	@Column(name = "IS_VIEWABLE")
	private boolean viewable;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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