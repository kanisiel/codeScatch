package shapes;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class COvalManager extends CShapeManager {
	private static final long serialVersionUID = 1L;

	
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
	
	@Override
	public void draw(GraphicsContext gc){
		gc.setFill(fill);
		gc.setStroke(stroke);
		gc.fillOval(p.getX(), p.getY(), d.getWidth(), d.getHeight());
		gc.strokeText(body, tp.getX(), tp.getY());
	}

	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub
		Ellipse e = new Ellipse(p.getX(), p.getY(), d.getWidth()/2, d.getHeight()/2);
		e.setStroke(stroke);
		e.setStrokeWidth(2);
		e.setFill(Color.TRANSPARENT);
		return e;
	}
	@Override
	public Text getText() {
		text = new Text(body);
		text.setX(tp.getX());
		text.setY(tp.getY());
		return text;
	}

	@Override
	public void setCoords(Point2D p, Dimension2D d, Dimension2D w) {
		// TODO Auto-generated method stub
		Text t = new Text(body);
		this.setTd(new Dimension2D(t.getLayoutBounds().getWidth(), t.getLayoutBounds().getHeight()));
		this.setP(new Point2D((w.getWidth()/2)-(td.getWidth()/2+20), p.getY()+d.getHeight()+40));
		
		this.setD(new Dimension2D(td.getWidth()+40, td.getHeight()+30));
		this.setTp(new Point2D(this.getP().getX()+20, this.getP().getY()+27));
	}
}
