package id.base.app.valueobject;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.NotEmpty;

@Entity(name="Lookup")
@Table(name = "LOOKUP" , uniqueConstraints = @UniqueConstraint(columnNames = { "LOOKUP_GROUP", "CODE" }))
@Inheritance(strategy=InheritanceType.JOINED)
public class Lookup extends BaseEntity implements Comparable<Lookup>, Serializable{

	private static final long serialVersionUID = 8400121251901905911L;
	
	public Lookup() {}
	
	public static Lookup getInstance(String lookupGroup) {
		Lookup obj = new Lookup();
			obj.setLookupGroupString(lookupGroup);
		return obj;
	}
	
	public static Lookup getInstancePk(Long pkLookup) {
		Lookup obj = new Lookup();
			obj.setPkLookup(pkLookup);
		return obj;
	}

	public static Lookup getInstanceShort(String code,String descr) {
		Lookup obj = new Lookup();
			obj.setCode(code);
			obj.setDescr(descr);
		return obj;
	}
	
	public static Lookup getInstanceFull(LookupGroup lookupGroup, String code, String descr, Long orderNo, String usage) {
		Lookup obj = new Lookup();
			obj.setLookupGroup(lookupGroup);
			obj.setCode(code);
			obj.setDescr(descr);	
			obj.setOrderNo(orderNo);
		return obj;
	}
	
	public static final String ID 					= "pkLookup";
	public static final String LOOKUP_GROUP_STRING 	= "lookupGroupString";
	public static final String ORDER_NO_STRING 		= "orderNo";
	public static final String CODE 				= "code";
	public static final String STATUS				= "status";
	public static final String NAME					= "name";
	public static final String DESCRIPTION 			= "descr";
	public static final String USAGE				= "usage";
	
	public static final String[] MAINTENANCE_LIST_FIELDS = {
		Lookup.ID, Lookup.CODE, Lookup.NAME, Lookup.ORDER_NO_STRING, Lookup.STATUS
	};
	
	@Id
	@Column(name = "PK_LOOKUP", unique = true, nullable = false)
	@NotNull(groups=UpdateEntity.class, message="{error.message.update.not.allowed}")
	@Null(groups=CreateEntity.class, message="{error.message.create.not.allowed}")
	@SequenceGenerator(name="lookup_pk_lookup_seq", sequenceName="lookup_pk_lookup_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="lookup_pk_lookup_seq")
	private Long pkLookup;
	
	@Column(name="LOOKUP_GROUP")
	@NotEmpty(groups=CreateEntity.class, message="{error.lookup.group.mandatory}")
	private String lookupGroupString;
	
	@Transient
	private LookupGroup lookupGroup;
	
	@Column(name = "CODE", length = 100)
	private String code;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name = "DESCR")
	private String descr;
	
	@Column(name = "ORDER_NO")
	private Long orderNo;
	
	@Column(name = "USAGE")
	private String usage;
	
	@Column(name="STATUS")
	private Integer status;
	
	public Long getPkLookup() {
		return pkLookup;
	}
	public void setPkLookup(Long pkLookup) {
		this.pkLookup = pkLookup;
	}

	public String getLookupGroupString() {
		return lookupGroupString;
	}
	public void setLookupGroupString(String lookupGroupString) {
		this.lookupGroupString = lookupGroupString;
	}
	
	public LookupGroup getLookupGroup() {
		return lookupGroup;
	}
	public void setLookupGroup(LookupGroup lookupGroup) {
		this.lookupGroup = lookupGroup;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public int compareTo(Lookup obj) {
		return this.descr.compareTo(obj.getDescr());
	}
}