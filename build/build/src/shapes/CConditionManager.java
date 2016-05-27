package shapes;

import Settings.CConstants;

public class CConditionManager extends CShapeNode {
	
	public CConditionManager(String condition, CShapeNode parent) {
		super();
		// TODO Auto-generated constructor stub
		this.condition = condition;
		this.parent = parent;
		type = CConstants.CONDITION;
	}
	
	@Override
	public CShapeManager getShape(){
		shape = new CDiamondManager(condition);
		return shape;
	}
}
