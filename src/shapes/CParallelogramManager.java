package shapes;

import java.awt.Graphics2D;

import Settings.Constants.EShapeType;

public class CParallelogramManager extends CShapeManager {
	private static final long serialVersionUID = 1L;
	private final int len; 

	public CParallelogramManager(EShapeType shapeType) {
		super(shapeType);
		len = 80;
	}
	
	@Override
	public void draw(Graphics2D g, CShapeNode node) {
		g.drawLine(node.getX() + 80, node.getY(), node.getX() + 80 + len, node.getY());
		g.drawLine(node.getX() + 80, node.getY(), node.getX() + 80 - len / 2, node.getY() + len);
		g.drawLine(node.getX() + 80 - len / 2, node.getY() + len, node.getX() + 80 + len / 2, node.getY() + len);
		g.drawLine(node.getX() + 80 + len, node.getY(), node.getX() + 80 + len / 2, node.getY() + len);
		g.drawString(node.getShapeContent(), node.getX() + 80, node.getY() + len / 2);
	}
}
