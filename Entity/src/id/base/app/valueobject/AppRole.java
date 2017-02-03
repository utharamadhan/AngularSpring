package id.base.app.valueobject;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "APP_ROLE")
public class AppRole extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 351069103170081973L;
	
	public static final String CODE = "code";
	public static final String TYPE = "type";
	public static final String NAME = "name";
	public static final String PK_APP_ROLE = "pkAppRole";
	public static final String USERS = "users";
	public static final String USERS_ID = "users.pkAppUser";
	
	public static final String OP_EDIT = "edit";
	public static final String OP_ADD = "add";
	public static final String OP_REMOVE = "remove";
	
	@Id
	@SequenceGenerator(name="app_role_pk_app_role_seq", sequenceName="app_role_pk_app_role_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="app_role_pk_app_role_seq")
	@Column(name = "PK_APP_ROLE", unique = true, nullable = false, precision = 10, scale = 0)
	@NotNull(groups=UpdateEntity.class, message="{error.message.update.not.allowed}")
	@Null(groups=CreateEntity.class, message="{error.message.create.not.allowed}")
	private Long pkAppRole;
	
	@Column(name = "CODE", length = 10)
	private String code;
	
	@Column(name = "NAME", length = 200)
	private String name;
	
	@Column(name = "USER_TYPE", length = 1)
	private Integer type;
	
	@JsonIgnore
	@ManyToMany(mappedBy="appRoles")
	private List<AppUser> users;
	
	@Transient
	private List<AppFunction> functionList;
	@Transient
	private int[] pkAppFunctionList;	

	/** default constructor */
	public AppRole() {
	}

	public Long getPkAppRole() {
		return this.pkAppRole;
	}

	public void setPkAppRole(Long pkAppRole) {
		this.pkAppRole = pkAppRole;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@JsonIgnore
	public List<AppUser> getUsers() {
		return users;
	}

	@JsonIgnore
	public void setUsers(List<AppUser> users) {
		this.users = users;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null 
				|| !(obj instanceof AppRole)) {
			return false;
		}
		
		if (getPkAppRole() == null) {
			return super.equals(obj);
		}
		
		return getPkAppRole().equals(((AppRole) obj).getPkAppRole());
	}

	public List<AppFunction> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<AppFunction> functionList) {
		this.functionList = functionList;
	}

	public int[] getPkAppFunctionList() {
		return pkAppFunctionList;
	}

	public void setPkAppFunctionList(int[] pkAppFunctionList) {
		this.pkAppFunctionList = pkAppFunctionList;
	}

	
}