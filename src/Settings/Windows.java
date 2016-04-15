package Settings;

public class Windows {
	//Internal Frame Attributes
	public static enum InternalWindows {
		Flow("Flow Chart"),
		Code("Code Viewer"),
		Task("Task List");
		
		String title;
		//Constructor
		private InternalWindows(String title){
			this.title = title;
		}
		
		public String getTitle(){
			return this.title;
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
