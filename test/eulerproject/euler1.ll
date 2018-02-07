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
    %max = alloca i32
    %sum = alloca i32
    %i = alloca i32
    store i32 1000, i32* %max
    store i32 0, i32* %sum
    store i32 0, i32* %i
    br label %incfor0

  incfor0:
    %0 = load i32, i32* %max
    %1 = load i32, i32* %i
    %2 = icmp sge i32 1,0
    %3 = icmp sle i32 1,0
    %4 = icmp sle i32 %1,%0
    %5 = icmp sge i32 %1,%0
    %6 = and i1 %2,%4
    %7 = and i1 %3,%5
    %8 = or i1 %6,%7
    br i1 %8 , label %for1 , label %endfor2

  for1:
    %9 = load i32, i32* %i
    %10 = srem i32 %9, 3
    %11 = icmp eq i32 %10,0
    %12 = load i32, i32* %i
    %13 = srem i32 %12, 5
    %14 = icmp eq i32 %13,0
    %15 = or i1 %11,%14
    br i1 %15 , label %if3 , label %endif4

  if3:
    %16 = load i32, i32* %sum
    %17 = load i32, i32* %i
    %18 = add i32 %16, %17
    store i32 %18, i32* %sum
    br label %endif4

  endif4:
    %19 = load i32, i32* %i
    %20 = add i32 %19, 1
    store i32 %20, i32* %i
    br label %incfor0

  endfor2:
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([9 x i8], [9 x i8]* @str0, i32 0, i32 0))
    %22 = load i32, i32* %sum
    call void @printint(i32 %22)
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @str1, i32 0, i32 0))
    ret i32 0

}
