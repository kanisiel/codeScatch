package adapter;

import java.util.Vector;

import org.antlr.v4.runtime.tree.ParseTree;

import application.FlowChartCanvas;
import javafx.application.Platform;
import shapes.CDiamondManager;
import shapes.COvalManager;
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
		list.clear();
		//String[] stmts = body.split("\n");
		for(String stmt : body.split("\n")){
			if(body.contains("=")){
				CRectangleManager declare = new CRectangleManager();
				declare.setBody(stmt);
				list.add(declare);
			} else if(body.contains("if(")){
				CDiamondManager declare = new CDiamondManager();
				declare.setBody(stmt);
				list.add(declare);
			} else {
				COvalManager declare = new COvalManager();
				declare.setBody(stmt);
				list.add(declare);
			}
		}
		
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				for(CShapeManager e : list){
					canvas.addShape(e);
				}
			}
			
		});
		
	}
}
