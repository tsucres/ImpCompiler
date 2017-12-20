import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.TreeMap;

import AST.AST;
import AST.ASTException;
import LLVM.LLVMGenerator;
import Lexer.ImpLexer;
import Lexer.LexicalUnit;
import Lexer.Symbol;
import Parser.Parser;
import TreePrinter.TreePrinter;

import java.util.Map;


public class Main {
	public static void main(String [] args) throws FileNotFoundException, IOException, SecurityException {
		if(args.length < 1){
            System.out.println("Usage:  java -jar part3.jar file.imp [out.ll]\n"
                             + "or\tjava "+Main.class.getSimpleName()+" file.imp [out.ll]");
            System.exit(0);
        }
		

        FileReader source = new FileReader(args[0]);
        final ImpLexer analyzer = new ImpLexer(source);
        
        TreeMap<String,Symbol> identifiersTable = new TreeMap<String,Symbol>();
        ArrayList<Symbol> symbols = new ArrayList<Symbol>();
        Symbol symbol = null;
        // We iterate while we do not reach the end of the file (marked by EOS)
        while(!(symbol = analyzer.yylex()).getType().equals(LexicalUnit.EOS)){
            symbols.add(symbol);
            // If it is a variable, add it to the table
            if(symbol.getType().equals(LexicalUnit.VARNAME)){
                if(!identifiersTable.containsKey(symbol.getValue())){
                    identifiersTable.put(symbol.getValue().toString(),symbol);
                }
            }
        }

        for(Map.Entry<String, Symbol> identifier : identifiersTable.entrySet())
            System.out.println(identifier.getKey()+"\t"+(identifier.getValue().getLine()));


        Parser p = new Parser(symbols);
        AST ast = new AST();
        
        try {
            ast.buildASTFromParserTree(p.getTree());
            System.out.print(TreePrinter.toString(ast.getAST()));
        } catch(ASTException ex) {
            System.out.println(ex);
        }
        LLVMGenerator generator = new LLVMGenerator();
        String llvmIrCode = generator.generateFromAST(ast.getAST(), identifiersTable);
        System.out.println(llvmIrCode);
        	if (args.length == 2) {
        		PrintWriter writer = new PrintWriter(args[1], "UTF-8");
        		writer.print(llvmIrCode);
        		writer.close();
		}
        
	}

}
