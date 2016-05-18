package panels;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import frames.CFrame;

public class FontPanel extends PreferenceDetailPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String superMenu;
	private FontSettingPanel fontPanel;
	
	public FontPanel(String superMenu, String title, int width, int height) {
		super(width, height);
		
		this.superMenu = superMenu;
		this.title = title;
		this.bActionHandler.init(this);
		this.setSize(width, height);
		this.titleLabel = new JLabel(this.superMenu + " > "+ this.title);
		titleLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		this.setLayout(new BorderLayout());
		this.add(titleLabel, BorderLayout.NORTH);
		this.fontPanel = new FontSettingPanel();
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(fontPanel, BorderLayout.CENTER);
		JButton applyButton = new JButton("Apply");
		applyButton.setHorizontalAlignment(SwingConstants.LEFT);
		applyButton.setActionCommand("Apply");
		applyButton.addActionListener(this.bActionHandler);
		contentPanel.add(applyButton,BorderLayout.SOUTH);
		this.add(contentPanel, BorderLayout.CENTER);
		this.setVisible(true);
	}
	@Override
	public void init(CFrame target){
		this.target = target;
		this.fontPanel.init(target);
	}

	public FontSettingPanel getFontSettingPanel(){
		return this.fontPanel;
	}

}
