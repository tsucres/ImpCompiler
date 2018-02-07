import java.util.HashMap;
import java.util.Collections;
import java.util.ArrayList;


%%// Options of the scanner
%class ImpLexer		//Name
%unicode			//Use unicode
%line         		//Use line counter (yyline variable)
%column       		//Use character counter by line (yycolumn variable)
%type Symbol  		//Says that the return type is Symbol
%standalone			//Standalone mode


%{
	public HashMap<String,Integer> identifiersTable = new HashMap<String,Integer>();

	private Symbol symbol(LexicalUnit unit,int line,int column,Object value){
		Symbol sym = new Symbol(unit,line, column, value ); 
		// System.out.println(sym.toString()); 
		return sym; 
	}
	
	StringBuffer string = new StringBuffer();
%}


// called after scanning
%eof{
	/*System.out.println("\nIdentifiers");

	ArrayList<String> ids = new ArrayList<String>(identifiersTable.keySet());
	Collections.sort(ids, String.CASE_INSENSITIVE_ORDER);

	for(String id : ids) {
 		System.out.println(id + " " + identifiersTable.get(id));
	}*/
%eof}


// Return value of the program
%eofval{
	return new Symbol(LexicalUnit.EOS, yyline, yycolumn, "[EOS]");
%eofval}


// Extended Regular Expressions
AlphaUpperCase	= [A-Z]
AlphaLowerCase	= [a-z]
Alpha			= {AlphaUpperCase}|{AlphaLowerCase}
Numeric			= [0-9]
AlphaNumeric	= {Alpha}|{Numeric}
Space			= "\t" | " "
EndOfLine		= "\r"?"\n"
VarName			= {Alpha}{AlphaNumeric}*
BadInteger     = (0[0-9]+)
Integer        = ([1-9][0-9]*)|0


%state COMMENT
%state STRING

%%// Identification of tokens

<YYINITIAL> {
	"(*" {
		yybegin(COMMENT);
	}
	
	"*)" { 
		System.out.println("Error: unmatched (*"); 
	}

	"begin" { 
		return symbol(LexicalUnit.BEGIN,yyline, yycolumn, yytext() );
	}
	"end" { 
		return symbol(LexicalUnit.END,yyline, yycolumn, yytext() ); 
	}
	";" { 
		return symbol(LexicalUnit.SEMICOLON,yyline, yycolumn, yytext() ); 
	}

	// Arithm Operators
	":=" { 
		return symbol(LexicalUnit.ASSIGN,yyline, yycolumn, yytext() ); 
	}
	"+" { 
		return symbol(LexicalUnit.PLUS,yyline, yycolumn, yytext() ); 
	}
	"-" { 
		return symbol(LexicalUnit.MINUS,yyline, yycolumn, yytext() ); 
	}
	"*" { 
		return symbol(LexicalUnit.TIMES,yyline, yycolumn, yytext() ); 
	}
	"/" { 
		return symbol(LexicalUnit.DIVIDE,yyline, yycolumn, yytext() ); 
	}
	"%" { 
		return symbol(LexicalUnit.REM,yyline, yycolumn, yytext() ); 
	}

	"(" { 
		return symbol(LexicalUnit.LPAREN,yyline, yycolumn, yytext() ); 
	}
	")" { 
		return symbol(LexicalUnit.RPAREN,yyline, yycolumn, yytext() ); 
	}

	// Bin Operators
	"not" { 
		return symbol(LexicalUnit.NOT,yyline, yycolumn, yytext() ); 
	}
	"and" { 
		return symbol(LexicalUnit.AND,yyline, yycolumn, yytext() ); 
	}
	"or" { 
		return symbol(LexicalUnit.OR,yyline, yycolumn, yytext() ); 
	}

	// Comparison Operators
	"=" { 
		return symbol(LexicalUnit.EQ,yyline, yycolumn, yytext() ); 
	}
	">=" { 
		return symbol(LexicalUnit.GEQ,yyline, yycolumn, yytext() ); 
	}
	">" { 
		return symbol(LexicalUnit.GT,yyline, yycolumn, yytext() ); 
	}
	"<=" { 
		return symbol(LexicalUnit.LEQ,yyline, yycolumn, yytext() ); 
	}
	"<" { 
		return symbol(LexicalUnit.LT,yyline, yycolumn, yytext() ); 
	}
	"<>" { 
		return symbol(LexicalUnit.NEQ,yyline, yycolumn, yytext() ); 
	}

	// If - Else
	"if" { 
		return symbol(LexicalUnit.IF,yyline, yycolumn, yytext() ); 
	}
	"then" { 
		return symbol(LexicalUnit.THEN,yyline, yycolumn, yytext() ); 
	}
	"else" { 
		return symbol(LexicalUnit.ELSE,yyline, yycolumn, yytext() ); 
	}
	"endif" { 
		return symbol(LexicalUnit.ENDIF,yyline, yycolumn, yytext() ); 
	}

	// Loops
	"while" { 
		return symbol(LexicalUnit.WHILE,yyline, yycolumn, yytext() ); 
	}
	"do" { 
		return symbol(LexicalUnit.DO,yyline, yycolumn, yytext() ); 
	}
	"done" { 
		return symbol(LexicalUnit.DONE,yyline, yycolumn, yytext() ); 
	}
	"for" { 
		return symbol(LexicalUnit.FOR,yyline, yycolumn, yytext() ); 
	}
	"by" {
		return symbol(LexicalUnit.BY,yyline, yycolumn, yytext() ); 
	}
	"to" { 
		return symbol(LexicalUnit.TO,yyline, yycolumn, yytext() ); 
	}
	"from" { 
		return symbol(LexicalUnit.FROM, yyline, yycolumn, yytext() ); 
	}
	
	
	// Functions
	"func" {
		return symbol(LexicalUnit.FUNC,yyline, yycolumn, yytext() ); 
	}
	"endfunc" {
		return symbol(LexicalUnit.ENDFUNC,yyline, yycolumn, yytext() ); 
	}
	"with" { 
		return symbol(LexicalUnit.WITH, yyline, yycolumn, yytext() ); 
	}
	"in" {
		return symbol(LexicalUnit.IN, yyline, yycolumn, yytext() ); 
	}
	"return" {
		return symbol(LexicalUnit.RETURN, yyline, yycolumn, yytext() ); 
	}
	"call" {
		return symbol(LexicalUnit.CALL, yyline, yycolumn, yytext() ); 
	}

	// Print - Read
	"print" { 
		return symbol(LexicalUnit.PRINT, yyline, yycolumn, yytext() ); 
	}
	"read" { 
		return symbol(LexicalUnit.READ, yyline, yycolumn, yytext() ); 
	}

	// Varname
	{VarName} {
		Symbol sym = symbol(LexicalUnit.VARNAME, yyline, yycolumn, yytext() ); 
		if(!identifiersTable.containsKey(yytext())){
			identifiersTable.put(yytext(), yyline+1);
		}
		return sym;
	}
	
	// String
	\" { 
		string.setLength(0); yybegin(STRING); 
	}
	
	
	{BadInteger} {
		System.out.println("Warning: numbers with leading zeros are deprecated."); 
		return symbol(LexicalUnit.NUMBER, yyline, yycolumn, new Integer(yytext()));
	}
  	{Integer} {
  		return symbol(LexicalUnit.NUMBER, yyline, yycolumn, new Integer(yytext()));
  	}

	// Space
	{Space}|{EndOfLine} { }
}

<COMMENT> {
	"*)" { yybegin(YYINITIAL); }
	. {}
}

<STRING> {
	\"                             { yybegin(YYINITIAL); 
                                       return symbol(LexicalUnit.STRING, yyline, yycolumn,
                                       string.toString()); }
    [^\n\r\"\\]+                   { string.append( yytext() ); }
    \\t                            { string.append('\t'); }
    \\n                            { string.append('\n'); }

    \\r                            { string.append('\r'); }
    \\\"                           { string.append('\"'); }
    \\                             { string.append('\\'); }
}


// Ignore other characters
. {}