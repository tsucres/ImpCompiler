package Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Lexer.Symbol;
import TreePrinter.PrintableTreeNode;

public class IdentifierTableNode implements PrintableTreeNode {
	private IdentifierTable table;
	
	protected List<IdentifierTableNode> children;
	protected IdentifierTableNode parent;
	
	public IdentifierTableNode getParent() {
		return parent;
	}

	public IdentifierTableNode(IdentifierTable table) {
		this.table = table;
		this.children = new ArrayList<IdentifierTableNode>();
	}
	
	public static IdentifierTableNode withEmptyTable() {
		return new IdentifierTableNode(new IdentifierTable());
	}

	public void addChild(IdentifierTableNode child){
		child.parent = this;
		this.children.add(child);
	}
	
	public void setTable(IdentifierTable table) {
		this.table = table;
	}
	
	/**
	 * @param sym the symbol (of type VARNAME) to add in the identifier table
	 * @return see IdentifierTable.addSymbol(Symbol)
	 */
	public boolean addIdentifier(Symbol sym, IdentifierType type) {
		return this.table.addSymbol(sym, type);
	}

	/* (non-Javadoc)
	 * @see TreePrinter.PrintableTreeNode#name()
	 */
	public String name() {
		String res = Integer.toString(this.table.size()) + ": ";
		for(Map.Entry<String, IdentifierTableEntry> identifier : this.table.getTable().entrySet())
            res += identifier.getKey() + ": " + identifier.getValue().getType().getBasicType().name() + "; ";
		return res;
	}

	/**
	 * @return the symbol carried by the node
	 */
	public IdentifierTable getTable() {
		return this.table;
	}

	/* (non-Javadoc)
	 * @see TreePrinter.PrintableTreeNode#children()
	 */
	public List<IdentifierTableNode> children() {
		return this.children;
	}
}
