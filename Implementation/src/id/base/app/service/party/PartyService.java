package id.base.app.service.party;

import id.base.app.dao.party.IPartyDAO;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.party.Party;
import id.base.app.valueobject.party.PartyRole;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PartyService implements IPartyService {
	
	@Autowired
	private IPartyDAO partyDAO;
	
	@Override
	public Party findById(Long id) throws SystemException {
		return null;
	}

	@Override
	public void saveOrUpdate(Party anObject) throws SystemException {
		partyDAO.saveOrUpdate(anObject);
	}

	@Override
	public void delete(Long[] objectPKs) throws SystemException {
	}

	@Override
	public List<Party> findObjects(Long[] objectPKs) throws SystemException {
		return null;
	}

	@Override
	public PagingWrapper<Party> findAllByFilter(int startNo, int offset,
			List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		return partyDAO.findAllByFilter(startNo, offset, filter, order);
	}

	@Override
	public List<Party> findAll(List<SearchFilter> filter,
			List<SearchOrder> order) throws SystemException {
		return null;
	}

	@Override
	public List<Party> findAllPartyByRole(Long pkCompany, String roleCode, String keyword) {
		return partyDAO.findAllPartyByRole(pkCompany, roleCode, keyword);
	}

	@Override
	public PartyRole findPartyRole(String partyRoleCode, Long pkParty) throws SystemException {
		return partyDAO.findPartyRole(partyRoleCode, pkParty);
	}
	
	@Override
	public PartyRole findPartyRole(Long pkParty) throws SystemException {
		return partyDAO.findPartyRole(pkParty);
	}
	
}