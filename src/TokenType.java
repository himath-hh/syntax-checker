/**
 * @author Himath Helessage
 * This enumerated type contains all possible types of tokens
 */
enum TokenType {
    PROGRAM, BEGIN, END,
    IF, THEN, LOOP,
    IDENTIFIER, INT_CONST,
    EQUALS, LESSER_THAN, GREATER_THAN,
    PLUS, MINUS, MULTIPLY, DIVIDE,
    SEMICOLON, BRAC_OPEN, BRAC_CLOSE,
    END_OF_FILE;
}
