package ie.atu.sw.simplifiermenu;

import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

import ie.atu.sw.menu.Menu;
import ie.atu.sw.menu.MenuItem;
import ie.atu.sw.menu.MenuPrinter;
import ie.atu.sw.util.InputReader;
import ie.atu.sw.wordreplacer.WordReplacer;

public class SimplifierMainMenu extends Menu {

    private final WordReplacer wordReplacer;
    private final SimplifierSettingsMenu settingsMenu;
    private final Preferences preferences = Preferences.userNodeForPackage(SimplifierMainMenu.class);

    public SimplifierMainMenu(
            InputReader inputReader,
            MenuPrinter menuPrinter,
            WordReplacer wordReplacer) {

        super("Simplifier", inputReader, menuPrinter);

        this.wordReplacer = wordReplacer;

        settingsMenu = new SimplifierSettingsMenu(inputReader, menuPrinter, preferences, wordReplacer);

    }

    @Override
    protected void createMenuItems() {

        List<MenuItem> itemList = List.of(

                new MenuItem(
                        "1",
                        "Launch Simplify File",
                        () -> {
                            settingsMenu.printPreferences();
                            launchSimplifyTextFile();
                            printMenuAndAcceptChoice();
                        }),

                new MenuItem(
                        "2",
                        "Launch Simplify Text",
                        () -> {
                            settingsMenu.printPreferences();
                            launchSimplifyText();
                            printMenuAndAcceptChoice();
                        }),

                new MenuItem(
                        "3",
                        "Load Word-Embeddings File",
                        () -> {
                            try {
                                settingsMenu.loadWordEmbeddingsFile();
                            } catch (IOException e) {
                                getMenuPrinter().printError(e.getMessage());
                            } finally {
                                printMenuAndAcceptChoice();
                            }
                        }),

                new MenuItem(
                        "4",
                        "Load Replacement-Words File",
                        () -> {
                            try {
                                settingsMenu.loadReplacementWordsFile();
                            } catch (IOException e) {
                                getMenuPrinter().printError(e.getMessage());
                            } finally {
                                printMenuAndAcceptChoice();
                            }
                        }),

                new MenuItem(
                        "s",
                        "Settings",
                        () -> {
                            launchSettingsMenu();
                            printMenuAndAcceptChoice();
                        }),

                new MenuItem(
                        "q",
                        "Quit",
                        this::quitProgram));

        addMenuItemList(itemList);

    }

    private void launchSimplifyTextFile() {

        try {

            getMenuPrinter().printTitle("Simplify File");

            if (wordReplacer.isWordEmbeddingMapNull()) {
                settingsMenu.loadWordEmbeddingsFile();
            }

            if (wordReplacer.isReplacementWordSetNull()) {
                settingsMenu.loadReplacementWordsFile();
            }

            String inputTextFileName = getInputReader().getFileName(
                    "INPUT FILE",
                    settingsMenu.getInputTextFileName(),
                    true);

            settingsMenu.setInputTextFileName(inputTextFileName);

            String outputTextFileName = getInputReader().getFileName(
                    "OUTPUT FILE",
                    settingsMenu.getOutputTextFileName(),
                    false);

            settingsMenu.setOutputTextFileName(outputTextFileName);

            wordReplacer
                    .writeReplacedFile(
                            inputTextFileName,
                            outputTextFileName);

            getMenuPrinter().printSuccess("OUTPUT TEXT file written (" + outputTextFileName + ").");

        } catch (Exception e) {
            getMenuPrinter().printError(e.getMessage());
        }
    }

    private void launchSimplifyText() {

        try {

            getMenuPrinter().printTitle("Simplify Text");

            if (wordReplacer.isWordEmbeddingMapNull()) {
                settingsMenu.loadWordEmbeddingsFile();
            }

            if (wordReplacer.isReplacementWordSetNull()) {
                settingsMenu.loadReplacementWordsFile();
            }

            System.out.println();
            getMenuPrinter().printWithUnderline("Enter some text to simplify:");
            System.out.println();

            String text = getInputReader().getString();
            String simplified = wordReplacer.replaceString(text);

            System.out.println();
            getMenuPrinter().printWithUnderline("Simplified Text:");
            System.out.println();
            System.out.println(simplified);

        } catch (Exception e) {
            getMenuPrinter().printError(e.getMessage());
        }
    }

    private void launchSettingsMenu() {
        settingsMenu.printMenuAndAcceptChoice();
    }

    private void quitProgram() {
        getInputReader().close();
        getMenuPrinter().printInfo("Quitting");
        System.exit(0);
    }

}
