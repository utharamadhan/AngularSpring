package id.base.app.service.party;

import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.service.MaintenanceService;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.party.PartyContact;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class PartyContactService implements MaintenanceService<PartyContact>, IPartyContactService{

	@Override
	public PartyContact findById(Long id) throws SystemException {
		return null;
	}

	@Override
	public void saveOrUpdate(PartyContact anObject) throws SystemException {
	}

	@Override
	public void delete(Long[] objectPKs) throws SystemException {
	}

	@Override
	public List<PartyContact> findObjects(Long[] objectPKs)
			throws SystemException {
		return null;
	}

	@Override
	public PagingWrapper<PartyContact> findAllByFilter(int startNo, int offset,
			List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PartyContact> findAll(List<SearchFilter> filter,
			List<SearchOrder> order) throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}
}