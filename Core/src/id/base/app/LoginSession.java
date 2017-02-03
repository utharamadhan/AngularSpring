package id.base.app;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginSession implements Serializable  {

	private static final long serialVersionUID = 4468155798623949421L;
	private Long pkAppUser;
	private String email;
	private String userName;
	private String name;
	private Integer userType = 1;
	private Integer sessionType = 1;
	private String sessionId ;
	private Boolean flagSuperUser;
	private List<Long> userRoles;
	private Map<String, Boolean> permissions;
	private String companyList;
	private Long companySelected;
	
	public String getSessionId() {
		return sessionId;
	}
	
	public Integer getSessionType() {
		if(sessionType==null){
			sessionType = userType;
		}
		return sessionType;
	}
	
	public void setSessionType(Integer sessionType) {
		this.sessionType = sessionType;
	}
	
	public void addPermission(Integer pkAppFunction, Boolean isTransactional) {
		if (permissions == null) {
			permissions = new HashMap<String, Boolean>();
		}
		
		permissions.put(pkAppFunction.toString(), isTransactional);
	}
	public void setPermission(Map<String,Boolean> permissions){
		this.permissions = permissions;
	}
	public boolean hasPermission(Integer menuId) {
		if(permissions.containsKey(menuId.toString())){
			return true;
		}else{
			return false;
		}
	}
	public List<Long> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(List<Long> roles) {
		this.userRoles = roles;
	}
	public boolean hasRole(Long role) {
		return userRoles.contains(role);
	}
	
	public Map<String, Boolean> getPermissions() {
		return permissions;
	}

	public Long getPkAppUser() {
		return pkAppUser;
	}

	public void setPkAppUser(Long pkAppUser) {
		this.pkAppUser = pkAppUser;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Boolean getFlagSuperUser() {
		return flagSuperUser;
	}

	public void setFlagSuperUser(Boolean flagSuperUser) {
		this.flagSuperUser = flagSuperUser;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public void setPermissions(Map<String, Boolean> permissions) {
		this.permissions = permissions;
	}

	public String getCompanyList() {
		return companyList;
	}

	public void setCompanyList(String companyList) {
		this.companyList = companyList;
	}

	public Long getCompanySelected() {
		return companySelected;
	}
	public void setCompanySelected(Long companySelected) {
		this.companySelected = companySelected;
	}
}