package panels;

import java.util.Vector;

import javax.swing.JDesktopPane;

import Settings.Windows;
import Settings.Windows.InternalWindows;
import frames.InternalFrame;

public class DesktopPane extends JDesktopPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<InternalFrame> frameList;
	
	public DesktopPane() {
		super();
		frameList = new Vector<>();
		initIFrames();
	}
	String titleArray[] = Windows.titleArray;
	
	private void initIFrames(){
		for(InternalWindows window : InternalWindows.values()){
			String title = window.getTitle();
			System.out.println(title);
			frameList.add(new InternalFrame(title));
		}
		for(InternalFrame iframe : frameList){
	        iframe.setVisible(true);
			this.add(iframe);
		}
	}
	
	public void toggleFrame(String title){
		for(InternalFrame frame : frameList){
			if(frame.getTitle().equals(title)){
				if(frame.isVisible()){
					frame.setVisible(false);
				} else {
					frame.setVisible(true);
				}
			}
		}
	}
}
