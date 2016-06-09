package application;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import models.FontChooserDialog;
/**
 *
 * @author
 *  newmacpro
 */
public class MainUIController implements Initializable {
    
	
//	@FXML 
	public DesktopPaneController desktopPaneController;
	public ToolBarController toolBarController;
	@FXML private Menu fileMenu;
	private String code = " ";
	private int trimmed = 0;
	private VBox desktopPane;
	
    public MainUIController() {}
    
    
    public void init(VBox desktopPane){//, VBox toolBar){
    	this.desktopPane = desktopPane;
    	desktopPaneController = new DesktopPaneController(desktopPane);   	
//    	toolBarController = new ToolBarController(toolBar, desktopPaneController.getFlowChartCanvas());
    	
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {}
		
	@FXML
	public void open() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select C file");
		fileChooser.setInitialDirectory(new File("."));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("C Sources (*.c)", "*.c"));
		
		File selectedFile = fileChooser.showOpenDialog(null);
				
		if (selectedFile == null) return;
		
		if(desktopPaneController.tts.getRootNode().getNodes().size()>2){
			prepare();
		}
		
		try {
			String filePath = selectedFile.getAbsolutePath();
			
			if (filePath.endsWith(".c")) {
				StringBuffer fileData = new StringBuffer();
				BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
				char[] buf = new char[1024];
		        int numRead = 0;
		        
		        while((numRead = reader.read(buf)) != -1)
		            fileData.append(String.valueOf(buf, 0, numRead));
		        
		        reader.close();
		        
		        code = fileData.toString();
		        String buffer, trim;
		        String buffers[];
				buffer = code;
				buffers = buffer.split(System.getProperty("line.separator"));
				Vector<String> trimCode = new Vector<>(); 
				for(String str : buffers){
					if(str.trim().equals("")){
						trimmed++;
						trimCode.add(str);
					} else if (str.startsWith("#")){
						trimmed++;
						trimCode.add(str);
					} else {
//						trimCode.add(str);
						break;
					}
				}
//				System.out.println(trimCode.firstElement());
				trim = String.join(System.getProperty("line.separator"), trimCode);
//				System.out.println(trim);
				buffer = buffer.replace(trim, "");
				
//				String swap = code;
//				code = buffer;
//				buffer = swap;
				desktopPaneController.textArea.setText(code);
				doParse(buffer);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
//			desktopPaneController.fcc.showAll();
		}
	}
	
	private WritableImage getSnapshot() {
		String fcCanvasWidth = String.valueOf(Math.ceil(desktopPaneController.fcc.getWidth()));
		String fcCanvasHeight = String.valueOf(Math.ceil(desktopPaneController.fcc.getHeight()));
        WritableImage image = new WritableImage(Integer.parseInt(fcCanvasWidth.substring(0, fcCanvasWidth.indexOf('.'))), 
				Integer.parseInt(fcCanvasHeight.substring(0, fcCanvasHeight.indexOf('.'))));
        
        return desktopPaneController.fcc.snapshot(new SnapshotParameters(), image);
	}
	
	@FXML
	public void save() {		
		try {
			File output = new File("snapshot.png");
            ImageIO.write(SwingFXUtils.fromFXImage(this.getSnapshot(), null), "png", output);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {}
	}
	
	public void saveAs() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select PNG file");
		fileChooser.setInitialDirectory(new File("."));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Portable Network Graphics (*.png)", "*.png"));
		
		try {
			File selectedFile = fileChooser.showSaveDialog(null);
			
			if (selectedFile.getAbsolutePath().endsWith(".png")) 
				ImageIO.write(SwingFXUtils.fromFXImage(this.getSnapshot(), null), "png", selectedFile);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {}
	}
		
	@FXML
	public void close(ActionEvent event){
		prepare();
	}
	
	@FXML
	public void background(ActionEvent event){
		MenuItem source = (MenuItem) event.getSource();
		String sourceId = source.getId();
		Image img = new Image(getClass().getResource("graph-paper2.jpg").toExternalForm());
    	BackgroundImage bi = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    	Background bg;
    	switch(sourceId){
    	case "Graph Paper":
    		bg = new Background(bi);
    		break;
    	case "Blank Paper":
    		bg = new Background(new BackgroundFill(Color.WHITE, null, null));
    		break;
    	default:
			bg = new Background(bi);
			break;
    	}
    	desktopPaneController.fcc.setBackground(bg);
//		System.out.println(sourceId);
	}
	public void prepare(){
		desktopPane.getChildren().clear();
		desktopPaneController = new DesktopPaneController(desktopPane);
		
	}
	public void doParse(String code){
		desktopPaneController.ctt.doParse(code);
	}
	@FXML
	public void exit() {
		Platform.exit();
		System.exit(0);
	}
	
	@FXML
	public void openPreference() {
		FontChooserDialog dialog = new FontChooserDialog(desktopPaneController.textArea);
		dialog.requestFocus();
	}
}
