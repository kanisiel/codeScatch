package shapes;

import java.util.Vector;

import Settings.CConstants;

public class CIfManager extends CShapeNode {
	private static final long serialVersionUID = 1L;
	
	public CIfManager(String type, String condition, CShapeNode parent) {
		super();
		this.parent = parent;
		this.type = type;
		this.bodies = new Vector<>();
		this.condition = condition;
		if(type.equals(CConstants.IF)){
			this.bodies.add(new CConditionManager(condition, this));
		}else if(type.equals(CConstants.ELSEIF)){
			this.bodies.add(new CConditionManager(condition, this));
		}
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
