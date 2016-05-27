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
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
import jfxtras.scene.control.window.CloseIcon;
import jfxtras.scene.control.window.MinimizeIcon;
import jfxtras.scene.control.window.Window;
import listener.CodeViewerListener;

public class DesktopPaneController extends VBox {
    
//    @FXML
//    private FXDesktopPane fxDesktopPane;
//   
//    private FXDesktopWindowManager manager;
	final int windowWidth = (Constants.FRAME_W-76)/2;
	final int windowHeight = Constants.FRAME_H-50;
	public FlowChartCanvas fcc;
	public RSyntaxTextArea textArea;
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
	        w.setLayoutX((Constants.FRAME_W-76)/2);
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
//        swingNode.prefWidth(w.getPrefWidth());
//        swingNode.prefHeight(w.getPrefHeight());
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
        	Pane p = new Pane();
        	fcc = new FlowChartCanvas();
        	ScrollBar sc = new ScrollBar();
        	sc.setLayoutX(w.getPrefWidth()-sc.getWidth());
        	sc.setMin(0);
        	sc.setOrientation(Orientation.VERTICAL);
        	sc.setPrefHeight(w.getPrefHeight()-30);
//             sc.setMax(3000);
//        	sc.visibleAmountProperty().set(80);
        	sc.valueProperty().addListener(new ChangeListener<Number>() {
        		public void changed(ObservableValue<? extends Number> ov,
                     Number old_val, Number new_val) {
                	 for(javafx.scene.Node n : fcc.getChildren()){
        	 			int index = fcc.getChildren().indexOf(n);
        	 			n.setLayoutY(-new_val.doubleValue());
        	 			Group g = (Group) n;
    	 				Shape s = (Shape) g.getChildren().get(0);
	 					if(index == 0 || index == fcc.getChildren().size()-1){
        	 				if(Double.parseDouble(s.getId()) + n.getLayoutY()<0){
                	 			n.setVisible(false);
                	 		} else {
                	 			n.setVisible(true);
                	 		}
    	 				}
            	 		 else {
            	 			if(Double.parseDouble(s.getId()) + n.getLayoutY()<=0){
                	 			n.setVisible(false);
                	 		} else {
                	 			n.setVisible(true);
                	 		}
            	 		}	
        	 		}
                 }
        	});
        	fcc.getChildren().addListener(new ListChangeListener<Node>(){

				@Override
				public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c) {
//					if(c.getList().size()>2){
//					c.getList().get(c.getList().size()-1);
//					System.out.println();
					double thumb = 0;
					if(fcc.getManager().getNodes().size()>0){
						double bottom = fcc.getManager().getNodes().lastElement().getP().getY();
						thumb = sc.getPrefHeight()/bottom;
						if(thumb < 1){
							sc.visibleAmountProperty().set(100*thumb);
							sc.setMax(100*bottom/sc.getPrefHeight());
						}else {
							sc.visibleAmountProperty().set(100);
							sc.setMax(100);
						}
						
						//sc.setMax(bottom);
					}
					
				}
        	
        	});
        	p.getChildren().addAll(fcc, sc);
        	w.setContentPane(p);
        }
        w.setVisible(true);
        
        return w;
    }
    private void setContent(Window w, SwingNode swingNode){
        Pane p = new Pane(swingNode);
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