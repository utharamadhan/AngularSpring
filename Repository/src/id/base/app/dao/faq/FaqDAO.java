package id.base.app.dao.faq;

import id.base.app.AbstractHibernateDAO;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.Faq;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class FaqDAO extends AbstractHibernateDAO<Faq,Long> implements IFaqDAO {

	public Faq findById(Long lookupId) throws SystemException {
		return super.findByPK(lookupId);
	}

	public void saveOrUpdate(Faq lookup) throws SystemException {
		if(lookup.getPkFaq()==null)
			super.create(lookup);
		else
		    super.update(lookup);
	}
	
	public PagingWrapper<Faq> findAllFaq(int startNo,
			int offset, List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		return super.findAllWithPagingWrapper(startNo, offset, filter, order, null);
	}

	@Override
	public List<Faq> findAll(List<SearchFilter> filters,
			List<SearchOrder> orders) throws SystemException {
		return super.findAll(filters, orders, null);
	}

	@Override
	public void delete(Long[] objectPKs) throws SystemException {
	}

	@Override
	public List<Faq> findObjects(Long[] objectPKs) throws SystemException {
		return null;
	}

	@Override
	public PagingWrapper<Faq> findAllByFilter(int startNo, int offset,
			List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		return null;
	}
}
