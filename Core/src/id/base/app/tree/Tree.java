package id.base.app.tree;

public class Tree {

	private final String treeName;
	private final TreeNode nodes;

	public Tree(TreeNode nd, String vb) {
	    this.nodes = nd;
	    this.treeName = vb;
	}
	
	
	public TreeNode getNodes() {
		return nodes;
	}

	public String toString() {
	    return String.format("Tree[name=%s,nodes=%s]", this.treeName, this.nodes);
	}


	public String getTreeName() {
		return treeName;
	}
}
