package shapes;

import java.io.Serializable;

import Settings.Constants;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public abstract class CShapeManager extends Canvas implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String body;
	protected Point2D p;
	protected Dimension2D d;
	protected Point2D tp;
	protected Dimension2D td;
	protected Color fill;
	protected Color stroke;
	protected Text text;
	
	public CShapeManager(){
		this.prefWidth(Constants.windowWidth);
		this.prefHeight(Constants.windowHeight);
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
	//	protected EShapeType shapeType;
//	protected Point sw, ne;
//	
//	public CShapeManager(EShapeType shapeType) {
//		this.shapeType = shapeType;
//	}
//	
//	public void drawArrowHead(Graphics2D g, Point tip, Point tail, Color color) {
//		g.setPaint(color);
//        double dy = tip.y - tail.y;
//        double dx = tip.x - tail.x;
//        double theta = Math.atan2(dy, dx);
//        double x, y, rho = theta + Math.toRadians(30);
//        
//        for (int i = 0; i < 2; i++) {
//            x = tip.x - 10 * Math.cos(rho);
//            y = tip.y - 10 * Math.sin(rho);
//            g.draw(new Line2D.Double(tip.x, tip.y, x, y));
//            rho = theta - Math.toRadians(30);
//        }
//	}
//	
//	public EShapeType getShapeType() {	return shapeType;	}
//	
	
}
