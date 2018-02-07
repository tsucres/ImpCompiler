@.strP = private unnamed_addr constant [3 x i8] c"%d\00", align 1
define void @printint(i32 %x) #0 {
  %1 = alloca i32, align 4
  store i32 %x, i32* %1, align 4
  %2 = load i32, i32* %1, align 4  %3 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.strP, i32 0, i32 0), i32 %2)
  ret void
}
declare i32 @printf(i8*, ...) #1
define i32 @isPrime(i32 %nv) {
  entry:
    %n = alloca i32
    %rtnValue = alloca i32
    %i = alloca i32
    %w = alloca i32
    store i32 %nv, i32* %n
    %0 = mul i32 1, -1
    store i32 %0, i32* %rtnValue
    %1 = load i32, i32* %n
    %2 = icmp eq i32 %1,1
    br i1 %2 , label %if0 , label %endif1

  if0:
    store i32 0, i32* %rtnValue
    br label %endif1

  endif1:
    %3 = load i32, i32* %n
    %4 = icmp eq i32 %3,2
    br i1 %4 , label %if2 , label %else4

  if2:
    store i32 1, i32* %rtnValue
    br label %endif3

  else4:
    %5 = load i32, i32* %n
    %6 = icmp eq i32 %5,3
    br i1 %6 , label %if5 , label %else7

  if5:
    store i32 1, i32* %rtnValue
    br label %endif6

  else7:
    %7 = load i32, i32* %n
    %8 = srem i32 %7, 2
    %9 = icmp eq i32 %8,0
    br i1 %9 , label %if8 , label %else10

  if8:
    store i32 0, i32* %rtnValue
    br label %endif9

  else10:
    %10 = load i32, i32* %n
    %11 = srem i32 %10, 3
    %12 = icmp eq i32 %11,0
    br i1 %12 , label %if11 , label %endif12

  if11:
    store i32 0, i32* %rtnValue
    br label %endif12

  endif12:
    br label %endif9

  endif9:
    br label %endif6

  endif6:
    br label %endif3

  endif3:
    %13 = load i32, i32* %rtnValue
    %14 = mul i32 1, -1
    %15 = icmp eq i32 %13,%14
    br i1 %15 , label %if13 , label %endif20

  if13:
    store i32 5, i32* %i
    store i32 2, i32* %w
    br label %condwhile14

  condwhile14:
    %16 = load i32, i32* %i
    %17 = load i32, i32* %i
    %18 = mul i32 %16, %17
    %19 = load i32, i32* %n
    %20 = icmp sle i32 %18,%19
    %21 = load i32, i32* %n
    %22 = load i32, i32* %i
    %23 = srem i32 %21, %22
    %24 = icmp ne i32 %23,0
    %25 = and i1 %20,%24
    br i1 %25 , label %while15 , label %done16

  while15:
    %26 = load i32, i32* %i
    %27 = load i32, i32* %w
    %28 = add i32 %26, %27
    store i32 %28, i32* %i
    %29 = load i32, i32* %w
    %30 = sub i32 6, %29
    store i32 %30, i32* %w
    br label %condwhile14

  done16:
    %31 = load i32, i32* %n
    %32 = load i32, i32* %i
    %33 = srem i32 %31, %32
    %34 = icmp eq i32 %33,0
    %35 = load i32, i32* %i
    %36 = load i32, i32* %i
    %37 = mul i32 %35, %36
    %38 = load i32, i32* %n
    %39 = icmp sle i32 %37,%38
    %40 = and i1 %34,%39
    br i1 %40 , label %if17 , label %else19

  if17:
    store i32 0, i32* %rtnValue
    br label %endif18

  else19:
    store i32 1, i32* %rtnValue
    br label %endif18

  endif18:
    br label %endif20

  endif20:
    %41 = load i32, i32* %rtnValue
    ret i32 %41

}
define i32 @main() {
  entry:
    %max = alloca i32
    %sum = alloca i32
    %i = alloca i32
    %isIPrime = alloca i32
    store i32 2000000, i32* %max
    store i32 2, i32* %sum
    store i32 3, i32* %i
    br label %incfor0

  incfor0:
    %0 = load i32, i32* %max
    %1 = load i32, i32* %i
    %2 = icmp sge i32 2,0
    %3 = icmp sle i32 2,0
    %4 = icmp sle i32 %1,%0
    %5 = icmp sge i32 %1,%0
    %6 = and i1 %2,%4
    %7 = and i1 %3,%5
    %8 = or i1 %6,%7
    br i1 %8 , label %for1 , label %endfor2

  for1:
    %9 = load i32, i32* %i
    %10 = call i32 @isPrime(i32 %9)
    store i32 %10, i32* %isIPrime
    %11 = load i32, i32* %isIPrime
    %12 = icmp eq i32 %11,1
    br i1 %12 , label %if3 , label %endif4

  if3:
    %13 = load i32, i32* %sum
    %14 = load i32, i32* %i
    %15 = add i32 %13, %14
    store i32 %15, i32* %sum
    br label %endif4

  endif4:
    %16 = load i32, i32* %i
    %17 = add i32 %16, 2
    store i32 %17, i32* %i
    br label %incfor0

  endfor2:
    %18 = load i32, i32* %sum
    call void @printint(i32 %18)
    ret i32 0

}
