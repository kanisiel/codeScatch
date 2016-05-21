package adapter;

import java.util.Vector;

import org.antlr.v4.runtime.tree.ParseTree;

import application.FlowChartCanvas;
import javafx.application.Platform;
import shapes.CDiamondManager;
import shapes.CParallelogramManager;
import shapes.CRectangleManager;
import shapes.CShapeManager;

public class TreeToShape {
	private ParseTree tree;
	private FlowChartCanvas canvas;
	private Vector<CShapeManager> list;
	
	public TreeToShape(FlowChartCanvas canvas){
		this.canvas = canvas;
		this.list = new Vector<>();
	}
	
	public void declareToShape(String body){
		
		Platform.runLater(new Runnable(){
			//String buffer;
			@Override
			public void run() {
				list.clear();
				canvas.init();
				canvas.clearCanvas();
				//String[] stmts = body.split("\n");
				for(String stmt : body.split("\n")){
					if(stmt.contains("=")){
						CRectangleManager declare = new CRectangleManager();
						declare.setBody(stmt);
						list.add(declare);
					} else if(stmt.contains("if(")){
						CDiamondManager declare = new CDiamondManager();
						declare.setBody(stmt);
						list.add(declare);
					} else {
						CParallelogramManager declare = new CParallelogramManager();
						declare.setBody(stmt);
						list.add(declare);
					}
				}
				for(CShapeManager e : list){
					canvas.addShape(e);
				}
			}
			
		});
		
	}
}
