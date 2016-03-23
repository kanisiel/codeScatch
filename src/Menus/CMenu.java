package Menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;


public class CMenu extends JMenu{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CMenu(String name, Enum<?>[] menuItemNames) {
		//super(name);
		this.setText(name);
		
		for(Enum<?> menuItemName: menuItemNames){
			System.out.println(menuItemName.getClass());
			JMenuItem menuItem = new JMenuItem(menuItemName.name());
			menuItem.setActionCommand(menuItemName.name());
			this.add(menuItem);
		}	
	}
}
