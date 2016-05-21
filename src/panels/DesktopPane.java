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
//		for(InternalWindows item : InternalWindows.values()){
//			InternalFrame frame = item.getInternalFrame();
//			//frame.init(this);
//			frameList.put(item.getTitle(), frame);
//			this.add(frame);
//			if(item.name().equals("Code")){
//				codeViewerFrame = (CodeViewer) frame;
//			} else if(item.name().equals("Flow")){
//				flowChartFrame = (FlowChart) frame;
//			} else if(item.name().equals("Task")){
//				taskListFrame = (TaskList) frame;
//			}
//		}
	}
	
	public void init(CFrame parent){
		this.setParent(parent);
		
	}
	
	public void toggleFrame(String title, Boolean visible){
		for(JInternalFrame frame : this.getAllFrames()){
			if(frame.getTitle().equals(title)){
				frame.setVisible(visible);
			}
		}
	}

	public CodeViewer getCodeViewerFrame() {
		return codeViewerFrame;
	}

	public FlowChart getFlowChartFrame() {
		return flowChartFrame;
	}

	public TaskList getTaskListFrame() {
		return taskListFrame;
	}

	public CFrame getParent() {
		return parent;
	}

	public void setParent(CFrame parent) {
		this.parent = parent;
	}

}
