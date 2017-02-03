package id.base.app.service.user;

import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.service.MaintenanceService;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.AppRole;
import id.base.app.valueobject.AppUser;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IUserService extends MaintenanceService<AppUser>{
	
	public AppUser findByUserName(String username) throws SystemException;
	
	public AppUser findByUserNameAndPassword(String username, String unencryptedPassword) throws SystemException;
	
	public AppUser findByUserNameAndActivationCode(String username, String activationCode) throws SystemException;
	
	public AppUser findByUserNameAndType(String username, int type) throws SystemException;
	
	public AppUser findByUserNameTypeAndPassword(String username, int type, String unencryptedPassword) throws SystemException;
	
	public AppUser findByIdFetchRoles(Long pkUser);
	
	public void saveNewRoles(Long userPK, List<AppRole> newRoles);

	public List<AppUser> findAllByFilter( List<SearchFilter> srcSearchFilterList,
			List<SearchOrder> searchOrders);

	public abstract void lockIdleUsers(Date paramDate) throws SystemException;

	public List<ErrorHolder> findUserId(String userName) throws SystemException;
	
	public PagingWrapper<AppUser> findExternalByFilter(int startNo, int offset,List<SearchFilter> filter,List<SearchOrder> order) throws SystemException ;
	
	public AppUser findExternalAppUserById(Long id) throws SystemException;
	
	public List<AppUser> findSupervisor(Long partnerPkParty, Long pkLocationStructure, Long pkTitleStructure, Long pkOrgUnit)
			throws SystemException;
	
	public Map<String, Object> validateFile(String fileName, Long pkPartner) throws SystemException;
	
	public Map<String, Object> processFile(String fileName, Long pkPartner) throws SystemException;
	
	public AppUser activateUserByActivationCode(String activationCode) throws SystemException;
	
	public Boolean validatePassword(Long pkAppUser, String unencryptedPassword) throws SystemException;
	
	public Boolean validateActivationCode(String userName, String activationCode) throws SystemException;
	
	public void activate(Long pkAppUser) throws SystemException;
	
	public void activate(String userName, String activationCode) throws SystemException;
	
	public void updateInitialWizard(Long pkAppUser, Integer initialWizardStep) throws SystemException;
	
	public Boolean isEmailAlreadyInUsed(String email) throws SystemException;
	
	public Boolean isPhoneAlreadyInUsed(String phoneNumber) throws SystemException;
	
}
