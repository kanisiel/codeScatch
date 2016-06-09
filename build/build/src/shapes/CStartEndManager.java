package shapes;

import Settings.Constants;
import javafx.geometry.BoundingBox;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CStartEndManager extends CShapeManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Circle shape;
	private int flag;
	
	public CStartEndManager(int flag){
		super();
		this.flag = flag;
		// TODO Auto-generated constructor stub
		if(flag==Constants.EShapeType.START.ordinal()){
			this.setD(new Dimension2D(40, 40));
			this.setP(new Point2D((Constants.windowWidth/2), 30));
			this.setBody("Start");
			label = new Label("Start");
			Text t = new Text(this.getBody());
			t.setFont(new Font("Arial", Double.parseDouble("10.0")));
			this.setTd(new Dimension2D(t.getLayoutBounds().getWidth(), t.getLayoutBounds().getHeight()));
			this.setTp(new Point2D((this.getP().getX()+(this.getD().getWidth()+5)), (this.getP().getY()+5.0)));
		} else if(flag == Constants.EShapeType.STOP.ordinal()){
			this.setD(new Dimension2D(40, 40));
			this.setP(new Point2D((Constants.windowWidth/2), Constants.windowHeight-30));
			this.setBody("End");
			label = new Label("End");
			Text t = new Text(this.getBody());
			t.setFont(new Font("Arial", Double.parseDouble("10.0")));
			this.setTd(new Dimension2D(t.getLayoutBounds().getWidth(), t.getLayoutBounds().getHeight()));
			this.setTp(new Point2D((this.getP().getX()+(this.getD().getWidth()+5)), (this.getP().getY())+(5.0)));
		}
		shape = new Circle(this.p.getX(), this.p.getY(), 15);
		if(flag == Constants.EShapeType.START.ordinal()){
			shape.setFill(Color.GREEN);
		} else {
			shape.setFill(Color.RED);
		}
		setAnchor();
	}
	public CStartEndManager(int flag, Point2D p){
		super();
		this.flag = flag;
		// TODO Auto-generated constructor stub
		if(flag==Constants.EShapeType.START.ordinal()){
			this.setD(new Dimension2D(40, 40));
			this.setP(p);
			this.setBody("Start");
			label = new Label("Start");
			Text t = new Text(this.getBody());
			t.setFont(new Font("Arial", Double.parseDouble("10.0")));
			this.setTd(new Dimension2D(t.getLayoutBounds().getWidth(), t.getLayoutBounds().getHeight()));
			this.setTp(new Point2D((this.getP().getX()+(this.getD().getWidth()+5)), (this.getP().getY()+5.0)));
		} else if(flag == Constants.EShapeType.STOP.ordinal()){
			this.setD(new Dimension2D(40, 40));
			this.setP(p);
			this.setBody("End");
			label = new Label("End");
			Text t = new Text(this.getBody());
			t.setFont(new Font("Arial", Double.parseDouble("10.0")));
			this.setTd(new Dimension2D(t.getLayoutBounds().getWidth(), t.getLayoutBounds().getHeight()));
			this.setTp(new Point2D((this.getP().getX()+(this.getD().getWidth()+5)), (this.getP().getY())+(5.0)));
		}
		shape = new Circle(this.p.getX(), this.p.getY(), 15);
		if(flag == Constants.EShapeType.START.ordinal()){
			shape.setFill(Color.GREEN);
		} else {
			shape.setFill(Color.RED);
		}
		setAnchor();
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
		
		return shape;
	}
	
	@Override
	public Shape Shape(){
		return shape;
	}
	
	@Override
	public Text getText() {
		text = new Text(body);
		text.setX(tp.getX());
		text.setY(tp.getY());
		text.setFill(Color.BLACK);
		return text;
	}
	@Override
	public BoundingBox getBounds(){
	
		return new BoundingBox(0, 0, 30, 30); 
	}
	@Override
	public void setCoords(Point2D p, Dimension2D d, Dimension2D w) {
		// TODO Auto-generated method stub
		upperAnchor = new Point2D(this.p.getX(), this.p.getY()-15);
		lowerAnchor = new Point2D(this.p.getX(), this.p.getY()+15);
	}
	public void setAnchor(){
		upperAnchor = new Point2D(p.getX(), p.getY()-15);
		lowerAnchor = new Point2D(p.getX(), p.getY()+15);
	}
	public void setUpperAnchor(Point2D p){
		upperAnchor = p;
	}
}
