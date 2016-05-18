package Settings;

import java.awt.Font;

import panels.FontPanel;
import panels.Language;
import panels.PreferenceDetailPanel;

public class Preference {
	
	public static final String settingFile = "setting.ini";
	public static final Font defaultFont = new Font("NanumGothic", Font.PLAIN, 9);
	public static final String fontSizes[] = { "8", "10", "11", "12", "14", "16", "18", "20", "24", "30", "36", "40", "48", "60", "72" };
	
	public static final int preferenceDetailPanel_W = (Constants.PFRAME_W/3)*2;
	public static final int preferenceDetailPanel_H = Constants.PFRAME_H-30;
	public static final String preferenceItems[] = {
			"General", "Code Viewer", "Flow Chart", "Task List"
	};
	
	public static final String preferenceItemsChild[] = {
			 "Language", "Fonts" 
	};
	
//	public static final PreferenceDetailPanel panelArray[][] = {
//			{ new PreferenceDetailPanel((Constants.FRAME_W/3)*2, Constants.PFRAME_H)  }
//	};
	public static enum preferenceItemsChilds {
		GL(preferenceItems[0], preferenceItemsChild[0], new Language(preferenceItems[0], preferenceItemsChild[0], preferenceDetailPanel_W, preferenceDetailPanel_H-30)),
		CF(preferenceItems[1], preferenceItemsChild[1], new FontPanel(preferenceItems[1], preferenceItemsChild[1], preferenceDetailPanel_W, preferenceDetailPanel_H-30)),
		FF(preferenceItems[2], preferenceItemsChild[1], new FontPanel(preferenceItems[2], preferenceItemsChild[1], preferenceDetailPanel_W, preferenceDetailPanel_H-30)),
		TF(preferenceItems[3], preferenceItemsChild[1], new FontPanel(preferenceItems[3], preferenceItemsChild[1], preferenceDetailPanel_W, preferenceDetailPanel_H-30));
		
		String parent;
		String itemName;
		PreferenceDetailPanel panel;
		private preferenceItemsChilds(String parent, String itemName, PreferenceDetailPanel panel){
			this.parent = parent;
			this.itemName = itemName;
			this.panel = panel;
		}
		public String getParent(){
			return parent;
		}
		public String getItemName(){
			return itemName;
		}
		public PreferenceDetailPanel getPanel(){
			return panel;
		}
	};
}
