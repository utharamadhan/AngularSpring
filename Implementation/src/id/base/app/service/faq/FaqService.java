package id.base.app.service.faq;

import id.base.app.dao.faq.IFaqDAO;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.Faq;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FaqService implements IFaqService {

	@Autowired
	protected  IFaqDAO faqDAO;	
    
	@Override
	public Faq findById(Long id) throws SystemException {
		return faqDAO.findById(id);
	}

	@Override
	public void saveOrUpdate(Faq anObject) throws SystemException {
		faqDAO.saveOrUpdate(anObject);
	}
	
	@Override
	public PagingWrapper<Faq> findAllByFilter(int startNo, int offset, List<SearchFilter> filter, List<SearchOrder> order) throws SystemException {
		return faqDAO.findAllByFilter(startNo, offset, filter, order);
	}

	@Override
	public List<Faq> findAll(List<SearchFilter> filter,List<SearchOrder> order) throws SystemException {return null;}
	
	@Override
	public void delete(Long[] objectPKs) throws SystemException {}

	@Override
	public List<Faq> findObjects(Long[] objectPKs) throws SystemException {return null;}


}