package id.base.app.dao.tree;

import id.base.app.exception.SystemException;
import id.base.app.tree.TreeNode;

import java.util.List;

public interface ITreeMenuBuilderDAO {
	public List<? extends TreeNode> buildTreeNodes(Object[] param, int pkAppFunctionParent) throws SystemException;
}
