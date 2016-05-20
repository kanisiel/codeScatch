package shapes;

import javafx.scene.canvas.GraphicsContext;

public class COvalManager extends CShapeManager {
	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	
	public COvalManager(){
		super();
	}
//	public COvalManager(EShapeType shapeType) {
//		super(shapeType);
//		width = 50;
//		height = 50;
//	}
//	
//	@Override
//	public void draw(Graphics2D g, CShapeNode node) {
//		if (shapeType == EShapeType.START)
//			drawStartOval(g, node);
//		
//		else
//			drawStopOval(g, node);
//	}
//	
//	public void drawStartOval(Graphics2D g, CShapeNode node) {
//		g.drawOval(node.getX() - 25, node.getY(), 50, 50);
//        //g.drawString("Fig: " + node.getShapeNo(), node.getX() - 100, node.getY() + 20);
//        g.drawString(node.getShapeContent(), node.getX() - 18, node.getY() + 30);
//        sw = new Point(node.getX(), node.getY() + 50);
//        ne = new Point(node.getX(), node.getY() + 80);
//        g.draw(new Line2D.Double(sw, ne));
//        super.drawArrowHead(g, ne, sw, Color.blue);
//        g.setColor(Color.black);
//	}
//	
//	public void drawStopOval(Graphics2D g, CShapeNode node) {
//		g.drawOval(node.getX() - 25, node.getY(), width, height);
//		g.drawString(node.getShapeContent(), node.getX() - 15, node.getY() + 30);
//	}

	@Override
	public void drawText(GraphicsContext gc) {
		// TODO Auto-generated method stub
	}
}
