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
	public TreeNode<TreeData> getParent() {return this.parent;}
	
	public CSemanticAnalysis(CParser parser){
		this.parser = parser;
		this.parseTrees = new Vector<ParseTree>();
		this.parent = new TreeNode<TreeData>(null);
		TreeData treeData = new TreeData(null, "root", "root");
		this.parent = new TreeNode<TreeData>(treeData);
		this.st = new Vector<>();
		this.line = "";
	}
	
	public TreeNode<TreeData> init(){
		// make tree
		makeTree(parseTrees, this.parent);
		// merge code
		for(int i = 0; i < isNeedMerge(this.parent); i++){
			mergeCode(this.parent);
		}
		return parent;
		
	}
	
	public void analyzeFunc(ParseTree parseTree) {
		
		if(parseTree.getChildCount() > 0){
			for(int i = 0; i < parseTree.getChildCount(); ++i){
				if(Trees.getNodeText(parseTree.getChild(i), parser).equals(CConstants.BLOCKITEM)){
					parseTrees.add(parseTree.getChild(i));
					return ;
				}	
				analyzeFunc(parseTree.getChild(i));
			}
		}
		else{;}
	}
	
	// iteration or selection
	public void analyzeNode(TreeNode<TreeData> parent){
		for(int i = 0; i < parent.getChildList().size(); ++i){		
			if(parent.getChildList().get(i).getData().getNodeType().equals(CConstants.ITERATIONSTATEMENT)){
				analyzeIterationStatement(parent.getChildList().get(i).getData().getParseTree(), parent.getChildList().get(i));
			}
			if(parent.getChildList().get(i).getData().getNodeType().equals(CConstants.SELECTIONSTATEMENT)){
				analyzeSelectionStatement(parent.getChildList().get(i).getData().getParseTree(), parent.getChildList().get(i));
			}
		}
	}
	
	public void analyzeIterationStatement(ParseTree parseTree, TreeNode<TreeData> parent){
		//parseTree nodename is blockItem in here
		//System.out.println("in parent : " +  parent.getData().getKind());
		//System.out.println(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(4), parser));
		for(int i = 0; i < parseTree.getChild(0).getChild(0).getChildCount(); ++i){
			if( Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(i), parser)
					.equals(CConstants.STATEMENT)){
//				System.out.println("here");
//				System.out.println(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(i).getChild(0), parser));
				if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(i).getChild(0), parser)
						.equals(CConstants.COMPOUNDSTATEMENT)){
					this.parseTrees.clear();
					analyzeCompoundStatement(parseTree.getChild(0).getChild(0).getChild(i).getChild(0));
					makeTree(this.parseTrees, parent);
//					System.out.println(this.parent.getChildList().get(1).getChildList().get(0).getData().getNodeType());
//					System.out.println(this.parent.getChildList().get(1).getChildList().get(1).getData().getNodeType());
//					System.out.println(this.parent.getChildList().get(1).getChildList().get(2).getData().getNodeType());
					for(int k = 0; k < isNeedMerge(parent); k++){
						mergeCode(parent);
					}
//					System.out.println(this.parent.getChildList().get(0).getChildList().get(0).getData().getNodeType());
//					System.out.println(this.parent.getChildList().get(1).getChildList().get(0).getData().getNodeType());
				}
				else{
					System.out.println("here");
					//System.out.println(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(i).getChild(0), parser));
					if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(i).getChild(0), parser)
							.equals(CConstants.ITERATIONSTATEMENT)){
						parent.addChild(new TreeData(parseTree.getChild(0).getChild(0).getChild(i).getChild(0), CConstants.ITERATIONSTATEMENT, 
								parseTree.getChild(0).getChild(0).getChild(i).getChild(0).getChild(0).getText()));
						//System.out.println(this.parent.getChildList().get(1).getChildList().get(0).getData().getNodeType());
						
					}
					else if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(i).getChild(0).getChild(0), parser)
							.equals(CConstants.SELECTIONSTATEMENT)){
						parent.addChild(new TreeData(parseTree.getChild(0).getChild(0).getChild(i).getChild(0), CConstants.SELECTIONSTATEMENT, 
								parseTree.getChild(0).getChild(0).getChild(i).getChild(0).getChild(0).getText()));
					}
					else{
						parent.addChild(new TreeData(parseTree.getChild(0).getChild(0).getChild(i).getChild(0), CConstants.CODE, 
								parseTree.getChild(0).getChild(0).getChild(i).getChild(0).getChild(0).getText()));
					}
				}		
			}
		}
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
	
	
	public void analyzeSelectionStatement(ParseTree parseTree, TreeNode<TreeData> parent){
		//parseTree nodename is blockItem in here
		//System.out.println("in parent : " + parent.getData().getKind());
		if(parseTree.getChild(0).getChild(0).getChild(0).getText().equals(CConstants.IF)){
			boolean  isElse = lookForElse(parseTree.getChild(0).getChild(0));
			
			if(isElse == false){
				for(int i = 0; i < parseTree.getChild(0).getChild(0).getChildCount(); ++i){
					if( Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(i), parser)
							.equals(CConstants.STATEMENT)){
						if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(i).getChild(0), parser)
								.equals(CConstants.COMPOUNDSTATEMENT)){
							this.parseTrees.clear();
							analyzeCompoundStatement(parseTree.getChild(0).getChild(0).getChild(i).getChild(0));
							makeTree(this.parseTrees, parent);
							for(int k = 0; k < isNeedMerge(parent); k++){
								mergeCode(parent);
							}
//							System.out.println(this.parent.getChildList().get(1).getChildList().get(0).getData().getNodeType());
//							System.out.println(this.parent.getChildList().get(1).getChildList().get(1).getData().getNodeType());

						}
						else{
							//System.out.println(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(i).getChild(0), parser));
							if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(i).getChild(0), parser)
									.equals(CConstants.ITERATIONSTATEMENT)){
								parent.addChild(new TreeData(parseTree.getChild(0).getChild(0).getChild(i).getChild(0), CConstants.ITERATIONSTATEMENT, 
										parseTree.getChild(0).getChild(0).getChild(i).getChild(0).getChild(0).getText()));
							}
							else if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(i).getChild(0).getChild(0), parser)
									.equals(CConstants.SELECTIONSTATEMENT)){
								parent.addChild(new TreeData(parseTree.getChild(0).getChild(0).getChild(i).getChild(0), CConstants.SELECTIONSTATEMENT, 
										parseTree.getChild(0).getChild(0).getChild(i).getChild(0).getChild(0).getText()));
							}
							else{
								parent.addChild(new TreeData(parseTree.getChild(0).getChild(0).getChild(i).getChild(0), CConstants.CODE, 
										parseTree.getChild(0).getChild(0).getChild(i).getChild(0).getChild(0).getText()));
							}
						}
					}
				}
			}

			// is else
			else{
				// else if, compoundStatement
				if( Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(4), parser)
						.equals(CConstants.STATEMENT) && 
						Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(6).getChild(0), parser)
						.equals(CConstants.SELECTIONSTATEMENT)){
					if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(4).getChild(0), parser)
							.equals(CConstants.COMPOUNDSTATEMENT)){
						this.parseTrees.clear();
						analyzeCompoundStatement(parseTree.getChild(0).getChild(0).getChild(4).getChild(0));
						makeTree(this.parseTrees, parent);
						for(int k = 0; k < isNeedMerge(parent); k++){
							mergeCode(parent);
						}
						// add node else if
						makeTree(parseTree.getChild(0).getChild(0).getChild(6).getChild(0), parent,CConstants.SELECTIONSTATEMENT, CConstants.ELSEIF);
//						System.out.println(parent.getChildList().size());
//						System.out.println(parent.getChildList().get(0).getData().getNodeType());
//						System.out.println(parent.getChildList().get(1).getData().getNodeType());
					}
					// else if, statement
					else{
						//System.out.println(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(i).getChild(0), parser));
						if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(4).getChild(0), parser)
								.equals(CConstants.ITERATIONSTATEMENT)){
							parent.addChild(new TreeData(parseTree.getChild(0).getChild(0).getChild(4).getChild(0), CConstants.ITERATIONSTATEMENT, 
									parseTree.getChild(0).getChild(0).getChild(4).getChild(0).getChild(0).getText()));
							makeTree(parseTree.getChild(0).getChild(0).getChild(6).getChild(0), parent,CConstants.SELECTIONSTATEMENT, CConstants.ELSEIF);
						}
						else if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(4).getChild(0).getChild(0), parser)
								.equals(CConstants.SELECTIONSTATEMENT)){
							parent.addChild(new TreeData(parseTree.getChild(0).getChild(0).getChild(4).getChild(0), CConstants.SELECTIONSTATEMENT, 
									parseTree.getChild(0).getChild(0).getChild(4).getChild(0).getChild(0).getText()));
							makeTree(parseTree.getChild(0).getChild(0).getChild(6).getChild(0), parent,CConstants.SELECTIONSTATEMENT, CConstants.ELSEIF);
						}
						else{
							parent.addChild(new TreeData(parseTree.getChild(0).getChild(0).getChild(4).getChild(0), CConstants.CODE, 
									parseTree.getChild(0).getChild(0).getChild(4).getChild(0).getChild(0).getText()));
							makeTree(parseTree.getChild(0).getChild(0).getChild(6).getChild(0), parent,CConstants.SELECTIONSTATEMENT, CConstants.ELSEIF);
//							System.out.println(parent.getChildList().size());
//							System.out.println(parent.getChildList().get(0).getData().getParseTree().getText());
//							System.out.println(parent.getChildList().get(0).getData().getNodeType());
//							System.out.println(parent.getChildList().get(1).getData().getNodeType());
						}
					}
				}
				// ������� �ٽ� 
				// else
				else{
					// if-else, both compoundStatement
					if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(4).getChild(0), parser)
							.equals(CConstants.COMPOUNDSTATEMENT) && Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(6).getChild(0), parser)
							.equals(CConstants.COMPOUNDSTATEMENT)){
						this.parseTrees.clear();			
						analyzeCompoundStatement(parseTree.getChild(0).getChild(0).getChild(4).getChild(0));
						makeTree(this.parseTrees, parent);
						for(int k = 0; k < isNeedMerge(parent); k++){
							mergeCode(parent);
						}
						parent.setELSE(new TreeData(parseTree.getChild(0).getChild(0).getChild(6).getChild(0),
								CConstants.SELECTIONSTATEMENT, CConstants.ELSE));
//						System.out.println("else:" + parent.getELSE().getKind());
//						System.out.println(parent.getParent().getChildList().size());
//						System.out.println(parent.getChildList().get(0).getData().getParseTree().getText());						
					}
					
					// if - compoundStatement, else statement
					else if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(4).getChild(0), parser)
							.equals(CConstants.COMPOUNDSTATEMENT) && !(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(6).getChild(0), parser)
							.equals(CConstants.COMPOUNDSTATEMENT))){
						this.parseTrees.clear();			
						analyzeCompoundStatement(parseTree.getChild(0).getChild(0).getChild(4).getChild(0));
						makeTree(this.parseTrees, parent);
						for(int k = 0; k < isNeedMerge(parent); k++){
							mergeCode(parent);
						}
						parent.setELSE(new TreeData(parseTree.getChild(0).getChild(0).getChild(6).getChild(0),
								CConstants.SELECTIONSTATEMENT, CConstants.ELSE));
//						System.out.println("else:" + parent.getELSE().getKind());
//						System.out.println(parent.getParent().getChildList().size());
//						System.out.println(parent.getChildList().get(0).getData().getParseTree().getText());						
					}
					
					// if-else, if statement, else compound
					else if(!(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(4).getChild(0), parser)
							.equals(CConstants.COMPOUNDSTATEMENT)) && Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(6).getChild(0), parser)
							.equals(CConstants.COMPOUNDSTATEMENT)){
						if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(4).getChild(0), parser)
								.equals(CConstants.ITERATIONSTATEMENT)){
							parent.addChild(new TreeData(parseTree.getChild(0).getChild(0).getChild(4).getChild(0), CConstants.ITERATIONSTATEMENT, 
									parseTree.getChild(0).getChild(0).getChild(4).getChild(0).getChild(0).getText()));
							parent.setELSE(new TreeData(parseTree.getChild(0).getChild(0).getChild(6).getChild(0),
									CConstants.SELECTIONSTATEMENT, CConstants.ELSE));
						}
						else if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(4).getChild(0).getChild(0), parser)
								.equals(CConstants.SELECTIONSTATEMENT)){
							parent.addChild(new TreeData(parseTree.getChild(0).getChild(0).getChild(4).getChild(0), CConstants.SELECTIONSTATEMENT, 
									parseTree.getChild(0).getChild(0).getChild(4).getChild(0).getChild(0).getText()));
							parent.setELSE(new TreeData(parseTree.getChild(0).getChild(0).getChild(6).getChild(0),
									CConstants.SELECTIONSTATEMENT, CConstants.ELSE));
						}
						else{
							parent.addChild(new TreeData(parseTree.getChild(0).getChild(0).getChild(4).getChild(0), CConstants.CODE, 
									parseTree.getChild(0).getChild(0).getChild(4).getChild(0).getChild(0).getText()));
							parent.setELSE(new TreeData(parseTree.getChild(0).getChild(0).getChild(6).getChild(0),
									CConstants.SELECTIONSTATEMENT, CConstants.ELSE));
//							System.out.println(parent.getChildList().size());
//							System.out.println(parent.getChildList().get(0).getData().getParseTree().getText());
//							System.out.println("else:" + parent.getELSE().getKind());						
						}
					}
					
					//  if-else, if statement, else statement
					else if(!(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(4).getChild(0), parser)
							.equals(CConstants.COMPOUNDSTATEMENT)) && !(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(6).getChild(0), parser)
							.equals(CConstants.COMPOUNDSTATEMENT))){
						if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(4).getChild(0), parser)
								.equals(CConstants.ITERATIONSTATEMENT)){
							parent.addChild(new TreeData(parseTree.getChild(0).getChild(0).getChild(4).getChild(0), CConstants.ITERATIONSTATEMENT, 
									parseTree.getChild(0).getChild(0).getChild(4).getChild(0).getChild(0).getText()));
							parent.setELSE(new TreeData(parseTree.getChild(0).getChild(0).getChild(6).getChild(0),
									CConstants.SELECTIONSTATEMENT, CConstants.ELSE));
						}
						else if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(4).getChild(0).getChild(0), parser)
								.equals(CConstants.SELECTIONSTATEMENT)){
							parent.addChild(new TreeData(parseTree.getChild(0).getChild(0).getChild(4).getChild(0), CConstants.SELECTIONSTATEMENT, 
									parseTree.getChild(0).getChild(0).getChild(4).getChild(0).getChild(0).getText()));
							parent.setELSE(new TreeData(parseTree.getChild(0).getChild(0).getChild(6).getChild(0),
									CConstants.SELECTIONSTATEMENT, CConstants.ELSE));
						}
						else{
							parent.addChild(new TreeData(parseTree.getChild(0).getChild(0).getChild(4).getChild(0), CConstants.CODE, 
									parseTree.getChild(0).getChild(0).getChild(4).getChild(0).getChild(0).getText()));
							parent.setELSE(new TreeData(parseTree.getChild(0).getChild(0).getChild(6).getChild(0),
									CConstants.SELECTIONSTATEMENT, CConstants.ELSE));
//							System.out.println(parent.getChildList().size());
//							System.out.println(parent.getChildList().get(0).getData().getParseTree().getText());
//							System.out.println("else:" + parent.getELSE().getKind());						
						}
					}
				}
			}
		}
		
		//switch
		else if(parseTree.getChild(0).getChild(0).getChild(0).getText().equals(CConstants.SWITCH)){
			
		}

	}
	
	//input parseTree.getChild(0).getChild(0), look for else in if_parsetree
	public boolean lookForElse(ParseTree parseTree){
		boolean isElse = false;
		
		for(int i = 0; ++i < parseTree.getChildCount(); ++i){
			if(Trees.getNodeText(parseTree.getChild(i), parser).equals(CConstants.ELSE)){
				isElse = true;
			}
		}
		
		return isElse;
	}
	
	
	
	
	public Vector<String> getST(){
		return st;
	}
	public void getLine(TreeNode<TreeData> parents){		
		visitChild(parents);
	}
	
	public void visitChild(TreeNode<TreeData> parents) {
		for(int i = 0; i < parent.getChildList().size(); ++i){
			line = "";
			if(parent.getChildList().get(i).getData().getNodeType().equals("code")){
				for(int j = 0; j < parent.getChildList().get(i).getData().getCodeVector().size(); ++j){
					visitChildren(parent.getChildList().get(i).getData().getCodeVector().get(j));					
				}
				st.add(line);
			}
			else{
				st.add(parent.getChildList().get(i).getData().getParseTree().getText());
			}
		}
	}
	public String getCondition(ParseTree parseTree){//TreeNode<TreeData> parents){
//		ParseTree parseTree = parents.getData().getParseTree();
		String buffer = "";
		int s = 0, e = 0;
		String open = "(", close = ")";
		if(parseTree.getChildCount()>1){
			for(int i = 1; i< parseTree.getChildCount(); i++){
				if(parseTree.getChild(i).getText().equals(open)){
					s = i+1;
				}else if(parseTree.getChild(i).getText().equals(close)){
					e = i;
				}
			}
			for(int i = s; i < e; i++){
				buffer += parseTree.getChild(i).getText();
			}
			return buffer;
		} else {
			return getCondition(parseTree.getChild(0));//parents.getChildList().get(0));
		}
	}
	public String getBody(ParseTree parseTree){//TreeNode<TreeData> parents){
//		ParseTree parseTree = parents.getData().getParseTree();
		int flag = 0;
		String open = "{";
		if(parseTree.getChildCount()>1){
			for(int i = 1; i< parseTree.getChildCount(); i++){
				if(parseTree.getChild(i).getText().contains(open)){
					flag = i;
				}
			}
//			System.out.println(flag);
			String buffer = parseTree.getChild(flag).getText();
			buffer = buffer.replace("{", "");
			buffer = buffer.replace("}", "");
			return buffer;
		} else {
			return getBody(parseTree.getChild(0));//parents.getChildList().get(0));
		}
	}
	public String getElseBody(ParseTree parseTree){//TreeNode<TreeData> parents){
		String buffer = parseTree.getText();
		buffer = buffer.replace("{", "");
		buffer = buffer.replace("}", "");
		return buffer;

	}
	public void visitChildren(ParseTree parseTree) {
		if(parseTree.getChildCount() > 0){
			for(int i = 0; i < parseTree.getChildCount(); ++i){
				visitChildren(parseTree.getChild(i));
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
			else if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(0), parser).equals(CConstants.ELSE)){
				kind = CConstants.ELSE;
				return kind;
			}
			else if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(0), parser).equals(CConstants.SWITCH)){
				kind = CConstants.SWITCH;
				return kind;
			}
		}
		else if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(0), parser).equals(CConstants.JUMPSTATEMENT)){
			if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(0), parser).equals(CConstants.RETURN)){
				kind = CConstants.RETURN;
				return kind;
			}
			if(Trees.getNodeText(parseTree.getChild(0).getChild(0).getChild(0), parser).equals(CConstants.BREAK)){
				kind = CConstants.BREAK;
				return kind;
			}
		}
		else{
			kind = "none";
			return kind;
		}
		return kind;
	}
	
	public void makeTree(Vector<ParseTree> parseTrees, TreeNode<TreeData> parent){
		for(int i = 0; i < parseTrees.size(); ++i){
			parent.addChild(new TreeData(parseTrees.get(i), analyzeParseTreeType(parseTrees.get(i)), 
					analyzeParseTreeKind(parseTrees.get(i))));
		}
	}
	
	public void makeTree(ParseTree parseTree, TreeNode<TreeData> parent, String type, String elseFlag){
		parent.addChild(new TreeData(parseTree, type, elseFlag));
	}
	
	public void mergeCode(TreeNode<TreeData> parent){
		if(parent.getChildList().size() < 2){
			return ;
		}
		Boolean flag = false;
		Vector<Integer> codeIndex = new Vector<>();
		for(int index = 0; index < parent.getChildList().size(); ++index){
			if(parent.getChildList().get(index).getData().getNodeType().equals(CConstants.CODE)){
				if(index+1 <  parent.getChildList().size()){
					if(!flag){
						if(parent.getChildList().get(index+1).getData().getNodeType().equals(CConstants.CODE)){
							flag = true;
						}
					}
				}
				if(flag){
					codeIndex.add(index);
				}
			}
			else{
				if(flag){
					break;
				}
			}
			
		}
		if(codeIndex.size()>0){
			merge(parent, codeIndex.firstElement(), codeIndex.lastElement());
		}
	}
	
	public int isNeedMerge(TreeNode<TreeData> parent){
		if(parent.getChildList().size() < 2){
			return 0;
		}
		Boolean flag = false;
		int need = 0;
		for(int index = 0; index < parent.getChildList().size()-1; ++index){
			if(parent.getChildList().get(index).getData().getNodeType().equals(CConstants.CODE)){
				if(!flag){
					flag = true;
					need++;
				}		
			}
			else{
				flag=false;
			}
		}
		//System.out.println(need);
		return need;
	}
	public void merge(TreeNode<TreeData> parent, int start, int end){
		for(int index = start+1; index <= end ; ++index){
			parent.getChildList().get(start).getData().getCodeVector().add(parent.getChildList().get(index).getData().getParseTree());
		}
		
		for(int index = end; index >= start+1 ; index--){
			parent.getChildList().remove(index);
		}
		
	}

	public void setParent(TreeNode<TreeData> parent) {this.parent = parent;}
	
	public void parseCompound(ParseTree parseTree){
		for(int i = 0; i < parseTree.getChildCount(); ++i){
	    	  if(Trees.getNodeText(parseTree.getChild(i), parser).equals(CConstants.COMPOUNDSTATEMENT)){
	    		  setParseTree(parseTree.getChild(i));
	    		  analyzeFunc(parseTree.getChild(i));
	    	  }
	    }
	}
	public void analyzeFunc(ParseTree parseTree, Vector<ParseTree> parseTrees) {
		
		if(parseTree.getChildCount() > 0){
			for(int i = 0; i < parseTree.getChildCount(); ++i){
				if(Trees.getNodeText(parseTree.getChild(i), parser).equals(CConstants.BLOCKITEM)){
					parseTrees.add(parseTree.getChild(i));
					return ;
				}	
				analyzeFunc(parseTree.getChild(i));
			}
		}
		else{;}
	}
	public ParseTree passthroughTree(ParseTree parseTree, String flag){
		if(flag.equals(CConstants.ROOT)){
			if(parseTree.getChildCount()>1){
				return parseTree;
			} else if(parseTree.getChildCount()==1){
				return passthroughTree(parseTree.getChild(0),CConstants.ROOT);
			} else {
				return null;
			}
		} else if(flag.equals(CConstants.END)){
			if(parseTree.getChildCount()==1){
				return passthroughTree(parseTree.getChild(0),CConstants.ROOT);
			} else if (parseTree.getChildCount() == 0){
				return parseTree;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	public Vector<ParseTree> getParseTrees() {
		return parseTrees;
	}
}



