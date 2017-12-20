package LLVM;

/**
 * Represent an object that can produce LLVM code (Instruction, BasicGroup, Function, ...)
 */
interface LLVMCodeGenerator {
	/**
	 * @return generated LLVM IR code
	 */
	public String generate();
	/**
	 * Generate the LLVM IR code and adds thespecified indentation to each line.
	 * @param indentation the additionnal indentation level for each line (indentation = 2 spaces)
	 * @return generated LLVM IR code
	 */
	public String generate(int indentation);
}