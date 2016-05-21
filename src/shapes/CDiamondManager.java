package shapes;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class CDiamondManager extends CShapeManager {
	private static final long serialVersionUID = 1L;
	private Point2D e, w, s, n;
	
	public CDiamondManager(String body){
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
		Polygon p = new Polygon();
		p.setFill(Color.TRANSPARENT);
		p.setStroke(stroke);
		p.setStrokeWidth(2);
		p.getPoints().addAll(e.getX(),e.getY(),n.getX(),n.getY(), w.getX(), w.getY(), s.getX(), s.getY());
		return p;
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
		this.setD(new Dimension2D((Math.floor(text.getWrappingWidth())*1.4)+40, td.getHeight()+40));
		this.setP(new Point2D((w.getWidth()/2)-(this.d.getWidth()/2), p.getY()+d.getHeight()+40));
		this.setTp(new Point2D(this.getP().getX()+(14*1.4), this.getP().getY()+(24*1.4)));
		
		this.e = new Point2D(this.getP().getX(),this.getP().getY()+(this.getD().getHeight()/2));
		this.w = new Point2D(this.getP().getX()+this.getD().getWidth(), this.getP().getY()+(this.getD().getHeight()/2));
		this.n = new Point2D(this.getP().getX()+(this.getD().getWidth()/2), this.getP().getY());
		this.s = new Point2D(this.getP().getX()+(this.getD().getWidth()/2), this.getP().getY()+this.getD().getHeight());

		upperAnchor = new Point2D(this.p.getX()+this.d.getWidth()/2, this.p.getY());
		lowerAnchor = new Point2D(this.p.getX()+this.d.getWidth()/2, this.p.getY()+this.d.getHeight());
	}
}
