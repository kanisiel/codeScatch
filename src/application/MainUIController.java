package application;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
/**
 *
 * @author newmacpro
 */
public class MainUIController implements Initializable {
    
	
//	@FXML 
	public DesktopPaneController desktopPaneController;
	public ToolBarController toolBarController;
	
    public MainUIController(){

    }
    public void init(VBox desktopPane, VBox toolBar){
    	desktopPaneController = new DesktopPaneController(desktopPane);   	
    	toolBarController = new ToolBarController(toolBar, desktopPaneController.getFlowChartCanvas());

    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
    
}
