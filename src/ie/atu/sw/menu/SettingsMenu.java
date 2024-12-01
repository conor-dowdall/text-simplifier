package ie.atu.sw.menu;

import java.util.prefs.Preferences;

import ie.atu.sw.util.InputReaderInterface;
import ie.atu.sw.util.MenuPrinterInterface;

/**
 * Abstract class representing a settings menu in an application.
 * This class extends {@link Menu} and is designed for managing user preferences
 * and settings.
 * It provides methods for displaying and resetting preferences.
 */
public abstract class SettingsMenu extends Menu {

    private final Preferences preferences;

    /**
     * Constructs a new SettingsMenu with the given title, input reader, menu
     * printer, and preferences.
     * 
     * @param title       the title of the settings menu
     * @param inputReader an {@link InputReaderInterface} used to read user input
     * @param menuPrinter an {@link MenuPrinterInterface} used to print the menu and
     *                    options
     * @param preferences the {@link Preferences} object that stores the user
     *                    preferences
     */
    public SettingsMenu(
            String title,
            InputReaderInterface inputReader,
            MenuPrinterInterface menuPrinter,
            Preferences preferences) {

        super(title, inputReader, menuPrinter);

        this.preferences = preferences;

    }

    /**
     * Returns the {@link Preferences} object associated with this settings menu.
     * 
     * @return the {@link Preferences} instance that holds the user preferences
     */
    protected Preferences getPreferences() {
        return preferences;
    }

    /**
     * Prints the current user preferences to the console.
     * This method should be implemented by subclasses to display the specific
     * preferences.
     */
    public abstract void printPreferences();

    /**
     * Resets the user preferences to their default values.
     * This method should be implemented by subclasses to define how preferences are
     * reset.
     */
    public abstract void resetPreferences();

}
