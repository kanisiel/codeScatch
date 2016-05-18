package panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import Settings.Constants.EShapeType;
import shapes.CDiamondManager;
import shapes.COvalManager;
import shapes.CParallelogramManager;
import shapes.CRectangleManager;
import shapes.CShapeManager;
import shapes.CShapeNode;

public class FlowChartPane extends JPanel {
	private static final long serialVersionUID = 1L;
	private CShapeNode root;
	private CShapeManager shapeManager;
	
	public FlowChartPane() {
		root = new CShapeNode(0, EShapeType.START, "START", false);
        this.setCoords(root, 55, 30);
        //shapeManager = new CShapeManager(EShapeType.START);
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
}
