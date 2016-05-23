package shapes;

import java.util.Vector;

public class CIteratorManager extends CShapeNode {
	private String type;
	private String init;
	private String condition;
	private String amount;
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
}
