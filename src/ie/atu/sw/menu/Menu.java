package ie.atu.sw.menu;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ie.atu.sw.console.ConsoleColor;

public class Menu {
    private String title;
    protected Scanner scanner;
    private final LinkedHashMap<String, MenuItem> shortcutMap = new LinkedHashMap<>();

    public Menu(String title, Scanner scanner) {
        this.title = title;
        this.scanner = scanner;
    }

    public void printMenuAndAcceptChoice() {
        this.printTitle(this.title);
        this.printItems();
        this.scanInputOptionAndRunItemRunnable();
    }

    public void addMenuItem(String shortcut, String label, Runnable runnable) {
        this.shortcutMap.put(shortcut, new MenuItem(shortcut, label, runnable));
    }

    public void addMenuItem(MenuItem item) {
        this.shortcutMap.put(item.shortcut(), item);
    }

    public void addMenuItemList(List<MenuItem> itemList) {
        for (MenuItem item : itemList)
            this.shortcutMap.put(item.shortcut(), item);
    }

    public void scanInputOptionAndRunItemRunnable() {
        try {
            String inputOption = this.scanner.nextLine();
            MenuItem item = shortcutMap.get(inputOption);

            if (item != null) {
                this.printChoice(item.toString());
                if (item.runnable() != null) {
                    item.runnable().run();
                } else {
                    this.printWarning("'" + item + "' has null functionality");
                }
            } else {
                throw new IllegalStateException("Invalid Option: " + inputOption);
            }
        } catch (Exception e) {
            this.printError(e.getMessage());
            this.printMenuAndAcceptChoice();
        }
    }

    /**
     * print a stylized title to the terminal
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
     * print a stylized item list to the terminal
     */
    public void printItems() {
        System.out.print(ConsoleColor.BLACK_BACKGROUND);
        System.out.print(ConsoleColor.GREEN_BOLD_BRIGHT);
        System.out.print("[OPTIONS] Select from the following:");
        System.out.println();
        System.out.println();

        if (this.shortcutMap.isEmpty())
            throw new IllegalStateException("No Items found in Menu");
        else
            for (Map.Entry<String, MenuItem> item : this.shortcutMap.entrySet())
                System.out.println(item.getValue());

        System.out.println();
        System.out.print("Option: ");
        System.out.print(ConsoleColor.RESET);
    }

    /**
     * print a stylized heading to the terminal
     * 
     * @param heading - a string to be stylized as a heading
     */
    public void printHeading(String heading) {
        System.out.println();
        printWithUnderline(heading);
        System.out.println();
    }

    /**
     * print an underlined string to the terminal
     * 
     * @param text - a string to be underlined
     */
    public void printWithUnderline(String text) {
        System.out.print(ConsoleColor.BLACK_BACKGROUND);
        System.out.print(ConsoleColor.WHITE_UNDERLINED);
        System.out.print(text);
        System.out.print(ConsoleColor.RESET);
    }

    /**
     * print stylized information-text to the terminal
     * 
     * @param info - a string to be stylized as information-text
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
     * print stylized choice-information-text to the terminal
     * 
     * @param choice - a string to be stylized as information-text
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
     * print stylized warning-text to the terminal
     * 
     * @param warning - a string to be stylized as warning-text
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
     * print stylized error-text to the terminal
     * 
     * @param error - a string to be stylized as error-text
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
     * print stylized success-text to the terminal
     * 
     * @param success - a string to be stylized as success-text
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
