@.strP = private unnamed_addr constant [3 x i8] c"%d\00", align 1
define void @printint(i32 %x) #0 {
  %1 = alloca i32, align 4
  store i32 %x, i32* %1, align 4
  %2 = load i32, i32* %1, align 4  %3 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.strP, i32 0, i32 0), i32 %2)
  ret void
}
declare i32 @printf(i8*, ...) #1
@.strR = private unnamed_addr constant [3 x i8] c"%d\00", align 1
define i32 @readInt() #0 {
  %x = alloca i32, align 4
  %1 = call i32 (i8*, ...) @scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.strR, i32 0, i32 0), i32* %x)
  %2 = load i32, i32* %x, align 4
  ret i32 %2
}
declare i32 @scanf(i8*, ...) #1
@str0 = internal constant [93 x i8] c"This program calculate the factorial of the number you specify using a recursive algorithm.\0A\00"

@str1 = internal constant [56 x i8] c"It only works for numbers below 13 (since 13! > 2**32)\0A\00"

@str2 = internal constant [9 x i8] c"Number: \00"

@str3 = internal constant [18 x i8] c"The factorial of \00"

@str4 = internal constant [6 x i8] c" is: \00"

@str5 = internal constant [2 x i8] c"\0A\00"

define i32 @recursFactorial(i32 %nv) {
  entry:
    %n = alloca i32
    %rtnValue = alloca i32
    %recurs = alloca i32
    store i32 %nv, i32* %n
    store i32 1, i32* %rtnValue
    %0 = load i32, i32* %n
    %1 = icmp ne i32 %0,0
    br i1 %1 , label %if0 , label %endif1

  if0:
    %2 = load i32, i32* %n
    %3 = sub i32 %2, 1
    %4 = call i32 @recursFactorial(i32 %3)
    store i32 %4, i32* %recurs
    %5 = load i32, i32* %n
    %6 = load i32, i32* %recurs
    %7 = mul i32 %5, %6
    store i32 %7, i32* %rtnValue
    br label %endif1

  endif1:
    %8 = load i32, i32* %rtnValue
    ret i32 %8

}
define i32 @main() {
  entry:
    %fact = alloca i32
    %n = alloca i32
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([93 x i8], [93 x i8]* @str0, i32 0, i32 0))
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([56 x i8], [56 x i8]* @str1, i32 0, i32 0))
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([9 x i8], [9 x i8]* @str2, i32 0, i32 0))
    store i32 0, i32* %n
    %3 = call i32 @readInt()
    store i32 %3, i32* %n
    %4 = load i32, i32* %n
    %5 = call i32 @recursFactorial(i32 %4)
    store i32 %5, i32* %fact
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([18 x i8], [18 x i8]* @str3, i32 0, i32 0))
    %7 = load i32, i32* %n
    call void @printint(i32 %7)
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([6 x i8], [6 x i8]* @str4, i32 0, i32 0))
    %9 = load i32, i32* %fact
    call void @printint(i32 %9)
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @str5, i32 0, i32 0))
    ret i32 0

}
