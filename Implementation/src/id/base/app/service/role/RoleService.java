package id.base.app.service.role;

import id.base.app.dao.role.IAppRoleDAO;
import id.base.app.dao.role.IAppRoleFunctionDAO;
import id.base.app.dao.user.IUserDAO;
import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.service.MaintenanceService;
import id.base.app.util.dao.Operator;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.util.dao.SearchOrder.Sort;
import id.base.app.valueobject.AppRole;
import id.base.app.valueobject.AppUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleService implements MaintenanceService<AppRole>,IRoleService {

	@Autowired
	private ResourceBundleMessageSource messageSource;
	@Autowired
	protected IAppRoleDAO roleDAO;
	@Autowired
	protected IUserDAO userDAO;
	@Autowired
	protected  IAppRoleFunctionDAO roleFunctionDAO;
	
	public RoleService(){};
    
    public RoleService(IAppRoleDAO roleDAO, IUserDAO userDAO){
    	this.roleDAO = roleDAO;
    	this.userDAO = userDAO;
    }
	
	public void delete(Long[] objectPKs) throws SystemException {
		roleDAO.delete(objectPKs);
	}

	public PagingWrapper<AppRole> findAll(int startNo, int offset)
			throws SystemException {
		return roleDAO.findAllAppRole(startNo, offset);
	}
	
	public List<AppRole> findAll(){
		return roleDAO.findAllRole();
	}

	public AppRole findById(Long id) throws SystemException {
		return roleDAO.findAppRoleById(id);
	}

	public void saveOrUpdate(AppRole anObject) throws SystemException {
		roleDAO.saveOrUpdate(anObject);
		roleFunctionDAO.deleteAccessList(anObject.getPkAppRole());
		roleFunctionDAO.insertAccessList(anObject.getPkAppRole(), anObject.getPkAppFunctionList()==null?new int[0]:anObject.getPkAppFunctionList());
	}
	
	
	public List<ErrorHolder> validateCodeAndName(AppRole role){
		List<ErrorHolder> errorHolders = new ArrayList<ErrorHolder>();
		AppRole roleByCode = roleDAO.findAppRoleByCodeIgnoreCase(role.getCode());
		AppRole roleByName = roleDAO.findAppRoleByNameIgnoreCase(role.getName());
		if(role.getPkAppRole() != null){
			if(roleByCode != null && !roleByCode.getPkAppRole().equals(role.getPkAppRole())){
				errorHolders.add(new ErrorHolder(messageSource.getMessage("error.message.user.role.code.already.exist", null, Locale.ENGLISH)));
			}
			if(roleByName != null && !roleByName.getPkAppRole().equals(role.getPkAppRole())){
				errorHolders.add(new ErrorHolder(messageSource.getMessage("error.message.user.role.name.already.exist", null, Locale.ENGLISH)));
			}
		}
		return errorHolders;
	}
	
	public void saveOrUpdateMap(AppRole anObject, String method) throws SystemException{
		List<ErrorHolder> errorHolders = new ArrayList<ErrorHolder>();
		if(anObject.getPkAppRole()!= null){
			AppRole check = findByIdFetchUsers(anObject.getPkAppRole());
			if(check.getUsers().size()>0 && method.equals(AppRole.OP_REMOVE)){
				errorHolders.add(new ErrorHolder(messageSource.getMessage("error.message.user.role.already.inused", new String[]{check.getCode()}, Locale.ENGLISH)));
				throw new SystemException(errorHolders);
			}else{
				if(method.equals(AppRole.OP_REMOVE)){
					roleDAO.saveOrUpdate(anObject);
				}else if(method.equals(AppRole.OP_EDIT)){
					AppRole role = findById(anObject.getPkAppRole());
					if(!role.getType().equals(anObject.getType())){
						roleFunctionDAO.deleteAccessList(anObject.getPkAppRole());
					}
					if(check.getUsers().size()>0 && !role.getCode().equals(anObject.getCode())){
						errorHolders.add(new ErrorHolder(messageSource.getMessage("error.message.user.role.code.cannot.change", new String[]{check.getCode()}, Locale.ENGLISH)));
						throw new SystemException(errorHolders);
					}else if(check.getUsers().size()>0 && !role.getType().equals(anObject.getType())){
						errorHolders.add(new ErrorHolder(messageSource.getMessage("error.message.user.role.type.cannot.change", new String[]{check.getCode()}, Locale.ENGLISH)));
						throw new SystemException(errorHolders);
					}else{
						errorHolders.addAll(validateCodeAndName(anObject));
						if(errorHolders.size()>0){
							throw new SystemException(errorHolders);
						}else{
							role.setCode(anObject.getCode());
							role.setName(anObject.getName());
							role.setType(anObject.getType());
							roleDAO.saveOrUpdate(role);
						}
					}
				}
			}
		}else{
			errorHolders.addAll(validateCodeAndName(anObject));
			if(errorHolders.size()>0){
				throw new SystemException(errorHolders);
			}else{
				roleDAO.saveOrUpdate(anObject);
			}
		}
	}
	
	public AppRole findAppRoleByName(String roleName) throws SystemException {
        return roleDAO.findAppRoleByName(roleName);
    }
	
	public AppRole findAppRoleByCode(String roleCode) throws SystemException {
        return roleDAO.findAppRoleByCode(roleCode);
    }	

	public List<AppRole> findInternalRoles() {
		return roleDAO.findInternalRoles();
	}

	public List<AppRole> findExternalRoles() {
		return roleDAO.findExternalRoles();
	}
	
	public List<AppRole> findAllRoleCodeAndName() {
		return roleDAO.findAllRoleCodeAndName();
	}

	public PagingWrapper<AppRole> findAllByFilter(int startNo, int offset,
			List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		if (order == null) {
			order = new ArrayList<SearchOrder>();
			order.add(new SearchOrder(AppRole.TYPE, Sort.ASC));
			order.add(new SearchOrder(AppRole.NAME, Sort.ASC));
		}
		return roleDAO.findAllAppRole(startNo, offset, filter, order);
	}

	@Override
	public List<AppRole> findObjects(Long[] objectPKs) throws SystemException {
		List<AppRole> appRoles=new ArrayList<AppRole>();
		AppRole role=null;
		for(Long l:objectPKs){
			role=roleDAO.findAppRoleById(l);
			appRoles.add(role);
		}
		return appRoles;
	}
	
	@Override
	public List<AppRole> findAllRole() {
		return roleDAO.findAllRole();
	}
	
	public List<AppRole> findAllByFilter(
			List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		if (order == null) {
			order = new ArrayList<SearchOrder>();
			order.add(new SearchOrder(AppRole.TYPE, Sort.ASC));
			order.add(new SearchOrder(AppRole.NAME, Sort.ASC));
		}				
		return roleDAO.findAllAppRole(filter, order);
	}
	
	public AppRole findByIdFetchUsers(Long pkAppRole) {
		return roleDAO.findAppRoleByIdFetchUser(pkAppRole);
	}

	@Override
	public void saveNewUsers(Long userRolePK, Long[] pkUsers) {
		AppRole role = roleDAO.findAppRoleById(userRolePK);
		// userType and roleType are interchangeable
		List<AppUser> users = userDAO.findAppUserByRole(userRolePK, role.getType());
		
		
		for (AppUser appUser : users) {
			 appUser.getAppRoles().remove(role);
		}
		
		List<SearchFilter> searchFilterList = new ArrayList<SearchFilter>();
		searchFilterList.add(new SearchFilter(AppUser.PK_APP_USER,
				Operator.IN_ARRAY, pkUsers));
		
		List<AppUser> newUsers = userDAO.findAllByFilter(searchFilterList, null, null);
		
		for (AppUser user : newUsers) {
			user.getAppRoles().add(role);
		}
		
	}

	@Override
	public List<AppRole> findAll(List<SearchFilter> filter,
			List<SearchOrder> order) throws SystemException {
		return roleDAO.findAllAppRole(filter, order);
	}

	@Override
	public List<AppRole> findAppRolesByAppUserId(Long pkAppUser)
			throws SystemException {
		return roleDAO.findAppRolesByAppUserId(pkAppUser);
	}
	
}
