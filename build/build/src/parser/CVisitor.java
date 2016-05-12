package parser;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;


public class CVisitor extends AbstractParseTreeVisitor<ParseTree>{

	@Override
	public ParseTree visit(ParseTree parsetree) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParseTree visitChildren(RuleNode rulenode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParseTree visitErrorNode(ErrorNode errornode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParseTree visitTerminal(TerminalNode terminalnode) {
		// TODO Auto-generated method stub
		return null;
	}

}
