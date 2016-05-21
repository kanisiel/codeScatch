package panels;

import javax.swing.JPanel;

public class FlowChartCanvas extends JPanel {
//	private static final long serialVersionUID = 1L;
//	private CShapeNode root;
//	private CShapeManager shapeManager;
//	private Image img;
//	private BufferedImage bi;
//	final int windowWidth = (Constants.FRAME_W-76)/2;
//	final int windowHeight = Constants.FRAME_H-30;
//	
//	public FlowChartPane() {
//		this.setSize(windowWidth, windowHeight);
//		this.setOpaque(false);
//		this.setBackground(new Color(0,0,0,0));
//	}
//	
//	public FlowChartPane(BufferedImage bi) {
//		this.bi = bi;
//		this.setSize(windowWidth, windowHeight);
//		this.setOpaque(false);
//		this.setBackground(new Color(0,0,0,0));
//	}
//	public void init(){
//		Graphics2D g2d = this.bi.createGraphics();
//		root = new CShapeNode(0, EShapeType.START, "START", false);
//		this.setCoords(root, this.getWidth()/2, 40);
//		shapeManager = new COvalManager(EShapeType.START);
//		g2d.drawOval(windowWidth/2 - 25, 30, 50, 50);
//		shapeManager.draw(g2d, root);
//		this.paint(g2d);
//		//this.getGraphics().drawOval(windowWidth/2 -25, 30, 50, 50);
////        this.paint(bi.getGraphics());
////        System.out.println(this.getGraphics());
//        //this.getGraphics().drawOval(windowWidth/2 -25, 30, 50, 50);
//	}
//	
//	public void setShapeManager(CShapeManager shapeManager) {	
//		this.shapeManager = shapeManager;
//	}
//
//	public void setCoords(CShapeNode node, int x, int y){
//        if (node != null){
//            node.setX(x);
//            node.setY(y);
//               
//	        if (!node.getIsDiamond())
//	        	this.setCoords(node.getRight(), x, y + 80);
//	            
//	        else {
//	        	this.setCoords(node.getLeft(), x , y + 110);
//	        	this.setCoords(node.getRight(), x + 200 , y + 110);
//	        }
//        }
//    }
//	
//	public CShapeNode getRootNode() {	return root;	}
//	
//	public void paint(Graphics g) {
//		super.paint(g);
//		this.drawNode(g, root);
//	}
//	
//	public void drawNode(Graphics g, CShapeNode node) {
//		Graphics2D g2d = (Graphics2D) g;
//		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		
//		if (node != null) {
//			if (node.getShapeType() == EShapeType.START)
//				shapeManager = new COvalManager(node.getShapeType());
//			/*
//			else 
//				shapeManager = EToolBarButton.valueOf(node.getShapeType().name()).getShape();
//			*/
//			else if (node.getShapeType() == EShapeType.PROCESS)
//				// draw rectangle
//				shapeManager = new CRectangleManager(node.getShapeType());
//			
//			else if (node.getShapeType() == EShapeType.IO)
//				// draw parallogram
//				shapeManager = new CParallelogramManager(node.getShapeType());
//			
//			else if (node.getShapeType() == EShapeType.CONDITION)
//				shapeManager = new CDiamondManager(node.getShapeType());
//			
//			else if (node.getShapeType() == EShapeType.LOOP)
//				// draw as CONDITION but one of the arrows is toward upward
//				shapeManager = new CDiamondManager(node.getShapeType());
//			
//			else if (node.getShapeType() == EShapeType.FUNCTION) {
//				// ... complex ... it may be an add on button...
//			}
//			
//			else if (node.getShapeType() == EShapeType.STOP)
//				shapeManager = new COvalManager(node.getShapeType());
//			
//			shapeManager.draw(g2d, node);
//								
//			if (!node.getIsDiamond())
//				this.drawNode(g, node.getLeft());
//			
//			else {
//				this.drawNode(g, node.getLeft());
//				this.drawNode(g, node.getRight());
//			}
//		}
//	}
//	
//
//	  public void paintComponent(Graphics g) {
//	    g.drawImage(img, 0, 0, null);
//	  }
//
//	public Image getImg() {
//		return img;
//	}
//
//	public void setImg(Image img) {
//		this.img = img;
//	}
}
