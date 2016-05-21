/*
 * 
 * Model Class MenuItem
 * 
 * Class Variable :
 * 	- Name(String) : Item name for display
 * 
 * Method :
 * 	- Getter and Setter : For Class Variable
 *  - Constructor : For Initialize Class Variable at Construct
 *  
 *  Author : Lee Junsoo
 *  Last Modify : 2016/04/12
 */
package models;

public class MenuItem {
	private String name;
	private Boolean separate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getSeparate() {
		return separate;
	}

	public void setSeparate(Boolean separate) {
		this.separate = separate;
	}

	public MenuItem(String name, Boolean separate) {
		super();
		this.name = name;
		this.separate = separate;
	}
	
}
