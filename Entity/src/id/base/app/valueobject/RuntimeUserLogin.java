package id.base.app.valueobject;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RUNTIME_USER_LOGIN")
public class RuntimeUserLogin implements Serializable {

	private static final long serialVersionUID = -7619739878194444047L;
	
	public static final String USER_ID = "userId";
	public static final String EMAIL = "email";
	
	public static RuntimeUserLogin newInstance(Long userId, String email, String remoteAddress) {
		RuntimeUserLogin obj = new RuntimeUserLogin();
			obj.setUserId(userId);
			obj.setEmail(email);
			obj.setRemoteAddress(remoteAddress);
			obj.setLoginTime(Calendar.getInstance().getTime());
		return obj;
	}
	
	@Id
	@Column(name = "USER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	private Long userId;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "LOGIN_TIME")
	private Date loginTime;
	@Column(name = "REMOTE_ADDRESS")
	private String remoteAddress;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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

}