package shapes;

import javafx.scene.canvas.GraphicsContext;

public class CDiamondManager extends CShapeManager {
	private static final long serialVersionUID = 1L;

	
	public CDiamondManager(){
		super();
	}
//	public CDiamondManager(EShapeType shapeType) {
//		super(shapeType);
//	}
//	
//	@Override
//	public void draw(Graphics2D g, CShapeNode node) {
//		if (shapeType == EShapeType.CONDITION)
//			this.drawCondition(g, node);
//		
//		else
//			this.drawLoop(g, node);
//	}
//	
//	private void drawCondition(Graphics2D g, CShapeNode node) {
//		g.drawLine(node.getX() - 40, node.getY() + 40, node.getX(), node.getY());
//        g.drawLine(node.getX() - 40, node.getY() + 40, node.getX(), node.getY() + 80);
//        g.drawLine(node.getX(), node.getY(), node.getX() + 40, node.getY() + 40);
//        g.drawLine(node.getX() + 40, node.getY() + 40, node.getX(), node.getY() + 80);
//        g.drawString(node.getShapeContent(), node.getX() - 30, node.getY() + 40);
//        
//        //Draw No line
//        sw = new Point(node.getX(), node.getY() + 80);
//        ne = new Point(node.getX(), node.getY() + 110);
//        g.draw(new Line2D.Double(sw, ne));
//        super.drawArrowHead(g, ne, sw, Color.blue);
//        g.drawString("NO", node.getX() + 10, node.getY() + 95);
//        g.setColor(Color.black);
//                        
//        //Draw yes line
//        g.drawLine(node.getX()+40, node.getY()+40, node.getX() +200, node.getY() + 40);
//        g.drawLine(node.getX()+200, node.getY()+40, node.getX() + 200, node.getY() + 110);
//        sw = new Point(node.getX() + 200, node.getY() + 70);
//        ne = new Point(node.getX() + 200, node.getY() + 110);
//        super.drawArrowHead(g, ne, sw, Color.blue);
//        g.drawString("YES", node.getX() + 170, node.getY() + 95);
//        g.setColor(Color.black);
//	}
//	
//	private void drawLoop(Graphics2D g, CShapeNode node) {
//		this.drawCondition(g, node);
//	}


	@Override
	public void drawText(GraphicsContext gc) {
		// TODO Auto-generated method stub
	}
}
