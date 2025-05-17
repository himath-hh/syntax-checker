/**
 * @author Himath Helessage
 * This class implements a recursive descent parser based on the provided grammar
 */
public class Parser {
    private LexicalAnalyser lexer;
    private Token thisToken;

    /**
     * Constructor for a Parser
     * @param lexer the lexical analyser with the program input
     */
    public Parser(LexicalAnalyser lexer) {
        this.lexer = lexer;
        this.thisToken = lexer.nextToken();
    }

    //Grammar : <program> -> program begin <statement_list> end
    public void parseProgram() {
        System.out.println("Enter <program>");

        //evaluate tokens program and begin
        evaluate(TokenType.PROGRAM);
        evaluate(TokenType.BEGIN);

        //parse the statement list
        parseStatementList();

        //evaluate the end token
        evaluate(TokenType.END);

        System.out.println("Exit <program>");
        
        //make sure the end of file is reached
        evaluate(TokenType.END_OF_FILE);
    }

    //Grammar : <statement_list> -> <statement> {;<statement>}
    private void parseStatementList() {
        System.out.println("Enter <statement_list>");

        //parse the statement
        parseStatement();

        //keep on parsing statements as long as there exists semicolons
        while (thisToken.tokenType == TokenType.SEMICOLON) {
            consume();
            parseStatement();
        }

        System.out.println("Exit <statement_list>");
    }

    //Grammar : <assignment_statement> | <if_statement> | <loop_statement>
    private void parseStatement() {
        System.out.println("Enter <statement>");

        //identifier token means an assignment
        if (thisToken.tokenType == TokenType.IDENTIFIER) {
            parseAssignmentStatement();

        //if statement
        } else if (thisToken.tokenType == TokenType.IF) {
            parseIfStatement();

        //loop token
        } else if (thisToken.tokenType == TokenType.LOOP) {
            parseLoopStatement();
        
        //token doesn't match the 3 statement types
        } else {
            throw new SyntaxException("Expected 'if', 'loop' or IDENTIFIER. Unexpected token found: " + thisToken.toString());
        }

        System.out.println("Exit <statement>");
    }

    //Grammar : <loop_statement> -> loop (<logic_expression>) <statement>
    private void parseLoopStatement() {
        System.out.println("Enter <loop_statement>");

        //check for tokens loop and open bracket
        evaluate(TokenType.LOOP);
        evaluate(TokenType.BRAC_OPEN);

        //logic expression between brackets
        parseLogicExpression();

        //parse the closing bracket
        evaluate(TokenType.BRAC_CLOSE);
        
        //parse the statements inside the loop
        parseStatement();
        
        System.out.println("Exit <loop_statement>");
    }

    //Grammar : if (<logic_expression>) then <statement>
    private void parseIfStatement() {
        System.out.println("Enter <if_statement>");

        //check for tokens if and open bracket
        evaluate(TokenType.IF);
        evaluate(TokenType.BRAC_OPEN);

        //logic expression within brackets
        parseLogicExpression();

        //check for closing bracket and then tokens
        evaluate(TokenType.BRAC_CLOSE);
        evaluate(TokenType.THEN);

        //prase the statements inside the if statement
        parseStatement();

        System.out.println("Exit <if_statement>");
    }

    //Grammar : <logic_expression> -> <variable> (< | >) <variable>
    private void parseLogicExpression() {
        System.out.println("Enter <logic_expression>");

        //parse first variable
        parseVariable();

        //check for less than or greater than tokens
        if (thisToken.tokenType == TokenType.LESSER_THAN) {
            consume();

        } else if (thisToken.tokenType == TokenType.GREATER_THAN) {
            consume();

        //throw error for any other token
        } else {
            throw new SyntaxException("Expected '<' or '>'. Found: " + thisToken.toString());
        }

        //parse second variable
        parseVariable();

        System.out.println("Exit <logic_expression>");
    }

    //Grammar : <assignment_statement> -> <variable> = <expression>
    private void parseAssignmentStatement() {
        System.out.println("Enter <assignment_statement>");

        //parse the variable at first
        parseVariable();

        //check for equals operator
        evaluate(TokenType.EQUALS);

        //parse assignment expression
        parseExpression();

        System.out.println("Exit <assignment_statement>");
    }

    //Grammar : <expression> -> <term> { (+ | -) <term> }
    private void parseExpression() {
        System.out.println("Enter <expression>");

        //parse the first term
        parseTerm();

        //continue parsing multiple as long as there is a + or - token in between the term token and operator
        while (thisToken.tokenType == TokenType.PLUS || thisToken.tokenType == TokenType.MINUS) {
            consume();
            parseTerm();
        }

        System.out.println("Exit <expression>");
    }

    //Grammar : <term> -> <factor> { (* | /) <factor> }
    private void parseTerm() {
        System.out.println("Enter <term>");

        //parse the first factor
        parseFactor();
        
        //continue parsing multiple as long as there is a * or / token in between the term token and operator
        while (thisToken.tokenType == TokenType.MULTIPLY || thisToken.tokenType == TokenType.DIVIDE) {
            consume();
            parseFactor();
        }

        System.out.println("Exit <term>");
    }

    // Grammar : <factor> -> identifier | int_constant | (<expression>)
    private void parseFactor() {
        System.out.println("Enter <factor>");

        //check for an identifier token
        if (thisToken.tokenType == TokenType.IDENTIFIER) {
            consume();

        //for an integer constant
        } else if (thisToken.tokenType == TokenType.INT_CONST) {
            consume();

        //for an expression within
        } else if (thisToken.tokenType == TokenType.BRAC_OPEN) {
            consume();
            //consumes the bracket and evaluates the expression
            parseExpression();

            //consumes the closing bracket
            evaluate(TokenType.BRAC_CLOSE);

        //throw an error for any other token
        } else {
            throw new SyntaxException("Expected IDENTIFIER, INT_CONSTANT or (<expression>). Found: " + thisToken.toString());
        }

        System.out.println("Exit <factor>");
    }

    //Grammar : <variable> -> identifier
    private void parseVariable() {
        System.out.println("Enter <variable>");

        //checks for the identifer token
        evaluate(TokenType.IDENTIFIER);

        System.out.println("Exit <variable>");
    }

    /**
     * This method evaluates whether a token is valid within a given syntax or not
     * @param expected The token type that would pass the syntax check
     */
    public void evaluate(TokenType expected) {
        //consumes the token if the currrent token matches the expected token
        if (thisToken.tokenType == expected) {
            consume();

        //throws an error if not
        } else {
            unexpectedTokenError(expected);
        }
    }

    /**
     * This method consumes a token via the lexer method
     */
    public void consume() {
        System.out.println("Consumed Token: " + thisToken.toString());
        thisToken = lexer.nextToken();
    }

    /**
     * This method throws an error if token types don't match
     * @param expected The token type that would pass the syntax check
     */
    public void unexpectedTokenError(TokenType expected) {
        throw new SyntaxException("Expected: " + expected + ". Found: " + thisToken.toString());
    }
}
