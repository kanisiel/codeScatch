package frames;

import javax.swing.ImageIcon;
import javax.swing.JRadioButton;


public class CRadioButton extends JRadioButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2884849433154382985L;

		//Constructor
		public CRadioButton(String icon, String selectedIcon){
			//set Attribute
			this.setIconTextGap(PREVIOUS);
			this.setIcon(new ImageIcon(icon));
			this.setSelectedIcon(new ImageIcon(selectedIcon));
		}
}
