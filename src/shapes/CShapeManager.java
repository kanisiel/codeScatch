package shapes;

import java.io.Serializable;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public abstract class CShapeManager implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String body;
	protected Point2D p, tp, upperAnchor, lowerAnchor;
	protected Dimension2D d, td;
	protected Color fill;
	protected Color stroke;
	protected Text text;
	
	public CShapeManager(){
		this.stroke = Color.BLACK;
		this.fill = Color.BLACK;
	}
	public String getBody(){return body;}
	public void setBody(String body){ this.body = body;}
	public abstract void draw(GraphicsContext gc);
	public abstract void drawText(GraphicsContext gc);
	public abstract Shape getShape();
	public abstract Text getText();
	public abstract void setCoords(Point2D p, Dimension2D d, Dimension2D w);
	public Point2D getP() {
		return p;
	}
	public Dimension2D getD() {
		return d;
	}
	public Point2D getTp() {
		return tp;
	}
	public Dimension2D getTd() {
		return td;
	}
	public void setP(Point2D p) {
		this.p = p;
	}
	public void setD(Dimension2D d) {
		this.d = d;
	}
	public void setTp(Point2D tp) {
		this.tp = tp;
	}
	public void setTd(Dimension2D td) {
		this.td = td;
	}
	public Color getFill() {
		return fill;
	}
	public Color getStroke() {
		return stroke;
	}
	public void setFill(Color fill) {
		this.fill = fill;
	}
	public void setStroke(Color stroke) {
		this.stroke = stroke;
	}
	
	public Point2D getUpperAnchor() {
		return upperAnchor;
	}
	public Point2D getLowerAnchor() {
		return lowerAnchor;
	}
	public void setUpperAnchor(Point2D upperAnchor) {
		this.upperAnchor = upperAnchor;
	}
	public void setLowerAnchor(Point2D lowerAnchor) {
		this.lowerAnchor = lowerAnchor;
	}
	
}
