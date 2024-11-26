package ie.atu.sw.simplifiermenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.prefs.Preferences;

import ie.atu.sw.menu.Menu;
import ie.atu.sw.menu.MenuItem;
import ie.atu.sw.wordreplacer.ReplacementMethod;
import ie.atu.sw.wordreplacer.WordReplacer;

public class ReplacementMethodMenu extends Menu {
    private static final String REPLACEMENT_METHOD_KEY = "replacementMethodToUse";
    private static final String REPLACEMENT_METHOD_DEFAULT = ReplacementMethod.MOST_SIMILAR.name();

    private WordReplacer wordReplacer;
    private Preferences preferences;

    public ReplacementMethodMenu(Scanner scanner, WordReplacer wordReplacer, Preferences preferences) {
        super("Replacement Method", scanner);
        this.preferences = preferences;
        this.wordReplacer = wordReplacer;
        this.wordReplacer.setReplacementMethodToUse(this.getReplacementMethodToUse());

        List<MenuItem> itemList = new ArrayList<MenuItem>();
        for (ReplacementMethod replacementMethod : ReplacementMethod.values()) {
            itemList.add(
                    new MenuItem(
                            String.valueOf(replacementMethod.ordinal() + 1),
                            replacementMethod.toString(),
                            () -> {
                                this.wordReplacer.setReplacementMethodToUse(replacementMethod);
                                this.setReplacementMethodToUse(replacementMethod);
                                this.printSuccess("Replacement Method set to: " + replacementMethod.toString());
                            }));
        }
        this.addMenuItemList(itemList);
    }

    public ReplacementMethod getReplacementMethodToUse() {
        String replacementMethodToUse = this.preferences.get(
                REPLACEMENT_METHOD_KEY,
                REPLACEMENT_METHOD_DEFAULT);

        return ReplacementMethod.valueOf(replacementMethodToUse);
    }

    public void setReplacementMethodToUse(ReplacementMethod replacementMethod) {
        this.preferences.put(REPLACEMENT_METHOD_KEY, replacementMethod.name());
    }
}
