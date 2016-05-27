package Settings;

public class Constants {
	//Main frame attributes
	public static final int FRAME_X = 100;
	public static final int FRAME_Y = 100;
	public static final int FRAME_W = 900;
	public static final int FRAME_H = 600;
	public static final int windowWidth = (FRAME_W-76)/2;
	public static final int windowHeight = FRAME_H-80;
	public static final double centerAxisX = (Constants.windowWidth/2);
	public static final String EAST = "EAST";
	public static final String WEST = "WEST";
	public static final String SOUTH = "SOUTH";
	public static final String NORTH = "NORTH";
	
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
	public static final int CODE = 1;
	public static final int IF = 2;
	public static final int FOR = 3;
	public static final int WHILE = 4;
	public static final int DO = 5;
	public static final int SWITCH = 6;
	
	//Node Type
	public static final int NUMERIC = 1;
	public static final int OPERATOR = 2;
	public static final int VARIABLE = 3;
	
}
