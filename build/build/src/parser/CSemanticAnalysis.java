package parser;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.Trees;


public class CSemanticAnalysis {
	private CParser parser;
	private String variableType = "";
	private String variableIds = "";
	private String functionReturnType = "";
	private String functionName = "";
	private String parameterList = "";
	private Boolean isTypedefNode = false;
	private Boolean assignmentFlag = false;
	private String flag = "";

	
	
	CSemanticAnalysis(CParser parser){
		this.parser = parser;
	}	
	
	public void ClearVariable(){
		this.variableType = "";
		this.variableIds = "";
		this.functionReturnType = "";
		this.functionName = "";
		this.parameterList = "";
		this.isTypedefNode = false;
		this.assignmentFlag = false;
		this.flag = "";
	}
	
	// analyze external declaration
	public void analyzeDCLR(ParseTree parseTree) {
		// TODO Auto-generated method stub
		String paren = "(";
		String declaration = "declaration";
		String functionDefinition = "functionDefinition";
		
		// function declaration
		if(Trees.getNodeText(parseTree, this.parser).equals(declaration) && parseTree.getText().contains(paren)){
			analyzeFunDCLR(parseTree);
			System.out.println("external declaration");
			funtest();
			ClearVariable();
		}
		// variable declaration
		else if(Trees.getNodeText(parseTree, this.parser).equals(declaration) && !(parseTree.getText().contains(paren))){
			searchTypedefNode(parseTree);
			if(isTypedefNode == false){
				System.out.println("external variable");
				analyzeVarDCLR(parseTree);
			}
			else{
				System.out.println("external variable");
				analyzeVarDCLRTypedef(parseTree);
			}
			vartest();
			ClearVariable();
		}
		else if(Trees.getNodeText(parseTree, this.parser).equals(functionDefinition)){
			String compoundStatement = "compoundStatement";
			System.out.println("function definition");
			// 0 is declarator
			analyzeFun(parseTree);
			funtest();
			ClearVariable();
			
			// 1 is compoundStatement
			for(int i = 0; i < parseTree.getChildCount(); ++i){
				if(Trees.getNodeText(parseTree.getChild(i), parser).equals(compoundStatement)){
					analyzeFunDef(parseTree);
				}
			}
		}
	}
	
	// analyze function definition
	public void analyzeFunDef(ParseTree parseTree){
		String compoundStatement = "compoundStatement";
		
		if(parseTree.getChildCount() > 0){
			for(int i = 0; i < parseTree.getChildCount(); ++i){
				if(Trees.getNodeText(parseTree, parser).equals(compoundStatement)){
					analyzeCMPNDStatement(parseTree);
					return ;
				}
				analyzeFunDef(parseTree.getChild(i));
			}
		}
		else{;}
	}
	
	public void analyzeCMPNDStatement(ParseTree parseTree){
		String declaration = "declaration";
		String statement = "statement";
		String paren = "(";
		
		if(parseTree.getChildCount() > 0){
			for(int i = 0; i < parseTree.getChildCount(); ++i){
				if(Trees.getNodeText(parseTree, this.parser).equals(declaration) && parseTree.getText().contains(paren)){
					System.out.println("paren");
					return ;
				}
				else if(Trees.getNodeText(parseTree, this.parser).equals(declaration) && !(parseTree.getText().contains(paren))){
					
					searchTypedefNode(parseTree);
					
					if(isTypedefNode == false){
						analyzeVarDCLR(parseTree);
						vartest();
						ClearVariable();
					}
					else{
						analyzeVarDCLRTypedef(parseTree);
						vartest();
						ClearVariable();
					}
					return ;
				}
				else if(Trees.getNodeText(parseTree, parser).equals(statement)){
					System.out.println("statement : " + parseTree.getText());
					return ;
				}
				analyzeCMPNDStatement(parseTree.getChild(i));
			}
		}
		else{;}
	}
	
	// variable declaration(type, initDeclaratorList), when don't have typedefName
	public void analyzeVarDCLR(ParseTree parseTree) {
		// TODO Auto-generated method stub
		String typeSpecifier = "typeSpecifier";
		String initDeclaratorList = "initDeclaratorList";
		
		if(parseTree.getChildCount() > 0){
			for(int i = 0; i < parseTree.getChildCount(); ++i){
				if(Trees.getNodeText(parseTree, this.parser).equals(typeSpecifier)){
					variableType = parseTree.getText();
					this.assignmentFlag = true;
					return ;
				}		
				else if(Trees.getNodeText(parseTree, this.parser).equals(initDeclaratorList)){
					getInitDeclarator(parseTree);
					return ;
				}
				analyzeVarDCLR(parseTree.getChild(i));
			}
		}
		else{;}
	}
	
	// variable declaration(type, id), when have typedefName
	public void analyzeVarDCLRTypedef(ParseTree parseTree) {
		// TODO Auto-generated method stub
		String typeSpecifier = "typeSpecifier";
		String typedefName = "typedefName";
		
		if(parseTree.getChildCount() > 0){
			for(int i = 0; i < parseTree.getChildCount(); ++i){
				if(Trees.getNodeText(parseTree, this.parser).equals(typedefName)){
					this.variableIds = parseTree.getText();
					this.assignmentFlag = true;
					return ;
				}
				else if(Trees.getNodeText(parseTree, this.parser).equals(typeSpecifier) &&
						this.assignmentFlag == false){
					variableType = parseTree.getText();
					this.assignmentFlag = true;
					return ;
				}		
				analyzeVarDCLRTypedef(parseTree.getChild(i));
			}
		}
		else{;}
	}
	
	// when variable have initDeclaratorList, analyze ids, don't have typedefName node
	public void getInitDeclarator(ParseTree parseTree){
		if(parseTree.getChildCount() > 0){
			for(int i = 0; i < parseTree.getChildCount(); ++i){
				getInitDeclarator(parseTree.getChild(i));
			}
		}
		else{
			this.variableIds += parseTree.getText() + " ";
		}
	}
	
	// search TypedefName node
	public void searchTypedefNode(ParseTree parseTree) {
		String typedefName = "typedefName";
		
		if(parseTree.getChildCount() > 0){
			for(int i = 0; i < parseTree.getChildCount(); ++i){
				if(!(Trees.getNodeText(parseTree, this.parser).equals(typedefName))){
					searchTypedefNode(parseTree.getChild(i));
				}
				else{
					this.isTypedefNode = true;
				}	
			}
		}
	}
	
	// function declaration return type, function name, parameterList
	public void analyzeFunDCLR(ParseTree parseTree) {
		// TODO Auto-generated method stub
		String typeSpecifier = "typeSpecifier";
		String directDeclarator = "directDeclarator";
		String parameterList = "parameterList";
		
		if(parseTree.getChildCount() > 0){
			for(int i = 0; i < parseTree.getChildCount(); ++i){
				if(Trees.getNodeText(parseTree, this.parser).equals(typeSpecifier)
						&& this.assignmentFlag == false){
					this.functionReturnType = parseTree.getText();
					this.assignmentFlag = true;
				}
				else if(Trees.getNodeText(parseTree, this.parser).equals(directDeclarator)){
					functionName = parseTree.getChild(0).getText();
				}			
				else if(Trees.getNodeText(parseTree, this.parser).equals(parameterList)){
					getParameterList(parseTree);
					return ;
				}
				analyzeFunDCLR(parseTree.getChild(i));
			}
		}
		else{;}
	}
	
	// get function parameterList
	public void getParameterList(ParseTree parseTree) {
		if(parseTree.getChildCount() > 0){
			for(int i = 0; i < parseTree.getChildCount(); ++i){
				getParameterList(parseTree.getChild(i));
			}
		}
		else{
			this.parameterList += parseTree.getText() + " ";
		}
	}
	
	//look for type, name, parameterList in function definition 
	public void analyzeFun(ParseTree parseTree){
		String declarationSpecifiers = "declarationSpecifiers";
		String declarator = "declarator";
		
		for(int i = 0; i < parseTree.getChildCount(); ++i){
			if(Trees.getNodeText(parseTree.getChild(i), parser).equals(declarationSpecifiers)){
				this.functionReturnType = parseTree.getChild(i).getText();
			}
			else if(Trees.getNodeText(parseTree.getChild(i), parser).equals(declarator)){
				this.functionName = parseTree.getChild(i).getChild(0).getChild(0).getText();
				// get parameterList
				analyzeFunDCLR(parseTree.getChild(i));
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
			;
		}
	}
	
	public void vartest(){
		System.out.println("Var analyze");
		System.out.println("type: " + this.variableType);
		System.out.println("ids: "+ this.variableIds);
	}
	
	public void funtest(){
		System.out.println("Fun analyze");
		System.out.println("type: " + this.functionReturnType);
		System.out.println("functionName: "+ this.functionName);
		System.out.println("parameterList: "+ this.parameterList);
	}
}
