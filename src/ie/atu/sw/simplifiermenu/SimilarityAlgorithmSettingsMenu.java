package ie.atu.sw.simplifiermenu;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import ie.atu.sw.menu.MenuItem;
import ie.atu.sw.util.InputReaderInterface;
import ie.atu.sw.util.MenuPrinterInterface;
import ie.atu.sw.util.SimilarityAlgorithm;
import ie.atu.sw.wordreplacer.WordReplacerAbstract;

/**
 * Menu for configuring the similarity algorithm used by the WordReplacer.
 */
public class SimilarityAlgorithmSettingsMenu extends WordReplacerSettingsMenu {

    private static final String SIMILARITY_ALGORITHM_KEY = "similarityAlgorithmToUse";
    private static final String SIMILARITY_ALGORITHM_DEFAULT = SimilarityAlgorithm.COSINE_SIMILARITY.name();

    /**
     * Constructor to initialize the SimilarityAlgorithmSettingsMenu.
     *
     * @param inputReader  the input reader interface for user input.
     * @param menuPrinter  the menu printer interface for output.
     * @param preferences  the preferences object for storing settings.
     * @param wordReplacer the WordReplacerAbstract instance to configure.
     */
    public SimilarityAlgorithmSettingsMenu(
            InputReaderInterface inputReader,
            MenuPrinterInterface menuPrinter,
            Preferences preferences,
            WordReplacerAbstract wordReplacer) {

        super(inputReader, menuPrinter, preferences, wordReplacer);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initWordReplacer() {
        getWordReplacer().setSimilarityAlgorithm(getSimilarityAlgorithmToUse());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void createMenuItems() {
        List<MenuItem> itemList = new ArrayList<MenuItem>();
        for (SimilarityAlgorithm algorithm : SimilarityAlgorithm.values()) {
            itemList.add(
                    new MenuItem(
                            String.valueOf(algorithm.ordinal() + 1),
                            algorithm.toString(),
                            () -> setSimilarityAlgorithmToUse(algorithm)));
        }

        addMenuItemList(itemList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printPreferences() {
        getMenuPrinter().printInfo("Similarity Algorithm: \t\t"
                + getSimilarityAlgorithmToUse().toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetPreferences() {
        getWordReplacer().setSimilarityAlgorithm(getSimilarityAlgorithmToUse());
    }

    /**
     * Retrieves the similarity algorithm to use from preferences.
     *
     * @return the SimilarityAlgorithm to use.
     */
    private SimilarityAlgorithm getSimilarityAlgorithmToUse() {
        return SimilarityAlgorithm
                .valueOf(
                        getPreferences().get(SIMILARITY_ALGORITHM_KEY, SIMILARITY_ALGORITHM_DEFAULT));
    }

    /**
     * Sets the similarity algorithm to use and updates preferences.
     *
     * @param similarityAlgorithm the SimilarityAlgorithm to set.
     */
    private void setSimilarityAlgorithmToUse(SimilarityAlgorithm similarityAlgorithm) {
        getWordReplacer().setSimilarityAlgorithm(similarityAlgorithm);
        getPreferences().put(SIMILARITY_ALGORITHM_KEY, similarityAlgorithm.name());
        getMenuPrinter().printSuccess("Similarity Algorithm set to: " + similarityAlgorithm.toString());
    }

}
