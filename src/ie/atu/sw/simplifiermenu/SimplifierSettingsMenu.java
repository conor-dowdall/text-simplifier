package ie.atu.sw.simplifiermenu;

import java.io.IOException;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import ie.atu.sw.console.ConsoleProgressMeter;
import ie.atu.sw.menu.MenuItem;
import ie.atu.sw.util.InputReaderInterface;
import ie.atu.sw.util.MenuPrinterInterface;
import ie.atu.sw.wordembedding.WordEmbeddingMap;
import ie.atu.sw.wordreplacer.ReplacementWordSet;
import ie.atu.sw.wordreplacer.WordReplacerAbstract;

/**
 * Represents the settings menu for the simplifier application, extending the
 * functionality of {@link WordReplacerSettingsMenu}. Provides configuration
 * options for managing preferences related to word embeddings, replacement
 * words, similarity algorithms, and replacement methods.
 */
public class SimplifierSettingsMenu extends WordReplacerSettingsMenu {

    // Constants defining preference keys and default values
    private static final String WORD_EMBEDDINGS_FILE_NAME_KEY = "wordEmbeddingsFileName";
    private static final String WORD_EMBEDDINGS_FILE_NAME_DEFAULT = "../word-embeddings.txt";

    private static final String WORD_EMBEDDINGS_FILE_DELIMITER_KEY = "wordEmbeddingsFileDelimiter";
    private static final String WORD_EMBEDDINGS_FILE_DELIMITER_DEFAULT = ", ";

    private static final String REPLACEMENT_WORDS_FILE_NAME_KEY = "replacementWordsFileName";
    private static final String REPLACEMENT_WORDS_FILE_NAME_DEFAULT = "../google-1000.txt";

    private static final String REPLACEMENT_WORDS_FILE_DELIMITER_KEY = "replacementWordsFileDelimiter";
    private static final String REPLACEMENT_WORDS_FILE_DELIMITER_DEFAULT = ", ";

    private static final String INPUT_TEXT_FILE_NAME_KEY = "inputTextFileName";
    private static final String INPUT_TEXT_FILE_NAME_DEFAULT = "../input.txt";

    private static final String OUTPUT_TEXT_FILE_NAME_KEY = "outputTextFileName";
    private static final String OUTPUT_TEXT_FILE_NAME_DEFAULT = "../output.txt";

    private static final String NUM_SIMILAR_WORDS_KEY = "numSimilarReplacementWordsToStore";
    private static final int NUM_SIMILAR_WORDS_DEFAULT = 1;

    // Instance variables
    /**
     * Settings menu for managing similarity algorithm configurations.
     */
    private final SimilarityAlgorithmSettingsMenu similarityAlgorithmMenu;

    /**
     * Settings menu for managing replacement method configurations.
     */
    private final ReplacementMethodSettingsMenu replacementMethodMenu;

    /**
     * Constructs a new instance of the simplifier settings menu.
     *
     * @param inputReader  the input reader for capturing user input.
     * @param menuPrinter  the printer for displaying menu options and messages.
     * @param preferences  the preferences storage for saving and retrieving
     *                     settings.
     * @param wordReplacer the word replacer instance to configure.
     */
    public SimplifierSettingsMenu(
            InputReaderInterface inputReader,
            MenuPrinterInterface menuPrinter,
            Preferences preferences,
            WordReplacerAbstract wordReplacer) {

        super(inputReader, menuPrinter, preferences, wordReplacer);

        this.similarityAlgorithmMenu = new SimilarityAlgorithmSettingsMenu(
                inputReader,
                menuPrinter,
                preferences,
                wordReplacer);

        this.replacementMethodMenu = new ReplacementMethodSettingsMenu(
                inputReader,
                menuPrinter,
                preferences,
                wordReplacer);

    }

    /**
     * Initializes the word replacer instance with the number of similar replacement
     * words to store.
     */
    @Override
    protected void initWordReplacer() {
        getWordReplacer()
                .setSimilarReplacementWords(getNumSimilarReplacementWordsToStore());
    }

    /**
     * Creates and registers the menu items for this settings menu.
     */
    @Override
    protected void createMenuItems() {

        List<MenuItem> itemList = List.of(

                new MenuItem(
                        "1",
                        "Load Word-Embeddings File",
                        () -> {
                            try {
                                loadWordEmbeddingsFile();
                            } catch (Exception e) {
                                getMenuPrinter().printError(e.getMessage());
                            } finally {
                                printMenuAndAcceptChoice();
                            }
                        }),

                new MenuItem(
                        "2",
                        "Load Replacement-Words File",
                        () -> {
                            try {
                                loadReplacementWordsFile();
                            } catch (Exception e) {
                                getMenuPrinter().printError(e.getMessage());
                            } finally {
                                printMenuAndAcceptChoice();
                            }
                        }),

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
                        "6",
                        "Set Word-Embeddings File Delimiter",
                        () -> {
                            setWordEmbeddingsFileDelimiter(
                                    getInputReader()
                                            .getString(
                                                    "Word Embeddings File DELIMITER",
                                                    getWordEmbeddingsFileDelimiter()));
                            getMenuPrinter()
                                    .printInfo(
                                            "Word Embeddings File DELIMITER = " + getWordEmbeddingsFileDelimiter());
                            printMenuAndAcceptChoice();
                        }),

                new MenuItem(
                        "7",
                        "Set Replacement-Words File Delimiter",
                        () -> {
                            setReplacementWordsFileDelimiter(
                                    getInputReader()
                                            .getString(
                                                    "Replacement Words File DELIMITER",
                                                    getReplacementWordsFileDelimiter()));
                            getMenuPrinter()
                                    .printInfo(
                                            "Replacement Words File DELIMITER = " + getReplacementWordsFileDelimiter());
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

    /**
     * Retrieves the current word embeddings file name from the preferences.
     *
     * @return the word embeddings file name, or the default value if not set.
     */
    private String getWordEmbeddingsFileName() {
        return getPreferences().get(WORD_EMBEDDINGS_FILE_NAME_KEY, WORD_EMBEDDINGS_FILE_NAME_DEFAULT);
    }

    /**
     * Updates the word embeddings file name in the preferences.
     *
     * @param fileName the new word embeddings file name to set.
     */
    private void setWordEmbeddingsFileName(String fileName) {
        getPreferences().put(WORD_EMBEDDINGS_FILE_NAME_KEY, fileName);
    }

    /**
     * Retrieves the current delimiter for the word embeddings file from the
     * preferences.
     *
     * @return the word embeddings file delimiter, or the default value if not set.
     */
    private String getWordEmbeddingsFileDelimiter() {
        return getPreferences().get(WORD_EMBEDDINGS_FILE_DELIMITER_KEY, WORD_EMBEDDINGS_FILE_DELIMITER_DEFAULT);
    }

    /**
     * Updates the delimiter for the word embeddings file in the preferences.
     *
     * @param delimiter the new delimiter for the word embeddings file to set.
     */
    private void setWordEmbeddingsFileDelimiter(String delimiter) {
        getPreferences().put(WORD_EMBEDDINGS_FILE_DELIMITER_KEY, delimiter);
    }

    /**
     * Updates the delimiter for the word embeddings file in the preferences.
     *
     * @param delimiter the new delimiter for the word embeddings file to set.
     */
    private String getReplacementWordsFileName() {
        return getPreferences().get(REPLACEMENT_WORDS_FILE_NAME_KEY, REPLACEMENT_WORDS_FILE_NAME_DEFAULT);
    }

    /**
     * Updates the replacement words file name in the preferences.
     *
     * @param fileName the new replacement words file name to set.
     */
    private void setReplacementWordsFileName(String fileName) {
        getPreferences().put(REPLACEMENT_WORDS_FILE_NAME_KEY, fileName);
    }

    /**
     * Retrieves the current delimiter for the replacement words file from the
     * preferences.
     *
     * @return the replacement words file delimiter, or the default value if not
     *         set.
     */
    private String getReplacementWordsFileDelimiter() {
        return getPreferences().get(REPLACEMENT_WORDS_FILE_DELIMITER_KEY, REPLACEMENT_WORDS_FILE_DELIMITER_DEFAULT);
    }

    /**
     * Updates the delimiter for the replacement words file in the preferences.
     *
     * @param delimiter the new delimiter for the replacement words file to set.
     */
    private void setReplacementWordsFileDelimiter(String delimiter) {
        getPreferences().put(REPLACEMENT_WORDS_FILE_DELIMITER_KEY, delimiter);
    }

    /**
     * Retrieves the current input text file name from the preferences.
     *
     * @return the input text file name, or the default value if not set.
     */
    String getInputTextFileName() {
        return getPreferences().get(INPUT_TEXT_FILE_NAME_KEY, INPUT_TEXT_FILE_NAME_DEFAULT);
    }

    /**
     * Updates the input text file name in the preferences.
     *
     * @param fileName the new input text file name to set.
     */
    void setInputTextFileName(String fileName) {
        getPreferences().put(INPUT_TEXT_FILE_NAME_KEY, fileName);
    }

    /**
     * Retrieves the current output text file name from the preferences.
     *
     * @return the output text file name, or the default value if not set.
     */
    String getOutputTextFileName() {
        return getPreferences().get(OUTPUT_TEXT_FILE_NAME_KEY, OUTPUT_TEXT_FILE_NAME_DEFAULT);
    }

    /**
     * Updates the output text file name in the preferences.
     *
     * @param fileName the new output text file name to set.
     */
    void setOutputTextFileName(String fileName) {
        getPreferences().put(OUTPUT_TEXT_FILE_NAME_KEY, fileName);
    }

    /**
     * Retrieves the current number of similar replacement words to store from the
     * preferences.
     *
     * @return the number of similar replacement words, or the default value if not
     *         set.
     */
    private int getNumSimilarReplacementWordsToStore() {
        return getPreferences().getInt(NUM_SIMILAR_WORDS_KEY, NUM_SIMILAR_WORDS_DEFAULT);
    }

    /**
     * Updates the number of similar replacement words to store in the preferences.
     *
     * @param n the new number of similar replacement words to set.
     */
    private void setNumSimilarReplacementWordsToStore(int n) {
        getPreferences().putInt(NUM_SIMILAR_WORDS_KEY, n);
    }

    /**
     * Handles loading of the word embeddings file, updating preferences, and
     * setting the word replacer's map.
     * <p>
     * Loading word embeddings from file: O(n), where n is the number of
     * lines in the file.
     * </p>
     *
     * @throws IOException           if an I/O error occurs while reading the file.
     * @throws NumberFormatException if the file format is invalid.
     */
    void loadWordEmbeddingsFile() throws IOException, NumberFormatException {
        try {
            int CROSSOVER_TIME = 99;

            getMenuPrinter().printInfo("\"Word Embeddings Delimiter\" = \"" + getWordEmbeddingsFileDelimiter() + "\"");

            String fileName = getInputReader().getFileName(
                    "WORD EMBEDDINGS",
                    getWordEmbeddingsFileName(),
                    true);

            for (int i = 0; i <= CROSSOVER_TIME; i++) {
                ConsoleProgressMeter.printProgress(i, 100);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    System.err.println("Sleep interrupted: " + e.getMessage());
                }
            }
            getWordReplacer()
                    .setWordEmbeddingsMap(
                            WordEmbeddingMap
                                    .getMapFromFile(fileName, getWordEmbeddingsFileDelimiter()));

            setWordEmbeddingsFileName(fileName);

            for (int i = CROSSOVER_TIME; i <= 100; i++) {
                ConsoleProgressMeter.printProgress(i, 100);
            }

        } catch (NumberFormatException e) {
            getMenuPrinter()
                    .printError("Something is wrong in the word-embeddings file. Suggestion: Check delimiter setting.");
        }
    }

    /**
     * Handles loading of the replacement words file, updating preferences, and
     * setting the word replacer's set.
     * <p>
     * Loading replacement words from file: O(n), where n is the number of
     * lines in the file.
     * </p>
     *
     * @throws IOException if an I/O error occurs while reading the file.
     */
    void loadReplacementWordsFile() throws IOException {
        int CROSSOVER_TIME = 99;

        getMenuPrinter().printInfo("\"Replacement Words Delimiter\" = \"" + getReplacementWordsFileDelimiter() + "\"");

        String fileName = getInputReader().getFileName(
                "REPLACEMENT WORDS",
                getReplacementWordsFileName(),
                true);

        for (int i = 0; i <= CROSSOVER_TIME; i++) {
            ConsoleProgressMeter.printProgress(i, 100);
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                System.err.println("Sleep interrupted: " + e.getMessage());
            }
        }

        getWordReplacer()
                .setReplacementWordsSet(
                        ReplacementWordSet
                                .getSetFromFile(fileName, getReplacementWordsFileDelimiter()));

        setReplacementWordsFileName(fileName);

        for (int i = CROSSOVER_TIME; i <= 100; i++) {
            ConsoleProgressMeter.printProgress(i, 100);
        }
    }

    /**
     * Updates the number of similar replacement words to store and reflects the
     * change in the preferences and UI.
     */
    public void scanNumSimilarReplacementWordsToStore() {

        int n = getInputReader().getInt(
                "SIMILAR WORDS TO STORE",
                getNumSimilarReplacementWordsToStore());

        getWordReplacer().setSimilarReplacementWords(n);

        setNumSimilarReplacementWordsToStore(n);

        getMenuPrinter().printInfo("SIMILAR WORDS TO STORE = " + n);

    }

    /**
     * Prints the current preferences to the menu printer.
     */
    @Override
    public void printPreferences() {
        try {

            getMenuPrinter().printTitle("Preferences");

            getMenuPrinter().printInfo("Word Embeddings File Name: \t"
                    + getWordEmbeddingsFileName());

            getMenuPrinter().printInfo("\"Word Embeddings Delimiter\": \t\""
                    + getWordEmbeddingsFileDelimiter() + "\"");

            getMenuPrinter().printInfo("Replacement Words File Name: \t"
                    + getReplacementWordsFileName());

            getMenuPrinter().printInfo("\"Replacement Words Delimiter\": \t\""
                    + getReplacementWordsFileDelimiter() + "\"");

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
     * Resets all preferences to their default values.
     */
    @Override
    public void resetPreferences() {
        try {

            getPreferences().clear();

            getWordReplacer().setSimilarReplacementWords(getNumSimilarReplacementWordsToStore());

            similarityAlgorithmMenu.resetPreferences();

            replacementMethodMenu.resetPreferences();

            getMenuPrinter().printSuccess("Preferences reset to default values");

            printPreferences();

            getMenuPrinter().printWarning("Any Previous Word Embeddings and Replacement Words Are Still Loaded");

        } catch (BackingStoreException e) {
            getMenuPrinter().printError(e.getMessage());
        }
    }

    /**
     * Closes the settings menu and prints a closing message.
     */
    private void closeSettings() {
        getMenuPrinter().printInfo("Closing Settings");
    }

}
