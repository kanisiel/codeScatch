package shapes;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class CConnectManager extends CShapeManager {
	private Point2D upper, lower;
	private Path path;
	
	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawText(GraphicsContext gc) {
		// TODO Auto-generated method stub

	}

	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub
		MoveTo mtl = new MoveTo(upper.getX(), upper.getY());
		LineTo ltl = new LineTo(lower.getX(), lower.getY());
		Path path = new Path(mtl, ltl);
		path.setStroke(Color.BLACK);
		path.setStrokeWidth(2);
		return path;
	}

	@Override
	public Text getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCoords(Point2D p, Dimension2D d, Dimension2D w) {
		// TODO Auto-generated method stub
		
	}
	public void setCoord(Point2D upper, Point2D lower){
		this.upper = upper;
		this.lower = lower;
		this.p = upper;
		this.d = new Dimension2D(lower.getX()-upper.getX(), lower.getY()-upper.getY());
		this.upperAnchor = upper;
		this.lowerAnchor = lower;
	}

	@Override
	public Shape Shape() {
		// TODO Auto-generated method stub
		return path;
	}

}
