package frames;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import Settings.Constants;
import Settings.Preference;
import panels.ButtonPanel;
import panels.TreeViewPanel;

public class PreferenceFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CFrame parents;
	private String title;
	private TreeViewPanel treePanel;
	
	public PreferenceFrame() throws HeadlessException {
		super();

		// attributes initialization
		this.setSize(Constants.PFRAME_W,Constants.PFRAME_H);
		this.setLocation(Constants.PFRAME_X, Constants.PFRAME_Y);
		this.setResizable(true);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.treePanel = new TreeViewPanel(initTree());
		JScrollPane scrollPane = new JScrollPane(treePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds( 0, 0, treePanel.getWidth(), treePanel.getHeight());
		this.getContentPane().add(scrollPane, BorderLayout.WEST);
		this.getContentPane().add(new ButtonPanel(this), BorderLayout.SOUTH);
		
	}
	
	private JTree initTree(){
		JTree tree = new JTree();
		//tree.setRootVisible( false );
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root node, should be invisible");
		DefaultTreeModel defaultTreeModel = new DefaultTreeModel( rootNode );
		
		tree.setModel( defaultTreeModel );
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) defaultTreeModel.getRoot();
		DefaultMutableTreeNode parentNode; 
		DefaultMutableTreeNode node;
		
		
		for(String parentItem : Preference.preferenceItems){
			parentNode = (DefaultMutableTreeNode) defaultTreeModel.getRoot(); 
			node = new DefaultMutableTreeNode(parentItem);
			addNodeToDefaultTreeModel( defaultTreeModel, parentNode, node );
		}
		for(String[] parentIndex : Preference.preferenceItemsChild){
			int index = Arrays.asList(Preference.preferenceItemsChild).indexOf(parentIndex);
			parentNode = (DefaultMutableTreeNode) defaultTreeModel.getChild(root, index);
			for(String items : parentIndex){
				node = new DefaultMutableTreeNode(items);
				node.setUserObject(items);
				addNodeToDefaultTreeModel( defaultTreeModel, parentNode, node );
			}
		}
	
		return tree;
	}
	
	private static void addNodeToDefaultTreeModel( DefaultTreeModel treeModel, DefaultMutableTreeNode parentNode, DefaultMutableTreeNode node ) {
		
		treeModel.insertNodeInto(  node, parentNode, parentNode.getChildCount()  );
		
		if (  parentNode == treeModel.getRoot()  ) {
			treeModel.nodeStructureChanged(  (TreeNode) treeModel.getRoot()  );
		}
	}
	public void applyChanges(){
		parents.repaint();
	}
	//2nd phase initialization
	public void init(CFrame parents, String title){
		this.parents = parents;
		this.title = title;
		this.setTitle(this.title);
		this.treePanel.setTree(initTree());
		this.treePanel.init(this, 200, 400);
		this.setVisible(true);
	}
	
}
