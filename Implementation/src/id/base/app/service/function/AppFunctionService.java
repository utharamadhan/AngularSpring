package id.base.app.service.function;

import id.base.app.IAccessibilityConstant;
import id.base.app.dao.appfunction.IAppFunctionDAO;
import id.base.app.exception.SystemException;
import id.base.app.valueobject.AppFunction;
import id.base.app.valueobject.AppRole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AppFunctionService implements IAppFunctionService  {

	@Autowired
	private IAppFunctionDAO appFunctionDao;
	
	public List<AppFunction> findAllAppFunction() throws SystemException {
		return appFunctionDao.findAllAppFunction();
	}
	
	
	
	public List<AppFunction> findAppFunctionMenuByUserRole(Long userRole) throws SystemException{
		return appFunctionDao.findAppFunctionMenuByUserRole(userRole);
	}

	public void setAppFunctionDao(IAppFunctionDAO appFunctionDao) {
		this.appFunctionDao = appFunctionDao;
	}

	@Override
	public List<AppFunction> findAppFunctionByPermission(Long appRole)throws SystemException {
		 return appFunctionDao.findAppFunctionByPermission(appRole);
	}
	
	@Override
	public List<AppFunction> findAppFunctionMenuByUserRoles(List<AppRole> userRole)throws SystemException {
		List<AppFunction> list = new ArrayList<AppFunction>();
		for( AppRole a: userRole){
			list.addAll(appFunctionDao.findAppFunctionMenuByUserRole(a.getPkAppRole()));
		}
		return sortForScreen(list);
	}
	
	private List<AppFunction> sortForScreen(List<AppFunction> list) {
		if(list != null) {			
			Collections.sort(list, new Comparator<AppFunction>() {
				@Override
				public int compare(AppFunction obj1, AppFunction obj2) {
					if(obj1.getFkAppFunctionParent() != null && obj2.getFkAppFunctionParent() != null) {
						if(obj1.getFkAppFunctionParent() != IAccessibilityConstant.INT_INTERNAL_FUNCTION 
								&& obj2.getFkAppFunctionParent() != IAccessibilityConstant.INT_INTERNAL_FUNCTION) {
							if(obj1.getOrderNo() == null || obj1.getOrderNo() == null) {
								return 0;
							}else if(obj1.getOrderNo() != null && obj2.getOrderNo() != null) {
								return obj1.getOrderNo().compareTo(obj2.getOrderNo());
							}
						} else {
							if(obj1.getOrderNo() == null || obj1.getOrderNo() == null) {
								return 0;
							}else if(obj1.getOrderNo() != null && obj2.getOrderNo() != null) {
								return obj1.getOrderNo().compareTo(obj2.getOrderNo());
							}
						}
					}
					return 1;
				}
			});
		}
		return list;
	}

	@Override
	public List<AppFunction> findAppFunctionByPermissionList(List<AppRole> appRoles)throws SystemException {
		List<AppFunction> list = new ArrayList<AppFunction>();
		for( AppRole a: appRoles){
			list.addAll(appFunctionDao.findAppFunctionByPermission(a.getPkAppRole()));
		}
		return list;
	}
	
	public List<AppFunction> findAppFunctionByAppRole(Long pkAppRole) throws SystemException {
		return appFunctionDao.findAppFunctionByAppRole(pkAppRole);
	}
}
