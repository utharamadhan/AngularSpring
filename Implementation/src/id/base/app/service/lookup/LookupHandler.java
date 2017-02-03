package id.base.app.service.lookup;

import id.base.app.dao.lookup.ILookupDAO;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.service.MaintenanceService;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.Lookup;

import java.util.List;

public class LookupHandler implements MaintenanceService<Lookup> {

	protected  ILookupDAO lookupDAO;
    
    public LookupHandler(){};
    
    public LookupHandler(ILookupDAO lookupDAO){
    	this.lookupDAO = lookupDAO;
    }
	
	public PagingWrapper<Lookup> findAll(int startNo, int offset)
			throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

	public Lookup findById(Long id) throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveOrUpdate(Lookup anObject) throws SystemException {
		// TODO Auto-generated method stub
		
	}

	public void delete(Long[] objectPKs) throws SystemException {
		// TODO Auto-generated method stub
		
	}
	
	public List<Lookup> findByLookupGroup(String lookupGroup) throws SystemException{
		return lookupDAO.findLookupByLookupGroup(lookupGroup);
		
	}

	public PagingWrapper<Lookup> findAllByFilter(int startNo, int offset,
			List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		return lookupDAO.findAllLookupWithFilter(startNo, offset, filter, order);
	}

	@Override
	public List<Lookup> findObjects(Long[] objectPKs) throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Lookup> findAll(List<SearchFilter> filter,
			List<SearchOrder> order) throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
