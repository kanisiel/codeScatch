package application;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import com.sibvisions.rad.ui.javafx.ext.mdi.FXInternalWindow;
import com.sibvisions.rad.ui.javafx.ext.mdi.windowmanagers.FXDesktopWindowManager;

import Settings.Constants;
import Settings.Preference;
import Settings.Windows;
import Settings.Windows.InternalWindows;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class DesktopPaneController extends VBox {
    
//    @FXML
//    private FXDesktopPane fxDesktopPane;
//   
//    private FXDesktopWindowManager manager;
    
    public DesktopPaneController(){
//    	FXMLLoader fxmlLoader = new FXMLLoader(DesktopPaneController.class.getResource("DesktopPane.fxml"));
//    	//fxmlLoader.setRoot(this);
//    	fxmlLoader.setController(this);
//    	
//    	try{
//    		fxmlLoader.load();
    	
//    		manager = new FXDesktopWindowManager();
//            fxDesktopPane = new FXDesktopPane();
//            
//            fxDesktopPane.setVisible(true);
//    		addInternalFrames(manager);
    		
//    	} catch(IOException e){
//    		throw new RuntimeException(e);
//    	}
    }
    
    private void addInternalFrames(FXDesktopWindowManager manager){
		for(InternalWindows item : InternalWindows.values()){
	    	
			//InternalFrame frame = item.getInternalFrame();
			FXInternalWindow w = new FXInternalWindow(item.getTitle());
	        // set the window position to 10,10 (coordinates inside canvas)
			if(item.getTitle().equals(Windows.InternalWindows.Code.getTitle())){
		        w.setLayoutX((Constants.FRAME_W-76)/2);
		        w.setLayoutY(0);
			} else {
				w.setLayoutX(0);
		        w.setLayoutY(0);
			}
	        // define the initial window size
	        w.setPrefSize((Constants.FRAME_W-76)/2, Constants.FRAME_H-30);
//	        // either to the left
//	        w.getLeftIcons().add(new CloseIcon(w));
//	        // .. or to the right
//	        w.getRightIcons().add(new MinimizeIcon(w));
	        //Initialize components
	        final SwingNode swingNode = new SwingNode();
	        createAndSetSwingContent(swingNode, item.getTitle());
	        
	        // add some content	       
	       // w.getContent().add(swingNode);
	        // add the window to the canvas
	        setContent(w, swingNode);
	        w.setVisible(true);
	        System.out.println(w);
	        manager.addWindow(w);
		} 
	}
    private void setContent(FXInternalWindow window, SwingNode swingNode){
        Pane p = new Pane(swingNode);
        window.setContent(p);
	
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
    
}