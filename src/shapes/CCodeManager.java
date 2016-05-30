package shapes;

import Settings.CConstants;

public class CCodeManager extends CShapeNode {
	private static final long serialVersionUID = 1L;
	public String body;

	public CCodeManager(String body, CShapeNode parent) {
		super();
		this.parent = parent;
		this.firstNode = this;
		// TODO Auto-generated constructor stub
		type = CConstants.CODE;
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
	}

	@Override
	public CShapeManager getShape() {
		// TODO Auto-generated method stub
		shape = new CRectangleManager(body);
		return shape;
	}
	
	
	
}
