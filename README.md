# Syntax Checker

This project implements a simple **lexical analyzer** and **syntax checker** for a custom mini programming language. It processes input code, tokenizes it, and validates its syntactical correctness using recursive descent parsing.

## Features

- Lexical analysis: Tokenizes input into meaningful components (keywords, identifiers, operators, etc.)
- Syntax checking: Validates code structure using a parser
- Custom exception handling for syntax errors
- Modular design with separate components for each responsibility
- Command-line based execution and testability

## Project Structure

```
├── LexicalAnalyser.java      # Scans input and produces a list of tokens
├── Parser.java               # Parses the token list for syntax correctness
├── SyntaxChecker.java        # Main entry point for file parsing and validation
├── SyntaxException.java      # Custom exception for syntax errors
├── Token.java                # Represents a lexical token
└── TokenType.java            # Enum for different token types
```

## How to Run

**Run the Syntax Checker with a test input:**

   ```bash
   java -jar SyntaxChecker.jar
   ```

   *proceed to provide the path of the file.*

   If the input is correct, you will see:

   ```
   No syntax errors detected.
   ```

   If there are errors, a `SyntaxException` will be thrown with a helpful message.

## Token Types Supported

- **Keywords**: `program`, `begin`, `end`, `if`, `fi`, `then`
- **Identifiers**: Variable names (e.g., `var1`, `total`)
- **Operators**: `+`, `-`, `=`, `<`, `>`
- **Punctuation**: `(`, `)`, `;`

## Language Grammar

Below is the formal grammar definition for the supported mini programming language, written in Backus-Naur Form (BNF):

```
<program>           → program begin <statement_list> end 
<statement_list>    → <statement> { ; <statement> }
<statement>         → <assignment_statement> | <if_statement> | <loop_statement>
<assignment_statement> → <variable> = <expression>
<variable>          → identifier 
                      (An identifier is a string that begins with a letter followed by 0 or more letters and/or digits)
<expression>        → <term> { (+ | -) <term> }
<term>              → <factor> { (* | /) <factor> }
<factor>            → identifier | int_constant | (<expression>)
<if_statement>      → if (<logic_expression>) then <statement>
<logic_expression>  → <variable> (< | >) <variable> 
                      (Logic expressions have only < or > operators)
<loop_statement>    → loop (<logic_expression>) <statement>
```

## Example of a Program

```
program
begin

sum1 = var1 + var2;
sum2 = var3 + var2 * 90;
sum3 = (var2 + var1) * var3;

if (sum1 < sum2) then
    if (var1 > var2) then
        var4 = sum2 - sum1;

loop (var1 < var2)
    var5 = var4/45

end
```

## License

This project is licensed under the [MIT License](LICENSE.md).
