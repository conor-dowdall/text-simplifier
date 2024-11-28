package ie.atu.sw.menu;

import java.util.Scanner;
import java.util.prefs.Preferences;

public abstract class SettingsMenu extends Menu {

    private final Preferences preferences;

    public SettingsMenu(
            String title,
            Scanner scanner,
            MenuPrinter menuPrinter,
            Preferences preferences) {

        super(title, scanner, menuPrinter);

        this.preferences = preferences;

    }

    protected Preferences getPreferences() {
        return preferences;
    }

    public abstract void printPreferences();

    public abstract void resetPreferences();

}
