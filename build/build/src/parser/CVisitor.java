package parser;
import java.util.Vector;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.Trees;

import Settings.CConstants;

public class CVisitor implements ParseTreeVisitor<ParseTree>{
	private Vector<ParseTree> parseTrees = new Vector<ParseTree>();
	private ParseTree parseTree;
	private CParser parser;
	public CVisitor(ParseTree parseTree, CParser parser) {
		// TODO Auto-generated constructor stub
		this.parseTree = parseTree;
		this.parser = parser;
	}
	
	// analyze codeStructure
	public Vector<ParseTree> codeStructure(ParseTree parseTree) {
		// TODO Auto-generated method stub
		TransUnitGoChildNode(parseTree);
		return parseTrees; 
	}
	
	public ParseTree getFunDefinition(Vector<ParseTree> parseTrees){
		for(int i = 0; i < parseTrees.size(); ++i){
	    	  if(Trees.getNodeText(parseTrees.get(i).getChild(0), parser).equals(CConstants.FUNCTIONDEFINITION)){
	    		  return parseTrees.get(i).getChild(0);
	    	  }
	    }
		return null;
	}
	
	public void TransUnitGoChildNode(ParseTree parseTree){
		String externalDeclaration = "externalDeclaration";
		//first 160, TransUnit
		if(!(Trees.getNodeText(parseTree, this.parser).equals(externalDeclaration)) &&
				parseTree.getChildCount() > 0){
			for(int j = 0; j < parseTree.getChildCount(); ++j){
				TransUnitGoChildNode(parseTree.getChild(j));
			}
		}
		else if(Trees.getNodeText(parseTree, this.parser).equals(externalDeclaration)){
			parseTrees.add(parseTree);
		}
	}
	
	// print parseTree contents
	public void parseTreeToString(ParseTree parseTree) {
		visitChildren(parseTree);
	}
	
	//visit parseTree nodes
	public void visitChildren(ParseTree parseTree) {
		if(parseTree.getChildCount() > 0){
			for(int i = 0; i < parseTree.getChildCount(); ++i){
				visitChildren(parseTree.getChild(i));
			}
		}
		else{
			System.out.println(parseTree.getText());
		}
	}
	
	public void parseTreeToString(ParseTree parseTree, CParser parser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ParseTree visitChildren(RuleNode arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParseTree visitErrorNode(ErrorNode arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParseTree visitTerminal(TerminalNode arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParseTree visit(ParseTree parsetree) {
		// TODO Auto-generated method stub
		return null;
	}


}
