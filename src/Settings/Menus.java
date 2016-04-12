package Settings;

public class Menus {
	//Menu attributes
	/*
	 * 
	 * Enumeration for Setting EMENU
	 * 
	 * enum Variable :
	 * 	- Name(String) : Menu name for display
	 * 
	 * Method :
	 * 	- Getter : For Class Variable
	 *  - Constructor : For Initialize Class Variable at Construct
	 *  
	 *  Author : Lee Junsoo
	 *  Last Modify : 2016/04/12
	 */
	public static enum EMENU {
		File("File"),
		Edit("Edit"),
		View("View"),
		Window("Window"),
		Help("Help");
		
		
		private String name;
		private EMENU(String name){
			this.name=name;
		}
		public String getName(){	return name;	}
	};
	/*
	 * 
	 * Enumeration for menu items MenuItems
	 * 
	 * enum Variable :
	 * 	- Name(String) : Item name for display
	 * 	- Menu Name(String) : Parent menu name for association
	 * 	- Separate (Boolean) : A Boolean variable for need separation bar after this item
	 * 
	 * Method :
	 * 	- Getter : For Class Variable
	 *  - Constructor : For Initialize Class Variable at Construct
	 *  
	 *  Author : Lee Junsoo
	 *  Last Modify : 2016/04/12
	 */
	public static enum MenuItems {
		New("New", "File", true),
		Open("Open", "File", false),
		Save("Save", "File", false),
		SaveAs("Save as", "File", true),
		Close("Close", "File", false),
		Exit("Exit", "File", false),
		
		Undo("Undo", "Edit", false),
		Redo("Redo", "Edit" , true),
		Copy("Copy", "Edit", false),
		Cut("Cut", "Edit", false),
		Paste("Paste", "Edit", false),
		
		FullScreen("Full Screen", "View", false),
		Minimize("Minimize","View",false),
		WindowMode("Window Mode", "View", false),
		
		FlowChart("Flow Chart","Window", false),
		CodeViewer("Code Viewer", "Window", false),
		TaskList("TaskList", "Window", false),
		
		Help("Help","Help",false),
		About("About", "Help", false);
		
		private String name;
		private String menuName;
		private Boolean separate;
		
		private MenuItems(String name, String menuName, Boolean separate){
			this.name = name;
			this.menuName = menuName;
			this.separate = separate;
		}
		
		public String getName(){
			return name;
		}
		public String getMenuName(){
			return menuName;
		}
		public Boolean getSeparate(){
			return separate;
		}
	};
}