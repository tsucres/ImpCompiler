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
    %lastFactor = alloca i32
    %n = alloca i32
    %factor = alloca i32
    store i32 436729935, i32* %n
    store i32 1, i32* %lastFactor
    %0 = load i32, i32* %n
    %1 = srem i32 %0, 2
    %2 = icmp eq i32 %1,0
    br i1 %2 , label %if0 , label %else5

  if0:
    %3 = load i32, i32* %n
    %4 = sdiv i32 %3, 2
    store i32 %4, i32* %n
    store i32 2, i32* %lastFactor
    br label %condwhile1

  condwhile1:
    %5 = load i32, i32* %n
    %6 = srem i32 %5, 2
    %7 = icmp eq i32 %6,0
    br i1 %7 , label %while2 , label %done3

  while2:
    %8 = load i32, i32* %n
    %9 = sdiv i32 %8, 2
    store i32 %9, i32* %n
    br label %condwhile1

  done3:
    br label %endif4

  else5:
    store i32 3, i32* %factor
    br label %condwhile6

  condwhile6:
    %10 = load i32, i32* %n
    %11 = icmp sgt i32 %10,1
    br i1 %11 , label %while7 , label %done8

  while7:
    %12 = load i32, i32* %n
    %13 = load i32, i32* %factor
    %14 = srem i32 %12, %13
    %15 = icmp eq i32 %14,0
    br i1 %15 , label %if9 , label %endif13

  if9:
    %16 = load i32, i32* %n
    %17 = load i32, i32* %factor
    %18 = sdiv i32 %16, %17
    store i32 %18, i32* %n
    %19 = load i32, i32* %factor
    store i32 %19, i32* %lastFactor
    br label %condwhile10

  condwhile10:
    %20 = load i32, i32* %n
    %21 = load i32, i32* %factor
    %22 = srem i32 %20, %21
    %23 = icmp eq i32 %22,0
    br i1 %23 , label %while11 , label %done12

  while11:
    %24 = load i32, i32* %n
    %25 = load i32, i32* %factor
    %26 = sdiv i32 %24, %25
    store i32 %26, i32* %n
    br label %condwhile10

  done12:
    br label %endif13

  endif13:
    %27 = load i32, i32* %factor
    %28 = add i32 %27, 2
    store i32 %28, i32* %factor
    br label %condwhile6

  done8:
    br label %endif4

  endif4:
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([9 x i8], [9 x i8]* @str0, i32 0, i32 0))
    %30 = load i32, i32* %lastFactor
    call void @printint(i32 %30)
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @str1, i32 0, i32 0))
    ret i32 0

}
