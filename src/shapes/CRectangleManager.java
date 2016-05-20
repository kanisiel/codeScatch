package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CRectangleManager extends CShapeManager {
	private static final long serialVersionUID = 1L;
	
	public CRectangleManager(){
		super();
	}
	
	@Override
	public void drawText(GraphicsContext gc){
		gc.setStroke(Color.BLACK);
		gc.strokeText(body, 30, 30);
	}
	
	@Override
	public void draw(GraphicsContext gc){
		gc.setStroke(Color.BLACK);
		gc.strokeRect(30, 30, 100, 100);
	}
//	public CRectangleManager(EShapeType shapeType) {
//		super(shapeType);
//	}
//	
//	@Override
//	public void draw(Graphics2D g, CShapeNode node) {
//		g.drawRect(node.getX() - 25, node.getY(), 80, 80);
//		g.drawString(node.getShapeContent(), node.getX() - 24, node.getY() - 24);
//		sw = new Point(node.getX(), node.getY() + 80);
//        ne = new Point(node.getX(), node.getY() + 110);
//		g.draw(new Line2D.Double(sw, ne));
//        super.drawArrowHead(g, ne, sw, Color.blue);
//        g.setColor(Color.black);
//	}

}
