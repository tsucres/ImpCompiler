# ImpCompiler

[![Javadoc](https://img.shields.io/badge/javadoc-OK-orange.svg)](https://tsucres.github.io/ImpCompiler/index.html)


This is a compiler written in Java for a simple imperative language (referred as "Imp"). It supports all the basic features of a programming language, including: 

- integer variables
- string constants
- arithmetic expressions
- boolean expressions
- basic flow control instructions: if/else, while, for
- functions
- print/read statements

Its full grammar is presented in the [grammar.md](grammar.md) file. You can check the [`test`](test) folder for some example source codes.

The project is composed of 4 main parts: 
- The lexical analyzer, built with jFlex, outputting a sequence of lexemes and a list of detected identifiers
- The parser, which builds a parser tree from the sequence of lexemes
- The semantic analyzer, which basically just builds an AST from the parser tree and runs some basic verifications concerning the semantic of the input program
- The code generator: generate and outputs an LLVM-IR code equivalent to the input program.

The produced LLVM code can then be ran using LLVM's `lli` command.

## Build & Run

### Build
To build the project, run
```sh
make 
```

You can also import it in your favorite IDE.

The previous command will compile the project in the bin directory, produce a jar in the dist folder and output the javadoc in the docs folder.

### Produce jar

```sh
make jar
```

The jar will be created in the dist folder

### Produce javadoc

The following will produce the javadoc in the docs folder

```sh
make doc
```

### Run

```sh
$ java -jar dist/ImpCompiler.jar 
	
	Usage:  java -jar ImpCompiler.jar file.imp [out.ll] [--debug] [--exec]
	or	java Main file.imp  [out.ll] [--debug] [--exec]

	--debug		 prints the ParserTree and the AST
	--exec		 executes the resulting LLVM code (lli must be an accessible command)

```

## Project structure

- `dist`: contains the .jar
- `docs`: contains the javadoc (available [here](https://tsucres.github.io/ImpCompiler/index.html))
- `test`: contains a bunch of imp source codes (valid and syntactically/semantically invalid) used to test the compiler. They're sorted according to the functionalities of the Imp language they implement and to whether they are valid or not.
- `src`:
	- The `Lexer` package implements the lexical analyzer. It's mainly composed of the model classes `Symbol` and `LexicalUnit` and of the jflex-generated class `ImpLexer`
	- The `Parser` package implements... well, the parser. It contains everything needed to build the parser tree and a tree of identifier tables (representing the variable scoping)
	- The `AST` package is made of a single class used to simplify the parser tree into an Abstract Syntax Tree
	- The `LLVM` package is basically a java binding for LLVM that handles the needed functionalities. It's independent to the Imp language, expect for the `LLVMGenerator` class which produce the LLVM-IR code from the (Imp) AST.

