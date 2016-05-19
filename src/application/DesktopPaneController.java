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
import javafx.embed.swing.SwingNode;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
	final int windowWidth = (Constants.FRAME_W-76)/2;
	final int windowHeight = Constants.FRAME_H-30;
	
    
    public DesktopPaneController(VBox desktopPane){
    	Pane canvas = new Pane();
    	desktopPane.getChildren().add(canvas);
//    	Window w = new Window("test");
//    	canvas.getChildren().add(w);
    	
    	addInternalFrames(canvas);

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
        swingNode.prefWidth(w.getWidth());
        swingNode.prefHeight(w.getHeight());
        createAndSetSwingContent(swingNode, title);
        
        // add some content
        if(w.getTitle().equals(Windows.InternalWindows.Code.getTitle())){
        	setContent(w, swingNode);
        } else {
        	FlowChartCanvas canvas = new FlowChartCanvas();
        	Image img = new Image(getClass().getResource("graph-paper2.jpg").toExternalForm());
        	BackgroundImage bi = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        	canvas.setBackground(new Background(bi));
        	canvas.init();
        	w.setContentPane(canvas);
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
        	        RSyntaxTextArea textArea;
        	        RTextScrollPane sp;
        	        textArea = new RSyntaxTextArea();
        	        textArea.setColumns(windowWidth/9);
        	        textArea.setRows(windowHeight/13);
        	        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        	        textArea.setCodeFoldingEnabled(true);
        	        textArea.setFont(Preference.defaultFont);
        	        textArea.addCaretListener(new CodeViewerListener());
        	        sp = new RTextScrollPane(textArea);
        	        cp.add(sp);
        	        swingNode.setContent(cp);
            	} 
                if(window.equals(Windows.InternalWindows.Flow.getTitle())){
                	//JPanel cp = new JPanel();
                	
                	
                	
//                	
//                	canvas.setImg(img);
//                	canvas.paint(img.getGraphics());
//        	        canvas.setVisible(true);
//        	        canvas.setBackground(new Color(0, 0, 0));
//        	        canvas.init();
//        	        cp.add(canvas);
//        	        swingNode.setContent(cp);
                	//Graphics2D g2d = (Graphics2D) new BufferedImage(windowWidth, windowHeight, java.awt.Image.SCALE_DEFAULT).createGraphics();
                    //System.out.println(g2d.);
//                	JPanel cp = new JPanel(new BorderLayout());
//                	BufferedImage bi = new BufferedImage(windowWidth, windowHeight, java.awt.Image.SCALE_DEFAULT);
//                	FlowChartPane fcp = new FlowChartPane(bi);
//                	System.out.println(fcp.getSize());
//                	
//                	fcp.setOpaque(false);
//                	fcp.setVisible(true);
//                	fcp.init();
//                	cp.add(fcp, BorderLayout.CENTER);
//                	swingNode.setContent(cp);
                	
            	}
    	        
            }
        });
    }
    
}