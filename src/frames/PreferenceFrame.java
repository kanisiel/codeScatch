package frames;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Settings.Constants;
import panels.PreferenceDetailPanel;
import panels.PreferencePanel;

public class PreferenceFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CFrame mainFrame;
	private String title;
	private PreferencePanel preferencePanel;
	private BorderLayout layout;
	private JPanel buttonPanel;
	private JFrame preferenceFrame;
	private ActionHandler actionHandler;
	private Map<String, PreferenceDetailPanel> panelList;
	
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
		this.layout = new BorderLayout();
		this.getContentPane().setLayout(this.layout);
		this.add(this.preferencePanel, BorderLayout.CENTER);
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
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		
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
	public void setPanelList(Map<String, PreferenceDetailPanel> panelList){
		this.panelList = panelList;
	}
}
