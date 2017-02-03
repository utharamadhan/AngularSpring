package id.base.app.tree;

import java.util.List;

public class TreeNode {

	private List<? extends TreeNode> children;        // this list of child only applied for ROOT node,
     // non ROOT node no need to set this, leave as null
	private String parentValue;
	private String description;    // for ALT attribute in tree view
	private String name;
	private String value; 
	private String path;
	
	public boolean hasChild() {
		return (children != null && children.size() > 0);
	}
	
	public void setParentValue(String parentValue) {
		this.parentValue = parentValue;
	}
	
	public String getParentValue() {
		return parentValue;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setChildren(List<? extends TreeNode> childs) {
		this.children = childs;
	}
	
	public List<? extends TreeNode> getChildren() {
		return children;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String string) {
		description = string;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String toString() {
		return String.format("WorklistTreeNode(name=%s,value=%s,parent=%s,children=%s)", this.name, this.value, this.parentValue, this.children);
	}
	
}
