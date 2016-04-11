package Settings;



public class Menus {
	//Menu attributes
	public static enum EMENU {
		File("File", FileMenu.values(), FileMenu.class),//,new CMenu()),//"File", EFILEMENUITEM.values())),
		Edit("Edit", EditMenu.values(), EditMenu.class),//,new CMenu()),//"Edit",EEDITMENUITEM.values())),
		View("View", ViewMenu.values(), ViewMenu.class),
		Window("Window", WindowMenu.values(),WindowMenu.class),//,new CMenu());//"Color",ECOLORMENUITEM.values()));
		Help("Help", HelpMenu.values(), HelpMenu.class);//, EHELPMENUITEM.values(),new CHelpMenu());
		
		
		private String name;
		private Enum<?>[] menuItems;
		private Class classObject;
		private EMENU(String name, Enum<?>[] menuItems, Class classObject){//, CMenu menu){
			this.name=name;
			this.menuItems=menuItems;
			this.classObject = classObject;
		}
		public String getName(){	return name;	}
		public Enum<?>[] getmenuItems(){	return menuItems;	}
		public Class getClassObject(){	return classObject; }
	};
	public static enum MenuItems {
		New("New", "File", true),
		Open("Open", "File", true),
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
		CodeEditor("Code Editor", "Window", false),
		Browser("Browser", "Window", false),
		Help("Help","Help",false),
		About("About", "About", false);
		
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
	public static enum FileMenu {
		New("New", 0, true),
		Open("Open", 1, true),
		Save("Save", 2, false),
		SaveAs("Save as", 2, true),
		Close("Close", 3, false),
		Exit("Exit", 3, false);
		
		private String name;
		private int label;
		private Boolean separate;
		
		private FileMenu(String name, int label, Boolean separate){
			this.name = name;
			this.label = label;
			this.separate = separate;
		}
		public Boolean getSeparate(){
			return separate;
		}
		public int getLabel(){
			return label;
		}
		public String getName(){
			return name;
		}
	};
	public static enum EditMenu{
		Undo("Undo", 0, false),
		Redo("Redo", 0 , true),
		Copy("Copy", 1, false),
		Cut("Cut", 1, false),
		Paste("Paste", 1, false);
		
		private String name;
		private int label;
		private Boolean separate;
		
		private EditMenu(String name, int label, Boolean separate){
			this.name = name;
			this.label = label;
			this.separate = separate;
		}
		public Boolean getSeparate(){
			return separate;
		}
		public int getLabel(){
			return label;
		}
		public String getName(){
			return name;
		}
	};
	public static enum ViewMenu{
		FullScreen("Full Screen", 0, false),
		Minimize("Minimize",0,false),
		WindowMode("Window Mode", 0, false);
		
		private String name;
		private int label;
		private Boolean separate;
		
		private ViewMenu(String name, int label, Boolean separate){
			this.name = name;
			this.label = label;
			this.separate = separate;
		}
		public Boolean getSeparate(){
			return separate;
		}
		public int getLabel(){
			return label;
		}
		public String getName(){
			return name;
		}
	};
	public static enum WindowMenu{
		FlowChart("Flow Chart",0, false),
		CodeEditor("Code Editor", 0, false),
		Browser("Browser", 0, false);
		
		private String name;
		private int label;
		private Boolean separate;
		
		private WindowMenu(String name, int label, Boolean separate){
			this.name = name;
			this.label = label;
			this.separate = separate;
		}
		public Boolean getSeparate(){
			return separate;
		}
		public int getLabel(){
			return label;
		}
		public String getName(){
			return name;
		}
	};

	public static enum HelpMenu{
		Help("Help",0,false),
		About("About", 0, false);
		
		private String name;
		private int label;
		private Boolean separate;
		
		private HelpMenu(String name, int label, Boolean separate){
			this.name = name;
			this.label = label;
			this.separate = separate;
		}
		public Boolean getSeparate(){
			return separate;
		}
		public int getLabel(){
			return label;
		}
		public String getName(){
			return name;
		}
	};
}
