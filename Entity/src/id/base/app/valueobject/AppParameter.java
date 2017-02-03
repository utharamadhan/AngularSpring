package id.base.app.valueobject;

import id.base.app.SystemConstant;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "APP_PARAMETER")
public class AppParameter extends BaseEntity {

	private static final long serialVersionUID = 5933206321367656004L;
	
	public static AppParameter getInstance() {
		return new AppParameter();
	}
	
	public static AppParameter getInstance(String name, String value) {
		AppParameter obj = new AppParameter();
			obj.setName(name);
			obj.setValue(value);
		return obj;
	}

	public static final String IS_VIEWABLE	= "isViewable";
	public static final String NAME 		= "name";
	public static final String VALUE 		= "value";
	public static final String DATA_TYPE	= "datatype";
	
	private static final String INTEGER = "INTEGER";
	private static final String LONG    = "LONG";
	private static final String DOUBLE  = "DOUBLE";
	private static final String STRING  = "STRING";
	private static final String DATE    = "DATE";
	private static final String BOOLEAN = "BOOLEAN";
	private static final String CRON	= "CRON";
	
	public static final List<Lookup> DATA_TYPE_LOOKUP = new ArrayList<>();
	
	static {
		DATA_TYPE_LOOKUP.add(Lookup.getInstanceShort(SystemConstant.FIELD_TYPE_INT.toString(), INTEGER));
		DATA_TYPE_LOOKUP.add(Lookup.getInstanceShort(SystemConstant.FIELD_TYPE_LONG.toString(), LONG));
		DATA_TYPE_LOOKUP.add(Lookup.getInstanceShort(SystemConstant.FIELD_TYPE_DOUBLE.toString(), DOUBLE));
		DATA_TYPE_LOOKUP.add(Lookup.getInstanceShort(SystemConstant.FIELD_TYPE_STRING.toString(), STRING));
		DATA_TYPE_LOOKUP.add(Lookup.getInstanceShort(SystemConstant.FIELD_TYPE_DATE.toString(), DATE));
		DATA_TYPE_LOOKUP.add(Lookup.getInstanceShort(SystemConstant.FIELD_TYPE_BOOLEAN.toString(), BOOLEAN));
	}

	@Id
	@GeneratedValue
	@Column(name = "PK_APP_PARAMETER", unique = true, nullable = false)
	@NotNull(groups=UpdateEntity.class, message="{error.message.update.not.allowed}")
	@Null(groups=CreateEntity.class, message="{error.message.create.not.allowed}")
	private Long pkAppParameter;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "VALUE")
	private String value;
	
	@Column(name = "DESCR")
	private String descr;
	
	@Column(name = "IS_VIEWABLE")
	private Boolean isViewable;
	
	@Column(name = "DATATYPE")
	private Integer datatype;
	
	@Transient
	private String dataTypeDescr;
	

	public Long getPkAppParameter() {
		return pkAppParameter;
	}
	public void setPkAppParameter(Long pkAppParameter) {
		this.pkAppParameter = pkAppParameter;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getDescr() {
		return this.descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Boolean getIsViewable() {
		return this.isViewable;
	}
	public void setIsViewable(Boolean isViewable) {
		this.isViewable = isViewable;
	}

	public Integer getDatatype() {
		return datatype;
	}
	public void setDatatype(Integer datatype) {
		this.datatype = datatype;
	}

	@Transient
	public String getDataTypeDescr() {
		if(datatype!=null){
			if(datatype.equals(SystemConstant.FIELD_TYPE_INT)) {
				return INTEGER;
			}else if(datatype.equals(SystemConstant.FIELD_TYPE_LONG)) {
				return LONG;
			}else if(datatype.equals(SystemConstant.FIELD_TYPE_DOUBLE)) {
				return DOUBLE;
			}else if(datatype.equals(SystemConstant.FIELD_TYPE_STRING)) {
				return STRING;
			}else if(datatype.equals(SystemConstant.FIELD_TYPE_DATE)) {
				return DATE;
			}else if(datatype.equals(SystemConstant.FIELD_TYPE_BOOLEAN)) {
				return BOOLEAN;
			}else if(datatype.equals(SystemConstant.FIELD_TYPE_CRON)) {
				return CRON;
			}
		}
        return null;
    }
	
}