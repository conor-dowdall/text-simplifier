package ie.atu.sw.simplifiermenu;

import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

import ie.atu.sw.menu.Menu;
import ie.atu.sw.menu.MenuItem;
import ie.atu.sw.util.InputReaderInterface;
import ie.atu.sw.util.MenuPrinterInterface;
import ie.atu.sw.wordreplacer.WordReplacerAbstract;

/**
 * Main menu for the Simplifier application. Provides options for text
 * simplification, managing settings, and loading configuration files.
 */
public class SimplifierMainMenu extends Menu {

    private final WordReplacerAbstract wordReplacer;
    private final SimplifierSettingsMenu settingsMenu;
    private final Preferences preferences = Preferences.userNodeForPackage(SimplifierMainMenu.class);

    /**
     * Constructs the main menu for the Simplifier application.
     *
     * @param inputReader  the input reader for user interactions
     * @param menuPrinter  the printer for displaying menu and messages
     * @param wordReplacer the word replacer instance for text simplification
     */
    public SimplifierMainMenu(
            InputReaderInterface inputReader,
            MenuPrinterInterface menuPrinter,
            WordReplacerAbstract wordReplacer) {

        super("Simplifier", inputReader, menuPrinter);

        this.wordReplacer = wordReplacer;

        settingsMenu = new SimplifierSettingsMenu(inputReader, menuPrinter, preferences, wordReplacer);

    }

    /**
     * Creates and initializes the menu items for the main menu.
     */
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

    /**
     * Handles the logic for simplifying a text file.
     */
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

    /**
     * Handles the logic for simplifying an input string of text.
     */
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

    /**
     * Launches the settings menu.
     */
    private void launchSettingsMenu() {
        settingsMenu.printMenuAndAcceptChoice();
    }

    /**
     * Handles the logic for quitting the program.
     */
    private void quitProgram() {
        getInputReader().close();
        getMenuPrinter().printInfo("Quitting");
        System.exit(0);
    }

}
