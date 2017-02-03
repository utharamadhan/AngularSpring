package id.base.app.valueobject;

import id.base.app.SystemConstant;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.softtech.kismiss.property.Detail;
import com.softtech.kismiss.property.Header;
import com.softtech.kismiss.property.Kismiss;
import com.softtech.kismiss.property.Property;


@Entity
@Table(name = "LOG_AUDIT_TRAIL")
@Kismiss(name = "AuditTrailLog", isIgnorePagination=true)
@Header(columnHeaderHeight=25, isColumnHeaderBold=true, columnHeaderColor="#BDBBBC", lineWidth=0.5)
@Detail(lineWidth=0.5)
public class LogAuditTrail  {

	
	private static final long serialVersionUID = 1L;
	
	private Long pkLogAuditTrail;
	
	private Date accessTime;
	private String logGroup;
	private Integer code;
	private String descr;
	private String userName;
	private String status;
	private String clientHost;
	private Integer subCode;

	// Constructors
	public static final String LOG_AUDIT_TRAIL_CODE="code";
	/** default constructor */
	public LogAuditTrail() {
	}
	
	public static final String ACCESS_TIME = "accessTime";
	public static final String LOG_GROUP = "logGroup";
	public static final String DESCRIPTION = "descr";
	public static final String USERNAME = "userName";
	public static final String STATUS = "status";
	public static final String CLIENT_HOST = "clientHost";


	/** full constructor */
	public LogAuditTrail(Date accessTime,
			String logGroup, Integer code, String descr, String userName,
			String status, String clientHost) {
		this.accessTime = accessTime;
		this.logGroup = logGroup;
		this.code = code;
		this.descr = descr;
		this.userName = userName;
		this.status = status;
		this.clientHost = clientHost;
	}
	
	public LogAuditTrail(Date accessTime,
			String logGroup, Integer code, String descr, String userName,
			String status, String clientHost, Integer subCode) {
		this.accessTime = accessTime;
		this.logGroup = logGroup;
		this.code = code;
		this.descr = descr;
		this.userName = userName;
		this.status = status;
		this.clientHost = clientHost;
		this.subCode = subCode;
	}

	// Property accessors
	@Id
	@SequenceGenerator(name="SEQ_AUDIT_TRAIL", allocationSize=1, sequenceName="SEQ_AUDIT_TRAIL")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_AUDIT_TRAIL")
	@Column(name = "PK_LOG_AUDIT_TRAIL", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getPkLogAuditTrail() {
		return this.pkLogAuditTrail;
	}

	public void setPkLogAuditTrail(Long pkLogAuditTrail) {
		this.pkLogAuditTrail = pkLogAuditTrail;
	}

	@Column(name = "ACCESS_TIME", length = 7)
	@Property(name = "Date", width = 100,  position = 3, pattern=SystemConstant.SYSTEM_DATE_TIME_SECOND_MASK )
	public Date getAccessTime() {
		return this.accessTime;
	}

	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}

	@Column(name = "LOG_GROUP", length = 50)
	public String getLogGroup() {
		return this.logGroup;
	}

	public void setLogGroup(String logGroup) {
		this.logGroup = logGroup;
	}

	@Column(name = "CODE", precision = 22, scale = 0)
	@Property(name = "Code", width = 100,  position = 1)
	public Integer getCode() {
		return this.code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Column(name = "DESCR", length = 1000)
	@Property(name = "Description", width = 285,  position = 2)
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	
	
	@Column(name = "STATUS", length = 50)
	@Property(name = "Status", width = 50,  position = 4)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "CLIENT_HOST")
	public String getClientHost() {
		return this.clientHost;
	}

	public void setClientHost(String clientHost) {
		this.clientHost = clientHost;
	}

	@Column(name = "USER_NAME",length = 50)
	@Property(name = "User", width = 100,  position = 1)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "SUB_CODE")
	public Integer getSubCode() {
		return subCode;
	}

	public void setSubCode(Integer subCode) {
		this.subCode = subCode;
	}

	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

}