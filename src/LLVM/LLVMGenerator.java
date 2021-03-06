package LLVM;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import Parser.IdentifierTableNode;
import Parser.SymbolNode;
import Lexer.LexicalUnit;

/**
 * Generates the LLVM IR code from a valid AST.
 * No errors/incoherences are handled here. If the AST is malformed, 
 * the program will probably just crash with a random error.
 */
public class LLVMGenerator {
	
	/**
	 * Indicates if there's a call of the printInt function if the program 
	 * and thus if the declaration of the printInt function has
	 * to be included in the final LLVM IR code.
	 */
	private boolean printIntUsed = false;
	/**
	 * Indicates if there's a call of the scanf function if the program 
	 * and thus if the declaration of the scanf function has
	 * to be included in the final LLVM IR code.
	 */
	private boolean readUsed = false;
	
	/**
	 * Indicates if there's a call to the C printf function and thus if 
	 * its declaration is necessary in the generated LLVM IR code.
	 */
	private boolean printfUsed = false;
	
	/**
	 * The program that will be generated.
	 */
	private LLVMProgram program = new LLVMProgram();
	
	/**
	 * Converts the specified AST into compilable LLVM IR code.
	 * @param ast a valid Imp AST (generated from the parser tree by the class AST)
	 * @param identifiersTable The list of identifiers detected by the lexer
	 * @return the LLVM IR code corresponding to the specified AST
	 */
	public String generateFromAST(SymbolNode ast, IdentifierTableNode identifiersTable) {
		if (ast.getSymbol().getValue().toString() != "Start") {
			return "";
		}
		
		int i = 0;
		for (i = 0; i < ast.children().size()-1; i++) {
			SymbolNode funcNode = ast.children().get(i);
			LLVMFunction func = generateFunc(funcNode, identifiersTable.children().get(i));

			program.addBlock(func);
		}
		
		
		LLVMFunction main = LLVMFunction.createMain();
		

		LLVMBasicGroup entryGroup = new LLVMBasicGroup("entry");
		generateAllocationsForIdTable(identifiersTable.children().get(i), entryGroup, new ArrayList<String>());
		main.addBlock(entryGroup);
		LLVMBasicGroup lastGroup;
		if (ast.children().size() > 0) {
			lastGroup = this.generateInstList(ast.children().get(i), entryGroup, main);
		} else {
			lastGroup = entryGroup;
		}
		lastGroup.addInstruction(LLVMInstruction.createReturnInt("0"));
		
		program.addBlock(main);

		String result = this.generateNecessaryImports();
		result += this.program.generate();
		return result;
	} 
	
	/**
	 * @param astFuncNode the func node from the Imp AST (LexicalUnit = FUNC)
	 * @param identifiersTable the identifier table (with all its children) for the function.
	 * @return An LLVMFunction instance capable of producing the LLVM code
	 */
	public LLVMFunction generateFunc(SymbolNode astFuncNode, IdentifierTableNode identifiersTable) {
		LLVMFunction func = new LLVMFunction("i32", "@" + astFuncNode.children().get(0).getSymbol().getValue().toString());
		LLVMBasicGroup entryGroup = new LLVMBasicGroup("entry");
		generateAllocationsForIdTable(identifiersTable, entryGroup, new ArrayList<String>()); 
		func.addBlock(entryGroup);
		
		SymbolNode funcParamNode = astFuncNode.children().get(1);
		for (SymbolNode paramNode: funcParamNode.children()) {
			func.addParam("%" + paramNode.getSymbol().getValue().toString() + "v", "i32");
			entryGroup.addInstruction(LLVMInstruction.createIntAssignation("%" + paramNode.getSymbol().getValue().toString(), "%" + paramNode.getSymbol().getValue().toString() + "v"));
		}
		
		SymbolNode funcInNode = astFuncNode.children().get(2);
		LLVMBasicGroup lastGroup = entryGroup;
		if (funcInNode.children().size() > 0 && funcInNode.children().get(0).getSymbol().getValue().equals("<InstList>")) {
			lastGroup = this.generateInstList(funcInNode.children().get(0), entryGroup, func);
		}
		String rtnValue = "0";
		if (astFuncNode.children().size() == 5) {
			// There's a return stmt
			if (astFuncNode.children().get(3).children().get(0).getSymbol().getType() == LexicalUnit.STRING) {
				String strValue = astFuncNode.children().get(3).children().get(0).getSymbol().getValue().toString();
				func.setRtnType("[" + Integer.toString(strValue.length()+1) + " x i8]");
				//String strVarName = this.program.addStringLitteral(ast.children().get(0).getSymbol().getValue().toString());
				//group.addInstruction(LLVMInstruction.createStrPrint(strVarName, ast.children().get(0).getSymbol().getValue().toString().length() + 1));
			
				rtnValue = this.generateExprArithm(astFuncNode.children().get(3).children().get(0), lastGroup, func);
			} else {
				rtnValue = this.generateExprArithm(astFuncNode.children().get(3).children().get(0), lastGroup, func);
			}
			
		}

		lastGroup.addInstruction(LLVMInstruction.createReturnInt(rtnValue));

		return func;
	}

	/**
	 * Add the allocation instructions for each specified identifier in the specified group.
	 * @param identifiersTable The list of identifiers detected by the lexer
	 * @param group The group to add the instructions in.
	 * @param alreadyDeclared Used to avoid double declaration (same variable could be declared in several scopes)
	 */
	private void generateAllocationsForIdTable(IdentifierTableNode identifiersTable, LLVMBasicGroup group, List<String> alreadyDeclared) {
		for(Entry<String, Parser.IdentifierTableEntry> identifier : identifiersTable.getTable().getTable().entrySet()) {
			if (!alreadyDeclared.contains(identifier.getKey())) {
				group.addInstruction(LLVMInstruction.createIntAllocation("%" + identifier.getKey()));
				alreadyDeclared.add(identifier.getKey());
			}
		}
		for (IdentifierTableNode child: identifiersTable.children()) {
			generateAllocationsForIdTable(child, group, alreadyDeclared);
		}
	} 
	

	/**
	 * @return The LLVM IR code if the imports (scanf and printf declarations)
	 */
	private String generateNecessaryImports() {
		String result = "";
		if (this.printIntUsed) {
			result += LLVMUtilsFunctions.PRINTINT;
			this.printfUsed = true;
		}
		if (this.printfUsed) {
			result += LLVMUtilsFunctions.PRINTF;
		}
		if (this.readUsed) {
			result += LLVMUtilsFunctions.SCANF;
		}
		return result;
	}


	/**
	 * @param ast a valid {@literal <InstList> } AST node 
	 * @param entryGroup the group to add the generated instruction in
	 * @param parentFunction the function containing the entryGroup and in which to add the eventual new ones.
	 * @return the last group instructions have been added in. 
	 * 			It can be different from the entryGroup if the instruction 
	 * 			in the specified node alter the execution flow. 
	 */
	private LLVMBasicGroup generateInstList(SymbolNode ast, LLVMBasicGroup entryGroup, LLVMFunction parentFunction) {
		LLVMBasicGroup group = entryGroup;
		for (SymbolNode instructionNode: ast.children()) {
			group = this.generateInstruction(instructionNode, group, parentFunction);
		}
		return group;
	}


	/**
	 * @param ast a valid {@literal <Instruction> } AST node 
	 * @param group the group to add the generated instruction in
	 * @param parentFunction the function containing the entryGroup and in which to add the eventual new ones.
	 * @return the last group instructions have been added in. 
	 * 			It can be different from the entryGroup if the instruction 
	 * 			in the specified node alter the execution flow. 
	 */
	private LLVMBasicGroup generateInstruction(SymbolNode ast, LLVMBasicGroup group, LLVMFunction parentFunction) {
		LLVMBasicGroup nextGroup = group;
		if (ast.getSymbol().getValue().toString().equals(":=")) {
			this.generateAssignation(ast, group, parentFunction);
		} else if (ast.getSymbol().getType() == LexicalUnit.PRINT) {
			this.generatePrint(ast, group, parentFunction);
		} else if (ast.getSymbol().getType() == LexicalUnit.READ) {
			this.generateRead(ast, group, parentFunction);
		} else if (ast.getSymbol().getType() == LexicalUnit.IF) {
			nextGroup = this.generateIf(ast, group, parentFunction);
		} else if (ast.getSymbol().getType() == LexicalUnit.WHILE) {
			nextGroup = this.generateWhile(ast, group, parentFunction);
		} else if (ast.getSymbol().getType() == LexicalUnit.FOR) {
			nextGroup = this.generateFor(ast, group, parentFunction);
		} else if (ast.getSymbol().getType() == LexicalUnit.CALL) {
			this.generateFuncCall(ast, group, parentFunction);
		}
		else {
			System.out.println("Unexpected instruction: " + ast.getSymbol().getValue().toString());
		
		}
		return nextGroup;
	}


	/**
	 * @param ast a valid {@literal <ExprArith> } AST node 
	 * @param group group the group to add the generated instruction in
	 * @param parentFunction parentFunction the function containing the entryGroup and in which to add the eventual new ones.
	 */
	private void generateAssignation(SymbolNode ast, LLVMBasicGroup group, LLVMFunction parentFunction) {
		String varName = "%" + ast.children().get(0).getSymbol().getValue().toString();
		if (ast.children().get(1).getSymbol().getType() == LexicalUnit.CALL) {
			String value = this.generateFuncCall(ast.children().get(1), group, parentFunction);
			group.addInstruction(LLVMInstruction.createIntAssignation(varName, value));
		} else {
			String value = this.generateExprArithm(ast.children().get(1), group, parentFunction);
			group.addInstruction(LLVMInstruction.createIntAssignation(varName, value));
		}
		
	}
	
	/**
	 * @param ast
	 * @param group
	 * @param parentFunction
	 * @return the name of the temporary variable where the result produced by the function is stored
	 */
	private String generateFuncCall(SymbolNode ast, LLVMBasicGroup group, LLVMFunction parentFunction) {
		ArrayList<String> params = new ArrayList<String>();
		
		for (SymbolNode child: ast.children().get(1).children()) {
			params.add(this.generateExprArithm(child, group, parentFunction));
		}
		String tempVarName = parentFunction.newTempVar();
		group.addInstruction(LLVMInstruction.createIntFuncCall(tempVarName, ast.children().get(0).getSymbol().getValue().toString(), params));
		return tempVarName;
	}

	
	/**
	 * @param ast a valid AST node with the symbol of type IF in it
	 * @param group the group to add the generated instruction in
	 * @param parentFunction parentFunction the function containing the entryGroup and in which to add the eventual new ones.
	 * @return the last group instructions have been added in. 
	 * 			It can be different from the entryGroup if the instruction 
	 * 			in the specified node alter the execution flow. 
	 * 			It's the group that to add the next instruction to.
	 */
	private LLVMBasicGroup generateIf(SymbolNode ast, LLVMBasicGroup group, LLVMFunction parentFunction) {
		
		// generate the condition in the if
		String boolVar = this.generateCond(ast.children().get(0), group, parentFunction);

		// Create the group to jump to if the condition is true
		LLVMBasicGroup ifGroup = new LLVMBasicGroup(parentFunction.newGroupName("if"));
		parentFunction.addBlock(ifGroup);
		LLVMBasicGroup lastIfGroup = ifGroup;
		if (ast.children().size() > 1 && ast.children().get(1).getSymbol().getValue().equals("<InstList>")) {
			lastIfGroup = this.generateInstList(ast.children().get(1), ifGroup, parentFunction);
		}
		

		// generate the group to jump to when the code in the if or the else is executed.
		LLVMBasicGroup endifGroup = new LLVMBasicGroup(parentFunction.newGroupName("endif"));
		

				
		// Create the jump instruction that goes to the endifGroup
		LLVMInstruction unconditionnalJump = LLVMInstruction.createUnConditionalJump(endifGroup.getName());
		lastIfGroup.addInstruction(unconditionnalJump);
		
		// Create (and fill) the group to jump to if the condition is false (if there's a else)
		LLVMBasicGroup elseGroup = null;
		if (ast.children().size() == 3) {
			elseGroup = new LLVMBasicGroup(parentFunction.newGroupName("else"));
			if (elseGroup != null) {
				parentFunction.addBlock(elseGroup);
			}
			
			LLVMBasicGroup lastEndIfGroup = this.generateInstList(ast.children().get(2), elseGroup, parentFunction);
			lastEndIfGroup.addInstruction(unconditionnalJump);
			group.addInstruction(LLVMInstruction.createConditionalJump(boolVar, ifGroup.getName(), elseGroup.getName()));
		} else if (ast.children().size() == 2) {
			group.addInstruction(LLVMInstruction.createConditionalJump(boolVar, ifGroup.getName(), endifGroup.getName()));
		}
		
		parentFunction.addBlock(endifGroup);
		
		
		return endifGroup;

	}
	
	/**
	 * @param ast a valid AST node with the symbol of type WHILE in it
	 * @param group the group to add the generated instruction in
	 * @param parentFunction parentFunction the function containing the entryGroup and in which to add the eventual new ones.
	 * @return the last group instructions have been added in, which is different 
	 * 			from the entryGroup since the execution flow is modified.
	 * 			It's the group that to add the next instruction to.
	 */
	private LLVMBasicGroup generateWhile(SymbolNode ast, LLVMBasicGroup group, LLVMFunction parentFunction) {
		
		// Create the group evaluating the condition
		LLVMBasicGroup condWhileGroup = new LLVMBasicGroup(parentFunction.newGroupName("condwhile"));
		parentFunction.addBlock(condWhileGroup);
		
		// Generate the condition
		String boolVar = this.generateCond(ast.children().get(0), condWhileGroup, parentFunction);
		
		// Create the group containing the code inside the while
		LLVMBasicGroup whileGroup = new LLVMBasicGroup(parentFunction.newGroupName("while"));
		parentFunction.addBlock(whileGroup);
				
				
		// Create the group to jump to when leaving the loop.
		LLVMBasicGroup doneGroup = new LLVMBasicGroup(parentFunction.newGroupName("done"));
		


		
		
		// Generates the instructions inside the loop and retrieve the group it ends in.
		LLVMBasicGroup endOfWhileGroup = whileGroup;
		if (ast.children().size() > 1 && ast.children().get(1).getSymbol().getValue().toString().equals("<InstList>")) {
			endOfWhileGroup = this.generateInstList(ast.children().get(1), whileGroup, parentFunction);
			
		}
		// At the end of the loop, jump in the condition evaluation code
		endOfWhileGroup.addInstruction(LLVMInstruction.createUnConditionalJump(condWhileGroup.getName()));

		// Generates the code in the condition
		condWhileGroup.addInstruction(LLVMInstruction.createConditionalJump(boolVar, whileGroup.getName(), doneGroup.getName()));
		
		
		// Jumps in the condition for the first time
		group.addInstruction(LLVMInstruction.createUnConditionalJump(condWhileGroup.getName()));
		
		parentFunction.addBlock(doneGroup);
		
		

		return doneGroup;

	}
	
	/**
	 * @param ast a valid AST node with the symbol of type FOR in it
	 * @param group the group to add the generated instruction in
	 * @param parentFunction parentFunction the function containing the entryGroup and in which to add the eventual new ones.
	 * @return the last group instructions have been added in, which is different 
	 * 			from the entryGroup since the execution flow is modified.
	 * 			It's the group that to add the next instruction to.
	 */
	private LLVMBasicGroup generateFor(SymbolNode ast, LLVMBasicGroup group, LLVMFunction parentFunction) {
		
		
		
		// In the current group, we initialize the var to the "from" and jump inside the incForGroup
		String fromVar = this.generateExprArithm(ast.children().get(1), group, parentFunction);
		

		// Create the group incrementing the variable and evaluting the condition
		LLVMBasicGroup incForGroup = new LLVMBasicGroup(parentFunction.newGroupName("incfor"));
		
		parentFunction.addBlock(incForGroup);

		// Create the group containing the code to execute in each loop
		LLVMBasicGroup forGroup = new LLVMBasicGroup(parentFunction.newGroupName("for"));
		
		parentFunction.addBlock(forGroup);
		
		// Create the bloc to jump to at the end of the loop 
		LLVMBasicGroup endforGroup = new LLVMBasicGroup(parentFunction.newGroupName("endfor"));


		// if (by>=0 and a <= to) or (by<=0 and a>=to) then jmp -> forGroup else jmp endforGroup
		String byVar = this.generateExprArithm(ast.children().get(2), incForGroup, parentFunction);
		String counterVar = "%" + ast.children().get(0).getSymbol().getValue().toString();
		String toVar = this.generateExprArithm(ast.children().get(3), incForGroup, parentFunction);
		
		String loadedCounterVar = parentFunction.newTempVar();
		incForGroup.addInstruction(LLVMInstruction.createIntLoad(loadedCounterVar, counterVar));
		String isByPositiveVar = parentFunction.newTempVar();
		incForGroup.addInstruction(LLVMInstruction.createComparison(LLVMInstruction.ComparisonOperation.SGE, isByPositiveVar, byVar, "0"));
		String isByNegativeVar = parentFunction.newTempVar();
		incForGroup.addInstruction(LLVMInstruction.createComparison(LLVMInstruction.ComparisonOperation.SLE, isByNegativeVar, byVar, "0"));
		
		String aSmallerThanToVar = parentFunction.newTempVar();
		incForGroup.addInstruction(LLVMInstruction.createComparison(LLVMInstruction.ComparisonOperation.SLE, aSmallerThanToVar, loadedCounterVar, toVar));
		String aBiggerThanToVar = parentFunction.newTempVar();
		incForGroup.addInstruction(LLVMInstruction.createComparison(LLVMInstruction.ComparisonOperation.SGE, aBiggerThanToVar, loadedCounterVar, toVar));
		
		String firstAndVar = parentFunction.newTempVar();
		incForGroup.addInstruction(LLVMInstruction.createBitwiseOperation(LLVMInstruction.BitwiseOperation.AND, firstAndVar, isByPositiveVar, aSmallerThanToVar));
		String secondAndVar = parentFunction.newTempVar();
		incForGroup.addInstruction(LLVMInstruction.createBitwiseOperation(LLVMInstruction.BitwiseOperation.AND, secondAndVar, isByNegativeVar, aBiggerThanToVar));
		String continueForVar = parentFunction.newTempVar();
		incForGroup.addInstruction(LLVMInstruction.createBitwiseOperation(LLVMInstruction.BitwiseOperation.OR, continueForVar, firstAndVar, secondAndVar));
		
		incForGroup.addInstruction(LLVMInstruction.createConditionalJump(continueForVar, forGroup.getName(), endforGroup.getName()));

		// Add the instructions in the code group
		LLVMBasicGroup endOfForGroup = forGroup;
		if (ast.children().size() >= 5 && ast.children().get(4).getSymbol().getValue().toString().equals("<InstList>")) {
			endOfForGroup = this.generateInstList(ast.children().get(4), forGroup, parentFunction);
			
		}
		
		// Increment the counter at the end of the code in the for
		String endLoadedCounterVar = parentFunction.newTempVar();
		endOfForGroup.addInstruction(LLVMInstruction.createIntLoad(endLoadedCounterVar, counterVar));
		String newCounterValueVar = parentFunction.newTempVar();
		endOfForGroup.addInstruction(LLVMInstruction.createAddtion(newCounterValueVar, endLoadedCounterVar, byVar));
		endOfForGroup.addInstruction(LLVMInstruction.createIntAssignation(counterVar, newCounterValueVar));
		endOfForGroup.addInstruction(LLVMInstruction.createUnConditionalJump(incForGroup.getName())); // At the end of the code inside the for, jump in the condition


		group.addInstruction(LLVMInstruction.createIntAssignation(counterVar, fromVar));
		group.addInstruction(LLVMInstruction.createUnConditionalJump(incForGroup.getName()));
		
		parentFunction.addBlock(endforGroup);
		
		return endforGroup;
	}

	/**
	 * @param ast a valid AST node with the symbol (NUMBER, VARNAME, *, /, -, +)
	 * @param group the group to add the generated instructions in
	 * @param parentFunction parentFunction the function containing the entryGroup and in which to add the eventual new ones.
	 * @return the name of the variable containing the i32 result of the operation
	 */
	private String generateExprArithm(SymbolNode ast, LLVMBasicGroup group, LLVMFunction parentFunction) {
		if (ast.getSymbol().getType() == LexicalUnit.NUMBER) {
			return ast.getSymbol().getValue().toString();
		} else if (ast.getSymbol().getType() == LexicalUnit.VARNAME) {
			String tempVarname = parentFunction.newTempVar();
			group.addInstruction(LLVMInstruction.createIntLoad(tempVarname, "%" + ast.getSymbol().getValue().toString()));
			return tempVarname;
		} else if (ast.getSymbol().getValue().toString().equals("*")) {
			return this.generateMultiplication(ast, group, parentFunction);
		} else if (ast.getSymbol().getValue().toString().equals("/")) {
			return this.generateDivision(ast, group, parentFunction);
		} else if (ast.getSymbol().getValue().toString().equals("%")) {
			return this.generateRemainer(ast, group, parentFunction);
		} else if (ast.getSymbol().getValue().toString().equals("+")) {
			return this.generateAddition(ast, group, parentFunction);
		} else if (ast.getSymbol().getValue().toString().equals("-")) {
			if (ast.children().size() == 1) {
				return this.generateOposite(ast, group, parentFunction);
			} else if (ast.children().size() == 2) { 
				return this.generateSubstraction(ast, group, parentFunction);
			} else {
				System.out.println("Unexpected number of children for a minus operation");
				return "";
			}
		} else {
			System.out.println("Unexpected arithmetic operation: " + ast.getSymbol().getValue().toString());
			return "";
		}
	}

	/**
	 * @param op a supported arithmetic operator (see LLVMInstruction.ArithmeticOperation)
	 * @param ast a valid AST node with the symbol being an arithmetic operator (*,/,-,+) and having two children.
	 * @param group the group to add the generated instructions in
	 * @param parentFunction parentFunction the function containing the entryGroup and in which to add the eventual new ones.
	 * @return the name of the variable containing the i32 result of the operation
	 */
	private String generateArithmeticOperation(String op, SymbolNode ast, LLVMBasicGroup group, LLVMFunction parentFunction) {
		String leftHandside = this.generateExprArithm(ast.children().get(0), group, parentFunction);
		String rightHandside = this.generateExprArithm(ast.children().get(1), group, parentFunction);
		String tempVarname = parentFunction.newTempVar();
		LLVMInstruction inst = LLVMInstruction.createArthmeticOperation(
			op, 
			tempVarname, 
			leftHandside,
			rightHandside
		);
		group.addInstruction(inst);
		return tempVarname;
	}
	
	/**
	 * @param ast a valid AST node with the symbol being a - exactly one child.
	 * @param group the group to add the generated instructions in
	 * @param parentFunction parentFunction the function containing the entryGroup and in which to add the eventual new ones.
	 * @return the name of the variable containing the i32 result of the operation
	 */
	private String generateOposite(SymbolNode ast, LLVMBasicGroup group, LLVMFunction parentFunction) {
		String leftHandside = this.generateExprArithm(ast.children().get(0), group, parentFunction);
		String rightHandside = "-1";
		
		String tempVarname = parentFunction.newTempVar();
		group.addInstruction(LLVMInstruction.createArthmeticOperation(LLVMInstruction.ArithmeticOperation.MUL, tempVarname, leftHandside, rightHandside));
		return tempVarname; 
	}


	private String generateMultiplication(SymbolNode ast, LLVMBasicGroup group, LLVMFunction parentFunction) {
		return this.generateArithmeticOperation(LLVMInstruction.ArithmeticOperation.MUL, ast, group, parentFunction);
	}
	private String generateDivision(SymbolNode ast, LLVMBasicGroup group, LLVMFunction parentFunction) {
		return this.generateArithmeticOperation(LLVMInstruction.ArithmeticOperation.DIV, ast, group, parentFunction);
	}
	private String generateAddition(SymbolNode ast, LLVMBasicGroup group, LLVMFunction parentFunction) {
		return this.generateArithmeticOperation(LLVMInstruction.ArithmeticOperation.ADD, ast, group, parentFunction);
	}
	private String generateSubstraction(SymbolNode ast, LLVMBasicGroup group, LLVMFunction parentFunction) {
		return this.generateArithmeticOperation(LLVMInstruction.ArithmeticOperation.SUB, ast, group, parentFunction);
	}
	private String generateRemainer(SymbolNode ast, LLVMBasicGroup group, LLVMFunction parentFunction) {
		return this.generateArithmeticOperation(LLVMInstruction.ArithmeticOperation.REM, ast, group, parentFunction);
	}
	
	/**
	 * @param op a supported bitwise operator (see LLVMInstruction.BitwiseOperation)
	 * @param ast a valid AST node with the symbol beeing a bitwise operator (AND/OR/NOT) and having two children.
	 * @param group the group to add the generated instructions in
	 * @param parentFunction parentFunction the function containing the entryGroup and in which to add the eventual new ones.
	 * @return the name of the variable containing the bool result of the operation
	 */
	private String generateBitwiseOperation(String op, SymbolNode ast, LLVMBasicGroup group, LLVMFunction parentFunction) {
		String leftHandside = this.generateCond(ast.children().get(0), group, parentFunction);
		String rightHandside = this.generateCond(ast.children().get(1), group, parentFunction);
		
		
		String tempVarname = parentFunction.newTempVar();
		group.addInstruction(LLVMInstruction.createBitwiseOperation(op, tempVarname, leftHandside, rightHandside));
		return tempVarname;
	}
	
	/**
	 * @param ast a valid AST node with the symbol with a valid type: either a bitwise operator (AND/OR/NOT) or a comparison operator.
	 * @param group the group to add the generated instructions in
	 * @param parentFunction parentFunction the function containing the entryGroup and in which to add the eventual new ones.
	 * @return the name of the variable containing the bool result of the operation
	 */
	private String generateCond(SymbolNode ast, LLVMBasicGroup group, LLVMFunction parentFunction) {
		if (ast.getSymbol().getType() == LexicalUnit.AND) {
			return this.generateBitwiseOperation(LLVMInstruction.BitwiseOperation.AND, ast, group, parentFunction);
		} else if (ast.getSymbol().getType() == LexicalUnit.OR) {
			return this.generateBitwiseOperation(LLVMInstruction.BitwiseOperation.OR, ast, group, parentFunction);
		} else if (ast.getSymbol().getType() == LexicalUnit.NOT) {
			return this.generateNot(ast, group, parentFunction);
		} else {
			String op = "";
			if (ast.getSymbol().getType() == LexicalUnit.EQ) {
				op = LLVMInstruction.ComparisonOperation.EQ;
			} else if (ast.getSymbol().getType() == LexicalUnit.NEQ) {
				op = LLVMInstruction.ComparisonOperation.NEQ;
			} else if (ast.getSymbol().getType() == LexicalUnit.LT) {
				op = LLVMInstruction.ComparisonOperation.SLT;
			} else if (ast.getSymbol().getType() == LexicalUnit.LEQ) {
				op = LLVMInstruction.ComparisonOperation.SLE;
			} else if (ast.getSymbol().getType() == LexicalUnit.GT) {
				op = LLVMInstruction.ComparisonOperation.SGT;
			} else if (ast.getSymbol().getType() == LexicalUnit.GEQ) {
				op = LLVMInstruction.ComparisonOperation.SGE;
			} else {
				System.out.println("I've no idea what that operator is: " + ast.getSymbol().getType().toString());
				return null;
			}
			return this.generateComparison(op, ast, group, parentFunction);

		} 
	}
	
	
	/**
	 * @param ast a valid AST node with the symbol of type NOT in it
	 * @param group the group to add the generated instructions in
	 * @param parentFunction parentFunction the function containing the entryGroup and in which to add the eventual new ones.
	 * @return the name of the variable containing the bool result of the operation
	 */
	private String generateNot(SymbolNode ast, LLVMBasicGroup group, LLVMFunction parentFunction) {
		String beforeNotVar = this.generateCond(ast.children().get(0), group, parentFunction);
		String notVar = parentFunction.newTempVar();
		group.addInstruction(LLVMInstruction.createNot(notVar, beforeNotVar));
		return notVar;
	}
	
	/**
	 * @param op a supported comparison operator (see LLVMInstruction.ComparisonOperation)
	 * @param ast a valid AST node with the symbol with a type of one of the possible comparison operators.
	 * @param group the group to add the generated instructions in
	 * @param parentFunction parentFunction the function containing the entryGroup and in which to add the eventual new ones.
	 * @return the name of the variable containing the bool result of the operation
	 */
	private String generateComparison(String op, SymbolNode ast, LLVMBasicGroup group, LLVMFunction parentFunction) {
		String leftHandside = this.generateExprArithm(ast.children().get(0), group, parentFunction);
		String rightHandside = this.generateExprArithm(ast.children().get(1), group, parentFunction);
		
		String tempVarname = parentFunction.newTempVar();
		group.addInstruction(LLVMInstruction.createComparison(op, tempVarname, leftHandside, rightHandside));
		return tempVarname;
	}



	/**
	 * @param ast a valid AST node with the symbol of type PRINT in it
	 * @param group the group to add the generated instructions in
	 * @param parentFunction parentFunction the function containing the entryGroup and in which to add the eventual new ones.
	 */
	private void generatePrint(SymbolNode ast, LLVMBasicGroup group, LLVMFunction parentFunction) {
		if (ast.children().get(0).getSymbol().getType() == LexicalUnit.STRING) {
			this.printfUsed = true;
			parentFunction.newTempVar();
			String strVarName = this.program.addStringLitteral(ast.children().get(0).getSymbol().getValue().toString());
			group.addInstruction(LLVMInstruction.createStrPrint(strVarName, ast.children().get(0).getSymbol().getValue().toString().length() + 1));
		//} else if (ast.children().get(0).getSymbol().getType() == LexicalUnit.VARNAME
		//		&& this.ta) {
			// TODO: current scope
		} else {
			this.printIntUsed = true;
			String tempVarname = generateExprArithm(ast.children().get(0), group, parentFunction);
			group.addInstruction(LLVMInstruction.createIntPrint(tempVarname));
		}
		
	}
	/**
	 * @param ast a valid AST node with the symbol of type READ in it
	 * @param group the group to add the generated instructions in
	 * @param parentFunction parentFunction the function containing the entryGroup and in which to add the eventual new ones.
	 */
	private String generateRead(SymbolNode ast, LLVMBasicGroup group, LLVMFunction parentFunction) {
		this.readUsed = true;
		String tempVarname = parentFunction.newTempVar();
		group.addInstruction(LLVMInstruction.createRead(tempVarname));
		group.addInstruction(LLVMInstruction.createIntAssignation("%" + ast.children().get(0).getSymbol().getValue().toString(), tempVarname));
		return tempVarname;
	}


}