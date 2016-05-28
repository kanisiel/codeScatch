package parser;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;


public class TreeNode<TreeData> implements Iterable<TreeNode<TreeData>>{
	
    private TreeNode<TreeData> parent;
    private Vector<TreeNode<TreeData>> children;
    private TreeData data;

	public TreeData getData() {return data;}
	public void setData(TreeData data) {this.data = data;}
	public TreeNode<TreeData> getParent() {return parent;}
	
	public TreeNode(TreeData child) {
		this.data = child;
        this.children = new Vector<TreeNode<TreeData>>(); 
	}

	public void addChild(TreeData child) {
        TreeNode<TreeData> childNode = new TreeNode<TreeData>(child);
        childNode.parent = this;
        this.children.add(childNode);
    }
	
	public List<TreeNode<TreeData>> getChildList() {
		return this.children;
    }
 
    @Override
    public Iterator<TreeNode<TreeData>> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

}

