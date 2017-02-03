package id.base.app.service.simple;

import id.base.app.dao.simple.ISimpleObjectDAO;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.simple.SimpleObject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SimpleObjectService implements ISimpleObjectService {

	@Autowired
	private ISimpleObjectDAO simpleObjectDAO;
    
	public PagingWrapper<SimpleObject> findAll(int startNo, int offset) throws SystemException {
		return null;
	}

	public SimpleObject findById(Long id) throws SystemException {
		return simpleObjectDAO.findById(id);
	}

	public void saveOrUpdate(SimpleObject anObject) throws SystemException {
		simpleObjectDAO.saveOrUpdate(anObject);
	}

	public void delete(Long[] objectPKs) throws SystemException {
		simpleObjectDAO.delete(objectPKs);
	}

	public PagingWrapper<SimpleObject> findAllByFilter(int startNo, int offset, List<SearchFilter> filter, List<SearchOrder> order) throws SystemException {
		return simpleObjectDAO.findAllByFilter(startNo, offset, filter, order);
	}
	
	@Override
	public List<SimpleObject> findObjects(Long[] objectPKs) throws SystemException {
		List<SimpleObject> objects = new ArrayList<>();
		SimpleObject object = null;
		for(Long l:objectPKs){
			object = simpleObjectDAO.findById(l);
			objects.add(object);
		}
		return objects;
	}

	@Override
	public List<SimpleObject> findAll(List<SearchFilter> filter, List<SearchOrder> order) throws SystemException {
		return simpleObjectDAO.findAll(filter, order);
	}

}
