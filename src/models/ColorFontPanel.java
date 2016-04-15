package models;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import frames.CFrame;
import panels.PreferenceDetailPanel;

public class ColorFontPanel extends PreferenceDetailPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String superMenu;
	public ColorFontPanel(String superMenu, String title, CFrame parent, int width, int height) {
		super(parent, width, height);
		
		this.parent = parent;
		this.superMenu = superMenu;
		this.title = title;
		this.setSize(width, height);
		this.titleLabel = new JLabel(title);
		titleLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		this.setLayout(new BorderLayout());
		this.add(titleLabel, BorderLayout.NORTH);
	}
	

}
