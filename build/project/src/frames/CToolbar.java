package frames;

import javax.swing.JToolBar;

import Settings.Constants;

public class CToolbar extends JToolBar{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CToolbar(){
		// attributes initialization
		this.setSize(Constants.TOOLBAR_W, Constants.TOOLBAR_H);
		this.setFloatable(true);
		this.setFocusable(true);
	}
}
