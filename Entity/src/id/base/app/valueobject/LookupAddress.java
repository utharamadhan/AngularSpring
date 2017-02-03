package id.base.app.valueobject;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.NotEmpty;

@Entity(name="LookupAddress")
@Table(name = "LOOKUP_ADDRESS")
public class LookupAddress extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -4590102599450214754L;
	
	public LookupAddress() {}
	
	public static LookupAddress getInstance(Long pkLookupAddress) {
		LookupAddress obj = new LookupAddress();
			obj.setPkLookupAddress(pkLookupAddress);
		return obj;
	}
	
	public static LookupAddress getInstance(String addressGroup, String name) {
		LookupAddress obj = new LookupAddress();
			obj.setAddressGroup(addressGroup);
			obj.setName(name);
		return obj;
	}
	
	public static final String ID 				= "pkLookupAddress";
	public static final String ADDRESS_GROUP 	= "addressGroup";
	public static final String NAME				= "name";
	public static final String ORDER_NO			= "orderNo";
	public static final String STATUS			= "status";
	public static final String COORDINATE		= "coordinate";
	
	public static final String[] MAINTENANCE_LIST_FIELDS = {
		LookupAddress.ID, LookupAddress.ADDRESS_GROUP, LookupAddress.NAME, LookupAddress.ORDER_NO, LookupAddress.STATUS, LookupAddress.COORDINATE
	};

	@Id
	@SequenceGenerator(name="LOOKUP_ADDRESS_PK_LOOKUP_ADDRESS_SEQ", sequenceName="LOOKUP_ADDRESS_PK_LOOKUP_ADDRESS_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="LOOKUP_ADDRESS_PK_LOOKUP_ADDRESS_SEQ")
	@Column(name = "PK_LOOKUP_ADDRESS", unique = true, nullable = false)
	@NotNull(groups=UpdateEntity.class, message="{error.message.update.not.allowed}")
	@Null(groups=CreateEntity.class, message="{error.message.create.not.allowed}")
	private Long pkLookupAddress;
	
	@Column(name="ADDRESS_GROUP")
	@NotEmpty(groups=CreateEntity.class, message="{error.lookup.group.mandatory}")
	private String addressGroup;
	
	@Column(name = "ADDRESS_NAME", length = 100)
	private String name;
	
	@Column(name = "ORDER_NO")
	private Long orderNo;
	
	@Column(name="STATUS")
	private Integer status;
	
	@Column(name="COORDINATE")
	private String coordinate;
	
	public Long getPkLookupAddress() {
		return pkLookupAddress;
	}
	public void setPkLookupAddress(Long pkLookupAddress) {
		this.pkLookupAddress = pkLookupAddress;
	}

	public String getAddressGroup() {
		return addressGroup;
	}
	public void setAddressGroup(String addressGroup) {
		this.addressGroup = addressGroup;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
}