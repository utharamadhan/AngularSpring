package id.base.app.service.lookup;

import id.base.app.dao.lookup.ILookupGroupDAO;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.service.MaintenanceService;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.LookupGroup;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import softtech.hong.hce.core.QueryTransformer;
import softtech.hong.hce.model.Expression;

@Service
@Transactional
public class LookupGroupService extends QueryTransformer<LookupGroup> implements MaintenanceService<LookupGroup> , ILookupGroupService {

	@Autowired
	protected  ILookupGroupDAO lookupGroupDAO;
	
	public LookupGroupService() {
		super();
	}

	public LookupGroupService(ILookupGroupDAO lookupGroupDAO){
		this.lookupGroupDAO = lookupGroupDAO;
	}

	public LookupGroup findById(Long id) throws SystemException {
		return null;
	}

	public void saveOrUpdate(LookupGroup anObject) throws SystemException {
	}

	public void delete(Long[] objectPKs) throws SystemException {
	}

	public Long create(LookupGroup objectToCreate) {
		return null;
	}

	public LookupGroup findByReferencesId(Long referencesPK)
			throws SystemException {
		return null;
	}

	public PagingWrapper<LookupGroup> findAllByFilter(int startNo, int offset,
			List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		if (order==null) {
			order = new ArrayList<SearchOrder>();
            order.add(new SearchOrder(LookupGroup.NAME, SearchOrder.Sort.ASC));
		}
		return lookupGroupDAO.findAllLookupGroup(startNo, offset, filter, order);
	}

	@Override
	public List<LookupGroup> findObjects(Long[] objectPKs)
			throws SystemException {
		return null;
	}

	@Override
	public List<LookupGroup> findAll(List<SearchFilter> filter,
			List<SearchOrder> order) throws SystemException {
		return null;
	}
	
	@Override
	public LookupGroup findByName(String name) throws SystemException {
		return lookupGroupDAO.findByName(name);
	}
	
	@Override
	public boolean checkUpdatableByGroupName(String name) throws SystemException {
		return lookupGroupDAO.checkUpdatableByGroupName(name);
	}
	
	@Override
	public boolean checkUpdatableByLookupPK(Long pk) throws SystemException {
		return lookupGroupDAO.checkUpdatableByLookupPK(pk);
	}
	
}
