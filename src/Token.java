/**
 * @author Himath Helessage
 * This class encapsulates a token as its lexeme and type
 */
public class Token {
    TokenType tokenType;
    String lex;

    /**
     * Constructor for a token
     * @param tokenType enum type of token
     * @param lex string recognized as a token
     */
    public Token(TokenType tokenType, String lex) {
        this.tokenType = tokenType;
        this.lex = lex;
    }

    /**
     * Method for printing
     */
    @Override
    public String toString() {
        return (lex + " | " + tokenType);
    }
}
