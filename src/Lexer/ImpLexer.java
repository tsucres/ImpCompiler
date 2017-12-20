package Lexer;
/* The following code was generated by JFlex 1.6.1 */

import java.util.HashMap;
import java.util.Collections;
import java.util.ArrayList;



/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.6.1
 */
public class ImpLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int COMMENT = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1, 1
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\0\1\4\1\43\1\43\1\3\22\0\1\0\7\0\1\6"+
    "\1\10\1\7\1\22\1\0\1\23\1\0\1\24\1\5\11\2\1\20"+
    "\1\17\1\32\1\21\1\31\2\0\32\1\6\0\1\27\1\11\1\1"+
    "\1\16\1\12\1\33\1\13\1\34\1\14\2\1\1\35\1\41\1\15"+
    "\1\25\1\42\1\1\1\30\1\36\1\26\2\1\1\37\1\1\1\40"+
    "\1\1\12\0\1\43\u1fa2\0\1\43\1\43\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\udfe6\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\1\1\1\2\1\3\1\4\1\3\1\5\1\6"+
    "\1\7\5\2\1\10\1\1\1\11\1\12\1\13\1\14"+
    "\4\2\1\15\1\16\3\2\1\4\1\1\1\17\1\20"+
    "\1\21\1\2\1\22\2\2\1\23\1\2\1\24\1\25"+
    "\1\26\1\27\3\2\1\30\1\31\1\32\4\2\1\33"+
    "\1\2\1\34\1\2\1\35\2\2\1\36\1\2\1\37"+
    "\5\2\1\40\1\41\1\42\1\43\1\44\2\2\1\45"+
    "\1\46\1\47\1\50";

  private static int [] zzUnpackAction() {
    int [] result = new int[81];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\44\0\110\0\154\0\220\0\264\0\330\0\374"+
    "\0\u0120\0\110\0\u0144\0\u0168\0\u018c\0\u01b0\0\u01d4\0\110"+
    "\0\u01f8\0\110\0\110\0\110\0\110\0\u021c\0\u0240\0\u0264"+
    "\0\u0288\0\u02ac\0\u02d0\0\u02f4\0\u0318\0\u033c\0\110\0\u0360"+
    "\0\330\0\110\0\110\0\u0384\0\154\0\u03a8\0\u03cc\0\154"+
    "\0\u03f0\0\u0414\0\110\0\154\0\154\0\u0438\0\u045c\0\u0480"+
    "\0\110\0\110\0\110\0\u04a4\0\u04c8\0\u04ec\0\u0510\0\110"+
    "\0\u0534\0\u0558\0\u057c\0\154\0\u05a0\0\u05c4\0\154\0\u05e8"+
    "\0\154\0\u060c\0\u0630\0\u0654\0\u0678\0\u069c\0\154\0\154"+
    "\0\154\0\154\0\154\0\u06c0\0\u06e4\0\154\0\154\0\154"+
    "\0\154";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[81];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\3\1\4\1\5\1\6\1\3\1\7\1\10\1\11"+
    "\1\12\1\13\1\14\1\4\1\15\1\16\1\17\1\20"+
    "\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30"+
    "\1\31\1\32\1\33\1\34\3\4\1\35\2\4\1\36"+
    "\1\37\3\3\2\37\2\3\1\40\33\3\1\37\45\0"+
    "\2\4\2\0\1\4\3\0\6\4\6\0\4\4\2\0"+
    "\10\4\3\0\1\5\2\0\1\5\42\0\1\3\41\0"+
    "\1\41\2\0\1\41\45\0\1\42\44\0\1\43\34\0"+
    "\2\4\2\0\1\4\3\0\1\4\1\44\4\4\6\0"+
    "\4\4\2\0\5\4\1\45\2\4\2\0\2\4\2\0"+
    "\1\4\3\0\4\4\1\46\1\4\6\0\4\4\2\0"+
    "\2\4\1\47\5\4\2\0\2\4\2\0\1\4\3\0"+
    "\6\4\6\0\4\4\2\0\1\50\7\4\2\0\2\4"+
    "\2\0\1\4\3\0\6\4\6\0\1\51\3\4\2\0"+
    "\10\4\2\0\2\4\2\0\1\4\3\0\6\4\6\0"+
    "\1\52\3\4\2\0\10\4\22\0\1\53\23\0\2\4"+
    "\2\0\1\4\3\0\6\4\6\0\3\4\1\54\2\0"+
    "\10\4\2\0\2\4\2\0\1\4\3\0\6\4\6\0"+
    "\1\55\3\4\2\0\1\4\1\56\6\4\2\0\2\4"+
    "\2\0\1\4\3\0\4\4\1\57\1\4\6\0\4\4"+
    "\2\0\10\4\2\0\2\4\2\0\1\4\3\0\1\4"+
    "\1\60\4\4\6\0\4\4\2\0\10\4\22\0\1\61"+
    "\43\0\1\62\7\0\1\63\13\0\2\4\2\0\1\4"+
    "\3\0\6\4\6\0\1\64\2\4\1\65\2\0\10\4"+
    "\2\0\2\4\2\0\1\4\3\0\6\4\6\0\4\4"+
    "\2\0\1\4\1\66\6\4\2\0\2\4\2\0\1\4"+
    "\3\0\6\4\6\0\3\4\1\67\2\0\10\4\11\0"+
    "\1\70\34\0\2\4\2\0\1\4\3\0\2\4\1\71"+
    "\3\4\6\0\4\4\2\0\10\4\2\0\2\4\2\0"+
    "\1\4\3\0\5\4\1\72\6\0\4\4\2\0\10\4"+
    "\2\0\2\4\2\0\1\4\3\0\6\4\6\0\4\4"+
    "\2\0\3\4\1\73\4\4\2\0\2\4\2\0\1\4"+
    "\3\0\6\4\6\0\1\4\1\74\2\4\2\0\10\4"+
    "\2\0\2\4\2\0\1\4\3\0\4\4\1\75\1\4"+
    "\6\0\4\4\2\0\10\4\2\0\2\4\2\0\1\4"+
    "\3\0\1\4\1\76\4\4\6\0\4\4\2\0\10\4"+
    "\2\0\2\4\2\0\1\4\3\0\5\4\1\77\6\0"+
    "\4\4\2\0\10\4\2\0\2\4\2\0\1\4\3\0"+
    "\6\4\6\0\2\4\1\100\1\4\2\0\10\4\2\0"+
    "\2\4\2\0\1\4\3\0\6\4\6\0\3\4\1\101"+
    "\2\0\10\4\2\0\2\4\2\0\1\4\3\0\6\4"+
    "\6\0\1\102\3\4\2\0\10\4\2\0\2\4\2\0"+
    "\1\4\3\0\3\4\1\103\2\4\6\0\4\4\2\0"+
    "\10\4\2\0\2\4\2\0\1\4\3\0\3\4\1\104"+
    "\2\4\6\0\4\4\2\0\10\4\2\0\2\4\2\0"+
    "\1\4\3\0\3\4\1\105\2\4\6\0\4\4\2\0"+
    "\10\4\2\0\2\4\2\0\1\4\3\0\3\4\1\106"+
    "\2\4\6\0\4\4\2\0\10\4\2\0\2\4\2\0"+
    "\1\4\3\0\1\4\1\107\4\4\6\0\4\4\2\0"+
    "\10\4\2\0\2\4\2\0\1\4\3\0\1\4\1\110"+
    "\4\4\6\0\4\4\2\0\10\4\2\0\2\4\2\0"+
    "\1\4\3\0\4\4\1\111\1\4\6\0\4\4\2\0"+
    "\10\4\2\0\2\4\2\0\1\4\3\0\5\4\1\112"+
    "\6\0\4\4\2\0\10\4\2\0\2\4\2\0\1\4"+
    "\3\0\6\4\6\0\4\4\2\0\6\4\1\113\1\4"+
    "\2\0\2\4\2\0\1\4\3\0\6\4\6\0\4\4"+
    "\2\0\2\4\1\114\5\4\2\0\2\4\2\0\1\4"+
    "\3\0\4\4\1\115\1\4\6\0\4\4\2\0\10\4"+
    "\2\0\2\4\2\0\1\4\3\0\4\4\1\116\1\4"+
    "\6\0\4\4\2\0\10\4\2\0\2\4\2\0\1\4"+
    "\3\0\6\4\6\0\4\4\2\0\1\117\7\4\2\0"+
    "\2\4\2\0\1\4\3\0\1\4\1\120\4\4\6\0"+
    "\4\4\2\0\10\4\2\0\2\4\2\0\1\4\3\0"+
    "\6\4\6\0\1\4\1\121\2\4\2\0\10\4\1\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[1800];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\0\1\11\6\1\1\11\5\1\1\11\1\1\4\11"+
    "\11\1\1\11\2\1\2\11\7\1\1\11\5\1\3\11"+
    "\4\1\1\11\31\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[81];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;
  
  /** 
   * The number of occupied positions in zzBuffer beyond zzEndRead.
   * When a lead/high surrogate has been read from the input stream
   * into the final zzBuffer position, this will have a value of 1;
   * otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /* user code: */
	public HashMap<String,Integer> identifiersTable = new HashMap<String,Integer>();

	private Symbol symbol(LexicalUnit unit,int line,int column,Object value){
		Symbol sym = new Symbol(unit,line, column, value ); 
		// System.out.println(sym.toString()); 
		return sym; 
	}


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public ImpLexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x110000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 146) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException("Reader returned 0 characters. See JFlex examples for workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      /* If numRead == requested, we might have requested to few chars to
         encode a full Unicode character. We assume that a Reader would
         otherwise never return half characters. */
      if (numRead == requested) {
        if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    zzFinalHighSurrogate = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE)
      zzBuffer = new char[ZZ_BUFFERSIZE];
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() {
    if (!zzEOFDone) {
      zzEOFDone = true;
    	/*System.out.println("\nIdentifiers");

	ArrayList<String> ids = new ArrayList<String>(identifiersTable.keySet());
	Collections.sort(ids, String.CASE_INSENSITIVE_ORDER);

	for(String id : ids) {
 		System.out.println(id + " " + identifiersTable.get(id));
	}*/

    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public Symbol yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn += zzCharCount;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
            zzDoEOF();
          { 	return new Symbol(LexicalUnit.EOS, yyline, yycolumn, "[EOS]");
 }
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1: 
            { 
            }
          case 41: break;
          case 2: 
            { Symbol sym = symbol(LexicalUnit.VARNAME, yyline, yycolumn, yytext() ); 
		if(!identifiersTable.containsKey(yytext())){
			identifiersTable.put(yytext(), yyline+1);
		}
		return sym;
            }
          case 42: break;
          case 3: 
            { return symbol(LexicalUnit.NUMBER, yyline, yycolumn, new Integer(yytext()));
            }
          case 43: break;
          case 4: 
            { System.out.print(yytext());
            }
          case 44: break;
          case 5: 
            { return symbol(LexicalUnit.LPAREN,yyline, yycolumn, yytext() );
            }
          case 45: break;
          case 6: 
            { return symbol(LexicalUnit.TIMES,yyline, yycolumn, yytext() );
            }
          case 46: break;
          case 7: 
            { return symbol(LexicalUnit.RPAREN,yyline, yycolumn, yytext() );
            }
          case 47: break;
          case 8: 
            { return symbol(LexicalUnit.SEMICOLON,yyline, yycolumn, yytext() );
            }
          case 48: break;
          case 9: 
            { return symbol(LexicalUnit.EQ,yyline, yycolumn, yytext() );
            }
          case 49: break;
          case 10: 
            { return symbol(LexicalUnit.PLUS,yyline, yycolumn, yytext() );
            }
          case 50: break;
          case 11: 
            { return symbol(LexicalUnit.MINUS,yyline, yycolumn, yytext() );
            }
          case 51: break;
          case 12: 
            { return symbol(LexicalUnit.DIVIDE,yyline, yycolumn, yytext() );
            }
          case 52: break;
          case 13: 
            { return symbol(LexicalUnit.GT,yyline, yycolumn, yytext() );
            }
          case 53: break;
          case 14: 
            { return symbol(LexicalUnit.LT,yyline, yycolumn, yytext() );
            }
          case 54: break;
          case 15: 
            { System.out.println("Warning: numbers with leading zeros are deprecated."); 
		return symbol(LexicalUnit.NUMBER, yyline, yycolumn, new Integer(yytext()));
            }
          case 55: break;
          case 16: 
            { yybegin(COMMENT);
            }
          case 56: break;
          case 17: 
            { System.out.println("Error: unmatched (*");
            }
          case 57: break;
          case 18: 
            { return symbol(LexicalUnit.BY,yyline, yycolumn, yytext() );
            }
          case 58: break;
          case 19: 
            { return symbol(LexicalUnit.IF,yyline, yycolumn, yytext() );
            }
          case 59: break;
          case 20: 
            { return symbol(LexicalUnit.DO,yyline, yycolumn, yytext() );
            }
          case 60: break;
          case 21: 
            { return symbol(LexicalUnit.ASSIGN,yyline, yycolumn, yytext() );
            }
          case 61: break;
          case 22: 
            { return symbol(LexicalUnit.OR,yyline, yycolumn, yytext() );
            }
          case 62: break;
          case 23: 
            { return symbol(LexicalUnit.TO,yyline, yycolumn, yytext() );
            }
          case 63: break;
          case 24: 
            { return symbol(LexicalUnit.GEQ,yyline, yycolumn, yytext() );
            }
          case 64: break;
          case 25: 
            { return symbol(LexicalUnit.LEQ,yyline, yycolumn, yytext() );
            }
          case 65: break;
          case 26: 
            { return symbol(LexicalUnit.NEQ,yyline, yycolumn, yytext() );
            }
          case 66: break;
          case 27: 
            { yybegin(YYINITIAL);
            }
          case 67: break;
          case 28: 
            { return symbol(LexicalUnit.END,yyline, yycolumn, yytext() );
            }
          case 68: break;
          case 29: 
            { return symbol(LexicalUnit.NOT,yyline, yycolumn, yytext() );
            }
          case 69: break;
          case 30: 
            { return symbol(LexicalUnit.AND,yyline, yycolumn, yytext() );
            }
          case 70: break;
          case 31: 
            { return symbol(LexicalUnit.FOR,yyline, yycolumn, yytext() );
            }
          case 71: break;
          case 32: 
            { return symbol(LexicalUnit.ELSE,yyline, yycolumn, yytext() );
            }
          case 72: break;
          case 33: 
            { return symbol(LexicalUnit.DONE,yyline, yycolumn, yytext() );
            }
          case 73: break;
          case 34: 
            { return symbol(LexicalUnit.THEN,yyline, yycolumn, yytext() );
            }
          case 74: break;
          case 35: 
            { return symbol(LexicalUnit.READ, yyline, yycolumn, yytext() );
            }
          case 75: break;
          case 36: 
            { return symbol(LexicalUnit.FROM, yyline, yycolumn, yytext() );
            }
          case 76: break;
          case 37: 
            { return symbol(LexicalUnit.BEGIN,yyline, yycolumn, yytext() );
            }
          case 77: break;
          case 38: 
            { return symbol(LexicalUnit.ENDIF,yyline, yycolumn, yytext() );
            }
          case 78: break;
          case 39: 
            { return symbol(LexicalUnit.WHILE,yyline, yycolumn, yytext() );
            }
          case 79: break;
          case 40: 
            { return symbol(LexicalUnit.PRINT, yyline, yycolumn, yytext() );
            }
          case 80: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }

  /**
   * Runs the scanner on input files.
   *
   * This is a standalone scanner, it will print any unmatched
   * text to System.out unchanged.
   *
   * @param argv   the command line, contains the filenames to run
   *               the scanner on.
   */
  public static void main(String argv[]) {
    if (argv.length == 0) {
      System.out.println("Usage : java ImpLexer [ --encoding <name> ] <inputfile(s)>");
    }
    else {
      int firstFilePos = 0;
      String encodingName = "UTF-8";
      if (argv[0].equals("--encoding")) {
        firstFilePos = 2;
        encodingName = argv[1];
        try {
          java.nio.charset.Charset.forName(encodingName); // Side-effect: is encodingName valid? 
        } catch (Exception e) {
          System.out.println("Invalid encoding '" + encodingName + "'");
          return;
        }
      }
      for (int i = firstFilePos; i < argv.length; i++) {
        ImpLexer scanner = null;
        try {
          java.io.FileInputStream stream = new java.io.FileInputStream(argv[i]);
          java.io.Reader reader = new java.io.InputStreamReader(stream, encodingName);
          scanner = new ImpLexer(reader);
          while ( !scanner.zzAtEOF ) scanner.yylex();
        }
        catch (java.io.FileNotFoundException e) {
          System.out.println("File not found : \""+argv[i]+"\"");
        }
        catch (java.io.IOException e) {
          System.out.println("IO error scanning file \""+argv[i]+"\"");
          System.out.println(e);
        }
        catch (Exception e) {
          System.out.println("Unexpected exception:");
          e.printStackTrace();
        }
      }
    }
  }


}