package id.base.app.valueobject;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RUNTIME_USER_LOGIN")
public class RuntimeUserLogin implements Serializable {

	private static final long serialVersionUID = -7619739878194444047L;
	
	public static final String USER_NAME = "userName";
	public static final String NAME = "name";
	public static final String ACCESS_INFO = "accessInfo";
	
	@Id
	@Column(name = "USER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	private Long userId;
	@Column(name = "ACCESS_INFO")
	private String accessInfo;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "USER_NAME")
	private String userName;
	@Column(name = "NAME")
	private String name;
	@Column(name="USER_TYPE")
    private Integer userType;
	@Column(name="SESSION_TYPE")
    private Integer sessionType;
	@Column(name = "LOGIN_TIME")
	private Date loginTime;
	@Column(name = "REMOTE_ADDRESS")
	private String remoteAddress;
	@Column(name = "COMPANY_LIST")
	private String companyList;
	@Column(name = "COMPANY_SELECTED")
	private Long companySelected;
	
	/** default constructor */
	public RuntimeUserLogin() {
	}

	/** full constructor */
	public RuntimeUserLogin(String userName, String name, String remoteAddress, Date loginTime) {
		this.userName = userName;
		this.name = name;
		this.remoteAddress = remoteAddress;
		this.loginTime = loginTime;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAccessInfo() {
		return accessInfo;
	}
	public void setAccessInfo(String accessInfo) {
		this.accessInfo = accessInfo;
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

	public String getRemoteAddress() {
		return this.remoteAddress;
	}
	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public Date getLoginTime() {
		return this.loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
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