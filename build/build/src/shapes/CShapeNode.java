package shapes;

import java.util.Vector;

public abstract class CShapeNode {
	protected String type;
	protected String condition;
	protected Vector<CShapeNode> bodies;
	public CShapeManager shape;
	protected int childNum;
	protected int depth;
	protected CShapeNode parent;
	
	public String getType(){
		return type;
	}
	public CShapeNode() {
		super();
		this.bodies = new Vector<>();
		childNum = 0;
		depth = 0;
	}
	public abstract CShapeManager getShape();
	public void clearNodes(){
		this.bodies.clear();
	}
	public void addNode(CShapeNode node){
		this.bodies.add(node);
		childNum++;
		node.depth = this.depth+1;
	}
	public void addNode(int index, CShapeNode node){
		this.bodies.add(index, node);
		childNum++;
		node.depth = this.depth+1;
	}
	public int findNode(CShapeNode node){
		return this.bodies.indexOf(node);
	}
	public CShapeNode findNext(CShapeNode node){
		if(findNode(node)<this.bodies.size()-1){
			return this.bodies.get(findNode(node)+1);
		}else {
			return this.bodies.lastElement();
		}
	}
	public Vector<CShapeNode> getNodes(){
		return bodies;
	}
	public int getchildNum(){
		return childNum;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public CShapeNode getParent() {
		return parent;
	}
	public void setParent(CShapeNode parent) {
		this.parent = parent;
	}
}
