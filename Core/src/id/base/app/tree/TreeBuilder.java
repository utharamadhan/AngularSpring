package id.base.app.tree;

import java.util.List;

public interface TreeBuilder {

	public abstract List<? extends TreeNode> buildTrees(Object[] treeParams) ;
	
	public abstract List<? extends TreeNode> buildTree(Long treeParam) ;
	
	public abstract List<? extends TreeNode> buildTree() ; 
	
}
