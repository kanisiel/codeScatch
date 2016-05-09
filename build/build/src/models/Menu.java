/*
 * 
 * Model Class Menu
 * 
 * Class Variable :
 * 	- Name(String) : Menu name for display
 * 	- Item list (Vector) : List about including items for each Menu
 * 
 * Method :
 * 	- Getter and Setter : For Class Variable
 *  - Constructor : For Initialize Class Variable at Construct
 *  
 *  Author : Lee Junsoo
 *  Last Modify : 2016/04/12
 */
package models;

import java.util.Vector;

public class Menu {
	private String name;
	private Vector<MenuItem> items;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Vector<MenuItem> getItems() {
		return this.items;
	}
	public void setItems(Vector<MenuItem> items) {
		this.items = items;
	}
	
	public Menu(String name) {
		super();
		this.name = name;
		this.items = new Vector<>();
	}
	
}
