package id.base.app.dao.role;

import id.base.app.exception.SystemException;
import id.base.app.valueobject.UserGroupAccessNode;

import java.util.List;

public interface IAppRoleFunctionDAO {
	public List<UserGroupAccessNode> getAccessTree(long groupPK,int userType)
			throws SystemException;

	public void deleteAccessList(Long pkAppRole) throws SystemException;
	public void insertAccessList(Long pkAppRole, int[] pkAppFunction);
}
