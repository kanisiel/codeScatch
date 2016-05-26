package shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.io.Serializable;

import Settings.Constants.EArrowHeadDirection;
import Settings.Constants.EControlStatementMode;
import Settings.Constants.EShapeType;

public abstract class CShapeManager implements Serializable {
	private static final long serialVersionUID = 1L;
	protected EShapeType shapeType;
	protected Point sw, ne;
	protected Shape shape;
	
	public CShapeManager(EShapeType shapeType) {	
		this.shapeType = shapeType;
		this.shape = null;
	}
	
	public void drawArrowHead(Graphics2D g, Point tip, Point tail, EArrowHeadDirection arrowHeadDirection) {
		double dy 		= tip.y - tail.y;
        double dx 		= tip.x - tail.x;
        double theta	= Math.atan2(dy, dx);
        double rad 		= (arrowHeadDirection != EArrowHeadDirection.LEFT) ? 30 : 120;	// 5*pi/6(rad) for upward-headed arrow
        double rho 		= theta + Math.toRadians(rad);
        
        for (int i = 0; i < 2; i++) {
        	double x = tip.x - 10 * Math.cos(rho);
        	double y = tip.y - 10 * Math.sin(rho);
            g.draw(new Line2D.Double(tip.x, tip.y, x, y));
            rho = theta - Math.toRadians(rad);
        }
	}
	
	public EShapeType getShapeType() {	return shapeType;	}
	public void draw(Graphics2D g, CShapeNode node) {};
	public void draw(Graphics2D g, CShapeNode node, EControlStatementMode mode) {};
}
