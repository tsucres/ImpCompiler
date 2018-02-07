@.strP = private unnamed_addr constant [3 x i8] c"%d\00", align 1
define void @printint(i32 %x) #0 {
  %1 = alloca i32, align 4
  store i32 %x, i32* %1, align 4
  %2 = load i32, i32* %1, align 4  %3 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.strP, i32 0, i32 0), i32 %2)
  ret void
}
declare i32 @printf(i8*, ...) #1
@str0 = internal constant [9 x i8] c"Result: \00"

@str1 = internal constant [2 x i8] c"\0A\00"

define i32 @main() {
  entry:
    %a = alloca i32
    %b = alloca i32
    %c = alloca i32
    %max = alloca i32
    %sum = alloca i32
    store i32 4000000, i32* %max
    store i32 0, i32* %sum
    store i32 1, i32* %a
    store i32 1, i32* %b
    %0 = load i32, i32* %a
    %1 = load i32, i32* %b
    %2 = add i32 %0, %1
    store i32 %2, i32* %c
    br label %condwhile0

  condwhile0:
    %3 = load i32, i32* %c
    %4 = load i32, i32* %max
    %5 = icmp slt i32 %3,%4
    br i1 %5 , label %while1 , label %done2

  while1:
    %6 = load i32, i32* %sum
    %7 = load i32, i32* %c
    %8 = add i32 %6, %7
    store i32 %8, i32* %sum
    %9 = load i32, i32* %b
    %10 = load i32, i32* %c
    %11 = add i32 %9, %10
    store i32 %11, i32* %a
    %12 = load i32, i32* %c
    %13 = load i32, i32* %a
    %14 = add i32 %12, %13
    store i32 %14, i32* %b
    %15 = load i32, i32* %a
    %16 = load i32, i32* %b
    %17 = add i32 %15, %16
    store i32 %17, i32* %c
    br label %condwhile0

  done2:
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([9 x i8], [9 x i8]* @str0, i32 0, i32 0))
    %19 = load i32, i32* %sum
    call void @printint(i32 %19)
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @str1, i32 0, i32 0))
    ret i32 0

}
