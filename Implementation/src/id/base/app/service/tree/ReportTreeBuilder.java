package id.base.app.service.tree;

import id.base.app.dao.report.ReportTreeBuilderDAO;
import id.base.app.dao.tree.TreeBuilderDAO;
import id.base.app.tree.TreeBuilder;
import id.base.app.tree.TreeNode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author antony
 *
 */
@Service
@Transactional
public class ReportTreeBuilder implements TreeBuilder {

	@Autowired
	private TreeBuilderDAO reportTreeBuilderDAO;
	public ReportTreeBuilder(){}
	
	public void setReportTreeBuilderDAO(ReportTreeBuilderDAO reportTreeBuilderDAO){
		this.reportTreeBuilderDAO=reportTreeBuilderDAO;
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
		return reportTreeBuilderDAO.buildTreeNodes(treeParams);
	}

}
