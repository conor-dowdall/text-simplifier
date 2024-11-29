package ie.atu.sw.simplifiermenu;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import ie.atu.sw.console.ConsoleProgressMeter;
import ie.atu.sw.menu.MenuItem;
import ie.atu.sw.menu.MenuPrinter;
import ie.atu.sw.util.FileName;
import ie.atu.sw.wordreplacer.WordReplacer;

public class SimplifierSettingsMenu extends WordReplacerSettingsMenu {

    private static final String WORD_EMBEDDINGS_FILE_NAME_KEY = "wordEmbeddingsFileName";
    private static final String WORD_EMBEDDINGS_FILE_NAME_DEFAULT = "../word-embeddings.txt";

    private static final String REPLACEMENT_WORDS_FILE_NAME_KEY = "replacementWordsFileName";
    private static final String REPLACEMENT_WORDS_FILE_NAME_DEFAULT = "../google-1000.txt";

    private static final String INPUT_TEXT_FILE_NAME_KEY = "inputTextFileName";
    private static final String INPUT_TEXT_FILE_NAME_DEFAULT = "../input.txt";

    private static final String OUTPUT_TEXT_FILE_NAME_KEY = "outputTextFileName";
    private static final String OUTPUT_TEXT_FILE_NAME_DEFAULT = "../output.txt";

    private static final String NUM_SIMILAR_WORDS_KEY = "numSimilarReplacementWordsToStore";
    private static final int NUM_SIMILAR_WORDS_DEFAULT = 1;

    private final SimilarityAlgorithmSettingsMenu similarityAlgorithmMenu;
    private final ReplacementMethodSettingsMenu replacementMethodMenu;

    public SimplifierSettingsMenu(
            Scanner scanner,
            MenuPrinter menuPrinter,
            Preferences preferences,
            WordReplacer wordReplacer) {

        super(scanner, menuPrinter, preferences, wordReplacer);

        this.similarityAlgorithmMenu = new SimilarityAlgorithmSettingsMenu(
                scanner,
                menuPrinter,
                preferences,
                wordReplacer);

        this.replacementMethodMenu = new ReplacementMethodSettingsMenu(
                scanner,
                menuPrinter,
                preferences,
                wordReplacer);

    }

    @Override
    protected void initWordReplacer() {
        getWordReplacer()
                .setNumSimilarReplacementWordsToStore(getNumSimilarReplacementWordsToStore());
    }

    @Override
    protected void createMenuItems() {
        List<MenuItem> itemList = List.of(
                new MenuItem(
                        "1",
                        "Load Word-Embeddings File",
                        this::loadWordEmbeddingsFileAndPrintMenu),
                new MenuItem(
                        "2",
                        "Load Replacement-Words File",
                        this::loadReplacementWordsFileAndPrintMenu),
                new MenuItem(
                        "3",
                        "Set Number of Similar-Replacement-Words to Store",
                        () -> {
                            scanNumSimilarReplacementWordsToStore();
                            printMenuAndAcceptChoice();
                        }),
                new MenuItem(
                        "4",
                        "Set Similarity Algorithm to Use",
                        () -> {
                            similarityAlgorithmMenu.printMenuAndAcceptChoice();
                            printMenuAndAcceptChoice();
                        }),
                new MenuItem(
                        "5",
                        "Set String Replacement Method to Use",
                        () -> {
                            replacementMethodMenu.printMenuAndAcceptChoice();
                            printMenuAndAcceptChoice();
                        }),
                new MenuItem(
                        "r",
                        "Reset Settings to Default Values",
                        () -> {
                            resetPreferences();
                            printMenuAndAcceptChoice();
                        }),
                new MenuItem(
                        "s",
                        "Print Preferences",
                        () -> {
                            printPreferences();
                            printMenuAndAcceptChoice();
                        }),
                new MenuItem(
                        "q",
                        "Close Settings",
                        this::closeSettings));

        addMenuItemList(itemList);
    }

    private String getWordEmbeddingsFileName() {
        return getPreferences().get(WORD_EMBEDDINGS_FILE_NAME_KEY, WORD_EMBEDDINGS_FILE_NAME_DEFAULT);
    }

    private void setWordEmbeddingsFileName(String fileName) {
        getPreferences().put(WORD_EMBEDDINGS_FILE_NAME_KEY, fileName);
    }

    private String getReplacementWordsFileName() {
        return getPreferences().get(REPLACEMENT_WORDS_FILE_NAME_KEY, REPLACEMENT_WORDS_FILE_NAME_DEFAULT);
    }

    private void setReplacementWordsFileName(String fileName) {
        getPreferences().put(REPLACEMENT_WORDS_FILE_NAME_KEY, fileName);
    }

    String getInputTextFileName() {
        return getPreferences().get(INPUT_TEXT_FILE_NAME_KEY, INPUT_TEXT_FILE_NAME_DEFAULT);
    }

    void setInputTextFileName(String fileName) {
        getPreferences().put(INPUT_TEXT_FILE_NAME_KEY, fileName);
    }

    String getOutputTextFileName() {
        return getPreferences().get(OUTPUT_TEXT_FILE_NAME_KEY, OUTPUT_TEXT_FILE_NAME_DEFAULT);
    }

    void setOutputTextFileName(String fileName) {
        getPreferences().put(OUTPUT_TEXT_FILE_NAME_KEY, fileName);
    }

    private int getNumSimilarReplacementWordsToStore() {
        return getPreferences().getInt(NUM_SIMILAR_WORDS_KEY, NUM_SIMILAR_WORDS_DEFAULT);
    }

    private void setNumSimilarReplacementWordsToStore(int n) {
        getPreferences().putInt(NUM_SIMILAR_WORDS_KEY, n);
    }

    private void loadWordEmbeddingsFileAndPrintMenu() {
        try {
            loadWordEmbeddingsFile();
        } catch (IOException e) {
            getMenuPrinter().printError(e.getMessage());
        } finally {
            printMenuAndAcceptChoice();
        }
    }

    void loadWordEmbeddingsFile() throws IOException {
        int CROSSOVER_TIME = 99;

        String fileName = FileName.scanFileName(
                getScanner(),
                getMenuPrinter(),
                "WORD EMBEDDINGS",
                getWordEmbeddingsFileName());

        for (int i = 0; i <= CROSSOVER_TIME; i++) {
            ConsoleProgressMeter.printProgress(i, 100);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                System.err.println("Sleep interrupted: " + e.getMessage());
            }
        }

        getWordReplacer().loadWordEmbeddingsFile(fileName);
        setWordEmbeddingsFileName(fileName);

        for (int i = CROSSOVER_TIME; i <= 100; i++) {
            ConsoleProgressMeter.printProgress(i, 100);
        }
    }

    private void loadReplacementWordsFileAndPrintMenu() {
        try {
            loadReplacementWordsFile();
        } catch (IOException e) {
            getMenuPrinter().printError(e.getMessage());
        } finally {
            printMenuAndAcceptChoice();
        }
    }

    void loadReplacementWordsFile() throws IOException {
        int CROSSOVER_TIME = 99;

        String fileName = FileName.scanFileName(
                getScanner(),
                getMenuPrinter(),
                "REPLACEMENT WORDS",
                getReplacementWordsFileName());

        for (int i = 0; i <= CROSSOVER_TIME; i++) {
            ConsoleProgressMeter.printProgress(i, 100);
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                System.err.println("Sleep interrupted: " + e.getMessage());
            }
        }

        getWordReplacer().loadReplacementWordsFile(fileName);

        setReplacementWordsFileName(fileName);

        for (int i = CROSSOVER_TIME; i <= 100; i++) {
            ConsoleProgressMeter.printProgress(i, 100);
        }
    }

    public void scanNumSimilarReplacementWordsToStore() {
        int defaultN = getNumSimilarReplacementWordsToStore();
        getMenuPrinter().printInfo("Hit ENTER for default: " + defaultN);
        getMenuPrinter().printWithUnderline("Enter NUMBER of similar words to store: ");

        String input = getScanner().nextLine();
        int n;

        try {
            if (input.isEmpty()) {
                n = defaultN;
            } else {
                n = Integer.parseInt(input);
            }

            getWordReplacer().setNumSimilarReplacementWordsToStore(n);
            setNumSimilarReplacementWordsToStore(n);
            getMenuPrinter().printSuccess("NUMBER of similar words to store = " + n);
        } catch (NumberFormatException e) {
            getMenuPrinter().printError(e.getMessage());
        }

    }

    @Override
    public void printPreferences() {
        try {

            getMenuPrinter().printTitle("Preferences");

            getMenuPrinter().printInfo("Word Embeddings File Name: \t"
                    + getWordEmbeddingsFileName());

            getMenuPrinter().printInfo("Replacement Words File Name: \t"
                    + getReplacementWordsFileName());

            getMenuPrinter().printInfo("Input Text File Name: \t\t"
                    + getInputTextFileName());

            getMenuPrinter().printInfo("Output Text File Name: \t\t"
                    + getOutputTextFileName());

            getMenuPrinter().printInfo("Num Similar Words to Store: \t"
                    + getNumSimilarReplacementWordsToStore());

            similarityAlgorithmMenu.printPreferences();

            replacementMethodMenu.printPreferences();

        } catch (Exception e) {
            getMenuPrinter().printError("Error reading preferences: " + e.getMessage());
        }
    }

    /**
     * reset all settings to their defaults by clearing the preferences
     */
    @Override
    public void resetPreferences() {
        try {

            getPreferences().clear();

            getWordReplacer().setNumSimilarReplacementWordsToStore(getNumSimilarReplacementWordsToStore());

            similarityAlgorithmMenu.resetPreferences();

            replacementMethodMenu.resetPreferences();

            getMenuPrinter().printSuccess("Preferences reset to default values");

            printPreferences();

        } catch (BackingStoreException e) {
            getMenuPrinter().printError(e.getMessage());
        }
    }

    private void closeSettings() {
        getMenuPrinter().printInfo("Closing Settings");
    }

}
