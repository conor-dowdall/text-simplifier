package ie.atu.sw.menu;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public abstract class Menu {

    private final String title;
    private final Scanner scanner;
    private final MenuPrinter menuPrinter;
    private final LinkedHashMap<String, MenuItem> shortcutMap = new LinkedHashMap<>();

    public Menu(String title, Scanner scanner, MenuPrinter menuPrinter) {
        this.title = title;
        this.scanner = scanner;
        this.menuPrinter = menuPrinter;

        createMenuItems();
    }

    protected String getTitle() {
        return title;
    }

    protected MenuPrinter getMenuPrinter() {
        return menuPrinter;
    }

    protected Scanner getScanner() {
        return scanner;
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
            String inputOption = scanner.nextLine();
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
