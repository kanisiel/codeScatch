package shapes;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class CParallelogramManager extends CShapeManager {
	private static final long serialVersionUID = 1L;
	private Point2D ul, ur, dl, dr;
	private Polygon poly;
	public CParallelogramManager(String body){
		super();
		this.body = body;
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
		poly = new Polygon();
		poly.setFill(Color.TRANSPARENT);
		poly.setStroke(stroke);
		poly.setStrokeWidth(2);
		poly.getPoints().addAll(ul.getX(),ul.getY(),ur.getX(),ur.getY(), dr.getX(), dr.getY(), dl.getX(), dl.getY());
		return poly;
	}
	@Override
	public Text getText() {
		text = new Text(body);
		text.setX(tp.getX());
		text.setY(tp.getY());
		return text;
	}

	@Override
	public void setCoords(Point2D p, Dimension2D d, Dimension2D w) {
		// TODO Auto-generated method stub
		text = new Text(body);
		text.setWrappingWidth(Math.ceil(text.getLayoutBounds().getWidth()));
		this.setTd(new Dimension2D(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight()));
		this.setD(new Dimension2D((Math.floor(text.getWrappingWidth())*1.4)+60, td.getHeight()+30));
		this.setP(new Point2D((w.getWidth()/2)-(this.d.getWidth()/2), p.getY()+d.getHeight()+40));
		this.setTp(new Point2D(this.getP().getX()+30, this.getP().getY()+27));
		
		this.ul = new Point2D(this.getP().getX()+30,this.getP().getY());
		this.ur = new Point2D(this.getP().getX()+this.getD().getWidth(), this.getP().getY());
		this.dl = new Point2D(this.getP().getX(), this.getP().getY()+this.getD().getHeight());
		this.dr = new Point2D(this.getP().getX()+(this.getD().getWidth()-30), this.getP().getY()+this.getD().getHeight());

		upperAnchor = new Point2D(this.p.getX()+this.d.getWidth()/2, this.p.getY());
		lowerAnchor = new Point2D(this.p.getX()+this.d.getWidth()/2, this.p.getY()+this.d.getHeight());
	}

	@Override
	public Shape Shape() {
		// TODO Auto-generated method stub
		return poly;
	}
	
//	public CParallelogramManager(EShapeType shapeType) {
//		super(shapeType);
//	}
//	
//	@Override
//	public void draw(Graphics2D g, CShapeNode node) {
//		
//	}
}
