package listener;

import java.util.Vector;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.Trees;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import Settings.CConstants;
import Settings.Constants;
import adapter.TreeToShape;
import parser.CLexer;
import parser.CParser;
import parser.CSemanticAnalysis;
import parser.CVisitor;
import parser.TreeData;
import parser.TreeNode;
import shapes.CShapeNode;

public class CodeViewerListener implements CaretListener {
	public String buffer;
	private ANTLRInputStream in;
	private CLexer lexer;
	private CommonTokenStream tokens;
	private CParser parser;
	private ParseTree tree;
	//private ParserFunction functions;
	private TreeToShape tts;
	
	public CodeViewerListener(TreeToShape tts) {
		this.tts = tts;
	}
	@Override
	public void caretUpdate(CaretEvent e) {
		// TODO Auto-generated method stub
		RSyntaxTextArea source = (RSyntaxTextArea)e.getSource();
		if(source.getText().length()>1){
			if(!buffer.equals(source.getText())){
				buffer = source.getText();
				ANTLRInputStream in = new ANTLRInputStream(buffer);
//				// input Stream into lexer
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
			      
			    for(int i = 0; i < parseTree.getChildCount(); ++i){
			    	if(Trees.getNodeText(parseTree.getChild(i), parser).equals("compoundStatement")){
			    		semanticAnalysis.setParseTree(parseTree.getChild(i));
			    	    semanticAnalysis.analyzeCompoundStatement(parseTree.getChild(i));
			    	}
			    }
			    
//			    for(int j = 0; j < parent.getChildList().get(i).getData().getCodeVector().size(); ++j){
//					System.out.println(parent.getChildList().get(i).getData().getCodeVector().get(j).getText());	
//				}
//			    semanticAnalysis.test();
			    //Prepare Canvas
			    tts.prepareCanvas(); 
			    //Split to type and body
			    TreeNode<TreeData> parent = semanticAnalysis.getParent();
//			    semanticAnalysis.getLine(parent);
			    
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
			    	} else {
			    		shape = null;
			    	}
		    		parent.getChildList().get(i).getData().setShapeNode(shape);
				}
//			    semanticAnalysis.makeTree(parseTrees);
//			    System.out.println(semanticAnalysis.getParent().getChildList());
//			    List<TreeNode<TreeData>> tnl = semanticAnalysis.getParent().getChildList();
//			    Vector<String> lines = new Vector<>();
//			    for(TreeNode<TreeData> tn : tnl){
//			    		lines.add(semanticAnalysis.getLine(tn.getData().getParseTree()));
//			    }
			    //java.util.MissingFormatArgumentException
				tts.drawRoot();//tts.drawToCanvas();
			}
		} else {
			buffer = source.getText();
		}
	}

	public void showParent(TreeNode<TreeData> parent){
		for(int i = 0; i < parent.getChildList().size(); i++){
			System.out.println(parent.getChildList().get(i).getData().getShapeNode());
		}
	}


	public String getBuffer() {
		return buffer;
	}




	public void setBuffer(String buffer) {
		this.buffer = buffer;
	}
	
}

