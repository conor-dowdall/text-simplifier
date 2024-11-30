package ie.atu.sw.simplifiermenu;

import java.util.prefs.Preferences;

import ie.atu.sw.menu.SettingsMenu;
import ie.atu.sw.util.InputReaderInterface;
import ie.atu.sw.util.MenuPrinterInterface;
import ie.atu.sw.wordreplacer.WordReplacer;

public abstract class WordReplacerSettingsMenu extends SettingsMenu {

    private final WordReplacer wordReplacer;

    public WordReplacerSettingsMenu(
            InputReaderInterface inputReader,
            MenuPrinterInterface menuPrinter,
            Preferences preferences,
            WordReplacer wordReplacer) {

        super("Word Replacer Settings", inputReader, menuPrinter, preferences);

        this.wordReplacer = wordReplacer;

        initWordReplacer();

    }

    protected WordReplacer getWordReplacer() {
        return wordReplacer;
    }

    protected abstract void initWordReplacer();
}
