package converter;

import java.util.StringTokenizer;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import Settings.Constants.ERectDrawType;
import Settings.Constants.EShapeType;
import panels.FlowChartPane;
import shapes.CShapeNode;

public class CConverter {
	private FlowChartPane flowChartPane;
	private RSyntaxTextArea textArea;
	private CShapeNode shapeNode;
	private String contents;
	
	public CConverter(FlowChartPane flowChartPane) {	this.flowChartPane = flowChartPane;	}
	public CConverter(RSyntaxTextArea textArea) {	
		this.textArea = textArea;
		contents = "";
	}
	
	public void setContents(String contents) {	this.contents = contents;	}
	public void setContents(char c) {	this.contents += c;	}
	
	public RSyntaxTextArea getTextArea() {		return textArea;	}
	public String getContents() {	return contents + "\n";	}
	
	public void convert() {
		StringTokenizer st = new StringTokenizer(contents, "\n");
		while(st.hasMoreTokens()) {
			String tmp = st.nextToken();
			
			if (tmp.contains("main("))
				System.out.println(tmp + " -> " + EShapeType.START.name());
			else if (!(tmp.contains("if") || tmp.contains("while") || tmp.contains("for") || 
					tmp.contains("switch") || tmp.contains("return") || tmp.contains("case") || 
					tmp.contains("break") || tmp.contains("continue"))) {
				System.out.println(tmp + " -> " + ERectDrawType.CODE.name().toUpperCase());
				//flowChartPane.drawNode(flowChartPane.getGraphics(), new CShapeNode(EShapeType.PROCESS, ERectDrawType.CODE.getName()));
			}
			
			else if (tmp.contains("#include")) continue;
			
			else if (tmp.contains("}") && tmp.length() == 1) System.out.println(tmp + " -> " + EShapeType.END.name()); 
				
			else System.out.println(tmp + " -> " + tmp.substring(0, tmp.indexOf(' ')).toUpperCase());//flowChartPane.drawNode(flowChartPane.getGraphics(), new CShapeNode(EShapeType.PROCESS, tmp.substring(0, tmp.indexOf(' ')).toUpperCase()));
		}
	}
	
}
