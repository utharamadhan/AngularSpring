package id.base.app.valueobject.email;

import id.base.app.valueobject.BaseEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "EMAIL_TEMPLATE")
public class EmailTemplate extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 213138958498099156L;

	public EmailTemplate() {}
	
	public static final String ID = "pkEmailTemplate";
	public static final String CODE = "code";
	public static final String TEMPLATE = "template";
	
	@Id
	@SequenceGenerator(name="EMAIL_TEMPLATE_PK_EMAIL_TEMPLATE_SEQ", sequenceName="EMAIL_TEMPLATE_PK_EMAIL_TEMPLATE_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="EMAIL_TEMPLATE_PK_EMAIL_TEMPLATE_SEQ")
	@Column(name = "pk_EMAIL_TEMPLATE", unique = true ,nullable = false)
	private Long pkEmailTemplate;
	
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "TEMPLATE")
	private String template;
	
	@Column(name = "STATUS")
	private Integer status;

	public Long getPkEmailTemplate() {
		return pkEmailTemplate;
	}
	public void setPkEmailTemplate(Long pkEmailTemplate) {
		this.pkEmailTemplate = pkEmailTemplate;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
