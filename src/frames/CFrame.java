package frames;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import Menus.CMenuBar;
import Settings.Constants;
import listner.CComponentListener;
import panels.DesktopPane;
import panels.StatusBar;

public class CFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//components
	private CMenuBar menuBar;
	private CToolbar toolbar;
	private StatusBar statusBar;
	private DesktopPane desktopPane;
	private CComponentListener componentListener;
	
	
	public CFrame(){
		// attributes initialization
		this.setTitle("codeScatch v0.1a");
		this.setSize(Constants.FRAME_W,Constants.FRAME_H);
		this.setLocation(Constants.FRAME_X, Constants.FRAME_Y);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		
		//components initialization
		this.menuBar = new CMenuBar();
		this.menuBar.init(this);
		this.setJMenuBar(menuBar);
		this.getContentPane().setLayout(new BorderLayout());
		this.statusBar = new StatusBar();
		this.getContentPane().add(statusBar, BorderLayout.SOUTH);
		this.toolbar = new CToolbar();
		this.add(this.toolbar, BorderLayout.WEST);
		this.desktopPane = new DesktopPane(this.getWidth(), this.getHeight());
		this.add(desktopPane, BorderLayout.CENTER);
		this.componentListener = new CComponentListener();
		this.componentListener.init(this.desktopPane);
		this.addComponentListener(componentListener);

		
	}
	//2nd phase initialization
	public void init(){
		//components association initialization
//		this.menuBar = (CMenuBar) this.getJMenuBar();
//		this.menuBar.init(drawingPanel, this); //this.menuBar.init(drawingPanel);
//		this.toolbar.init(drawingPanel);
//		this.drawingPanel.init();
				
		//associated attributes initialization
		this.setVisible(true);
		this.statusBar.setText("Welcome!");
		try {
			
			//sleep 5 seconds
			Thread.sleep(5000);
			
			this.statusBar.clearText();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
