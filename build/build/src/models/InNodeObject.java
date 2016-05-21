package models;

public class InNodeObject {
	private String title;
	private String superMenu;
	private String name;
	
	public InNodeObject(String title, String superMenu, String name) {
		super();
		this.title = title;
		this.superMenu = superMenu;
		this.setName(name);
	}
	
	public String toString(){
		return title;
	}
	
	public String getLocation(){
		return superMenu + " > " + title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSuperMenu() {
		return superMenu;
	}

	public void setSuperMenu(String superMenu) {
		this.superMenu = superMenu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
