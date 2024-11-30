package ie.atu.sw.console;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import ie.atu.sw.util.InputReader;
import ie.atu.sw.util.Printer;

public class ConsoleInputReader implements InputReader {

    Scanner scanner;
    Printer printer;

    public ConsoleInputReader(Scanner scanner, Printer printer) {
        this.scanner = scanner;
        this.printer = printer;
    }

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

    @Override
    public String getString() {
        return scanner.nextLine();
    }

    @Override
    public void close() {
        scanner.close();
    }

}
