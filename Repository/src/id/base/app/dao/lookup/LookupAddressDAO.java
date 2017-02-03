package id.base.app.dao.lookup;

import id.base.app.AbstractHibernateDAO;
import id.base.app.SystemConstant;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.Lookup;
import id.base.app.valueobject.LookupAddress;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LookupAddressDAO extends AbstractHibernateDAO<LookupAddress,Long> implements ILookupAddressDAO {

	@Override
	public void saveOrUpdate(LookupAddress anObject) throws SystemException {
		if(anObject.getPkLookupAddress()==null)
			super.create(anObject);
		else
		    super.update(anObject);
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
		return super.findAllWithPagingWrapper(startNo, offset, filter, order, null);
	}
	
	@Override
	public LookupAddress findById(Long id) throws SystemException {
		return super.findByPK(id);
	}
	
	@Override
	public void delete(Long[] ids) throws SystemException {
		List<LookupAddress> objectList = new ArrayList<LookupAddress>();
		for(int i=0;i<ids.length;i++){
			LookupAddress object = new LookupAddress();
			object = findById(ids[i]);
			objectList.add(object);
		}
		super.delete(objectList);
	}

	@Override
	public List<LookupAddress> findByLookupAddressGroup(String lookupAddressGroup)
			throws SystemException {
		List<LookupAddress> list = new ArrayList<LookupAddress>();
		Criteria c = getSession().createCriteria(LookupAddress.class);
		c.add(Restrictions.eq(LookupAddress.ADDRESS_GROUP, lookupAddressGroup));
		c.add(Restrictions.eq(LookupAddress.STATUS, SystemConstant.ValidFlag.VALID));
		c.addOrder(Order.asc(LookupAddress.NAME));
		c.setCacheable(true);
		c.setCacheRegion("query.findByLookupAddressGroup");
		list = c.list();
        if(list.size() > 0)
    	   return list ;
        else
        	return null;
	}

	@Override
	public List<LookupAddress> findByGroupOrderBy(String lookupAddressGroup,
			String orderBy, boolean desc) throws SystemException {
		List<LookupAddress> list = new ArrayList<LookupAddress>();
		Criteria c = getSession().createCriteria(LookupAddress.class);
		c.add(Restrictions.eq(LookupAddress.ADDRESS_GROUP, lookupAddressGroup));
		c.add(Restrictions.eq(Lookup.STATUS, SystemConstant.ValidFlag.VALID));
		if(desc){
			c.addOrder(Order.desc(orderBy));			
		}else{
			c.addOrder(Order.asc(orderBy));
		}
		list = c.list();
        if(list.size() > 0){
    	   return list ;
        }else{
        	return null;
        }
	}
}