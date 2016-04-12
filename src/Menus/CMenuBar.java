package Menus;

import java.util.EnumMap;
import java.util.Map;

import javax.swing.JMenuBar;

import Settings.Menus;
import Settings.Menus.EMENU;
import adapter.MenuAdapter;

public class CMenuBar extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MenuAdapter mAdapter = new MenuAdapter();
	public CMenuBar() {
		EnumMap<EMENU,Enum[]> menuItems = putitem();
		for(Map.Entry<EMENU,Enum[]> items : menuItems.entrySet()){
			CMenu menu = new CMenu(items.getKey().getName(), items.getValue());
			this.add(menu);
		}
//		for(Enum menuItem : EMENU.values()){
//			System.out.println(menuItem.name());
//			JMenu menu = new JMenu(menuItem.name());
//			this.add(menu);
//		}
	}
	private EnumMap<EMENU,Enum[]> putitem() {
		EnumMap<EMENU,Enum[]> menuItems = new EnumMap<>(EMENU.class);
		for(EMENU eMenu : EMENU.values()){
			menuItems.put(eMenu, eMenu.getmenuItems());//menuItem);
		}		
		return menuItems;
	}
	private String trim(String str){
		String result;
		result = str.replace("[L"+Menus.class.getName()+"$", "");
		result = result.replace(";", "");
		return result;
	}
	
}
