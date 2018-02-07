package Parser;

import java.util.ArrayList;
import java.util.TreeMap;

import Lexer.LexicalUnit;
import Lexer.Symbol;


enum BasicIdentifierType {
	INT,
	STR,
	FUNC,
	UNKNOWN,
	VOID
}

/**
 * A class meant to store the type of an identifier. Some types can have subtypes (such as functions).
 *
 */
class IdentifierType {
	public static IdentifierType INT = new IdentifierType(BasicIdentifierType.INT);
	public static IdentifierType STR = new IdentifierType(BasicIdentifierType.STR);
	public static IdentifierType FUNC(IdentifierType rtnType) {
		IdentifierType func = new IdentifierType(BasicIdentifierType.FUNC);
		func.addSubType(rtnType);
		return func;
	}
	public static IdentifierType VOID = new IdentifierType(BasicIdentifierType.VOID);
	public static IdentifierType UNKNOWN = new IdentifierType(BasicIdentifierType.UNKNOWN);
	
	
	
	private BasicIdentifierType basicType;
	public BasicIdentifierType getBasicType() {
		return basicType;
	}
	public void setBasicType(BasicIdentifierType basicType) {
		this.basicType = basicType;
	}

	private ArrayList<IdentifierType> subtypes = new ArrayList<IdentifierType>();
	public IdentifierType getSubTypeAtIndex(int index) {
		return this.subtypes.get(index);
	}
	public IdentifierType(BasicIdentifierType type) {
		this.basicType = type;
	}
	
	public void addSubType(IdentifierType type) {
		this.subtypes.add(type);
	}
	
	public boolean equals(IdentifierType other) {
		return this.equalsOrUnknown(other, false);
		
	}
	
	/**
	 * Checks recursively the equality of the basic type of all the subtypes with the basic tyê of amm the subtypes of the sepcified IdentifierTpe.
	 * @param other the other IdentifierType to compare the current instance with
	 * @param allowUnknown If this is set to true, a comparison of any basicType with UNKNOWN will yield to true.
	 * @return a boolean indicating if the specified IdentifierType
	 */
	public boolean equalsOrUnknown(IdentifierType other, boolean allowUnknown) {
		boolean isEqual = this.basicType == other.basicType || (allowUnknown && this.basicType == BasicIdentifierType.UNKNOWN);
		if (this.subtypes.size() != other.subtypes.size()) {
			return false;
		}
		for (int i = 0; i< this.subtypes.size(); i++) {
			if (!this.subtypes.get(i).equals(other.subtypes.get(i))) {
				if (!allowUnknown ||
						(allowUnknown && this.subtypes.get(i).basicType != BasicIdentifierType.UNKNOWN)) {
					return false;
				}
				
			}
		}
		return isEqual;
	}
}

public class IdentifierTable {
	private TreeMap<String,IdentifierTableEntry> table = new TreeMap<String,IdentifierTableEntry>();
	
	public TreeMap<String, IdentifierTableEntry> getTable() {
		return table;
	}

	/**
	 * Add the specified symbol in the table (only if the type of the 
	 * symbol is VARNAME and it's already in the table).
	 * @param symbol the symbol to add in the table
	 * @return A bool indicating if the symbol could be added in the table.
	 */
	public boolean addSymbol(Symbol symbol, IdentifierType type) {
		if(symbol.getType().equals(LexicalUnit.VARNAME)){
            if(!this.table.containsKey(symbol.getValue())){
                this.table.put(symbol.getValue().toString(),new IdentifierTableEntry(symbol, type));
                return true;
            }
        }
		return false;
	}
	
	/**
	 * Return a boolean indicating whether or not the table contains the specified symbol.
	 * @param symbol the symbol to check the existence in the table for.
	 * @return A bool indicating if the table contains the specified symbol
	 */
	public boolean contains(Symbol symbol) {
		return this.table.containsKey(symbol.getValue());
	}
	
	public void setTypeForSymbol(Symbol symbol, IdentifierType type) {
		this.table.put(symbol.getValue().toString(),new IdentifierTableEntry(symbol, type));
	}
	
	public int size() {
		return this.table.size();
	}
	
	public IdentifierType getTypeForSymbol(Symbol sym) {
		return this.table.get(sym.getValue().toString()).getType();
	}
}
