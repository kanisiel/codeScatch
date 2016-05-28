package application;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import Settings.CConstants;
import Settings.Constants;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
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
	private Point2D startPoint;
	private CRootManager root;
	private Map<String, Double> boundaryWidth;
	private Map<String, Double> layoutY;
	private Map<String, Double> lastLowerAnchor;
	private double centerLineX = (Constants.windowWidth/2);
	public double height = Constants.windowHeight-30;
	
	public FlowChartCanvas() {
		Image img = new Image(getClass().getResource("graph-paper2.jpg").toExternalForm());
    	BackgroundImage bi = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    	this.setBackground(new Background(bi));
//    	this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		this.manager = new FlowChartManager();
		this.boundaryWidth = new LinkedHashMap<>();
		this.layoutY = new LinkedHashMap<>();
		this.lastLowerAnchor = new LinkedHashMap<>();
		canvas = new Canvas();
		startPoint = new Point2D(0, 0);
		this.setPrefSize(Constants.windowWidth-30, Constants.windowHeight-30);
		
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
//		drawBounds(root);
		drawConnects(root);
	}
	public void drawBounds(CShapeNode root){
		Vector<CShapeNode> nodes = root.getNodes();
		for(CShapeNode node : nodes){
			if(node.getchildNum()>0){
				drawBound(node);
			}
		}
	}
	public void drawNodes(CShapeNode root){
		Vector<CShapeNode> nodes = root.getNodes();
		this.boundaryWidth.put(root.getType()+root.getDepth(), 0.0);
		for(CShapeNode node : nodes){
			if(node.getchildNum()>0){
				this.boundaryWidth.put(node.getType()+node.getDepth(), 0.0);
				this.layoutY.put(node.getType()+node.getDepth(), 0.0);
				this.lastLowerAnchor.put(node.getType()+node.getDepth(), 0.0);
				drawNodes(node);
			}else {
				draw(node);
			}
		}
	}
	public void drawBound(CShapeNode node){
		VBox box = new VBox();
		Text t = new Text(node.getType());
		t.setStroke(Color.PURPLE);
		t.setStrokeWidth(2);
		System.out.println(node.getType()+node.getDepth());
		double w = this.boundaryWidth.get(node.getType()+node.getDepth());
		double y = this.layoutY.get(node.getType()+node.getDepth());
		double h = this.lastLowerAnchor.get(node.getType()+node.getDepth())-y;
		System.out.println(w+","+h+","+y);
		Rectangle rect = new Rectangle(w+30, h-y);
		rect.setFill(Color.TRANSPARENT);
		rect.setStroke(Color.PURPLE);
		rect.setStrokeWidth(2);
		box.getChildren().addAll(t, rect);
		box.setLayoutX(Constants.centerAxisX-(w/2));
		box.setLayoutY(y);
		box.setPrefHeight(h);
		box.setMinSize(w, h);
		box.setPrefHeight(w);
		this.getChildren().add(box);
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
				startPoint = this.manager.findPrev(node.shape).getLowerAnchor();
				Point2D p = new Point2D(startPoint.getX(), startPoint.getY()+55);
				CStartEndManager end = (CStartEndManager) this.manager.getNodes().lastElement();
				end.setP(p);
				end.setTp(new Point2D(p.getX()+(end.getD().getWidth()+5), (p.getY())+(5.0)));
				end.setUpperAnchor(new Point2D(p.getX(), p.getY()-15));
				this.height = p.getY()+40;
				CStartEndManager start = (CStartEndManager) this.manager.getNodes().firstElement();
				startPoint = node.shape.getUpperAnchor();
				Point2D p2 = new Point2D(startPoint.getX(), 30);
				start.setP(p2);
				start.setTp(new Point2D(p2.getX()+(start.getD().getWidth()+5), (p2.getY())+(5.0)));
				start.setLowerAnchor(new Point2D(p2.getX(), p2.getY()+15));
				Group g = (Group)this.getChildren().get(0);
				g.getChildren().set(0,start.getShape()); 
			}
			
		}
		if(bw<shape.getD().getWidth()){
			bw = shape.getD().getWidth();
		}
		StackPane sp = new StackPane();
		Shape s = shape.getShape();
		s.setUserData(shape.getUpperAnchor().getY());
		s.setId("");
		Text text = shape.getText();
		Group g = new Group();
//		g.setOnMouseEntered(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {}
//		});
		if(!node.getType().equals(Constants.EShapeType.START.name())&&!node.getType().equals(Constants.EShapeType.STOP.name())){
			g.addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent event) {
	//		        if(event.isPrimaryButtonDown()){
			        	Shape s = (Shape) g.getChildren().get(0);
			        	s.setStroke(Color.CRIMSON);
	//		        }
			    }
			});
			g.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent event) {
	//		        if(event.isPrimaryButtonDown()){
			        	Shape s = (Shape) g.getChildren().get(0);
			        	s.setStroke(Color.BLACK);
	//		        }
			    }
			});
		}
		g.getChildren().addAll(s, text);
//		sp.getChildren().addAll(s, text);
//		sp.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//		    @Override
//		    public void handle(MouseEvent event) {
//		        if(event.isPrimaryButtonDown()){
//		        	System.out.println(sp.getLayoutX());
//		        }
//		    }
//		});
//		sp.setPrefSize(shape.getD().getWidth(), shape.getD().getHeight());
//		sp.setLayoutX(centerLineX);
//		if(this.getChildren().size()<1){
//			sp.setLayoutY(30);
//		}else {
//			StackPane prev = (StackPane) this.getChildren().get(this.getChildren().size()-1);
//			if(node.getType().equals(Constants.EShapeType.STOP.name())){
//				if(prev.getLayoutY()<this.getPrefHeight()){
//					sp.setLayoutY(this.getPrefHeight());					
//				} else {
//					sp.setLayoutY(prev.getLayoutY()+prev.getPrefHeight()+40);
//				}
//			} else {
//				sp.setLayoutY(prev.getLayoutY()+prev.getPrefHeight()+40);
//			}
//		}
//		sp.setUserData(sp.getLayoutY()+sp.getPrefHeight());
////		System.out.println(sp.getUserData());
//		if(node.getParent().findNode(node)==0){
//			this.layoutY.replace(node.getParent().getType()+node.getParent().getDepth(), sp.getLayoutY()-15);
//		}
//		if(node.getParent().findNode(node) == (node.getParent().getNodes().size()-1)){
//			this.lastLowerAnchor.replace(node.getParent().getType()+node.getParent().getDepth(), sp.getLayoutY()+sp.getPrefHeight()+15);
//		}
		this.getChildren().add(g);
	}
	public void drawOrthogoral(CShapeNode node){
		CShapeNode parent = node.getParent();
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
		lineif.setUserData(node.shape.getRightAnchor().getY());
		lineif.setId(String.valueOf(node.shape.getRightAnchor().getY()));
		Shape headif = lhif.getShape();
		Text tn = new Text("No");
		tn.setX(node.shape.getRightAnchor().getX()+10);
		tn.setY(node.shape.getRightAnchor().getY()-10);//tn.getLayoutBounds().getHeight());
		arrowif.getChildren().addAll(lineif, headif, tn);
		this.manager.getConnects().put(node.getType()+node.getDepth()+"N", arrowif);
		this.getChildren().add(arrowif);
	}
	public void drawConnect(CShapeNode node){
		//StackPane spConnect = new StackPane();
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
			lineif.setUserData(node.shape.getRightAnchor().getY());
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
				lineW.setUserData(prev.shape.getLeftAnchor().getY());
				lineW.setId(String.valueOf(prev.shape.getLeftAnchor().getY()));
				Shape headW = lhW.getShape();
				arrowW.getChildren().addAll(lineW, headW);
				this.manager.getConnects().put(node.getType()+node.getDepth()+"While", arrowW);
				this.getChildren().add(arrowW);			
			}
		}
		//node.getType().equals(CConstants.CODE)&&
//		System.out.println(parent.findNode(node) +" : "+ (parent.getNodes().size()-1) );//&&parent.getParent().findNext(parent) == parent.getParent().getNodes().get(parent.getParent().getNodes().size()-1) ));
		if(!(node.getType().equals(CConstants.CODE) && (parent.getType().equals(CConstants.WHILE)))&&!(parent.findNext(node).equals(this.manager.getNodes().lastElement())) && !(parent.getType().equals(CConstants.FOR) && parent.getNodes().lastElement().equals(node)) ){
			CShapeManager next = this.manager.findNext(node.shape);
			cm.setVertex(node.shape.getLowerAnchor(), next.getUpperAnchor());
			lh.setHead(Constants.SOUTH, cm.lower, cm.upper);		
			Shape line = cm.getShape();
			line.setUserData(node.shape.getLowerAnchor().getY());
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

