package id.base.app.dao.appfunction;

import id.base.app.exception.SystemException;
import id.base.app.valueobject.AppFunction;

import java.util.List;

public interface IAppFunctionDAO {
	public List<AppFunction> findAppFunctionByPermission(Long appRole) throws SystemException;
	public abstract List<AppFunction> findAllAppFunction() throws SystemException;
	
	public abstract List<AppFunction> findAppFunctionMenuByUserRole(Long userRole) throws SystemException ;

	public abstract List<AppFunction> findAppFunctionByUserRole(Long userRole) throws SystemException; 
	
	public abstract List<AppFunction> findAppFunctionByAppRole(Long pkAppRole) throws SystemException;

}