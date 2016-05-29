package shapes;

import Settings.Constants;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class CArrowHead extends CShapeManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point2D lp, rp, cp, sp;
	private Polygon poly;
	
	public CArrowHead() {
		super();
		
	}
	public void setHead(String arrowTo, Point2D endP, Point2D staP, double scale) {
		double d = 10 * scale;
		cp = endP;
		sp = staP;
		if(arrowTo.equals(Constants.EAST)){
			lp = new Point2D(endP.getX()-d, endP.getY()-d);
			rp = new Point2D(endP.getX()-d, endP.getY()+d);
		}else if(arrowTo.equals(Constants.WEST)){
			lp = new Point2D(endP.getX()+d, endP.getY()+d);
			rp = new Point2D(endP.getX()+d, endP.getY()-d);
		}else if(arrowTo.equals(Constants.SOUTH)){
			lp = new Point2D(endP.getX()+d, endP.getY()-d);
			rp = new Point2D(endP.getX()-d, endP.getY()-d);
		}else if(arrowTo.equals(Constants.NORTH)){
			lp = new Point2D(endP.getX()-d, endP.getY()+d);
			rp = new Point2D(endP.getX()+d, endP.getY()+d);
		}
		
	}
	public void setHead(String arrowTo, Point2D endP, Point2D staP){
		cp = endP;
		sp = staP;
		if(arrowTo.equals(Constants.EAST)){
			lp = new Point2D(endP.getX()-10, endP.getY()-10);
			rp = new Point2D(endP.getX()-10, endP.getY()+10);
			p = new Point2D(cp.getX()-10, cp.getY());
		}else if(arrowTo.equals(Constants.WEST)){
			lp = new Point2D(endP.getX()+10, endP.getY()+10);
			rp = new Point2D(endP.getX()+10, endP.getY()-10);
			p = new Point2D(cp.getX()+10, cp.getY());
		}else if(arrowTo.equals(Constants.SOUTH)){
			lp = new Point2D(endP.getX()+10, endP.getY()-10);
			rp = new Point2D(endP.getX()-10, endP.getY()-10);
			p = new Point2D(cp.getX(), cp.getY()-10);
		}else if(arrowTo.equals(Constants.NORTH)){
			lp = new Point2D(endP.getX()-10, endP.getY()+10);
			rp = new Point2D(endP.getX()+10, endP.getY()+10);
			p = new Point2D(cp.getX(), cp.getY()+10);
		}
		
	}
	public void setHead(String arrowTo, Point2D endP){
		cp = endP;
		if(arrowTo.equals(Constants.EAST)){
			lp = new Point2D(endP.getX()-10, endP.getY()-10);
			rp = new Point2D(endP.getX()-10, endP.getY()+10);
			p = new Point2D(cp.getX()-10, cp.getY());
		}else if(arrowTo.equals(Constants.WEST)){
			lp = new Point2D(endP.getX()+10, endP.getY()+10);
			rp = new Point2D(endP.getX()+10, endP.getY()-10);
			p = new Point2D(cp.getX()+10, cp.getY());
		}else if(arrowTo.equals(Constants.SOUTH)){
			lp = new Point2D(endP.getX()+10, endP.getY()-10);
			rp = new Point2D(endP.getX()-10, endP.getY()-10);
			p = new Point2D(cp.getX(), cp.getY()-10);
		}else if(arrowTo.equals(Constants.NORTH)){
			lp = new Point2D(endP.getX()-10, endP.getY()+10);
			rp = new Point2D(endP.getX()+10, endP.getY()+10);
			p = new Point2D(cp.getX(), cp.getY()+10);
		}
		
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
		poly = new Polygon(cp.getX(), cp.getY(), lp.getX(), lp.getY(), rp.getX(), rp.getY());
		poly.setFill(Color.BLACK);
		poly.setStrokeWidth(2);
//		poly.getTransforms().add(new Rotate(GetAngle(cp,sp),cp.getX(),cp.getY()));
		return poly;
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
	

	public double GetAngle(Point2D p1, Point2D p2) {
	    double xdf = p2.getX() - p1.getX();
	    double ydf = p2.getY() - p1.getY();
	
	    double ang = RadianToDegree(Math.atan2(ydf, xdf));
	    return ang + 90;
	}
	public double RadianToDegree(double rad) {
	    return rad * (180 / Math.PI);
	}
	@Override
	public Shape Shape() {
		// TODO Auto-generated method stub
		return poly;
	}
}
