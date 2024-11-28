package ie.atu.sw.simplifiermenu;

import java.util.Scanner;
import java.util.prefs.Preferences;

import ie.atu.sw.menu.MenuPrinter;
import ie.atu.sw.menu.SettingsMenu;
import ie.atu.sw.wordreplacer.WordReplacer;

public abstract class WordReplacerSettingsMenu extends SettingsMenu {

    private final WordReplacer wordReplacer;

    public WordReplacerSettingsMenu(
            Scanner scanner,
            MenuPrinter menuPrinter,
            Preferences preferences,
            WordReplacer wordReplacer) {

        super("Word Replacer Settings", scanner, menuPrinter, preferences);

        this.wordReplacer = wordReplacer;

        initWordReplacer();

    }

    protected WordReplacer getWordReplacer() {
        return wordReplacer;
    }

    protected abstract void initWordReplacer();
}
