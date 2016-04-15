package models;

public class InNodeObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String superMenu;
	
	public InNodeObject(String title, String superMenu) {
		super();
		this.title = title;
		this.superMenu = superMenu;
		
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
}
