package Menus;

import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import frames.CFrame;
import listener.ActionHandler;
import models.MenuItem;


public class CMenu extends JMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Components
	protected ActionHandler actionHandler;
	protected CFrame parents;
	protected String menuItem;
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * Constructor													 *
	 *  - Parameter : Menu Name (String), Item List (Vector)		 *
	 *  															 *
	 *  Author : Lee JunSoo											 *
	 *  Last Modify : 2016/04/12									 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	public CMenu(String name, Vector<MenuItem> items){
		this.setText(name);
		this.actionHandler = new ActionHandler();
		for(MenuItem item : items){
			JMenuItem menuItem = new JMenuItem(item.getName());
			System.out.println(item.getName());
			menuItem.setActionCommand(item.getName());
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
			if(item.getSeparate()){	this.addSeparator();	}
		}
	}
	public CMenu(){
		super();
	}


	public String getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(String menuItem) {
		this.menuItem = menuItem;
	}
	
	public void invokedMethod(String name){
		try {
			this.getClass().getMethod(name).invoke(this);
		} catch (Exception e) {e.printStackTrace();	}
	}
	
	public void init(CFrame parents){
		
		this.parents = parents;
		this.actionHandler.init(parents);
//		this.actionHandler.setMenu(this);
	}
	
}
