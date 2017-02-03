package id.base.app.dao.lookup;

import id.base.app.IBaseDAO;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.LookupGroup;

import java.util.List;

public interface ILookupGroupDAO extends IBaseDAO<LookupGroup>{
	
	public PagingWrapper<LookupGroup> findAllLookupGroup(
			int startNo, int offset) throws SystemException ;

	PagingWrapper<LookupGroup> findAllLookupGroup(int startNo, int offset,
			List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException;
	
	public LookupGroup findByName(String name) throws SystemException;

	public abstract boolean checkUpdatableByLookupPK(Long pk)
			throws SystemException;

	public abstract boolean checkUpdatableByGroupName(String name)
			throws SystemException;

}
