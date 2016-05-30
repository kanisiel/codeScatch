package adapter;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import Settings.CConstants;
import Settings.Constants;
import parser.CLexer;
import parser.CParser;
import parser.CSemanticAnalysis;
import parser.CVisitor;
import parser.TreeData;
import parser.TreeNode;
import shapes.CShapeNode;

public class CodeToTree {
	private TreeToShape tts;
	private Timer timer;
	public CodeToTree(TreeToShape tts) {
		super();
		// TODO Auto-generated constructor stub
		this.tts = tts;
		this.timer = new Timer();
		
	}
	public void doParse(String buffer){
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				parse(buffer);
			}
		};
		timer.schedule(task, 10);
//		timer.schedule(parse(buffer), 1000);
	}
	public void parse(String buffer){
		
		ANTLRInputStream in = new ANTLRInputStream(buffer);
//		// input Stream into lexer
	    CLexer lexer = new CLexer(in);
	  
	    // tokenStream into parser
	    CommonTokenStream tokens = new CommonTokenStream(lexer);
	    CParser parser = new CParser(tokens);
	  
	    // make parseTree
	    ParseTree parseTree = parser.translationUnit();
	                
	    // get code structure  
	    CVisitor visitor = new CVisitor(parseTree, parser);
	    Vector<ParseTree> parseTrees = visitor.codeStructure(parseTree);
	    parseTree = visitor.getFunDefinition(parseTrees);
	    
	    CSemanticAnalysis semanticAnalysis = new CSemanticAnalysis(parser);   
	     
	    semanticAnalysis.parseCompound(parseTree);
//	    for(int i = 0; i < parseTree.getChildCount(); ++i){
//	    	  if(Trees.getNodeText(parseTree.getChild(i), parser).equals(CConstants.COMPOUNDSTATEMENT)){
//	    		  semanticAnalysis.setParseTree(parseTree.getChild(i));
//	    		  semanticAnalysis.analyzeFunc(parseTree.getChild(i));
//	    	  }
//	    }
	   
	    // getParent ����
	    TreeNode<TreeData> parent = semanticAnalysis.init();

	
	    //Prepare Canvas
	    tts.prepareCanvas(); 
	    //Split to type and body
	    
//	    semanticAnalysis.getLine(parent);
//	    for(int i = 0; i < parent.getChildList().size(); ++i){
//	    	TreeNode<TreeData> child = parent.getChildList().get(i);
//	    	System.out.println(child.getData().getNodeType());
//	    	if(!child.getData().getNodeType().equals(CConstants.CODE)){
//	    		for(int j = 0; j < child.getChildList().size(); j++){
//	    			System.out.println(child.getChildList().get(j).getData().getNodeType());
//	    		}
//	    	}
//	    }
	    
	    // make for full tree
	    semanticAnalysis.analyzeNode(parent);
	    
	    /*
	    for(int i = 0; i < parent.getChildList().size(); ++i){
	    	for(int j = 0; j < parent.getChildList().get(j).getChildList().size(); ++j)
	    		System.out.println(parent.getChildList().get(i).getChildList().get(j).getData().getNodeType());
	    }*/
	    
	    semanticAnalysis.getLine(parent.getChildList().get(0));
	    Vector<String> st = semanticAnalysis.getST();
	    CShapeNode shape;
	    for(int i = 0; i < parent.getChildList().size(); ++i){
    		Vector<String> sts = new Vector<>();
    		ParseTree tree = parent.getChildList().get(i).getData().getParseTree();
	    	if(parent.getChildList().get(i).getData().getNodeType().equals(CConstants.CODE)){
	    		sts.add(st.get(i));
	    		shape = tts.declareToShape(sts,Constants.CODE);
	    		
	    	} else if(semanticAnalysis.analyzeParseTreeKind(tree).equals(CConstants.IF)){
	    		String body = semanticAnalysis.getBody(parent.getChildList().get(i).getData().getParseTree());
	    		sts.add(body);
	    		String condition = semanticAnalysis.getCondition(parent.getChildList().get(i).getData().getParseTree());
	    		sts.add(condition);
	    		shape = tts.declareToShape(sts, Constants.IF);
	    		TreeData elses = parent.getChildList().get(i).getELSE();
	    		if(elses!=null){
	    			Vector<String> stelse = new Vector<>();
	    			String elsebody = semanticAnalysis.getElseBody(elses.getParseTree());
	    			stelse.add(elsebody);
	    			System.out.println(elsebody);
	    			shape = tts.declareToShape(stelse, Constants.ELSE);
	    		}
	    	} else if(semanticAnalysis.analyzeParseTreeKind(tree).equals(CConstants.WHILE)){
	    		String body = semanticAnalysis.getBody(parent.getChildList().get(i).getData().getParseTree());
	    		sts.add(body);
	    		String condition = semanticAnalysis.getCondition(parent.getChildList().get(i).getData().getParseTree());
	    		sts.add(condition);
	    		shape = tts.declareToShape(sts, Constants.WHILE);
	    	} else if (semanticAnalysis.analyzeParseTreeKind(tree).equals(CConstants.FOR)){
	    		String body = semanticAnalysis.getBody(parent.getChildList().get(i).getData().getParseTree());
	    		sts.add(body);
	    		String condition = semanticAnalysis.getCondition(parent.getChildList().get(i).getData().getParseTree());
	    		sts.add(condition);
	    		shape = tts.declareToShape(sts, Constants.FOR);
	    	} else if(semanticAnalysis.analyzeParseTreeKind(tree).equals(CConstants.ELSE)){
	    		String body = semanticAnalysis.getBody(parent.getChildList().get(i).getData().getParseTree());
	    		sts.add(body);
	    		String condition = semanticAnalysis.getCondition(parent.getChildList().get(i).getData().getParseTree());
	    		sts.add(condition);
	    		shape = tts.declareToShape(sts, Constants.ELSE);
	    	} else {
	    		shape = null;
	    	}
    		parent.getChildList().get(i).getData().setShapeNode(shape);
		}
		tts.drawRoot();
	}
	
}
