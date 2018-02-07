package AST;
import Lexer.Symbol;
import Parser.SymbolNode;

import Lexer.LexicalUnit;

/**
 * A SymbolNode with exactly two children.
 */
class BinSymbolNode extends SymbolNode{
	
	
	/**
	 * @param sym The symbol that the node will carry
	 * @param left The first child of the node
	 * @param right The second child of the node
	 */
	public BinSymbolNode(Symbol sym, SymbolNode left, SymbolNode right) {
		super(sym);
		this.addChild(left);
		this.addChild(right);
	}

	public SymbolNode getLeftChild() {
		return this.children().get(0);
	}
	public SymbolNode getRightChild() {
		return this.children().get(1);
	}

	public void setLeftChild(SymbolNode child) {
		this.children.set(0, child);
	}
	public void setRightChild(SymbolNode child) {
		this.children.set(1, child);
	}
}
/**
 * Parser tree to AST converter.
 */
public class AST {
	private SymbolNode astree = null;
	
	public SymbolNode getAST() {
		return this.astree;
	}
	

	
	/**
	 * @param tree The parser tree (produced by a Parser) 
	 * @throws ASTException When the parser tree is malformed 
	 * 			(i.e. a node doesn't have the expected number 
	 * 			of chidren or the expected type of children).
	 */
	public void buildASTFromParserTree(SymbolNode tree) throws ASTException {
		if (tree.getSymbol().getType() != null && tree.getSymbol().getValue() != "<S>") {
			System.out.println("The parser tree should start with <S>");
			return;
		}
		this.astree = new SymbolNode(new Symbol(null, "Start"));
		int i = 0;
		for (i = 0; i < tree.children().size()-1; i++) {
			this.astree.addChild(this.parseFunc(tree.children().get(i)));
		}
		SymbolNode programNode = tree.children().get(i);
		this.parseBegin(programNode.children().get(0));
		this.astree.addChild(this.parseCode(programNode.children().get(1)));
		this.parseEnd(programNode.children().get(2));

	} 
	
	/**
	 * Checks if the type of a node matches the specified type. Throws an exception if it's not the case.
	 * @param node The node to which to check the type
	 * @param expectedType The type the specified node is expected to be
	 * @throws ASTException If the types don't match.
	 */
	private void checkTypeOfNode(SymbolNode node, LexicalUnit expectedType) throws ASTException {
		if (node.getSymbol().getType() != expectedType) {
			String type = "null";
			String expectedTypeStr = "null";
			if (node.getSymbol().getType() != null) {
				type = node.getSymbol().getType().toString();
			}
			if (expectedType != null) {
				expectedTypeStr = expectedType.toString();
			}
			throw new ASTException(String.format("type Error: %s found, %s expected", type, expectedTypeStr)); // TODO: explicit error
		}
	}
	
	/**
	 * Checks if the value of a node matches the specified value. Throws an exception if it's not the case.
	 * @param node The node to which to check the value
	 * @param expectedValue The value the specified node is expected to have
	 * @throws ASTException If the values don't match.
	 */
	private void checkValueOfNode(SymbolNode node, String expectedValue) throws ASTException {
		if (!node.getSymbol().getValue().toString().equals(expectedValue)) {
			throw new ASTException(String.format("value Error: %s found, %s expected", node.getSymbol().getValue().toString(), expectedValue)); // TODO: explicit error
		}
	}
	
	/**
	 * @param beginNode a SymbolNode of type BEGIN
	 * @throws ASTException if the specified node isn't of type BEGIN
	 */
	private void parseBegin(SymbolNode beginNode) throws ASTException {
		this.checkTypeOfNode(beginNode, LexicalUnit.BEGIN);
		if (!(beginNode.children().size() == 0)) {
			throw new ASTException("Begin error");
		}
	}
	/**
	 * @param endNode a SymbolNode of type END
	 * @throws ASTException if the specified node isn't of type END
	 */
	private void parseEnd(SymbolNode endNode) throws ASTException {
		this.checkTypeOfNode(endNode, LexicalUnit.END);
		if (!(endNode.children().size() == 0)) {
			throw new ASTException("End error");
		}
	}
	
	
	
	/**
	 * @param codeNode a SymbolNode with the value "{@literal <Code> }"
	 * @throws ASTException if the specified node hasn't the expected value.
	 * @return The created AST node
	 */
	private SymbolNode parseCode(SymbolNode codeNode) throws ASTException {
		this.checkTypeOfNode(codeNode, null);
		this.checkValueOfNode(codeNode, "<Code>");
		if (codeNode.children().size() == 0) {
			return null;
		}
		if (codeNode.children().size() > 1) {
			throw new ASTException("Error: <Code> should have only one children of type <InstList>");
		}

		return parseInstList(codeNode.children().get(0));

	}

	private SymbolNode parseInstList(SymbolNode instListNode) throws ASTException {
		this.checkTypeOfNode(instListNode, null);
		this.checkValueOfNode(instListNode, "<InstList>");
		SymbolNode astInstListNode = new SymbolNode(instListNode.getSymbol());
		astInstListNode.addChild(this.parseInstruction(instListNode.children().get(0)));
		if (instListNode.children().size() == 2) {
			astInstListNode = this.parseInstListSuffix(instListNode.children().get(1), astInstListNode); 
		}
		return astInstListNode;
	}
	private SymbolNode parseInstListSuffix(SymbolNode instListSuffixNode, SymbolNode parentInstList) throws ASTException {
		this.checkTypeOfNode(instListSuffixNode, null);
		this.checkValueOfNode(instListSuffixNode, "<InstListSuffix>");
		SymbolNode childInstListNode = instListSuffixNode.children().get(1);
		parentInstList.addChild(this.parseInstruction(childInstListNode.children().get(0)));
		if (childInstListNode.children().size() == 2) {
			parentInstList = this.parseInstListSuffix(childInstListNode.children().get(1), parentInstList); 
		}
		return parentInstList;
	}
	

	private SymbolNode parseInstruction(SymbolNode instructionNode) throws ASTException {
		this.checkTypeOfNode(instructionNode, null);
		this.checkValueOfNode(instructionNode, "<Instruction>");
		if (instructionNode.children().size() != 1) {
			throw new ASTException("Error: <Instruction> should have exactly one child.");
		}
		
		if (instructionNode.children().get(0).getSymbol().getValue().toString().equals("<Assign>")) {
			return this.parseAssign(instructionNode.children().get(0));
		} else if (instructionNode.children().get(0).getSymbol().getValue().toString().equals("<If>")) {
			return this.parseIf(instructionNode.children().get(0));
		} else if (instructionNode.children().get(0).getSymbol().getValue().toString().equals("<While>")) {
			return this.parseWhile(instructionNode.children().get(0));
		} else if (instructionNode.children().get(0).getSymbol().getValue().toString().equals("<For>")) {
			return this.parseFor(instructionNode.children().get(0));
		} else if (instructionNode.children().get(0).getSymbol().getValue().toString().equals("<Print>")) {
			return this.parsePrint(instructionNode.children().get(0));
		} else if (instructionNode.children().get(0).getSymbol().getValue().toString().equals("<Read>")) {
			return this.parseRead(instructionNode.children().get(0));
		} else if (instructionNode.children().get(0).getSymbol().getValue().toString().equals("<FuncCall>")) {
			return this.parseFuncCall(instructionNode.children().get(0));
		}else {
			throw new ASTException("Invalid Instruction: " + instructionNode.children().get(0).getSymbol().getValue().toString());
		}
	}
	private SymbolNode parseFunc(SymbolNode funcNode) throws ASTException {
		this.checkTypeOfNode(funcNode, null);
		this.checkValueOfNode(funcNode, "<Func>");
		if (funcNode.children().size() != 4) {
			throw new ASTException("Error: <Func> should have exactly 4 children.");
		}
		SymbolNode astFuncNode = new SymbolNode(funcNode.children().get(0).getSymbol());
		astFuncNode.addChild(new SymbolNode(funcNode.children().get(1).getSymbol())); // name of the function
		SymbolNode astWithNode = new SymbolNode(funcNode.children().get(2).getSymbol());
		
		SymbolNode astFuncSyffix = funcNode.children().get(3);
		while (astFuncSyffix.children().get(0).getSymbol().getType() == LexicalUnit.VARNAME) {
			astWithNode.addChild(new SymbolNode(astFuncSyffix.children().get(0).getSymbol()));
			astFuncSyffix = astFuncSyffix.children().get(1);
		}
		if (astFuncSyffix.children().get(0).getSymbol().getType() != LexicalUnit.IN ) {
			throw new ASTException("Error: func should end with a \"in\" and only contain variable names");
		}
		astFuncNode.addChild(astWithNode);
		SymbolNode astInNode = new SymbolNode(astFuncSyffix.children().get(0).getSymbol());
		int offset = 0;
		if (astFuncSyffix.children().get(1).getSymbol().getValue().equals("<Code>")) {
			astInNode.addChild(parseCode(astFuncSyffix.children().get(1)));
			offset = 1;
		} 
		
		astFuncNode.addChild(astInNode);
		SymbolNode rtnStmtNode = astFuncSyffix.children().get(1 + offset);
		offset = 0;
		if (rtnStmtNode.children().size() == 3) {
			SymbolNode rtnNode = new SymbolNode(rtnStmtNode.children().get(0).getSymbol());
			if (rtnStmtNode.children().get(1).getSymbol().getType() == LexicalUnit.STRING) {
				rtnNode.addChild(rtnStmtNode.children().get(1));
			} else {
				rtnNode.addChild(parseExprArith(rtnStmtNode.children().get(1)));
			}
			
			astFuncNode.addChild(rtnNode);
			offset = 2;
		} 
		
		
		astFuncNode.addChild(new SymbolNode(rtnStmtNode.children().get(offset).getSymbol()));
		
		return astFuncNode;
	}
	
	
	private SymbolNode parseFuncCall(SymbolNode funcCallNode) throws ASTException {
		this.checkTypeOfNode(funcCallNode, null);
		this.checkValueOfNode(funcCallNode, "<FuncCall>");
		if (funcCallNode.children().size() != 4) {
			throw new ASTException("Error: <FuncCall> should have exactly 4 children.");
		}
		SymbolNode callNode = funcCallNode.children().get(0);
		callNode.addChild(funcCallNode.children().get(1));
		SymbolNode argsNode = new SymbolNode(new Symbol(null, "Args"));
		SymbolNode suffixNode = funcCallNode.children().get(3);
		while (suffixNode.children().get(0).getSymbol().getType() != LexicalUnit.RPAREN) {
			argsNode.addChild(parseExprArith(suffixNode.children().get(0)));
			suffixNode = suffixNode.children().get(1);
		}
		callNode.addChild(argsNode);
		return callNode;
	}

		
	private SymbolNode parseAssign(SymbolNode assignNode) throws ASTException {
		this.checkTypeOfNode(assignNode, null);
		this.checkValueOfNode(assignNode, "<Assign>");
		if (assignNode.children().size() != 3) {
			throw new ASTException("Error: <Assign> should have exactly 3 children.");
		}

		SymbolNode astAssignNode = new SymbolNode(assignNode.children().get(1).getSymbol());

		astAssignNode.addChild(new SymbolNode(assignNode.children().get(0).getSymbol()));
		
		astAssignNode.addChild(this.parseAssignSuffix(assignNode.children().get(2)));
		return astAssignNode;
	}	
	private SymbolNode parseAssignSuffix(SymbolNode assignSuffixNode) throws ASTException {
		this.checkTypeOfNode(assignSuffixNode, null);
		this.checkValueOfNode(assignSuffixNode, "<AssignSuffix>");
		if (assignSuffixNode.children().size() != 1) {
			throw new ASTException("Error: <AssignSuffix> should have exactly 1 child.");
		}
		SymbolNode child = assignSuffixNode.children().get(0);
		if (child.getSymbol().getValue().toString().equals("<ExprArith>")) {
			return this.parseExprArith(child);
		} else if (child.getSymbol().getValue().toString().equals("<FuncCall>")) {
			return this.parseFuncCall(child);
		} else {
			SymbolNode astAssignNode = new SymbolNode(child.getSymbol());
			return astAssignNode;
		}
	}

	private SymbolNode parseExprArith(SymbolNode exprArithNode) throws ASTException {
		SymbolNode leftSide = this.parseEA1(exprArithNode.children().get(0));
		if (exprArithNode.children().size() == 1) {
			return leftSide;
		} 
		return this.parseEA2(exprArithNode.children().get(1), leftSide);
	}
	private SymbolNode parseEA1(SymbolNode ea1Node) throws ASTException {
		return this.parseProd(ea1Node.children().get(0));
	}
	private BinSymbolNode parseEA2(SymbolNode ea2Node, SymbolNode leftSide) throws ASTException {
		BinSymbolNode astEA2Node = new BinSymbolNode(ea2Node.children().get(0).getSymbol(), leftSide, null);
		astEA2Node.setRightChild(this.parseProd(ea2Node.children().get(1)));
		if (ea2Node.children().size() == 3) {
			return this.parseEA2(ea2Node.children().get(2), astEA2Node);
		} else if (ea2Node.children().size() == 2) {
			return astEA2Node;
		}
		else {
			throw new ASTException("Invalid AE2");
		}

	}
	private SymbolNode parseProd(SymbolNode prodNode) throws ASTException {
		SymbolNode leftSide = this.parseP1(prodNode.children().get(0));
		if (prodNode.children().size() == 1) {
			return leftSide;
		} 
		return this.parseP2(prodNode.children().get(1), leftSide);
	}
	private SymbolNode parseP1(SymbolNode p1Node) throws ASTException {
		return this.parseAtom(p1Node.children().get(0));
	}
	private BinSymbolNode parseP2(SymbolNode p2Node, SymbolNode leftSide) throws ASTException {
		BinSymbolNode astP2Node = new BinSymbolNode(p2Node.children().get(0).getSymbol(), leftSide, null);
		astP2Node.setRightChild(this.parseAtom(p2Node.children().get(1)));
		if (p2Node.children().size() == 3) {
			return this.parseP2(p2Node.children().get(2), astP2Node);
		} else if (p2Node.children().size() == 2) {
			return astP2Node;
		}
		else {
			throw new ASTException("Invalid P2");
		}
	}
	private SymbolNode parseAtom(SymbolNode atomNode) throws ASTException {
		if (atomNode.children().get(0).getSymbol().getType() == LexicalUnit.VARNAME) {
			// TODO: check that the variable is initialized
			return new SymbolNode(atomNode.children().get(0).getSymbol());
		} else if (atomNode.children().get(0).getSymbol().getType() == LexicalUnit.NUMBER) {
			return new SymbolNode(atomNode.children().get(0).getSymbol());
		} else if (atomNode.children().get(0).getSymbol().getType() == LexicalUnit.MINUS) {
			SymbolNode astMinusNode = new SymbolNode(atomNode.children().get(0).getSymbol());
			astMinusNode.addChild(parseAtom(atomNode.children().get(1)));
			return astMinusNode;
		} else if (atomNode.children().get(0).getSymbol().getType() == LexicalUnit.LPAREN) {
			return parseExprArith(atomNode.children().get(1));
		} else {
			throw new ASTException("Invalid Atom: " + atomNode.children().get(0).getSymbol().getValue().toString());
		}
	}

	private SymbolNode parseCond(SymbolNode condNode) throws ASTException {
		this.checkTypeOfNode(condNode, null);
		this.checkValueOfNode(condNode, "<Cond>");
		SymbolNode leftSide = this.parseC1(condNode.children().get(0));
		if (condNode.children().size() == 1) {
			return leftSide;
		} 
		return this.parseC2(condNode.children().get(1), leftSide);
	}
	
	private SymbolNode parseC1(SymbolNode c1Node) throws ASTException {
		return this.parseBoolAnd(c1Node.children().get(0));
	}
	
	private BinSymbolNode parseC2(SymbolNode c2Node, SymbolNode leftSide) throws ASTException {
		BinSymbolNode astC2Node = new BinSymbolNode(c2Node.children().get(0).getSymbol(), leftSide, null);
		astC2Node.setRightChild(this.parseBoolAnd(c2Node.children().get(1)));
		if (c2Node.children().size() == 3) {
			return this.parseC2(c2Node.children().get(2), astC2Node);
		} else if (c2Node.children().size() == 2) {
			return astC2Node;
		}
		else {
			throw new ASTException("Invalid C2");
		}

	}
	private SymbolNode parseBoolAnd(SymbolNode boolAndNode) throws ASTException {
		SymbolNode leftSide = this.parseBA1(boolAndNode.children().get(0));
		if (boolAndNode.children().size() == 1) {
			return leftSide;
		} 
		return this.parseBA2(boolAndNode.children().get(1), leftSide);
	}
	private SymbolNode parseBA1(SymbolNode ba1Node) throws ASTException {
		return this.parseBoolAtom(ba1Node.children().get(0));
	}
	private BinSymbolNode parseBA2(SymbolNode ba2Node, SymbolNode leftSide) throws ASTException {
		BinSymbolNode astBA2Node = new BinSymbolNode(ba2Node.children().get(0).getSymbol(), leftSide, null);
		astBA2Node.setRightChild(this.parseBoolAtom(ba2Node.children().get(1)));
		if (ba2Node.children().size() == 3) {
			return this.parseBA2(ba2Node.children().get(2), astBA2Node);
		} else if (ba2Node.children().size() == 2) {
			return astBA2Node;
		}
		else {
			throw new ASTException("Invalid BA2");
		}
	}
	private SymbolNode parseBoolAtom(SymbolNode boolAtomNode) throws ASTException {
		if (boolAtomNode.children().get(0).getSymbol().getType() == LexicalUnit.NOT) {
			SymbolNode notNode = new SymbolNode(boolAtomNode.children().get(0).getSymbol());
			notNode.addChild(this.parseBoolAtom(boolAtomNode.children().get(1)));
			return notNode;
		} else if (boolAtomNode.children().get(0).getSymbol().getValue().toString().equals("<ExprArith>")) {
			BinSymbolNode compSym = new BinSymbolNode(boolAtomNode.children().get(1).children().get(0).getSymbol(), 
				this.parseExprArith(boolAtomNode.children().get(0)), 
				this.parseExprArith(boolAtomNode.children().get(2))
			);
			return compSym;
		}  else {
			throw new ASTException("Invalid BoolAtom: " + boolAtomNode.children().get(0).getSymbol().getValue().toString());
		}
	}

	
	private SymbolNode parseIf(SymbolNode ifNode) throws ASTException {
		this.checkTypeOfNode(ifNode, null);
		this.checkValueOfNode(ifNode, "<If>");
		SymbolNode astIfNode = new SymbolNode(ifNode.children().get(0).getSymbol());
		astIfNode.addChild(this.parseCond(ifNode.children().get(1))); // condition for first child...
		
		int offset = 0;
		if (ifNode.children().get(3).getSymbol().getValue().equals("<Code>")) {
			astIfNode.addChild(this.parseCode(ifNode.children().get(3))); // if code for second...
			offset = 1;
		} else {
			astIfNode.addChild(new SymbolNode(new Symbol(null, "<InstList>")));
		}
		
		astIfNode = this.parseIfSuffix(ifNode.children().get(3 + offset), astIfNode); // else code for third.
		
		return astIfNode;
	}
	private SymbolNode parseIfSuffix(SymbolNode ifSuffixNode, SymbolNode parentIfNode) throws ASTException {
		this.checkTypeOfNode(ifSuffixNode, null);
		this.checkValueOfNode(ifSuffixNode, "<IfSuffix>");
		if (ifSuffixNode.children().size() == 3) { // Otherwise it should be 1 (i.e. without else)
			SymbolNode childCodeNode = ifSuffixNode.children().get(1);
			parentIfNode.addChild(this.parseCode(childCodeNode));
		}
		
		return parentIfNode;
	}



	private SymbolNode parseWhile(SymbolNode whileNode) throws ASTException {
		this.checkTypeOfNode(whileNode, null);
		this.checkValueOfNode(whileNode, "<While>");
		SymbolNode astWhileNode = new SymbolNode(whileNode.children().get(0).getSymbol());
		astWhileNode.addChild(this.parseCond(whileNode.children().get(1))); // the condition as first child
		if (whileNode.children().get(3).getSymbol().getValue().equals("<Code>")) { // If the loop isn't empty
			astWhileNode.addChild(this.parseCode(whileNode.children().get(3))); // the code as second child
		}
		
		return astWhileNode;
	}
	private SymbolNode parseFor(SymbolNode forNode) throws ASTException {
		this.checkTypeOfNode(forNode, null);
		this.checkValueOfNode(forNode, "<For>");
		SymbolNode astForNode = new SymbolNode(forNode.children().get(0).getSymbol());
		astForNode.addChild(forNode.children().get(1)); // varname as first child
		astForNode.addChild(this.parseExprArith(forNode.children().get(3))); // the "from" as second child
		astForNode = this.parseForSuffix(forNode.children().get(4), astForNode); // the other childs are add by the parseForSuffix method
		
		return astForNode;
	}
	private SymbolNode parseForSuffix(SymbolNode forSuffixNode, SymbolNode parentForNode) throws ASTException {
		this.checkTypeOfNode(forSuffixNode, null);
		this.checkValueOfNode(forSuffixNode, "<ForSuffix>");
		int offset = 0;
		if (forSuffixNode.children().get(0).getSymbol().getType() == LexicalUnit.BY) {
			parentForNode.addChild(this.parseExprArith(forSuffixNode.children().get(1)));
			offset = 2;
		} else {
			parentForNode.addChild(new SymbolNode(new Symbol(LexicalUnit.NUMBER, "1"))); // by default by=1
		}
		
		parentForNode.addChild(this.parseExprArith(forSuffixNode.children().get(1 + offset))); // the "to" fourth child
		if (forSuffixNode.children().get(3 + offset).getSymbol().getValue().equals("<Code>")) {
			parentForNode.addChild(this.parseCode(forSuffixNode.children().get(3 + offset))); // the code in the last child
		} else {
			parentForNode.addChild(new SymbolNode(new Symbol(null, "<InstList>")));
		}
		
		return parentForNode;
	}
	private SymbolNode parseRead(SymbolNode readNode) throws ASTException {
		SymbolNode astReadNode = new SymbolNode(readNode.children().get(0).getSymbol());
		astReadNode.addChild(new SymbolNode(readNode.children().get(2).getSymbol()));
		return astReadNode;
	}
	private SymbolNode parsePrint(SymbolNode printNode) throws ASTException {
		SymbolNode astPrintNode = new SymbolNode(printNode.children().get(0).getSymbol());
		astPrintNode.addChild(parsePrintSuffix(printNode.children().get(2)));
		
		return astPrintNode;
	}
	private SymbolNode parsePrintSuffix(SymbolNode printNode) throws ASTException {
		if (printNode.children().get(0).getSymbol().getValue().equals("<ExprArith>")) {
			return parseExprArith(printNode.children().get(0));
		} else {
			return new SymbolNode(printNode.children().get(0).getSymbol());
		}
	}
}














