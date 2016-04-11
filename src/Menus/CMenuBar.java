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
		EnumMap<EMENU,Object> menuItems = putitem();
		for(Map.Entry<EMENU,Object> items : menuItems.entrySet()){
			String className = trim(items.getValue().getClass().getName());
			System.out.println(items.getValue());
			CMenu menu = new CMenu(className.substring(0, className.length()-4), items.getValue());
			//System.out.println(className.substring(0, className.indexOf(";")));
			this.add(menu);
		}
//		for(Enum menuItem : EMENU.values()){
//			System.out.println(menuItem.name());
//			JMenu menu = new JMenu(menuItem.name());
//			this.add(menu);
//		}
	}
	private EnumMap<EMENU,Object> putitem() throws InstantiationException, IllegalAccessException{
		EnumMap<EMENU,Object> menuItems = new EnumMap<>(EMENU.class);
		for(EMENU eMenu : EMENU.values()){
			Class classObject = eMenu.getClassObject();
			Object menuItem = classObject.newInstance();
			menuItem = classObject.cast(eMenu.getmenuItems());
			menuItems.put(eMenu, menuItem);
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
