package adapter;

import java.util.Vector;

import org.antlr.v4.runtime.tree.ParseTree;

import Settings.CConstants;
import Settings.Constants;
import application.FlowChartCanvas;
import javafx.application.Platform;
import shapes.CCodeManager;
import shapes.CIfManager;
import shapes.CIteratorManager;
import shapes.CRootManager;
import shapes.CShapeManager;
import shapes.CShapeNode;
import shapes.CStartEndNode;

public class TreeToShape {
	private ParseTree tree;
	private FlowChartCanvas canvas;
	private Vector<CShapeManager> list;
	private CRootManager rootNode;
	private CShapeNode rv;
	
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
	public CShapeNode declareToShape(Vector<String> body, int type, int[] lines){
		rv = null;
//		Platform.runLater(new Runnable(){
//			//String buffer;
//			
//			@Override
//			public void run() {
				//String[] stmts = body.split("\n");
				if(type == Constants.CODE){
					rv = new CCodeManager(body.get(0), rootNode);
					rv.setLines(lines);
					rootNode.addNode(rootNode.getNodes().size()-1, rv);
				} else if(type == Constants.IF){
					rv = new CIfManager(CConstants.IF, body.get(1), rootNode);
					rv.setLines(lines);
					rootNode.addNode(rootNode.getNodes().size()-1,rv);
					rv.addNode(new CCodeManager(body.get(0), rv));
				} else if(type == Constants.ELSE){
					rv = new CIfManager(CConstants.ELSE, "", rootNode);
					rv.setLines(lines);
					rootNode.addNode(rootNode.getNodes().size()-1,rv);
					rv.addNode(new CCodeManager(body.get(0), rv));
				} else if(type == Constants.WHILE){
					rv = new CIteratorManager(CConstants.WHILE, body.get(1), rootNode);
					rv.setLines(lines);
					rootNode.addNode(rootNode.getNodes().size()-1,rv);
					rv.addNode(new CCodeManager(body.get(0), rv));
//					
				} else if(type == Constants.FOR){
					String condition[] = body.get(1).split(";");
					rv = new CIteratorManager(CConstants.FOR, condition[0], condition[1], condition[2], rootNode);
					rv.setLines(lines);
					rootNode.addNode(rootNode.getNodes().size()-1,rv);
					rv.addNode(rv.getNodes().size()-1, new CCodeManager(body.get(0), rv));
				}
//				System.out.println(rootNode.getNodes());
//			}
//			
//		});
		return rv;
	}
}
