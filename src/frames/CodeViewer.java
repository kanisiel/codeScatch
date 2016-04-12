/*
 * 
 * Internal Frame Class CodeViewer
 * 
 * Class Variable :
 * 	- textPane(JTextPane) : text pane for text editor
 *  - scrollPane(JScrollPane) : scrollable Pane for text pane
 *  - textLineNumber(TextLineNumber) : panel for line numbering
 *  - linePainter(LinePainter) : Highlighter for coloring current line
 *  - option(String) : For regular expression using by coloring reserved, variable name, string words
 * 
 * Method :
 * 	- Getter and Setter : For Class Variable
 *  - Constructor : For Initialize Class Variable at Construct
 *  - hex2Rgb : return Color object by Hexical Color code String
 *  - findLastNonWordChar : find non alphanumeric start by Last index
 *  - findFirstNonWordChar : find non alphanumeric start by First index
 *  
 *  Author : Lee Junsoo
 *  Last Modify : 2016/04/12
 */

package frames;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import Settings.Windows;
import panels.LinePainter;
import panels.TextLineNumber;

public class CodeViewer extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private JTextPane textPane;
    private JScrollPane scrollPane;
    private TextLineNumber textLineNumber;
    private LinePainter linePainter;
    private String option = "(\\W)*(";
    
	public CodeViewer(String title) {
        super(title, 
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable
 
        //...Create the GUI and put it in the window...
 
        //...Then set the window size or call pack...
        setSize(300,300);
 
        //Set the window's location.
        setLocation(0, 0);
        
        //set Styled text Attribute
        StyleContext cont = StyleContext.getDefaultStyleContext();
	    AttributeSet attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, hex2Rgb(Windows.reservedWordColor)); //for Reserved word
	    AttributeSet attrInclude = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, hex2Rgb(Windows.includeColor)); //for Reserved word
	    AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
	    
	    //concatenate reserved word to regular expression
        for(String word : Windows.reservedWord){
        	if(option.equals("(\\W)*(")){
        		option = option.concat(word);
        	} else{
        		option = option.concat("|"+word);
        	}
        }
        option = option.concat(")");
	    //set DefaultStyledDocument
	    DefaultStyledDocument doc = new DefaultStyledDocument() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
	            super.insertString(offset, str, a);

	            String text = getText(0, getLength());
	            int before = findLastNonWordChar(text, offset);
	            if (before < 0) before = 0;
	            int after = findFirstNonWordChar(text, offset + str.length());
	            int wordL = before;
	            int wordR = before;
	            
	     
	            while (wordR <= after) {
	                if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
	                    if (text.substring(wordL, wordR).matches(option)) {
	                        setCharacterAttributes(wordL, wordR - wordL, attr, false);
	                    }
	                    else if (text.substring(wordL, wordR).matches(Windows.include)){
	                    	setCharacterAttributes(wordL, wordR - wordL, attrInclude, false);
	                    }
	                    else
	                        setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
	                    wordL = wordR;
	                }
	                wordR++;
	            }
	        }

	        public void remove (int offs, int len) throws BadLocationException {
	            super.remove(offs, len);
	            
	            String text = getText(0, getLength());
	            int before = findLastNonWordChar(text, offs);
	            if (before < 0) before = 0;
	            int after = findFirstNonWordChar(text, offs);

	            if (text.substring(before, after).matches(option)) {
	                setCharacterAttributes(before, after - before, attr, false);
	            } else {
	                setCharacterAttributes(before, after - before, attrBlack, false);
	            }
	        }
	    };
	
        
        //set Attributes
		this.getContentPane().setLayout(new BorderLayout());
		this.textPane = new JTextPane(doc);
		this.add(textPane);
		this.scrollPane = new JScrollPane(textPane);
		this.textLineNumber = new TextLineNumber(textPane);
		this.add(textLineNumber);
		this.scrollPane.setRowHeaderView( textLineNumber );
		this.add(scrollPane);
		this.setLinePainter(new LinePainter(textPane, hex2Rgb(Windows.highLightColor)));
    }
	public static Color hex2Rgb(String colorStr) {
	    return new Color(
	            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
	            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
	            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
	}
	
	//find by last
	private int findLastNonWordChar (String text, int index) {
		while (--index >= 0) {
			if (String.valueOf(text.charAt(index)).matches("\\W")) {
				break;
			}
		}
		return index;
	}
	
	//find by first
	private int findFirstNonWordChar (String text, int index) {
		while (index < text.length()) {
			if (String.valueOf(text.charAt(index)).matches("\\W")) {
				break;
			}
			index++;
		}
		return index;
	}
	public LinePainter getLinePainter() {
		return linePainter;
	}
	public void setLinePainter(LinePainter linePainter) {
		this.linePainter = linePainter;
	}
}
