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
	private TreeNode<TreeData> root;
	private CSemanticAnalysis semanticAnalysis;
	private String wholeCode;
	private String texts[];
	private String code;
	private Vector<Integer> codeBlocks;
	
	public CodeToTree(TreeToShape tts) {
		super();
		// TODO Auto-generated constructor stub
		this.tts = tts;
		this.timer = new Timer();
		this.codeBlocks = new Vector<>();
		this.codeBlocks.add(0);
		
	}
	public void doParse(String buffer){
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				code = buffer;
				parse(buffer);
			}
		};
		timer.schedule(task, 20);
	}
	public void parse(String buffer){
		
		texts = buffer.split(System.getProperty("line.separator"));
		for(int i = 1; i < texts.length-1;i++){
			texts[i] = texts[i].trim();
			texts[i] = texts[i].replaceAll(" ", "");
		}
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
	    
	    semanticAnalysis = new CSemanticAnalysis(parser);   
	     
	    semanticAnalysis.parseCompound(parseTree);
//	    for(int i = 0; i < parseTree.getChildCount(); ++i){
//	    	  if(Trees.getNodeText(parseTree.getChild(i), parser).equals(CConstants.COMPOUNDSTATEMENT)){
//	    		  semanticAnalysis.setParseTree(parseTree.getChild(i));
//	    		  semanticAnalysis.analyzeFunc(parseTree.getChild(i));
//	    	  }
//	    }
	   
	    // getParent
	    TreeNode<TreeData> parent = semanticAnalysis.init();
	    
	    // make child of child node
	    semanticAnalysis.analyzeNode(parent);
	    // function structure
//	    System.out.println("------------------------------------------------------");
//	    for(int i = 0; i < parent.getChildList().size(); ++i){
//	    	System.out.println(parent.getChildList().get(i).getData().getNodeType());
//	    }
//	    System.out.println("------------------------------------------------------");
	    this.root = parent;
	    semanticAnalysis.visitChilds(parent);
//	    visitChildren(parent);
	    
	    
//	    System.out.println(parent.getChildList().get(1).getData().getIfCondition());
//	    for(int i = 0; i < parent.getChildList().get(1).getELSEIF().size(); ++i){
//	    	System.out.println(parent.getChildList().get(1).getELSEIF().get(i).getIfCondition());
//	    }
	    //System.out.println(parent.getChildList().get(1).getELSE().getParseTree().getText());
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
	    
	    
	    /*
	    for(int i = 0; i < parent.getChildList().size(); ++i){
	    	for(int j = 0; j < parent.getChildList().get(j).getChildList().size(); ++j)
	    		System.out.println(parent.getChildList().get(i).getChildList().get(j).getData().getNodeType());
	    }*/
	    
	    semanticAnalysis.getLine(parent.getChildList().get(0));
	    wholeCode = "";
		for(TreeNode<TreeData> node : this.root.getChildList()){
			if(node.getData().getNodeType().equals(CConstants.CODE)){
				int index = this.root.getChildList().indexOf(node);
				wholeCode += semanticAnalysis.getST().get(index);
			}else {
				wholeCode += node.getData().getParseTree().getText();
			}
		}
		declare(parent, buffer);
		tts.drawRoot();
	}
	private void declare(TreeNode<TreeData> parent, String buffer){
		
		Vector<String> st = semanticAnalysis.getST();
	    CShapeNode shape, parentNode = null;
	    Vector<Integer> linePointer = new Vector<>();
	    for(int i = 0; i < parent.getChildList().size(); ++i){
	    	TreeNode<TreeData> node = parent.getChildList().get(i);
    		ParseTree tree = node.getData().getParseTree();
    		if(parent.getData().getNodeType().equals("root")){
    			parentNode = tts.getRootNode();
	    	} else {
	    		parentNode = node.getParent().getData().getShapeNode();
	    	}
//    		System.out.println(node.getData().getNodeType());
//    		System.out.println(node.getParent().getData().getNodeType());
//    		System.out.println(node.getParent().getData().getShapeNode());
//    		System.out.println();
	    	if(node.getData().getNodeType().equals(CConstants.CODE)){
	    		shape = tts.declareToShape(node.getData().getBody(),Constants.CODE, parentNode, findLines(buffer, st.get(i), CConstants.CODE, findDupCode(st, st.get(i), i)));
	    	} else if(node.getData().getKind().equals(CConstants.IF)){
	    		shape = tts.declareToShape(node.getData().getIfCondition(), Constants.IF, parentNode, findLines(buffer, node.getData().getParseTree().getText(), CConstants.IF, findDupOther(parent, node, i)));
	    	} else if(node.getData().getKind().equals(CConstants.ELSEIF)){
	    		shape = tts.declareToShape(node.getData().getIfCondition(), Constants.ELSEIF, parentNode, findLines(buffer, node.getData().getParseTree().getText(), CConstants.ELSEIF, findDupOther(parent, node, i)));
	    	} else if(node.getData().getKind().equals(CConstants.WHILE)){
	    		shape = tts.declareToShape(node.getData().getIterationCondition(), Constants.WHILE, parentNode, findLines(buffer, node.getData().getParseTree().getText(), CConstants.WHILE, findDupOther(parent, node, i)));
	    	} else if(node.getData().getKind().equals(CConstants.DO)){
	    		shape = tts.declareToShape(node.getData().getIterationCondition(), Constants.DO, parentNode, findLines(buffer, node.getData().getParseTree().getText(), CConstants.DO, findDupOther(parent, node, i)));
	    	} else if (node.getData().getKind().equals(CConstants.FOR)){
	    		shape = tts.declareToShape(node.getData().getIterationCondition(), Constants.FOR, parentNode, findLines(buffer, node.getData().getParseTree().getText(), CConstants.FOR, findDupOther(parent, node, i)));
//	    	} else if(!node.getELSE().equals(null)){
//	    		shape = tts.declareToShape("", Constants.ELSE, parentNode, checkLine(node.getData().getParseTree().getText(), CConstants.ELSE));
	    	} else {
	    		shape = null;
	    	}
    		node.getData().setShapeNode(shape);
//    		if(shape.shape==null){
//    			;
//    		}else {
//	    		System.out.println(shape.shape.sid);
//	    		System.out.println();
//    		}
	    	if(node.getChildList().size()>0){
    			declare(node, buffer);
    		}
	    	
		}
	}
	public void visitChildren(TreeNode<TreeData> parent) {
		if(parent.getChildList().size() > 0){
			for(int i = 0; i < parent.getChildList().size(); ++i){
				System.out.println("parent: "+parent.getData().getNodeType());
				System.out.println(i+"/"+parent.getChildList().size());
				System.out.println(parent.getChildList().get(i).getData().getNodeType());
				System.out.println(parent.getChildList().get(i).getData().getKind());
				if(parent.getChildList().get(i).getData().getKind().contains("if")){
					System.out.println(parent.getChildList().get(i).getData().getIfCondition());
				}
				System.out.println(parent.getChildList().get(i).getData().getBody());//getParseTree().getText());
				System.out.println();
				visitChildren(parent.getChildList().get(i));
			}
		}
		else{
			;
		}
	}
	
	public int[] findLines(String text, String context, String type, int dup){
		
		return checkLine(context, type);
	}
	private Boolean checkSame(Vector<String> vs, int start){
		Boolean rv = false;
		String value = "";
		String target = "";
		for(int i = 0; i < vs.size()-1; i++){
//			if(texts[i+start].trim().equals(vs.get(i))){
//				rv = true;
//			}else {
//				rv = false;
//			}
			value += vs.get(i);
			target += texts[i+start].replaceAll("}", "");
		}
		value += "}";
		target += "}";
		if(value.equals(target)){
			rv = true;
		}
		
		return rv;
	}
	private int duplicate(String target){
		String buffer[] = texts;
		String tb = target.replace(" ", "");
		Boolean flag = false;
		int rv = 0;

		for(int index = 0; index < buffer.length; index++){
			String s = buffer[index];
			s = s.replace(" ", "");
			if(tb.contains(s)){
				if(!flag){
					flag = true;
					rv++;
				}
			} else{
				if(flag){
					flag = false;
					break;
				}
			}
		}
		return rv;
	}
	private int findDupCode(Vector<String> st, String targetbody, int max){
		int rv = 0;
		for(int i = 0; i <= max; i++){
			String body = st.get(i);
    		
    		if(targetbody.equals(body)){
    			rv++;
    		}
		}
		return rv;
	}
	private int findDupOther(TreeNode<TreeData> parent, TreeNode<TreeData> node, int max){
		int rv = 0;
		String targetBody = semanticAnalysis.getBody(node.getData().getParseTree());
		String targetCondition = semanticAnalysis.getCondition(node.getData().getParseTree());
		for(int i = 0; i <= max; ++i){
			String body = semanticAnalysis.getBody(parent.getChildList().get(i).getData().getParseTree());
    		String condition = semanticAnalysis.getCondition(parent.getChildList().get(i).getData().getParseTree());
    		
    		if(targetBody.equals(body)&&targetCondition.equals(condition)){
    			rv++;
    		}
		}
		return rv;
	}
	
	public int[] checkLine(String target, String type){
		int rv[] = new int[2];
		String buffer[] = code.split(System.getProperty("line.separator"));
		String tb = target.replace(" ", "");
		Boolean flag = false;
		int start = 0;
		int end = 0;
		for(int index = codeBlocks.lastElement(); index < buffer.length; index++){
			String s = buffer[index];
			s = s.replace(" ", "").trim();
			if(!s.equals("}")){
				if(tb.contains(s)&&!s.equals("")){
					if(!flag){
						flag = true;
						start = index;
					}
				} else{
	//				System.out.println("target: "+tb);
	//				System.out.println("base: "+s);
					if(flag){
						end = index-1;
						break;
					}
				}
			} else {
				if(flag){
					end = index;
					break;
				}
			}
			if(index==buffer.length-1){
				if(flag){
					end = index-1;
					break;
				}
			}
		}
		if(end==buffer.length-1){
			end--;
		}
		rv[0] = start;
		rv[1] = end;
		codeBlocks.add(end);
//		System.out.println(target);
//		System.out.println(code);
//		System.out.println();
//		String ss[] = code.split(System.getProperty("line.separator"));
//		for(int i = 0; i < ss.length; i++){
//			System.out.println(ss[i]);
//		}
//		System.out.println();
		return rv;
	}
}
