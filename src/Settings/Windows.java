package Settings;

public class Windows {
	public static String titleArray[] = 
		{"Flow Chart", "Code Viewer", "Task List"};
	
	public static enum InternalWindows {
		Flow("Flow Chart"),
		Code("Code Viewer"),
		Task("Task List");
		
		String title;
		private InternalWindows(String title){
			this.title = title;
		}
		
		public String getTitle(){
			return this.title;
		}
	};
}
