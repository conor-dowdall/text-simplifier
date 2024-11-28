package ie.atu.sw.simplifiermenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.prefs.Preferences;

import ie.atu.sw.menu.MenuItem;
import ie.atu.sw.menu.MenuPrinter;
import ie.atu.sw.menu.SettingsMenu;
import ie.atu.sw.util.SimilarityAlgorithm;
import ie.atu.sw.wordreplacer.WordReplacer;

public class SimilarityAlgorithmSettingsMenu extends SettingsMenu {

    private static final String SIMILARITY_ALGORITHM_KEY = "similarityAlgorithmToUse";
    private static final String SIMILARITY_ALGORITHM_DEFAULT = SimilarityAlgorithm.COSINE_SIMILARITY.name();

    private WordReplacer wordReplacer;

    public SimilarityAlgorithmSettingsMenu(
            Scanner scanner,
            MenuPrinter menuPrinter,
            Preferences preferences,
            WordReplacer wordReplacer) {

        super("Similarity Algorithm", scanner, menuPrinter, preferences);

        this.wordReplacer = wordReplacer;
        this.wordReplacer.setSimilarityAlgorithmToUse(getSimilarityAlgorithmToUse());

    }

    @Override
    protected void createMenuItems() {
        List<MenuItem> itemList = new ArrayList<MenuItem>();
        for (SimilarityAlgorithm algorithm : SimilarityAlgorithm.values()) {
            itemList.add(
                    new MenuItem(
                            String.valueOf(algorithm.ordinal() + 1),
                            algorithm.toString(),
                            () -> {
                                wordReplacer.setSimilarityAlgorithmToUse(algorithm);
                                setSimilarityAlgorithmToUse(algorithm);
                                getMenuPrinter().printSuccess("Similarity Algorithm set to: " + algorithm.toString());
                            }));
        }

        addMenuItemList(itemList);
    }

    public SimilarityAlgorithm getSimilarityAlgorithmToUse() {
        return SimilarityAlgorithm
                .valueOf(
                        getPreferences().get(SIMILARITY_ALGORITHM_KEY, SIMILARITY_ALGORITHM_DEFAULT));
    }

    public void setSimilarityAlgorithmToUse(SimilarityAlgorithm similarityAlgorithm) {
        getPreferences().put(SIMILARITY_ALGORITHM_KEY, similarityAlgorithm.name());
    }

    @Override
    public void printPreferences() {
        getMenuPrinter().printInfo("Similarity Algorithm: \t\t"
                + getSimilarityAlgorithmToUse().toString());
    }

    @Override
    public void resetPreferences() {
        wordReplacer.setSimilarityAlgorithmToUse(getSimilarityAlgorithmToUse());
    }

}
