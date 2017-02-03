package id.base.app;

import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;

import softtech.hong.hce.model.Expression;
import softtech.hong.hce.model.PropertyValue;

public interface IBaseDAO<T> {
	public T findById(Long id) throws SystemException  ;
	
	public void saveOrUpdate(T anObject) throws SystemException ;
	
	public void delete(Long[] objectPKs) throws SystemException ;
	
	public List<T> findObjects(Long[] objectPKs)throws SystemException;
	
	public PagingWrapper<T> findAllByFilter(int startNo, int offset,List<SearchFilter> filter,List<SearchOrder> order) throws SystemException ;
	
	public List<T> findAll(List<SearchFilter> filter,List<SearchOrder> order) throws SystemException ;
	
	public List<T> loadAll(DetachedCriteria detachedCriteria) throws SystemException;
	
	public List<T> top(DetachedCriteria detachedCriteria, int rows) throws SystemException;
	
	public T first(DetachedCriteria detachedCriteria) throws SystemException;
	
	public void updateByIdentity(String property, Object value, Object identityValue) throws SystemException;
	
	public void updateByIdentity(Class<?> clazz, String property, Object value, Object identityValue) throws SystemException;
	
	public void update(String property, Object value, Expression expression) throws SystemException;
	
	public void update(Class<?> clazz, Long[] ids, PropertyValue[] propertyValues) throws SystemException;
	
	public void update(PropertyValue[] propertyValues, Expression...expressions) throws SystemException;
	
	public void update(Class<?> clazz, PropertyValue[] propertyValues, Expression...expressions) throws SystemException;
	
	public boolean isExists(DetachedCriteria detachedCriteria) throws HibernateException;
	
	public Long getCount(DetachedCriteria detachedCriteria) throws HibernateException;
	
	public void hardDelete( Long[] ids) throws SystemException;
	
	public void softDelete(Class<?> clazz, String property, Long[] ids, boolean value) throws SystemException;
	
	public void softDelete(Class<?> clazz, Long[] ids, softtech.hong.hce.model.Expression... expressions) throws SystemException;
	
	public void softDelete(String property, Long[] ids, boolean value) throws SystemException;
	
	public void softDelete( Long[] ids, softtech.hong.hce.model.Expression... expressions) throws SystemException;
	
	public PagingWrapper<T> findAllWithPagingWrapper(DetachedCriteria[] detachedCriterias, int startNo, int offset) throws SystemException; 

	public Date getCurrentSystemDate();
}
