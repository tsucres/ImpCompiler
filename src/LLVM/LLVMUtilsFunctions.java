package LLVM;
class LLVMUtilsFunctions {
	/**
	 * The implementation of the printf function. 
	 */
	public static String PRINTF = 
		"@.strP = private unnamed_addr constant [4 x i8] c\"%d\\0A\\00\", align 1\n" + 
		"define void @println(i32 %x) #0 {\n" + 
  		"  %1 = alloca i32, align 4\n" + 
  		"  store i32 %x, i32* %1, align 4\n" + 
  		"  %2 = load i32, i32* %1, align 4" + 
  		"  %3 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.strP, i32 0, i32 0), i32 %2)\n" + 
  		"  ret void\n" + 
		"}\n" + 
		"declare i32 @printf(i8*, ...) #1\n";

	/**
	 * The implementation of the scanf function. 
	 */
	public static String SCANF = 
		"@.strR = private unnamed_addr constant [3 x i8] c\"%d\\00\", align 1\n" + 
		"define i32 @readInt() #0 {\n" +
  		"  %x = alloca i32, align 4\n" +
  		"  %1 = call i32 (i8*, ...) @scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.strR, i32 0, i32 0), i32* %x)\n" + 
  		"  %2 = load i32, i32* %x, align 4\n" + 
  		"  ret i32 %2\n" + 
		"}\n" + 
		"declare i32 @scanf(i8*, ...) #1\n";
}