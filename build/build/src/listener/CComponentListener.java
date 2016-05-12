/*
 * 
 * Listener Class CComponentListener
 * 
 * Class Variable :
 * 	- desktopPane(DesktopPane) : parent component;
 * 
 * Method :
 * 	- init : set parent component by parent's call
 *  - Overriding Methods : override.
 *  	@componentResized : resizing parent's internal frames
 *  
 *  Author : Lee Junsoo
 *  Last Modify : 2016/04/12
 */
package listener;

import java.awt.event.ComponentEvent;

import javax.swing.JInternalFrame;

import Settings.Windows.InternalWindows;
import panels.DesktopPane;

public class CComponentListener implements java.awt.event.ComponentListener {
	
	//parent component
	private DesktopPane desktopPane;

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		for(JInternalFrame frame : desktopPane.getAllFrames()){
			int ordinal = InternalWindows.valueOf(frame.getTitle().substring(0, 4)).ordinal();
			if(ordinal == 0){
				frame.setLocation(0, -5);
			} else {
				frame.setLocation((e.getComponent().getWidth()/2)-20, -5);
			}
			frame.setSize((e.getComponent().getWidth()/2), e.getComponent().getHeight() - 60);
		}
		desktopPane.repaint();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}
	
	public void init(DesktopPane desktopPane){
		this.desktopPane = desktopPane;
	}
}
