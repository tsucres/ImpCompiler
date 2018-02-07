@.strP = private unnamed_addr constant [3 x i8] c"%d\00", align 1
define void @printint(i32 %x) #0 {
  %1 = alloca i32, align 4
  store i32 %x, i32* %1, align 4
  %2 = load i32, i32* %1, align 4  %3 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.strP, i32 0, i32 0), i32 %2)
  ret void
}
declare i32 @printf(i8*, ...) #1
@str0 = internal constant [10 x i8] c"Result : \00"

@str1 = internal constant [26 x i8] c"\0AExpected result: 104743\0A\00"

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
    %candidate = alloca i32
    %count = alloca i32
    %limit = alloca i32
    %isCandidatePrime = alloca i32
    store i32 10001, i32* %limit
    store i32 1, i32* %count
    store i32 1, i32* %candidate
    br label %condwhile0

  condwhile0:
    %0 = load i32, i32* %count
    %1 = load i32, i32* %limit
    %2 = icmp ne i32 %0,%1
    br i1 %2 , label %while1 , label %done2

  while1:
    %3 = load i32, i32* %candidate
    %4 = add i32 %3, 2
    store i32 %4, i32* %candidate
    %5 = load i32, i32* %candidate
    %6 = call i32 @isPrime(i32 %5)
    store i32 %6, i32* %isCandidatePrime
    %7 = load i32, i32* %isCandidatePrime
    %8 = icmp eq i32 %7,1
    br i1 %8 , label %if3 , label %endif4

  if3:
    %9 = load i32, i32* %count
    %10 = add i32 %9, 1
    store i32 %10, i32* %count
    br label %endif4

  endif4:
    br label %condwhile0

  done2:
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([10 x i8], [10 x i8]* @str0, i32 0, i32 0))
    %12 = load i32, i32* %candidate
    call void @printint(i32 %12)
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([26 x i8], [26 x i8]* @str1, i32 0, i32 0))
    ret i32 0

}
