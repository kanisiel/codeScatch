package frames;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import Settings.Constants;
import net.miginfocom.swing.MigLayout;
import panels.PreferencePanel;
import panels.TreeViewPanel;

public class PreferenceFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CFrame mainFrame;
	private String title;
	private PreferencePanel preferencePanel;
	private JScrollPane scrollPane;
	private MigLayout layout;
	private JPanel buttonPanel;
	private JFrame preferenceFrame;
	private ActionHandler actionHandler;
	private TreeViewPanel treePanel;
	
	public PreferenceFrame() throws HeadlessException {
		super();

		this.title = Settings.Menus.MenuItems.Preference.getMenuName();
		// attributes initialization
		this.preferenceFrame = this;
		this.actionHandler = new ActionHandler();
		this.preferencePanel = new PreferencePanel();
		
		// set components
		this.setSize(Constants.PFRAME_W,Constants.PFRAME_H);
		this.setLocation(Constants.PFRAME_X, Constants.PFRAME_Y);
		this.setResizable(true);
		this.layout = new MigLayout("","["+(Constants.PFRAME_W)/3+"]["+(Constants.PFRAME_W)/3+"]["+(Constants.PFRAME_W)/3+"]");
		this.getContentPane().setLayout(this.layout);
		this.treePanel = new TreeViewPanel();
		this.scrollPane = new JScrollPane(treePanel);
		this.scrollPane.setBounds( 0, 0, Constants.PFRAME_W/3, Constants.PFRAME_H-30);
		this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(this.scrollPane);
		this.add(this.preferencePanel);
		this.add(new JPanel(), "span 2");
		this.buttonPanel = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.setHorizontalAlignment(SwingConstants.LEFT);
		okButton.setActionCommand("ok");
		okButton.addActionListener(this.actionHandler);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setHorizontalAlignment(SwingConstants.RIGHT);
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(this.actionHandler);
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		this.add(buttonPanel);
		
		
	}
	public CFrame getParent(){
		return this.mainFrame;
	}
	public void setDetailPane(String key){
		this.preferencePanel.setDetailPanel(key);
		this.preferencePanel.repaint();
	}
	
	public void applyChanges(){
		mainFrame.repaint();
	}
	//2nd phase initialization
	public void init(CFrame mainFrame){
		this.mainFrame = mainFrame;
		this.preferencePanel.init(mainFrame, this);
		this.setTitle(this.title);
		this.setVisible(true);
	}
	
	
	public class ActionHandler implements ActionListener {
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand().equals("cancel")){
				preferenceFrame.dispose();
			}
		}
//		public void init(CFrame parents){
//			mainFrame = parents;
//		}

	}
}
