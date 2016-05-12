package parser;

import java.util.Vector;

import org.antlr.v4.runtime.tree.ParseTree;

public class ParserFunction {
	public String buffer;
	public Vector<String> tokenS = new Vector<>();
	public void toTokenString(ParseTree tree){

		  if(tree.getChildCount() < 4 && tree.getChildCount() > 0){
			  for(int i = 0; i < tree.getChildCount(); i ++){
				  toTokenString(tree.getChild(i));
			  }
		  } else {
			  buffer += " ";
			  buffer += tree.getText();
			  if(tree.getText().equals(";")||(tree.getText().equals("{")||tree.getText().equals("}"))){
				tokenS.add(buffer);
				buffer = "";
			  }
		  }
	  }
	public  void getStmt(ParseTree tree){
	}
	
	public void printTokens(){
		for(String s : tokenS){
			System.out.println(s);
		}
	}
}
