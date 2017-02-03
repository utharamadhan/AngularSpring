package id.base.app.dao.login;

import id.base.app.AbstractHibernateDAO;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.DateTimeFunction;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.RuntimeUserLogin;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDAO extends AbstractHibernateDAO<RuntimeUserLogin,Long> implements ILoginDAO {

	public void delete(Long[] objectPKs) throws SystemException {
		List<RuntimeUserLogin> objectList = new ArrayList<RuntimeUserLogin>();
		RuntimeUserLogin object =null;
		for(int i=0;i<objectPKs.length;i++){
			object= new RuntimeUserLogin();
			object = findRuntimeUserLoginById(objectPKs[i]);
			if(object!=null)objectList.add(object);

		}
		super.delete(objectList);
	}

	public PagingWrapper<RuntimeUserLogin> findAllRuntimeUserLogin(int startNo,
			int offset) throws SystemException {
		return super.findAllWithPagingWrapper(startNo, offset);
	}

	public RuntimeUserLogin findRuntimeUserLoginById(Long id)
			throws SystemException {
		return super.findByPK(new Long(id));
	}

	public void saveOrUpdate(RuntimeUserLogin anObject) throws SystemException {
		super.saveUpdate(anObject);
	}

	public PagingWrapper<RuntimeUserLogin> findAllRuntimeUserLoginByFilter(
			int startNo, int offset, List<SearchFilter> filter,
			List<SearchOrder> searchOrder) throws SystemException {
		return super.findAllWithPagingWrapper(startNo, offset, filter, searchOrder, null);
	}
	
	@Override
	public void deleteAll() {
		Query query = getSession().createQuery("delete from RuntimeUserLogin");
		query.executeUpdate();
	}
	
	@Override
	public void deleteExpiredUsers() {
		LOGGER.debug("deleting expired users...");
		Query query = getSession().createQuery("delete from RuntimeUserLogin where assignedLogoutTime < :currentDate").setParameter("currentDate", DateTimeFunction.getCurrentDate());
		query.executeUpdate();
	}
	
	@Override
	public RuntimeUserLogin findByAccessInfoId(String accessInfoId)
			throws SystemException {
		RuntimeUserLogin userLogin = (RuntimeUserLogin) getSession().createCriteria(RuntimeUserLogin.class).add(
				Restrictions.eq(RuntimeUserLogin.ACCESS_INFO, accessInfoId))
				.uniqueResult();
		return userLogin;
	}
	
	@Override
	public RuntimeUserLogin findByUserName(String userName)
			throws SystemException {
		RuntimeUserLogin userLogin = (RuntimeUserLogin) getSession().createCriteria(RuntimeUserLogin.class).add(
				Restrictions.eq(RuntimeUserLogin.USER_NAME, userName))
				.uniqueResult();
		return userLogin;
	}
}
