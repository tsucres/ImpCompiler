package LLVM;
import java.util.ArrayList;

import Utils.StringUtils;

/**
 * Build and produce the IR code for an LLVM basic group made of LLVMInstructions
 */
class LLVMBasicGroup implements LLVMCodeGenerator {

	private ArrayList<LLVMCodeGenerator> content = new ArrayList<LLVMCodeGenerator>();
	/**
	 * The name of the group. The generated group will start with "name: [list of instructions]"
	 */
	private String name;
	
	public LLVMBasicGroup(String name) {
		this.name = name;
	}
	/**
	 * @return the LLVM name of the group
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Add a new instruction inside the group
	 * @param inst the instruction to add
	 */
	public void addInstruction(LLVMInstruction inst) {
		this.content.add(inst);
	}

	/* (non-Javadoc)
	 * @see LLVM.LLVMCodeGenerator#generate(int)
	 */
	public String generate() {
		return this.generate(0);
	}
	/* (non-Javadoc)
	 * @see LLVM.LLVMCodeGenerator#generate(int)
	 */
	public String generate(int indentation) {
		String result = StringUtils.repeatString("  ", indentation) + this.name + ":\n";
		for (LLVMCodeGenerator inst: this.content) {
			result += inst.generate(indentation + 1);
		}
		return result;
	}


}