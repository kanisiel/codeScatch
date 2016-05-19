package panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import Settings.Buttons.EToolBarButton;
import Settings.Constants.EShapeType;
import shapes.COvalManager;
import shapes.CShapeManager;
import shapes.CShapeNode;

public class FlowChartPane extends JPanel {
	private static final long serialVersionUID = 1L;
	private CShapeNode root;
	private CShapeManager shapeManager;
	
	public FlowChartPane() {
		root = new CShapeNode(0, EShapeType.START, "START");
        this.setCoords(root, 55, 30);
	}
	
	public void setShapeManager(CShapeManager shapeManager) {	this.shapeManager = shapeManager;	}

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
			if (node.getShapeType() == EShapeType.START || node.getShapeType() == EShapeType.STOP)
				shapeManager = new COvalManager(node.getShapeType());
			
			else 
				shapeManager = EToolBarButton.valueOf(node.getShapeType().name()).getShape();
		
			shapeManager.draw(g2d, node);
								
			if (node.getIsDiamond())
				this.drawNode(g, node.getLeft());
			
			this.drawNode(g, node.getRight());
		}
	}
}
