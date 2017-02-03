package id.base.app.valueobject;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "app_user_password_hist")
public class PasswordHistory implements Serializable{
	
	private static final long serialVersionUID = -6967842452502439810L;
	private Long pkAppUserPwdHist;
	private Long fkAppUser;
	private Date recordedDate;
	private String passwordValue;
	
	public void setPkAppUserPwdHist(Long pkAppUserPwdHist) {
		this.pkAppUserPwdHist = pkAppUserPwdHist;
	}
	@Id
	@SequenceGenerator(name="seq_app_user_pwd_hist", allocationSize=1, sequenceName="seq_app_user_pwd_hist")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_app_user_pwd_hist")
	@Column(name = "pk_app_user_pwd_hist", unique = true ,nullable = false, precision = 10, scale = 0)
	public Long getPkAppUserPwdHist() {
		return this.pkAppUserPwdHist;
	}
	
	public void setFkAppUser(Long fkAppUser) {
		this.fkAppUser = fkAppUser;
	}
	@Column(name = "fk_app_user", nullable = false, length = 20)
	public Long getFkAppUser() {
		return this.fkAppUser;
	}
	public void setRecordedDate(Date recordedDate) {
		this.recordedDate = recordedDate;
	}
	
	@Column(name="recorded_date")
	public Date getRecordedDate() {
		return this.recordedDate;
	}
	public void setPasswordValue(String passwordValue) {
		this.passwordValue = passwordValue;
	}
	
	@Column(name="password_value")
	public String getPasswordValue() {
		return this.passwordValue;
	}
}
