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
		END;
	};
	
	public static enum EArrowHeadDirection {
		DOWN,
		LEFT,
		RIGHT;
	};
	
	public static enum EIOType {
		Input,
		Print;
	}
	
	public static enum EVarType {
		Int,
		Short,
		Float,
		Double,
		Char;
	};
	
	public static enum EControlStatementMode {
		IF_ONLY,
		IF_ELSE,
		IF_ELSEIF_ELSE,
		CASE,
		WHILE,
		DO_WHILE,
		FOR;
	};
	
	public static enum ERectDrawType {
		CODE("Code"),
		CONDITION("IF"),
		DO_WHILE("DO-WHILE"),
		WHIlE("WHILE"),
		FOR("FOR"),
		SWITCH("SWITCH"),
		CONTINUE("Continue"),
		BREAK("Break"),
		RETURN("Return");
		
		private String name;
		
		private ERectDrawType(String name) {	this.name = name;	}
		public String getName() {	return name;	}
	};
	
	public static final String[] DECISION = {"NO", "YES"};

	//Preference frame attributes
	public static final int PFRAME_X = 100;
	public static final int PFRAME_Y = 100;
	public static final int PFRAME_W = 600;
	public static final int PFRAME_H = 400;
	
	//Toolbar Attributes
	public static final int TOOLBAR_W = FRAME_W;
	public static final int TOOLBAR_H = 50;
}
