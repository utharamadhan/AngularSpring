package id.base.app.valueobject.party;

import id.base.app.valueobject.BaseEntity;
import id.base.app.valueobject.Lookup;
import id.base.app.valueobject.LookupAddress;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="PARTY_ADDRESS")
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="partyAddressJid", scope=PartyAddress.class)
public class PartyAddress extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8786553639416195545L;
	
	public PartyAddress() {}

	public PartyAddress getInstance (Long pkParty) {
		PartyAddress pa = new PartyAddress();
			pa.setParty(Party.getInstance(pkParty));
		return pa;
	}
	
	public static final String ID 		= "pkPartyAddress";
	public static final String PARTY_ID = "party.pkParty";
	
	@Id
	@SequenceGenerator(name="PARTY_ADDRESS_PK_PARTY_ADDRESS_SEQ", sequenceName="PARTY_ADDRESS_PK_PARTY_ADDRESS_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="PARTY_ADDRESS_PK_PARTY_ADDRESS_SEQ")
	@Column(name = "PK_PARTY_ADDRESS", unique = true ,nullable = false, precision = 10, scale = 0)
	private Long pkPartyAddress;
	
	@ManyToOne(cascade={CascadeType.DETACH})
	@JoinColumn(name="FK_PARTY", nullable=true)
	private Party party;
	
	@OneToOne
	@JoinColumn(name = "FK_LOOKUP_ADDRESS_TYPE")
	private Lookup addressType;
	
	@Column(name="ALAMAT")
	private String alamat= "";
	
	@OneToOne
	@JoinColumn(name = "FK_LOOKUP_ADDR_KELURAHAN")
	private LookupAddress kelurahan;
	
	@Transient
	private List<LookupAddress> kelurahanOptions;

	@OneToOne
	@JoinColumn(name = "FK_LOOKUP_ADDR_KECAMATAN")
	private LookupAddress kecamatan;
	
	@Transient
	private List<LookupAddress> kecamatanOptions;
	
	@OneToOne
	@JoinColumn(name = "FK_LOOKUP_ADDR_KABUPATEN_KOTA")
	private LookupAddress kabupatenKota;
	
	@Transient
	private List<LookupAddress> kabupatenKotaOptions;
	
	@OneToOne
	@JoinColumn(name = "FK_LOOKUP_ADDR_PROVINSI")
	private LookupAddress provinsi;
	
	@Transient
	private List<LookupAddress> provinsiOptions;
	
	@Column(name="KODEPOS")
	private Integer kodepos;
	
	public Long getPkPartyAddress() {
		return pkPartyAddress;
	}

	public void setPkPartyAddress(Long pkPartyAddress) {
		this.pkPartyAddress = pkPartyAddress;
	}

	public Party getParty() {
		return party;
	}
	public void setParty(Party party) {
		this.party = party;
	}

	public Lookup getAddressType() {
		return addressType;
	}
	public void setAddressType(Lookup addressType) {
		this.addressType = addressType;
	}

	public String getAlamat() {
		return alamat;
	}
	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public LookupAddress getKelurahan() {
		return kelurahan;
	}
	public void setKelurahan(LookupAddress kelurahan) {
		this.kelurahan = kelurahan;
	}
	
	public List<LookupAddress> getKelurahanOptions() {
		return kelurahanOptions;
	}
	public void setKelurahanOptions(List<LookupAddress> kelurahanOptions) {
		this.kelurahanOptions = kelurahanOptions;
	}

	public LookupAddress getKecamatan() {
		return kecamatan;
	}
	public void setKecamatan(LookupAddress kecamatan) {
		this.kecamatan = kecamatan;
	}
	
	public List<LookupAddress> getKecamatanOptions() {
		return kecamatanOptions;
	}
	public void setKecamatanOptions(List<LookupAddress> kecamatanOptions) {
		this.kecamatanOptions = kecamatanOptions;
	}

	public LookupAddress getKabupatenKota() {
		return kabupatenKota;
	}
	public void setKabupatenKota(LookupAddress kabupatenKota) {
		this.kabupatenKota = kabupatenKota;
	}
	
	public List<LookupAddress> getKabupatenKotaOptions() {
		return kabupatenKotaOptions;
	}
	public void setKabupatenKotaOptions(List<LookupAddress> kabupatenKotaOptions) {
		this.kabupatenKotaOptions = kabupatenKotaOptions;
	}

	public LookupAddress getProvinsi() {
		return provinsi;
	}
	public void setProvinsi(LookupAddress provinsi) {
		this.provinsi = provinsi;
	}
	
	public List<LookupAddress> getProvinsiOptions() {
		return provinsiOptions;
	}
	public void setProvinsiOptions(List<LookupAddress> provinsiOptions) {
		this.provinsiOptions = provinsiOptions;
	}

	public Integer getKodepos() {
		return kodepos;
	}
	public void setKodepos(Integer kodepos) {
		this.kodepos = kodepos;
	}	
}