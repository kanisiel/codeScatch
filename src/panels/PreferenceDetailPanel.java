package panels;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import frames.CFrame;

public class PreferenceDetailPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String title;
	private CFrame parent;
	private JLabel titleLabel; 
	public PreferenceDetailPanel(CFrame parent, int width, int height) {
		super();
		
		this.parent = parent;
		this.setSize(width, height);
		this.titleLabel = new JLabel(title);
		titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		this.setLayout(new BorderLayout());
		this.add(titleLabel, BorderLayout.NORTH);
	}
	
	public void init(String title){
		this.title = title;
	}
	
}
