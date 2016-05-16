package Settings;

public class Constants {
	//Main frame attributes
	public static final int FRAME_X = 100;
	public static final int FRAME_Y = 100;
	public static final int FRAME_W = 900;
	public static final int FRAME_H = 600;
	
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
	
	public static enum EArrowHeadDirection {
		DOWN,
		LEFT,
		RIGHT;
	};

	//Preference frame attributes
	public static final int PFRAME_X = 100;
	public static final int PFRAME_Y = 100;
	public static final int PFRAME_W = 600;
	public static final int PFRAME_H = 400;
	
	//Toolbar Attributes
	public static final int TOOLBAR_W = FRAME_W;
	public static final int TOOLBAR_H = 50;
}
