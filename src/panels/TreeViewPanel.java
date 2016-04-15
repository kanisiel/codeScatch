package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import Settings.Constants;
import Settings.Preference;
import listener.CTreeExpansionListener;
import models.InNodeObject;

public class TreeViewPanel extends JPanel implements TreeSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTree tree;
	JFrame parents;
	CTreeExpansionListener expantionListener;
	

	public TreeViewPanel() {
		super();
		this.setLayout(new BorderLayout());
		this.expantionListener = new CTreeExpansionListener();
		this.expantionListener.init(this);
		this.tree = initTree();
		this.tree.expandRow(0);
		this.tree.setRootVisible(false);
		this.tree.addTreeSelectionListener(this);
		this.tree.addTreeExpansionListener(this.expantionListener);
		this.add(tree, BorderLayout.WEST);
		this.setSize(Constants.PFRAME_W/2, Constants.PFRAME_H);
	}
	
	public void init(JFrame parent, int width, int height){
		this.parents = parent;
		this.setSize(width, height);
		this.setBackground(Color.WHITE);
	}
	
	private JTree initTree(){
		Map<String, PreferenceDetailPanel> panelList = new LinkedHashMap<>();
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
				node = new DefaultMutableTreeNode(new InNodeObject(items, Preference.preferenceItems[index]));
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
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

		if (node == null) return;
		
		Object nodeInfo = node.getUserObject();
		if (node.isLeaf()) {
			InNodeObject nodeObject = (InNodeObject) nodeInfo;
			System.out.println(nodeObject.getLocation());
		}
		
	}
	
	public JTree getTree(){
		return this.tree;
	}
	
}

