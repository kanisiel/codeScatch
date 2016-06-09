package application;

import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;

public class ZoomPane extends BorderPane {
	private Button in, out;
	private TextField value;
	public double zoomValue = 100;
	private DoubleProperty amountDue;
	
	public ZoomPane(Scale scale, Pane pane) {
		super();
		amountDue = new SimpleDoubleProperty();
		Scale half = new Scale(0.5, 0.5);
		in = new Button("+");
		in.setMaxWidth(10);
		in.setMaxHeight(10);
		in.setPrefSize(10, 10);
		in.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		        if(event.isPrimaryButtonDown()){
		        	zoomValue+=5;
		        	setPercent(zoomValue);
		        }
		    }
		});
//		in.getTransforms().add(half);
		out = new Button("-");
		out.setMaxWidth(10);
		out.setMaxHeight(10);
		out.setPrefSize(10, 10);
		out.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		        if(event.isPrimaryButtonDown()){
		        	zoomValue-=5;
		        	setPercent(zoomValue);
		        }
		    }
		});
//		out.getTransforms().add(half);
		value = new TextField();
		setPercent(zoomValue);
		value.setMaxHeight(10);
		value.setMaxWidth(80);
		value.setPrefSize(30, 10);
		value.setFont(new Font("arial", 10));
		value.setAlignment(Pos.CENTER);
		value.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				setAmountDue((zoomValue/100));
//				System.out.println(scale.getX());
	        	scale.setX(getAmountDue());
	        	scale.setY(getAmountDue());
	        	@SuppressWarnings("unchecked")
				Map<String, Double> bounds = (Map<String, Double>) pane.getUserData();
	        	double width = bounds.get("width"), height = bounds.get("height");
	        	FlowChartCanvas fcc = (FlowChartCanvas) pane.getChildren().get(0);
	        	pane.setPrefWidth(width*getAmountDue());
	        	pane.setPrefHeight(height*getAmountDue());
			}
		});
		setLeft(out);
		setCenter(value);
		setRight(in);
	}

	public ZoomPane(Node center, Node top, Node right, Node bottom, Node left) {
		super(center, top, right, bottom, left);
		// TODO Auto-generated constructor stub
	}

	public ZoomPane(Node center) {
		super(center);
		// TODO Auto-generated constructor stub
	}
	
	private void setPercent(double percent){
		int intPercent = (int)percent;
		if(percent - intPercent==0){
			value.setText(intPercent+"%");
		}else {
			value.setText(percent+"%");
		}
		
	}
	
	// Define a getter for the property's value
    public final double getAmountDue(){return amountDue.get();}
 
    // Define a setter for the property's value
    public final void setAmountDue(double value){amountDue.set(value);}
 
     // Define a getter for the property itself
    public DoubleProperty amountDueProperty() {return amountDue;}
}
