package parser;

public class CMain
{
//  public static void main(String[] args)
//  {
//	  
//	  //input text and translate String to Stream
//	  ANTLRInputStream in = new ANTLRInputStream("int a = 10; int b;\nint are(int a, float b);\nint main(){ int sum = 0;\n int arr[10]; int i; for(i = 1; i < 11; ++i){ "
//				+ "int b;\n sum += i; if(sum > 10)\nbreak;}"
//				+ "printf(\"%d\", sum); are(sum); {int a;}return 0;}\n int are(int a, float b){return 0;}");
//	  /*"int are(int a, float b);\nint a = 3, b = a + 6, ad; \n float c = 6, f = 3;\nint main(){\n \n int g; a = 5; g = 3;\nprintf(\"hello world!\""+
//			  ");\nreturn 0;\n}\n int are(itt a, float b){return 0;}"*/
//      
//	  /*"main(){ int sum = 0; int i; for(i = 1; i < 11; ++i){ "
//		+ "int b;\n sum += i; if(sum > 10)\nbreak;}"
//		+ "printf(\"%d\", sum); }"*/
//	  /*"int are(int a);\nmain(){ int sum = 0; int i; for(i = 1; i < 11; ++i){ "
//				+ "int b;\n sum += i; if(sum > 10)\nbreak;}"
//				+ "printf(\"%d\", sum); are(sum);}\n int are(int a){return 0;}"*/
//	  //"int a; \n int b = 10; \n int c = a+b;"
//	  
//	  //"int are(float a, float b); \n int is(void);\n void am();"
//	  /*"int a = 10; int b;\nint are(int a, float b);\nint main(){ int sum = 0; int i; for(i = 1; i < 11; ++i){ "
//		+ "int b;\n sum += i; if(sum > 10)\nbreak;}"
//		+ "printf(\"%d\", sum); are(sum); {int a;}return 0;}\n int are(int a, float b){return 0;}"
//	  */
//	  // input Stream into lexer
//	  CLexer lexer = new CLexer(in);
//	  
//	  // tokenStream into parser
//	  CommonTokenStream tokens = new CommonTokenStream(lexer);
//	  CParser parser = new CParser(tokens);
//	  
//	  // make parseTree
//	  ParseTree parseTree = parser.translationUnit();
//	          
//      // AST Tree Viewer text
//      //System.out.println(parseTree.toStringTree(parser));
//      
//      // get code structure  
//      CVisitor visitor = new CVisitor(parseTree, parser);
//      Vector<ParseTree> parseTrees = visitor.codeStructure(parseTree);
//      
//      // AST Tree Viewer graphic
//      JFrame frame = new JFrame("Antlr AST");
//      JPanel panel = new JPanel();
//      JScrollPane scrollPane = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//      scrollPane.setPreferredSize(new Dimension(600, 600));
//      scrollPane.getVerticalScrollBar().setUnitIncrement(16);
//      TreeViewer viewer = new TreeViewer(Arrays.asList(
//              parser.getRuleNames()), parseTree);
//      viewer.setScale(1);//scale a little
//      panel.add(viewer);
//  
//      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//      frame.setSize(1600,800);
//      frame.add(scrollPane);
//      frame.setVisible(true);
//      
//      //print parseTree
//      //visitor.parseTreeToString(parseTree);
//      
//      CSemanticAnalysis semanticAnalysis = new CSemanticAnalysis(parser);
//      
//
//      for(int i = 0; i < parseTrees.size(); ++i){
//	      semanticAnalysis.analyzeDCLR(parseTrees.get(i).getChild(0));
//      }
//  
//  }
}


//tokens.getTokens().get(0).getType() token�� type
//tokens.getTokens().get(0).getText() token�� �̸�

/*	
private static void toTokenString(ParseTree tree){

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
}*/