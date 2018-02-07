all: jar doc

jar: build
	jar cfe dist/ImpCompiler.jar Main -C bin .

build:
	javac -d bin -cp src/ src/Main.java

doc:
	javadoc -private -sourcepath src/ -subpackages AST Lexer LLVM Parser TreePrinter -d docs/