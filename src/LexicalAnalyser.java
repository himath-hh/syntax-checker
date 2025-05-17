/**
 * @author Himath Helessage
 * This class performs a lexical analysis and recognizes tokens
 */
public class LexicalAnalyser {
    private String fileInput;
    private int currentPos;
    private int fileLength;

    /**
     * Constructor for a Lexical Analyser
     * @param fileInput the content of the program/file to be analysed
     */
    public LexicalAnalyser(String fileInput) {
        this.fileInput = fileInput;

        //sets the current position to 0, so analysis will start from the 0th index
        currentPos = 0;
        
        //final index of the file is set
        fileLength = fileInput.length();
    }

    public Token nextToken() {
        //checks for end of the file
        if (currentPos >= fileLength) {
            return new Token(TokenType.END_OF_FILE, "^Z");
        }

        //skips any blank characters in the file input
        while (currentPos < fileLength && fileInput.charAt(currentPos) == ' ') {
            currentPos++;
        }

        //checks for end of the file after blanks are skipped
        if (currentPos >= fileLength) {
            return new Token(TokenType.END_OF_FILE, "^Z");
        }

        //converts numbers to integer constants
        if (Character.isDigit(fileInput.charAt(currentPos))) {
            int startPos = currentPos;

            //find the end position of the integer
            while (Character.isDigit(fileInput.charAt(currentPos)) && currentPos < fileLength) {
                currentPos++;
            }

            //converts the int constant to token of type int_const with its value as a string
            return new Token(TokenType.INT_CONST, fileInput.substring(startPos, currentPos));
        }

        //converts words into keywords or identifiers
        if (Character.isLetter(fileInput.charAt(currentPos))) {
            int startPos = currentPos;

            //find the end position of the word
            while (Character.isLetterOrDigit(fileInput.charAt(currentPos)) && currentPos < fileLength) {
                currentPos++;
            }

            //completes a word for analysis
            String thisWord = fileInput.substring(startPos, currentPos);

            //where the words are keywords, tokens are created appropriately and returned
            if (thisWord.equals("program")) {
                return new Token(TokenType.PROGRAM, "program");

            } else if (thisWord.equals("begin")) {
                return new Token(TokenType.BEGIN, "begin");
            
            } else if (thisWord.equals("end")) {
                return new Token(TokenType.END, "end");

            } else if (thisWord.equals("if")) {
                return new Token(TokenType.IF, "if");

            } else if (thisWord.equals("then")) {
                return new Token(TokenType.THEN, "then");

            } else if (thisWord.equals("loop")) {
                return new Token(TokenType.LOOP, "loop");
            
            //final case where it is not a keyword or may contain digits
            } else {
                //an identifier token is returned with the word name as the variable name
                return new Token(TokenType.IDENTIFIER, thisWord);
            }
        }

        //for single character tokens
        switch (fileInput.charAt(currentPos++)) {
            case ';':
                return new Token(TokenType.SEMICOLON, ";");

            case '=':
                return new Token(TokenType.EQUALS, "=");
            
            case '+':
                return new Token(TokenType.PLUS, "+");
        
            case '-':
                return new Token(TokenType.MINUS, "-");
        
            case '*':
                return new Token(TokenType.MULTIPLY, "*");
        
            case '/':
                return new Token(TokenType.DIVIDE, "/");
        
            case '(':
                return new Token(TokenType.BRAC_OPEN, "(");
        
            case ')':
                return new Token(TokenType.BRAC_CLOSE, ")");
        
            case '<':
                return new Token(TokenType.LESSER_THAN, "<");
        
            case '>':
                return new Token(TokenType.GREATER_THAN, ">");
            
            //if nothing matches, throw an exception for an unrecognized character
            default:
                System.out.println("|"  + fileInput.charAt(currentPos) + "|");
                throw new SyntaxException("Unrecognized character at location: " + currentPos);
        }

    }
}