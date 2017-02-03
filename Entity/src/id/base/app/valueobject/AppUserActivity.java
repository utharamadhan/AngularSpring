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
@Table(name = "APP_USER_ACTIVITY")
@Inheritance(strategy=InheritanceType.JOINED)
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="appUserActivityJid", scope=AppUserActivity.class)
public class AppUserActivity implements Serializable {

	private static final long serialVersionUID = -7962229832138245287L;
	
	@Id
	@Column(name = "PK_APP_USER_ACTIVITY", unique = true ,nullable = false)
	private Long pkAppUserActivity;

	@ManyToOne(cascade={CascadeType.DETACH})
	@JoinColumn(name="FK_APP_USER")
	private AppUser appUser;
	
	@Column(name = "ACTIVITY_NAME")
	private String activityName;
	
	@Column(name="CREATED_BY", nullable=true, insertable=true, updatable=false)
	private String createdBy;
	
	@Column(name="CREATION_TIME", nullable=true, insertable=true, updatable=false)
	private Date creationTime;

	public Long getPkAppUserActivity() {
		return pkAppUserActivity;
	}

	public void setPkAppUserActivity(Long pkAppUserActivity) {
		this.pkAppUserActivity = pkAppUserActivity;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
}