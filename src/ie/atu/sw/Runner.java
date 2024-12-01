package ie.atu.sw;

import java.util.Scanner;

import ie.atu.sw.console.ConsoleInputReader;
import ie.atu.sw.menu.ConsoleMenuPrinter;
import ie.atu.sw.simplifiermenu.SimplifierMainMenu;
import ie.atu.sw.util.InputReaderInterface;
import ie.atu.sw.util.MenuPrinterInterface;
import ie.atu.sw.wordreplacer.WordReplacer;
import ie.atu.sw.wordreplacer.WordReplacerAbstract;

/**
 * The Runner class serves as the entry point for the application.
 * It initializes necessary components and invokes the main menu to interact
 * with the user.
 */
public class Runner {

    /**
     * The main method that initiates the application.
     * It sets up the necessary components for reading input, printing menus, and
     * processing word replacements.
     * 
     * @param args the command-line arguments (not used in this implementation)
     */
    public static void main(String[] args) {

        // Create a scanner to read input from the user
        Scanner scanner = new Scanner(System.in);

        // Create an instance of MenuPrinterInterface to display menus in the console
        MenuPrinterInterface menuPrinter = new ConsoleMenuPrinter();

        // Create an instance of InputReaderInterface to handle user input
        InputReaderInterface inputReader = new ConsoleInputReader(scanner, menuPrinter);

        // Create an instance of WordReplacerAbstract to perform word replacement
        // operations
        WordReplacerAbstract wordReplacer = new WordReplacer();

        // Create and invoke the main menu to interact with the user and process their
        // choices
        new SimplifierMainMenu(inputReader, menuPrinter, wordReplacer)
                .printMenuAndAcceptChoice();

    }

}
