package LLVM;
import java.util.ArrayList;

import Utils.StringUtils;

/**
 * Build an LLVM IR function
 */
class LLVMFunction extends LLVMGroupContainer implements LLVMCodeGenerator {
	private String name;
	public String getName() {
		return name;
	}
	
	private String rtnType;
	public String getRtnType() {
		return rtnType;
	}
	
	
	private ArrayList<LLVMCodeGenerator> contentBlocks = new ArrayList<LLVMCodeGenerator>();
	
	public LLVMFunction(String rtnType, String name) {
		this.name = name;
		this.rtnType = rtnType;
	}

	public static LLVMFunction createMain() {
		return new LLVMFunction("i32", "@main");
	}

	/**
	 * Adds the specified block in the function.
	 * @param block can be an LLVMInstruction or a LLVMBasicGroup
	 */
	public void addBlock(LLVMCodeGenerator block) {
		this.contentBlocks.add(block);
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
 		String result = StringUtils.repeatString("\t", indentation) + 
 				"define " + this.rtnType + " "+ this.name + "() {\n";

 		for (int i = this.contentBlocks.size()-1; i>= 0 ; i-- ) {
 			result += this.contentBlocks.get(i).generate(indentation + 1) + "\n";
 		}
 		
 		result += StringUtils.repeatString("\t", indentation) + "}";
 		return result;
 	}
}