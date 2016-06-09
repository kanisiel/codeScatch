package shapes;

import javafx.geometry.BoundingBox;

public class CCodeManager extends CShapeNode {
	private static final long serialVersionUID = 1L;
	public String body;
	public BoundingBox bounds;

	public CCodeManager(String type, String body, CShapeNode parent) {
		super();
		this.parent = parent;
		this.firstNode = this;
		// TODO Auto-generated constructor stub
		this.type = type;
		String[] tokens = body.split(";");
		this.body = "";
		String lineSeparator = System.getProperty("line.separator");
		for(String token : tokens){
			this.body += token;
			if(this.body.length()<String.join("", tokens).length()){
				this.body +=lineSeparator;
			}
		}
//		this.bodies.add(this);
		shape = new CRectangleManager(this.body);
		this.bounds = shape.getBounds();
	}

	@Override
	public CShapeManager getShape() {
		// TODO Auto-generated method stub
		
		return shape;
	}
	
	
	
}
