package application;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import Settings.Constants;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import shapes.CShapeManager;

public class FlowChartManager {
	private Vector<CShapeManager> nodes;
	private Vector<Group> allnodes;
	private Map<String, Group> connects;
	private Vector<StackPane> connection;
	
	public FlowChartManager(){
		this.nodes = new Vector<>();
		this.allnodes = new Vector<>();
		this.setConnects(new LinkedHashMap<>());
		this.setConnection(new Vector<>());
	}
	public void initManager(CShapeManager start, CShapeManager end){
		this.nodes.clear();
		this.nodes.add(start);
		this.nodes.add(end);
	}
	public void initManager(){
		this.nodes.clear();
		this.allnodes.clear();
	}
	public CShapeManager getEndNode(){
		return nodes.get(nodes.size()-1);
	}
	public void addNode(CShapeManager node){
		Boolean flag = false;
		int index = -1;
		for(CShapeManager s : this.nodes){
			if(s.getText().equals(Constants.EShapeType.STOP.name())){
				flag = true;
				index = this.nodes.indexOf(s);
			}
		}
		if(flag && index > 0){
			this.nodes.insertElementAt(node, index);
		} else {
			this.nodes.add(node);
		}
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
		if(nodes.indexOf(node) < nodes.size()-1){
			return nodes.get(nodes.indexOf(node)+1);
		}else {
			return node;
		}
	}
	public void updateConnect(String key, Group g){
		this.connects.replace(key, g);
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
	public Map<String, Group> getConnects() {
		return connects;
	}
	public void setConnects(Map<String, Group> connects) {
		this.connects = connects;
	}
	public Vector<StackPane> getConnection() {
		return connection;
	}
	public void setConnection(Vector<StackPane> connection) {
		this.connection = connection;
	}
}
