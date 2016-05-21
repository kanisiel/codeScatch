package shapes;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

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
		Rectangle r = new Rectangle(p.getX(), p.getY(), d.getWidth(), d.getHeight());
		r.setStroke(stroke);
		r.setStrokeWidth(2);
		r.setFill(Color.TRANSPARENT);
		return r;
	}
	@Override
	public Text getText() {
		text = new Text(body);
		text.setX(tp.getX());
		text.setY(tp.getY());
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
		text.setWrappingWidth(Math.ceil(text.getLayoutBounds().getWidth()));
		this.setTd(new Dimension2D(text.getLayoutBounds().getWidth(), text.getLayoutBounds().getHeight()));
		this.setD(new Dimension2D((Math.floor(text.getWrappingWidth())*1.4)+20, td.getHeight()+30));
		this.setP(new Point2D((w.getWidth()/2)-(this.d.getWidth()/2), p.getY()+d.getHeight()+40));
		this.setTp(new Point2D(this.getP().getX()+10, this.getP().getY()+27));
		
		upperAnchor = new Point2D(this.p.getX()+this.d.getWidth()/2, this.p.getY());
		lowerAnchor = new Point2D(this.p.getX()+this.d.getWidth()/2, this.p.getY()+this.d.getHeight());
	}

}
