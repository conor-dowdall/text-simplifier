package ie.atu.sw.menu;

import java.util.Collection;

import ie.atu.sw.console.ConsoleColor;
import ie.atu.sw.util.MenuPrinterInterface;

/**
 * Implementation of MenuPrinterInterface for printing menu-related content to
 * the console.
 * Provides methods to print titles, choices, headings, and other messages with
 * various stylings (e.g., bold, underline).
 */
public class ConsoleMenuPrinter implements MenuPrinterInterface {

    /**
     * Constructs a new {@link ConsoleMenuPrinter}.
     * This constructor is used to initialize the printer object.
     */
    public ConsoleMenuPrinter() {
        // Default constructor
    }

    /**
     * Prints a stylized title to the terminal.
     * 
     * @param title the title to be displayed
     */
    public void printTitle(String title) {
        System.out.print(ConsoleColor.BLACK_BACKGROUND);
        System.out.print(ConsoleColor.CYAN_BOLD);
        System.out.println();
        System.out.println();
        System.out.print("TITLE: " + title);
        System.out.println();
        System.out.print(ConsoleColor.RESET);
        System.out.println();
    }

    /**
     * Prints a stylized list of menu items to the terminal.
     * 
     * @param items the collection of menu items to be displayed
     */
    public void printItems(Collection<MenuItem> items) {
        System.out.print(ConsoleColor.BLACK_BACKGROUND);
        System.out.print(ConsoleColor.GREEN_BOLD_BRIGHT);
        System.out.print("[OPTIONS] Select from the following:");
        System.out.println();
        System.out.println();

        for (MenuItem item : items)
            System.out.println(item.toString());

        System.out.println();
        System.out.print("Option: ");
        System.out.print(ConsoleColor.RESET);
    }

    /**
     * Prints a stylized choice message to the terminal.
     * 
     * @param choice the choice information to be displayed
     */
    public void printChoice(String choice) {
        System.out.println();
        System.out.print(ConsoleColor.BLACK_BACKGROUND);
        System.out.print(ConsoleColor.WHITE_BOLD);
        System.out.print("CHOICE: " + choice);
        System.out.print(ConsoleColor.RESET);
        System.out.println();
    }

    /**
     * Prints a stylized heading to the terminal.
     * 
     * @param heading the heading to be displayed
     */
    public void printHeading(String heading) {
        System.out.println();
        printWithUnderline(heading);
        System.out.println();
    }

    /**
     * Prints a string with underlined style to the terminal.
     * 
     * @param text the text to be underlined
     */
    public void printWithUnderline(String text) {
        System.out.print(ConsoleColor.BLACK_BACKGROUND);
        System.out.print(ConsoleColor.WHITE_UNDERLINED);
        System.out.print(text);
        System.out.print(ConsoleColor.RESET);
    }

    /**
     * Prints a stylized information message to the terminal.
     * 
     * @param info the information text to be displayed
     */
    public void printInfo(String info) {
        System.out.println();
        System.out.print(ConsoleColor.BLACK_BACKGROUND);
        System.out.print(ConsoleColor.PURPLE_BOLD_BRIGHT);
        System.out.print("INFO: " + info);
        System.out.print(ConsoleColor.RESET);
        System.out.println();
    }

    /**
     * Prints a stylized warning message to the terminal.
     * 
     * @param warning the warning text to be displayed
     */
    public void printWarning(String warning) {
        System.out.println();
        System.out.print(ConsoleColor.BLACK_BACKGROUND);
        System.out.print(ConsoleColor.YELLOW_BOLD_BRIGHT);
        System.out.print("WARNING: " + warning);
        System.out.print(ConsoleColor.RESET);
        System.out.println();
    }

    /**
     * Prints a stylized error message to the terminal.
     * 
     * @param error the error text to be displayed
     */
    public void printError(String error) {
        System.err.println();
        System.err.print(ConsoleColor.BLACK_BACKGROUND);
        System.err.print(ConsoleColor.RED_BOLD_BRIGHT);
        System.err.print("ERROR: " + error);
        System.err.print(ConsoleColor.RESET);
        System.err.println();
    }

    /**
     * Prints a stylized success message to the terminal.
     * 
     * @param success the success text to be displayed
     */
    public void printSuccess(String success) {
        System.err.println();
        System.err.print(ConsoleColor.BLACK_BACKGROUND);
        System.err.print(ConsoleColor.GREEN_BRIGHT);
        System.err.print("SUCCESS: " + success);
        System.err.print(ConsoleColor.RESET);
        System.err.println();
    }

}
