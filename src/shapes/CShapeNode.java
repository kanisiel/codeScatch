package shapes;

import Settings.Constants.EShapeType;

public class CShapeNode {
	private CShapeNode left;
	private CShapeNode right;
	private int shapeNo;
	private String shapeContent;
	private EShapeType shapeType;
	private boolean isDiamond;
	private int x;
	private int y;
	private int numberOfShape;
    
	
    public CShapeNode(int shapeNo, EShapeType shapeType, String shapeContent, boolean isDiamond){
        left = null;
        right = null;
        this.shapeNo = shapeNo;
        this.shapeType = shapeType;
        this.shapeContent = shapeContent;
        this.isDiamond = isDiamond;
        this.numberOfShape = 0;
    }
    
    public CShapeNode(EShapeType shapeType, String shapeContent, boolean isDiamond){
        left = null;
        right = null;
        this.shapeNo++;
        this.shapeType = shapeType;
        this.shapeContent = shapeContent;
        this.isDiamond = isDiamond;
        this.numberOfShape = 0;
    }
    
    public void setCoords(CShapeNode node, int x, int y){
        if (node != null){
            node.x = x;
            node.y = y;
               
	        if (node.isDiamond == false)
	        	this.setCoords(node.left,x,y+80);
	            
	        else {
	        	this.setCoords(node.left,x,y+110);
	        	this.setCoords(node.right,x+200,y+110);
	        }
        }
    }
    
    public boolean insertNode(CShapeNode node) {
    	if (this.right != null) {
    		right = node;
    		numberOfShape++;
    	}
    	
    	return true;
    }

    public void setX(int x) {	this.x = x;	}
    public void setY(int y) {	this.y = y;	}
    
	public CShapeNode getLeft() {	return left;	}
	public CShapeNode getRight() {	return right;	}
	public int getShapeNo() {	return shapeNo;	}
	public String getShapeContent() {	return shapeContent;	}
	public EShapeType getShapeType() {	return shapeType;	}
	public boolean getIsDiamond() {	return isDiamond;	}
	public int getX() {	return x;	}
	public int getY() {	return y;	}
}
