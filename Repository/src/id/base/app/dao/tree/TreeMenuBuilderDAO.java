package id.base.app.dao.tree;

import id.base.app.AbstractHibernateDAO;
import id.base.app.exception.SystemException;
import id.base.app.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

@Repository
public class TreeMenuBuilderDAO  extends AbstractHibernateDAO<TreeNode, Long> implements ITreeMenuBuilderDAO  {
	
	private static final String TREE_QUERY=
	" select distinct f.pk_app_function,f.name,f.access_page from app_function f  "+
	" inner join app_role_function r  "+
	" on f.pk_app_function=r.fk_app_function  "+
	" where f.fk_app_function_parent= :pkAppFunctionParent "+
	" and fk_app_role in (:role_list) and f.is_active = 1";
	
	@Override
	public List<TreeNode> buildTreeNodes(final Object[] param, int pkAppFunctionParent)
			throws SystemException {
		List<TreeNode> nodes = new ArrayList<TreeNode>();

		List<TreeNode> treeNodes = getSession().createSQLQuery(TREE_QUERY)
							.setParameter("pkAppFunctionParent", pkAppFunctionParent)
							.setParameterList("role_list", (List<Long>) param[0])
							.setResultTransformer(
								new ResultTransformer() {
									@Override
									public TreeNode transformTuple(Object[] tuple,String[] arg1) {
										TreeNode node = new TreeNode();
										node.setParentValue("-1");
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
		nodes.addAll(treeNodes);
		return nodes;
	}

}
