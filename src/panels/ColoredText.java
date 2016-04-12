package panels;

import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

public class ColoredText {
	private JTextComponent component;
	
	public ColoredText(JTextComponent component) {
		this.component = component;
	}
	
	public void setColor(){
		int length = this.component.getDocument().getLength();
		try {
			String document = this.component.getDocument().getText(0, length);
			int index = document.indexOf("#include");
			
			
			
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
}
