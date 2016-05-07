package application;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

/**
 *
 * @author newmacpro
 */
public class MainUIController extends VBox {
    
	@FXML private DesktopPaneController desktopPane;
	//@FXML private DesktopPane desktopPane;
    
    public MainUIController(){
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainUI.fxml"));
    	System.out.println(getClass().getResource("MainUI.fxml"));
    	fxmlLoader.setRoot(this);
    	fxmlLoader.setController(this);
    	try{
    		fxmlLoader.load();
    	} catch(IOException e){
    		e.getStackTrace();
    		throw new RuntimeException(e);
    	}
    }
    
}
