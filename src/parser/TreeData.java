package parser;

import java.util.Vector;

import org.antlr.v4.runtime.tree.ParseTree;

import shapes.CShapeNode;


public class TreeData {
	private ParseTree parseTree;
	private Vector<ParseTree> codeVector;
	private String nodeType;
    private String kind; 
    private String ifCondition;
    private String iterationCondition;
	
	public String getIterationCondition() {return iterationCondition;}
	public void setIterationCondition(String iterationCondition) {this.iterationCondition = iterationCondition;}
	public String getIfCondition() {return ifCondition;}
	public void setIfCondition(String ifCondition) {this.ifCondition = ifCondition;}
	private CShapeNode shapeNode;
    
	public ParseTree getParseTree() {return parseTree;}
	public void setParseTree(ParseTree parseTree) {this.parseTree = parseTree;}
	public Vector<ParseTree> getCodeVector() {return codeVector;}
	public void setCodeVector(Vector<ParseTree> parseTreeVector) {this.codeVector = parseTreeVector;}
	public String getNodeType() {return nodeType;}
	public void setNodeType(String nodeType) {this.nodeType = nodeType;}
	public String getKind() {return kind;}
	public void setKind(String kind) {this.kind = kind;}



    public TreeData(ParseTree parseTree, String nodeType, String kind){
    	this.parseTree = parseTree;
    	this.nodeType = nodeType;
    	this.kind = kind;
    	this.codeVector = new Vector<ParseTree>();
    	this.codeVector.add(this.parseTree);
    }
	public CShapeNode getShapeNode() {
		return shapeNode;
	}
	public void setShapeNode(CShapeNode shapeNode) {
		this.shapeNode = shapeNode;
	}
    
}
