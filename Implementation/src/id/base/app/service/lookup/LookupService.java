package id.base.app.service.lookup;

import id.base.app.dao.lookup.ILookupDAO;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.service.MaintenanceService;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.Lookup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LookupService implements MaintenanceService<Lookup>, ILookupService {

	@Autowired
	protected  ILookupDAO lookupDAO;	
    
    public LookupService(){};
    
    public LookupService(ILookupDAO lookupDAO){
    	this.lookupDAO = lookupDAO;
    }
	
	public PagingWrapper<Lookup> findAll(int startNo, int offset)
			throws SystemException {
		return null;
	}

	public Lookup findById(Long id) throws SystemException {
		return lookupDAO.findById(id);
	}

	public void saveOrUpdate(Lookup anObject) throws SystemException {
		lookupDAO.saveOrUpdate(anObject);
	}

	public void delete(Long[] objectPKs) throws SystemException {
		lookupDAO.delete(objectPKs);
	}
	
	public List<Lookup> findByLookupGroup(String lookupGroup) throws SystemException{
		return lookupDAO.findLookupByLookupGroup(lookupGroup);
	}
	
	@Override
	public List<Lookup> findByLookupGroupAndUsage(String lookupGroup, String usage) throws SystemException{
		return lookupDAO.findByLookupGroupAndUsage(lookupGroup, usage);
	}
	
	@Override
	public List<Lookup> findByLookupGroupOrderBy(String lookupGroup, String orderBy, boolean desc) throws SystemException{
		return lookupDAO.findLookupByLookupGroupOrderBy(lookupGroup, orderBy, desc);
	}

	public PagingWrapper<Lookup> findAllByFilter(int startNo, int offset,
			List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		return lookupDAO.findAllLookupWithFilter(startNo, offset, filter, order);
	}

	public Lookup findByCode(String code, String lookupGroup) {
		return lookupDAO.findByCode(code, lookupGroup);
	}
	
	@Override
	public List<Lookup> findObjects(Long[] objectPKs) throws SystemException {
		List<Lookup> lookups=new ArrayList<Lookup>();
		Lookup lookup=null;
		for(Long l:objectPKs){
			lookup=lookupDAO.findById(l);
			lookups.add(lookup);
		}
		return lookups;
	}

	@Override
	public List<Lookup> findAll(List<SearchFilter> filter,
			List<SearchOrder> order) throws SystemException {
		return lookupDAO.findAll(filter, order);
	}
}