package frames;

import java.awt.Color;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import Settings.Constants;
import panels.PreferenceDetailPanel;
import panels.TreeViewPanel;

public class PreferenceFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CFrame parents;
	private String title;
	private TreeViewPanel treePanel;
	private JScrollPane scrollPane;
	private GridBagLayout layout;
	private JPanel buttonPanel;
	private JFrame preferenceFrame;
	private ActionHandler actionHandler;
	private Map<String, PreferenceDetailPanel> panelList;
	
	public PreferenceFrame() throws HeadlessException {
		super();

		// attributes initialization
		this.preferenceFrame = this;
		this.actionHandler = new ActionHandler();
		this.panelList = new LinkedHashMap<>();
		
		
		// set components
		this.setSize(Constants.PFRAME_W,Constants.PFRAME_H);
		this.setLocation(Constants.PFRAME_X, Constants.PFRAME_Y);
		this.setResizable(true);
		this.layout = new GridBagLayout();
		this.getContentPane().setLayout(this.layout);
		GridBagConstraints constraints = new GridBagConstraints();
		this.treePanel = new TreeViewPanel();
		this.scrollPane = new JScrollPane(treePanel);
		this.scrollPane.setBounds( 0, 0, Constants.PFRAME_W/3, treePanel.getHeight());
		this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		addGrid(layout, constraints, scrollPane, 0, 0, 1, 2, 1, 1, GridBagConstraints.BOTH);
		
		this.buttonPanel = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.setHorizontalTextPosition(SwingConstants.CENTER);
		okButton.setActionCommand("ok");
		okButton.addActionListener(this.actionHandler);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setHorizontalTextPosition(SwingConstants.CENTER);
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(this.actionHandler);
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		buttonPanel.setBackground(Color.WHITE);
		addGrid(layout, constraints, buttonPanel, 1, 2, 1, 1, 1, 1, GridBagConstraints.BOTH);
		
		
	}
	
	
	public void applyChanges(){
		parents.repaint();
	}
	//2nd phase initialization
	public void init(CFrame parents, String title){
		this.parents = parents;
		this.title = title;
		this.setTitle(this.title);
		this.treePanel.init(this, Constants.PFRAME_W/3, Constants.PFRAME_H);
        scrollPane.setBounds( 0, 0, Constants.PFRAME_W/3, treePanel.getHeight());
		this.setVisible(true);
	}
	public void reSize(int width, int height){
		this.treePanel.init(this, width, height);
        scrollPane.setBounds( 0, 0, width, height);
	}
	
	private void addGrid(GridBagLayout layout, GridBagConstraints constraint, Component c,  
            int gridx, int gridy, int gridwidth, int gridheight, int weightx, int weighty, int fill) {
		constraint.gridx = gridx;
		constraint.gridy = gridy;
		constraint.gridwidth = gridwidth;
		constraint.gridheight = gridheight;
		constraint.weightx = weightx;
		constraint.weighty = weighty;
		constraint.fill = fill;
		layout.setConstraints(c, constraint);
		add(c);
	}
	public class ActionHandler implements ActionListener {
		private CFrame parents;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand().equals("cancel")){
				preferenceFrame.dispose();
			}
			//System.out.println(e.getActionCommand());
		}
		public void init(CFrame parents){
			this.parents = parents;
		}

	}
	public void test(){
		JTree tree = treePanel.getTree();
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
//		for(int i = 0; i < model.getChildCount(root); i++){
//			for
//		}
	}
}
