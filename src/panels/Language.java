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
		
		this.setSuperMenu(superMenu);
		this.setTitle(title);
		this.setSize(width, height);
		this.titleLabel = new JLabel(superMenu + " > "+ title);
		titleLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		this.setLayout(new BorderLayout());
		this.add(titleLabel, BorderLayout.NORTH);
		this.setVisible(true);
	}

	public String getSuperMenu() {
		return superMenu;
	}

	public void setSuperMenu(String superMenu) {
		this.superMenu = superMenu;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
