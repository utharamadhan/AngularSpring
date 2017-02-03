package id.base.app.valueobject.party;

import id.base.app.valueobject.CreateEntity;
import id.base.app.valueobject.Lookup;
import id.base.app.valueobject.UpdateEntity;

import java.io.Serializable;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name="PARTY_CONTACT")
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="partyContactJid", scope=PartyContact.class)
public class PartyContact implements Serializable{

	private static final long serialVersionUID = 5916028964066121083L;

	public PartyContact() {}
	
	public PartyContact getInstance (Party party) {
		PartyContact obj = new PartyContact();
			obj.setParty(party);
		return obj;
	}
	
	public PartyContact getInstance (Long pkParty) {
		PartyContact obj = new PartyContact();
			obj.setParty(Party.getInstance(pkParty));
		return obj;
	}
	
	@Id
	@Column(name = "PK_PARTY_CONTACT", unique = true ,nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name="PARTY_CONTACT_PK_PARTY_CONTACT_SEQ", sequenceName="PARTY_CONTACT_PK_PARTY_CONTACT_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="PARTY_CONTACT_PK_PARTY_CONTACT_SEQ")
	@NotNull(groups=UpdateEntity.class, message="{error.message.update.not.allowed}")
	@Null(groups=CreateEntity.class, message="{error.message.create.not.allowed}")
	private Long pkPartyContact;
	
	@ManyToOne(cascade={CascadeType.DETACH})
	@JoinColumn(name="FK_PARTY", nullable=true)
	@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="partyContactParty", scope=PartyContact.class)
	private Party party;
	
	@OneToOne
	@JoinColumn(name = "FK_LOOKUP_CONTACT_TYPE")
	private Lookup contactType;
	
	@Column(name="CONTACT")
	private String contact;
	
	public Long getPkPartyContact() {
		return pkPartyContact;
	}
	public void setPkPartyContact(Long pkPartyContact) {
		this.pkPartyContact = pkPartyContact;
	}

	public Party getParty() {
		return party;
	}
	public void setParty(Party party) {
		this.party = party;
	}

	public Lookup getContactType() {
		return contactType;
	}
	public void setContactType(Lookup contactType) {
		this.contactType = contactType;
	}

	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
}