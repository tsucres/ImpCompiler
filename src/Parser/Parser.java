package Parser;
import java.util.ArrayList;

import Lexer.LexicalUnit;
import Lexer.Symbol;

// TODO: the production rules aren't correct since the addition of strings and functions
/**
 * A static class giving names to the construction rule ids.
 */
class ProductionRules {
    public static final int PROGRAM = 1;
    public static final int CODE_EPSILON = 2;
    public static final int CODE_INSTLIST = 3;
    public static final int INSTLIST = 4;
    public static final int INSTLISTSUFFIX_EPSILON = 5;
    public static final int INSTLISTSUFFIX_INSTLIST = 6;
    public static final int INSTRUCTION_ASSIGN = 7;
    public static final int INSTRUCTION_IF = 8;
    public static final int INSTRUCTION_WHILE = 9;
    public static final int INSTRUCTION_FOR = 10;
    public static final int INSTRUCTION_PRINT = 11;
    public static final int INSTRUCTION_READ = 12;
    public static final int ASSIGN = 13;
    public static final int EXPRARITH = 14;
    public static final int EA1 = 15;
    public static final int EA2_PLUS = 16;
    public static final int EA2_MINUS = 17;
    public static final int EA2_EPSILON = 18;
    public static final int PROD = 19;
    public static final int P1 = 20;
    public static final int P2_TIMES = 21;
    public static final int P2_DIVIDE = 22;
    public static final int P2_EPSILON = 23;
    public static final int ATOM_VARNAME = 24;
    public static final int ATOM_NUMBER = 25;
    public static final int ATOM_MINUS = 26;
    public static final int ATOM_LPAREN = 27;
    public static final int COND = 28;
    public static final int C1 = 29;
    public static final int C2_OR = 30;
    public static final int C2_EPSILON = 31;
    public static final int BOOLAND = 32;
    public static final int BA1 = 33;
    public static final int BA2_AND = 34;
    public static final int BA2_EPSILON = 35;
    public static final int BOOLATOM_NOT = 36;
    public static final int BOOLATM_EXPRARITH = 37;
    public static final int IF = 38;
    public static final int IFSUFFIX_ENDIF = 39;
    public static final int IFSUFFIX_ELSE = 40;
    public static final int COMP_EQ = 41;
    public static final int COMP_GEQ = 42;
    public static final int COMP_GT = 43;
    public static final int COMP_LEQ = 44;
    public static final int COMP_LT = 45;
    public static final int COMP_NEQ = 46;
    public static final int WHILE = 47;
    public static final int FOR = 48;
    public static final int FORSUFFIX_BY = 49;
    public static final int FORSUFFIX_TO = 50;
    public static final int PRINT = 51;
    public static final int READ = 52;

}
/**
 * The Imp parser
 */
public class Parser {
    
    /** The list of symbols in the order they were detected by the lexer. */
    private ArrayList<Symbol> symbols = new ArrayList<Symbol>();
    /** The index (in symbols) of the symbol currently being processed by the parser. */
    private int currentSymbolIndex = 0;
    /** The resulting parse tree */
    private SymbolNode parserTree = null;
    /** The resulting derivation sequence */
    private ArrayList<Integer> derivation = new ArrayList<Integer>();
    
    private IdentifierTableNode identifierTablesTree = IdentifierTableNode.withEmptyTable();
    public IdentifierTableNode getIdentifierTablesTree() {
		return identifierTablesTree;
	}


	/**
     * @return The resulting parse tree
     */
    public SymbolNode getTree() {
        return this.parserTree;
    } 
    
    
    /**
     * Builds the parser tree from the specified list of symbols
     * @param symbols The list of symbols detected by the lexer
     */
    public Parser(ArrayList<Symbol> symbols) {
        this.symbols = symbols;
        this.parserTree = new SymbolNode(new Symbol(null, "<S>"));
        Symbol next = getNextSymbol();
        while (next.getType() == LexicalUnit.FUNC) {
        		this.parserTree.addChild(constrFunc());
        		next = getNextSymbol();
        }
        this.parserTree.addChild(constrProgram());
        
        // once the parser tree is built, we determine the types of 
        // all the variables that could not be trivially determined before.
        this.completeIdTableWithTypes(this.parserTree);
    }
    
    private void completeIdTableWithTypes(SymbolNode parserTree) {
    		// TODO
    		/*for (SymbolNode child: parserTree.children()) {
    			if (child.getSymbol().getType() == LexicalUnit.RETURN) {
    				
    			} else if (child.getSymbol().getValue().equals("<FuncCall>")) {
    				
    			}
    			completeIdTableWithTypes(child);
    		}*/
    }
    /**
     * Add the specified rule the derivation path.
     * @param ruleId the id of the rule to add in the derivation sequence.
     */
    private void deriv(int ruleId) {
        derivation.add(ruleId);
    }
    /**
     * Consume the current symbol and move the parser to the next symbol.
     * @return the readed symbol
     */
    private Symbol readNextSymbol() {
        if (currentSymbolIndex < symbols.size()) {
            currentSymbolIndex += 1;
            return symbols.get(currentSymbolIndex-1);
        }
        error("Error: bad ending. Expected END at the end of the program.");
        return null;
    }
    /**
     * Read the next symbol and checks that its type matches the expected 
     * LexicalUnit. If it's not the case, shows an error and terminate the 
     * parser.
     * @param lexUnit the type of the symbol to match.
     * @return the matched symbol.
     */
    private Symbol match(LexicalUnit lexUnit) {
        Symbol currentSym = readNextSymbol();
        if (currentSym.getType() != lexUnit) {
            unexpectedSymbolError(currentSym, lexUnit);
        }
        
        this.checkForUndeclaredVarUsage(currentSym);
        this.updateIdTable(currentSym);
        return currentSym;
    }
    
    /**
     * Change the current scope (create new idTables if necessary) according to the specified symbol.
     * @param sym the symbol telling how the scope should be affected.
     */
    private void updateIdTable(Symbol sym) {
    		int scopeChange = this.getScopeForLexUnit(sym.getType());
    		switch (scopeChange) {
    			case 1:
    				// Create a new IdTable and add it as a child of the current one.
    				IdentifierTableNode currentScope = IdentifierTableNode.withEmptyTable();
    				this.identifierTablesTree.addChild(currentScope);
    				this.identifierTablesTree = currentScope;
    				break;
    			case 2:
    				// Create a new IdTable and add it as a child of the parent one
    				IdentifierTableNode currentScope2 = IdentifierTableNode.withEmptyTable();
    				IdentifierTableNode parentScope = this.identifierTablesTree.getParent();
    				parentScope.addChild(currentScope2);
    				this.identifierTablesTree = currentScope2;
    				break;
    			case -1:
    				// Change the current IdTable to the parent of the current one
    				this.identifierTablesTree = this.identifierTablesTree.getParent();
    				break;
    			case -2:
    				this.identifierTablesTree = this.identifierTablesTree.getParent().getParent();
    			default:
    				break;
    		}
    }
    
    /**
     * Cheks in the current scope (current idTable and all its parents) if a variable is declared.
     * @param sym the symbol (of type VARNAME) you want to check the existance
     * @return Boolean indicating if the variable is already declared.
     */
    private boolean isVarnameDeclaredInCurrentScope(Symbol sym) {
    		if (sym.getType() == LexicalUnit.VARNAME) {
    			IdentifierTableNode parentNode = this.identifierTablesTree;
    			while (parentNode != null) {
    				if (parentNode.getTable().contains(sym)) {
    					return true;
    				}
    				parentNode = parentNode.getParent();
    			}
    		}
    		return false;
    }
    
    /**
     * Detect uses of undeclared variable and add the new (valid) declaration in the current idTable.
     * Quits the program with an error if there's an undefined variable.
     * @param sym the mast matched symbol
     */
    private void checkForUndeclaredVarUsage(Symbol sym) {
    		if (sym.getType() == LexicalUnit.VARNAME) {
    			if (!(this.isVarnameDeclaredInCurrentScope(sym))) {
    				Symbol next = this.getNextSymbol();
    				if (next.getType() == LexicalUnit.ASSIGN || // before assignation
    						next.getType() == LexicalUnit.VARNAME || // the parameters in the function
    						next.getType() == LexicalUnit.IN) { // the parameters in the function
    					// Add the variable in the current IdTable
    					this.identifierTablesTree.addIdentifier(sym, IdentifierType.UNKNOWN);
    				}
    				else if (next.getType() == LexicalUnit.FROM) { // in a for loop
    					this.identifierTablesTree.addIdentifier(sym, IdentifierType.INT);
    				} else if (next.getType() == LexicalUnit.WITH) {  // the name of a function
    					this.identifierTablesTree.addIdentifier(sym, IdentifierType.FUNC(IdentifierType.UNKNOWN));
    				}
    				else {
    					int line = sym.getLine();
    					int col = sym.getColumn();
    					String value = sym.getValue().toString();
    					error(String.format("Error: variable %s was used before initialisation. Line %d col %d", value, line, col));
    				}
    			}
    		}
    }
    
    private void setVariableType(Symbol sym, IdentifierType type) {
    		if (sym.getType() == LexicalUnit.VARNAME) {
			IdentifierTableNode parentNode = this.identifierTablesTree;
			while (parentNode != null) {
				if (parentNode.getTable().contains(sym)) {
					if (	parentNode.getTable().getTypeForSymbol(sym).equalsOrUnknown(type, true)
							) {
						IdentifierTable t = parentNode.getTable();
						t.setTypeForSymbol(sym, type);
						parentNode.setTable(t);
					} else {
						error(String.format("Type of symbol %s is ambiguous.", sym.getValue()));
					}
					
				}
				parentNode = parentNode.getParent();
			}
		}
    }
    
    private IdentifierType getVariableType(Symbol sym) {
    		if (sym.getType() == LexicalUnit.VARNAME) {
			IdentifierTableNode parentNode = this.identifierTablesTree;
			while (parentNode != null) {
				if (parentNode.getTable().contains(sym)) {
					return parentNode.getTable().getTypeForSymbol(sym);
				}
				parentNode = parentNode.getParent();
			}
		}
    		return null;
    }
    
    /**
     * @param lexicalUnit the lexicalUnit to describe
     * @return an int representing how the specified LexicalUnit affects the scope.
     */
    private int getScopeForLexUnit(LexicalUnit lexicalUnit) {
    		switch (lexicalUnit) {
			case DO:
			case THEN:
			case FOR:
			case WHILE:
			case WITH:
			case IN:
			case BEGIN:
				return 1; // New child scope
			case ELSE:
				return 2; // New scope on the same level
			case DONE: // before a DONE there's a WHILE/FOR and a DO (so there are 2 chilren created). This is so that variable can be initialised in "For x from"
			case ENDFUNC:
				return -2; // Go back to the "grandparent" scope
			case ENDIF:
			case END:
				return -1; // Go back to the parent scope

			default:
				return 0; // Doesn't change the scope
		}
    }
    /**
     * @return the first non-consumed symbol.
     */
    private Symbol getNextSymbol() {
        if (currentSymbolIndex < symbols.size()) {
            return symbols.get(currentSymbolIndex);
        }
        error("Error: bad ending. Expected END at the end of the program.");
        return null;
    }
    /**
     * Shows an error and terminate the parser.
     * @param symbol the unexpected symbol that was matched.
     * @param expected the type the matched symbol was expected to be.
     */
    private void unexpectedSymbolError(Symbol symbol, LexicalUnit expected) {
        String msg = String.format("Error line %d Col %d: unexpected symbol of type %s matched.", symbol.getLine(), symbol.getColumn(), symbol.getType());
        if (expected != null) {
            msg += String.format(" Expected %s.", expected.toString());
        }
        error(msg);
    }
    /**
     * Prints the specified error and termintes the parser.
     * @param error the message to show
     */
    private void error(String error) {
        System.out.println(error);
        System.exit(1);
    }
    
    
    private SymbolNode constrProgram() {
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<Program>"));
        Symbol beginSymbol = match(LexicalUnit.BEGIN);
        currentTreeLevel.addChild(new SymbolNode(beginSymbol));
        deriv(ProductionRules.PROGRAM);
        SymbolNode bodyTree = constrCode();
        if (bodyTree != null) {
            currentTreeLevel.addChild(bodyTree);
        }
        Symbol endSymbol = match(LexicalUnit.END);
        currentTreeLevel.addChild(new SymbolNode(endSymbol));
        return currentTreeLevel;
    }
    
    private SymbolNode constrCode() {
        Symbol next = getNextSymbol();
        switch (next.getType()) {
            case VARNAME:
            case IF:
            case WHILE:
            case FOR:
            case PRINT:
            case READ:
            case CALL:
                deriv(ProductionRules.CODE_INSTLIST);
                SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<Code>"));
                currentTreeLevel.addChild(constrInstList());
                return currentTreeLevel;
            default:
                deriv(ProductionRules.CODE_EPSILON);
                return null; // epsilon
        }
    }
    private SymbolNode constrInstList() {
        deriv(ProductionRules.INSTLIST);
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<InstList>"));
        SymbolNode instructionTree = constrInstruction();
        currentTreeLevel.addChild(instructionTree);

        SymbolNode instListSuffixTree = constrInstListSuffix();
        if (instListSuffixTree != null) {
            currentTreeLevel.addChild(instListSuffixTree);
        }
        return currentTreeLevel;
    }
    private SymbolNode constrInstListSuffix() {
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<InstListSuffix>"));
        Symbol next = getNextSymbol();
        if (next.getType() == LexicalUnit.SEMICOLON) {
            deriv(ProductionRules.INSTLISTSUFFIX_INSTLIST);
            Symbol sym = match(LexicalUnit.SEMICOLON);
            
            currentTreeLevel.addChild(new SymbolNode(sym));
            currentTreeLevel.addChild(constrInstList());
            
        } else {
            deriv(ProductionRules.INSTLISTSUFFIX_EPSILON);
            return null; // epsilon
        } 
        return currentTreeLevel;

    }
    private SymbolNode constrInstruction() {
        Symbol next = getNextSymbol();

        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<Instruction>"));

        switch (next.getType()) {
            case VARNAME:
                deriv(ProductionRules.INSTRUCTION_ASSIGN);
                currentTreeLevel.addChild(constrAssign());
                break;
            case IF:
                deriv(ProductionRules.INSTRUCTION_IF);
                currentTreeLevel.addChild(constrIf());
                break;
            case WHILE:
                deriv(ProductionRules.INSTRUCTION_WHILE);
                currentTreeLevel.addChild(constrWhile());
                break;
            case FOR:
                deriv(ProductionRules.INSTRUCTION_FOR);
                currentTreeLevel.addChild(constrFor());
                break;
            case PRINT:
                deriv(ProductionRules.INSTRUCTION_PRINT);
                currentTreeLevel.addChild(constrPrint());
                break;
            case READ:
                deriv(ProductionRules.INSTRUCTION_READ);
                currentTreeLevel.addChild(constrRead());
                break;
            case CALL:
            		currentTreeLevel.addChild(constrFuncCall());
            		break;
            default:
                unexpectedSymbolError(next, null);
        }
        return currentTreeLevel;

    }
    
    private SymbolNode constrFunc() {
    		SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<Func>"));
    		Symbol funcSym = match(LexicalUnit.FUNC);
        currentTreeLevel.addChild(new SymbolNode(funcSym));
        Symbol varSym = match(LexicalUnit.VARNAME);
        // TODO: check for function redeclaration
        currentTreeLevel.addChild(new SymbolNode(varSym));
        Symbol withSym = match(LexicalUnit.WITH);
        currentTreeLevel.addChild(new SymbolNode(withSym));
        currentTreeLevel.addChild(constrFuncSuffix());
        
    		return currentTreeLevel;
    }
    private SymbolNode constrFuncSuffix() {
    		Symbol next = getNextSymbol();
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<FuncSuffix>"));
        if (next.getType() == LexicalUnit.IN) {
            //deriv(ProductionRules.FUNCSUFFIX_RP);
            Symbol rpSym = match(LexicalUnit.IN);
            currentTreeLevel.addChild(new SymbolNode(rpSym));
            SymbolNode codeTree = constrCode();
            if (codeTree != null) {
                currentTreeLevel.addChild(codeTree);
            }
            currentTreeLevel.addChild(constrReturnStmt());
        } else {
            //deriv(ProductionRules.FUNCSUFFIX_VN);
        		Symbol varSym =	getNextSymbol();
        		
        		if (this.identifierTablesTree.getTable().contains(varSym)) {
        			error(String.format("Duplicate variable name \"%s\". Line %d col %d.", 
        					varSym.getValue().toString(), varSym.getLine(), varSym.getColumn()));
        		}
        		varSym = match(LexicalUnit.VARNAME);
            currentTreeLevel.addChild(new SymbolNode(varSym));
            currentTreeLevel.addChild(constrFuncSuffix());
        }
        return currentTreeLevel;
    }
    
    private SymbolNode constrReturnStmt() {
    		Symbol next = getNextSymbol();
    		SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<ReturnStmt>"));
    		if (next.getType() == LexicalUnit.RETURN) {
    			Symbol returnSym = match(LexicalUnit.RETURN);
            currentTreeLevel.addChild(new SymbolNode(returnSym));
            next = getNextSymbol();
            if (next.getType() == LexicalUnit.STRING) {
            		currentTreeLevel.addChild(new SymbolNode(match(LexicalUnit.STRING)));
            } else {
            		currentTreeLevel.addChild(constrExprArith());
            }
            
                
    		} 
    		Symbol endfuncSym = match(LexicalUnit.ENDFUNC);
        currentTreeLevel.addChild(new SymbolNode(endfuncSym));
    		
    		return currentTreeLevel;
    			
    }
    private SymbolNode constrAssign() {
        deriv(ProductionRules.ASSIGN);
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<Assign>"));
        Symbol varSym = match(LexicalUnit.VARNAME);
        currentTreeLevel.addChild(new SymbolNode(varSym));
        Symbol assignSym = match(LexicalUnit.ASSIGN);
        currentTreeLevel.addChild(new SymbolNode(assignSym));
        SymbolNode assignSuffixNode = constrAssignSuffix();
        if (assignSuffixNode.children().get(0).getSymbol().getType() == LexicalUnit.STRING) {
        		setVariableType(varSym, IdentifierType.STR);
        } else if (assignSuffixNode.children().get(0).getSymbol().getValue().equals("<ExprArith>")) {
        		setVariableType(varSym, IdentifierType.INT);
        } else if (assignSuffixNode.children().get(0).getSymbol().getValue().equals("<FuncCall>")) {
        		//SymbolNode astFuncCall = assignSuffixNode.children().get(0);
        		setVariableType(varSym, IdentifierType.INT);
        		//IdentifierType funcRtnType = this.getVariableType(astFuncCall.children().get(0).getSymbol()).getSubTypeAtIndex(0);
        		//setVariableType(varSym, funcRtnType);
        		// TODO
        }
        currentTreeLevel.addChild(assignSuffixNode);
        return currentTreeLevel;
    }
    private SymbolNode constrAssignSuffix() {
    		//deriv(ProductionRules.ASSIGNSUFFIX);
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<AssignSuffix>"));
        Symbol next = getNextSymbol();
        
        if (next.getType() == LexicalUnit.STRING) {
        		Symbol strSym = match(LexicalUnit.STRING);
        		currentTreeLevel.addChild(new SymbolNode(strSym));
        } else if (next.getType() == LexicalUnit.CALL) {
        		currentTreeLevel.addChild(constrFuncCall());
        } else {
        		currentTreeLevel.addChild(constrExprArith());
        }
        return currentTreeLevel;
    }
    private SymbolNode constrExprArith() {
        deriv(ProductionRules.EXPRARITH);
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<ExprArith>"));
        currentTreeLevel.addChild(constrEA1());
        SymbolNode ea2Tree = constrEA2();
        if (ea2Tree != null) {
            currentTreeLevel.addChild(ea2Tree);
        }
        return currentTreeLevel;
    }
    private SymbolNode constrEA1() {
        deriv(ProductionRules.EA1);
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<EA1>"));
        currentTreeLevel.addChild(constrProd());
        return currentTreeLevel;
    }
    private SymbolNode constrEA2() {
        Symbol next = getNextSymbol();
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<EA2>"));
        if (next.getType() == LexicalUnit.PLUS) {
            deriv(ProductionRules.EA2_PLUS);
            Symbol plusSym = match(LexicalUnit.PLUS);
            currentTreeLevel.addChild(new SymbolNode(plusSym));
            currentTreeLevel.addChild(constrProd());
            SymbolNode ea2Tree = constrEA2();
            if (ea2Tree != null) {
                currentTreeLevel.addChild(ea2Tree);
            }
            return currentTreeLevel; 
        } else if (next.getType() == LexicalUnit.MINUS) {
            deriv(ProductionRules.EA2_MINUS);
            Symbol minusSym = match(LexicalUnit.MINUS);
            currentTreeLevel.addChild(new SymbolNode(minusSym));
            currentTreeLevel.addChild(constrProd());
            SymbolNode ea2Tree = constrEA2();
            if (ea2Tree != null) {
                currentTreeLevel.addChild(ea2Tree);
            }
            return currentTreeLevel; 
        } else { // epsilon
            deriv(ProductionRules.EA2_EPSILON);
            return null;
        } 
    }
    private SymbolNode constrProd() {
        deriv(ProductionRules.PROD);
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<Prod>"));
        currentTreeLevel.addChild(constrP1());
        SymbolNode p2Tree = constrP2();
        if (p2Tree != null) {
            currentTreeLevel.addChild(p2Tree);
        }
        return currentTreeLevel;
    }
    private SymbolNode constrP1() {
        deriv(ProductionRules.P1);
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<P1>"));
        currentTreeLevel.addChild(constrAtom());
        return currentTreeLevel;
    }
    private SymbolNode constrP2() {
        Symbol next = getNextSymbol();
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<P2>"));
        if (next.getType() == LexicalUnit.TIMES) {
            deriv(ProductionRules.P2_TIMES);
            Symbol multSym = match(LexicalUnit.TIMES);
            currentTreeLevel.addChild(new SymbolNode(multSym));
            currentTreeLevel.addChild(constrAtom());
            SymbolNode p2Tree = constrP2();
            if (p2Tree != null) {
                currentTreeLevel.addChild(p2Tree);
            }
            return currentTreeLevel; 
        } else if (next.getType() == LexicalUnit.DIVIDE) {
            deriv(ProductionRules.P2_DIVIDE);
            Symbol divSym = match(LexicalUnit.DIVIDE);
            currentTreeLevel.addChild(new SymbolNode(divSym));
            currentTreeLevel.addChild(constrAtom());
            SymbolNode p2Tree = constrP2();
            if (p2Tree != null) {
                currentTreeLevel.addChild(p2Tree);
            }
            return currentTreeLevel; 
        } else if (next.getType() == LexicalUnit.REM) {
            //deriv(ProductionRules.P2_REM);
            Symbol remSym = match(LexicalUnit.REM);
            currentTreeLevel.addChild(new SymbolNode(remSym));
            currentTreeLevel.addChild(constrAtom());
            SymbolNode p2Tree = constrP2();
            if (p2Tree != null) {
                currentTreeLevel.addChild(p2Tree);
            }
            return currentTreeLevel; 
        }else { // epsilon
            deriv(ProductionRules.P2_EPSILON);
            return null;
        } 
    }
    private SymbolNode constrAtom() {
        Symbol next = getNextSymbol();

        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<Atom>"));

        switch (next.getType()) {
            case VARNAME:
                deriv(ProductionRules.ATOM_VARNAME);
                Symbol varSym = match(LexicalUnit.VARNAME);
                currentTreeLevel.addChild(new SymbolNode(varSym));
                break;
            case NUMBER:
                deriv(ProductionRules.ATOM_NUMBER);
                Symbol numSym = match(LexicalUnit.NUMBER);
                currentTreeLevel.addChild(new SymbolNode(numSym));
                break;
            case MINUS:
                deriv(ProductionRules.ATOM_MINUS);
                Symbol minSym = match(LexicalUnit.MINUS);
                currentTreeLevel.addChild(new SymbolNode(minSym));
                currentTreeLevel.addChild(constrAtom());
                break;
            case LPAREN:
                deriv(ProductionRules.ATOM_LPAREN);
                Symbol lpSym = match(LexicalUnit.LPAREN);
                currentTreeLevel.addChild(new SymbolNode(lpSym));
                currentTreeLevel.addChild(constrExprArith());
                Symbol rpSym = match(LexicalUnit.RPAREN);
                currentTreeLevel.addChild(new SymbolNode(rpSym));
                break;

            default:
                unexpectedSymbolError(next, null);
        }
        return currentTreeLevel;
    }
    private SymbolNode constrCond() {
        deriv(ProductionRules.COND);
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<Cond>"));
        currentTreeLevel.addChild(constrC1());
        SymbolNode c2Tree = constrC2();
        if (c2Tree != null) {
            currentTreeLevel.addChild(c2Tree);
        }
        return currentTreeLevel;
    }
    private SymbolNode constrC1() {
        deriv(ProductionRules.C1);
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<C1>"));
        currentTreeLevel.addChild(constrBoolAnd());
        return currentTreeLevel;
    }
    private SymbolNode constrC2() {
        Symbol next = getNextSymbol();
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<C2>"));
        if (next.getType() == LexicalUnit.OR) {
            deriv(ProductionRules.C2_OR);
            Symbol orSym = match(LexicalUnit.OR);
            currentTreeLevel.addChild(new SymbolNode(orSym));
            currentTreeLevel.addChild(constrBoolAnd());
            SymbolNode c2Tree = constrC2();
            if (c2Tree != null) {
                currentTreeLevel.addChild(c2Tree);
            }
            return currentTreeLevel; 
        } else { // epsilon
            deriv(ProductionRules.C2_EPSILON);
            return null; 
        } 
    }
    private SymbolNode constrBoolAnd() {
        deriv(ProductionRules.BOOLAND);
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<BoolAnd>"));
        currentTreeLevel.addChild(constrBA1());
        SymbolNode ba2Tree = constrBA2();
        if (ba2Tree != null) {
            currentTreeLevel.addChild(ba2Tree);
        }
        return currentTreeLevel;
    }
    private SymbolNode constrBA1() {
        deriv(ProductionRules.BA1);
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<BA1>"));
        currentTreeLevel.addChild(constrBoolAtom());
        return currentTreeLevel;
    }
    private SymbolNode constrBA2() {
        Symbol next = getNextSymbol();
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<BA2>"));
        if (next.getType() == LexicalUnit.AND) {
            deriv(ProductionRules.BA2_AND);
            Symbol andSYm = match(LexicalUnit.AND);
            currentTreeLevel.addChild(new SymbolNode(andSYm));
            currentTreeLevel.addChild(constrBoolAtom());
            SymbolNode ba2Tree = constrBA2();
            if (ba2Tree != null) {
                currentTreeLevel.addChild(ba2Tree);
            }
            return currentTreeLevel; 
        } else { // epsilon
            deriv(ProductionRules.BA2_EPSILON);
            return null;
        } 
    }
    private SymbolNode constrBoolAtom() {
        Symbol next = getNextSymbol();
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<BoolAtom>"));
        if (next.getType() == LexicalUnit.NOT) {
            deriv(ProductionRules.BOOLATOM_NOT);
            Symbol notSym = match(LexicalUnit.NOT);
            currentTreeLevel.addChild(new SymbolNode(notSym));
            currentTreeLevel.addChild(constrBoolAtom());

        } else if (next.getType() == LexicalUnit.VARNAME || 
            next.getType() == LexicalUnit.NUMBER || 
            next.getType() == LexicalUnit.MINUS || 
            next.getType() == LexicalUnit.LPAREN) {
            deriv(ProductionRules.BOOLATM_EXPRARITH);
            currentTreeLevel.addChild(constrExprArith());
            currentTreeLevel.addChild(constrComp());
            currentTreeLevel.addChild(constrExprArith());

        } else {
            unexpectedSymbolError(next, null);
        }
        return currentTreeLevel;
    }
    private SymbolNode constrIf() {
        deriv(ProductionRules.IF);
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<If>"));
        Symbol ifSym = match(LexicalUnit.IF);
        currentTreeLevel.addChild(new SymbolNode(ifSym));
        currentTreeLevel.addChild(constrCond());
        Symbol thenSym = match(LexicalUnit.THEN);
        currentTreeLevel.addChild(new SymbolNode(thenSym));
        SymbolNode codeTree = constrCode();
        if (codeTree != null) {
            currentTreeLevel.addChild(codeTree);
        }
        currentTreeLevel.addChild(constrIfSuffix());
        
        return currentTreeLevel;
    }
    private SymbolNode constrIfSuffix() {
        Symbol next = getNextSymbol();
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<IfSuffix>"));
        if (next.getType() == LexicalUnit.ENDIF) {
            deriv(ProductionRules.IFSUFFIX_ENDIF);
            Symbol endifSym = match(LexicalUnit.ENDIF);
            currentTreeLevel.addChild(new SymbolNode(endifSym));
        } else if (next.getType() == LexicalUnit.ELSE) { 
            deriv(ProductionRules.IFSUFFIX_ELSE);
            Symbol elseSym = match(LexicalUnit.ELSE);
            currentTreeLevel.addChild(new SymbolNode(elseSym));
            SymbolNode codeTree = constrCode();
            if (codeTree != null) {
                currentTreeLevel.addChild(codeTree);
            }
            Symbol endifSym = match(LexicalUnit.ENDIF);
            currentTreeLevel.addChild(new SymbolNode(endifSym));
        } else {
            unexpectedSymbolError(next, null);
        } 
        return currentTreeLevel;
    }
    private SymbolNode constrComp() {
        Symbol next = getNextSymbol();
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<IfSuffix>"));
        switch (next.getType()) {
            case EQ:    
                deriv(ProductionRules.COMP_EQ);
                break;
            case GEQ:
                deriv(ProductionRules.COMP_GEQ);
                break;
            case GT:
                deriv(ProductionRules.COMP_GT);
                break;
            case LEQ:
                deriv(ProductionRules.COMP_LEQ);
                break;
            case LT:
                deriv(ProductionRules.COMP_LT);
                break;
            case NEQ:
                deriv(ProductionRules.COMP_NEQ);
                break;
            default:
                unexpectedSymbolError(next, null);
        }
        Symbol sym = match(next.getType());
        currentTreeLevel.addChild(new SymbolNode(sym));
        return currentTreeLevel;
    }
    private SymbolNode constrWhile() {
        deriv(ProductionRules.WHILE);
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<While>"));
        Symbol whileSym = match(LexicalUnit.WHILE);
        currentTreeLevel.addChild(new SymbolNode(whileSym));
        currentTreeLevel.addChild(constrCond());
        Symbol doSym = match(LexicalUnit.DO);
        currentTreeLevel.addChild(new SymbolNode(doSym));
        SymbolNode codeTree = constrCode();
        if (codeTree != null) {
            currentTreeLevel.addChild(codeTree);
        }
        Symbol doneSym = match(LexicalUnit.DONE);
        currentTreeLevel.addChild(new SymbolNode(doneSym));
        
        return currentTreeLevel;
    }
    private SymbolNode constrFor() {
        deriv(ProductionRules.FOR);
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<For>"));
        Symbol forSym = match(LexicalUnit.FOR);
        currentTreeLevel.addChild(new SymbolNode(forSym));
        Symbol varSym = match(LexicalUnit.VARNAME);
        currentTreeLevel.addChild(new SymbolNode(varSym));
        Symbol fromSym = match(LexicalUnit.FROM);
        currentTreeLevel.addChild(new SymbolNode(fromSym));
        currentTreeLevel.addChild(constrExprArith());
        currentTreeLevel.addChild(constrForSuffix());
        
        
        return currentTreeLevel;
    }
    private SymbolNode constrForSuffix() {
        Symbol next = getNextSymbol();
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<ForSuffix>"));
        if (next.getType() == LexicalUnit.BY) {
            deriv(ProductionRules.FORSUFFIX_BY);
            Symbol bySym = match(LexicalUnit.BY);
            currentTreeLevel.addChild(new SymbolNode(bySym));
            currentTreeLevel.addChild(constrExprArith());
            
        } else {
            deriv(ProductionRules.FORSUFFIX_TO);
        }
        Symbol toSym = match(LexicalUnit.TO);
        currentTreeLevel.addChild(new SymbolNode(toSym));
        currentTreeLevel.addChild(constrExprArith());
        Symbol doSym = match(LexicalUnit.DO);
        currentTreeLevel.addChild(new SymbolNode(doSym));
        SymbolNode codeTree = constrCode();
        if (codeTree != null) {
            currentTreeLevel.addChild(codeTree);
        }
        Symbol doneSym = match(LexicalUnit.DONE);
        currentTreeLevel.addChild(new SymbolNode(doneSym));
       
        return currentTreeLevel;
    }
    private SymbolNode constrPrint() {
        deriv(ProductionRules.PRINT);
        SymbolNode currentTreeLevel = new SymbolNode( new Symbol(null, "<Print>"));
        Symbol printSym = match(LexicalUnit.PRINT);
        currentTreeLevel.addChild(new SymbolNode(printSym));
        Symbol lpSym = match(LexicalUnit.LPAREN);
        currentTreeLevel.addChild(new SymbolNode(lpSym));
        
        currentTreeLevel.addChild(constrPrintSuffix());
        return currentTreeLevel;
    }
    
    private SymbolNode constrPrintSuffix() {
    		//deriv(ProductionRules.PRINTSUFFIX);
    		SymbolNode currentTreeLevel = new SymbolNode( new Symbol(null, "<PrintSuffix>"));
    		Symbol next = getNextSymbol();
    		
    		if (next.getType() == LexicalUnit.STRING) {
    			//deriv(ProductionRules.PRINTSUFFIX_STR);
    			Symbol varSym = match(LexicalUnit.STRING);
            currentTreeLevel.addChild(new SymbolNode(varSym));
           
    		} else {
    			//deriv(ProductionRules.PRINTSUFFIX_VN);
    			currentTreeLevel.addChild(constrExprArith());
    		}
    		Symbol rpSym = match(LexicalUnit.RPAREN);
        currentTreeLevel.addChild(new SymbolNode(rpSym));
    			
    		
    		return currentTreeLevel;
    }
    private SymbolNode constrRead() {
        deriv(ProductionRules.READ);
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<Read>"));
        Symbol readSym =match(LexicalUnit.READ);
        currentTreeLevel.addChild(new SymbolNode(readSym));
        Symbol lpSym =match(LexicalUnit.LPAREN);
        currentTreeLevel.addChild(new SymbolNode(lpSym));
        Symbol varSym = match(LexicalUnit.VARNAME);
        currentTreeLevel.addChild(new SymbolNode(varSym));
        Symbol rpSym =match(LexicalUnit.RPAREN);
        currentTreeLevel.addChild(new SymbolNode(rpSym));

        return currentTreeLevel;
    }
    
    
    private SymbolNode constrFuncCall() {
    		SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<FuncCall>"));
    		Symbol callSym = match(LexicalUnit.CALL);
        currentTreeLevel.addChild(new SymbolNode(callSym));
        Symbol varSym = match(LexicalUnit.VARNAME);
        currentTreeLevel.addChild(new SymbolNode(varSym));
        Symbol lpSym = match(LexicalUnit.LPAREN);
        currentTreeLevel.addChild(new SymbolNode(lpSym));
        currentTreeLevel.addChild(constrFuncCallSuffix(varSym));
        return currentTreeLevel;
    }
    
    /**
     * @param func a Symbol of type VARNAME
     * @return
     */
    private SymbolNode constrFuncCallSuffix(Symbol func) {
    		Symbol next = getNextSymbol();
        SymbolNode currentTreeLevel = new SymbolNode(new Symbol(null, "<FuncCallSuffix>"));
        if (next.getType() == LexicalUnit.RPAREN) {
            Symbol rpSym = match(LexicalUnit.RPAREN);
            currentTreeLevel.addChild(new SymbolNode(rpSym));
        } else {
        		SymbolNode astFuncParamExprArith;
        		if (next.getType() == LexicalUnit.STRING) {
        			astFuncParamExprArith = new SymbolNode(match(LexicalUnit.STRING));
        		} else {
        			astFuncParamExprArith = constrExprArith();
        		}
        		
            currentTreeLevel.addChild(astFuncParamExprArith);
            // TODO: support Strings
           // IdentifierType funcType = getVariableType(func);
            //funcType.set
            //this.setVariableType(func, type);
            currentTreeLevel.addChild(constrFuncCallSuffix(func));
        }
        return currentTreeLevel;
    }

}
