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

@str2 = internal constant [27 x i8] c"Expected result: 31875000\0A\00"

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
    %mLimit = alloca i32
    %rtnValue = alloca i32
    %s = alloca i32
    %sBis = alloca i32
    %m = alloca i32
    %k = alloca i32
    %sm = alloca i32
    %gcdValue = alloca i32
    %a = alloca i32
    %b = alloca i32
    %c = alloca i32
    %d = alloca i32
    %n = alloca i32
    store i32 0, i32* %rtnValue
    store i32 1000, i32* %s
    %0 = load i32, i32* %s
    %1 = sdiv i32 %0, 2
    store i32 %1, i32* %sBis
    %2 = sub i32 23, 1
    store i32 %2, i32* %mLimit
    store i32 2, i32* %m
    br label %incfor0

  incfor0:
    %3 = load i32, i32* %mLimit
    %4 = load i32, i32* %m
    %5 = icmp sge i32 1,0
    %6 = icmp sle i32 1,0
    %7 = icmp sle i32 %4,%3
    %8 = icmp sge i32 %4,%3
    %9 = and i1 %5,%7
    %10 = and i1 %6,%8
    %11 = or i1 %9,%10
    br i1 %11 , label %for1 , label %endfor2

  for1:
    %12 = load i32, i32* %sBis
    %13 = load i32, i32* %m
    %14 = srem i32 %12, %13
    %15 = icmp eq i32 %14,0
    br i1 %15 , label %if3 , label %endif15

  if3:
    %16 = load i32, i32* %sBis
    %17 = load i32, i32* %m
    %18 = sdiv i32 %16, %17
    store i32 %18, i32* %sm
    br label %condwhile4

  condwhile4:
    %19 = load i32, i32* %sm
    %20 = srem i32 %19, 2
    %21 = icmp eq i32 %20,0
    br i1 %21 , label %while5 , label %done6

  while5:
    %22 = load i32, i32* %sm
    %23 = sdiv i32 %22, 2
    store i32 %23, i32* %sm
    br label %condwhile4

  done6:
    store i32 0, i32* %k
    %24 = load i32, i32* %m
    %25 = srem i32 %24, 2
    %26 = icmp eq i32 %25,1
    br i1 %26 , label %if7 , label %else9

  if7:
    %27 = load i32, i32* %m
    %28 = add i32 %27, 2
    store i32 %28, i32* %k
    br label %endif8

  else9:
    %29 = load i32, i32* %m
    %30 = add i32 %29, 1
    store i32 %30, i32* %k
    br label %endif8

  endif8:
    br label %condwhile10

  condwhile10:
    %31 = load i32, i32* %k
    %32 = load i32, i32* %m
    %33 = mul i32 2, %32
    %34 = icmp slt i32 %31,%33
    %35 = load i32, i32* %k
    %36 = load i32, i32* %sm
    %37 = icmp sle i32 %35,%36
    %38 = and i1 %34,%37
    br i1 %38 , label %while11 , label %done12

  while11:
    %39 = load i32, i32* %k
    %40 = load i32, i32* %m
    %41 = call i32 @gcd(i32 %39, i32 %40)
    store i32 %41, i32* %gcdValue
    %42 = load i32, i32* %sm
    %43 = load i32, i32* %k
    %44 = srem i32 %42, %43
    %45 = icmp eq i32 %44,0
    %46 = load i32, i32* %gcdValue
    %47 = icmp eq i32 %46,1
    %48 = and i1 %45,%47
    br i1 %48 , label %if13 , label %endif14

  if13:
    %49 = load i32, i32* %sBis
    %50 = load i32, i32* %k
    %51 = load i32, i32* %m
    %52 = mul i32 %50, %51
    %53 = sdiv i32 %49, %52
    store i32 %53, i32* %d
    %54 = load i32, i32* %k
    %55 = load i32, i32* %m
    %56 = sub i32 %54, %55
    store i32 %56, i32* %n
    %57 = load i32, i32* %d
    %58 = load i32, i32* %m
    %59 = load i32, i32* %m
    %60 = mul i32 %58, %59
    %61 = load i32, i32* %n
    %62 = load i32, i32* %n
    %63 = mul i32 %61, %62
    %64 = sub i32 %60, %63
    %65 = mul i32 %57, %64
    store i32 %65, i32* %a
    %66 = load i32, i32* %d
    %67 = mul i32 2, %66
    %68 = load i32, i32* %m
    %69 = mul i32 %67, %68
    %70 = load i32, i32* %n
    %71 = mul i32 %69, %70
    store i32 %71, i32* %b
    %72 = load i32, i32* %d
    %73 = load i32, i32* %m
    %74 = load i32, i32* %m
    %75 = mul i32 %73, %74
    %76 = load i32, i32* %n
    %77 = load i32, i32* %n
    %78 = mul i32 %76, %77
    %79 = add i32 %75, %78
    %80 = mul i32 %72, %79
    store i32 %80, i32* %c
    %81 = load i32, i32* %a
    %82 = load i32, i32* %b
    %83 = mul i32 %81, %82
    %84 = load i32, i32* %c
    %85 = mul i32 %83, %84
    store i32 %85, i32* %rtnValue
    br label %endif14

  endif14:
    %86 = load i32, i32* %k
    %87 = add i32 %86, 2
    store i32 %87, i32* %k
    br label %condwhile10

  done12:
    br label %endif15

  endif15:
    %88 = load i32, i32* %m
    %89 = add i32 %88, 1
    store i32 %89, i32* %m
    br label %incfor0

  endfor2:
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([9 x i8], [9 x i8]* @str0, i32 0, i32 0))
    %91 = load i32, i32* %rtnValue
    call void @printint(i32 %91)
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([2 x i8], [2 x i8]* @str1, i32 0, i32 0))
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([27 x i8], [27 x i8]* @str2, i32 0, i32 0))
    ret i32 0

}
