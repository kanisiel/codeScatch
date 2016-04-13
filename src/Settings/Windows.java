package Settings;

import javax.swing.JInternalFrame;

import frames.CodeViewer;
import frames.FlowChart;
import frames.TaskList;

public class Windows {
	//Internal Frame Attributes
	public static enum InternalWindows {
		Flow("Flow Chart", new FlowChart("Flow Chart")),
		Code("Code Viewer", new CodeViewer("Code Viewer")),
		Task("Task List", new TaskList("Task List"));
		
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
	public static final String includeAttrColor = "#FFFF00";
	public static final String functionColor = "#80FF00";
	
	public static enum words {
		Reserved("while|for|if|do|static"),
		Variable("int|char|double|float|struct|void");
		
		String wordList;
		private words(String wordList){
			this.wordList = wordList;
		}
		
		public String getWordList(){
			return wordList;
		}
	};
	public static final String include = "#include";
}
