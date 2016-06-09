package application;

import java.awt.BorderLayout;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import Settings.Constants;
import Settings.Preference;
import Settings.Windows;
import Settings.Windows.InternalWindows;
import adapter.CodeToTree;
import adapter.TreeToShape;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingNode;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import jfxtras.scene.control.window.CloseIcon;
import jfxtras.scene.control.window.MinimizeIcon;
import jfxtras.scene.control.window.Window;

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
	public CodeToTree ctt;
	public Pane pane;
	private VBox desktopPane;
	public Map<String, Window> windows;
	public Map<String, Double> paneBound;
	private Pane canvas;
	
    
    public DesktopPaneController(VBox desktopPane){
    	this.windows = new LinkedHashMap<>();
    	this.setDesktopPane(desktopPane);
    	canvas = new Pane();
    	desktopPane.getChildren().add(canvas);
//    	Window w = new Window("test");
//    	canvas.getChildren().add(w);
    	
    	addInternalFrames(canvas);
    	this.tts = new TreeToShape(fcc);
    	this.ctt = new CodeToTree(tts);
    }

    private void addInternalFrames(Pane canvas){
    	//Pane p = new Pane(swingNode);
		for(InternalWindows item : InternalWindows.values()){
	    	
			Window w = createWindow(item.getTitle());
			windows.put(w.getTitle(), w);
	        // add Node
	        canvas.getChildren().add(w);
		} 
	}
    public void revalidate(String title){
    	canvas.getChildren().remove(0);
    	windows.remove(title);
    	Window w = createWindow(title);
    	windows.replace(title, w);
    	canvas.getChildren().add(0, w);
    	this.tts = new TreeToShape(fcc);
    	this.ctt = new CodeToTree(tts);
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
					double diff = newValue.doubleValue() - oldValue.doubleValue();
					if(fcc.getPrefWidth()<newValue.doubleValue()){
						sp.setPrefWidth(newValue.doubleValue());
						fcc.setPrefWidth(newValue.doubleValue());
						paneBound.put("width", fcc.getPrefWidth());
					}
					fcc.setCenterLineX(newValue.doubleValue()/2);
//					for(Node node : fcc.getChildren()){
//						if(node.getClass().equals(Group.class)){
//							node.setLayoutX(node.getLayoutX()+(diff/2));
//						}
//					}
				}
			});
        	w.heightProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					if(fcc.getPrefHeight()<newValue.doubleValue()){
						sp.setPrefHeight(newValue.doubleValue());
						fcc.setPrefHeight(newValue.doubleValue());
						paneBound.put("width", fcc.getPrefHeight());
					}
				}
			});
        	pane = new Pane();
        	sp = new ScrollPane();
        	fcc = new FlowChartCanvas(this);
//        	fcc.minWidthProperty().bind(w.prefWidthProperty());
        	fcc.minHeightProperty().bind(w.prefHeightProperty());
        	fcc.prefWidthProperty().bind(w.prefWidthProperty());
        	fcc.getChildren().addListener(new ListChangeListener<Node>(){
				@Override
				public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c) {
					fcc.setPrefHeight(fcc.height);
					
					if(fcc.getPrefWidth()<w.getPrefWidth()){
						fcc.prefWidth(w.getPrefWidth());
					}
//					pane.getChildren().set(0, fcc);
					sp.setPrefHeight(windowHeight-30);
					sp.setPrefWidth(windowWidth);
					sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
					sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
					
				}
        	});
        	paneBound = new LinkedHashMap<>();
    		Image img = new Image(getClass().getResource("graph-paper2.jpg").toExternalForm());
        	BackgroundImage bi = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);		
        	pane.setBackground(new Background(bi));
        	pane.getChildren().add(fcc);
        	pane.minWidthProperty().bind(fcc.prefWidthProperty());
        	pane.setPrefWidth(w.getPrefWidth());
        	paneBound.put("width", pane.getPrefWidth());
        	paneBound.put("height", pane.getPrefHeight());
        	pane.setPrefHeight(fcc.getPrefHeight());
        	pane.minHeightProperty().bind(fcc.prefHeightProperty());
        	pane.setUserData(paneBound);
        	sp.setContent(pane);
        	Scale scale = new Scale();
        	ZoomPane zp = new ZoomPane(scale, pane);
        	BorderPane p = new BorderPane();
        	p.setCenter(sp);
        	p.setBottom(zp);
        	zp.setMaxHeight(10);
        	fcc.getTransforms().add(scale);
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
//        	        textArea.addCaretListener(new CodeViewerListener(tts));
        	        textArea.setEditable(false);
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

	public CodeToTree getCtt() {
		return ctt;
	}

	public void setCtt(CodeToTree ctt) {
		this.ctt = ctt;
	}

	public VBox getDesktopPane() {
		return desktopPane;
	}

	public void setDesktopPane(VBox desktopPane) {
		this.desktopPane = desktopPane;
	}

	public TreeToShape getTts() {
		return tts;
	}

	public void setTts(TreeToShape tts) {
		this.tts = tts;
	}

	public FlowChartCanvas getFcc() {
		return fcc;
	}

	public void setFcc(FlowChartCanvas fcc) {
		this.fcc = fcc;
	}

	public Map<String, Window> getWindows() {
		return windows;
	}

	public void setWindows(Map<String, Window> windows) {
		this.windows = windows;
	}
}