package id.base.app.service.tree;

import id.base.app.dao.tree.TreeBuilderDAO;
import id.base.app.tree.TreeBuilder;
import id.base.app.tree.TreeNode;

import java.util.List;

public abstract class AbstractTreeBuilder implements TreeBuilder {
	
	protected abstract String getRootTreeName();
	protected abstract String getRootTreeValue();
	protected abstract String getTreeName();
	
	
	protected TreeBuilderDAO treeBuilderDAO ;
	
	public List<? extends TreeNode> buildTrees(Object[] treeParams) { return null ; }
	
	public List<? extends TreeNode> buildTree(Long treeParam) {
		List<? extends TreeNode> nodes = treeBuilderDAO.buildTreeNodes(new Long(getRootTreeValue()));
        return nodes;
	}
	
	public void setTreeBuilderDAO(TreeBuilderDAO treeBuilderDAO) {
		this.treeBuilderDAO = treeBuilderDAO;
	}

}
