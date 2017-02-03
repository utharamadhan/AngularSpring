package id.base.app.dao.role;

import id.base.app.AbstractHibernateDAO;
import id.base.app.SystemConstant;
import id.base.app.exception.SystemException;
import id.base.app.valueobject.AppFunction;
import id.base.app.valueobject.AppRole;
import id.base.app.valueobject.AppRoleFunction;
import id.base.app.valueobject.UserGroupAccessNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AppRoleFunctionDAO extends AbstractHibernateDAO<AppRoleFunction, Long> implements IAppRoleFunctionDAO{

	public List<UserGroupAccessNode> getAccessTree(long groupPK,int userType)
			throws SystemException {
		List<AppFunction> ret;
		List<AppRoleFunction> ret2;
		List<AppFunction> ret3 = new ArrayList<AppFunction>();
		Criteria criteria = getSession().createCriteria(AppFunction.class);
		criteria.add(Restrictions.eq("userType", userType));
		criteria.add(Restrictions.eq("isActive",Boolean.TRUE));
		if(userType==SystemConstant.USER_TYPE_EXTERNAL){
			criteria.addOrder(Order.desc("pkAppFunction"));
		}else{
			criteria.addOrder(Order.asc("pkAppFunction"));
		}
		Criteria criteria2 = getSession().createCriteria(AppRoleFunction.class);
		Criteria subCriteria = criteria2.createAlias("appRole", "appRole");
		subCriteria.add(Restrictions.eq("appRole.pkAppRole", groupPK));
		
		ret = criteria.list();
		ret2 = criteria2.list();
		if(ret2.size()>0){
			for(AppRoleFunction appRoleFunction : ret2){
				ret3.add(appRoleFunction.getAppFunction());
			}
		}
		
		List<UserGroupAccessNode> nodes = new LinkedList<UserGroupAccessNode>();
        if(ret.size() > 0){
        	for (AppFunction appFunction : ret) {
        		UserGroupAccessNode node = new UserGroupAccessNode();
        		node.setChildPK(appFunction.getPkAppFunction());
        		if(appFunction.getFkAppFunctionParent()!=null){
        			node.setParentPK(appFunction.getFkAppFunctionParent());
        		}else{
        			node.setParentPK(0);
        		}
        		node.setName(appFunction.getName());
        		if(ret3.contains(appFunction)){
        			node.setChecked(true);
        		}else{
        			node.setChecked(false);
        		}
        		nodes.add(node);
			}
        	return nodes;
        }else{
        	return null;
        }
	}

	public void deleteAccessList(Long pkAppRole) throws SystemException {
		List<AppRoleFunction> ret2;
		Criteria criteria2 = getSession().createCriteria(AppRoleFunction.class);
		Criteria subCriteria = criteria2.createAlias("appRole", "appRole");
		subCriteria.add(Restrictions.eq("appRole.pkAppRole", pkAppRole));
		ret2 = criteria2.list();
		super.delete(ret2);
    }

	public void insertAccessList(Long pkAppRole, int[] pkAppFunction) {
		List<AppRoleFunction> objList = new ArrayList<AppRoleFunction>();
		for(int i=0;i<pkAppFunction.length;i++){
			AppRoleFunction obj = new AppRoleFunction();
			AppRole role = new AppRole();
			role.setPkAppRole(pkAppRole);
			obj.setAppRole(role);
			AppFunction appFunction = new AppFunction();
			appFunction.setPkAppFunction(Long.valueOf(pkAppFunction[i]));
			obj.setAppFunction(appFunction);
			objList.add(obj);
		}
		super.create(objList);
	}

}
