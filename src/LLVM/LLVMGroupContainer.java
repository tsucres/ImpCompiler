package LLVM;
/**
 * This class handles the generation of valid variable & group names.
 */
class LLVMGroupContainer {
	
	/**
	 * Count the number of generated group names. Allow to generate unique names.
	 */
	private int groupCounter = 0;
	/**
	 * Count the number of generated var names. Allow to generate unique names.
	 */
	private int tempVarCounter = 0;
	
	/**
	 * @return new, valid (unique) group name.
	 */
	public String newGroupName() {
		return this.newGroupName("group");
	}
	
	/**
	 * @param prefix a prefix to add to the group name.
	 * @return new, valid (unique) group name, starting with the specified prefix
	 */
	public String newGroupName(String prefix) {
		String groupName = prefix + Integer.toString(groupCounter);
		this.groupCounter ++;
		return groupName;
	}


	/**
	 * @return new, valid (unique and ordered) temp variable name ("%" + num)
	 */
	public String newTempVar() {
		String varname = "%" + Integer.toString(tempVarCounter);
		this.tempVarCounter ++;
		return varname;
	}
}