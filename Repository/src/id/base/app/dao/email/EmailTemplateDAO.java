package id.base.app.dao.email;

import id.base.app.AbstractHibernateDAO;
import id.base.app.SystemConstant;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.email.EmailTemplate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EmailTemplateDAO extends AbstractHibernateDAO<EmailTemplate, Long> implements IEmailTemplateDAO {

	@Override
	public EmailTemplate findById(Long id) throws SystemException {
		return super.findByPK(id);
	}

	@Override
	public void saveOrUpdate(EmailTemplate anObject) throws SystemException {
		if(anObject.getPkEmailTemplate()==null){			
			super.create(anObject);
		} else {
			super.update(anObject);
		}
	}

	@Override
	public void delete(Long[] objectPKs) throws SystemException {
		for(Long objectPK : objectPKs){
			EmailTemplate obj = findById(objectPK);
				obj.setStatus(SystemConstant.ValidFlag.INVALID);
			super.update(obj);
		}
	}

	@Override
	public List<EmailTemplate> findObjects(Long[] objectPKs) throws SystemException {
		return null;
	}

	@Override
	public PagingWrapper<EmailTemplate> findAllByFilter(int startNo, int offset, List<SearchFilter> filter, List<SearchOrder> order) throws SystemException {
		return super.findAllWithPagingWrapper(startNo, offset, filter, order, null);
	}

	@Override
	public EmailTemplate findByCode(String templateCode) throws SystemException {
		Criteria crit = getSession().createCriteria(domainClass);
			crit.add(Restrictions.eq("code", templateCode));
			crit.add(Restrictions.eq("status", SystemConstant.ValidFlag.VALID));
			crit.setMaxResults(1);
		return (EmailTemplate) crit.uniqueResult();
	}
	
}