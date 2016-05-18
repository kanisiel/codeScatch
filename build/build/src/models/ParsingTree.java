package models;

import java.util.TreeMap;

public class ParsingTree implements ParseTree {
	private TreeMap<Integer, ParseTree> parseTree;
	

	public ParsingTree() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TreeMap<Integer, ParseTree> getParseTree() {
		return parseTree;
	}

	public void setParseTree(TreeMap<Integer, ParseTree> parseTree) {
		this.parseTree = parseTree;
	}
	
	public void addNode(ParseTree T){
		
	}
}
