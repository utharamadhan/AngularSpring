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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
@Table(name="PARTY_ROLE")
@Inheritance(strategy=InheritanceType.JOINED)
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="partyRoleJid", scope=PartyRole.class)
public class PartyRole implements Serializable{
	
	private static final long serialVersionUID = 2042594740342005369L;
	
	public static final String ID = "pkPartyRole";
	public static final String PARTY = "party";
	public static final String PARTY_ID = "party.pkParty";
	
	public static PartyRole getInstance (Lookup partyRole, Party party) {
		PartyRole obj = new PartyRole();
			obj.setRole(partyRole);
			obj.setParty(party);
		return obj;
	}	

	@Id
	@Column(name = "PK_PARTY_ROLE", unique = true ,nullable = false, precision = 10, scale = 0)
	@SequenceGenerator(name="PARTY_ROLE_PK_PARTY_ROLE_SEQ", sequenceName="PARTY_ROLE_PK_PARTY_ROLE_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="PARTY_ROLE_PK_PARTY_ROLE_SEQ")
	@NotNull(groups=UpdateEntity.class, message="{error.message.update.not.allowed}")
	@Null(groups=CreateEntity.class, message="{error.message.create.not.allowed}")
	private Long pkPartyRole;
	
	@ManyToOne(cascade={CascadeType.DETACH})
	@JoinColumn(name="FK_PARTY", nullable=true)
	private Party party = new Party();
	
	@OneToOne
	@JoinColumn(name = "FK_LOOKUP_PARTY_ROLE")
	private Lookup role = new Lookup();
	
	public PartyRole() {
		super();
	}

	public Long getPkPartyRole() {
		return pkPartyRole;
	}

	public void setPkPartyRole(Long pkPartyRole) {
		this.pkPartyRole = pkPartyRole;
	}

	public Lookup getRole() {
		return role;
	}

	public void setRole(Lookup role) {
		this.role = role;
	}
	
	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}
}
