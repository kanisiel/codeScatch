package listener;

import java.util.List;
import java.util.Vector;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.Trees;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import adapter.TreeToShape;
import parser.CLexer;
import parser.CParser;
import parser.CSemanticAnalysis;
import parser.CVisitor;
import parser.TreeData;
import parser.TreeNode;

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
			      
			    //semanticAnalysis.test();
//			    semanticAnalysis.makeTree(parseTrees);
			    System.out.println(semanticAnalysis.getParent().getChildList());
			    List<TreeNode<TreeData>> tnl = semanticAnalysis.getParent().getChildList();
			    for(TreeNode<TreeData> tn : tnl){
//			    	System.out.println();
			    	
			    	semanticAnalysis.visitChildren(tn.getData().getParseTree());
			    }
				tts.declareToShape(buffer);
			}
		} else {
			buffer = source.getText();
		}
	}




	public String getBuffer() {
		return buffer;
	}




	public void setBuffer(String buffer) {
		this.buffer = buffer;
	}
	
}

