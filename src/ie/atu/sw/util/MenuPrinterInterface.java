package ie.atu.sw.util;

import java.util.Collection;

import ie.atu.sw.menu.MenuItem;

public interface MenuPrinterInterface extends PrinterInterface {

    void printItems(Collection<MenuItem> items);

}
