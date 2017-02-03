package id.base.app.dao.appfunction;

import id.base.app.AbstractHibernateDAO;
import id.base.app.IAccessibilityConstant;
import id.base.app.exception.SystemException;
import id.base.app.valueobject.AppFunction;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AppFunctionDAO extends AbstractHibernateDAO<AppFunction, Long> implements IAppFunctionDAO {
	public List<AppFunction> findAllAppFunction() throws SystemException {
		return (List<AppFunction>)super.findAll();
	}
	
	@SuppressWarnings("unchecked")
	public List<AppFunction> findAppFunctionMenuByUserRole(Long appRole) {
		return getSession()
				.createCriteria(AppFunction.class)
				.createAlias("appRoleFunctions", "apr")
				.add(Restrictions.eq("apr.appRole.pkAppRole", appRole))
				.add(Restrictions.eq(AppFunction.IS_ACTIVE, Boolean.TRUE))
				.add(Restrictions.in(AppFunction.FK_APP_FUNCTION_PARENT, IAccessibilityConstant.MENU_LIST)).list();
	}
	
	public List<AppFunction> findAppFunctionByPermission(Long appRole) {
		return getSession()
				.createCriteria(AppFunction.class)
				.createAlias("appRoleFunctions", "apr")
				.add(Restrictions.eq("apr.appRole.pkAppRole", appRole))
				.add(Restrictions.eq("isActive", true))
				.addOrder(Order.asc(AppFunction.PK_APP_FUNCTION)).list();
	}
	
	
	@Override
	public List<AppFunction> findAppFunctionByUserRole(Long userRole)
			throws SystemException {
		Criteria criteria = getSession().createCriteria(AppFunction.class);
		criteria.createAlias("appRoleFunctions", "apr");
		criteria.add(Restrictions.eq("apr.appRole.pkAppRole", userRole));
		criteria.add(Restrictions.eq(AppFunction.IS_ACTIVE, Boolean.TRUE));
		return criteria.list();
	}
	
	public List<AppFunction> findAppFunctionByAppRole(Long pkAppRole) throws SystemException {
		Query query = getSession().createQuery(" from AppFunction where fkAppFunctionParent is null");
		List<AppFunction> appFunctionList = query.list();
		for (AppFunction parent:appFunctionList){
			checkIsExistInUserRole(parent,pkAppRole);
			
			query = getSession().createQuery(" from AppFunction where fkAppFunctionParent = ?");
			query.setParameter(0, Long.valueOf(String.valueOf(parent.getPkAppFunction())));
			List<AppFunction> child1List = query.list();
			for (AppFunction child:child1List){
				checkIsExistInUserRole(child,pkAppRole);
				
				query = getSession().createQuery(" from AppFunction where fkAppFunctionParent = ?");
				query.setParameter(0, Long.valueOf(String.valueOf(child.getPkAppFunction())));
				List<AppFunction> child2List = query.list();
				child.setChildList(child2List);
				for (AppFunction child2:child2List){
					checkIsExistInUserRole(child2,pkAppRole);
				}
			}
			parent.setChildList(child1List);
		}
		return appFunctionList;
	}
	
	public void checkIsExistInUserRole(AppFunction function, Long pkAppRole) {
		if (pkAppRole != null){
			List<AppFunction> appRoleList = findAppFunctionByUserRole(pkAppRole);
			for (AppFunction appRole:appRoleList){
				if (function.getPkAppFunction().equals(appRole.getPkAppFunction())){
					function.setIsSelected(true);
					break;
				}
				}
		}
	}
}
