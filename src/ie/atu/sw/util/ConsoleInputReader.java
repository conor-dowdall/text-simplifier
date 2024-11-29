package ie.atu.sw.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import ie.atu.sw.menu.MenuPrinter;

public class ConsoleInputReader {

    public static String scanFileName(
            Scanner scanner,
            MenuPrinter menuPrinter,
            String fileNameDescription,
            String defaultFileName,
            boolean checkIfFileExists)
            throws FileNotFoundException {

        menuPrinter.printInfo("Hit ENTER for default: " + defaultFileName);
        menuPrinter.printWithUnderline("Enter " + fileNameDescription + " file name: ");

        String inputFileName = scanner.nextLine();

        if (inputFileName.isEmpty()) {
            inputFileName = defaultFileName;
        }

        File file = new File(inputFileName);

        if (checkIfFileExists && (!file.exists() || !file.isFile())) {
            throw new FileNotFoundException("Cannot find " + fileNameDescription + " file: " + inputFileName);
        } else {
            menuPrinter.printInfo(fileNameDescription + " file = " + file.getAbsolutePath());
            return inputFileName;
        }

    }

    public static int scanInt(
            Scanner scanner,
            MenuPrinter menuPrinter,
            String intDescription,
            int defaultInt)
            throws NumberFormatException {

        menuPrinter.printInfo("Hit ENTER for default: " + defaultInt);
        menuPrinter.printWithUnderline("Enter " + intDescription + " int: ");

        String input = scanner.nextLine();
        int n;

        if (input.isEmpty()) {
            n = defaultInt;
        } else {
            n = Integer.parseInt(input);
        }
        return n;
    }

}
