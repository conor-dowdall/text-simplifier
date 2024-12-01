package ie.atu.sw.console;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import ie.atu.sw.util.InputReaderInterface;
import ie.atu.sw.util.PrinterInterface;

/**
 * Implementation of the InputReaderInterface for reading input from the
 * console.
 * Uses a Scanner to read user input and a PrinterInterface to display prompts
 * and messages.
 */
public class ConsoleInputReader implements InputReaderInterface {

    Scanner scanner;
    PrinterInterface printer;

    /**
     * Constructs a new ConsoleInputReader with the given scanner and printer.
     * 
     * @param scanner the Scanner to read user input from the console
     * @param printer the PrinterInterface to print prompts and messages to the
     *                console
     */
    public ConsoleInputReader(Scanner scanner, PrinterInterface printer) {
        this.scanner = scanner;
        this.printer = printer;
    }

    /**
     * Prompts the user to enter a file name, with validation for file existence if
     * specified.
     * 
     * @param fileNameDescription a description of the file to be entered, shown to
     *                            the user
     * @param defaultFileName     the default file name to use if the user presses
     *                            ENTER without input
     * @param checkIfFileExists   whether to check if the file exists before
     *                            accepting the input
     * @return the file name entered by the user, or the default file name if input
     *         is empty
     * @throws FileNotFoundException if the file does not exist and
     *                               checkIfFileExists is true
     */

    @Override
    public String getFileName(
            String fileNameDescription,
            String defaultFileName,
            boolean checkIfFileExists)
            throws FileNotFoundException {

        printer.printInfo("Hit ENTER for default: " + defaultFileName);
        printer.printWithUnderline("Enter " + fileNameDescription + " file name: ");

        String inputFileName = scanner.nextLine();

        if (inputFileName.isEmpty()) {
            inputFileName = defaultFileName;
        }

        File file = new File(inputFileName);

        if (checkIfFileExists && (!file.exists() || !file.isFile())) {
            throw new FileNotFoundException("Cannot find " + fileNameDescription + " file: " + inputFileName);
        } else {
            printer.printInfo(fileNameDescription + " file = " + file.getAbsolutePath());
            return inputFileName;
        }

    }

    /**
     * Prompts the user to enter an integer, with a default value to use if the user
     * presses ENTER without input.
     * 
     * @param intDescription a description of the integer to be entered, shown to
     *                       the user
     * @param defaultInt     the default integer to use if the user presses ENTER
     *                       without input
     * @return the integer entered by the user, or the default integer if input is
     *         empty
     * @throws NumberFormatException if the user enters an invalid integer
     */
    @Override
    public int getInt(
            String intDescription,
            int defaultInt)
            throws NumberFormatException {

        printer.printInfo("Hit ENTER for default: " + defaultInt);
        printer.printWithUnderline("Enter " + intDescription + " int: ");

        String input = scanner.nextLine();
        int n;

        if (input.isEmpty()) {
            n = defaultInt;
        } else {
            n = Integer.parseInt(input);
        }
        return n;
    }

    /**
     * Prompts the user to enter a string.
     * 
     * @return the string entered by the user
     */
    @Override
    public String getString() {
        return scanner.nextLine();
    }

    /**
     * Prompts the user to enter a string, with a default value to use if the user
     * presses ENTER without input.
     * 
     * @param stringDescription a description of the string to be entered, shown to
     *                          the user
     * @param defaultString     the default string to use if the user presses ENTER
     *                          without input
     * @return the string entered by the user, or the default string if input is
     *         empty
     */
    @Override
    public String getString(
            String stringDescription,
            String defaultString) {

        printer.printInfo("Hit ENTER for default: " + defaultString);
        printer.printWithUnderline("Enter " + stringDescription + ": ");

        String inputString = scanner.nextLine();

        if (inputString.isEmpty()) {
            inputString = defaultString;
        }

        return inputString;

    }

    /**
     * Closes the scanner to release resources.
     */
    @Override
    public void close() {
        scanner.close();
    }

}
