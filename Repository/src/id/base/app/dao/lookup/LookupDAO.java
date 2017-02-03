package id.base.app.dao.lookup;

import id.base.app.AbstractHibernateDAO;
import id.base.app.SystemConstant;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.Lookup;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LookupDAO extends AbstractHibernateDAO<Lookup,Long> implements ILookupDAO {

	@SuppressWarnings("unchecked")
	public List<Lookup> findLookupByLookupGroup(String lookupGroup)
			throws SystemException {
		List<Lookup> lookups = new ArrayList<Lookup>();
		
		Criteria c = getSession().createCriteria(Lookup.class);
		c.add(Restrictions.eq(Lookup.LOOKUP_GROUP_STRING, lookupGroup));
		c.add(Restrictions.eq(Lookup.STATUS, SystemConstant.ValidFlag.VALID));
		c.addOrder(Order.asc(Lookup.ORDER_NO_STRING));
		c.setCacheable(true);
		c.setCacheRegion("query.findLookupByLookupGroup");
		
		lookups = c.list();
		
        if(lookups.size() > 0)
    	   return lookups ;
        else
        	return null;
	}
	
	
	@Override
	public List<Lookup> findLookupByLookupGroupOrderBy(String lookupGroup, String orderBy, boolean desc)
			throws SystemException {
		List<Lookup> lookups = new ArrayList<Lookup>();
		
		Criteria c = getSession().createCriteria(Lookup.class);
		c.add(Restrictions.eq(Lookup.LOOKUP_GROUP_STRING, lookupGroup));
		c.add(Restrictions.eq(Lookup.STATUS, SystemConstant.ValidFlag.VALID));
		if(desc){
			c.addOrder(Order.desc(orderBy));			
		}else{
			c.addOrder(Order.asc(orderBy));
		}
		
		lookups = c.list();
		
        if(lookups.size() > 0){
    	   return lookups ;
        }else{
        	return null;
        }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Lookup> findByLookupGroupAndUsage(String lookupGroup, String usage)
			throws SystemException {
		List<Lookup> lookups = new ArrayList<Lookup>();
		Criteria c = getSession().createCriteria(Lookup.class);
		c.add(Restrictions.eq(Lookup.LOOKUP_GROUP_STRING, lookupGroup));
		c.add(Restrictions.like(Lookup.USAGE, usage, MatchMode.ANYWHERE));
		c.add(Restrictions.eq(Lookup.STATUS, SystemConstant.ValidFlag.VALID));
		c.addOrder(Order.asc(Lookup.ORDER_NO_STRING));
		c.setCacheable(true);
		c.setCacheRegion("query.findByLookupGroupAndUsage");
		
		lookups = c.list();
		
        if(lookups.size() > 0)
    	   return lookups ;
        else
        	return null;
	}
	
	public Lookup findById(Long lookupId) throws SystemException {
		return super.findByPK(lookupId);
	}

	public void saveOrUpdate(Lookup lookup) throws SystemException {
		if(lookup.getPkLookup()==null)
			super.create(lookup);
		else
		    super.update(lookup);
	}

	public Lookup findByCode(String code, String lookupGroup) {
		Criteria codeCriteria = getSession().createCriteria(Lookup.class);
		codeCriteria.add(Restrictions.eq(Lookup.CODE, code));
		codeCriteria.add(Restrictions.eq(Lookup.LOOKUP_GROUP_STRING, lookupGroup));
		return (Lookup) codeCriteria.uniqueResult();
	}
	
	public PagingWrapper<Lookup> findAllLookupWithFilter(int startNo,
			int offset, List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		return super.findAllWithPagingWrapper(startNo, offset, filter, order, null);
	}

	@Override
	public List<Lookup> findAll(List<SearchFilter> filters,
			List<SearchOrder> orders) throws SystemException {
		return super.findAll(filters, orders, null);
	}
	
	@Override
	public void delete(Long[] ids) throws SystemException {
		List<Lookup> objectList = new ArrayList<Lookup>();
		for(int i=0;i<ids.length;i++){
			Lookup object = new Lookup();
			object = findById(ids[i]);
			objectList.add(object);
		}
		super.delete(objectList);
	}
}
