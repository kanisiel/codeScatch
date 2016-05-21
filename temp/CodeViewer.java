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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import Settings.Windows;
import Settings.Windows.words;
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
    private Map<String, Vector<String>> register;
    
	public CodeViewer(String title) {
        super(title, 
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable
 
        //...Create the GUI and put it in the window...
        register = new LinkedHashMap<>();
        //...Then set the window size or call pack...
        setSize(300,300);
 
        //Set the window's location.
        setLocation(0, 0);
        
        //set Styled text Attribute
        SimpleAttributeSet attrVariableReserve = new SimpleAttributeSet();
        attrVariableReserve.addAttribute(StyleConstants.Foreground, hex2Rgb(Windows.variableColor)); //for about Variable word
        attrVariableReserve.addAttribute(StyleConstants.Italic, Boolean.TRUE); //for about Variable word
        SimpleAttributeSet attrVariable = new SimpleAttributeSet();
        attrVariable.addAttribute(StyleConstants.Foreground, hex2Rgb(Windows.variableColor)); //for Variable name
        attrVariable.addAttribute(StyleConstants.Italic, Boolean.FALSE);
        SimpleAttributeSet attrFunction = new SimpleAttributeSet();
        attrFunction.addAttribute(StyleConstants.Foreground, hex2Rgb(Windows.functionColor)); //for Function name
        attrFunction.addAttribute(StyleConstants.Italic, Boolean.FALSE);
        SimpleAttributeSet attrReserve = new SimpleAttributeSet();
        attrReserve.addAttribute(StyleConstants.Foreground, hex2Rgb(Windows.reservedWordColor)); //for Reserved word
        attrReserve.addAttribute(StyleConstants.Italic, Boolean.FALSE);
        SimpleAttributeSet attrInclude = new SimpleAttributeSet();
        attrInclude.addAttribute(StyleConstants.Foreground, hex2Rgb(Windows.includeColor)); //for Include reserve word
        attrInclude.addAttribute(StyleConstants.Italic, Boolean.FALSE);
        SimpleAttributeSet attrIncludeAttr = new SimpleAttributeSet();
        attrIncludeAttr.addAttribute(StyleConstants.Foreground, hex2Rgb(Windows.includeAttrColor)); //for Include reserve word
        attrIncludeAttr.addAttribute(StyleConstants.Italic, Boolean.FALSE);
        SimpleAttributeSet attrBlack = new SimpleAttributeSet();
        attrBlack.addAttribute(StyleConstants.Foreground, Color.BLACK);
        attrBlack.addAttribute(StyleConstants.Italic, Boolean.FALSE);
	    
	   
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
	            int wordRR = before;
	            int wordLL = before;
	            Boolean matched = false;
	            String keywords;
	     
	            while (wordR <= after) {
	            	if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
	                	for(Entry<String, Vector<String>> item : register.entrySet()){
	        	            keywords="";
	                		for(String word : item.getValue()){
	                			if(keywords.equals("")){
	                				keywords=word;
	                			} else {
	                				keywords+="|"+word;
	                			}
	                		}
	                		if(!keywords.equals("") && text.substring(wordL, wordR).matches("(\\W)*("+keywords+")")){
	                			System.out.println(keywords);
                				matched = true;
                				switch(item.getKey()){
                		    	case "Variable":
                		    		setCharacterAttributes(wordL, wordR - wordL, attrReserve, false);
                		    		break;
                		    	case "Variable Reserve":
                		    		setCharacterAttributes(wordL, wordR - wordL, attrVariableReserve, false);
                		    		break;
                		    	case "Function":
                		    		setCharacterAttributes(wordL, wordR - wordL, attrFunction, false);
                		    		break;
                		    	case "Include Attr":
                		    		setCharacterAttributes(wordL, wordR - wordL, attrIncludeAttr, false);
                		    		break;
                		    	case "Include":
                		    		setCharacterAttributes(wordL, wordR - wordL, attrInclude, false);
                		    		break;
                		    	}
                				wordL=wordR;
                			}
	                	}
	                	if(!matched) {
		                	if (text.substring(wordL, wordR).matches("(\\W)*("+words.Reserved.getWordList()+")")) {
		                        setCharacterAttributes(wordL, wordR - wordL, attrReserve, false);
		                    } else if (text.substring(wordL, wordR).matches("(\\W)*("+words.Variable.getWordList()+")")){
		                    	setCharacterAttributes(wordL, wordR - wordL, attrVariableReserve, false);
	            				if(register.get("Variable Reserve")==null){
	            					Vector<String> wordList = new Vector<>();
	            					wordList.add(text.substring(wordL, wordR));
	            					register.put("Variable Reserve", wordList);
	            				} else {
	            					if(!register.get("Variable Reserve").contains(text.substring(wordL, wordR))){
	            						register.get("Variable Reserve").add(text.substring(wordL, wordR));
	            					}
	            				}
		                    } else if (text.substring(wordL, wordR).matches("(\\W)*([\\(])")){
		                    	setCharacterAttributes(wordL, wordR - 1 - wordL, attrFunction, false);
	            				if(register.get("Function")==null){
	            					Vector<String> wordList = new Vector<>();
	            					wordList.add(text.substring(wordL, wordR-1));
	            					register.put("Function", wordList);
	            				} else {
	            					if(!register.get("Function").contains(text.substring(wordL, wordR-1))){
	            						register.get("Function").add(text.substring(wordL, wordR-1));
	            					}
	            				}
		                    } else if (text.substring(wordL, wordR).matches("(\\W)*([\\;])")){
		                    	setCharacterAttributes(wordL, wordR - wordL, attrVariable, false);
	            				if(register.get("Variable")==null){
	            					Vector<String> wordList = new Vector<>();
	            					wordList.add(text.substring(wordL, wordR-1));
	            					register.put("Variable", wordList);
	            				} else {
	            					if(!register.get("Variable").contains(text.substring(wordL, wordR-1))){
	            						register.get("Variable").add(text.substring(wordL, wordR-1));
	            					}
	            				}
		                    } else if (text.substring(wordL, wordR).matches("(\\W)*([\\,])")){
		                    	Vector<String> buffer = register.get("Variable");
		                    	if(!buffer.isEmpty()&&buffer.contains(text.substring(wordL, wordR))){
		                    		setCharacterAttributes(wordL, wordR - wordL, attrVariable, false);
		                    	} else {
		                    		wordRR = findLastNonWordChar(text, wordL);
		                    		wordLL = wordRR;
		                    		while(!text.substring(wordLL, wordRR).matches("^(\\W)")){
		                    			if(text.substring(wordLL, wordRR).matches("(\\W)*("+words.Variable.getWordList()+")")){
		                    				setCharacterAttributes(wordL, wordR - wordL, attrVariable, false);
		                    				if(register.get("Variable")==null){
		                    					Vector<String> wordList = new Vector<>();
		                    					wordList.add(text.substring(wordL, wordR-1));
		                    					register.put("Variable", wordList);
		                    				} else {
		                    					if(!register.get("Variable").contains(text.substring(wordL, wordR-1))){
		    	            						register.get("Variable").add(text.substring(wordL, wordR-1));
		    	            					}
		                    				}
		                    			}
		                    			wordLL--;
		                    		}
		                    	}
		                    } else if (text.substring(wordL, wordR).matches("(\\W)*("+Windows.include+")")){
		                    	setCharacterAttributes(wordL, wordR - wordL, attrInclude, false);
	            				if(register.get("Include")==null){
	            					Vector<String> wordList = new Vector<>();
	            					wordList.add(text.substring(wordL, wordR));
	            					register.put("Include", wordList);
	            				} else {
	            					if(!register.get("Include").contains(text.substring(wordL, wordR))){
	            						register.get("Include").add(text.substring(wordL, wordR));
	            					}
	            				}
		                    } else if (text.substring(wordL, wordR).matches("([\"])(.)*([\"])") || text.substring(wordL, wordR).matches("([<])(.)*([>])")) {
		                    	wordRR = findLastNonWordChar(text, wordL);
		    	            	if((text.substring(wordRR - Windows.include.length(), wordRR).matches("(\\W)*("+Windows.include+")"))){
		    	            		setCharacterAttributes(wordLL, wordRR - wordLL, attrIncludeAttr, false);
		            				if(register.get("Include Attr")==null){
		            					Vector<String> wordList = new Vector<>();
		            					wordList.add(text.substring(wordL, wordR-1));
		            					register.put("Include Attr", wordList);
		            				} else {
		            					if(!register.get("Include Attr").contains(text.substring(wordL, wordR))){
		            						register.get("Include Attr").add(text.substring(wordL, wordR));
		            					}
		            				}
		    	            	}
		    		            	
		    	            } else
		                        setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
	                	}
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
	            int wordLL = before;
	            int wordRR = before;
	            
	            if (text.substring(before, after).matches("(\\W)*("+words.Reserved.getWordList()+")")) {
	                setCharacterAttributes(before, after - before, attrReserve, false);
	            } else if (text.substring(before, after).matches("(\\W)*("+words.Variable.getWordList()+")")){
                	setCharacterAttributes(before, after - before, attrVariableReserve, false);
    				if(register.get("Variable Reserve")==null){
    					Vector<String> beforeist = new Vector<>();
    					beforeist.add(text.substring(before, after));
    					register.put("Variable Reserve", beforeist);
    				} else {
    					register.get("Variable Reserve").add(text.substring(before, after));
    				}
                } else if (text.substring(before, after).matches("(\\W)*([\\(])")){
                	setCharacterAttributes(before, after - 1 - before, attrFunction, false);
    				if(register.get("Function")==null){
    					Vector<String> beforeist = new Vector<>();
    					beforeist.add(text.substring(before, after-1));
    					register.put("Function", beforeist);
    				} else {
    					register.get("Function").add(text.substring(before, after-1));
    				}
                } else if (text.substring(before, after).matches("(\\W)*([;])")){
                	setCharacterAttributes(before, after - before, attrVariable, false);
    				if(register.get("Variable")==null){
    					Vector<String> beforeist = new Vector<>();
    					beforeist.add(text.substring(before, after-1));
    					register.put("Variable", beforeist);
    				} else {
    					register.get("Variable").add(text.substring(before, after-1));
    				}
                } else if (text.substring(before, after).matches("(\\W)*([,])")){
                	Vector<String> buffer = register.get("Variable");
                	if(!buffer.isEmpty()&&buffer.contains(text.substring(before, after))){
                		setCharacterAttributes(before, after - before, attrVariable, false);
                	} else {
                		wordRR = findLastNonWordChar(text, before);
                		wordLL = wordRR;
                		while(!text.substring(wordLL, wordRR).matches("^(\\W)")){
                			if(text.substring(wordLL, wordRR).matches("(\\W)*("+words.Variable.getWordList()+")")){
                				setCharacterAttributes(before, after - before, attrVariable, false);
                				if(register.get("Variable")==null){
                					Vector<String> beforeist = new Vector<>();
                					beforeist.add(text.substring(before, after-1));
                					register.put("Variable", beforeist);
                				} else {
                					register.get("Variable").add(text.substring(before, after-1));
                				}
                			}
                			wordLL--;
                		}
                	}
                }  else if (text.substring(before, after).matches("(\\W)*("+Windows.include+")")){
                	setCharacterAttributes(before, after - before, attrInclude, false);
    				if(register.get("Include")==null){
    					Vector<String> beforeist = new Vector<>();
    					beforeist.add(text.substring(before, after-1));
    					register.put("Include", beforeist);
    				} else {
    					register.get("Include").add(text.substring(before, after-1));
    				}
                } else if (text.substring(before, after).matches("(\"{1}+)(\\W)*(\"{1}+)") || text.substring(before, after).matches("(<{1}+)(\\W)*(>{1}+)")) {
                	wordRR = findLastNonWordChar(text, before);
	            	if((text.substring(wordRR - Windows.include.length(), wordRR).matches("(\\W)*("+Windows.include+")"))){
	            		setCharacterAttributes(wordLL, wordRR - wordLL, attrIncludeAttr, false);
        				if(register.get("Include Attr")==null){
        					Vector<String> beforeist = new Vector<>();
        					beforeist.add(text.substring(before, after-1));
        					register.put("Include Attr", beforeist);
        				} else {
        					register.get("Include Attr").add(text.substring(before, after-1));
        				}
	            	}
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
	
	//Hex Color code to RGB java Color Object
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
