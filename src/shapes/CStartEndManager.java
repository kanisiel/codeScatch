package shapes;

import Settings.Constants;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class CStartEndManager extends CShapeManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CStartEndManager(int flag){
		super();
		// TODO Auto-generated constructor stub
		if(flag==Constants.EShapeType.START.ordinal()){
			this.setD(new Dimension2D(20, 20));
			this.setP(new Point2D((Constants.windowWidth/2), 30));
			this.setBody("Start");
			Text t = new Text(this.getBody());
			this.setTd(new Dimension2D(t.getLayoutBounds().getWidth(), t.getLayoutBounds().getHeight()));
			this.setTp(new Point2D((this.getP().getX()+(this.getD().getWidth()+10)), (this.getP().getY()+(this.getD().getHeight()/2)+(t.getLayoutBounds().getHeight()/3))));
		} else if(flag == Constants.EShapeType.STOP.ordinal()){
			this.setD(new Dimension2D(20, 20));
			this.setP(new Point2D((Constants.windowWidth/2), Constants.windowHeight-40));
			this.setBody("End");
			Text t = new Text(this.getBody());
			this.setTd(new Dimension2D(t.getLayoutBounds().getWidth(), t.getLayoutBounds().getHeight()));
			this.setTp(new Point2D((this.getP().getX()+(this.getD().getWidth()+10)), (this.getP().getY()+(this.getD().getHeight()/2))+(t.getLayoutBounds().getHeight()/3)));
		}
		upperAnchor = new Point2D(p.getX(), p.getY()-15);
		lowerAnchor = new Point2D(p.getX(), p.getY()+15);
	}

	@Override
	public void drawText(GraphicsContext gc) {
		// TODO Auto-generated method stub

	}
	@Override
	public void draw(GraphicsContext gc){
		gc.setFill(fill);
		gc.setStroke(stroke);
		//gc.fillOval(p.getX(), p.getY(), d.getWidth(), d.getHeight());
		gc.strokeText(body, tp.getX(), tp.getY());
	}

	@Override
	public Shape getShape() {
//		Circle shape = new Circle(p.getX()+(d.getWidth()/2), p.getY()+(d.getHeight()/2), 20);
//		shape.setFill(Color.BLACK);
		return new Circle(p.getX(), p.getY(), 15);
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
		upperAnchor = new Point2D(this.p.getX(), this.p.getY()-15);
		lowerAnchor = new Point2D(this.p.getX(), this.p.getY()+15);
	}

}
