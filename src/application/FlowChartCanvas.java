package application;

import Settings.Constants;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import shapes.CArrowHead;
import shapes.CShapeManager;
import shapes.CStartEndManager;

public class FlowChartCanvas extends BorderPane {
	private Canvas canvas;
	private FlowChartManager manager;
	
	public FlowChartCanvas() {
		this.manager = new FlowChartManager();
		canvas = new Canvas();
		canvas.widthProperty().bind(this.widthProperty());
		canvas.heightProperty().bind(this.heightProperty());
		this.setCenter(canvas);
		this.setPrefSize(Constants.windowWidth, Constants.windowHeight);
		
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
		for(CShapeManager s : this.manager.getNodes()){
			Shape shape = s.getShape();
			Text text = s.getText();
			Group g = new Group();
			if(!s.getClass().equals(CStartEndManager.class)){
				CShapeManager prev = this.manager.findPrev(s);
				CShapeManager next = this.manager.findNext(s);
				MoveTo mtu = new MoveTo(prev.getLowerAnchor().getX(), prev.getLowerAnchor().getY());
				LineTo ltu = new LineTo(s.getUpperAnchor().getX(), s.getUpperAnchor().getY());
				Path upper = new Path(mtu, ltu);
				upper.setStroke(Color.BLACK);
				upper.setStrokeWidth(2);
				CArrowHead uh = new CArrowHead(Constants.SOUTH, s.getUpperAnchor(), prev.getLowerAnchor());
				Shape upperHead = uh.getShape();
				MoveTo mtl = new MoveTo(s.getLowerAnchor().getX(), s.getLowerAnchor().getY());
				LineTo ltl = new LineTo(next.getUpperAnchor().getX(), next.getUpperAnchor().getY());
				Path lower = new Path(mtl, ltl);
				lower.setStroke(Color.BLACK);
				lower.setStrokeWidth(2);
				CArrowHead lh = new CArrowHead(Constants.SOUTH, next.getUpperAnchor(), s.getLowerAnchor());
				Shape lowerHead = lh.getShape();
				g.getChildren().addAll(upper, upperHead, shape, text, lower, lowerHead);
			}else {
				g.getChildren().addAll(shape, text);
			}
			g.setId(String.valueOf(this.manager.findNode(s)));
			this.getChildren().add(g);
			//c.getChildren().add();
			//s.draw(gc);
		}
	}

}

