declare i32 @printf(i8*, ...) #1
@.strR = private unnamed_addr constant [3 x i8] c"%d\00", align 1
define i32 @readInt() #0 {
  %x = alloca i32, align 4
  %1 = call i32 (i8*, ...) @scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.strR, i32 0, i32 0), i32* %x)
  %2 = load i32, i32* %x, align 4
  ret i32 %2
}
declare i32 @scanf(i8*, ...) #1
@str0 = internal constant [6 x i8] c"\0A>>> \00"

@str1 = internal constant [13 x i8] c"smaller\0A>>> \00"

@str2 = internal constant [12 x i8] c"bigger\0A>>> \00"

@str3 = internal constant [18 x i8] c"Congratulation!!\0A\00"

define i32 @main() {
  entry:
    %guess = alloca i32
    %numberToGuess = alloca i32
    %win = alloca i32
    store i32 108, i32* %numberToGuess
    store i32 0, i32* %guess
    store i32 0, i32* %win
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([6 x i8], [6 x i8]* @str0, i32 0, i32 0))
    %1 = call i32 @readInt()
    store i32 %1, i32* %guess
    br label %condwhile0

  condwhile0:
    %2 = load i32, i32* %guess
    %3 = load i32, i32* %numberToGuess
    %4 = icmp ne i32 %2,%3
    br i1 %4 , label %while1 , label %done2

  while1:
    %5 = load i32, i32* %guess
    %6 = load i32, i32* %numberToGuess
    %7 = icmp sgt i32 %5,%6
    br i1 %7 , label %if3 , label %else5

  if3:
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([13 x i8], [13 x i8]* @str1, i32 0, i32 0))
    br label %endif4

  else5:
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([12 x i8], [12 x i8]* @str2, i32 0, i32 0))
    br label %endif4

  endif4:
    %10 = call i32 @readInt()
    store i32 %10, i32* %guess
    br label %condwhile0

  done2:
    call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([18 x i8], [18 x i8]* @str3, i32 0, i32 0))
    ret i32 0

}
