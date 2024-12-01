package ie.atu.sw.util;

import java.util.Collection;

import ie.atu.sw.menu.MenuItem;

/**
 * Interface that extends PrinterInterface to include a method for printing menu
 * items.
 * Used for printing menu-related content such as options for the user to
 * select.
 */
public interface MenuPrinterInterface extends PrinterInterface {
    /**
     * Prints a collection of menu items.
     * 
     * @param items the collection of menu items to be displayed
     */
    void printItems(Collection<MenuItem> items);

}
