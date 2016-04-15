/*
 * 
 * Panel Class DesktopPane
 * 
 * Class Variable :
 * 	- frameList(LinkedHashMap) : List for internal frames
 * 	- Name(String) : Item name for display
 * 
 * Method :
 * 	- toggleFrame : For toggle internal frame's visibility
 *  - Constructor : For Initialize Class Variable at Construct
 *  - initFrames : create internal frames and sizing, positioning.
 *  
 *  Author : Lee Junsoo
 *  Last Modify : 2016/04/12
 */
package panels;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import Settings.Windows.InternalWindows;
import frames.CFrame;
import frames.CodeViewer;
import frames.FlowChart;
import frames.TaskList;

public class DesktopPane extends JDesktopPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CFrame parent;
	private TaskList taskListFrame;
	private FlowChart flowChartFrame;
	private CodeViewer codeViewerFrame;
	
	private Map<String, JInternalFrame> frameList;
	
	public DesktopPane(int width, int height) {
		super();
		//Components
		frameList = new LinkedHashMap<>();
		
		//Initializing
		this.setSize(width, height);
		initIFrames();
	}
	
	public void init(CFrame parent){
		this.parent = parent;
	}
	
	//2nd Initializing
	private void initIFrames(){
		taskListFrame = new TaskList(InternalWindows.Task.getTitle());
		flowChartFrame = new FlowChart(InternalWindows.Flow.getTitle());
		codeViewerFrame = new CodeViewer(InternalWindows.Code.getTitle());
		taskListFrame.init(this);
		flowChartFrame.init(this);
		codeViewerFrame.init(this);
		this.add(taskListFrame);
		this.add(flowChartFrame);
		this.add(codeViewerFrame);
		
	}
	
	public void toggleFrame(String title, Boolean visible){
		for(JInternalFrame frame : this.getAllFrames()){
			if(frame.getTitle().equals(title)){
				frame.setVisible(visible);
			}
		}
	}
}
