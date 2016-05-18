package listener;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;

import panels.TreeViewPanel;

public class CTreeExpansionListener implements TreeExpansionListener {
	private TreeViewPanel parent;
	
	public void treeExpanded(TreeExpansionEvent evt) {
//		JTree tree = (JTree) evt.getSource();
//
//		TreePath path = evt.getPath();
	}

	public void treeCollapsed(TreeExpansionEvent evt) {
//		JTree tree = (JTree) evt.getSource();
//
//		TreePath path = evt.getPath();
//		parent.setSize(tree.getWidth(), tree.getHeight());
	}
	public void init(TreeViewPanel parent){
		this.setParent(parent);
	}

	public TreeViewPanel getParent() {
		return parent;
	}

	public void setParent(TreeViewPanel parent) {
		this.parent = parent;
	}
}