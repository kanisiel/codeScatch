package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

import Settings.Constants.EArrowHeadDirection;
import Settings.Constants.EShapeType;

public class CRectangleManager extends CShapeManager {
	private static final long serialVersionUID = 1L;

	public CRectangleManager(EShapeType shapeType) {
		super(shapeType);
	}
	
	@Override
	public void draw(Graphics2D g, CShapeNode node) {
		g.drawRect(node.getX() - 25, node.getY(), 50, 50);
		g.drawString(node.getShapeContent(), node.getX() - 24, node.getY() - 24);
		sw = new Point(node.getX(), node.getY() + 50);
        ne = new Point(node.getX(), node.getY() + 80);
		g.draw(new Line2D.Double(sw, ne));
        super.drawArrowHead(g, ne, sw, Color.blue, EArrowHeadDirection.DOWN);
        g.setColor(Color.black);
	}
}
