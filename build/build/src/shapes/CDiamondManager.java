package shapes;

import javafx.geometry.BoundingBox;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class CDiamondManager extends CShapeManager {
	private static final long serialVersionUID = 1L;
	private Point2D e, w, s, n;
	private Polygon poly;
	public CDiamondManager(String body){
		super();
		this.body = body;
		label = new Label(body);
		text = new Text(body);
	}



	@Override
	public void drawText(GraphicsContext gc) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void draw(GraphicsContext gc){
		gc.setFill(fill);
		gc.setStroke(stroke);
		gc.fillOval(p.getX(), p.getY(), d.getWidth(), d.getHeight());
		gc.strokeText(body, tp.getX(), tp.getY());
	}


	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub
		this.w = new Point2D(0, (getBounds().getHeight()/2));
		this.e = new Point2D(getBounds().getWidth(), (getBounds().getHeight()/2));
		this.n = new Point2D((getBounds().getWidth()/2), 0);
		this.s = new Point2D((getBounds().getWidth()/2), getBounds().getHeight());
		
		poly = new Polygon();
		poly.setFill(fill);
		poly.setStroke(stroke);
		poly.setStrokeWidth(2);
		poly.getPoints().addAll(w.getX(),w.getY(),n.getX(),n.getY(), e.getX(), e.getY(), s.getX(), s.getY());
		return poly;
	}
	@Override
	public Text getText() {
		
//		text.setX(tp.getX());
//		text.setY(tp.getY());
		return text;
	}


	@Override
	public void setCoords(Point2D p, Dimension2D d, Dimension2D w) {
		// TODO Auto-generated method stub
		text = new Text(body);
		text.setWrappingWidth(Math.ceil(text.getLayoutBounds().getWidth()));
		this.setTd(new Dimension2D(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight()));
		this.setD(new Dimension2D((Math.floor(text.getWrappingWidth())*1.4)+40, td.getHeight()+40));
		this.setP(new Point2D((w.getWidth()/2)-(this.d.getWidth()/2), p.getY()+d.getHeight()+40));
		this.setTp(new Point2D(this.getP().getX()+(14*1.4), this.getP().getY()+(24*1.4)));
		
		this.w = new Point2D(this.getP().getX(),this.getP().getY()+(this.getD().getHeight()/2));
		this.e = new Point2D(this.getP().getX()+this.getD().getWidth(), this.getP().getY()+(this.getD().getHeight()/2));
		this.n = new Point2D(this.getP().getX()+(this.getD().getWidth()/2), this.getP().getY());
		this.s = new Point2D(this.getP().getX()+(this.getD().getWidth()/2), this.getP().getY()+this.getD().getHeight());

		upperAnchor = this.n;
		lowerAnchor = this.s;
		leftAnchor = this.e;
		rightAnchor = this.w;
	}

	@Override
	public BoundingBox getBounds(){
		Canvas buffer = new Canvas();
		GraphicsContext gc = buffer.getGraphicsContext2D();
 		@SuppressWarnings("restriction")
		float fontHeight = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().getFontMetrics(gc.getFont()).getLineHeight();
 		@SuppressWarnings("restriction")
		float fontWidth = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().computeStringWidth(body, gc.getFont());
		String[] lines = body.split(System.getProperty("line.separator"));
		double width = (fontWidth)*2+20;
		double height = (fontHeight*lines.length)+40;
//		System.out.println(text+" : "+fontWidth);
		return new BoundingBox(0, 0, width, height); 
	}

	@Override
	public Shape Shape() {
		// TODO Auto-generated method stub
		return poly;
	}
}
