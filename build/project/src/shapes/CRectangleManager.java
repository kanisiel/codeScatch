package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

import Settings.Constants.EShapeType;

public class CRectangleManager extends CShapeManager {
	private static final long serialVersionUID = 1L;

	public CRectangleManager(EShapeType shapeType) {
		super(shapeType);
	}
	
	@Override
	public void draw(Graphics2D g, CShapeNode node) {
		g.drawRect(node.getX() - 25, node.getY(), 80, 80);
		g.drawString(node.getShapeContent(), node.getX() - 24, node.getY() - 24);
		sw = new Point(node.getX(), node.getY() + 80);
        ne = new Point(node.getX(), node.getY() + 110);
		g.draw(new Line2D.Double(sw, ne));
        super.drawArrowHead(g, ne, sw, Color.blue);
        g.setColor(Color.black);
	}
}
