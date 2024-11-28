package ie.atu.sw.simplifiermenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.prefs.Preferences;

import ie.atu.sw.menu.MenuItem;
import ie.atu.sw.menu.MenuPrinter;
import ie.atu.sw.menu.SettingsMenu;
import ie.atu.sw.wordreplacer.ReplacementMethod;
import ie.atu.sw.wordreplacer.WordReplacer;

public class ReplacementMethodSettingsMenu extends SettingsMenu {

    private static final String REPLACEMENT_METHOD_KEY = "replacementMethodToUse";
    private static final String REPLACEMENT_METHOD_DEFAULT = ReplacementMethod.MOST_SIMILAR.name();

    private WordReplacer wordReplacer;

    public ReplacementMethodSettingsMenu(
            Scanner scanner,
            MenuPrinter menuPrinter,
            Preferences preferences,
            WordReplacer wordReplacer) {

        super("Replacement Method", scanner, menuPrinter, preferences);
        this.wordReplacer = wordReplacer;
        this.wordReplacer.setReplacementMethodToUse(getReplacementMethodToUse());

    }

    @Override
    protected void createMenuItems() {
        List<MenuItem> itemList = new ArrayList<MenuItem>();
        for (ReplacementMethod replacementMethod : ReplacementMethod.values()) {
            itemList.add(
                    new MenuItem(
                            String.valueOf(replacementMethod.ordinal() + 1),
                            replacementMethod.toString(),
                            () -> {
                                wordReplacer.setReplacementMethodToUse(replacementMethod);
                                setReplacementMethodToUse(replacementMethod);
                                getMenuPrinter()
                                        .printSuccess("Replacement Method set to: " + replacementMethod.toString());
                            }));
        }

        addMenuItemList(itemList);
    }

    @Override
    public void printPreferences() {
        getMenuPrinter().printInfo("Replacement Method: \t\t"
                + getReplacementMethodToUse().toString());
    }

    @Override
    public void resetPreferences() {
        wordReplacer.setReplacementMethodToUse(getReplacementMethodToUse());
    }

    public ReplacementMethod getReplacementMethodToUse() {
        String replacementMethodToUse = getPreferences().get(
                REPLACEMENT_METHOD_KEY,
                REPLACEMENT_METHOD_DEFAULT);

        return ReplacementMethod.valueOf(replacementMethodToUse);
    }

    public void setReplacementMethodToUse(ReplacementMethod replacementMethod) {
        getPreferences().put(REPLACEMENT_METHOD_KEY, replacementMethod.name());
    }
}
