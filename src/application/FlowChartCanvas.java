package application;

import Settings.Constants;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import shapes.CArrowHead;
import shapes.CConnectManager;
import shapes.CShapeManager;
import shapes.CStartEndManager;

public class FlowChartCanvas extends BorderPane {
	private Canvas canvas;
	private FlowChartManager manager;
	private Point2D endPoint;
	
	public FlowChartCanvas() {
		Image img = new Image(getClass().getResource("graph-paper2.jpg").toExternalForm());
    	BackgroundImage bi = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    	this.setBackground(new Background(bi));
		this.manager = new FlowChartManager();
		canvas = new Canvas();
		endPoint = new Point2D(0, 0);
//		canvas.setWidth(Constants.windowWidth-10);
//		canvas.widthProperty().bind(this.widthProperty());
//		canvas.heightProperty().bind(this.heightProperty());
		//this.setContent(canvas);
		this.setPrefSize(Constants.windowWidth, Constants.windowHeight-30);
		//Initialize canvas component;
		this.manager.initManager();
		drawAll();		
	}
	public void init(){
		this.manager.initManager();
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
		drawAll();
	}
	public void drawAll(){
		this.manager.getAllNodes().clear();
		for(CShapeManager s : this.manager.getNodes()){
			endPoint = s.getLowerAnchor();
			if(endPoint.getY() > (this.getPrefHeight())){
				//CStartEndManager end = (CStartEndManager)this.manager.getEndNode();
				Point2D p = new Point2D(endPoint.getX(), endPoint.getY()+55);
				CStartEndManager end = (CStartEndManager) this.manager.getNodes().get((this.manager.getNodes().size()-1));
				end.setP(p);
				end.setTp(new Point2D(p.getX()+(end.getD().getWidth()+5), (p.getY())+(5.0)));
				end.setUpperAnchor(new Point2D(p.getX(), p.getY()-15));
			}
			Shape shape = s.getShape();
			shape.setId(String.valueOf(s.getUpperAnchor().getY()));
			Text text = s.getText();
			Group g = new Group();
			g.getChildren().addAll(shape, text);
			this.manager.getAllNodes().add(g);
			this.getChildren().add(g);
			if(this.manager.findNode(s) != this.manager.getNodes().size()-1){
				Group arrow = new Group();
				CShapeManager next = this.manager.findNext(s);
				CConnectManager cm = new CConnectManager();
				cm.setCoord(s.getLowerAnchor(), next.getUpperAnchor());
				CArrowHead lh = new CArrowHead(Constants.SOUTH, next.getUpperAnchor(), s.getLowerAnchor());
				Shape line = cm.getShape();
				line.setId(String.valueOf(cm.getUpperAnchor().getY()));
				Shape head = lh.getShape();
				arrow.getChildren().addAll(line, head);
				this.manager.getAllNodes().add(arrow);
				this.getChildren().add(arrow);
			}
		}
	}
	public FlowChartManager getManager(){
		return manager;
	}
}

