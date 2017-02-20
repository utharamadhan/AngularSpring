package id.base.app.valueobject.party;

import id.base.app.SystemConstant;
import id.base.app.valueobject.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="PARTY")
@Inheritance(strategy=InheritanceType.JOINED)
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="partyJid", scope=Party.class)
public class Party extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 6669475504922371586L;
	
	public static Party getInstance() {
		Party obj = new Party();
			obj.setStatus(SystemConstant.ValidFlag.VALID);
		return obj;
	}
	
	public static Party getInstance(Long pkParty) {
		Party obj = new Party();
			obj.setPkParty(pkParty);
			obj.setStatus(SystemConstant.ValidFlag.VALID);
		return obj;
	}
	
	public static Party getInstance(String name) {
		Party obj = new Party();
			obj.setName(name);
			obj.setStatus(SystemConstant.ValidFlag.VALID);
		return obj;
	}
	
	public static final String ID	= "pkParty";
	public static final String CODE = "code";
	public static final String NAME	= "name";
	
	@Id
	@Column(name = "PK_PARTY", unique = true ,nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name="PARTY_PK_PARTY_SEQ", sequenceName="PARTY_PK_PARTY_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="PARTY_PK_PARTY_SEQ")
	private Long pkParty;
	
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "NAME")
	private String name;
	
	@OneToMany(mappedBy="party", cascade=CascadeType.ALL, orphanRemoval = true)
	@Column
	private List<PartyAddress> partyAddresses = new ArrayList<>();
	
	@Column(name = "STATUS")
	private Integer status;

	public Long getPkParty() {
		return pkParty;
	}
	public void setPkParty(Long pkParty) {
		this.pkParty = pkParty;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<PartyAddress> getPartyAddresses() {
		return partyAddresses;
	}
	public void setPartyAddresses(List<PartyAddress> partyAddresses) {
		if(this.partyAddresses == null) {
			this.partyAddresses = new ArrayList<PartyAddress>();
		}
		if(partyAddresses != null) {
			this.partyAddresses.clear();
			this.partyAddresses.addAll(partyAddresses);
		}
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}