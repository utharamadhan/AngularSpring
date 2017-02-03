package id.base.app.service.tree;

import id.base.app.tree.TreeNode;

import java.util.List;

public interface ITreeMenuBuilder {
	public abstract List<? extends TreeNode> buildTrees(Object[] treeParams, int pkAppFunctionParent) ;
}
