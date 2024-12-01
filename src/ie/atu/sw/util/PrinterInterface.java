package ie.atu.sw.util;

/**
 * Interface defining methods for printing various types of messages.
 * The messages can be styled with different formatting, such as title, heading,
 * or specific message types like error or warning.
 */
public interface PrinterInterface {
    /**
     * Prints a stylized title to the console.
     * 
     * @param title the title to be displayed
     */
    void printTitle(String title);

    /**
     * Prints a stylized choice message to the console.
     * 
     * @param choice the choice information to be displayed
     */
    void printChoice(String choice);

    /**
     * Prints a stylized heading to the console.
     * 
     * @param heading the heading to be displayed
     */
    void printHeading(String heading);

    /**
     * Prints a stylized string with an underline to the console.
     * 
     * @param text the text to be underlined
     */
    void printWithUnderline(String text);

    /**
     * Prints stylized information text to the console.
     * 
     * @param info the information to be displayed
     */
    void printInfo(String info);

    /**
     * Prints a stylized warning message to the console.
     * 
     * @param warning the warning text to be displayed
     */
    void printWarning(String warning);

    /**
     * Prints a stylized error message to the console.
     * 
     * @param error the error text to be displayed
     */
    void printError(String error);

    /**
     * Prints a stylized success message to the console.
     * 
     * @param success the success text to be displayed
     */
    void printSuccess(String success);

}
