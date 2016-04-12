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

public class DesktopPane extends JDesktopPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Map<String, JInternalFrame> frameList;
	
	public DesktopPane(int width, int height) {
		super();
		//Components
		frameList = new LinkedHashMap<>();
		
		//Initializing
		this.setSize(width, height);
		initIFrames();
	}
	
	//2nd Initializing
	private void initIFrames(){
		for(InternalWindows window : InternalWindows.values()){
			JInternalFrame frame =  window.getInternalFrame();
			int order = window.ordinal() % 2;
			if(order==0){
				frame.setLocation(0,0);
				frame.setSize(this.getWidth()/2, this.getHeight());
			} else {
				frame.setLocation(this.getWidth()/2, this.getHeight());
				frame.setSize(this.getWidth()/2, this.getHeight());
			}
			frameList.put(window.getTitle(), frame);
		}
		for(JInternalFrame frame : frameList.values()){
			//For only two frame to visible.
			if(frame.getTitle().equals(InternalWindows.Task.getTitle()) == false){
				frame.setVisible(true);
			}
			this.add(frame);
		}
	}
	
	public void toggleFrame(String title, Boolean visible){
		for(JInternalFrame frame : this.getAllFrames()){
			if(frame.getTitle().equals(title)){
				frame.setVisible(visible);
			}
		}
	}
}
