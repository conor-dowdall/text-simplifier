package ie.atu.sw.menu;

import java.util.LinkedHashMap;
import java.util.List;

import ie.atu.sw.util.InputReaderInterface;
import ie.atu.sw.util.MenuPrinterInterface;

public abstract class Menu {

    private final String title;
    private final InputReaderInterface inputReader;
    private final MenuPrinterInterface menuPrinter;
    private final LinkedHashMap<String, MenuItem> shortcutMap = new LinkedHashMap<>();

    public Menu(String title, InputReaderInterface inputReader, MenuPrinterInterface menuPrinter) {
        this.title = title;
        this.inputReader = inputReader;
        this.menuPrinter = menuPrinter;

        createMenuItems();
    }

    protected String getTitle() {
        return title;
    }

    protected MenuPrinterInterface getMenuPrinter() {
        return menuPrinter;
    }

    protected InputReaderInterface getInputReader() {
        return inputReader;
    }

    protected void addMenuItem(String shortcut, String label, Runnable runnable) {
        shortcutMap.put(shortcut, new MenuItem(shortcut, label, runnable));
    }

    protected void addMenuItem(MenuItem item) {
        shortcutMap.put(item.shortcut(), item);
    }

    protected void addMenuItemList(List<MenuItem> itemList) {
        for (MenuItem item : itemList)
            shortcutMap.put(item.shortcut(), item);
    }

    protected void scanInputOptionAndRunItemRunnable() {
        try {
            String inputOption = inputReader.getString();
            MenuItem item = shortcutMap.get(inputOption);

            if (item != null) {
                menuPrinter.printChoice(item.toString());
                if (item.runnable() != null) {
                    item.runnable().run();
                } else {
                    menuPrinter.printWarning("'" + item + "' has null functionality");
                }
            } else {
                throw new IllegalStateException("Invalid Option: " + inputOption);
            }
        } catch (Exception e) {
            menuPrinter.printError(e.getMessage());
            printMenuAndAcceptChoice();
        }
    }

    public void printMenuAndAcceptChoice() {
        menuPrinter.printTitle(title);
        menuPrinter.printItems(shortcutMap.values());
        scanInputOptionAndRunItemRunnable();
    }

    protected abstract void createMenuItems();

}
