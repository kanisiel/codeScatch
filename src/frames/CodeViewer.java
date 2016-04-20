/*
 * 
 * Internal Frame Class CodeViewer
 * 
 *  Using rsyntaxtextarea-2.5.7
 *  
 *  rsyntaxtextarea-2.5.7 is under a modified BSD license.
 *  
 *  Author : Lee Junsoo
 *  Last Modify : 2016/04/13
 */

package frames;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import panels.DesktopPane;

public class CodeViewer extends InternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private JPanel cp;
    private RSyntaxTextArea textArea;
    private RTextScrollPane sp;
    
	public CodeViewer(String title) {
        super(title); 
              
        
        cp = new JPanel(new BorderLayout());
        textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        textArea.setCodeFoldingEnabled(true);
        textArea.setFont(new Font("NanumGothic", Font.PLAIN, 9));
        sp = new RTextScrollPane(textArea);
        cp.add(sp);
        setContentPane(cp);
        pack();
	}
	
	@Override
	public void init(DesktopPane parent){
		this.parent = parent;
		this.setLocation(parent.getWidth()/2,0);
		this.setSize(parent.getWidth()/2, parent.getHeight());
		this.setVisible(true);
	}
	public RSyntaxTextArea getTextArea(){
		return this.textArea;
	}
	
	public Boolean setFont(String name, int style, int size){
		try{
			textArea.setFont(new Font(name, style, size));
		} catch (Exception e){
			return false;
		}
		return true;
	}
	public void setFonts(Font font){
		this.textArea.setFont(font);
		
	}
}
