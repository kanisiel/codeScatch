package listner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Menus.CMenu;
import Settings.Menus.MenuItems;
import frames.CFrame;

public class ActionHandler implements ActionListener {
	private CFrame parents;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		CMenu target = MenuItems.valueOf(e.getActionCommand()).getInvoke();
		target.init(parents);
		target.invokedMethod(target.getMenuItem().toLowerCase());
		//System.out.println(e.getActionCommand());
	}
	public void init(CFrame parents){
		this.parents = parents;
	}

}
