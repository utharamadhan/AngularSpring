package id.base.app.valueobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "APP_ROLE_FUNCTION")
public class AppRoleFunction  {

	@Id
	@SequenceGenerator(name="app_role_function_pk_app_role_function_seq", sequenceName="app_role_function_pk_app_role_function_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="app_role_function_pk_app_role_function_seq")
	@Column(name = "PK_APP_ROLE_FUNCTION", unique = true, nullable = false)
	private Long pkAppRoleFunction;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_APP_FUNCTION", nullable = false)
	private AppFunction appFunction;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_APP_ROLE", nullable = true)
	private AppRole appRole;

	// Constructors

	/** default constructor */
	public AppRoleFunction() {
	}

	/** full constructor */
	public AppRoleFunction(AppFunction appFunction, AppRole appRole) {
		this.appFunction = appFunction;
		this.appRole = appRole;
	}

	public Long getPkAppRoleFunction() {
		return this.pkAppRoleFunction;
	}

	public void setPkAppRoleFunction(Long pkAppRoleFunction) {
		this.pkAppRoleFunction = pkAppRoleFunction;
	}

	public AppFunction getAppFunction() {
		return this.appFunction;
	}

	public void setAppFunction(AppFunction appFunction) {
		this.appFunction = appFunction;
	}

	public AppRole getAppRole() {
		return this.appRole;
	}

	public void setAppRole(AppRole appRole) {
		this.appRole = appRole;
	}
	
	public boolean equals(Object obj) {
        if (obj instanceof AppRoleFunction) {
            return EqualsBuilder.reflectionEquals(obj, this);
        }
        return false;
    }
	
	 public int hashCode() {
		 return HashCodeBuilder.reflectionHashCode(this);
	 }
	 
	 public String toString(){
		 return ToStringBuilder.reflectionToString(this);
	}

}