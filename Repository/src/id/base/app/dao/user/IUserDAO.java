package id.base.app.dao.user;

import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.AppUser;

import java.util.Date;
import java.util.List;

public interface IUserDAO {

	
	public abstract AppUser findAppUserByName(String userName) throws SystemException;
	
	public abstract AppUser findAppUserByNameAndPassword(String userName, String unencryptedPassword) throws SystemException;
	
	public abstract AppUser findByUserNameAndActivationCode(String userName, String activationCode) throws SystemException;
	
	public abstract AppUser findAppUserByNameAndType(String userName, int type) throws SystemException;

	public PagingWrapper<AppUser> findAllAppUserByFilter(int startNo, int offset,
			List<SearchFilter> searchFilter,List<SearchOrder> order) throws SystemException;

	public abstract void delete(Long[] objectPKs) throws SystemException;

	public abstract PagingWrapper<AppUser> findAllAppUser(int startNo,
			int offset) throws SystemException;

	public abstract AppUser findAppUserById(Long id) throws SystemException;

	public abstract void saveOrUpdate(AppUser anObject) throws SystemException;

	public Long createUser(AppUser userObject) throws SystemException;
	
	public abstract AppUser findAuthorizedMerchantByUserId(Long userId) throws SystemException;
	
	public List<AppUser> findNotifiyingPasswordUser(final int passwordExpiredInterval)throws SystemException;
	
	public List<AppUser> findAllByFilter(List<SearchFilter> searchFilters,List<SearchOrder> orders,String cacheRegion) throws SystemException;

	public AppUser findByIdFetchRoles(Long pkUser) throws SystemException;

	public abstract List<AppUser> findAppUserByRole(Long userRolePK, int userType) throws SystemException;

	public abstract void lockIdleUsers(Date paramDate) throws SystemException;
	
	public List<ErrorHolder> findUserId(String userName) throws SystemException;
	
	public AppUser findExternalAppUserById(Long id) throws SystemException;
	
	public abstract PagingWrapper<AppUser> findExternalAppUserByFilter(int startNo, int offset, List<SearchFilter> searchFilter, List<SearchOrder> order) throws SystemException;
	
	public AppUser findExternalAppUserByCode(String code) throws SystemException;
	
	public abstract Boolean checkUserIsSuperUser(Long pkAppUser) throws SystemException;
	
	public Date getCurrentSystemDate();
	
	public AppUser getAppUserByActivationCode(String activationCode) throws SystemException;
	
	public String getStoredPassword(Long pkAppUser) throws SystemException;
	
	public void updateInitialWizard(Long pkAppUser, Integer initialWizardStep) throws SystemException;
	
	public Boolean isEmailAlreadyInUsed(String email) throws SystemException;
	
	public Boolean isPhoneAlreadyInUsed(String phoneNumber) throws SystemException;
	
	public Boolean validateActivationCode(String userName, String activationCode) throws SystemException;
	
	public AppUser findAppUserByUserNameAndActivationCode(String userName, String activationCode) throws SystemException;
	
}
