package shapes;

import Settings.CConstants;

public class CCodeManager extends CShapeNode {
	public String body;

	public CCodeManager(String body, CShapeNode parent) {
		super();
		this.parent = parent;
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
	}

	@Override
	public CShapeManager getShape() {
		// TODO Auto-generated method stub
		shape = new CRectangleManager(body);
		return shape;
	}
	
	
	
}
