package LLVM;
import java.util.ArrayList;

class LLVMProgram implements LLVMCodeGenerator {
	
	private ArrayList<LLVMCodeGenerator> contentBlocks = new ArrayList<LLVMCodeGenerator>();

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
 		for (LLVMCodeGenerator block: this.contentBlocks) {
 			result += block.generate(indentation) + "\n";
 		}
 		return result;
 	}
}