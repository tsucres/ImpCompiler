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
@str0 = internal constant [97 x i8] c"\0AThis program will calculate the gcd of the two numbers you specify using a recursive function.\0A\00"

@str1 = internal constant [15 x i8] c"First number: \00"

@str2 = internal constant [17 x i8] c"\0ASecond number: \00"

@str3 = internal constant [13 x i8] c"\0AThe gcd of \00"

@str4 = internal constant [6 x i8] c" and \00"

@str5 = internal constant [6 x i8] c" is: \00"

@str6 = internal constant [2 x i8] c"\0A\00"

define i32 @gcd(i32 %xv, i32 %yv) {
  entry:
    %x = alloca i32
    %y = alloca i32
    %rtnValue = alloca i32
    store i32 %xv, i32* %x
    store i32 %yv, i32* %y
    store i32 0, i32* %rtnValue
    %0 = load i32, i32* %y
    %1 = icmp eq i32 %0,0
    br i1 %1 , label %if0 , label %else2

  if0:
    %2 = load i32, i32* %x
    store i32 %2, i32* %rtnValue
    br label %endif1

  else2:
    %3 = load i32, i32* %y
    %4 = load i32, i32* %x
    %5 = load i32, i32* %y
    %6 = srem i32 %4, %5
    %7 = call i32 @gcd(i32 %3, i32 %6)
    store i32 %7, i32* %rtnValue
    br label %endif1

  endif1:
    %8 = load i32, i32* %rtnValue
    ret i32 %8

}
define i32 @main() {
  entry:
    %a = alloca i32
    %b = alloca i32
    %result = alloca i32
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([97 x i8], [97 x i8]* @str0, i32 0, i32 0))
    store i32 0, i32* %a
    store i32 0, i32* %b
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([15 x i8], [15 x i8]* @str1, i32 0, i32 0))
    %2 = call i32 @readInt()
    store i32 %2, i32* %a
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([17 x i8], [17 x i8]* @str2, i32 0, i32 0))
    %4 = call i32 @readInt()
    store i32 %4, i32* %b
    %5 = load i32, i32* %a
    %6 = load i32, i32* %b
    %7 = call i32 @gcd(i32 %5, i32 %6)
    store i32 %7, i32* %result
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([13 x i8], [13 x i8]* @str3, i32 0, i32 0))
    %9 = load i32, i32* %a
    call void @printint(i32 %9)
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([6 x i8], [6 x i8]* @str4, i32 0, i32 0))
    %11 = load i32, i32* %b
    call void @printint(i32 %11)
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([6 x i8], [6 x i8]* @str5, i32 0, i32 0))
    %13 = load i32, i32* %result
    call void @printint(i32 %13)
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @str6, i32 0, i32 0))
    ret i32 0

}
