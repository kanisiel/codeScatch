package application;

import java.util.Vector;

import Settings.Constants;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import shapes.CShapeManager;
import shapes.CStartEndManager;

public class FlowChartManager {
	private Vector<CShapeManager> nodes;
	private Vector<Group> allnodes;
	
	public FlowChartManager(){
		this.nodes = new Vector<>();
		this.allnodes = new Vector<>();
	}
	public void initManager(CShapeManager start, CShapeManager end){
		this.nodes.clear();
		this.nodes.add(start);
		this.nodes.add(end);
	}
	public void initManager(){
		this.nodes.clear();
		this.allnodes.clear();
		CStartEndManager sems = new CStartEndManager(Constants.EShapeType.START.ordinal());
		sems.setFill(Color.BLACK);
		sems.setStroke(Color.BLACK);
		CStartEndManager seme = new CStartEndManager(Constants.EShapeType.STOP.ordinal());
		seme.setFill(Color.BLACK);
		seme.setStroke(Color.BLACK);
		this.nodes.add(sems);
		this.nodes.add(seme);
	}
	public CShapeManager getEndNode(){
		return nodes.get(nodes.size()-1);
	}
	public void addNode(CShapeManager node){
		this.nodes.add((this.nodes.size()-1), node);
	}
	
	public int findNode(CShapeManager node){
		return nodes.indexOf(node);
	}
	public CShapeManager findPrev(CShapeManager node){
		if(nodes.indexOf(node)==0){
			return node;
		}else{
			return nodes.get(nodes.indexOf(node)-1);
		}
	}
	public CShapeManager findNext(CShapeManager node){
		if(nodes.indexOf(node)==nodes.size()){
			return node;
		}
		return nodes.get(nodes.indexOf(node)+1);
	}
	public int findAllNode(CShapeManager node){
		return allnodes.indexOf(node);
	}
	public Vector<CShapeManager> getNodes() {
		return nodes;
	}
	public Vector<Group> getAllNodes() {
		return allnodes;
	}
	public void setNodes(Vector<CShapeManager> nodes) {
		this.nodes = nodes;
	}
	public void setCanvases(Vector<Group> allnodes) {
		this.allnodes = allnodes;
	}
}
