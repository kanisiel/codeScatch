package adapter;

import Settings.Menus.EditMenu;
import Settings.Menus.FileMenu;

public class MenuAdapter {
	private FileMenu fileMenu;
	private EditMenu editMEnu;
	
	public Enum[] ConvertType(Enum<?>[] eNum){
		if(eNum.toString().contains("FileMenu")){
			return FileMenu.values();
		} else if (eNum.toString().contains("EditMenu")){
			return EditMenu.values();
		} else return FileMenu.values();
	}
}
