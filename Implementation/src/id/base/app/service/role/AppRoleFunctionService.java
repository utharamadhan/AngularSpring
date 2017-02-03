package id.base.app.service.role;

import id.base.app.dao.role.IAppRoleFunctionDAO;
import id.base.app.exception.SystemException;
import id.base.app.valueobject.UserGroupAccessNode;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AppRoleFunctionService implements IAppRoleFunctionService {

	@Autowired
	@Qualifier(value="appRoleFunctionDAO")
	protected  IAppRoleFunctionDAO roleFunctionDAO;
	
	static Logger logger = LoggerFactory.getLogger(AppRoleFunctionService.class);
	
	public AppRoleFunctionService(){};
    
    public AppRoleFunctionService(IAppRoleFunctionDAO roleFunctionDAO){
    	this.roleFunctionDAO = roleFunctionDAO;
    }

	public List<UserGroupAccessNode> getAccessTree(Long pkAppRole,
			Integer userType) throws SystemException {
		logger.info(" getAccessTree (pkAppRole[{}],userType[{}])  ", pkAppRole,
				userType);
		List<UserGroupAccessNode> accessNodes = this.roleFunctionDAO
				.getAccessTree(pkAppRole, userType);
		return accessNodes;
	}
	
	public void updateAccessibilities(Long pkAppRole, int[] pkAppFunction)
			throws SystemException {
		if (pkAppFunction.length == 0) {
			this.roleFunctionDAO.deleteAccessList(pkAppRole);
		} else if (pkAppFunction.length > 0) {
			this.roleFunctionDAO.deleteAccessList(pkAppRole);
			this.roleFunctionDAO.insertAccessList(pkAppRole, pkAppFunction);
		}
	}

}
