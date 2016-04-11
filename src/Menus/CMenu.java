package Menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import adapter.MenuAdapter;


public class CMenu extends JMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	MenuAdapter mAdapter = new MenuAdapter();
	public CMenu(String name, Object menuItemNames) throws ClassNotFoundException {
		
		//Object item = theClass.cast(menuItemNames);
		
		
		this.setText(name);
		//menuItemNames = mAdapter.ConvertType(menuItemNames);
		for(Object menuItemName: menuItemNames){
			//menuItemName.valueOf(menuItemName.getClass(), menuItemName.);
			JMenuItem menuItem = new JMenuItem(menuItemName.name());
			menuItem.setActionCommand(menuItemName.name());
			this.add(menuItem);
		}	
	}
}
