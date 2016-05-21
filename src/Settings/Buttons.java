package Settings;

import Settings.Constants.EShapeType;
import shapes.CDiamondManager;
import shapes.COvalManager;
import shapes.CParallelogramManager;
import shapes.CRectangleManager;
import shapes.CShapeManager;

public class Buttons {
	public static final String defaultBG = "img/roundButton.png";
	public static final String selectedBG = "img/selected.png";
	public static enum EBUTTON {
		English("English","img/USA.jpg","img/USALabel.png"),
		Korean("Korean","img/Korean.jpg","img/KoreanLabel.png");
		
		private String name;
		private String defImg;
		private String label;
		
		private EBUTTON(String name, String defImg, String label){
			this.name = name;
			this.defImg = defImg;
			this.label = label;
		}
		
		public String getName(){	return name;	}
		public String getDefImg(){	return defImg;	}
		public String getLabel(){	return label;	}
	};
}
