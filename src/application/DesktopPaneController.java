package application;

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
import adapter.TreeToShape;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingNode;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import jfxtras.scene.control.window.CloseIcon;
import jfxtras.scene.control.window.MinimizeIcon;
import jfxtras.scene.control.window.Window;
import listener.CodeViewerListener;

public class DesktopPaneController extends VBox {
    
//    @FXML
//    private FXDesktopPane fxDesktopPane;
//   
//    private FXDesktopWindowManager manager;
	final int windowWidth = (Constants.FRAME_W)/2;
	final int windowHeight = Constants.FRAME_H-50;
	public FlowChartCanvas fcc;
	public RSyntaxTextArea textArea;
	public ScrollPane sp;
	public TreeToShape tts;
	
    
    public DesktopPaneController(VBox desktopPane){
    	Pane canvas = new Pane();
    	desktopPane.getChildren().add(canvas);
//    	Window w = new Window("test");
//    	canvas.getChildren().add(w);
    	
    	addInternalFrames(canvas);
    	this.tts = new TreeToShape(fcc);
    }

    private void addInternalFrames(Pane canvas){
    	//Pane p = new Pane(swingNode);
		for(InternalWindows item : InternalWindows.values()){
	    	
			Window w = createWindow(item.getTitle());

	        // add Node
	        canvas.getChildren().add(w);
		} 
	}
    private Window createWindow(String title){
    	//InternalFrame frame = item.getInternalFrame();
		Window w = new Window(title);
        // set the window position to 10,10 (coordinates inside canvas)
		if(title.equals(Windows.InternalWindows.Code.getTitle())){
	        w.setLayoutX((Constants.FRAME_W)/2);
	        w.setLayoutY(0);
		} else {
//			Image img = new Image(getClass().getResource("graph-paper2.jpg").toExternalForm());
//			BackgroundImage bgImage = new BackgroundImage(img, null, null, null, null);
//			Background bg = new Background(bgImage);
//			w.setBackground(bg);
			w.setLayoutX(0);
	        w.setLayoutY(0);
		}
        // define the initial window size
        w.setPrefSize(windowWidth, windowHeight);
        // either to the left
        w.getLeftIcons().add(new CloseIcon(w));
        // .. or to the right
        w.getRightIcons().add(new MinimizeIcon(w));
 
        //Initialize components
        SwingNode swingNode = new SwingNode();
        createAndSetSwingContent(swingNode, title);
        
        // add some content
        if(w.getTitle().equals(Windows.InternalWindows.Code.getTitle())){
        	setContent(w, swingNode);
        } else {
        	w.widthProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					fcc.setPrefWidth(w.widthProperty().get()-5);					
				}
			});
        	w.heightProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					fcc.setPrefHeight(w.heightProperty().get()-30);					
				}
			});
//        	Pane p = new Pane();
        	sp = new ScrollPane();
        	fcc = new FlowChartCanvas();
        	fcc.getChildren().addListener(new ListChangeListener<Node>(){
				@Override
				public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c) {
					fcc.setPrefHeight(fcc.height);
					sp.setContent(null);
					sp.setContent(fcc);
					sp.setPrefHeight(windowHeight-30);
					sp.setPrefWidth(windowWidth);
					sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
					sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
					
				}
        	});
        	BorderPane p = new BorderPane(sp);
        	w.setContentPane(p);
        }
        w.setVisible(true);
        
        return w;
    }
    private void setContent(Window w, SwingNode swingNode){
        BorderPane p = new BorderPane(swingNode);
        w.setContentPane(p);
	
    }
    private void createAndSetSwingContent(SwingNode swingNode, String window) {
    	
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	if(window.equals(Windows.InternalWindows.Code.getTitle())){
                    JPanel cp = new JPanel(new BorderLayout());
        	        RTextScrollPane sp;
        	        textArea = new RSyntaxTextArea();
        	        textArea.setColumns(windowWidth/9);
        	        textArea.setRows(windowHeight/13);
        	        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        	        textArea.setCodeFoldingEnabled(true);
        	        textArea.setFont(Preference.defaultFont);
        	        textArea.addCaretListener(new CodeViewerListener(tts));
        	        textArea.setEditable(true);
        	        sp = new RTextScrollPane(textArea);
        	        cp.add(sp);
        	        swingNode.setContent(cp);
            	}
    	        
            }
        });
    }
    public FlowChartCanvas getFlowChartCanvas(){
    	return fcc;
    }
}