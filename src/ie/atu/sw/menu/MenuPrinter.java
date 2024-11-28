package ie.atu.sw.menu;

import java.util.Collection;

public interface MenuPrinter {

    void printTitle(String title);

    void printItems(Collection<MenuItem> items);

    void printChoice(String choice);

    void printHeading(String heading);

    void printWithUnderline(String text);

    void printInfo(String info);

    void printWarning(String warning);

    void printError(String error);

    void printSuccess(String success);

}
