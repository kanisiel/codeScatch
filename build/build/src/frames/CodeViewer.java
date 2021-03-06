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
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rtextarea.RTextScrollPane;

import Settings.Preference;
import Settings.Windows;

public class CodeViewer extends InternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private JPanel cp;
    private RSyntaxTextArea textArea;
    private RTextScrollPane sp;
    
	public CodeViewer() {
        super(Windows.InternalWindows.Code.getTitle()); 
              
        
        cp = new JPanel(new BorderLayout());
        textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        textArea.setCodeFoldingEnabled(true);
        textArea.setFont(Preference.defaultFont);
        sp = new RTextScrollPane(textArea);
        cp.add(sp);
        setContentPane(cp);
        pack();
        this.setVisible(true);
	}
	
//	@Override
//	public void init(DesktopPane parent){
//		this.parent = parent;
//		this.setLocation(parent.getWidth()/2,0);
//		this.setSize(parent.getWidth()/2, parent.getHeight());
//		this.setVisible(true);
//	}
	public RSyntaxTextArea getTextArea(){
		return this.textArea;
	}
	
	public void setFont(RSyntaxTextArea textArea, Font font) {
      if (font != null) {
         SyntaxScheme ss = textArea.getSyntaxScheme();
         ss = (SyntaxScheme) ss.clone();
         for (int i = 0; i < ss.getStyleCount(); i++) {
            if (ss.getStyle(i) != null) {
               ss.getStyle(i).font = font;
            }
         }
         textArea.setSyntaxScheme(ss);
         textArea.setFont(font);
         textArea.revalidate();
      }
   }
}
