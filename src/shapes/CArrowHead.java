package shapes;

import Settings.Constants;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class CArrowHead extends CShapeManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point2D lp, rp, cp, sp;
	
	public CArrowHead(String arrowTo, Point2D endP, Point2D staP, double scale) {
		super();
		double d = 10 * scale;
		cp = endP;
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
	public CArrowHead(String arrowTo, Point2D endP, Point2D staP) {
		super();
		cp = endP;
		sp = staP;
		if(arrowTo.equals(Constants.EAST)){
			lp = new Point2D(endP.getX()-10, endP.getY()-10);
			rp = new Point2D(endP.getX()-10, endP.getY()+10);
		}else if(arrowTo.equals(Constants.WEST)){
			lp = new Point2D(endP.getX()+10, endP.getY()+10);
			rp = new Point2D(endP.getX()+10, endP.getY()-10);
		}else if(arrowTo.equals(Constants.SOUTH)){
			lp = new Point2D(endP.getX()+10, endP.getY()-10);
			rp = new Point2D(endP.getX()-10, endP.getY()-10);
		}else if(arrowTo.equals(Constants.NORTH)){
			lp = new Point2D(endP.getX()-10, endP.getY()+10);
			rp = new Point2D(endP.getX()+10, endP.getY()+10);
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
		Polygon p = new Polygon(cp.getX(), cp.getY(), lp.getX(), lp.getY(), rp.getX(), rp.getY());
		p.setFill(Color.BLACK);
		p.setStrokeWidth(2);
		
		System.out.println(GetAngle(cp, sp));
		p.getTransforms().add(new Rotate(GetAngle(cp,sp),cp.getX(),cp.getY()));
		return p;
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
}
