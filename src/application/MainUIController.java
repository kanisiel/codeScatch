package application;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
/**
 *
 * @author newmacpro
 */
public class MainUIController implements Initializable {
    
	
//	@FXML 
	public DesktopPaneController desktopPaneController;
	public ToolBarController toolBarController;
	@FXML private Menu fileMenu;
	private String code = " ";
    public MainUIController() {}
    
    
    public void init(VBox desktopPane){//, VBox toolBar){
    	desktopPaneController = new DesktopPaneController(desktopPane);   	
//    	toolBarController = new ToolBarController(toolBar, desktopPaneController.getFlowChartCanvas());
    	
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	
	@FXML
	public void save() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Resource file");
		fileChooser.setInitialDirectory(new File("."));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Flow Chart(*.fct)", "*.fct"));
		
		try {
			File selectedFile = fileChooser.showSaveDialog(null);
			String filePath = selectedFile.getAbsolutePath();
			
			if (filePath.endsWith(".fct")) {
				ObjectOutputStream outputStream = new ObjectOutputStream(
						new BufferedOutputStream(new FileOutputStream(selectedFile)));
				outputStream.writeObject(desktopPaneController.fcc.getRootNode().getNodes());
				outputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {}
	}
	
	@FXML
	public void open() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Resource file");
		fileChooser.setInitialDirectory(new File("."));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("C Sources(*.c)", "*.c"));
		try {
			File selectedFile = fileChooser.showOpenDialog(null);
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
				desktopPaneController.textArea.setText(code);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			
		} finally {
			doParse();
		}
		
	}
	public void doParse(){
		desktopPaneController.ctt.doParse(code);
	}
	@FXML
	public void exit() {
		System.exit(0);
	}
}
