package ie.atu.sw.simplifiermenu;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import ie.atu.sw.menu.MenuItem;
import ie.atu.sw.menu.MenuPrinter;
import ie.atu.sw.util.InputReader;
import ie.atu.sw.util.SimilarityAlgorithm;
import ie.atu.sw.wordreplacer.WordReplacer;

public class SimilarityAlgorithmSettingsMenu extends WordReplacerSettingsMenu {

    private static final String SIMILARITY_ALGORITHM_KEY = "similarityAlgorithmToUse";
    private static final String SIMILARITY_ALGORITHM_DEFAULT = SimilarityAlgorithm.COSINE_SIMILARITY.name();

    public SimilarityAlgorithmSettingsMenu(
            InputReader inputReader,
            MenuPrinter menuPrinter,
            Preferences preferences,
            WordReplacer wordReplacer) {

        super(inputReader, menuPrinter, preferences, wordReplacer);

    }

    @Override
    protected void initWordReplacer() {
        getWordReplacer().setSimilarityAlgorithm(getSimilarityAlgorithmToUse());
    }

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

    @Override
    public void printPreferences() {
        getMenuPrinter().printInfo("Similarity Algorithm: \t\t"
                + getSimilarityAlgorithmToUse().toString());
    }

    @Override
    public void resetPreferences() {
        getWordReplacer().setSimilarityAlgorithm(getSimilarityAlgorithmToUse());
    }

    private SimilarityAlgorithm getSimilarityAlgorithmToUse() {
        return SimilarityAlgorithm
                .valueOf(
                        getPreferences().get(SIMILARITY_ALGORITHM_KEY, SIMILARITY_ALGORITHM_DEFAULT));
    }

    private void setSimilarityAlgorithmToUse(SimilarityAlgorithm similarityAlgorithm) {
        getWordReplacer().setSimilarityAlgorithm(similarityAlgorithm);
        getPreferences().put(SIMILARITY_ALGORITHM_KEY, similarityAlgorithm.name());
        getMenuPrinter().printSuccess("Similarity Algorithm set to: " + similarityAlgorithm.toString());
    }

}
