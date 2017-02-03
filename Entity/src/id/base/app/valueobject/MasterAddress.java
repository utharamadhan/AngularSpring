package id.base.app.valueobject;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity(name="masterAddress")
@Table(name = "MASTER_ADDRESS")
public class MasterAddress extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -5635113090633922487L;
	
	public static final String ID 			= "pkMasterAddress";
	public static final String KODEPOS 		= "kodepos";
	public static final String PROVINSI 	= "provinsi";
	public static final String PROVINSI_ID 	= PROVINSI +"."+ LookupAddress.ID;
	public static final String PROVINSI_NAME	= PROVINSI +"."+ LookupAddress.NAME;
	public static final String PROVINSI_STATUS	= PROVINSI +"."+ LookupAddress.STATUS;
	public static final String KAB_KOTA 	= "kabupatenKota";
	public static final String KAB_KOTA_ID 	= KAB_KOTA +"."+ LookupAddress.ID;
	public static final String KAB_KOTA_NAME 	= KAB_KOTA +"."+ LookupAddress.NAME;
	public static final String KAB_KOTA_STATUS 	= KAB_KOTA +"."+ LookupAddress.STATUS;
	public static final String KECAMATAN 	= "kecamatan";
	public static final String KECAMATAN_ID	= KECAMATAN +"."+ LookupAddress.ID;
	public static final String KECAMATAN_NAME	= KECAMATAN +"."+ LookupAddress.NAME;
	public static final String KECAMATAN_STATUS	= KECAMATAN +"."+ LookupAddress.STATUS;
	public static final String KELURAHAN 	= "kelurahan";
	public static final String KELURAHAN_ID	= KELURAHAN +"."+ LookupAddress.ID;
	public static final String KELURAHAN_NAME	= KELURAHAN +"."+ LookupAddress.NAME;
	public static final String KELURAHAN_STATUS	= KELURAHAN +"."+ LookupAddress.STATUS;
	
	public static final String[] ALL_ID_BY_KODEPOS_PROPERTIES = new String[]{ID, KODEPOS, 
		PROVINSI_ID, PROVINSI_NAME, KAB_KOTA_ID, KAB_KOTA_NAME, KECAMATAN_ID, KELURAHAN_ID};
	
	@Id
	@SequenceGenerator(name="MASTER_ADDRESS_PK_MASTER_ADDRESS_SEQ", sequenceName="MASTER_ADDRESS_PK_MASTER_ADDRESS_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="MASTER_ADDRESS_PK_MASTER_ADDRESS_SEQ")
	@Column(name = "PK_MASTER_ADDRESS", unique = true, nullable = false)
	@NotNull(groups=UpdateEntity.class, message="{error.message.update.not.allowed}")
	@Null(groups=CreateEntity.class, message="{error.message.create.not.allowed}")
	private Long pkMasterAddress;

	@Column(name="KODEPOS")
	private Integer kodepos;
	
	@OneToOne
	@JoinColumn(name = "FK_LOOKUP_ADDR_KELURAHAN")
	private LookupAddress kelurahan;
	
	@OneToOne
	@JoinColumn(name = "FK_LOOKUP_ADDR_KECAMATAN")
	private LookupAddress kecamatan;
	
	@OneToOne
	@JoinColumn(name = "FK_LOOKUP_ADDR_KABUPATEN_KOTA")
	private LookupAddress kabupatenKota;
	
	@OneToOne
	@JoinColumn(name = "FK_LOOKUP_ADDR_PROVINSI")
	private LookupAddress provinsi;
	
	@Transient
	private LookupAddress destLookupAddress;
	
	public Long getPkMasterAddress() {
		return pkMasterAddress;
	}

	public void setPkMasterAddress(Long pkMasterAddress) {
		this.pkMasterAddress = pkMasterAddress;
	}

	public Integer getKodepos() {
		return kodepos;
	}

	public void setKodepos(Integer kodepos) {
		this.kodepos = kodepos;
	}

	public LookupAddress getKelurahan() {
		return kelurahan;
	}

	public void setKelurahan(LookupAddress kelurahan) {
		this.kelurahan = kelurahan;
	}

	public LookupAddress getKecamatan() {
		return kecamatan;
	}

	public void setKecamatan(LookupAddress kecamatan) {
		this.kecamatan = kecamatan;
	}

	public LookupAddress getKabupatenKota() {
		return kabupatenKota;
	}

	public void setKabupatenKota(LookupAddress kabupatenKota) {
		this.kabupatenKota = kabupatenKota;
	}

	public LookupAddress getProvinsi() {
		return provinsi;
	}

	public void setProvinsi(LookupAddress provinsi) {
		this.provinsi = provinsi;
	}

	public LookupAddress getDestLookupAddress() {
		return destLookupAddress;
	}

	public void setDestLookupAddress(LookupAddress destLookupAddress) {
		this.destLookupAddress = destLookupAddress;
	}
}