package javaFX;
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

import Settings.Preference;
import Settings.Windows;
import Settings.Windows.InternalWindows;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
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
    
    private static int counter = 1;

	private void init(Stage primaryStage) throws Exception {
		Parent main = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
	    final Group root = new Group();  
	
	    root.getChildren().addAll(main);
            primaryStage.setTitle("codeScatch v0.3");
	    primaryStage.setResizable(false);
	    primaryStage.setScene(new Scene(root, 600, 500));
	    for(InternalWindows item : InternalWindows.values()){
	    	
			//InternalFrame frame = item.getInternalFrame();
			Window w = new Window(item.getTitle());
	        // set the window position to 10,10 (coordinates inside canvas)
	        w.setLayoutX(10);
	        w.setLayoutY(10);
	        // define the initial window size
	        w.setPrefSize(300, 200);
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
	
	public double getSampleWidth() {return 600;}
	public double getSampleHeight() {return 500;}
	
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
