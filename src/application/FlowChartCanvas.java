package application;

import java.awt.Graphics;
import java.awt.Image;

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
import shapes.CShapeNode;
import shapes.CStartEndManager;

public class FlowChartCanvas extends BorderPane {
	private static final long serialVersionUID = 1L;
	private CShapeNode root;
	private CShapeManager shapeManager;
	private Image img;
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
		//init();
		
		this.manager.addNode(csm);
		//Line l = new Line(this.getWidth()/2, startY, endX, endY) 
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
			//int index = this.manager.findNode(s);
//			Canvas c = new Canvas(Constants.windowWidth, Constants.windowHeight);
//			this.getChildren().add(c);
//			GraphicsContext gc = c.getGraphicsContext2D();
//			if(!s.getClass().equals(CStartEndManager.class)){
//				setCoord(s);
//			}
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
				CArrowHead uh = new CArrowHead(Constants.SOUTH, s.getUpperAnchor());
				Shape upperHead = uh.getShape();
				MoveTo mtl = new MoveTo(s.getLowerAnchor().getX(), s.getLowerAnchor().getY());
				LineTo ltl = new LineTo(next.getUpperAnchor().getX(), next.getUpperAnchor().getY());
				Path lower = new Path(mtl, ltl);
				lower.setStroke(Color.BLACK);
				lower.setStrokeWidth(2);
				CArrowHead lh = new CArrowHead(Constants.SOUTH, next.getUpperAnchor());
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
//	public void draw(GraphicsContext gc, CShapeManager csm){
//		int index = this.getChildren().indexOf(gc.getCanvas());
//		gc.setStroke(Color.BLACK);
//		gc.strokeRect((this.getWidth()/2)-50, 80*(index+1), 100, 60);
//		if(csm.getBody()!=null){
//			Text text = new Text(csm.getBody());
//			Bounds b = text.getLayoutBounds();
//			double tw = b.getWidth();
//			double th = b.getHeight();
//			gc.strokeText(csm.getBody(), (this.getWidth()/2)-40, 100*(index+1));
//		}
//	}
	public void setShapeManager(CShapeManager shapeManager) {	
		this.shapeManager = shapeManager;
	}

	public void setCoords(CShapeNode node, int x, int y){
        if (node != null){
            node.setX(x);
            node.setY(y);
               
	        if (!node.getIsDiamond())
	        	this.setCoords(node.getRight(), x, y + 80);
	            
	        else {
	        	this.setCoords(node.getLeft(), x , y + 110);
	        	this.setCoords(node.getRight(), x + 200 , y + 110);
	        }
        }
    }
	
	public CShapeNode getRootNode() {	return root;	}
	
//	public void paint(Graphics g) {
//		
//		//super.paint(g);
//		this.drawNode(g, root);
//	}
	
//	public void drawNode(Graphics g, CShapeNode node) {
//		Graphics2D g2d = (Graphics2D) g;
//		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		
//		if (node != null) {
//			if (node.getShapeType() == EShapeType.START)
//				shapeManager = new COvalManager(node.getShapeType());
//			/*
//			else 
//				shapeManager = EToolBarButton.valueOf(node.getShapeType().name()).getShape();
//			*/
//			else if (node.getShapeType() == EShapeType.PROCESS)
//				// draw rectangle
//				shapeManager = new CRectangleManager(node.getShapeType());
//			
//			else if (node.getShapeType() == EShapeType.IO)
//				// draw parallogram
//				shapeManager = new CParallelogramManager(node.getShapeType());
//			
//			else if (node.getShapeType() == EShapeType.CONDITION)
//				shapeManager = new CDiamondManager(node.getShapeType());
//			
//			else if (node.getShapeType() == EShapeType.LOOP)
//				// draw as CONDITION but one of the arrows is toward upward
//				shapeManager = new CDiamondManager(node.getShapeType());
//			
//			else if (node.getShapeType() == EShapeType.FUNCTION) {
//				// ... complex ... it may be an add on button...
//			}
//			
//			else if (node.getShapeType() == EShapeType.STOP)
//				shapeManager = new COvalManager(node.getShapeType());
//			
//			shapeManager.draw(g2d, node);
//								
//			if (!node.getIsDiamond())
//				this.drawNode(g, node.getLeft());
//			
//			else {
//				this.drawNode(g, node.getLeft());
//				this.drawNode(g, node.getRight());
//			}
//		}
//	}
	

	  public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	  }
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
}

