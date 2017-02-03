package id.base.app.valueobject;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "APP_FUNCTION")
public class AppFunction implements Serializable  {
	
	private static final long serialVersionUID = 3925973864779138451L;
	
	public static final String FK_APP_FUNCTION_PARENT= "fkAppFunctionParent";
	public static final String USER_TYPE= "userType";
	public static final String PK_APP_FUNCTION= "pkAppFunction";
	public static final String ORDER_NO = "orderNo";
	public static final String IS_ACTIVE = "isActive";
	
	public static AppFunction getInstance(Long pkAppFunction, String name, String accessPage, Long fkParent, Boolean isActive) {
		AppFunction af = new AppFunction();
			af.setPkAppFunction(pkAppFunction);
			af.setName(name);
			af.setAccessPage(accessPage);
			af.setFkAppFunctionParent(fkParent);
			af.setIsActive(isActive);
		return af;
	}
	
	public static Boolean isMenuHasChild(Long pkAppFunction, List<AppFunction> appFunctions) {
		if(appFunctions != null && appFunctions.size() > 0) {
			for(AppFunction af : appFunctions) {
				if(af.getFkAppFunctionParent() != null && pkAppFunction.equals(af.getFkAppFunctionParent())) {
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}
	
	/** default constructor */
	public AppFunction() {
	}

	/** full constructor */
	public AppFunction(String name, String descr,
			String accessPage, Boolean isActive) {
		this.name = name;
		this.descr = descr;
		this.accessPage = accessPage;
		this.isActive = isActive;
	}
	
	@Id
	@GeneratedValue
	@Column(name = "PK_APP_FUNCTION", unique = true, nullable = false, precision = 22, scale = 0)
	private Long pkAppFunction;
	
	@Column(name = "FK_APP_FUNCTION_PARENT")
	private Long fkAppFunctionParent;
	
	@Column(name = "NAME", length = 100)
	private String name;
	
	@Column(name = "DESCR", length = 200)
	private String descr;
	
	@Column(name = "ACCESS_PAGE", length = 200)
	private String accessPage;
	
	@Column(name = "IS_ACTIVE")
	private Boolean isActive;
	
	@Column(name="USER_TYPE", length=1)
	private Integer userType;
	
	@Column(name = "ORDER_NO")
	private Long orderNo;
	
	@JsonIgnore
	@OneToMany(mappedBy="appFunction",fetch=FetchType.LAZY)
	private List<AppRoleFunction> appRoleFunctions;
	
	@Transient
	private List<AppFunction> childList;
	@Transient
	private Boolean isSelected;
	
	// Property accessors
	public String getName() {
		return this.name;
	}

	public Long getPkAppFunction() {
		return pkAppFunction;
	}

	public void setPkAppFunction(Long pkAppFunction) {
		this.pkAppFunction = pkAppFunction;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getAccessPage() {
		return this.accessPage;
	}

	public void setAccessPage(String accessPage) {
		this.accessPage = accessPage;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getUserType() {
		return userType;
	}
	
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
	public Long getFkAppFunctionParent() {
		return fkAppFunctionParent;
	}

	public void setFkAppFunctionParent(Long fkAppFunctionParent) {
		this.fkAppFunctionParent = fkAppFunctionParent;
	}
	
	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	
	public List<AppRoleFunction> getAppRoleFunctions() {
		return appRoleFunctions;
	}

	public void setAppRoleFunctions(List<AppRoleFunction> appRoleFunctions) {
		this.appRoleFunctions = appRoleFunctions;
	}
	
	public List<AppFunction> getChildList() {
		return childList;
	}

	public void setChildList(List<AppFunction> childList) {
		this.childList = childList;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
}