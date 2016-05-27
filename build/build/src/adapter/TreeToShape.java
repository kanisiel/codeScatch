package adapter;

import java.util.Vector;

import org.antlr.v4.runtime.tree.ParseTree;

import Settings.Constants;
import application.FlowChartCanvas;
import javafx.application.Platform;
import shapes.CCodeManager;
import shapes.CIfManager;
import shapes.CRootManager;
import shapes.CShapeManager;
import shapes.CStartEndNode;

public class TreeToShape {
	private ParseTree tree;
	private FlowChartCanvas canvas;
	private Vector<CShapeManager> list;
	private CRootManager rootNode;
	
	public TreeToShape(FlowChartCanvas canvas){
		this.canvas = canvas;
		this.list = new Vector<>();
		this.rootNode = new CRootManager();
		rootNode.addNode(new CStartEndNode(Constants.EShapeType.START.name(), rootNode));
		rootNode.addNode(new CStartEndNode(Constants.EShapeType.STOP.name(), rootNode));
		canvas.setRootNode(rootNode);
	}
	
	public void prepareCanvas(){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				list.clear();
				canvas.init();
				canvas.clearCanvas();
				rootNode.clearNodes();
				rootNode.addNode(new CStartEndNode(Constants.EShapeType.START.name(), rootNode));
				rootNode.addNode(new CStartEndNode(Constants.EShapeType.STOP.name(), rootNode));
			}
		});
	}
	public void drawToCanvas(){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				for(CShapeManager e : list){
					canvas.addShape(e);
				}
			}
		});
	}
	public void drawRoot(){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				canvas.redraw(rootNode);
			}
		});
	}
	public void declareToShape(Vector<String> body, int type){
		
		Platform.runLater(new Runnable(){
			//String buffer;
			
			@Override
			public void run() {
				//String[] stmts = body.split("\n");
				if(type == Constants.CODE){
					rootNode.addNode(rootNode.getNodes().size()-1, new CCodeManager(body.get(0), rootNode));
				} else if(type == Constants.IF){
					CIfManager ifm = new CIfManager(body.get(1), rootNode);
					rootNode.addNode(rootNode.getNodes().size()-1,ifm);
					ifm.addNode(new CCodeManager(body.get(0), rootNode));
				}
//				System.out.println(rootNode.getNodes());
			}
			
		});
	
	}
}
