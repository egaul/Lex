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
    public static FileWriter myOutput;
    //public String[] lexemeList;

    public String[] tokenList;

    // Driver method
    // @throws IOException
    public static void main(String[] args) throws IOException {

        Lex run = new Lex();
        String line;
        Scanner scan;
        myOutput = new FileWriter("lexOutput.txt");

        // Print Header Info to Terminal and Output File
        String headerInfo = "Eliana Gaul, CSCI4200, Fall 2023, Lexical Analyzer";
        String headerAsterisks = "*".repeat(80);
        System.out.print(headerInfo + "\n");
        myOutput.write(headerInfo + "\n");
        System.out.print(headerAsterisks + "\n");
        myOutput.write(headerAsterisks + "\n");

        // Open the file, and scan each line for lexical analysis
        try {
            scan = new Scanner(new File("lexInput.txt"));

            // For each line, get each character
            while (scan.hasNextLine()) {

                line = scan.nextLine().trim();         // Trims leading and trailing whitespace from each line

                if(!line.isEmpty()){            // Skip blank lines in input file
                    System.out.println(line);
                    myOutput.write(line + "\n");

                    String[] lexemeList = run.getLexemes(line);

                    System.out.println(Arrays.toString(lexemeList));

                    System.out.print("\n");
                    myOutput.write("\n");
                }
            }
            // If there are no more lines, it must be the end of file
            System.out.printf("Next token is: %-18s Next lexeme is %s\n", "END_OF_FILE", "EOF");
            myOutput.write(String.format("Next token is: %-18s Next lexeme is %s\n", "END_OF_FILE", "EOF"));
            System.out.print("Lexical analysis of the program is complete!\n");
            myOutput.write("Lexical analysis of the program is complete!");

            scan.close();
        }

        // Exceptions / errors are printed to terminal and to output file
        catch (FileNotFoundException e) {
            System.out.println("Cannot find file OR Problem with output file creation.");
            myOutput.write("Cannot find file OR Problem with output file creation." + "\n");
            System.out.println(e.toString());
            myOutput.write(e.toString());
        }

        catch (Exception e) {
            e.printStackTrace();
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            String eStackTrace = stringWriter.toString();
            myOutput.write(eStackTrace);
        }

        myOutput.close();
    }

    public String[] getLexemes(String line){//Make method pretty and send to Professor.

        String prettyLine = line.replace("=", " = ").replace("(", " ( ").replace(")", " ) ").replace("+", " + ").replace("-", " - ").replace("/", " / ").replace("*", " * ");
        String[] lexemes = prettyLine.split("\\s+");
        return lexemes;
    }
}