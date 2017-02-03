package id.base.app.service.error;

import id.base.app.dao.error.IErrorDAO;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.service.MaintenanceService;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.Error;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ErrorService implements MaintenanceService<Error>, IErrorService {

	@Autowired
	private IErrorDAO errorDAO;
	
	@Override
	public Error findById(Long id) throws SystemException {
		return errorDAO.findById(id);
	}

	@Override
	public void saveOrUpdate(Error anObject) throws SystemException {
		errorDAO.saveOrUpdate(anObject);
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
		return errorDAO.findAllByFilter(startNo, offset, filter, order);
	}

	@Override
	public List<Error> findAll(List<SearchFilter> filter,
			List<SearchOrder> order) throws SystemException {
		return errorDAO.findAll(filter, order);
	}
}