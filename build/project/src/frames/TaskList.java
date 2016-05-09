package frames;

import java.awt.BorderLayout;

import Settings.Windows;

public class TaskList extends InternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TaskList() {
        super(Windows.InternalWindows.Task.getTitle());//iconifiable

        
        //...Create the GUI and put it in the window...

		this.getContentPane().setLayout(new BorderLayout());
	}
//	@Override
//	public void init(DesktopPane parent){
//		this.parent = parent;
//		this.setLocation(0,0);
//		this.setSize(parent.getWidth()/2, parent.getHeight());
//		//this.setVisible(true);
//	}
	
}
