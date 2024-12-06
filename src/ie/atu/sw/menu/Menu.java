package ie.atu.sw.menu;

import java.util.LinkedHashMap;
import java.util.List;

import ie.atu.sw.util.InputReaderInterface;
import ie.atu.sw.util.MenuPrinterInterface;

/**
 * Abstract class that represents a menu in an application.
 * This class is responsible for displaying the menu options, accepting user
 * input, and executing the associated actions for selected options.
 * It supports a shortcut-based system for selecting menu items.
 * <p>
 * A {@link LinkedHashMap} stores the shortcuts, which is similar to HashMap but
 * also maintains the order of insertion. The put() operation generally runs in
 * O(1) time on average. In the worst case it can take O(n) time.
 * </p>
 */
public abstract class Menu {

    private final String title;
    private final InputReaderInterface inputReader;
    private final MenuPrinterInterface menuPrinter;
    private final LinkedHashMap<String, MenuItem> shortcutMap = new LinkedHashMap<>();

    /**
     * Constructs a new Menu with the given title, input reader, and menu printer.
     * Initializes the menu items by calling {@link #createMenuItems()}.
     * 
     * @param title       the title of the menu to be displayed
     * @param inputReader an {@link InputReaderInterface} used to read user input
     * @param menuPrinter an {@link MenuPrinterInterface} used to print the menu and
     *                    options
     */
    public Menu(String title, InputReaderInterface inputReader, MenuPrinterInterface menuPrinter) {
        this.title = title;
        this.inputReader = inputReader;
        this.menuPrinter = menuPrinter;

        createMenuItems();
    }

    /**
     * Returns the title of the menu.
     * 
     * @return the title of the menu
     */
    protected String getTitle() {
        return title;
    }

    /**
     * Returns the {@link MenuPrinterInterface} used to print the menu and options.
     * 
     * @return the menu printer instance
     */
    protected MenuPrinterInterface getMenuPrinter() {
        return menuPrinter;
    }

    /**
     * Returns the {@link InputReaderInterface} used to read user input.
     * 
     * @return the input reader instance
     */
    protected InputReaderInterface getInputReader() {
        return inputReader;
    }

    /**
     * Adds a menu item to the menu using a shortcut key, label, and an associated
     * action (runnable).
     * 
     * @param shortcut the shortcut key for the menu item
     * @param label    the label to display for the menu item
     * @param runnable the action to execute when the menu item is selected
     */
    protected void addMenuItem(String shortcut, String label, Runnable runnable) {
        shortcutMap.put(shortcut, new MenuItem(shortcut, label, runnable));
    }

    /**
     * Adds a menu item to the menu.
     * 
     * @param item the {@link MenuItem} to be added
     */
    protected void addMenuItem(MenuItem item) {
        shortcutMap.put(item.shortcut(), item);
    }

    /**
     * Adds a list of menu items to the menu.
     * 
     * @param itemList the list of {@link MenuItem}s to be added
     */
    protected void addMenuItemList(List<MenuItem> itemList) {
        for (MenuItem item : itemList)
            shortcutMap.put(item.shortcut(), item);
    }

    /**
     * Reads the user's input for a menu option and executes the corresponding menu
     * item's action (if available).
     * If the input is invalid or an error occurs during execution, an error message
     * is printed.
     */
    protected void scanInputOptionAndRunItemRunnable() {
        try {
            // Get the input option from the user
            String inputOption = inputReader.getString();
            MenuItem item = shortcutMap.get(inputOption);

            if (item != null) {
                // Print the selected choice and run its action
                menuPrinter.printChoice(item.toString());
                if (item.runnable() != null) {
                    item.runnable().run();
                } else {
                    // Handle case where the menu item has no functionality
                    menuPrinter.printWarning("'" + item + "' has null functionality");
                }
            } else {
                throw new IllegalStateException("Invalid Option: " + inputOption);
            }
        } catch (Exception e) {
            // Print the error and prompt for a new choice
            menuPrinter.printError(e.getMessage());
            printMenuAndAcceptChoice();
        }
    }

    /**
     * Displays the menu title, menu items, and prompts the user to make a
     * selection.
     * After a selection is made, the corresponding action is executed.
     */
    public void printMenuAndAcceptChoice() {
        menuPrinter.printTitle(title);
        menuPrinter.printItems(shortcutMap.values());
        scanInputOptionAndRunItemRunnable();
    }

    /**
     * Abstract method to be implemented by subclasses to define the menu items.
     * This method is called during the construction of the menu to populate it with
     * items.
     */
    protected abstract void createMenuItems();

}
