package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

import Settings.Constants.EArrowHeadDirection;
import Settings.Constants.EShapeType;

public class CParallelogramManager extends CShapeManager {
	private static final long serialVersionUID = 1L;
	private final int len; 

	public CParallelogramManager(EShapeType shapeType) {
		super(shapeType);
		len = 60;
	}
	
	@Override
	public void draw(Graphics2D g, CShapeNode node) {
		g.drawLine(node.getX() - 25, node.getY(), node.getX() - 25 + len, node.getY());
		g.drawLine(node.getX() - 25, node.getY(), node.getX() - 25 - len / 2, node.getY() + len - 10);
		g.drawLine(node.getX() - 25 - len / 2, node.getY() + len - 10, node.getX() - 25 + len / 2, node.getY() + len - 10);
		g.drawLine(node.getX() - 25 + len, node.getY(), node.getX() - 25 + len / 2, node.getY() + len - 10);
		
		/* adjust coordinate of arrow */
		sw = new Point(node.getX(), node.getY() + 50);
        ne = new Point(node.getX(), node.getY() + 80);
		g.draw(new Line2D.Double(sw, ne));
        super.drawArrowHead(g, ne, sw, Color.blue, EArrowHeadDirection.DOWN);
		
		g.drawString(node.getShapeContent(), node.getX() - 25, node.getY() + len / 2);
		
		g.setColor(Color.black);
	}
}
