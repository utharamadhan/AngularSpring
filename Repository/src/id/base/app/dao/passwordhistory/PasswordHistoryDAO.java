package id.base.app.dao.passwordhistory;

import id.base.app.AbstractHibernateDAO;
import id.base.app.exception.SystemException;
import id.base.app.util.dao.Operator;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.util.dao.SearchOrder.Sort;
import id.base.app.valueobject.PasswordHistory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class PasswordHistoryDAO extends AbstractHibernateDAO<PasswordHistory, Long> implements IPasswordHistoryDAO {

	@Override
	public void delete(PasswordHistory history) {
		getSession().delete(history);
	}

	@Override
	public List<PasswordHistory> getTotalPasswordByfkAppuser(Long fkAppUser)
			throws SystemException {
		if(fkAppUser==null)
		{
			return new ArrayList<PasswordHistory>();
		}
		else
		{
			List<SearchFilter> filters = new ArrayList<SearchFilter>();
			List<SearchOrder> order = new ArrayList<SearchOrder>();
			filters.add(new SearchFilter("fkAppUser", Operator.EQUALS,fkAppUser));
			order.add(new SearchOrder("recordedDate",Sort.ASC));
			List<PasswordHistory> passwdHistories=super.findAll(filters, order, null);
			if(passwdHistories == null || passwdHistories.size() == 0) {
				return new ArrayList<PasswordHistory>();
			}
			else
			{
				return passwdHistories;
			}
		}
	}

	@Override
	public boolean isPasswordExistsHistory(String password, Long fkAppUser)
			throws SystemException {
		if(!StringUtils.trimToEmpty(password).equalsIgnoreCase("")) {
			List<SearchFilter> filter = new ArrayList<SearchFilter>();
			filter.add(new SearchFilter("passwordValue", Operator.EQUALS, password));
			filter.add(new SearchFilter("fkAppUser", Operator.EQUALS,fkAppUser));
			List<PasswordHistory> passwdHistories = super.findAll(filter, null, null);
			
			if(passwdHistories != null && passwdHistories.size() > 0) 
				return true;
		}
		return false;
	}
	

	@Override
	public void saveOrUpdate(PasswordHistory anObject) throws SystemException {
		if(anObject.getPkAppUserPwdHist()==null || anObject.getPkAppUserPwdHist()==0)
		{
			anObject.setRecordedDate(super.getCurrentSystemDate());
			super.create(anObject);
		}	
		else
		{
			anObject.setRecordedDate(super.getCurrentSystemDate());
			super.update(anObject);
		}
		
	}

	@Override
	public List<PasswordHistory> getTotalPasswordByPasswordAndfkAppuser(
			String password, Long fkAppUser) throws SystemException {
		if(password==null)
		{
			return new ArrayList<PasswordHistory>();
		}
		else{
			List<SearchFilter> filters = new ArrayList<SearchFilter>();
			List<SearchOrder> order = new ArrayList<SearchOrder>();
			filters.add(new SearchFilter("passwordValue", Operator.EQUALS,password));
			filters.add(new SearchFilter("fkAppUser", Operator.EQUALS,fkAppUser));
			order.add(new SearchOrder("recordedDate",Sort.ASC));
			List<PasswordHistory> passwdHistories=super.findAll(filters, order, null);
			if(passwdHistories == null || passwdHistories.size() == 0) {
				return new ArrayList<PasswordHistory>();
			}
			else
			{
				return passwdHistories;
			}
		}
	}
}
