package application;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import Settings.CConstants;
import Settings.Constants;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import shapes.CArrowHead;
import shapes.CConnectManager;
import shapes.CRootManager;
import shapes.CShapeManager;
import shapes.CShapeNode;
import shapes.CStartEndManager;

public class FlowChartCanvas extends BorderPane {
	private Canvas canvas;
	private FlowChartManager manager;
	private Point2D endPoint;
	private CRootManager root;
	private Map<String, Double> boundaryWidth;
	private double centerLineX = (Constants.windowWidth/2);
	
	public FlowChartCanvas() {
		Image img = new Image(getClass().getResource("graph-paper2.jpg").toExternalForm());
    	BackgroundImage bi = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    	this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));//new Background(bi));
		this.manager = new FlowChartManager();
		this.boundaryWidth = new LinkedHashMap<>();
		canvas = new Canvas();
		endPoint = new Point2D(0, 0);
		this.setPrefSize(Constants.windowWidth, Constants.windowHeight-30);
		
		//Initialize canvas component;
		this.manager.initManager();		
	}
	public void init(){
		this.manager.initManager();
	}
	public void setRootNode(CRootManager root){
		this.root = root;
		redraw(root);
	}
	public void clearCanvas(){
		this.getChildren().clear();
	}
	public void addShape(CShapeManager csm){
		this.manager.addNode(csm);
		setCoord(csm);
		reDraw();
		
	}
	public void setCoord(CShapeManager csm){
		Point2D p = this.manager.findPrev(csm).getP();
		Dimension2D d = this.manager.findPrev(csm).getD();
		Dimension2D w = new Dimension2D(this.getWidth(), this.getHeight());
		
		csm.setCoords(p, d, w);
	}
	public void reDraw(){
		clearCanvas();
	}
	public void redraw(CRootManager root){
		clearCanvas();
		this.manager.getNodes().clear();
		this.root = root;
		drawAllNodes(root);
	}
	public void setAllNodes(Vector<CShapeNode> root){
		
	}
	public void setNodes(Vector<CShapeNode> nodes){
		for(CShapeNode node : nodes){
			this.boundaryWidth.put(node.getType()+node.getDepth(), 0.0);
			if(node.getchildNum()>0){
				setNodes(node.getNodes());
			}else {
				draw(node);
			}
		}
	}
	public void set(CShapeNode node){
		CShapeManager shape = node.getShape();
		this.manager.addNode(shape);
		setCoord(shape);
	}
	public void drawAllNodes(CRootManager root){
//		this.root = root;
		drawNodes(root);
		drawConnects(root);
	}
	
	public void drawNodes(CShapeNode root){
		Vector<CShapeNode> nodes = root.getNodes();
		this.boundaryWidth.put(root.getType()+root.getDepth(), 0.0);
		for(CShapeNode node : nodes){
			if(node.getchildNum()>0){
				this.boundaryWidth.put(node.getType()+node.getDepth(), 0.0);
				drawNodes(node);
			}else {
				draw(node);
			}
		}
	}
	public void drawConnects(CShapeNode root){
		Vector<CShapeNode> nodes = root.getNodes();
		for(CShapeNode node : nodes){
			if(node.getchildNum()>0){
				drawConnects(node);
			}else {
				drawConnect(node);
			}
		}
	}
	
	public void draw(CShapeNode node){
		CShapeManager shape = node.getShape();
		this.manager.addNode(shape);
		setCoord(shape);
		double bw = this.boundaryWidth.get(node.getParent().getType()+node.getParent().getDepth());
		if(shape.getD().getWidth()/2 > bw){
			this.boundaryWidth.replace(node.getParent().getType()+node.getParent().getDepth(), shape.getD().getWidth()/2);
		}
		if(node.getType().equals(Constants.EShapeType.STOP.name())){
			if(node.getParent().getchildNum()>2){
				endPoint = this.manager.findPrev(node.shape).getLowerAnchor();
				Point2D p = new Point2D(endPoint.getX(), endPoint.getY()+55);
				CStartEndManager end = (CStartEndManager) this.manager.getNodes().lastElement();
				end.setP(p);
				end.setTp(new Point2D(p.getX()+(end.getD().getWidth()+5), (p.getY())+(5.0)));
				end.setUpperAnchor(new Point2D(p.getX(), p.getY()-15));
			}
			
		}
		if(bw<shape.getD().getWidth()){
			bw = shape.getD().getWidth();
		}
//		StackPane sp = new StackPane();
		Shape s = shape.getShape();
		s.setId(String.valueOf(shape.getUpperAnchor().getY()));
		Text text = shape.getText();
		Group g = new Group();
		g.getChildren().addAll(s, text);
//		sp.getChildren().addAll(s, text);
//		sp.setPrefSize(shape.getD().getWidth(), shape.getD().getWidth());
//		if(this.getChildren().size()<1){
//			sp.setLayoutY(30);
//			sp.setLayoutX(centerLineX-(sp.getPrefWidth()/2));
//		}else {
//			StackPane prev = (StackPane) this.getChildren().get(this.getChildren().size()-1);
//			if(node.getType().equals(Constants.EShapeType.STOP.name())){
//				if(prev.getLayoutY()<this.getPrefHeight()){
//					sp.setLayoutY(this.getPrefHeight());
//					sp.setLayoutX(centerLineX-(sp.getPrefWidth()/2));
//				}
//			} else {
//				sp.setLayoutX(centerLineX-(sp.getPrefWidth()/2));
//				sp.setLayoutY(prev.getLayoutY()+prev.getPrefHeight()+40);
//			}
//		}
//		sp.setUserData(sp.getLayoutY()+sp.getPrefHeight());
//		System.out.println(sp.getUserData());
		this.getChildren().add(g);
	}
	public void drawConnect(CShapeNode node){
		Group arrow = new Group();
		CConnectManager cm = new CConnectManager();
		CArrowHead lh = new CArrowHead();
		CShapeNode parent = node.getParent();
		if(node.getType().equals(CConstants.CONDITION)){
			Group arrowif = new Group();
			CConnectManager cmif = new CConnectManager();
			CArrowHead lhif = new CArrowHead();
			CShapeNode ancester = parent.getParent();
			CShapeNode nextBody;
			if(ancester.findNext(parent).getType().equals(CConstants.CODE)||ancester.findNext(parent).getType().equals(Constants.EShapeType.STOP.name())){
				nextBody = ancester.findNext(parent);
			}else {
				nextBody = ancester.findNext(parent).getFirstNode();
			}
			double gap = boundaryWidth.get(parent.getType()+parent.getDepth())/2;
			cmif.setHVertex(node.shape.getRightAnchor(), nextBody.shape.getUpperAnchor(), gap);
			lhif.setHead(Constants.SOUTH, cmif.lower, cmif.upper);
			Shape lineif = cmif.getShape();
			lineif.setId(String.valueOf(node.shape.getRightAnchor().getY()));
			Shape headif = lhif.getShape();
			Text tn = new Text("No");
			tn.setX(node.shape.getRightAnchor().getX()+10);
			tn.setY(node.shape.getRightAnchor().getY()-10);//tn.getLayoutBounds().getHeight());
			arrowif.getChildren().addAll(lineif, headif, tn);
			this.manager.getConnects().put(node.getType()+node.getDepth()+"N", arrowif);
			this.getChildren().add(arrowif);			
		}else if(node.getType().equals(CConstants.CODE)){
			CShapeNode prev = parent.findCondition();
			if(parent.findNode(node) == (parent.getNodes().size()-1)&&!(parent.getType().equals(CConstants.IF))){
				Group arrowW = new Group();
				CConnectManager cmW = new CConnectManager();
				CArrowHead lhW = new CArrowHead();
				CShapeNode endBody = parent.getNodes().lastElement();//node.getParent().findNode(node)+1).getNodes().get(0);
				double gap = boundaryWidth.get(parent.getType()+parent.getDepth())/2;
				cmW.setVVertex(node.shape.getLowerAnchor(), prev.shape.getLeftAnchor(), gap);
				lhW.setHead(Constants.EAST, cmW.lower, cmW.upper);
				Shape lineW = cmW.getShape();
//				System.out.println(lineW);
				lineW.setId(String.valueOf(prev.shape.getLeftAnchor().getY()));
				Shape headW = lhW.getShape();
				arrowW.getChildren().addAll(lineW, headW);
				this.manager.getConnects().put(node.getType()+node.getDepth()+"While", arrowW);
				this.getChildren().add(arrowW);			
			}
		}
		//node.getType().equals(CConstants.CODE)&&
//		System.out.println(parent.findNode(node) +" : "+ (parent.getNodes().size()-1) );//&&parent.getParent().findNext(parent) == parent.getParent().getNodes().get(parent.getParent().getNodes().size()-1) ));
		if(!(node.getType().equals(CConstants.CODE) && parent.getType().equals(CConstants.WHILE))&&!(parent.findNext(node).equals(this.manager.getNodes().lastElement()))){//&&parent.findNode(node)==parent.getNodes().size()-1)){
			CShapeManager next = this.manager.findNext(node.shape);
			cm.setVertex(node.shape.getLowerAnchor(), next.getUpperAnchor());
			lh.setHead(Constants.SOUTH, cm.lower, cm.upper);		
			Shape line = cm.getShape();
			line.setId(String.valueOf(node.shape.getLowerAnchor().getY()));
			Shape head = lh.getShape();
			if(node.getType().equals(CConstants.CONDITION)){
				Text ty = new Text("Yes");
				ty.setX(node.shape.getLowerAnchor().getX()+10);
				ty.setY(node.shape.getLowerAnchor().getY()+20);
				arrow.getChildren().addAll(line, head, ty);
			}else {
				arrow.getChildren().addAll(line, head);	
			}
			this.manager.getConnects().put(node.getType()+node.getDepth(), arrow);
			this.getChildren().add(arrow);
		}
		Group g = this.manager.getConnects().get(Constants.EShapeType.STOP.name()+"1");
		if(g!=null){
//			System.out.println(g.getChildren().get(0));
			Path p = (Path) g.getChildren().get(0);
			this.getChildren().remove(g);	
		}
	}
	public FlowChartManager getManager(){
		return manager;
	}
}

