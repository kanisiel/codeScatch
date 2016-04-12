package Menus;

import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import models.MenuItem;


public class CMenu extends JMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * Constructor													 *
	 *  - Parameter : Menu Name (String), Item List (Vector)		 *
	 *  															 *
	 *  Author : Lee JunSoo											 *
	 *  Last Modify : 2016/04/12									 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	public CMenu(String name, Vector<MenuItem> items){
		this.setText(name);
		for(MenuItem item : items){
			JMenuItem menuItem = new JMenuItem(item.getName());
			menuItem.setActionCommand(item.getName());
			this.add(menuItem);
			if(item.getSeparate()){	this.addSeparator();	}
		}
	}
}
