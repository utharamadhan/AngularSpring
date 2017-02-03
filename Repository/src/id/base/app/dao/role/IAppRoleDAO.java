package id.base.app.dao.role;

import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.AppRole;

import java.util.List;

public interface IAppRoleDAO {
	public abstract AppRole findAppRoleByCode(String roleCode)
			throws SystemException;

	public abstract AppRole findAppRoleByName(String roleName)
			throws SystemException;
	
	public AppRole findAppRoleByCodeIgnoreCase(String code) throws SystemException;
	public AppRole findAppRoleByNameIgnoreCase(String name) throws SystemException;

	public abstract void delete(Long[] objectPKs) throws SystemException;

	public abstract PagingWrapper<AppRole> findAllAppRole(
			int startNo, int offset) throws SystemException;

	PagingWrapper<AppRole> findAllAppRole(int startNo, int offset,
			List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException;


	public abstract AppRole findAppRoleById(Long id)
			throws SystemException;

	public abstract void saveOrUpdate(AppRole anObject)
			throws SystemException;
	public List<AppRole> findAllRole() throws SystemException;

	public List<AppRole> findExternalRoles() throws SystemException;
	
	public List<AppRole> findInternalRoles() throws SystemException;
	
	public List<AppRole> findAllRoleCodeAndName() throws SystemException;

	public abstract List<AppRole> findAllAppRole(List<SearchFilter> filter,
			List<SearchOrder> order) throws SystemException;

	public abstract AppRole findAppRoleByIdFetchUser(Long pkAppRole) throws SystemException;
	
	public List<AppRole> findAppRolesByAppUserId(Long pkAppUser)
			throws SystemException;
}
