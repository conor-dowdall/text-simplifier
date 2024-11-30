package ie.atu.sw.util;

public interface Printer {

    void printTitle(String title);

    void printChoice(String choice);

    void printHeading(String heading);

    void printWithUnderline(String text);

    void printInfo(String info);

    void printWarning(String warning);

    void printError(String error);

    void printSuccess(String success);

}
