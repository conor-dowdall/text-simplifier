package ie.atu.sw.simplifiermenu;

import java.util.prefs.Preferences;

import ie.atu.sw.menu.SettingsMenu;
import ie.atu.sw.util.InputReaderInterface;
import ie.atu.sw.util.MenuPrinterInterface;
import ie.atu.sw.wordreplacer.WordReplacerAbstract;

public abstract class WordReplacerSettingsMenu extends SettingsMenu {

    private final WordReplacerAbstract wordReplacer;

    public WordReplacerSettingsMenu(
            InputReaderInterface inputReader,
            MenuPrinterInterface menuPrinter,
            Preferences preferences,
            WordReplacerAbstract wordReplacer) {

        super("Word Replacer Settings", inputReader, menuPrinter, preferences);

        this.wordReplacer = wordReplacer;

        initWordReplacer();

    }

    protected WordReplacerAbstract getWordReplacer() {
        return wordReplacer;
    }

    protected abstract void initWordReplacer();
}
