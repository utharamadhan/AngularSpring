package id.base.app.service.tree;

import id.base.app.dao.tree.ITreeMenuBuilderDAO;
import id.base.app.tree.TreeNode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TreeMenuBuilder implements ITreeMenuBuilder {

	@Autowired
	private ITreeMenuBuilderDAO treeBuilderDAO;
	public TreeMenuBuilder(){
		
	}
	
	@Override
	public List<? extends TreeNode> buildTrees(Object[] treeParams,
			int pkAppFunctionParent) {
		return treeBuilderDAO.buildTreeNodes(treeParams, pkAppFunctionParent);
	}

}
