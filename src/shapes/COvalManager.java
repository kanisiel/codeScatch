package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

import Settings.Constants.EArrowHeadDirection;
import Settings.Constants.EShapeType;

public class COvalManager extends CShapeManager {
	private static final long serialVersionUID = 1L;
	private int radius;
	
	public COvalManager(EShapeType shapeType) {
		super(shapeType);
		radius = 50;
	}
	
	@Override
	public void draw(Graphics2D g, CShapeNode node) {
		if (shapeType == EShapeType.START)
			this.drawStartOval(g, node);
		
		else
			this.drawStopOval(g, node);
	}
	
	public void drawStartOval(Graphics2D g, CShapeNode node) {
		g.drawOval(node.getX() - 25, node.getY(), radius, radius);
        //g.drawString("Fig: " + node.getShapeNo(), node.getX() - 100, node.getY() + 20);
        g.drawString(node.getShapeContent(), node.getX() - 18, node.getY() + 30);
        sw = new Point(node.getX(), node.getY() + 50);
        ne = new Point(node.getX(), node.getY() + 80);
        g.draw(new Line2D.Double(sw, ne));
        super.drawArrowHead(g, ne, sw, Color.blue, EArrowHeadDirection.DOWN);
        g.setColor(Color.black);
	}
	
	public void drawStopOval(Graphics2D g, CShapeNode node) {
		g.drawOval(node.getX() - 25, node.getY(), radius, radius);
		g.drawString(node.getShapeContent(), node.getX() - 15, node.getY() + 30);
	}
}
