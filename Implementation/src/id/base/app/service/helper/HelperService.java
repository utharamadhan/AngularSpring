package id.base.app.service.helper;

import id.base.app.dao.helper.IHelperDAO;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.Helper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HelperService implements IHelperService {

	@Autowired
	protected IHelperDAO helperDAO;	
    
    @Override
	public Helper findById(Long id) throws SystemException {
		return helperDAO.findById(id);
	}

	@Override
	public void saveOrUpdate(Helper anObject) throws SystemException {
		helperDAO.saveOrUpdate(anObject);
	}
	
	@Override
	public PagingWrapper<Helper> findAllByFilter(int startNo, int offset, List<SearchFilter> filter, List<SearchOrder> order) throws SystemException {
		return helperDAO.findAllByFilter(startNo, offset, filter, order);
	}

	@Override
	public List<Helper> findAll(List<SearchFilter> filter,List<SearchOrder> order) throws SystemException {return null;}
	
	@Override
	public void delete(Long[] objectPKs) throws SystemException {}

	@Override
	public List<Helper> findObjects(Long[] objectPKs) throws SystemException {return null;}


}