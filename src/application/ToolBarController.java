package application;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import frames.CToolbar;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ToolBarController extends VBox {
	
	public ToolBarController(VBox toolBar){
    	Pane canvas = new Pane();
    	toolBar.getChildren().add(canvas);
    	addToolbar(canvas);
	}
	
	public void addToolbar(Pane canvas){
		SwingNode swingNode = new SwingNode();
		createAndSetSwingContent(swingNode);
		canvas.getChildren().add(swingNode);
		
	}
private void createAndSetSwingContent(final SwingNode swingNode) {
    	
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	JPanel cp = new JPanel();
            	CToolbar toolbar = new CToolbar();
            	cp.add(toolbar);
            	swingNode.setContent(cp);
            }
        });
    }
}
