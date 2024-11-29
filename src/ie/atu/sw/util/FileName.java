package ie.atu.sw.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import ie.atu.sw.menu.MenuPrinter;

public class FileName {

    public static String scanFileName(
            Scanner scanner,
            MenuPrinter menuPrinter,
            String fileNameDescription,
            String defaultFileName)
            throws FileNotFoundException {

        menuPrinter.printInfo("Hit ENTER for default: " + defaultFileName);
        menuPrinter.printWithUnderline("Enter " + fileNameDescription + " file name: ");

        String inputFileName = scanner.nextLine();

        if (inputFileName.isEmpty()) {
            inputFileName = defaultFileName;
        }

        File file = new File(inputFileName);

        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("Cannot find " + fileNameDescription + " file: " + inputFileName);
        } else {
            menuPrinter.printSuccess(fileNameDescription + " file = " + file.getAbsolutePath());
            return inputFileName;
        }

    }

}
