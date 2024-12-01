package ie.atu.sw.simplifiermenu;

import java.util.prefs.Preferences;

import ie.atu.sw.menu.SettingsMenu;
import ie.atu.sw.util.InputReaderInterface;
import ie.atu.sw.util.MenuPrinterInterface;
import ie.atu.sw.wordreplacer.WordReplacerAbstract;

/**
 * Abstract class representing a settings menu for configuring WordReplacer
 * settings.
 */
public abstract class WordReplacerSettingsMenu extends SettingsMenu {

    private final WordReplacerAbstract wordReplacer;

    /**
     * Constructor to initialize the WordReplacerSettingsMenu with required
     * parameters.
     *
     * @param inputReader  the input reader interface for user input.
     * @param menuPrinter  the menu printer interface for output.
     * @param preferences  the preferences object for storing settings.
     * @param wordReplacer the WordReplacerAbstract instance to configure.
     */
    public WordReplacerSettingsMenu(
            InputReaderInterface inputReader,
            MenuPrinterInterface menuPrinter,
            Preferences preferences,
            WordReplacerAbstract wordReplacer) {

        super("Word Replacer Settings", inputReader, menuPrinter, preferences);

        this.wordReplacer = wordReplacer;

        initWordReplacer();

    }

    /**
     * Retrieves the associated WordReplacer instance.
     *
     * @return the WordReplacerAbstract instance.
     */
    protected WordReplacerAbstract getWordReplacer() {
        return wordReplacer;
    }

    /**
     * Abstract method to initialize the WordReplacer settings. Implementations
     * should configure specific settings for the WordReplacer.
     */
    protected abstract void initWordReplacer();
}
