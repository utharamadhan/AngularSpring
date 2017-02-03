package id.base.app.service.role;

import id.base.app.exception.SystemException;
import id.base.app.valueobject.UserGroupAccessNode;

import java.util.List;

public interface IAppRoleFunctionService {
	public List<UserGroupAccessNode> getAccessTree(Long pkAppRole,Integer userType) throws SystemException;
	public void updateAccessibilities(Long pkAppRole, int pkAppFunction[]);
}
