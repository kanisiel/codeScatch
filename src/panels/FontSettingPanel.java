package panels;

import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import Settings.Preference;
import frames.CFrame;

public class FontSettingPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CFrame target;
	private GridBagLayout layout;
	private GridBagConstraints constants;
	private JComboBox<String> fontBox;
	private DefaultComboBoxModel<String> dcbmFont;
	private JComboBox<Integer> sizeBox;
	private DefaultComboBoxModel<Integer> dcbmSize;
	
	public FontSettingPanel(){
		this.constants = new GridBagConstraints();
		this.layout = new GridBagLayout();
		this.setLayout(layout);
		this.dcbmFont = new DefaultComboBoxModel<>(getFontList());
		this.fontBox = new JComboBox<>(dcbmFont);
		this.dcbmSize = new DefaultComboBoxModel<>(getFontSizeList());
		this.sizeBox = new JComboBox<>(dcbmSize);
		addGrid(layout, constants, fontBox, 0, 0, 2, 1, 1, 1, GridBagConstraints.BOTH);
		//addGrid(layout, constants, sizeBox, 0, 1, 1, 1, 1, 1, GridBagConstraints.BOTH);
		addGrid(layout, constants, sizeBox, 2, 0, 1, 1, 1, 1, GridBagConstraints.BOTH);
	}
	
	public void init(CFrame target){
		this.target = target;
	}
	
	private Vector<Integer> getFontSizeList(){
		Vector<Integer> sizeList = new Vector<>();
		for(String size: Preference.fontSizes){
			sizeList.add(Integer.parseInt(size));
		}
		return sizeList;
	}
	protected Vector<String> getFontList(){
		Vector<String> fontList = new Vector<>();
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    for(Font font : e.getAllFonts()){ // Get the fonts
	    	fontList.add(font.getFontName());
	    }
	    return fontList;
	}
	public Font findFont(){
		Font result;
		for(String name : getFontList()){
			if(name.equals(this.fontBox.getSelectedItem())){
				int size = (int) this.sizeBox.getSelectedItem();
				result = new Font(name, Font.PLAIN, size);
				
				return result;
			}
		}
		return Font.getFont("serif");
	}
	public void applyFont(){
		Font font = findFont();
		target.getDesktopPane().getCodeViewerFrame().getTextArea().setFont(font);
	}

	private void addGrid(GridBagLayout layout, GridBagConstraints constraint, Component c,  
            int gridx, int gridy, int gridwidth, int gridheight, int weightx, int weighty, int fill) {
		constraint.gridx = gridx;
		constraint.gridy = gridy;
		constraint.gridwidth = gridwidth;
		constraint.gridheight = gridheight;
		constraint.weightx = weightx;
		constraint.weighty = weighty;
		constraint.fill = fill;
		layout.setConstraints(c, constraint);
		add(c);
	}
}
