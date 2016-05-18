package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Menus.CMenu;
import Settings.Menus.MenuItems;
import frames.CFrame;

public class ActionHandler implements ActionListener {
	private CFrame parents;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		CMenu target = MenuItems.valueOf(e.getActionCommand()).getInvoke();
		target.init(parents);
		target.invokedMethod(target.getMenuItem().toLowerCase());
		
	}
	public void init(CFrame parents){
		this.parents = parents;
	}
//	public void setCaller(ColorFontPanel caller){
//		this.caller = caller;
//	}
//	public void setMenu(CMenu menu){
//		this.menu = menu;
//	}

}
