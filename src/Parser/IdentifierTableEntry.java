package Parser;

import Lexer.Symbol;

public class IdentifierTableEntry {
	Symbol sym;
	public Symbol getSym() {
		return sym;
	}
	public void setSym(Symbol sym) {
		this.sym = sym;
	}

	IdentifierType type;
	public IdentifierType getType() {
		return type;
	}
	public void setType(IdentifierType type) {
		this.type = type;
	}
	
	public IdentifierTableEntry(Symbol sym, IdentifierType type) {
		this.setSym(sym);
		this.setType(type);
	}
}