package id.base.app.valueobject.monitoring;

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
@Table(name = "JOB_MONITOR")
public class JobMonitor implements Serializable {
	public static final String STATUS_OK = "OK";
	public static final String STATUS_ERROR = "ERROR";
	
	@Id
	@SequenceGenerator(name="JOB_MONITOR_PK_JOB_MONITOR_SEQ", sequenceName="JOB_MONITOR_PK_JOB_MONITOR_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="JOB_MONITOR_PK_JOB_MONITOR_SEQ")
	@Column(name = "PK_JOB_MONITOR", unique = true, nullable = false, precision = 22, scale = 0)
	private Long pkJobMonitor;
	
	@Column(name="START_TIME")
	private Date startTime;
	
	@Column(name="END_TIME")
	private Date endTime;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="REMARK")
	private String remark;
	
	@Column(name="JOB_NAME")
	private String name;
	
	@Column(name="DURATION")
	private long duration;

	public Long getPkJobMonitor() {
		return pkJobMonitor;
	}

	public void setPkJobMonitor(Long pkJobMonitor) {
		this.pkJobMonitor = pkJobMonitor;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
	
}
