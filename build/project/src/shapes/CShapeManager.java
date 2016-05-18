package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.io.Serializable;

import Settings.Constants.EShapeType;

public abstract class CShapeManager implements Serializable {
	private static final long serialVersionUID = 1L;
	protected EShapeType shapeType;
	protected Point sw, ne;
	
	public CShapeManager(EShapeType shapeType) {
		this.shapeType = shapeType;
	}
	
	public void drawArrowHead(Graphics2D g, Point tip, Point tail, Color color) {
		g.setPaint(color);
        double dy = tip.y - tail.y;
        double dx = tip.x - tail.x;
        double theta = Math.atan2(dy, dx);
        double x, y, rho = theta + Math.toRadians(30);
        
        for (int i = 0; i < 2; i++) {
            x = tip.x - 10 * Math.cos(rho);
            y = tip.y - 10 * Math.sin(rho);
            g.draw(new Line2D.Double(tip.x, tip.y, x, y));
            rho = theta - Math.toRadians(30);
        }
	}
	
	public EShapeType getShapeType() {	return shapeType;	}
	
	public void draw(Graphics2D g, CShapeNode node) {};
}
