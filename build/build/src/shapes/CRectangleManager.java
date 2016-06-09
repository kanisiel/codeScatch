package shapes;

import com.sun.javafx.tk.Toolkit;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

@SuppressWarnings("restriction")
public class CRectangleManager extends CShapeManager {
	private static final long serialVersionUID = 1L;
	private static final double oneLineH = 15.310546875;
	
	Rectangle r;
	
	public CRectangleManager(String body){
		super();
		this.body = body;
		text = new Text(body);
		label = new Label(body);
	}
	
	@Override
	public void drawText(GraphicsContext gc){
		gc.setStroke(Color.BLACK);
		gc.strokeText(body, 30, 30);
	}
	@Override
	public void setBody(String body){
		this.body = body;
//		Text t = new Text(body);
//		
//		this.setD(new Dimension2D(20, 20));
//		this.setP(new Point2D((Constants.windowWidth/2)-10, Constants.windowHeight-40));
//		this.setBody("End");
//		this.setTd(new Dimension2D(t.getLayoutBounds().getWidth(), t.getLayoutBounds().getHeight()));
//		this.setTp(new Point2D((this.getP().getX()+(this.getD().getWidth()+10)), (this.getP().getY()+(this.getD().getHeight()/2))+(t.getLayoutBounds().getHeight()/3)));
	}
	@Override
	public void draw(GraphicsContext gc){
		gc.setFill(fill);
		gc.setStroke(stroke);
		//gc.strokeRect(p.getX(), p.getY(), d.getWidth(), d.getHeight());
		gc.strokeText(body, tp.getX(), tp.getY());
	}
	@Override
	public Shape getShape(){
		r = new Rectangle(0, 0, getBounds().getWidth(), getBounds().getHeight());
		r.setStroke(stroke);
		r.setStrokeWidth(2);
		r.setFill(fill);
//		text.getBoundsInLocal().getWidth()
		return r;
	}
	@Override
	public Text getText() {
//		text.setX(tp.getX());
//		text.setY(tp.getY());
//		text.setBoundsType(TextBoundsType.LOGICAL);
		return text;
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

	@Override
	public void setCoords(Point2D p, Dimension2D d, Dimension2D w) {
		// TODO Auto-generated method stub
		text = new Text(body);
		Canvas buffer = new Canvas();
		GraphicsContext gc = buffer.getGraphicsContext2D();
		@SuppressWarnings("restriction")
		float width = Toolkit.getToolkit().getFontLoader().computeStringWidth(body, gc.getFont());
		@SuppressWarnings("restriction")
		float height = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().getFontMetrics(gc.getFont()).getLineHeight();
		String[] line = body.split(System.getProperty("line.separator"));
		this.setTd(new Dimension2D(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight()));
		this.setD(new Dimension2D(width+30, (height*line.length)+30));
		this.setP(new Point2D((w.getWidth()/2)-(this.d.getWidth()/2), p.getY()+d.getHeight()+40));
		this.setTp(new Point2D(this.getP().getX()+10, this.getP().getY()+(1.8*oneLineH)));
		
		upperAnchor = new Point2D(this.p.getX()+this.d.getWidth()/2, this.p.getY());
		lowerAnchor = new Point2D(this.p.getX()+this.d.getWidth()/2, this.p.getY()+this.d.getHeight());
		leftAnchor = new Point2D(this.p.getX(), this.p.getY()+this.d.getHeight()/2);
		rightAnchor = new Point2D(this.leftAnchor.getX()+this.d.getWidth(), this.leftAnchor.getY());
	}

	@Override
	public Shape Shape() {
		// TODO Auto-generated method stub
		return r;
	}
	
	

}
