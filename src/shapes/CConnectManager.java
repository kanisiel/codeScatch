package shapes;

import java.util.Vector;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class CConnectManager extends CShapeManager {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<Point2D> points;
	public Point2D upper, lower;
	private Path path;
	private final double vgap = 15.0;
	
	
	
	public CConnectManager() {
		super();
		// TODO Auto-generated constructor stub
		this.points = new Vector<>();
	}
	public void setVertex(Point2D s, Point2D e){
		points.clear();
		upper = s;
		points.add(s);
		lower = e;
		points.add(e);
	}
	public void setHVertex(Point2D s, Point2D e, double hgap){
		points.clear();
		points.add(s);
		Point2D vertex1 = new Point2D(s.getX()+hgap, s.getY());
		points.add(vertex1);
		Point2D vertex2 = new Point2D(vertex1.getX(), e.getY()-vgap);
		points.add(vertex2);
		Point2D vertex3 = new Point2D(e.getX(), e.getY()-vgap);
		upper = vertex3;
		points.add(vertex3);
		lower = e;
		points.add(e);
	}
	public void setVVertex(Point2D s, Point2D e, double hgap){
		points.clear();
		points.add(e);
		lower = e;
		Point2D vertex1 = new Point2D(e.getX()-hgap, e.getY());
		points.add(vertex1);
		upper = vertex1;
		Point2D vertex2 = new Point2D(vertex1.getX(), s.getY()+vgap);
		points.add(vertex2);
		Point2D vertex3 = new Point2D(s.getX(), s.getY()+vgap);
		points.add(vertex3);
		points.add(s);
//		points.add(s);
//		Point2D vertex1 = new Point2D(s.getX(), s.getY()+vgap);
//		points.add(vertex1);
//		Point2D vertex2 = new Point2D(vertex1.getX()-hgap, vertex1.getY());
//		points.add(vertex2);
//		Point2D vertex3 = new Point2D(e.getX()-hgap, e.getY());
//		upper = vertex3;
//		points.add(vertex3);
//		lower = e;
//		points.add(e);
	}
	public void setVVertexL(Point2D s, Point2D e, double hgap){
		points.add(s);
		Point2D vertex1 = new Point2D(s.getX(), s.getY()+vgap);
		points.add(vertex1);
		Point2D vertex2 = new Point2D(vertex1.getX()-hgap, vertex1.getY());
		points.add(vertex2);
		Point2D vertex3 = new Point2D(vertex2.getX(), e.getY()-(vgap*2));
		points.add(vertex3);
		Point2D vertex4 = new Point2D(e.getX(), e.getY()-(vgap*2));
		upper = vertex4;
		points.add(vertex4);
		lower = e;
		points.add(e);
	}
	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawText(GraphicsContext gc) {
		// TODO Auto-generated method stub

	}

	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub
		Vector<PathElement> e = new Vector<>();
		e.add(new MoveTo(points.firstElement().getX(), points.firstElement().getY()));
		for(Point2D p : points){
			e.add(new LineTo(p.getX(), p.getY()));
		}
		path = new Path(e);//(mtl, ltl);
		path.setStroke(Color.BLACK);
		path.setStrokeWidth(2);
		return path;
	}

	@Override
	public Text getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCoords(Point2D p, Dimension2D d, Dimension2D w) {
		// TODO Auto-generated method stub
		
	}
	public void addPoint(Point2D point){
		this.points.add(point);
	}
	public void setCoord(){
		this.upper = points.get(0);
		this.lower = points.lastElement();
		this.p = upper;
		this.d = new Dimension2D(lower.getX()-upper.getX(), lower.getY()-upper.getY());
		this.upperAnchor = upper;
		this.lowerAnchor = lower;
	}

	@Override
	public Shape Shape() {
		// TODO Auto-generated method stub
		return path;
	}

	public Vector<Point2D> getPoints() {
		return points;
	}

	public void setPoints(Vector<Point2D> points) {
		this.points = points;
	}

}
