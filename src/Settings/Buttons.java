package Settings;

import Settings.Constants.EShapeType;
import shapes.CControlStatementManager;
import shapes.COvalManager;
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
	
	/*
	 * Enumeration for Buttons on ToolBar
	 */
	public static enum EToolBarButton {
		PROCESS("Process", "img/process.png", "img/process_SLT.png", new CRectangleManager(EShapeType.PROCESS)),
		IO("Input/Output", "img/io.png", "img/io_SLT.png", new CRectangleManager(EShapeType.IO)),
		CONDITION("Condition", "img/condition.png", "img/condition_SLT.png", new CControlStatementManager(EShapeType.CONDITION)),
		LOOP("Loop", "img/loop.png", "img/loop_SLT.png", new CControlStatementManager(EShapeType.LOOP)),
		//FUNCTION("Function", "img/fx.png", "img/fx_SLT.png", new COvalManager(EShapeType.FUNCTION)),
		STOP("Stop", "img/stop.png", "img/stop_SLT.png", new COvalManager(EShapeType.END));
		
		private String name;
		private String iconDefName;		// Icon's image default path
		private String iconSLTName;		// Icon's image path when it's selected
		private CShapeManager shape;	// Shape Type
		
		private EToolBarButton(String name, String iconDefName, String iconSLTName, CShapeManager shape) {
			this.name = name;
			this.iconDefName = iconDefName;
			this.iconSLTName = iconSLTName;
			this.shape = shape;
		}
		
		public String getName() {	return name;	}
		public String getIconDefName() {	return iconDefName;		}
		public String getIconSLTName() {	return iconSLTName;		}
		public CShapeManager getShape() {	return shape;		}
	}
}
