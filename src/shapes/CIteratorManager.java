package shapes;

import java.util.Vector;

import Settings.Constants;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class CIteratorManager extends CShapeNode {
	private String type;
	private String init;
	private String condition;
	private String amount;
	private Point2D boundP;
	private Dimension2D boundD;
	private Vector<CShapeManager> body;

	public CIteratorManager(String type, String condition) {
		super();
		this.type = type;
		this.condition = condition;
		this.body = new Vector<>();
	}
	public CIteratorManager(String type, String init, String condition, String amount) {
		super();
		this.type = type;
		this.init = init;
		this.condition = condition;
		this.amount = amount;
		this.body = new Vector<>();
	}
	
	public void addBody(CShapeManager csm){
		this.body.add(csm);
	}

	public String getInit() {
		return init;
	}

	public String getCondition() {
		return condition;
	}

	public String getAmount() {
		return amount;
	}

	public void setInit(String init) {
		this.init = init;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public Vector<CShapeManager> getShapes(){
		Vector<CShapeManager> shapes = new Vector<>();
		shapes.add(new CRectangleManager(init));
		shapes.add(new CDiamondManager(condition));
		for(CShapeManager csm : body){
			shapes.add(csm);
		}
		shapes.add(new CRectangleManager(amount));
		return shapes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public Point2D getBoundP() {
		return boundP;
	}
	public void setBoundP(Point2D boundP) {
		this.boundP = boundP;
	}
	public Dimension2D getBoundD() {
		return boundD;
	}
	public void setBoundD(Dimension2D boundD) {
		this.boundD = boundD;
	}
	
	public Shape getBound(){
		Rectangle r = new Rectangle();
		double bufferX = Constants.centerAxisX;
		double bufferD = 0;
		double y = body.get(0).p.getY()-20;
		double h = (body.get(body.size()-1).p.getY()) + 10;
		for(CShapeManager csm : body){
			if(csm.p.getX() < bufferX){
				bufferX = csm.p.getX();
			}
			if(csm.d.getWidth() > bufferD){
				bufferD = csm.d.getWidth();
			}
		}
		bufferX -= 10;
		bufferD += 20;
		return r;
	}
}
