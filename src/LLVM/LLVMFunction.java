package LLVM;
import java.util.ArrayList;

import Utils.StringUtils;


/**
 * A parameter (defined by a name and a type) for an LLVM function.
 */
class LLVMFunctionParam {
	/**
	 * A valid llvm type (i8, i32, [8 x i8], ..)
	 */
	private String type;
	/**
	 * @return the llvm type of the parameter (something like i8, i32, [8 x i8], ..)
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type a valid llvm vartype (something like i8, i32, [8 x i8], ..)
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the name of the parameter.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name a valid LLVM varname
	 */
	public void setName(String name) {
		this.name = name;
	}
	private String name;
	public LLVMFunctionParam(String type, String name) {
		this.setName(name);
		this.setType(type);
	}
}
/**
 * Build an LLVM IR function (with a name, a list of parameters and made of several Basic groups)
 */
class LLVMFunction extends LLVMGroupContainer implements LLVMCodeGenerator {
	private String name;
	/**
	 * @return the LLVM name of the function
	 */
	public String getName() {
		return name;
	}
	
	private String rtnType;
	/**
	 * @return the llvm type returned by the function (something like i8, i32, [8 x i8], ..)
	 */
	public String getRtnType() {
		return rtnType;
	}
	/**
	 * @param rtnType a valid llvm vartype (something like i8, i32, [8 x i8], ..)
	 */
	public void setRtnType(String rtnType) {
		this.rtnType = rtnType;
	}
	
	private ArrayList<LLVMFunctionParam> params = new ArrayList<LLVMFunctionParam>();
	/**
	 * Add a parameter to the function 
	 * @param name a valid LLVM varname
	 * @param type a valid llvm vartype (something like i8, i32, [8 x i8], ..)
	 */
	public void addParam(String name, String type) {
		params.add(new LLVMFunctionParam(type, name));
	}
	
	/**
	 * Several object capable of generating LLVM IR code as the body of the function (most likely an LLVMBasicBlock)
	 */
	private ArrayList<LLVMCodeGenerator> contentBlocks = new ArrayList<LLVMCodeGenerator>();
	
	public LLVMFunction(String rtnType, String name) {
		this.name = name;
		this.rtnType = rtnType;
	}

	/**
	 * @return a valid instance of LLVMFunction representing a valid "main" function (returning an i32).
	 */
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
 		String strParams = "";
 		for (LLVMFunctionParam param: this.params) {
 			strParams += String.format("%s %s, ", param.getType(), param.getName());
 		}
 		if (strParams.length() > 2) {
 			strParams = strParams.substring(0, strParams.length() - 2); // removes the last ", "
 		}

 		String result = StringUtils.repeatString("\t", indentation) + 
 				"define " + this.rtnType + " "+ this.name + "(" + strParams + ") {\n";

 		for (int i = 0; i< this.contentBlocks.size() ; i++ ) {
 			result += this.contentBlocks.get(i).generate(indentation + 1) + "\n";
 		}
 		
 		result += StringUtils.repeatString("\t", indentation) + "}";
 		return result;
 	}
}