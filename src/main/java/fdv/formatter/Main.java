package fdv.formatter;

import fdv.formatter.helpers.AlignmentMode;
import fdv.formatter.workers.TextFormatter;

import java.util.Scanner;

/**
 * Main is the application entry point.

 * The purpose of this class:
 * - it starts the program
 * - it reads command-line arguments
 * - it reads standard input
 * - it delegates the real formatting work to TextFormatter

 * Why Main is intentionally small:
 * - business logic should live in dedicated classes, not in the entry point
 */
public class Main {

    /**
     * Starts the application.

     * What it does:
     * - validates command-line arguments
     * - converts the mode and width
     * - reads the full text from standard input
     * - formats the text
     * - prints the result

     * Why it does it:
     * - this is the starting point Java looks for when running the jar

     * How it works:
     * - expect exactly two arguments: mode and width
     * - parse mode with AlignmentMode.fromString(...)
     * - parse width with Integer.parseInt(...)
     * - read all stdin using Scanner
     * - call TextFormatter.format(...)
     * - print the final result
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            printUsageAndExit();
        }

        try {
            AlignmentMode mode = AlignmentMode.fromString(args[0]);
            int width = Integer.parseInt(args[1]);

            Scanner scanner = new Scanner(System.in).useDelimiter("\\A");
            String input = scanner.hasNext() ? scanner.next() : "";

            TextFormatter formatter = new TextFormatter();
            String result = formatter.format(input, mode, width);

            System.out.print(result);

        } catch (NumberFormatException e) {
            System.err.println("Width must be a number.");
            printUsageAndExit();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Prints valid usage and exits the application.

     * What it does:
     * - prints the correct command format
     * - exits the application with an error code

     * Why it does it:
     * - keeps error handling inside main() shorter and clearer

     * How it works:
     * - writes to standard error
     * - calls System.exit(1)
     */
    private static void printUsageAndExit() {
        System.err.println("Usage: java -jar formatter.jar <left|right|center|justify|hard> <width>");
        System.exit(1);
    }
}