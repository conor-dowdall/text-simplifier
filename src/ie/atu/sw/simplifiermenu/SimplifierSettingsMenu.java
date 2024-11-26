package ie.atu.sw.simplifiermenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import ie.atu.sw.menu.Menu;
import ie.atu.sw.menu.MenuItem;
import ie.atu.sw.wordreplacer.WordReplacer;

public class SimplifierSettingsMenu extends Menu {
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

    private WordReplacer wordReplacer;
    private SimilarityAlgorithmMenu similarityAlgorithmMenu;
    private ReplacementMethodMenu replacementMethodMenu;
    private Preferences preferences = Preferences.userNodeForPackage(SimplifierSettingsMenu.class);

    public SimplifierSettingsMenu(Scanner scanner, WordReplacer wordReplacer) {
        super("Settings", scanner);
        this.wordReplacer = wordReplacer;
        this.wordReplacer.setNumSimilarReplacementWordsToStore(this.getNumSimilarReplacementWordsToStore());
        this.similarityAlgorithmMenu = new SimilarityAlgorithmMenu(scanner, this.wordReplacer, this.preferences);
        this.replacementMethodMenu = new ReplacementMethodMenu(scanner, this.wordReplacer, this.preferences);

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
                            this.scanNumSimilarReplacementWordsToStore();
                            this.printMenuAndAcceptChoice();
                        }),
                new MenuItem(
                        "4",
                        "Set Similarity Algorithm to Use",
                        () -> {
                            this.similarityAlgorithmMenu.printMenuAndAcceptChoice();
                            this.printMenuAndAcceptChoice();
                        }),
                new MenuItem(
                        "5",
                        "Set String Replacement Method to Use",
                        () -> {
                            this.replacementMethodMenu.printMenuAndAcceptChoice();
                            this.printMenuAndAcceptChoice();
                        }),
                new MenuItem(
                        "r",
                        "Reset Settings to Default Values",
                        () -> {
                            this.resetSettings();
                            this.printMenuAndAcceptChoice();
                        }),
                new MenuItem(
                        "s",
                        "Print Stored Settings",
                        () -> {
                            this.printStoredSettings();
                            this.printMenuAndAcceptChoice();
                        }),
                new MenuItem(
                        "q",
                        "Close Settings",
                        this::closeSettings));

        this.addMenuItemList(itemList);
    }

    private void loadWordEmbeddingsFileAndPrintMenu() {
        try {
            this.loadWordEmbeddingsFile();
        } catch (IOException e) {
            this.printError(e.getMessage());
        } finally {
            this.printMenuAndAcceptChoice();
        }
    }

    public void loadWordEmbeddingsFile() throws IOException {
        String fileName = this.scanWordEmbeddingsFileName();
        this.wordReplacer.loadWordEmbeddingsFile(fileName);
    }

    private String scanWordEmbeddingsFileName() throws FileNotFoundException {
        String defaultFileName = this.getWordEmbeddingsFileName();
        this.printInfo("Hit ENTER for default: " + defaultFileName);
        this.printWithUnderline("Enter WORD EMBEDDINGS file name: ");
        String inputFileName = this.scanner.nextLine();
        if (inputFileName.isEmpty()) {
            inputFileName = defaultFileName;
        }
        File file = new File(inputFileName);
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("Cannot find WORD EMBEDDINGS file: " + inputFileName);
        } else {
            this.printSuccess("WORD EMBEDDINGS text file = " + inputFileName);
            this.setWordEmbeddingsFileName(inputFileName);
            return inputFileName;
        }
    }

    private String getWordEmbeddingsFileName() {
        return this.preferences.get(WORD_EMBEDDINGS_FILE_NAME_KEY, WORD_EMBEDDINGS_FILE_NAME_DEFAULT);
    }

    private void setWordEmbeddingsFileName(String fileName) {
        this.preferences.put(WORD_EMBEDDINGS_FILE_NAME_KEY, fileName);
    }

    private void loadReplacementWordsFileAndPrintMenu() {
        try {
            this.loadReplacementWordsFile();
        } catch (IOException e) {
            this.printError(e.getMessage());
        } finally {
            this.printMenuAndAcceptChoice();
        }
    }

    public void loadReplacementWordsFile() throws IOException {
        String fileName = this.scanReplacementWordsFileName();
        this.wordReplacer.loadReplacementWordsFile(fileName);
    }

    private String scanReplacementWordsFileName() throws FileNotFoundException {
        String defaultFileName = this.getReplacementWordsFileName();
        this.printInfo("Hit ENTER for default: " + defaultFileName);
        this.printWithUnderline("Enter REPLACEMENT WORDS file name: ");
        String inputFileName = this.scanner.nextLine();
        if (inputFileName.isEmpty()) {
            inputFileName = defaultFileName;
        }
        File file = new File(inputFileName);
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("Cannot find REPLACEMENT WORDS file: " + inputFileName);
        } else {
            this.printSuccess("REPLACEMENT WORDS text file = " + inputFileName);
            this.setReplacementWordsFileName(inputFileName);
            return inputFileName;
        }
    }

    private String getReplacementWordsFileName() {
        return this.preferences.get(REPLACEMENT_WORDS_FILE_NAME_KEY, REPLACEMENT_WORDS_FILE_NAME_DEFAULT);
    }

    private void setReplacementWordsFileName(String fileName) {
        this.preferences.put(REPLACEMENT_WORDS_FILE_NAME_KEY, fileName);
    }

    public String scanInputTextFileName() throws FileNotFoundException {
        String defaultFileName = this.getInputTextFileName();
        this.printInfo("Hit ENTER for default: " + defaultFileName);
        this.printWithUnderline("Enter INPUT TEXT file name: ");
        String inputFileName = this.scanner.nextLine();
        if (inputFileName.isEmpty()) {
            inputFileName = defaultFileName;
        }
        File file = new File(inputFileName);
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("Cannot find INPUT TEXT file: " + inputFileName);
        } else {
            this.printSuccess("INPUT TEXT file = " + inputFileName);
            this.setInputTextFileName(inputFileName);
            return inputFileName;
        }
    }

    private String getInputTextFileName() {
        return this.preferences.get(INPUT_TEXT_FILE_NAME_KEY, INPUT_TEXT_FILE_NAME_DEFAULT);
    }

    private void setInputTextFileName(String fileName) {
        this.preferences.put(INPUT_TEXT_FILE_NAME_KEY, fileName);
    }

    public String scanOutputTextFileName() {
        String defaultOutputTextFileName = this.getOutputTextFileName();
        this.printInfo("Hit ENTER for default: " + defaultOutputTextFileName);
        this.printWithUnderline("Enter OUTPUT TEXT file name: ");
        String outputTextFileName = scanner.nextLine();
        if (outputTextFileName.isEmpty()) {
            outputTextFileName = defaultOutputTextFileName;
        }
        this.printSuccess("OUTPUT TEXT file = " + outputTextFileName);
        this.setOutputTextFileName(outputTextFileName);
        return outputTextFileName;
    }

    private String getOutputTextFileName() {
        return this.preferences.get(OUTPUT_TEXT_FILE_NAME_KEY, OUTPUT_TEXT_FILE_NAME_DEFAULT);
    }

    private void setOutputTextFileName(String fileName) {
        this.preferences.put(OUTPUT_TEXT_FILE_NAME_KEY, fileName);
    }

    public void scanNumSimilarReplacementWordsToStore() {
        int defaultN = this.getNumSimilarReplacementWordsToStore();
        this.printInfo("Hit ENTER for default: " + defaultN);
        this.printWithUnderline("Enter NUMBER of similar words to store: ");

        String input = this.scanner.nextLine();
        int n;

        try {
            if (input.isEmpty()) {
                n = defaultN;
            } else {
                n = Integer.parseInt(input);
            }

            this.wordReplacer.setNumSimilarReplacementWordsToStore(n);
            this.setNumSimilarReplacementWordsToStore(n);
            this.printSuccess("NUMBER of similar words to store = " + n);
        } catch (NumberFormatException e) {
            this.printError(e.getMessage());
        }

    }

    private int getNumSimilarReplacementWordsToStore() {
        return this.preferences.getInt(
                NUM_SIMILAR_WORDS_KEY,
                NUM_SIMILAR_WORDS_DEFAULT);
    }

    private void setNumSimilarReplacementWordsToStore(int n) {
        this.preferences.putInt(NUM_SIMILAR_WORDS_KEY, n);
    }

    private void printStoredSettings() {
        try {
            this.printTitle("Stored Settings");

            this.printInfo("Word Embeddings File Name: \t"
                    + this.getWordEmbeddingsFileName());
            this.printInfo("Replacement Words File Name: \t"
                    + this.getReplacementWordsFileName());
            this.printInfo("Input Text File Name: \t\t"
                    + this.getInputTextFileName());
            this.printInfo("Output Text File Name: \t\t"
                    + this.getOutputTextFileName());
            this.printInfo("Num Similar Words to Store: \t"
                    + this.getNumSimilarReplacementWordsToStore());
            this.printInfo("Similarity Algorighthm: \t\t"
                    + this.similarityAlgorithmMenu.getSimilarityAlgorithmToUse().toString());
            this.printInfo("Replacement Method: \t\t"
                    + this.replacementMethodMenu.getReplacementMethodToUse().toString());
        } catch (Exception e) {
            this.printError("Error reading preferences: " + e.getMessage());
        }
    }

    /**
     * reset all settings to their defaults by clearing the preferences
     */
    private void resetSettings() {
        try {
            this.preferences.clear();
            this.printSuccess("Settings reset to default values");
        } catch (BackingStoreException e) {
            this.printError(e.getMessage());
        }
    }

    private void closeSettings() {
        this.printInfo("Closing Settings");
    }

}
