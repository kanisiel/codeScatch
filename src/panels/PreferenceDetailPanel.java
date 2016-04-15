package panels;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JLabel;
import javax.swing.JPanel;

import frames.CFrame;

public abstract class PreferenceDetailPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected CFrame parent;
	protected JLabel titleLabel; 
	
	public PreferenceDetailPanel(CFrame parent, int width, int height) {
		super();
	}
	
	public PreferenceDetailPanel(int width, int height) {
		super();
		this.setSize(width, height);
	}
	
	protected Font[] getFontList(){
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    Font[] fonts = e.getAllFonts(); // Get the fonts
	    return fonts;
	}
	
}
