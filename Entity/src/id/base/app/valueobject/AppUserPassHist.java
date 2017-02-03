package id.base.app.valueobject;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "APP_USER_PASS_HIST")
@Inheritance(strategy=InheritanceType.JOINED)
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="appUserPassHistJid", scope=AppUserPassHist.class)
public class AppUserPassHist implements Serializable {

	private static final long serialVersionUID = -2890202487548409799L;
	
	@Id
	@Column(name = "PK_APP_USER_PASS_HIST", unique = true ,nullable = false)
	private Long pkAppUserPassHist;
	
	@ManyToOne(cascade={CascadeType.DETACH})
	@JoinColumn(name="FK_APP_USER")
	private AppUser appUser;
	
	@Column(name = "PASS_VALUE")
	private String passValue;
	
	@Column(name="CREATION_TIME", nullable=true, insertable=true, updatable=false)
	private Date creationTime;

	public Long getPkAppUserPassHist() {
		return pkAppUserPassHist;
	}

	public void setPkAppUserPassHist(Long pkAppUserPassHist) {
		this.pkAppUserPassHist = pkAppUserPassHist;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public String getPassValue() {
		return passValue;
	}

	public void setPassValue(String passValue) {
		this.passValue = passValue;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
}