package application;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import Settings.Constants;
import Settings.Constants.EShapeType;
import shapes.CDiamondManager;
import shapes.COvalManager;
import shapes.CParallelogramManager;
import shapes.CRectangleManager;
import shapes.CShapeManager;
import shapes.CShapeNode;

public class FlowChartCanvas extends Canvas {
	private static final long serialVersionUID = 1L;
	private CShapeNode root;
	private CShapeManager shapeManager;
	private Image img;
	final int windowWidth = (Constants.FRAME_W-76)/2;
	final int windowHeight = Constants.FRAME_H-30;
	
	public FlowChartCanvas() {
		this.setSize(windowWidth, windowHeight);
		
	}
	public void init(){
		Graphics2D g2d = (Graphics2D) this.getGraphics();
		System.out.println(g2d);
	}
	
	public void setShapeManager(CShapeManager shapeManager) {	
		this.shapeManager = shapeManager;
	}

	public void setCoords(CShapeNode node, int x, int y){
        if (node != null){
            node.setX(x);
            node.setY(y);
               
	        if (!node.getIsDiamond())
	        	this.setCoords(node.getRight(), x, y + 80);
	            
	        else {
	        	this.setCoords(node.getLeft(), x , y + 110);
	        	this.setCoords(node.getRight(), x + 200 , y + 110);
	        }
        }
    }
	
	public CShapeNode getRootNode() {	return root;	}
	
	public void paint(Graphics g) {
		super.paint(g);
		this.drawNode(g, root);
	}
	
	public void drawNode(Graphics g, CShapeNode node) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (node != null) {
			if (node.getShapeType() == EShapeType.START)
				shapeManager = new COvalManager(node.getShapeType());
			/*
			else 
				shapeManager = EToolBarButton.valueOf(node.getShapeType().name()).getShape();
			*/
			else if (node.getShapeType() == EShapeType.PROCESS)
				// draw rectangle
				shapeManager = new CRectangleManager(node.getShapeType());
			
			else if (node.getShapeType() == EShapeType.IO)
				// draw parallogram
				shapeManager = new CParallelogramManager(node.getShapeType());
			
			else if (node.getShapeType() == EShapeType.CONDITION)
				shapeManager = new CDiamondManager(node.getShapeType());
			
			else if (node.getShapeType() == EShapeType.LOOP)
				// draw as CONDITION but one of the arrows is toward upward
				shapeManager = new CDiamondManager(node.getShapeType());
			
			else if (node.getShapeType() == EShapeType.FUNCTION) {
				// ... complex ... it may be an add on button...
			}
			
			else if (node.getShapeType() == EShapeType.STOP)
				shapeManager = new COvalManager(node.getShapeType());
			
			shapeManager.draw(g2d, node);
								
			if (!node.getIsDiamond())
				this.drawNode(g, node.getLeft());
			
			else {
				this.drawNode(g, node.getLeft());
				this.drawNode(g, node.getRight());
			}
		}
	}
	

	  public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	  }
}

