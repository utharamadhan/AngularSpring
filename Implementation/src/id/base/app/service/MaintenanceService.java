package id.base.app.service;

import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;

import java.util.List;

public interface MaintenanceService<T> {
	
	public T findById(Long id) throws SystemException  ;
	
	public void saveOrUpdate(T anObject) throws SystemException ;
	
	public void delete(Long[] objectPKs) throws SystemException ;
	
	public List<T> findObjects(Long[] objectPKs)throws SystemException;
	
	public PagingWrapper<T> findAllByFilter(int startNo, int offset,List<SearchFilter> filter,List<SearchOrder> order) throws SystemException ;
	
	public List<T> findAll(List<SearchFilter> filter,List<SearchOrder> order) throws SystemException ;
	
}
