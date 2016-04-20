package panels;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Language extends PreferenceDetailPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String superMenu;
	
	public Language(String superMenu, String title, int width, int height) {
		super(width, height);
		
		this.superMenu = superMenu;
		this.title = title;
		this.setSize(width, height);
		this.titleLabel = new JLabel(superMenu + " > "+ title);
		titleLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		this.setLayout(new BorderLayout());
		this.add(titleLabel, BorderLayout.NORTH);
		this.setVisible(true);
	}

}
