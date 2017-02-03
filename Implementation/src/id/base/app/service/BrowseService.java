package id.base.app.service;

import id.base.app.exception.SystemException;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.AppUser;

import java.util.List;

public interface BrowseService<T> {

	public abstract List<T> findDestinations(Long pk) throws SystemException;
	public abstract void save(Long browsePK,long[] destinationPKs) throws SystemException;
	public abstract List<T> findByFilter(List<SearchFilter> searchFilter , List<SearchOrder> searchOrders) ;
	public abstract List<AppUser> findAllByFilter(List<SearchFilter> filter,
			List<SearchOrder> order) throws SystemException;
	
}
