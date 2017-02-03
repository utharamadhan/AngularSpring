package id.base.app.valueobject.party;

import id.base.app.SystemConstant;
import id.base.app.valueobject.BaseEntity;
import id.base.app.valueobject.CreateEntity;
import id.base.app.valueobject.UpdateEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="PARTY")
@Inheritance(strategy=InheritanceType.JOINED)
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="partyJid", scope=Party.class)
public class Party extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 6669475504922371586L;
	
	public Party() {}	
	
	public static Party getInstance() {
		Party obj = new Party();
			obj.setStatus(SystemConstant.ValidFlag.VALID);
		return obj;
	}
	
	public static Party getInstance(Long pkParty) {
		Party obj = new Party();
			obj.setPkParty(pkParty);
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
	public static final String NPWP = "npwp";
	public static final String PARTY_ADDRESS = "partyAddresses";
	public static final String PARTY_ADDRESS_KODEPOS = PARTY_ADDRESS + ".kodepos";
	public static final String PARTY_ADDRESS_ALAMAT_VALFIELD = PARTY_ADDRESS + "[%d].alamat";
	public static final String PARTY_ADDRESS_PROVINSI = PARTY_ADDRESS + ".provinsi";
	public static final String PARTY_ADDRESS_PROVINSI_NAME = PARTY_ADDRESS_PROVINSI + ".name";
	public static final String PARTY_ADDRESS_PROVINSI_VALFIELD = PARTY_ADDRESS + "[%d].provinsi.pkLookupAddress";
	public static final String PARTY_ADDRESS_KABUPATEN_KOTA = PARTY_ADDRESS + ".kabupatenKota";
	public static final String PARTY_ADDRESS_KABUPATEN_KOTA_NAME = PARTY_ADDRESS_KABUPATEN_KOTA + ".name";
	public static final String PARTY_ADDRESS_KABUPATEN_KOTA_VALFIELD = PARTY_ADDRESS + "[%d].kabupatenKota.pkLookupAddress";
	public static final String PARTY_ADDRESS_KELURAHAN = PARTY_ADDRESS + ".kelurahan";
	public static final String PARTY_ADDRESS_KELURAHAN_NAME = PARTY_ADDRESS_KELURAHAN + ".name";
	public static final String PARTY_ADDRESS_KELURAHAN_VALFIELD = PARTY_ADDRESS + "[%d].kelurahan.pkLookupAddress";
	public static final String PARTY_ADDRESS_KECAMATAN = PARTY_ADDRESS + ".kecamatan";
	public static final String PARTY_ADDRESS_KECAMATAN_NAME = PARTY_ADDRESS_KECAMATAN + ".name";
	public static final String PARTY_ADDRESS_KECAMATAN_VALFIELD = PARTY_ADDRESS + "[%d].kecamatan.pkLookupAddress";
	
	@Id
	@Column(name = "PK_PARTY", unique = true ,nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name="PARTY_PK_PARTY_SEQ", sequenceName="PARTY_PK_PARTY_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="PARTY_PK_PARTY_SEQ")
	@NotNull(groups=UpdateEntity.class, message="{error.message.update.not.allowed}")
	@Null(groups=CreateEntity.class, message="{error.message.create.not.allowed}")
	private Long pkParty;
	
	@ManyToOne(cascade={CascadeType.DETACH})
	@JoinColumn(name="FK_PARTY_PARENT")
	private Party partyParent;
	
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "NPWP")
	private String npwp;
	
	@OneToMany(mappedBy="party", cascade=CascadeType.ALL)
	@Column
	private Set<PartyRole> partyRoles = new HashSet<>();
	
	@OneToMany(mappedBy="party", cascade=CascadeType.ALL, orphanRemoval = true)
	@Column
	private List<PartyAddress> partyAddresses = new ArrayList<>();
	
	@OneToMany(mappedBy="party", cascade=CascadeType.ALL, orphanRemoval = true)
	@Column
	private List<PartyContact> partyContacts = new ArrayList<PartyContact>();
	
	@Column(name = "STATUS")
	private Integer status;

	public Long getPkParty() {
		return pkParty;
	}
	public void setPkParty(Long pkParty) {
		this.pkParty = pkParty;
	}
	
	public Party getPartyParent() {
		return partyParent;
	}
	public void setPartyParent(Party partyParent) {
		this.partyParent = partyParent;
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
	
	public String getNpwp() {
		return npwp;
	}
	public void setNpwp(String npwp) {
		this.npwp = npwp;
	}

	public Set<PartyRole> getPartyRoles() {
		return partyRoles;
	}
	public void setPartyRoles(Set<PartyRole> partyRoles) {
		this.partyRoles = partyRoles;
	}
	@Transient
	public void addPartyRole(PartyRole partyRole) {
		if(this.partyRoles == null){
			this.partyRoles = new HashSet<PartyRole>();
		}
		this.partyRoles.add(partyRole);
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

	public List<PartyContact> getPartyContacts() {
		return partyContacts;
	}
	public void setPartyContacts(List<PartyContact> partyContacts) {
		if(this.partyContacts == null) {
			this.partyContacts = new ArrayList<PartyContact>();
		}
		if(partyContacts != null) {
			this.partyContacts.clear();
			this.partyContacts.addAll(partyContacts);
		}
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}