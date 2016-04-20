package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import Settings.Constants;
import Settings.Preference;
import Settings.Preference.preferenceItemsChilds;
import frames.PreferenceFrame;
import listener.CTreeExpansionListener;
import models.InNodeObject;

public class TreeViewPanel extends JPanel implements TreeSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTree tree;
	PreferenceFrame parents;
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
		this.add(tree, BorderLayout.CENTER);
		this.setSize(0, 0);
		this.tree.setSize(Constants.PFRAME_W/3, Constants.PFRAME_H-30);
		
	}
	
	public void init(PreferenceFrame parent, int width, int height){
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
			panelList.put(parentItem, PreferenceDetailPanel.getInstance());
		}
		for(preferenceItemsChilds items : Preference.preferenceItemsChilds.values()){
			int index = Arrays.asList(Preference.preferenceItems).indexOf(items.getParent());
			parentNode = (DefaultMutableTreeNode) defaultTreeModel.getChild(root, index);
			node = new DefaultMutableTreeNode(new InNodeObject(items.getItemName(), items.getParent(), items.name()));
			addNodeToDefaultTreeModel( defaultTreeModel, parentNode, node );
			panelList.put(items.getParent()+">"+items.getItemName(), items.getPanel());
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
			
			String key = nodeObject.getSuperMenu()+">"+nodeObject.getTitle();
			parents.setDetailPane(key);
		}
		
	}
	
	public JTree getTree(){
		return this.tree;
	}
	
}

