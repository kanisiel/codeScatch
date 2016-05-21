package listener;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import adapter.TreeToShape;
import parser.CLexer;
import parser.CParser;
import parser.ParserFunction;

public class CodeViewerListener implements CaretListener {
	public String buffer;
	private ANTLRInputStream in;
	private CLexer lexer;
	private CommonTokenStream token;
	private CParser parser;
	private ParseTree tree;
	private ParserFunction functions;
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
//				in = new ANTLRInputStream(buffer);
//				lexer = new CLexer(in);
//				token = new CommonTokenStream(lexer);
//				parser = new CParser(token);
//				tree = parser.translationUnit();
//				functions = new ParserFunction();
//				functions.toTokenString(tree);
//				functions.printTokens();
//				System.out.println(source.getText());
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

