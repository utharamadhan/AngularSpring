package id.base.app.dao.lookup;

import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.Lookup;

import java.util.List;


public interface ILookupDAO {
	public abstract List<Lookup> findLookupByLookupGroup(String lookupGroup)
		throws SystemException;
	
	public abstract PagingWrapper<Lookup> findAllLookupWithFilter(int startNo,
			int offset, List<SearchFilter> filter,List<SearchOrder> order) throws SystemException ;
	
	public abstract Lookup findById(Long lookupId) throws SystemException ;
	
	public abstract void saveOrUpdate(Lookup lookup) throws SystemException ;
	
	Lookup findByCode(String code, String lookupGroup);
	
	List<Lookup> findAll(List<SearchFilter> filters, List<SearchOrder> orders) throws SystemException ;
	
	void delete(Long[] ids) throws SystemException;

	public abstract List<Lookup> findLookupByLookupGroupOrderBy(String lookupGroup,
			String orderBy, boolean desc) throws SystemException;

	List<Lookup> findByLookupGroupAndUsage(String lookupGroup, String usage)
			throws SystemException;
}
