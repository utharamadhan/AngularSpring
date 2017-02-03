package id.base.app.dao.error;

import id.base.app.AbstractHibernateDAO;
import id.base.app.IBaseDAO;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.Error;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ErrorDAO extends AbstractHibernateDAO<Error, Long> implements IBaseDAO<Error>, IErrorDAO {

	@Override
	public Error findById(Long id) throws SystemException {
		return super.findByPK(id);
	}

	@Override
	public void saveOrUpdate(Error anObject) throws SystemException {
		if(anObject.getPkError()!=null){
			super.update(anObject);
		}else{
			super.create(anObject);
		}
	}

	@Override
	public void delete(Long[] objectPKs) throws SystemException {
	}

	@Override
	public List<Error> findObjects(Long[] objectPKs) throws SystemException {
		return null;
	}

	@Override
	public PagingWrapper<Error> findAllByFilter(int startNo, int offset,
			List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		return super.findAllWithPagingWrapper(startNo, offset, filter, order, null);
	}
}