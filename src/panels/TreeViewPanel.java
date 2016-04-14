package panels;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreeViewPanel extends JPanel implements TreeSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTree tree;
	JFrame parentsFrame;
	JInternalFrame parentsIFrame;
	

	public TreeViewPanel(JTree tree) {
		super();
		this.tree = tree;
		this.tree.expandRow(0);
		this.tree.setRootVisible(false);
		tree.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent e){
		        if(e.getClickCount()==2){
		            
		        }
		    }
		});
		this.add(tree);
	}
	
	public void init(JFrame parent, int width, int height){
		this.parentsFrame = parent;
		this.setSize(width, height);
		this.setBackground(Color.WHITE);
	}
	public void init(JInternalFrame parent, int width, int height){
		this.parentsIFrame = parent;
		this.setSize(width, height);
		this.setBackground(Color.WHITE);
	}
	public JTree getTree() {
		return tree;
	}
	public void setTree(JTree tree) {
		this.tree = tree;
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

		if (node == null) return;
		
		Object nodeInfo = node.getUserObject();
		if (node.isLeaf()) {
		 String title = (String) nodeInfo;
		 
		} else {
		 
		}
		
	}
	
}

