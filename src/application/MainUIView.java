package application;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainUIView extends VBox{
	@FXML public VBox content;
	@FXML public VBox desktopPane; 
	public MainUIController controller;
		public MainUIView() throws IOException {

	    	 controller = new MainUIController();
	    	 URL url = MainUIView.class.getResource("MainUI.fxml");
	    	 System.out.println(url);
	    	 FXMLLoader loader = new FXMLLoader(url);
	    	 loader.setController(controller);
	    	 loader.load();
	    	 content = loader.getRoot();
	    	 BorderPane p = (BorderPane)content.getChildren().get(0);
	    	 desktopPane = (VBox)p.getChildren().get(1);
	    	 controller.init(desktopPane);
	     }
		
		public VBox getContent() {
	        return content;
	    }
}
