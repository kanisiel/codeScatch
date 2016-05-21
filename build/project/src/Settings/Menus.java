package Settings;

import Menus.CMenu;
import Menus.WindowMenu;

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
		New("New", "File", true, new CMenu()),
		Open("Open", "File", false, new CMenu()),
		Save("Save", "File", false, new CMenu()),
		SaveAs("Save as", "File", true, new CMenu()),
		Close("Close", "File", false, new CMenu()),
		Exit("Exit", "File", false, new CMenu()),
		
		Undo("Undo", "Edit", false, new CMenu()),
		Redo("Redo", "Edit" , true, new CMenu()),
		Copy("Copy", "Edit", false, new CMenu()),
		Cut("Cut", "Edit", false, new CMenu()),
		Paste("Paste", "Edit", false, new CMenu()),
		
		FullScreen("Full Screen", "View", false, new CMenu()),
		Minimize("Minimize","View",false, new CMenu()),
		WindowMode("Window Mode", "View", false, new CMenu()),
		
		FlowChart("Flow Chart","Window", false, new CMenu()),
		CodeViewer("Code Viewer", "Window", false, new CMenu()),
		TaskList("TaskList", "Window", true, new CMenu()),
		Preference("Preference", "Window", false, new WindowMenu("Preference")),
		
		Help("Help","Help",false, new CMenu()),
		About("About", "Help", false, new CMenu());
		
		private String name;
		private String menuName;
		private Boolean separate;
		private CMenu invoke;
		
		private MenuItems(String name, String menuName, Boolean separate, CMenu invoke){
			this.name = name;
			this.menuName = menuName;
			this.separate = separate;
			this.invoke = invoke;
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
		public CMenu getInvoke(){
			return invoke;
		}
	};
}