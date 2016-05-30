package shapes;

import java.io.Serializable;
import java.util.Vector;

import Settings.CConstants;
import javafx.scene.shape.Shape;

public abstract class CShapeNode implements Serializable {
	public static final long serialVersionUID = 1L;
	protected String type;
	protected String condition;
	protected Vector<CShapeNode> bodies;
	public CShapeManager shape;
	protected int childNum;
	protected int depth;
	protected CShapeNode parent;
	protected CShapeNode firstNode;
	protected Vector<Shape> connections;
	public int[] lines;
	
	public String getType(){
		return type;
	}
	public CShapeNode() {
		super();
		this.bodies = new Vector<>();
		this.connections = new Vector<>();
		childNum = 0;
		depth = 0;
	}
	public abstract CShapeManager getShape();
	public void clearNodes(){
		this.bodies.clear();
	}
	public void addConnection(Shape conn){
		this.connections.add(conn);
	}
	public void addConnection(int index, Shape conn){
		this.connections.add(index, conn);
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
	public void addLast(CShapeNode node){
		int index = this.bodies.indexOf(this.bodies.lastElement());
		this.bodies.add(index-1, node);
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
	public CShapeNode findPrev(CShapeNode node){
		if(findNode(node)>0){
			return this.bodies.get(findNode(node)-1);
		}else if(findNode(node)==0){
			return this.bodies.firstElement();
		} else {
			return parent.findPrev(this).getNodes().lastElement();
		}
	}
	public CShapeNode findCondition(){
		for(CShapeNode node : this.bodies){
			if(node.getType().equals(CConstants.CONDITION)){
				return node;
			}
		}
		return null;
	}
	public Vector<CShapeNode> getNodes(){
		return bodies;
	}
	public CShapeNode getFirstNode(){
		
		return this.bodies.firstElement();
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
	public Vector<Shape> getConnections() {
		return connections;
	}
	public void setConnections(Vector<Shape> connections) {
		this.connections = connections;
	}
	public int[] getLines() {
		return lines;
	}
	public void setLines(int[] lines) {
		this.lines = lines;
	}
}
