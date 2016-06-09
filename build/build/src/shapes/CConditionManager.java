package shapes;

import Settings.CConstants;

public class CConditionManager extends CShapeNode {
	private static final long serialVersionUID = 1L;
	
	public CConditionManager(String condition, CShapeNode parent) {
		super();
		// TODO Auto-generated constructor stub
		this.condition = condition;
		this.parent = parent;
		type = CConstants.CONDITION;
		this.firstNode = this;
		shape = new CDiamondManager(this.condition);
	}
	
	@Override
	public CShapeManager getShape(){
		
		return shape;
	}
}
