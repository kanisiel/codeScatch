package Menus;

import frames.CFrame;

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
		parents.getPreferenceFrame().setVisible(true);
	}
	
	
	@Override
	public void invokedMethod(String name){
		try {
			this.getClass().getMethod(name).invoke(this);
		} catch (Exception e) {e.printStackTrace();	}
	}
	
	@Override
	public void init(CFrame parent){
		this.parents = parent;
	}
}
