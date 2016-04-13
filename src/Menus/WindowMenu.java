package Menus;

import Settings.Menus.MenuItems;
import frames.CFrame;
import frames.PreferenceFrame;

public class WindowMenu extends CMenu{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WindowMenu(String menuItem) {
		super();
		this.menuItem = menuItem;
	}

	public void preference(){
		createPreferenceWindow(MenuItems.Preference.getName());
	}
	
	public void createPreferenceWindow(String title){
		PreferenceFrame preferenceFrame = new PreferenceFrame();
		preferenceFrame.init(super.parents, title);
	}

	
	
	
	public void init(CFrame parents){
		super.parents = parents;
	}
}
