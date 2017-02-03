package id.base.app.service.tree;

import id.base.app.dao.business.settings.BusinessSettingsTreeBuilderDAO;
import id.base.app.dao.tree.TreeBuilderDAO;
import id.base.app.tree.TreeBuilder;
import id.base.app.tree.TreeNode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BusinessSettingsTreeBuilder implements TreeBuilder {

	@Autowired
	private TreeBuilderDAO businessSettingsTreeBuilderDAO;
	public BusinessSettingsTreeBuilder(){}
	
	public void setBusinessSettingsTreeBuilderDAO(BusinessSettingsTreeBuilderDAO businessSettingsTreeBuilderDAO){
		this.businessSettingsTreeBuilderDAO=businessSettingsTreeBuilderDAO;
	}
	
	@Override
	public List<? extends TreeNode> buildTree(Long treeParam) {
		return null;
	}

	@Override
	public List<? extends TreeNode> buildTree() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends TreeNode> buildTrees(Object[] treeParams) {
		return businessSettingsTreeBuilderDAO.buildTreeNodes(treeParams);
	}

}
