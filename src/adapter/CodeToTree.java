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
	   
	    // getParent ����
	    TreeNode<TreeData> parent = semanticAnalysis.init();
	    this.root = parent;

	
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
	    wholeCode = "";
		for(TreeNode<TreeData> node : this.root.getChildList()){
			if(node.getData().getNodeType().equals(CConstants.CODE)){
				int index = this.root.getChildList().indexOf(node);
				wholeCode += semanticAnalysis.getST().get(index);
			}else {
				wholeCode += node.getData().getParseTree().getText();
			}
		}
	    Vector<String> st = semanticAnalysis.getST();
	    CShapeNode shape;
	    Vector<Integer> linePointer = new Vector<>();
	    for(int i = 0; i < parent.getChildList().size(); ++i){
    		Vector<String> sts = new Vector<>();
    		ParseTree tree = parent.getChildList().get(i).getData().getParseTree();
	    	if(parent.getChildList().get(i).getData().getNodeType().equals(CConstants.CODE)){
	    		sts.add(st.get(i));
	    		shape = tts.declareToShape(sts,Constants.CODE, findLines(buffer, st.get(i), CConstants.CODE, findDupCode(st, st.get(i), i)));
	    		
	    	} else if(semanticAnalysis.analyzeParseTreeKind(tree).equals(CConstants.IF)){
	    		String body = semanticAnalysis.getBody(parent.getChildList().get(i).getData().getParseTree());
	    		sts.add(body);
	    		String condition = semanticAnalysis.getCondition(parent.getChildList().get(i).getData().getParseTree());
	    		sts.add(condition);
	    		shape = tts.declareToShape(sts, Constants.IF, findLines(buffer, parent.getChildList().get(i).getData().getParseTree().getText(), CConstants.IF, findDupOther(parent, parent.getChildList().get(i), i)));
	    		TreeData elses = parent.getChildList().get(i).getELSE();
	    		if(elses!=null){
	    			Vector<String> stelse = new Vector<>();
	    			String elsebody = semanticAnalysis.getElseBody(elses.getParseTree());
	    			stelse.add(elsebody);
	    			shape = tts.declareToShape(stelse, Constants.ELSE, findLines(buffer, "else{"+elsebody, CConstants.ELSE, findDupOther(parent, parent.getChildList().get(i), i)));
	    		}
	    	} else if(semanticAnalysis.analyzeParseTreeKind(tree).equals(CConstants.WHILE)){
	    		String body = semanticAnalysis.getBody(parent.getChildList().get(i).getData().getParseTree());
	    		sts.add(body);
	    		String condition = semanticAnalysis.getCondition(parent.getChildList().get(i).getData().getParseTree());
	    		sts.add(condition);
	    		shape = tts.declareToShape(sts, Constants.WHILE, findLines(buffer, parent.getChildList().get(i).getData().getParseTree().getText(), CConstants.WHILE, findDupOther(parent, parent.getChildList().get(i), i)));
	    	} else if (semanticAnalysis.analyzeParseTreeKind(tree).equals(CConstants.FOR)){
	    		String body = semanticAnalysis.getBody(parent.getChildList().get(i).getData().getParseTree());
	    		sts.add(body);
	    		String condition = semanticAnalysis.getCondition(parent.getChildList().get(i).getData().getParseTree());
	    		sts.add(condition);
	    		shape = tts.declareToShape(sts, Constants.FOR, findLines(buffer, parent.getChildList().get(i).getData().getParseTree().getText(), CConstants.FOR, findDupOther(parent, parent.getChildList().get(i), i)));
	    	} /*else if(semanticAnalysis.analyzeParseTreeKind(tree).equals(CConstants.ELSE)){
	    		String body = semanticAnalysis.getBody(parent.getChildList().get(i).getData().getParseTree());
	    		sts.add(body);
	    		String condition = semanticAnalysis.getCondition(parent.getChildList().get(i).getData().getParseTree());
	    		sts.add(condition);
	    		shape = tts.declareToShape(sts, Constants.ELSE);
	    	} */else {
	    		shape = null;
	    	}
    		parent.getChildList().get(i).getData().setShapeNode(shape);
		}
		tts.drawRoot();
	}
	public int[] findLines(String text, String context, String type, int dup){
		int rv[] = new int[2];
		if(type.equals(CConstants.CODE)){
			int firstSC = context.indexOf(";");
			String target = context.substring(0, firstSC);
			int start = text.replace(", ", ",").indexOf(target);
			String forward = text.substring(0, start);
			String forwards[] = forward.split(System.getProperty("line.separator"));
			int startLine = forwards.length-1;
			rv[0] = startLine;
			String contexts[] = context.split(";");
			String lastContext = contexts[(contexts.length-1)];
			int last = text.indexOf(lastContext);
			forward = text.substring(0, last);
			forwards = forward.split(System.getProperty("line.separator"));
			int lastLine = forwards.length;
			rv[1] = lastLine;
		}else {
			if(context.startsWith("if(")){
				int cbracet = context.indexOf("}")+1;
				context = context.replace("else{", "");
				context = context.substring(0, cbracet);
			}else if(context.startsWith("else{")){
				context += "}";
			}
			Vector<String> vs = new Vector<>();
			int bracet = context.indexOf("{");
			String value = context.replace("{", "{"+System.getProperty("line.separator"));
			String values[] = value.split(System.getProperty("line.separator"));
			vs.add(values[0]);
			for(int i = 1; i < values.length; i++){
				values[i] = values[i].replaceAll(";", ";"+System.getProperty("line.separator"));
				String values2[] = values[i].split(System.getProperty("line.separator"));
				for(int j = 0; j < values2.length; j++){
					vs.add(values2[j]);
				}
			}
//			for(String s : vs){
//				System.out.println(s);
//			}

			int flag = 0;
			int startLine = 0;
			for(int i = 1; i < texts.length-1; i++){
				if(texts[i].startsWith("}")){
					String target = texts[i].replace("}", "");
					if(target.trim().equals(vs.get(0))){
						if(checkSame(vs, i)){
							flag++;
						}
					}
					if(flag == dup){
						startLine = i;
						break;
					}
				}else {
					if(texts[i].trim().equals(vs.get(0))){
						if(checkSame(vs, i)){
							flag++;
						}
					}
					if(flag == dup){
						startLine = i;
						break;
					}
				}
			}
			rv[0] = startLine;
			rv[1] = startLine + vs.size();
//			System.out.println(rv[0]);
//			System.out.println(rv[1]);
		}
		
		return rv;
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
}
