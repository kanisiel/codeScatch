package panels;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Settings.Preference;
import frames.CFrame;

public abstract class PreferenceDetailPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8205950031125250763L;
	protected CFrame target;
	protected PreferencePanel parent;
	protected JLabel titleLabel;
	protected BActionHandler bActionHandler;
	
	public PreferenceDetailPanel(CFrame parent, int width, int height) {
		super();
		this.bActionHandler = new BActionHandler();
		this.setVisible(true);
	}
	
	public PreferenceDetailPanel(int width, int height) {
		super();
		this.bActionHandler = new BActionHandler();
		this.setSize(width, height);
		this.setVisible(true);
	}
	public void setParent(PreferencePanel parent){
		this.parent = parent;
	}
	public void setTarget(CFrame target){
		this.target = target;
	}
	public JLabel getTitleLabel(){
		return titleLabel;
	}
	
	public void init(CFrame target){
		this.target = target;
		this.bActionHandler.init(this);
	}
	public static PreferenceDetailPanel getInstance() {
		PreferenceDetailPanel pdPanel = new PreferenceDetailPanel(Preference.preferenceDetailPanel_W,Preference.preferenceDetailPanel_H-30) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
		};
		pdPanel.setVisible(true);
		return pdPanel;
	}
	
	public class BActionHandler implements ActionListener {
		private PreferenceDetailPanel caller;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Apply")){
				FontPanel cfp = (FontPanel) caller;
				Font font = cfp.getFontSettingPanel().findFont();
				target.getDesktopPane().getCodeViewerFrame().setFont(target.getDesktopPane().getCodeViewerFrame().getTextArea(), font);
			}
			
			
		}
		public void init( PreferenceDetailPanel caller){
			this.caller = caller;
		}

	}
	
}
