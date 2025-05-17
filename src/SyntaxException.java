/**
 * @author Himath Helessage
 * This class handles all syntax errors during analysis
 */
public class SyntaxException extends RuntimeException {
    public SyntaxException(String message) {
        super("Syntax Error: " + message);
    }
}