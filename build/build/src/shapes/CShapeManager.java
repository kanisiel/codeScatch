package shapes;

import java.io.Serializable;
import java.util.Random;

import com.sun.javafx.tk.Toolkit;

import javafx.geometry.BoundingBox;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public abstract class CShapeManager implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6177225616280087402L;
	public double sid;
	protected String body;
	protected Point2D p, tp, upperAnchor, lowerAnchor, leftAnchor, rightAnchor;
	protected Dimension2D d, td;
	protected Color fill;
	protected Color stroke;
	protected Text text;
	protected Label label;
	protected int lines;
	
	public CShapeManager(){
		this.stroke = Color.BLACK;
		this.fill = Color.WHITE;
		setNewSid();
	}
	public Double setNewSid(){
		Random r = new Random();
		r.setSeed(System.currentTimeMillis());
		sid = Math.abs(r.nextGaussian());
		return sid;
	}
	public void setLines(int lines){ this.lines = lines; }
	public String getBody(){return body;}
	public void setBody(String body){ this.body = body; }
	public abstract void draw(GraphicsContext gc);
	public abstract void drawText(GraphicsContext gc);
	public abstract Shape getShape();
	public abstract Text getText();
	public abstract void setCoords(Point2D p, Dimension2D d, Dimension2D w);
	public Label getLabel(){
		return label;
	}
	public Point2D getP() {
		return p;
	}
	public Dimension2D getD() {
		return d;
	}
	public Point2D getTp() {
		return tp;
	}
	public Dimension2D getTd() {
		return td;
	}
	public void setP(Point2D p) {
		this.p = p;
	}
	public void setD(Dimension2D d) {
		this.d = d;
	}
	public void setTp(Point2D tp) {
		this.tp = tp;
	}
	public void setTd(Dimension2D td) {
		this.td = td;
	}
	public Color getFill() {
		return fill;
	}
	public Color getStroke() {
		return stroke;
	}
	public void setFill(Color fill) {
		this.fill = fill;
	}
	public void setStroke(Color stroke) {
		this.stroke = stroke;
	}
	
	public Point2D getUpperAnchor() {
		return upperAnchor;
	}
	public Point2D getLowerAnchor() {
		return lowerAnchor;
	}
	public void setUpperAnchor(Point2D upperAnchor) {
		this.upperAnchor = upperAnchor;
	}
	public void setLowerAnchor(Point2D lowerAnchor) {
		this.lowerAnchor = lowerAnchor;
	}
	public abstract Shape Shape();
	public Point2D getLeftAnchor() {
		return leftAnchor;
	}
	public Point2D getRightAnchor() {
		return rightAnchor;
	}
	public void setLeftAnchor(Point2D leftAnchor) {
		this.leftAnchor = leftAnchor;
	}
	public void setRightAnchor(Point2D rightAnchor) {
		this.rightAnchor = rightAnchor;
	}
	public BoundingBox getBounds(){
		Canvas buffer = new Canvas();
		GraphicsContext gc = buffer.getGraphicsContext2D();
		@SuppressWarnings("restriction")
		float fontWidth = Toolkit.getToolkit().getFontLoader().computeStringWidth(body, gc.getFont());
		@SuppressWarnings("restriction")
		float fontHeight = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().getFontMetrics(gc.getFont()).getLineHeight();
		String[] lines = body.split(System.getProperty("line.separator"));
		float width = fontWidth+30;
		float height = (fontHeight*lines.length)+30;
		return new BoundingBox(0, 0, width, height); 
	}
}
