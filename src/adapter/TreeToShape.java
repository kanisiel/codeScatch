package adapter;

import java.util.Vector;

import org.antlr.v4.runtime.tree.ParseTree;

import Settings.CConstants;
import Settings.Constants;
import application.FlowChartCanvas;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import shapes.CCodeManager;
import shapes.CIfManager;
import shapes.CIteratorManager;
import shapes.CRootManager;
import shapes.CShapeManager;
import shapes.CShapeNode;
import shapes.CStartEndNode;

public class TreeToShape {
	private ParseTree tree;
	private FlowChartCanvas canvas;
	private Vector<CShapeManager> list;
	private CRootManager rootNode;
	private CShapeNode rv;
	
	public TreeToShape(FlowChartCanvas canvas){
		this.canvas = canvas;
		this.list = new Vector<>();
		this.rootNode = new CRootManager();
		rootNode.addNode(0, new CStartEndNode(Constants.EShapeType.START.name(), rootNode));
		rootNode.addNode(1, new CStartEndNode(Constants.EShapeType.STOP.name(), rootNode));
		checkSid(rootNode);
		setStackPane(rootNode);
		canvas.setRootNode(rootNode);
		for(Node node : canvas.getChildren()){
			if(node.getClass().equals(Group.class)){
				node.setLayoutX(0);
			}
		}
	}
	
	public void prepareCanvas(){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				list.clear();
				canvas.init();
				canvas.clearCanvas();
				rootNode.clearNodes();
				rootNode.addNode(new CStartEndNode(Constants.EShapeType.START.name(), rootNode));
				rootNode.addNode(new CStartEndNode(Constants.EShapeType.STOP.name(), rootNode));
//				checkSid(rootNode);
//				setStackPane(rootNode);
			}
		});
	}
	public void drawToCanvas(){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				for(CShapeManager e : list){
					canvas.addShape(e);
				}
			}
		});
	}
	public void drawRoot(){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				checkSid(rootNode);
				setStackPane(rootNode);
				canvas.redraw(rootNode);
			}
		});
	}
	public CShapeNode declareToShape(String body, int type, CShapeNode parent, int[] lines){
		rv = null;
		
		if(type == Constants.CODE){
			rv = new CCodeManager(CConstants.BODY, body, parent);
			rv.setLines(lines);
		} else if(type == Constants.IF){
			rv = new CIfManager(CConstants.IF, body, parent);
			rv.setLines(lines);
		} else if(type == Constants.ELSE){
			rv = new CIfManager(CConstants.ELSE, "", parent);
			rv.setLines(lines);
		} else if(type == Constants.ELSEIF){
			rv = new CIfManager(CConstants.ELSEIF, body, parent);
			rv.setLines(lines);
		} else if(type == Constants.WHILE){
			rv = new CIteratorManager(CConstants.WHILE, body, parent);
			rv.setLines(lines);
//			
		} else if(type == Constants.DO){
			rv = new CIteratorManager(CConstants.DO, body, parent);
			rv.setLines(lines);
//			rv.addNode(new CCodeManager(body.get(0), rv));
//			
		} else if(type == Constants.FOR){
			String condition[] = body.split(";");
			rv = new CIteratorManager(CConstants.FOR, condition[0], condition[1], condition[2], parent);
			rv.setLines(lines);
		}
//		if(parent.getType().equals(CConstants.FOR)){
//			CShapeNode amount = parent.getNodes().get(parent.getchildNum()-1);
//			parent.remove(amount);
//			parent.addNode(rv);
//			parent.addNode(amount);
//		}
		if(parent.getType().equals(CConstants.DO)){
			CShapeNode condition = parent.getNodes().get(parent.getchildNum()-1);
			parent.remove(condition);
			parent.addNode(rv);
			parent.addNode(condition);
		}else {
			parent.addNode(rv);
		}
		return rv;
	}

	public CRootManager getRootNode() {
		return rootNode;
	}

	public void setRootNode(CRootManager rootNode) {
		this.rootNode = rootNode;
	}
	
	public void setStackPane(CShapeNode parent){
		if(parent.getchildNum()>0){
			for(CShapeNode node : parent.getNodes()){
				setStackPane(node);
			}
		} else {
			StackPane sp = create(parent);
			parent.setSp(sp);
		}
	}
	public StackPane create(CShapeNode node){
		StackPane sp = new StackPane();
		sp.setPrefSize(node.getShape().getBounds().getWidth(), node.getShape().getBounds().getHeight());
		Shape s = node.getShape().getShape();
//		s.setUserData(node.getShape().getUpperAnchor().getY());
		s.setId("");
		Text text = node.getShape().getText();
		sp.getChildren().addAll(s, text);
		sp.setUserData(node.getShape().sid);
		sp.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		        if(event.isPrimaryButtonDown()){
		        	System.out.println("sid: "+node.getShape().sid);
//		        	int[] lines = node.lines;
//		        	System.out.println(trimmed);
//		        	System.out.println(lines[0]+"~"+lines[1]);
//		        	System.out.println((lines[0]+trimmed)+"~"+(lines[1]+trimmed));
//		        	System.out.println();
//		        	System.out.println("Axis X : "+sp.getLayoutX());
//		        	System.out.println("Center Axis X : "+centerLineX);
//		        	System.out.println("Height : "+sp.getPrefHeight());
//		        	System.out.println("Sum : "+(sp.getLayoutY()+sp.getPrefHeight()));
//		        	Text t = (Text)sp.getChildren().get(1);
//		    		System.out.println(t.getWidth());
		        }
		    }
		});
		return sp;
	}
	public Boolean isRoot(CShapeNode node){
		if(node.getType().equals(CConstants.ROOT)){
			return true;
		} else return false;
	}
	
	public void checkSid(CShapeNode parent){
		Vector<Double> sidVector = new Vector<>();
		if(parent.getchildNum()>0){
			for(CShapeNode node : parent.getNodes()){
				check(node, sidVector);
				checkSid(node);
			}
		}
//		for(Double d : sidVector){
//			System.out.println(d);
//		}
	}
	private void check(CShapeNode node, Vector<Double> sidVector){
		if(node.getShape()==null){
		;
		} else {
			double sid = node.shape.checkSid(sidVector);
			sidVector.add(sid);
		}
	}
	
}
