package frames;

import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;

import Settings.Constants;

public class PreferenceFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CFrame parents;
	private String title;

	public PreferenceFrame() throws HeadlessException {
		super();

		// attributes initialization
		this.setSize(Constants.PFRAME_W,Constants.PFRAME_H);
		this.setLocation(Constants.PFRAME_X, Constants.PFRAME_Y);
		this.setResizable(true);
		

		this.getContentPane().setLayout(new BorderLayout());
	}
	
	public void applyChanges(){
		parents.repaint();
	}
	//2nd phase initialization
	public void init(CFrame parents, String title){
		this.parents = parents;
		this.title = title;
		this.setTitle(this.title);
		this.setVisible(true);
	}
	
}
