package Parser;
import java.util.ArrayList;
import java.util.List;

import Lexer.Symbol;
import TreePrinter.PrintableTreeNode;
	
/**
 * A node that can be used to build ASTs or parser trees
 *
 */
public class SymbolNode implements PrintableTreeNode{

	private Symbol sym;
	
	protected List<SymbolNode> children;

	public SymbolNode(Symbol sym) {
		this.sym = sym;
		this.children = new ArrayList<SymbolNode>();
	}

	public void addChild(SymbolNode child){
		this.children.add(child);
	}

	/* (non-Javadoc)
	 * @see TreePrinter.PrintableTreeNode#name()
	 */
	public String name() {
		return this.sym.getValue().toString();
	}

	/**
	 * @return the symbol carried by the node
	 */
	public Symbol getSymbol() {
		return this.sym;
	}

	/* (non-Javadoc)
	 * @see TreePrinter.PrintableTreeNode#children()
	 */
	public List<SymbolNode> children() {
		return this.children;
	}
}