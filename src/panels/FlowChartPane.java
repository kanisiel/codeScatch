package panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import Settings.Buttons.EToolBarButton;
import Settings.Constants;
import Settings.Constants.EControlStatementMode;
import Settings.Constants.EShapeType;
import converter.CConverter;
import shapes.COvalManager;
import shapes.CShapeManager;
import shapes.CShapeNode;

public class FlowChartPane extends JPanel {
	private static final long serialVersionUID = 1L;
	private CShapeNode root;
	private CShapeManager shapeManager;
	private MouseHandler mouseHandler;
	private KeyHandler keyHandler;
	private CConverter converter;
	
	public FlowChartPane() {
		mouseHandler = new MouseHandler();
		keyHandler = new KeyHandler();
		
		this.addMouseListener(mouseHandler);
		this.addKeyListener(keyHandler);
		
		converter = new CConverter(this);
		this.setFocusable(true);
		
		root = new CShapeNode(0, EShapeType.START, "START");
        this.setCoords(root, Constants.FRAME_W / 5, 30);
        
        this.testDraw();
	}
	
	public void setCoords(CShapeNode node, int x, int y){
        if (node != null){
            node.setX(x);
            node.setY(y);
            
	        if (!node.getIsDiamond())
	        	this.setCoords(node.getRight(), x, y + 80);
	        
	        else
	        	this.setCoords(node.getRight(), x, y + 140);
	            
//	        else {
//	        	this.setCoords(node.getLeft(), x , y + 80);
//	        	this.setCoords(node.getRight(), x + 100 , y + 80);
//	        }
        }
    }
	
	public void setShapeManager(CShapeManager shapeManager) {	this.shapeManager = shapeManager;	}

	public CShapeNode getRootNode() {	return root;	}
	
	public void paint(Graphics g) {
		super.paint(g);
		this.drawNode(g, root);
	}
	
	public void drawNode(Graphics g, CShapeNode node) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (node != null) {
			if (node.getShapeType() == EShapeType.START || node.getShapeType() == EShapeType.END)
				shapeManager = new COvalManager(node.getShapeType());
			
			else 
				shapeManager = EToolBarButton.valueOf(node.getShapeType().name()).getShape();
			
			System.out.println(shapeManager.getShapeType());
			
			if (node.getShapeType() == EShapeType.CONDITION)
				shapeManager.draw(g2d, node);
			
			else shapeManager.draw(g2d, node);
								
//			if (node.getIsDiamond())
//				this.drawNode(g, node.getLeft());
			
			this.drawNode(g, node.getRight());
		}
	}
	
	public void testDraw() {
		CShapeNode[] nodes = {
				new CShapeNode(EShapeType.LOOP, EControlStatementMode.WHILE),
				new CShapeNode(EShapeType.LOOP, EControlStatementMode.DO_WHILE),
				new CShapeNode(EShapeType.LOOP, EControlStatementMode.FOR),
				new CShapeNode(EShapeType.CONDITION, EControlStatementMode.IF_ELSE),
				new CShapeNode(EShapeType.CONDITION, EControlStatementMode.IF_ONLY),
		};
		
		for (CShapeNode node : nodes) {
			root.insertNode(node);
			this.setCoords(root, Constants.FRAME_W / 5, 30);
		}
	}
	
	public class MouseHandler implements MouseInputListener, MouseWheelListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				
			}
			
			else if (e.getButton() == MouseEvent.BUTTON2) {
				
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

		@Override
		public void mouseDragged(MouseEvent e) {}

		@Override
		public void mouseMoved(MouseEvent e) {
			
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			System.out.println(e.getPoint().x + e.getPoint().y);
		}
	}
	
	public class KeyHandler implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if ((e.getKeyCode() == KeyEvent.VK_UP) && e.isControlDown())
				System.out.println("Two key pressed!");
		}

		@Override
		public void keyReleased(KeyEvent e) {}

		@Override
		public void keyTyped(KeyEvent e) {	
		}
		
	}
}
