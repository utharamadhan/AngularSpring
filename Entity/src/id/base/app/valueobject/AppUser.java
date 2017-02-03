package id.base.app.valueobject;

import id.base.app.valueobject.party.Party;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "APP_USER")
public class AppUser extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -8213161281367083669L;
	
	public AppUser() {}
	
	public static AppUser getInstance() {
		return new AppUser();
	}
	
	public AppUser(AppUser appUser) {	
	    setUserType(appUser.getUserType());
	}
	
	public static final String PK_APP_USER = "pkAppUser" ;
	public static final String USER_TYPE = "userType" ;
	public static final String USER_NAME = "userName" ;
	public static final String EMAIL = "email" ;
	public static final String APP_ROLE = "appRole" ;
	public static final String APP_ROLE_NAME = "appRole.name" ;
	public static final String APP_ROLES = "appRoles";
	public static final String STATUS = "status";
	public static final String SUPER_USER="superUser";
	public static final String PASSWORD = "password";
	public static final String PASSWORD_CONFIRMATION = "passwordConfirmation";
	public static final String PARTY = "party";
	public static final String PARTY_NAME = PARTY + ".name";
	public static final String PARTY_CONTACTS = PARTY + ".partyContacts";
	public static final String PARTY_CONTACTS_CONTACT = PARTY_CONTACTS + "[%d].contact";
	public static final String ACTIVATION_CODE = "activationCode";
	public static final String ACTIVATION_METHOD = "activationMethod";
	public static final String LOCK = "lock";
	
	public static final String USER_FRONTEND = "userFrontEnd";
	
	public static final String[] MAINTENANCE_LIST_FIELDS = {
		AppUser.PK_APP_USER , AppUser.USER_TYPE 
	};

	@Id
	@SequenceGenerator(name="APP_USER_PK_APP_USER_SEQ", sequenceName="APP_USER_PK_APP_USER_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="APP_USER_PK_APP_USER_SEQ")
	@Column(name = "PK_APP_USER", unique = true ,nullable = false)
	private Long pkAppUser;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_PARTY")
	private Party party;
	
	@Column(name="USER_TYPE")
    private Integer userType;
	
	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name="EMAIL")
    private String email;
	
    @Column(name="PASSWORD")
	private String password;
    
    @Transient
    private String passwordConfirmation;
    
    @Column(name="RANDOM_KEY")
	private String randomKey;
    
    @Column(name="STATUS")
    private Integer status;
    
    @Column(name="LOGIN_FAILED")
    private Integer loginFailed ;
    
    @Column(name="LAST_ACTION")
	private String lastAction;
    
    @Column(name="LAST_LOGIN_ACCESS")
	private String lastLoginAccess;
    
    @Column(name="LAST_LOGIN_DEVICE")
	private String lastLoginDevice;
    
    @Column(name="LAST_LOGIN_DATE")
	private Date lastLoginDate;
    
    @Column(name = "IS_LOCK", precision = 1, scale = 0)
	private Boolean lock;
    
    @Column(name = "IS_SUPER_USER", precision = 1, scale = 0)
    private Boolean superUser;
    
    @Column(name = "ACTIVATION_CODE")
    private String activationCode;
    
    @Column(name = "INITIAL_WIZARD_STEP")
    private Integer initialWizardStep;
    
    @Column(name = "ACTIVATION_METHOD")
    private Integer activationMethod;
    
    @Transient
    private String roleFlag;
    
    @ManyToMany(
			fetch = FetchType.LAZY
			)
	@JoinTable(name="APP_USER_ROLE",
			joinColumns=@JoinColumn(name="FK_APP_USER"),
			inverseJoinColumns=@JoinColumn(name="FK_APP_ROLE"))
	private List<AppRole> appRoles;
	
	public Long getPkAppUser() {
		return pkAppUser;
	}

	public void setPkAppUser(Long pkAppUser) {
		this.pkAppUser = pkAppUser;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}
	
	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getRandomKey() {
		return randomKey;
	}
	
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public void setRandomKey(String randomKey) {
		this.randomKey = randomKey;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getLoginFailed() {
		return loginFailed;
	}
	public void setLoginFailed(Integer loginFailed) {
		this.loginFailed = loginFailed;
	}

	public String getLastAction() {
		return lastAction;
	}
	public void setLastAction(String lastAction) {
		this.lastAction = lastAction;
	}

	public String getLastLoginAccess() {
		return lastLoginAccess;
	}
	public void setLastLoginAccess(String lastLoginAccess) {
		this.lastLoginAccess = lastLoginAccess;
	}

	public String getLastLoginDevice() {
		return lastLoginDevice;
	}
	public void setLastLoginDevice(String lastLoginDevice) {
		this.lastLoginDevice = lastLoginDevice;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	public Boolean getLock() {
		return lock;
	}
	public void setLock(Boolean lock) {
		this.lock = lock;
	}

	public int hashCode() {
	        return HashCodeBuilder.reflectionHashCode(this);
	}
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	
	public List<AppRole> getAppRoles() {
		return appRoles;
	}
	public void setAppRoles(List<AppRole> appRoles) {
		this.appRoles = appRoles;
	}

	public Boolean getSuperUser() {
		return superUser;
	}
	public void setSuperUser(Boolean superUser) {
		this.superUser = superUser;
	}

	public String getRoleFlag() {
		return roleFlag;
	}
	public void setRoleFlag(String roleFlag) {
		this.roleFlag = roleFlag;
	}

	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public Integer getInitialWizardStep() {
		return initialWizardStep;
	}
	public void setInitialWizardStep(Integer initialWizardStep) {
		this.initialWizardStep = initialWizardStep;
	}

	public Integer getActivationMethod() {
		return activationMethod;
	}
	public void setActivationMethod(Integer activationMethod) {
		this.activationMethod = activationMethod;
	}
	
}