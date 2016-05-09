package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class MainUIView{
	@FXML public VBox content;
	 
	 
	     public MainUIView() {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainUI.fxml"));
	        loader.setRoot(content);
	        // loader.setController(controller);
	        loader.setControllerFactory(new Callback<Class<?>, Object>() {
	          public Object call(Class<?> clazz) {
	             if (clazz.equals(MainUIController.class)) {
	                return new MainUIController();
	             } else if (clazz.equals(DesktopPaneController.class)) {
	               return new DesktopPaneController();
	             } else return null ;
	          }
	        });
	        try {
	        	
	            loader.load();
	            
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	 
	        //template = loader.getRoot();
	    }
	 
	    public VBox getContent() {
	        return content;
	    }
}
