package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import Settings.Constants.EArrowHeadDirection;
import Settings.Constants.EShapeType;

public class CRectangleManager extends CShapeManager {
	private static final long serialVersionUID = 1L;

	public CRectangleManager(EShapeType shapeType) {
		super(shapeType);
		shape = new Rectangle();
	}
	
	@Override
	public void draw(Graphics2D g, CShapeNode node) {
		g.drawRect(node.getX() - 40, node.getY(), 80, 40);
		g.drawString(node.getShapeContent(), node.getX() - 12, node.getY() + 25);
	}
}
