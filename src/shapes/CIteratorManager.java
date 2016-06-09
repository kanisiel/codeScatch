package shapes;

import java.util.Vector;

import Settings.CConstants;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

public class CIteratorManager extends CShapeNode {
	private static final long serialVersionUID = 1L;
	private Point2D boundP;
	private Dimension2D boundD;

	public CIteratorManager(String type, String condition, CShapeNode parent) {
		super();
		this.type = type;
		this.parent = parent;
		this.bodies = new Vector<>();
		this.condition = condition;
		this.bodies.add(new CConditionManager(condition, this));
		this.childNum = 1;
		this.firstNode = this.bodies.firstElement();
	}
	
	public CIteratorManager(String type, String init, String condition, String amount, CShapeNode parent) {
		super();
		this.type = type;
		this.parent = parent;
		this.bodies = new Vector<>();
		this.bodies.add(new CCodeManager(CConstants.INIT, init, this));
		this.bodies.add(new CConditionManager(condition, this));
		this.bodies.add(new CCodeManager(CConstants.AMOUNT, amount, this));
		this.childNum += 3;
		this.firstNode = this.bodies.firstElement();
	}
	
	
	public Vector<CShapeNode> getBodies(){
		return bodies;
	}	
	
	@Override
	public String getType() {
		return type;
	}

	@Override
	public CShapeManager getShape() {
		// TODO Auto-generated method stub
		return null;
	}
	
//	public Shape getBound(){
//		Rectangle r = new Rectangle();
//		double bufferX = Constants.centerAxisX;
//		double bufferD = 0;
//		double y = body.get(0).p.getY()-20;
//		double h = (body.get(body.size()-1).p.getY()) + 10;
//		for(CShapeManager csm : body){
//			if(csm.p.getX() < bufferX){
//				bufferX = csm.p.getX();
//			}
//			if(csm.d.getWidth() > bufferD){
//				bufferD = csm.d.getWidth();
//			}
//		}
//		bufferX -= 10;
//		bufferD += 20;
//		return r;
//	}
}
