package shapes;

import java.awt.Graphics2D;
import java.awt.Point;

import Settings.Constants.EArrowHeadDirection;
import Settings.Constants.EControlStatementMode;
import Settings.Constants.EShapeType;

public class CControlStatementManager extends CShapeManager {
	private static final long serialVersionUID = 1L;

	public CControlStatementManager(EShapeType shapeType) {
		super(shapeType);
	}
	
	@Override
	public void draw(Graphics2D g, CShapeNode node) {
		if (shapeType == EShapeType.CONDITION)
			this.drawCondition(g, node, node.getMode());
		
		else if (shapeType == EShapeType.LOOP)
			this.drawLoop(g, node, node.getMode());
	}
	
	private void drawDiamond(Graphics2D g, CShapeNode node) {
		if (node.getMode() == EControlStatementMode.DO_WHILE) {
			g.drawLine(node.getX() - 40, node.getY() + 30 + 50, node.getX(), node.getY() + 50);
			g.drawLine(node.getX() - 40, node.getY() + 30 + 50, node.getX(), node.getY() + 60 + 50);
			g.drawLine(node.getX(), node.getY() + 50, node.getX() + 40, node.getY() + 30 + 50);
			g.drawLine(node.getX() + 40, node.getY() + 30 + 50, node.getX(), node.getY() + 60 + 50);
		}
			
		else {
			g.drawLine(node.getX() - 40, node.getY() + 30, node.getX(), node.getY());
			g.drawLine(node.getX() - 40, node.getY() + 30, node.getX(), node.getY() + 60);
			g.drawLine(node.getX(), node.getY(), node.getX() + 40, node.getY() + 30);
			g.drawLine(node.getX() + 40, node.getY() + 30, node.getX(), node.getY() + 60);
		}
		
		if (node.getMode().name().equals("WHILE"))
			g.drawString(node.getMode().name(), node.getX() - 17, node.getY() + 35);
		else if (node.getMode().name().equals("FOR"))
			g.drawString(node.getMode().name(), node.getX() - 12, node.getY() + 35);
		else if (node.getMode().name().equals("DO_WHILE"))
			g.drawString(node.getMode().name(), node.getX() - 32, node.getY() + 35 + 50);
		else g.drawString(node.getMode().name().substring(0, 2), node.getX() - 5, node.getY() + 35); 
		
		if (shapeType == EShapeType.CONDITION) {
			g.drawLine(node.getX() + 40, node.getY() + 30, node.getX() + 100, node.getY() + 30);
	        g.drawLine(node.getX() + 100, node.getY() + 30, node.getX() + 100, node.getY() + 80);
	        g.drawLine(node.getX(), node.getY() + 60, node.getX(), node.getY() + 80);
		}
		
		else if (shapeType == EShapeType.LOOP){
			if (node.getMode() == EControlStatementMode.DO_WHILE) {
				g.drawLine(node.getX() + 40, node.getY() + 30 + 50, node.getX() + 60, node.getY() + 30 + 50);
		        g.drawLine(node.getX() + 60, node.getY() + 30 + 50, node.getX() + 60, node.getY() + 80 + 50);
		        
		        g.drawLine(node.getX(), node.getY() + 60 + 50, node.getX(), node.getY() + 70 + 50);
			}
			
			else {
				g.drawLine(node.getX() + 40, node.getY() + 30, node.getX() + 60, node.getY() + 30);
		        g.drawLine(node.getX() + 60, node.getY() + 30, node.getX() + 60, node.getY() + 80);
		        
		        g.drawLine(node.getX(), node.getY() + 60, node.getX(), node.getY() + 70);
			}
		}
	}
	
	private void drawCondition(Graphics2D g, CShapeNode node, EControlStatementMode mode) {
        CShapeNode leftRectNode = new CShapeNode(EShapeType.PROCESS, "YES"), rightRectNode;
        
        leftRectNode.setX(node.getX());
        leftRectNode.setY(node.getY() + 80);
        
        // IF_ONLY, CASE
        if (mode == EControlStatementMode.IF_ONLY || mode == EControlStatementMode.CASE) {
        	g.drawLine(node.getX(), node.getY() + 80 + 40, node.getX(), node.getY() + 80 + 60);
        	g.drawLine(node.getX() + 100, node.getY() + 80, node.getX() + 100, node.getY() + 80 + 50);
        	g.drawLine(node.getX(), node.getY() + 80 + 50, node.getX() + 100, node.getY() + 80 + 50);
        
        }
        
        // IF_ELSE or IF_ELSEIF_ELSE
        else if (mode == EControlStatementMode.IF_ELSE || mode == EControlStatementMode.IF_ELSEIF_ELSE){
        	rightRectNode = new CShapeNode(EShapeType.PROCESS, "NO");
        	
        	rightRectNode.setX(node.getX() + 100);
        	rightRectNode.setY(node.getY() + 80);
        	
        	node.insertNode(rightRectNode);
        	
        	g.drawLine(node.getX(), node.getY() + 80 + 40, node.getX(), node.getY() + 80 + 60);
        	g.drawLine(node.getX() + 100, node.getY() + 80 + 40, node.getX() + 100, node.getY() + 80 + 50);
        	g.drawLine(node.getX(), node.getY() + 80 + 50, node.getX() + 100, node.getY() + 80 + 50);
        }
        
        node.insertNode(leftRectNode);
        this.drawDiamond(g, node);
	}
	
	private void drawLoop(Graphics2D g, CShapeNode node, EControlStatementMode mode) {
		drawCommonLoopLine(g, node);
		CShapeNode newNode = new CShapeNode(EShapeType.PROCESS, "LOOP");
		
		if (mode == EControlStatementMode.WHILE || mode == EControlStatementMode.FOR) {
			newNode.setX(node.getX());
			newNode.setY(node.getY() + 70);
			
			g.drawLine(node.getX() + 60, node.getY() + 80, node.getX() + 60, node.getY() + 130);
			
			sw = new Point(node.getX() + 60, node.getY() + 70);
	        ne = new Point(node.getX() + 60, node.getY() + 80);
	        super.drawArrowHead(g, ne, sw, EArrowHeadDirection.DOWN);
	        
		}
		
		else if (mode == EControlStatementMode.DO_WHILE) {
			newNode.setX(node.getX());
			newNode.setY(node.getY());
			
			g.drawLine(node.getX(), node.getY() + 40, node.getX(), node.getY() + 50);
			
			sw = new Point(node.getX() + 60, node.getY() + 70 + 30);
	        ne = new Point(node.getX() + 60, node.getY() + 80 + 30);
	        super.drawArrowHead(g, ne, sw, EArrowHeadDirection.DOWN);
		}
		
		node.insertNode(newNode);
        this.drawDiamond(g, node);
	}
	
	private void drawCommonLoopLine(Graphics2D g, CShapeNode node) {
		g.drawLine(node.getX(), node.getY() - 7, node.getX() - 80, node.getY() - 7);
		        
        sw = new Point(node.getX() - 80, node.getY() + 80);
        ne = new Point(node.getX() - 80, node.getY() + 70);
        super.drawArrowHead(g, ne, sw, EArrowHeadDirection.DOWN);
        
		g.drawLine(node.getX() - 80, node.getY() - 7, node.getX() - 80, node.getY() + 120);
		g.drawLine(node.getX() - 80, node.getY() + 120, node.getX(), node.getY() + 120);
		g.drawLine(node.getX(), node.getY() + 120, node.getX(), node.getY() + 110);
		g.drawLine(node.getX(), node.getY() + 130, node.getX(), node.getY() + 140);
		g.drawLine(node.getX(), node.getY() + 130, node.getX() + 60, node.getY() + 130);
	}
}
