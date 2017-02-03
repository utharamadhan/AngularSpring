package id.base.app.service.role;

import id.base.app.exception.SystemException;
import id.base.app.service.MaintenanceService;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.AppRole;

import java.util.List;

public interface IRoleService extends MaintenanceService<AppRole>{
	
	public List<AppRole> findInternalRoles();
	
	public List<AppRole> findExternalRoles();
	
	public List<AppRole> findAllRoleCodeAndName();
	
	public List<AppRole> findAllRole();
	
	public List<AppRole> findAllByFilter(List<SearchFilter> srcSearchFilterList, List<SearchOrder> searchOrders);
	
	public AppRole findByIdFetchUsers(Long pkAppRole);
	
	public void saveNewUsers(Long userRolePK, Long[] pkUsers);
	
	public List<AppRole> findAppRolesByAppUserId(Long pkAppUser) throws SystemException;
	
	public void saveOrUpdateMap(AppRole anObject, String method) throws SystemException;

}
