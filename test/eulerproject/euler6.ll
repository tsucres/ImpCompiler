@.strP = private unnamed_addr constant [3 x i8] c"%d\00", align 1
define void @printint(i32 %x) #0 {
  %1 = alloca i32, align 4
  store i32 %x, i32* %1, align 4
  %2 = load i32, i32* %1, align 4  %3 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.strP, i32 0, i32 0), i32 %2)
  ret void
}
declare i32 @printf(i8*, ...) #1
@str0 = internal constant [10 x i8] c"Result : \00"

@str1 = internal constant [28 x i8] c"\0AExpected result: 25164150\0A\00"

define i32 @main() {
  entry:
    %limit = alloca i32
    %sum = alloca i32
    %sumSq = alloca i32
    store i32 100, i32* %limit
    %0 = load i32, i32* %limit
    %1 = load i32, i32* %limit
    %2 = add i32 %1, 1
    %3 = mul i32 %0, %2
    %4 = sdiv i32 %3, 2
    store i32 %4, i32* %sum
    %5 = load i32, i32* %limit
    %6 = mul i32 2, %5
    %7 = add i32 %6, 1
    %8 = load i32, i32* %limit
    %9 = add i32 %8, 1
    %10 = mul i32 %7, %9
    %11 = load i32, i32* %limit
    %12 = mul i32 %10, %11
    %13 = sdiv i32 %12, 6
    store i32 %13, i32* %sumSq
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([10 x i8], [10 x i8]* @str0, i32 0, i32 0))
    %15 = load i32, i32* %sum
    %16 = load i32, i32* %sum
    %17 = mul i32 %15, %16
    %18 = load i32, i32* %sumSq
    %19 = sub i32 %17, %18
    call void @printint(i32 %19)
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([28 x i8], [28 x i8]* @str1, i32 0, i32 0))
    ret i32 0

}
