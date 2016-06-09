package application;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Settings.Constants;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Lee Junsoo
 */
public class CodeScatchFX extends Application {
	private MainUIView uiView;
	private void init(Stage primaryStage) throws Exception {
		uiView = new MainUIView();
	    primaryStage.setTitle("codeScatch v0.3");
	    primaryStage.setResizable(true);
	    primaryStage.setScene(new Scene(uiView.getContent()));
	    primaryStage.setWidth(Constants.FRAME_W);
	    primaryStage.setHeight(Constants.FRAME_H);
	    
	    
	}
	
	public double getSampleWidth() {return 600;}
	public double getSampleHeight() {return 500;}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	    init(primaryStage);
	    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				// TODO Auto-generated method stub
				Platform.exit();
				System.exit(0);
			}
		});
	    primaryStage.show();
	    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});
	}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
}
