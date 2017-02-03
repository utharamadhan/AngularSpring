package id.base.app.dao.party;

import id.base.app.IBaseDAO;
import id.base.app.exception.SystemException;
import id.base.app.valueobject.party.Party;
import id.base.app.valueobject.party.PartyRole;

import java.util.List;

public interface IPartyDAO extends IBaseDAO<Party> {
	
	public List<Party> findAllPartyByRole(Long pkCompany, String roleCode, String keyword);
	
	public PartyRole findPartyRole(String partyRoleCode, Long pkParty) throws SystemException;
	
	public PartyRole findPartyRole(Long pkParty) throws SystemException;
	
}
