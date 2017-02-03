package id.base.app.dao.tree;

import id.base.app.exception.SystemException;
import id.base.app.tree.TreeNode;

import java.util.List;

public interface TreeBuilderDAO {
	
	public List<? extends TreeNode> buildTreeNodes(Long param) throws SystemException;
	
	public List<? extends TreeNode> buildTreeNodes(Object[] param) throws SystemException;
	
	public List<? extends TreeNode> buildTreeNodes() throws SystemException;

	public String getRootTreeName() throws SystemException;

	public String getRootTreeValue() throws SystemException;
	
}
