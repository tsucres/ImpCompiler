package LLVM;
import java.util.ArrayList;


/**
 * Construct and generate an LLVM IR program, made of functions 
 * and global instructions.
 */
class LLVMProgram implements LLVMCodeGenerator {
	
	/**
	 * Contain all the constituants (mainly functions) of the program.
	 */
	private ArrayList<LLVMCodeGenerator> contentBlocks = new ArrayList<LLVMCodeGenerator>();
	/**
	 * Contains the global declaration of strings. 
	 * They are added with the method addStringLitteral.
	 */
	private ArrayList<LLVMInstruction> stringLitterals = new ArrayList<LLVMInstruction>();
	
	/**
	 * Adds the specified block in the program.
	 * @param block can be an LLVMInstruction or a LLVMFunction
	 */
	public void addBlock(LLVMCodeGenerator block) {
		this.contentBlocks.add(block);
	}
	
	
	public LLVMCodeGenerator getBlock(int index) {
		return this.contentBlocks.get(index);
	}
	
	/**
	 * @param str the string to store
	 * @return the name of the constant used to store the string
	 */
	public String addStringLitteral(String str) {
		int len = str.length() + 1;
		String varname = "@str" + this.stringLitterals.size();
		String processedString = str.replace("\n", "\\0A");
		processedString = processedString.replace("\t", "\\09");
		processedString = processedString.replace("\"", "\\22");
		processedString += "\\00";
		this.stringLitterals.add(LLVMInstruction.createStringLitteral(varname, processedString, len));
		return varname;
	}

	/* (non-Javadoc)
	 * @see LLVM.LLVMCodeGenerator#generate()
	 */
	public String generate() {
 		return this.generate(0);
 	}

 	/* (non-Javadoc)
 	 * @see LLVM.LLVMCodeGenerator#generate(int)
 	 */
 	public String generate(int indentation) {
 		String result = "";
 		for (LLVMInstruction stringLitteralDeclaration: this.stringLitterals) {
 			result += stringLitteralDeclaration.generate(indentation) + "\n";
 		}
 		for (LLVMCodeGenerator block: this.contentBlocks) {
 			result += block.generate(indentation) + "\n";
 		}
 		return result;
 	}
}