package id.base.app.dao.business.settings;

import id.base.app.AbstractHibernateDAO;
import id.base.app.IAccessibilityConstant;
import id.base.app.dao.tree.TreeBuilderDAO;
import id.base.app.exception.SystemException;
import id.base.app.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

@Repository
public class BusinessSettingsTreeBuilderDAO  extends AbstractHibernateDAO<TreeNode, Long> implements TreeBuilderDAO  {
	
	private static final String BUSINESS_SETTINGS_TREE_QUERY=
	" select distinct f.pk_app_function,f.name,f.access_page from app_function f  "+
	" inner join app_role_function r  "+
	" on f.pk_app_function=r.fk_app_function  "+
	" where f.fk_app_function_parent= :pkBusinessSetting "+
	" and r.fk_app_role in (:role_list) and f.is_active = 1";
	
	
	@Override
	public List<? extends TreeNode> buildTreeNodes(final Long param)
			throws SystemException {
		return null;
	}
 
	@Override
	public List<TreeNode> buildTreeNodes(final Object[] param)
			throws SystemException {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		
		TreeNode root = new TreeNode();
		root.setValue("0");
		root.setName("Business Settings");
		root.setParentValue("-1");

		List<TreeNode> businessSettingsTreeNodes = getSession().createSQLQuery(BUSINESS_SETTINGS_TREE_QUERY)
							/*.setParameter("pkBusinessSetting", IAccessibilityConstant.FUNC_INT_BUSINESS_SETTING)*/
							.setParameterList("role_list", (List<Long>) param[0])
							.setResultTransformer(
								new ResultTransformer() {
									@Override
									public TreeNode transformTuple(Object[] tuple,String[] arg1) {
										TreeNode node = new TreeNode();
										node.setParentValue("0");
										node.setValue(String.valueOf(tuple[0]));
										node.setName(String.valueOf(tuple[1]));
										node.setPath(String.valueOf(tuple[2]));
										return node;
									}
									@Override
									public List transformList(List arg0) {
										return arg0;
									}
								}).list();
		nodes.add(root);
		nodes.addAll(businessSettingsTreeNodes);
		return nodes;
	}

	@Override
	public List<? extends TreeNode> buildTreeNodes() throws SystemException {
		return null;
	}

	@Override
	public String getRootTreeName() throws SystemException {
		return null;
	}

	@Override
	public String getRootTreeValue() throws SystemException {
		return null;
	}

}
