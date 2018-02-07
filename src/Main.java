import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import AST.AST;
import AST.ASTException;
import LLVM.LLVMGenerator;
import Lexer.ImpLexer;
import Lexer.LexicalUnit;
import Lexer.Symbol;
import Parser.Parser;
import TreePrinter.TreePrinter;


class Args {
	public boolean debug = false;
	public String outputFilePath = null;
	public boolean exec = false;
	public String inputFilePath;
	public Args(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}
}

public class Main {
	private static void runLLI(String programCode) throws IOException, InterruptedException {
		String[] command =
		    {
		        "lli",
		    };
		    Process p = Runtime.getRuntime().exec(command);
		    new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
		    new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
		    new Thread(new SyncPipe(System.in, p.getOutputStream())).start();
		    PrintWriter stdin = new PrintWriter(p.getOutputStream());
		    stdin.println(programCode);
		    
		    stdin.close();
		    int returnCode = p.waitFor();
		    System.out.println("Return code = " + returnCode);
	}
	/**
	 * Parse the CLI arguments and construct a corresponding Args instance. 
	 * If the arguments aren't valid, print an error and exit.
	 * @param args The arguments passed to the main from the CLI
	 * @return an instance of Args filled with the valid CLI arguments
	 */
	private static Args parseArgs(String [] args) {
		String usage = "Usage:  java -jar part3.jar file.imp [out.ll] [--debug] [--exec]\n"
                + "or\tjava "+Main.class.getSimpleName()+" file.imp  [out.ll] [--debug] [--exec]\n\n"
                		+ "--debug\t\t prints the ParserTree and the AST\n"
                		+ "--exec\t\t executes the resulting LLVM code (lli must be an accessible command)";
		
		if(args.length < 1){
			System.out.println(usage);
			System.exit(0);
		}
		File inputFile = new File(args[0]);
		if (!(inputFile.exists() && !inputFile.isDirectory())) {
			System.out.println(usage);
			System.exit(0);
		}
		Args parsedArgs = new Args(args[0]);
		for (int i = 1; i < args.length; i++) {
			if (args[i].equals("--debug")) {
				parsedArgs.debug = true;
			} else if (args[i].equals("--exec")) {
				parsedArgs.exec = true;
			} else {
				File outFile = new File(args[i]);
				if (outFile != null && !outFile.isDirectory()) {
					parsedArgs.outputFilePath = args[i];
				} else {
					System.out.println("The specified path for the output file doesn't seem valid.");
				}
			}
		}
		return parsedArgs;
	}
	
	public static void main(String [] args) throws FileNotFoundException, IOException, InterruptedException {
		Args parsedArgs = Main.parseArgs(args);
		
        FileReader source = new FileReader(parsedArgs.inputFilePath);
        final ImpLexer analyzer = new ImpLexer(source);
        
        ArrayList<Symbol> symbols = new ArrayList<Symbol>();
        Symbol symbol = null;
        // We iterate while we do not reach the end of the file (marked by EOS)
        while(!(symbol = analyzer.yylex()).getType().equals(LexicalUnit.EOS)){
            symbols.add(symbol);
        }

        Parser p = new Parser(symbols);
        if (parsedArgs.debug) {
        		System.out.println("Parsing done...\n\n");
        		System.out.println("Parser tree: \n");
            System.out.print(TreePrinter.toString(p.getTree()));
            System.out.println("Identifier tables: \n");
            System.out.print(TreePrinter.toString(p.getIdentifierTablesTree()));
            System.out.print("\n\n\n");
        }
        AST ast = new AST();
        
        try {
            ast.buildASTFromParserTree(p.getTree());
            if (parsedArgs.debug) {
            		System.out.print(TreePrinter.toString(ast.getAST()));
            }
        } catch(ASTException ex) {
            System.err.println(ex);
        }
        
        LLVMGenerator generator = new LLVMGenerator();
        String llvmIrCode = generator.generateFromAST(ast.getAST(), p.getIdentifierTablesTree());
        
        System.out.println(llvmIrCode);
        	if (parsedArgs.outputFilePath != null) {
        		PrintWriter writer = new PrintWriter(parsedArgs.outputFilePath, "UTF-8");
        		writer.print(llvmIrCode);
        		writer.close();
		}
        	
        	if (parsedArgs.exec) {
        		Main.runLLI(llvmIrCode);
        	}
        
	}

}
