package shapes;

import java.io.Serializable;

import Settings.Constants.EControlStatementMode;
import Settings.Constants.EShapeType;

public class CShapeNode implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private CShapeNode left;
	private CShapeNode right;
	private int shapeNo;
	private String shapeContent;
	private EShapeType shapeType;
	private EControlStatementMode mode;
	private boolean isDiamond;
	private int x;
	private int y;
	private CShapeNode previousNode;
	private static int incShapeNo = 0;
    
	public CShapeNode(EShapeType shapeType, String shapeContent){
        left = null;
        right = null;
        this.shapeNo = ++incShapeNo;
        this.shapeType = shapeType;
        this.shapeContent = shapeContent;
        this.setIsDiamond();
        previousNode = null;
    }
	
	public CShapeNode(int shapeNo, EShapeType shapeType, String shapeContent){
        this(shapeType, shapeContent);
        this.shapeNo = shapeNo;
    }
	
	public CShapeNode(EShapeType shapeType, EControlStatementMode mode){
        this(shapeType, "");
        this.mode = mode;
    }
        
    private void setIsDiamond() {
    	isDiamond = (this.shapeType.equals(EShapeType.CONDITION) || this.shapeType.equals(EShapeType.LOOP)) ? true : false;    	
    }
    
    public boolean insertNode(int attachTo, CShapeNode node) {
    	if (attachTo == 0 && this.left == null) { 
    		previousNode = this;
    		this.left = node;
    	}
    	
    	else if (attachTo == 1) 
    		this.insertNode(node);
    	
    	return true;
    }
    
    public boolean insertNode(CShapeNode node) {
    	if (previousNode != null) {
    		System.out.println("Number of node: " + shapeNo);
    		System.out.println("PrevNode isDiamond: " + previousNode.isDiamond);
    	}
    	if (this.right == null) {
    		previousNode = this;
    		System.out.println(previousNode.getShapeType() + "(" + previousNode.getShapeNo() + ")");
    		this.right = node;
    		return true;
    	}
    	
    	return this.right.insertNode(node);
    }
    
    public CShapeNode findNode(CShapeNode node, int shapeNo){ 
        if (node != null){ 
            if(node.shapeNo == shapeNo) return node; 
             
            else { 
            	CShapeNode temp = null;
            	
                if (node.left != null)	temp = findNode(node.left, shapeNo);
                
                if (temp == null && node.right != null)	temp = findNode(node.right, shapeNo);
                
                return temp; 
            } 
        }
        
        return null; 
    }
    
   public void printNode(CShapeNode node) {
    	if (node != null) {
    		System.out.print(node.getShapeType().name() + "[" + node.shapeNo + "] -> ");
    		printNode(node.left);
    		printNode(node.right);
    	}
    }

    public void setX(int x) {	this.x = x;	}
    public void setY(int y) {	this.y = y;	}
    
	public CShapeNode getLeft() {	return left;	}
	public CShapeNode getRight() {	return right;	}
	public CShapeNode getPreviousNode() {	return previousNode;	}
	public int getShapeNo() {	return shapeNo;	}
	public String getShapeContent() {	return shapeContent;	}
	public EShapeType getShapeType() {	return shapeType;	}
	public EControlStatementMode getMode() {	return mode;	}
	public boolean getIsDiamond() {	return isDiamond;	}
	public int getX() {	return x;	}
	public int getY() {	return y;	}
}
