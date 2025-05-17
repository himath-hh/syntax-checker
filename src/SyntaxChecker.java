import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Himath Helessage
 * This is the main class which lets a user input a program and checks for its syntax
 */
public class SyntaxChecker {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //file path input
        System.out.print("Enter program file path: ");
        String programPath = input.nextLine();

        //opens the program file
        try (Scanner fileInput = new Scanner(new File(programPath))) {
            StringBuilder program = new StringBuilder();

            //places the input from the program in a string builder
            while (fileInput.hasNextLine()) {
                program.append(fileInput.nextLine() + " ");
            }

            //creates a lexer and passes it to the parser
            LexicalAnalyser lexer = new LexicalAnalyser(program.toString());
            Parser parser = new Parser(lexer);

            parser.parseProgram();

            //if we reach this statement, no errors are present in the program
            System.out.println("No syntax errors reported");

        //catches file read errors
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e);
        
        //catches syntax errors
        } catch (SyntaxException e) {
            System.err.println(e);
        }

        input.close();
    }
}
