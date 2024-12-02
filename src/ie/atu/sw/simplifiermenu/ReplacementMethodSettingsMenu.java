package ie.atu.sw.simplifiermenu;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import ie.atu.sw.menu.MenuItem;
import ie.atu.sw.util.InputReaderInterface;
import ie.atu.sw.util.MenuPrinterInterface;
import ie.atu.sw.wordreplacer.ReplacementMethod;
import ie.atu.sw.wordreplacer.WordReplacerAbstract;

/**
 * Menu for configuring the replacement method in the WordReplacer.
 */
public class ReplacementMethodSettingsMenu extends WordReplacerSettingsMenu {

    private static final String REPLACEMENT_METHOD_KEY = "replacementMethodToUse";
    private static final String REPLACEMENT_METHOD_DEFAULT = ReplacementMethod.MOST_SIMILAR.name();

    /**
     * Constructor to initialize the ReplacementMethodSettingsMenu.
     *
     * @param inputReader  the input reader interface for user input.
     * @param menuPrinter  the menu printer interface for output.
     * @param preferences  the preferences object for storing settings.
     * @param wordReplacer the WordReplacerAbstract instance to configure.
     */
    public ReplacementMethodSettingsMenu(
            InputReaderInterface inputReader,
            MenuPrinterInterface menuPrinter,
            Preferences preferences,
            WordReplacerAbstract wordReplacer) {

        super(inputReader, menuPrinter, preferences, wordReplacer);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initWordReplacer() {
        getWordReplacer().setReplacementMethod(getReplacementMethodToUse());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createMenuItems() {
        List<MenuItem> itemList = new ArrayList<MenuItem>();
        for (ReplacementMethod replacementMethod : ReplacementMethod.values()) {
            itemList.add(
                    new MenuItem(
                            String.valueOf(replacementMethod.ordinal() + 1),
                            replacementMethod.toString(),
                            () -> setReplacementMethodToUse(replacementMethod)));
        }

        addMenuItemList(itemList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printPreferences() {
        getMenuPrinter().printInfo("Replacement Method: \t\t"
                + getReplacementMethodToUse().toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetPreferences() {
        getWordReplacer().setReplacementMethod(getReplacementMethodToUse());
    }

    /**
     * Retrieves the replacement method to use from preferences.
     *
     * @return the ReplacementMethod to use.
     */
    public ReplacementMethod getReplacementMethodToUse() {
        String replacementMethodToUse = getPreferences().get(
                REPLACEMENT_METHOD_KEY,
                REPLACEMENT_METHOD_DEFAULT);

        return ReplacementMethod.valueOf(replacementMethodToUse);
    }

    /**
     * Sets the replacement method to use and updates preferences.
     *
     * @param replacementMethod the ReplacementMethod to set.
     */
    public void setReplacementMethodToUse(ReplacementMethod replacementMethod) {
        getWordReplacer().setReplacementMethod(replacementMethod);
        getPreferences().put(REPLACEMENT_METHOD_KEY, replacementMethod.name());
        getMenuPrinter().printSuccess("Replacement Method set to: " + replacementMethod.toString());
    }

}
