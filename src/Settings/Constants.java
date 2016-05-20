package Settings;

public class Constants {
	//Main frame attributes
	public static final int FRAME_X = 100;
	public static final int FRAME_Y = 100;
	public static final int FRAME_W = 900;
	public static final int FRAME_H = 600;
	public static final int windowWidth = (FRAME_W-76)/2;
	public static final int windowHeight = FRAME_H-30;
	
	public static enum EJFrame{
		Toolbar,
		FlowChart;
	};
	
	public static enum EShapeType {
		START,
		PROCESS,
		IO,
		CONDITION,
		LOOP,
		FUNCTION,
		STOP;
	};	

	//Preference frame attributes
	public static final int PFRAME_X = 100;
	public static final int PFRAME_Y = 100;
	public static final int PFRAME_W = 600;
	public static final int PFRAME_H = 400;
	
	//Toolbar Attributes
	public static final int TOOLBAR_W = FRAME_W;
	public static final int TOOLBAR_H = 50;
	
	
	//TREE Type
	public static final int PARSEROOT = 99;
	public static final int ASSIGN = 1;
	public static final int ARITH = 2;
	public static final int IF = 3;
	public static final int FOR = 4;
	public static final int WHILE = 5;
	public static final int DO = 6;
	public static final int FUNCTION = 7;
	public static final int VDECLARE = 8;
	public static final int FDECLARE = 9;
	public static final int LOGICAL = 10;
	
	//Node Type
	public static final int NUMERIC = 1;
	public static final int OPERATOR = 2;
	public static final int VARIABLE = 3;
	
}
