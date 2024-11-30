package ie.atu.sw.menu;

import java.util.Collection;

import ie.atu.sw.util.Printer;

public interface MenuPrinter extends Printer {

    void printItems(Collection<MenuItem> items);

}
