package Settings;

import javax.swing.JInternalFrame;

import frames.CodeViewer;
import frames.FlowChart;

public class Windows {
	//Internal Frame Attributes
	public static enum InternalWindows {
		Flow("Flow Chart", new FlowChart("Flow Chart")),
		Code("Code Viewer", new CodeViewer("Code Viewer")),
		Task("Task List", new JInternalFrame("Task List", true, true, true, true));
		
		String title;
		JInternalFrame frame;
		//Constructor
		private InternalWindows(String title, JInternalFrame frame){
			this.title = title;
			this.frame = frame;
		}
		
		public String getTitle(){
			return this.title;
		}
		
		public JInternalFrame getInternalFrame(){
			return frame;
		}
	};
	//Code Viewer Attributes
	public static final String highLightColor = "#CCE5FF"; 
	public static final String reservedWordColor = "#990099";
	public static final String variableColor = "#004C99";
	public static final String stringColor = "#0000CC";
	public static final String includeColor = "#CC0000";
	
	public static final String reservedWord[] = {
			"int", "char", "Double", "Float", "while", "for", "if", "do", "static", "struct"
	};
	public static final String include = "#include";
}
