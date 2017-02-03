package id.base.app.service.party;

import id.base.app.exception.SystemException;
import id.base.app.service.MaintenanceService;
import id.base.app.valueobject.party.Party;
import id.base.app.valueobject.party.PartyRole;

import java.util.List;

public interface IPartyService extends MaintenanceService<Party>{
	
	public List<Party> findAllPartyByRole(Long pkCompany, String roleCode, String keyword);
	
	public PartyRole findPartyRole(Long pkParty) throws SystemException;
	
	public PartyRole findPartyRole(String partyRoleCode, Long pkParty) throws SystemException;

}
