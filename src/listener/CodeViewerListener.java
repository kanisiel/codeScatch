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
		buffer = "";
	}
	@Override
	public void caretUpdate(CaretEvent e) {
		// TODO Auto-generated method stub
		RSyntaxTextArea source = (RSyntaxTextArea)e.getSource();
		if(source.getText().length() > 1) {
			if(!buffer.equals(source.getText())){
				buffer = source.getText();
				if (buffer.startsWith("#"))
					buffer = buffer.substring(buffer.indexOf('\n'));
//				System.out.println(buffer);
				
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

