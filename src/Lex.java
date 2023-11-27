/*
 * Student:   Eliana Gaul
 * Professor: Dr. Salimi
 * Course: 	  CSCI4200: Programming Languages
 * Date:      15 November 2023
 */

import java.util.*;
import java.io.*;

// Custom exception to handle errors
class CustomException extends Exception {
    public CustomException(String errorMessage) {
        super(errorMessage);
    }
}

public class Lex {
    private final int MAX = 1000;
    private FileWriter myOutput;
    private String[] lexemeList;
    private String[] tokenList = new String[this.MAX];

    // Driver method
    // @throws IOException
    public static void main(String[] args) throws IOException {

        //Class object instantiation to call methods
        Lex lexAnalyze = new Lex();

        //String and Scanner objects for input
        String line;
        Scanner scan;

        lexAnalyze.myOutput = new FileWriter("lexOutput.txt");

        // Print Header Info to Terminal and Output File
        String headerInfo = "Eliana Gaul, CSCI4200, Fall 2023, Lexical Analyzer";
        String headerAsterisks = "*".repeat(80);
        System.out.print(headerInfo + "\n");
        lexAnalyze.myOutput.write(headerInfo + "\n");
        System.out.print(headerAsterisks + "\n");
        lexAnalyze.myOutput.write(headerAsterisks + "\n");

        // Open the file, and scan each line for lexical analysis
        try {
            scan = new Scanner(new File("lexInput.txt"));

            // For each line, get each character
            while (scan.hasNextLine()) {

                line = scan.nextLine().trim();// Trims leading and trailing whitespace from each line

                if(!line.isEmpty()){// Skip blank lines in input file

                    //Separates the input line into lexemes and stores the lexemes in a global String array.
                    lexAnalyze.getLexemes(line);

                    //Tokenizes lexemes and stores the tokens in a global String array.
                    lexAnalyze.getTokens();

                    //Outputs lexemes and tokens
                    lexAnalyze.setMyOutput(line);
                }
            }
            // If there are no more lines, it must be the end of file
            System.out.printf("Next token is: %-18s Next lexeme is %s\n", "END_OF_FILE", "EOF");
            lexAnalyze.myOutput.write(String.format("Next token is: %-18s Next lexeme is %s\n", "END_OF_FILE", "EOF"));
            System.out.print("Lexical analysis of the program is complete!\n");
            lexAnalyze.myOutput.write("Lexical analysis of the program is complete!");

            scan.close();
        }
        // Exceptions / errors are printed to terminal and to output file
        catch (FileNotFoundException e) {
            System.out.println("Cannot find file OR Problem with output file creation.");
            lexAnalyze.myOutput.write("Cannot find file OR Problem with output file creation." + "\n");
            System.out.println(e);
            lexAnalyze.myOutput.write(e.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            String eStackTrace = stringWriter.toString();
            lexAnalyze.myOutput.write(eStackTrace);
        }
        lexAnalyze.myOutput.close();
    }
    private void getLexemes(String uglyLine){
        //Multiple String.replace method calls adds spaces to special operators
        String prettyLine = uglyLine.replace("=", " = ")
                .replace("(", " ( ")
                .replace(")", " ) ")
                .replace("+", " + ")
                .replace("-", " - ")
                .replace("/", " / ")
                .replace("*", " * ")
                .replace(";"," ; ");
        //Splits the modified line by one or more spaces
        this.lexemeList = prettyLine.split("\\s+");
    }
    private void getTokens(){
        //iterates lexemeList
        for (int i = 0; i < lexemeList.length; i++) {
            switch (lexemeList[i]) {//Compares lexemes to strings and assigns the appropriate token
                case "(":
                    tokenList[i] = "LEFT_PAREN";
                    break;
                case ")":
                    tokenList[i] = "RIGHT_PAREN";
                    break;
                case "=":
                    tokenList[i] = "ASSIGN_OP";
                    break;
                case "+":
                    tokenList[i] = "ADD_OP";
                    break;
                case "-":
                    tokenList[i] = "SUB_OP";
                    break;
                case "/":
                    tokenList[i] = "DIV_OP";
                    break;
                case "*":
                    tokenList[i] = "MULT_OP";
                    break;
                case ";":
                    tokenList[i] = "SEMICOLON";
                    break;
                case "if":
                    tokenList[i] = "IF_KEYWORD";
                    break;
                case "print":
                    tokenList[i] = "PRINT_KEYWORD";
                    break;
                case "then":
                    tokenList[i] = "THEN_KEYWORD";
                    break;
                case "read":
                    tokenList[i] = "READ_KEYWORD";
                    break;
                case "PROGRAM":
                    tokenList[i] = "PROGRAM_KEYWORD";
                    break;
                case "END":
                    tokenList[i] = "END_KEYWORD";
                    break;
                default:
                    if (lexemeList[i].matches("\\d+"))tokenList[i] = "INT_LIT";//Checks if lexeme is literal Integer by regex
                    else tokenList[i] = "IDENT";//sets token to IDENT if the lexeme is not a special character or Integer literal
            }
        }
    }
    private void setMyOutput(String line) throws IOException {
        //Prints and writes input line to terminal and output file
        System.out.println(line);
        myOutput.write(line + "\n");

        //Prints and writes each lexeme and token to terminal and output file
        for (int i = 0; i < this.lexemeList.length; i++){
            System.out.printf("Next token is: %-18s Next lexeme is %s\n", this.tokenList[i], this.lexemeList[i]);
            myOutput.write(String.format("Next token is: %-18s Next lexeme is %s\n", this.tokenList[i], this.lexemeList[i]));
        }

        //prints and writes newline to terminal and output file for readability
        System.out.print("\n");
        myOutput.write("\n");
    }
}