package Settings;



public class Menus {
	//Menu attributes
	public static enum EMENU {
		File("File", FileMenu.values()),//,new CMenu()),//"File", EFILEMENUITEM.values())),
		Edit("Edit", EditMenu.values()),//,new CMenu()),//"Edit",EEDITMENUITEM.values())),
		View("View", FileMenu.values()),
		Window("Window", FileMenu.values()),//,new CMenu());//"Color",ECOLORMENUITEM.values()));
		Help("Help", FileMenu.values());//, EHELPMENUITEM.values(),new CHelpMenu());
		
		
		private String name;
		private Enum<?>[] menuItems;
		private EMENU(String name, Enum<?>[] menuItems){//, CMenu menu){
			this.name=name;
			this.menuItems=menuItems;
		}
		public String getName(){	return name;	}
		public Enum<?>[] getmenuItems(){	return menuItems;	}
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
	}
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
	}
}
