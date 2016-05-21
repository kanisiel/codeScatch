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

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

public class CodeViewer extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private JPanel cp;
    private RSyntaxTextArea textArea;
    private RTextScrollPane sp;
    
	public CodeViewer(String title) {
        super(title, 
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable
        
        cp = new JPanel(new BorderLayout());
        textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        sp = new RTextScrollPane(textArea);
        cp.add(sp);

        setContentPane(cp);
        pack();
	}
	
	public RSyntaxTextArea getTextArea() {	return this.textArea;	}
}
