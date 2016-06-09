package shapes;

import Settings.Constants;
import javafx.scene.paint.Color;

public class CStartEndNode extends CShapeNode {
	private static final long serialVersionUID = 1L;
	

	public CStartEndNode(String type, CShapeNode parent) {
		super();
		// TODO Auto-generated constructor stub
		this.type = type;
		this.parent = parent;
		
	}

	@Override
	public CShapeManager getShape() {
		
		
		if(type.equals(Constants.EShapeType.START.name())){
			shape = new CStartEndManager(Constants.EShapeType.START.ordinal());
			shape.setFill(Color.BLACK);
			shape.setStroke(Color.BLACK);
		} else {	
			shape = new CStartEndManager(Constants.EShapeType.STOP.ordinal());
			shape.setFill(Color.BLACK);
			shape.setStroke(Color.BLACK);
		}
		return shape;
	}

}
