package frames;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;

public class FlowChart extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FlowChart(String title) {
        super(title, 
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable

		//System.out.println(InternalWindows.Flow.getTitle());
        //...Create the GUI and put it in the window...
 
        //...Then set the window size or call pack...
        setSize(300,300);
 
        //Set the window's location.
        setLocation(0, 0);

		this.getContentPane().setLayout(new BorderLayout());
	}
	
}
