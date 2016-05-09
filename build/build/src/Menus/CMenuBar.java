package Menus;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JMenuBar;

import Settings.Menus.EMENU;
import Settings.Menus.MenuItems;
import frames.CFrame;
import models.Menu;
import models.MenuItem;

public class CMenuBar extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Components
	private Map<String, CMenu> menus;
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * Constructor													 *
	 *  - Parameter : None											 *
	 *  															 *
	 *  Author : Lee JunSoo											 *
	 *  Last Modify : 2016/04/12									 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	public CMenuBar() {
		this.menus = new LinkedHashMap<>();
		setMenu();
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * Set Menu														 *
	 *  - Parameter : None											 *
	 *  - Feature : Create Hash Map about Menus and MenuItems		 *
	 *              and call Iterative Constructor call Method		 *
	 *  															 *
	 *  Author : Lee JunSoo											 *
	 *  Last Modify : 2016/04/12									 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	private void setMenu(){
		Map<String, Menu> menus = new LinkedHashMap<>();
		for(EMENU name : EMENU.values()){
			Menu menu = new Menu(name.getName());
			menus.put(name.getName(), menu);
		}
		for(MenuItems item : MenuItems.values()){
			MenuItem menuItem = new MenuItem(item.getName(), item.getSeparate());
			menus.get(item.getMenuName()).getItems().add(menuItem);
		}
		addMenus(menus);
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * Add Menu														 *
	 *  - Parameter : None											 *
	 *  - Feature : Iterative Constructor call and add to Menubar	 *
	 *  															 *
	 *  Author : Lee JunSoo											 *
	 *  Last Modify : 2016/04/12									 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	private void addMenus(Map<String, Menu> menus){
		for(Entry<String, Menu> entry : menus.entrySet()){
			CMenu menu = new CMenu(entry.getKey(), entry.getValue().getItems());
			this.menus.put(entry.getKey(), menu);
			this.add(menu);
		}
	}
	
	//initializing method
	public void init(CFrame parents){
		for(Entry<String, CMenu> entry : menus.entrySet()){
			entry.getValue().init(parents);
		}
	}
	
}
