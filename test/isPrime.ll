@.strP = private unnamed_addr constant [3 x i8] c"%d\00", align 1
define void @printint(i32 %x) #0 {
  %1 = alloca i32, align 4
  store i32 %x, i32* %1, align 4
  %2 = load i32, i32* %1, align 4  %3 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.strP, i32 0, i32 0), i32 %2)
  ret void
}
declare i32 @printf(i8*, ...) #1
@str0 = internal constant [2 x i8] c".\00"

@str1 = internal constant [16 x i8] c"\0ATest error : \22\00"

@str2 = internal constant [21 x i8] c"\22 is different from\22\00"

@str3 = internal constant [2 x i8] c"\0A\00"

@str4 = internal constant [2 x i8] c"\0A\00"

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
define i32 @assertEqual(i32 %valuev, i32 %expectedValuev) {
  entry:
    %expectedValue = alloca i32
    %value = alloca i32
    %rtnValue = alloca i32
    store i32 %valuev, i32* %value
    store i32 %expectedValuev, i32* %expectedValue
    store i32 1, i32* %rtnValue
    %0 = load i32, i32* %value
    %1 = load i32, i32* %expectedValue
    %2 = icmp eq i32 %0,%1
    br i1 %2 , label %if0 , label %else2

  if0:
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @str0, i32 0, i32 0))
    br label %endif1

  else2:
    store i32 0, i32* %rtnValue
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([16 x i8], [16 x i8]* @str1, i32 0, i32 0))
    %5 = load i32, i32* %value
    call void @printint(i32 %5)
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([21 x i8], [21 x i8]* @str2, i32 0, i32 0))
    %7 = load i32, i32* %expectedValue
    call void @printint(i32 %7)
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @str3, i32 0, i32 0))
    br label %endif1

  endif1:
    %9 = load i32, i32* %rtnValue
    ret i32 %9

}
define i32 @testIsPrime() {
  entry:
    %a = alloca i32
    %0 = call i32 @isPrime(i32 1)
    store i32 %0, i32* %a
    %1 = load i32, i32* %a
    %2 = call i32 @assertEqual(i32 %1, i32 0)
    %3 = call i32 @isPrime(i32 2)
    store i32 %3, i32* %a
    %4 = load i32, i32* %a
    %5 = call i32 @assertEqual(i32 %4, i32 1)
    %6 = call i32 @isPrime(i32 3)
    store i32 %6, i32* %a
    %7 = load i32, i32* %a
    %8 = call i32 @assertEqual(i32 %7, i32 1)
    %9 = call i32 @isPrime(i32 4)
    store i32 %9, i32* %a
    %10 = load i32, i32* %a
    %11 = call i32 @assertEqual(i32 %10, i32 0)
    %12 = call i32 @isPrime(i32 5)
    store i32 %12, i32* %a
    %13 = load i32, i32* %a
    %14 = call i32 @assertEqual(i32 %13, i32 1)
    %15 = call i32 @isPrime(i32 7)
    store i32 %15, i32* %a
    %16 = load i32, i32* %a
    %17 = call i32 @assertEqual(i32 %16, i32 1)
    %18 = call i32 @isPrime(i32 10)
    store i32 %18, i32* %a
    %19 = load i32, i32* %a
    %20 = call i32 @assertEqual(i32 %19, i32 0)
    %21 = call i32 @isPrime(i32 9)
    store i32 %21, i32* %a
    %22 = load i32, i32* %a
    %23 = call i32 @assertEqual(i32 %22, i32 0)
    %24 = call i32 @isPrime(i32 15485863)
    store i32 %24, i32* %a
    %25 = load i32, i32* %a
    %26 = call i32 @assertEqual(i32 %25, i32 1)
    %27 = call i32 @isPrime(i32 15485869)
    store i32 %27, i32* %a
    %28 = load i32, i32* %a
    %29 = call i32 @assertEqual(i32 %28, i32 0)
    %30 = call i32 @isPrime(i32 11)
    store i32 %30, i32* %a
    %31 = load i32, i32* %a
    %32 = call i32 @assertEqual(i32 %31, i32 1)
    %33 = call i32 @isPrime(i32 13)
    store i32 %33, i32* %a
    %34 = load i32, i32* %a
    %35 = call i32 @assertEqual(i32 %34, i32 1)
    %36 = call i32 @isPrime(i32 17)
    store i32 %36, i32* %a
    %37 = load i32, i32* %a
    %38 = call i32 @assertEqual(i32 %37, i32 1)
    %39 = call i32 @isPrime(i32 19)
    store i32 %39, i32* %a
    %40 = load i32, i32* %a
    %41 = call i32 @assertEqual(i32 %40, i32 1)
    %42 = call i32 @isPrime(i32 23)
    store i32 %42, i32* %a
    %43 = load i32, i32* %a
    %44 = call i32 @assertEqual(i32 %43, i32 1)
    %45 = call i32 @isPrime(i32 25)
    store i32 %45, i32* %a
    %46 = load i32, i32* %a
    %47 = call i32 @assertEqual(i32 %46, i32 0)
    %48 = call i32 @isPrime(i32 50)
    store i32 %48, i32* %a
    %49 = load i32, i32* %a
    %50 = call i32 @assertEqual(i32 %49, i32 0)
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @str4, i32 0, i32 0))
    ret i32 0

}
define i32 @main() {
  entry:
    %0 = call i32 @testIsPrime()
    ret i32 0

}
