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
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import converter.CConverter;

public class CodeViewer extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private JPanel cp;
    private RSyntaxTextArea textArea;
    private RTextScrollPane sp;
    private CConverter converter;
    private KeyHandler keyHandler;
    
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
        converter = new CConverter(textArea);
        
        keyHandler = new KeyHandler();
        this.textArea.addKeyListener(keyHandler);
        cp.add(sp);

        setContentPane(cp);
        pack();
	}
	
	public class KeyHandler implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			if ((e.getKeyCode() == KeyEvent.VK_V && e.isControlDown())) {
				Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(Toolkit.getDefaultToolkit().getSystemClipboard());
				if (contents != null) {
					try {
						String tmp = (String)contents.getTransferData(DataFlavor.stringFlavor);
						converter.setContents(tmp);
						converter.convert();
					} catch (Exception ex) {}
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {}

		@Override
		public void keyTyped(KeyEvent e) {
		//	if (e.getKeyChar() == '\n') {
		//		converter.setContents(((RSyntaxTextArea)e.getSource()).getText());
		//		converter.convert();
		//	}
		}
		
	}
}
