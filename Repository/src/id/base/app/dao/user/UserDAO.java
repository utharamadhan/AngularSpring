package id.base.app.dao.user;

import id.base.app.AbstractHibernateDAO;
import id.base.app.SystemConstant;
import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.DateTimeFunction;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.AppUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends AbstractHibernateDAO<AppUser, Long> implements IUserDAO{
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	public void delete(Long[] objectPKs) throws SystemException {
		List<AppUser> objectList = new ArrayList<AppUser>();
		AppUser object =null;
		for(int i=0;i<objectPKs.length;i++){
			object = findAppUserById(objectPKs[i]);
			objectList.add(object);
		}
		super.delete(objectList);
	}
	
	public PagingWrapper<AppUser> findAllAppUser(int startNo, int offset)
			throws SystemException {
		return super.findAllWithPagingWrapper(startNo, offset);
	}

	public AppUser findAppUserById(final Long appUserId) throws SystemException {
		if(appUserId!=null){
			return super.findByPK(appUserId);
		}else{
			return new AppUser();
		}
	}

	public AppUser findAppUserByName(final String userName) throws SystemException {
			return (AppUser) getSession().createCriteria(AppUser.class).add(
					Restrictions.eq(AppUser.USER_NAME, userName).ignoreCase())
					.setFetchMode(AppUser.APP_ROLES, FetchMode.EAGER)
					.uniqueResult();
	}
	
	public AppUser findByUserNameAndActivationCode(final String userName, final String activationCode) throws SystemException {
		return (AppUser) getSession().createCriteria(AppUser.class).add(
				Restrictions.eq(AppUser.USER_NAME, userName).ignoreCase())
				.add(Restrictions.eq(AppUser.ACTIVATION_CODE, activationCode))
				.setFetchMode(AppUser.APP_ROLES, FetchMode.JOIN)
				.uniqueResult();
	}
	
	public AppUser findAppUserByNameAndPassword(final String userName, final String encryptedPassword) throws SystemException {
		return (AppUser) getSession().createCriteria(AppUser.class).add(
				Restrictions.eq(AppUser.USER_NAME, userName).ignoreCase())
				.add(Restrictions.eq(AppUser.PASSWORD, encryptedPassword))
				.setFetchMode(AppUser.APP_ROLES, FetchMode.EAGER)
				.uniqueResult();
}
	
	public AppUser findAppUserByNameAndType(final String userName, final int type) throws SystemException {
		return (AppUser) getSession().createCriteria(AppUser.class).add(
				Restrictions.eq(AppUser.USER_NAME, userName).ignoreCase()).add(Restrictions.eq(AppUser.USER_TYPE, type))
				.setFetchMode(AppUser.APP_ROLES, FetchMode.EAGER)
				.uniqueResult();
	}

	public void saveOrUpdate(AppUser anObject) throws SystemException {
    	super.update(anObject);
	}
	
	public Long createUser(AppUser userObject)throws SystemException{
		return (Long)super.getSession().save(userObject);
	}

	public PagingWrapper<AppUser> findAllAppUserByFilter(int startNo,
			int offset, List<SearchFilter> searchFilter, List<SearchOrder> order)
			throws SystemException {
		return super.findAllWithPagingWrapper(startNo, offset, searchFilter, order, null);
	}
	
	@Override
	public PagingWrapper<AppUser> findExternalAppUserByFilter(int startNo,
			int offset, List<SearchFilter> searchFilter, List<SearchOrder> searchOrder)
			throws SystemException {
		return null;
	}
	
	
	public AppUser findExternalAppUserById(Long id) throws SystemException {
		return null;
	}
	
	@Override
	public AppUser findExternalAppUserByCode(String code) throws SystemException {
		return null;
	}
	
	public List<AppUser> findauthorizedUserByMerchant(final Long merchant) throws SystemException {
				return getSession()
						.createCriteria(AppUser.class)
						.createAlias("authorizedMerchants",
								"authorizedMerchant")
					     .add(Restrictions.eq(AppUser.STATUS, SystemConstant.UserStatus.ACTIVE))
						.add(Restrictions.eq("authorizedMerchant.merchantPK",
								merchant)).list();
	}
	
	public AppUser findAuthorizedMerchantByUserId(Long id) throws SystemException {
		AppUser appUser = super.findByPK(id);
		/*for (Merchant merchant : appUser.getAuthorizedMerchants()) {
			 merchant.getName();
		}*/
		return appUser;
	}

	@SuppressWarnings("unchecked")
	public List<AppUser> findNotifiyingPasswordUser(final int passwordExpiredInterval)throws SystemException{		
	
					return getSession()
							.createCriteria(AppUser.class)
							.add(Restrictions.between("pwdExpiredDate",
									DateTimeFunction.getCurrentDate(), 
									DateTimeFunction.addDate(DateTimeFunction.truncateDate(DateTimeFunction.getCurrentDate()), passwordExpiredInterval, Calendar.DATE)))
							.list();
	}

	@Override
	public List<AppUser> findAllByFilter(List<SearchFilter> searchFilters,
			List<SearchOrder> orders, String cacheRegion)
			throws SystemException {
		return super.findAll(searchFilters, orders, null);
	}

	@Override
	public AppUser findByIdFetchRoles(Long pkUser) throws SystemException {
		Criteria userCriteria = getSession().createCriteria(AppUser.class)
									.setFetchMode(AppUser.APP_ROLES, FetchMode.JOIN);
		userCriteria.add(Restrictions.eq(AppUser.PK_APP_USER, pkUser));
		
		AppUser result = (AppUser) userCriteria.uniqueResult();
		
		return result;
	}

	@Override
	public List<AppUser> findAppUserByRole(final Long userRolePK, final int userType)
			throws SystemException {

				return getSession()
						.createCriteria(AppUser.class)
						.createAlias("appRoles",
								"appRole")
					     .add(Restrictions.eq(AppUser.STATUS, SystemConstant.UserStatus.ACTIVE))
					     .add(Restrictions.eq(AppUser.USER_TYPE, userType))
						.add(Restrictions.eq("appRole.pkAppRole",
								userRolePK)).list();
	}
	
	@Override
	public void lockIdleUsers(Date paramDate) throws SystemException {
		Query query = getSession().createQuery("update AppUser set lock = 1 where lastLogInDate < :paramDate").setParameter("paramDate", paramDate);
		query.executeUpdate();
	}

	@Override
	public List<ErrorHolder> findUserId(String userName) throws SystemException {
		List<ErrorHolder> errorHolders = new ArrayList<ErrorHolder>();
		StringBuilder select = new StringBuilder();
		select.append("SELECT * FROM V_AOL_USER WHERE UPPER(LOGIN_ID) = '").append(userName.trim().toUpperCase()).append("'");
		Query querySelect = getSession().createSQLQuery(select.toString());
		
		StringBuilder select2 = new StringBuilder();
		select2.append("SELECT * FROM APP_USER WHERE UPPER(USER_NAME) = '").append(userName.trim().toUpperCase()).append("'");
		Query querySelect2 = getSession().createSQLQuery(select2.toString());
		if(querySelect.list().isEmpty()){
			errorHolders.add(new ErrorHolder(messageSource.getMessage("user.id.not.found", null, Locale.ENGLISH)));
		}else if(!querySelect2.list().isEmpty()){
			errorHolders.add(new ErrorHolder(messageSource.getMessage("user.id.already.register", null, Locale.ENGLISH)));
		}
		return errorHolders;
	}
	
	@Override
	public Boolean checkUserIsSuperUser(Long pkAppUser) throws SystemException {
		Criteria crit = getSession().createCriteria(AppUser.class);
		crit.add(Restrictions.eq(AppUser.SUPER_USER, Boolean.TRUE));
		crit.add(Restrictions.eq(AppUser.PK_APP_USER, pkAppUser));
		crit.setProjection(Projections.rowCount());
		Long cnt = (Long) crit.uniqueResult();
		return cnt>0?true:false;
	}


	@Override
	public Date getCurrentSystemDate(){
		return super.getCurrentSystemDate();
	}

	@Override
	public AppUser getAppUserByActivationCode(String activationCode) throws SystemException {
		Criteria crit = getSession().createCriteria(domainClass);
			crit.add(Restrictions.eq("activationCode", activationCode));
			crit.setMaxResults(1);
		return (AppUser) crit.uniqueResult();
	}

	@Override
	public String getStoredPassword(Long pkAppUser) throws SystemException {
		Criteria crit = getSession().createCriteria(domainClass);
			crit.add(Restrictions.eq("pkAppUser", pkAppUser));
			crit.setMaxResults(1);
			crit.setProjection(Projections.projectionList().add(Projections.property("password")));
			crit.setResultTransformer(new ResultTransformer() {
				@Override
				public Object transformTuple(Object[] tuple, String[] aliases) {
					try{
						return tuple[0].toString();
					}catch(Exception e){
						LOGGER.error(e.getMessage(), e);
					}
					return null;
				}
				
				@Override
				public List transformList(List collection) {
					return collection;
				}
			});
		return crit.uniqueResult().toString();
	}

	@Override
	public void updateInitialWizard(Long pkAppUser, Integer initialWizardStep) throws SystemException {
		AppUser user = findAppUserById(pkAppUser);
			user.setInitialWizardStep(initialWizardStep);
		saveOrUpdate(user);
	}

	@Override
	public Boolean isEmailAlreadyInUsed(String email) throws SystemException {
		Criteria crit = getSession().createCriteria(domainClass);
			crit.add(Restrictions.eq(AppUser.EMAIL, email).ignoreCase());
			crit.setProjection(Projections.rowCount());
		Long countRow = (Long) crit.uniqueResult();
		return countRow != null && countRow > 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Boolean isPhoneAlreadyInUsed(String phoneNumber) throws SystemException {
		//check whether phone number already in used as username for another account
		Criteria crit = getSession().createCriteria(domainClass);
			crit.add(Restrictions.eq(AppUser.USER_NAME, phoneNumber));
			crit.setProjection(Projections.rowCount());
		Long countRow = (Long) crit.uniqueResult();
		return countRow != null && countRow > 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Boolean validateActivationCode(String userName, String activationCode) throws SystemException {
		Criteria crit = getSession().createCriteria(domainClass);
			crit.add(Restrictions.eq(AppUser.USER_NAME, userName));
			crit.add(Restrictions.eq(AppUser.ACTIVATION_CODE, activationCode));
			crit.setProjection(Projections.rowCount());
		Long countRow = (Long) crit.uniqueResult();
		return countRow != null && countRow > 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public AppUser findAppUserByUserNameAndActivationCode(String userName, String activationCode) throws SystemException {
		Criteria crit = getSession().createCriteria(domainClass);
			crit.add(Restrictions.eq(AppUser.USER_NAME, userName));
			crit.add(Restrictions.eq(AppUser.ACTIVATION_CODE, activationCode));
			crit.setMaxResults(1);
		return (AppUser) crit.uniqueResult();
	}

}
