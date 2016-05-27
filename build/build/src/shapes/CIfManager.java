package shapes;

import java.util.Vector;

import Settings.CConstants;

public class CIfManager extends CShapeNode {
	
	public CIfManager(String condition, CShapeNode parent) {
		super();
		this.parent = parent;
		type = CConstants.IF;
		this.bodies = new Vector<>();
		this.condition = condition;
		this.bodies.add(new CConditionManager(condition, this));
		// TODO Auto-generated constructor stub
	}
	
	public Vector<CShapeNode> getBodies(){
		return bodies;
	}
	@Override
	public CShapeManager getShape() {
		// TODO Auto-generated method stub
		return null;
	}
}
