package ie.atu.sw.menu;

import java.util.prefs.Preferences;

import ie.atu.sw.util.InputReaderInterface;
import ie.atu.sw.util.MenuPrinterInterface;

public abstract class SettingsMenu extends Menu {

    private final Preferences preferences;

    public SettingsMenu(
            String title,
            InputReaderInterface inputReader,
            MenuPrinterInterface menuPrinter,
            Preferences preferences) {

        super(title, inputReader, menuPrinter);

        this.preferences = preferences;

    }

    protected Preferences getPreferences() {
        return preferences;
    }

    public abstract void printPreferences();

    public abstract void resetPreferences();

}
