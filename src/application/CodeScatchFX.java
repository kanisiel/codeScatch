package application;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import Settings.Constants;
import Settings.Preference;
import Settings.Windows;
import Settings.Windows.InternalWindows;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.scene.control.window.CloseIcon;
import jfxtras.scene.control.window.MinimizeIcon;
import jfxtras.scene.control.window.Window;

/**
 *
 * @author Lee Junsoo
 */
public class CodeScatchFX extends Application {

	private void init(Stage primaryStage) throws Exception {
		MainUIController uiController = new MainUIController();
	    //final Group root = new Group();  
	    
	    
	    //root.getChildren().addAll(uiController);
	    primaryStage.setTitle("codeScatch v0.3");
	    primaryStage.setResizable(false);
	    primaryStage.setScene(new Scene(uiController));
	    primaryStage.setWidth(Constants.FRAME_W);
	    primaryStage.setHeight(Constants.FRAME_H);
	    
	    
	}
	
	public double getSampleWidth() {return 600;}
	public double getSampleHeight() {return 500;}
	
	private void addInternalFrames(Group root){
		for(InternalWindows item : InternalWindows.values()){
	    	
			//InternalFrame frame = item.getInternalFrame();
			Window w = new Window(item.getTitle());
	        // set the window position to 10,10 (coordinates inside canvas)
			if(item.getTitle().equals(Windows.InternalWindows.Code.getTitle())){
		        w.setLayoutX(root.getLayoutX()+76+(Constants.FRAME_W-76)/2);
		        w.setLayoutY(root.getLayoutY()+30);
			} else {
				w.setLayoutX(root.getLayoutX()+76);
		        w.setLayoutY(root.getLayoutY()+30);
			}
	        // define the initial window size
	        w.setPrefSize((Constants.FRAME_W-76)/2, Constants.FRAME_H-30);
	        // either to the left
	        w.getLeftIcons().add(new CloseIcon(w));
	        // .. or to the right
	        w.getRightIcons().add(new MinimizeIcon(w));
	        //Initialize components
	        final SwingNode swingNode = new SwingNode();
	        createAndSetSwingContent(swingNode, item.getTitle());
	        
	        // add some content
	        w.getContentPane().getChildren().add(swingNode);
	        // add the window to the canvas
	        root.getChildren().add(w);  
		} 
	}
	private void createAndSetSwingContent(final SwingNode swingNode, String window) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	if(window.equals(Windows.InternalWindows.Code.getTitle())){
            		JPanel cp;
        	        RSyntaxTextArea textArea;
        	        RTextScrollPane sp;
            		cp = new JPanel(new BorderLayout());
        	        textArea = new RSyntaxTextArea(20, 60);
        	        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        	        textArea.setCodeFoldingEnabled(true);
        	        textArea.setFont(Preference.defaultFont);
        	        sp = new RTextScrollPane(textArea);
        	        cp.add(sp);
        	        swingNode.setContent(cp);
            	}
            }
        });
    }
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	    init(primaryStage);
	    primaryStage.show();
	
	
	}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
}
