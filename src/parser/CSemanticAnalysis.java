package parser;
import java.util.Vector;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.Trees;

import Settings.CConstants;


public class CSemanticAnalysis {
	private CParser parser;
	private ParseTree rootParseTree;
	private Vector<ParseTree> parseTrees;
	private TreeNode<TreeData> parent;
	private String line;
	public Vector<String> st;

	
	public void setParseTree(ParseTree parseTree) {this.rootParseTree = parseTree;}

	
	public CSemanticAnalysis(CParser parser){
		this.parser = parser;
		this.parseTrees = new Vector<ParseTree>();
		this.parent = new TreeNode<TreeData>(null);
		this.st = new Vector<>();
		this.line = "";
	}
	

	// analyze external declaration
	public void analyzeCompoundStatement(ParseTree parseTree) {
		
		if(parseTree.getChildCount() > 0){
			for(int i = 0; i < parseTree.getChildCount(); ++i){
				if(Trees.getNodeText(parseTree.getChild(i), parser).equals(CConstants.BLOCKITEM)){
					parseTrees.add(parseTree.getChild(i));
					return ;
				}	
				analyzeCompoundStatement(parseTree.getChild(i));
			}
		}
		else{;}
	}
	
	public void test(){
		// make tree
		
			makeTree(parseTrees);
		// merge code
			mergeCode(this.parent);
		
		
		
		for(int i = 0; i < parseTrees.size(); ++i){
			System.out.println(parseTrees.get(i).getText());
		}
		System.out.println("");
		
		// tree print
		for(int i = 0; i < parent.getChildList().size(); ++i){
			System.out.println(parent.getChildList().get(i).getData().getNodeType());
		}

		// print tree after merge
		System.out.println("\nmerge after!!!");
		for(int i = 0; i < parent.getChildList().size(); ++i){
			System.out.println(parent.getChildList().get(i).getData().getNodeType());
		}
		System.out.println("");
		
		//print sourcecode
		for(int i = 0; i < parent.getChildList().size(); ++i){
			if(parent.getChildList().get(i).getData().getNodeType().equals("code")){
				for(int j = 0; j < parent.getChildList().get(i).getData().getCodeVector().size(); ++j){
					System.out.println(parent.getChildList().get(i).getData().getCodeVector().get(j).getText());	
				}
			}
			else{
				System.out.println(parent.getChildList().get(i).getData().getParseTree().getText());
			}
		}
	}
	public Vector<String> getST(){
		return st;
	}
	public String getLine(ParseTree parseTree){
		line = "";
		visitChild(parseTree);
		return line;
	}
	
	public void visitChild(ParseTree parseTree) {
		if(parseTree.getChildCount() > 0){
			for(int i = 0; i < parseTree.getChildCount(); ++i){
				
				visitChild(parseTree.getChild(i));

			}
		}
		else{
			if(Trees.getNodeText(parseTree.getParent(), parser).equals("typeSpecifier")){
				line += parseTree.getText() + " ";
			} else {
				line += parseTree.getText();
			}
		}
	}
	
	public void visitChildren(ParseTree parseTree) {
		if(parseTree.getChildCount() > 0){
			for(int i = 0; i < parseTree.getChildCount(); ++i){
				visitChildren(parseTree.getChild(i));
			}
		}
		else{
			if(Trees.getNodeText(parseTree.getParent(), parser).equals("typeSpecifier")){
				parseTree.getText().replace("\"", "\\\"");
				parseTree.getText().replace("%", "\\%");
				System.out.print(parseTree.getText() + " ");
			} else {
				System.out.print(parseTree.getText()+ " ");
			}
		}
	}
	
	public String analyzeParseTreeType(ParseTree parseTree){
		String type = "";
		
		if(Trees.getNodeText(parseTree.getChild(0).getChild(0), parser).equals(CConstants.ITERATIONSTATEMENT)){
			type = CConstants.ITERATIONSTATEMENT;
			return type;
		}
		else if(Trees.getNodeText(parseTree.getChild(0).getChild(0), parser).equals(CConstants.SELECTIONSTATEMENT)){
			type = CConstants.SELECTIONSTATEMENT;
			return type;
		}
		else if(Trees.getNodeText(parseTree.getChild(0).getChild(0), parser).equals(CConstants.JUMPSTATEMENT)){
			type = CConstants.JUMPSTATEMENT;
			return type;
		}
		else{
			type = CConstants.CODE;
			return type;
		}
	}
	
	public String analyzeParseTreeKind(ParseTree parseTree){
		String kind = "";
		
		if(Trees.getNodeText(parseTree.getChild(0).getChild(0), parser).equals(CConstants.ITERATIONSTATEMENT)){
			if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(0), parser).equals(CConstants.FOR)){
				kind = CConstants.FOR;
			}
			else if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(0), parser).equals(CConstants.WHILE)){
				kind = CConstants.WHILE;
			}
			else if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(0), parser).equals(CConstants.DO)){
				kind = CConstants.DO;
			}
			return kind;
		}
		else if(Trees.getNodeText(parseTree.getChild(0).getChild(0), parser).equals(CConstants.SELECTIONSTATEMENT)){
			if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(0), parser).equals(CConstants.IF)){
				kind = CConstants.IF;
				return kind;
			}
			else if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(0), parser).equals(CConstants.SWITCH)){
				kind = CConstants.SWITCH;
				return kind;
			}
		}
		else if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(0), parser).equals(CConstants.JUMPSTATEMENT)){
			kind = CConstants.RETURN;
			return kind;
		}
		else{
			kind = "none";
			return kind;
		}
		return kind;
	}
	
	public void makeTree(Vector<ParseTree> parseTrees){
		String root = "root";
		
		TreeData treeData = new TreeData(null, root, root);
		this.parent = new TreeNode<TreeData>(treeData);
		
		for(int i = 0; i < parseTrees.size(); ++i){
			parent.addChild(new TreeData(parseTrees.get(i), analyzeParseTreeType(parseTrees.get(i)), 
					analyzeParseTreeKind(parseTrees.get(i))));
		}
	}
	
	public void mergeCode(TreeNode<TreeData> parent){
		if(parent.getChildList().size() < 2){
			return ;
		}
		
		for(int index = 0; index < parent.getChildList().size()-1; ++index){
			if(parent.getChildList().get(index).getData().getNodeType().equals(CConstants.CODE)){
				if(parent.getChildList().get(index+1).getData().getNodeType().equals(CConstants.CODE)){		
					parent.getChildList().get(index).getData().getCodeVector().add(parent.getChildList().get(index+1).getData().getParseTree());
					parent.getChildList().remove(index+1);
				}
			}
			else{
				;
			}
		}
	}


	public TreeNode<TreeData> getParent() {
		// make tree
		
			makeTree(parseTrees);
		// merge code
//			mergeCode(this.parent);
		return parent;
	}


	public void setParent(TreeNode<TreeData> parent) {
		this.parent = parent;
	}

}



