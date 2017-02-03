package id.base.app.valueobject;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "HELPER")
public class Helper extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 5176188761567593942L;
	
	@Id
	@SequenceGenerator(name="helper_pk_helper_seq", sequenceName="helper_pk_helper_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="helper_pk_helper_seq")
	@Column(name = "PK_HELPER", unique = true ,nullable = false)
	private Long pkHelper;
	
	@Column(name="CODE")
    private String code;
	
	@Column(name="CONTENT")
    private String content;
	
	public static final String PK_HELPER = "pkHelper" ;
	public static final String CODE = "code" ;
	public static final String CONTENT = "content" ;
	
	public Long getPkHelper() {
		return pkHelper;
	}
	public void setPkHelper(Long pkHelper) {
		this.pkHelper = pkHelper;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
