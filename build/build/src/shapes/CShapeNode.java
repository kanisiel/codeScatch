package shapes;

import java.io.Serializable;
import java.util.Vector;

import Settings.CConstants;
import javafx.scene.layout.StackPane;
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
	protected StackPane sp;
	protected double boundWidth = 0;
	
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
		if(this.getClass().equals(CRootManager.class)){
			if(this.bodies.size()>1){
				CShapeNode end = this.bodies.lastElement();
				this.bodies.remove(end);
				this.bodies.add(node);
				this.bodies.add(end);
			} else {
				this.bodies.add(node);
			}
		} else {
			if(this.getClass().equals(CIteratorManager.class)&&this.getType().equals(CConstants.FOR)){
				CShapeNode end = this.bodies.lastElement();
				this.bodies.remove(end);
				this.bodies.add(node);
				this.bodies.add(end);
			}else {
				this.bodies.add(node);
			}
		}
		childNum++;
		node.depth = this.depth+1;
	}
	public void addNode(int index, CShapeNode node){
		this.bodies.add(index, node);
		childNum++;
		node.depth = this.depth+1;
	}
	public void remove(CShapeNode node){
		this.bodies.remove(node);
		this.childNum--;
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
	public Boolean findElse(){
		Boolean rv = false, flag = false;
		for(CShapeNode node : this.bodies){
			if(node.getType().equals(CConstants.IF)){
				flag = true;
			}else if(flag&&(!node.getType().equals(CConstants.ELSEIF)&&!node.getType().equals(CConstants.ELSE))){
				flag = false;
			}else if(flag&&node.getType().equals(CConstants.ELSE)){
				rv = true;
			}
		}
		return rv;
	}
	public CShapeNode getElse(){
		CShapeNode rv = null;
		Boolean flag = false;
		for(CShapeNode node : this.bodies){
			if(node.getType().equals(CConstants.IF)){
				flag = true;
			}else if(flag&&(!node.getType().equals(CConstants.ELSEIF)&&!node.getType().equals(CConstants.ELSE))){
				flag = false;
			}else if(flag&&node.getType().equals(CConstants.ELSE)){
				rv = node;
			}
		}
		return rv;
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
	public StackPane getSp() {
		return sp;
	}
	public void setSp(StackPane sp) {
		this.sp = sp;
	}
	public double getBoundWidth() {
		return boundWidth;
	}
	public void setBoundWidth(double boundWidth) {
		this.boundWidth = boundWidth;
	}
}
