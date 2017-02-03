package id.base.app.service.lookup;

import id.base.app.dao.lookup.ILookupAddressDAO;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.LookupAddress;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LookupAddressService implements ILookupAddressService {

	@Autowired
	private ILookupAddressDAO lookupAddressDAO;
	
	@Override
	public LookupAddress findById(Long id) throws SystemException {
		return lookupAddressDAO.findById(id);
	}

	@Override
	public void saveOrUpdate(LookupAddress anObject) throws SystemException {
		lookupAddressDAO.saveOrUpdate(anObject);
	}

	@Override
	public void delete(Long[] objectPKs) throws SystemException {
		lookupAddressDAO.delete(objectPKs);
	}

	@Override
	public List<LookupAddress> findObjects(Long[] objectPKs)
			throws SystemException {
		return null;
	}

	@Override
	public PagingWrapper<LookupAddress> findAllByFilter(int startNo,
			int offset, List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		return lookupAddressDAO.findAllByFilter(startNo, offset, filter, order);
	}

	@Override
	public List<LookupAddress> findAll(List<SearchFilter> filter,
			List<SearchOrder> order) throws SystemException {
		return null;
	}

	@Override
	public List<LookupAddress> findByLookupAddressGroup(
			String lookupAddressGroup) throws SystemException {
		return lookupAddressDAO.findByLookupAddressGroup(lookupAddressGroup);
	}

	@Override
	public List<LookupAddress> findByGroupOrderBy(String lookupAddressGroup,
			String orderBy, boolean desc) throws SystemException {
		return lookupAddressDAO.findByGroupOrderBy(lookupAddressGroup, orderBy, desc);
	}

}