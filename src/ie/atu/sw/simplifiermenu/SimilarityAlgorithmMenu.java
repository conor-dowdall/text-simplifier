package ie.atu.sw.simplifiermenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.prefs.Preferences;

import ie.atu.sw.menu.Menu;
import ie.atu.sw.menu.MenuItem;
import ie.atu.sw.util.SimilarityAlgorithm;
import ie.atu.sw.wordreplacer.WordReplacer;

public class SimilarityAlgorithmMenu extends Menu {
    private static final String SIMILARITY_ALGORITHM_KEY = "similarityAlgorithmToUse";
    private static final String SIMILARITY_ALGORITHM_DEFAULT = SimilarityAlgorithm.COSINE_SIMILARITY.name();

    private WordReplacer wordReplacer;
    private Preferences preferences;

    public SimilarityAlgorithmMenu(Scanner scanner, WordReplacer wordReplacer, Preferences preferences) {
        super("Similarity Algorithm", scanner);
        this.preferences = preferences;
        this.wordReplacer = wordReplacer;
        this.wordReplacer.setSimilarityAlgorithmToUse(this.getSimilarityAlgorithmToUse());

        List<MenuItem> itemList = new ArrayList<MenuItem>();
        for (SimilarityAlgorithm algorithm : SimilarityAlgorithm.values()) {
            itemList.add(
                    new MenuItem(
                            String.valueOf(algorithm.ordinal() + 1),
                            algorithm.toString(),
                            () -> {
                                this.wordReplacer.setSimilarityAlgorithmToUse(algorithm);
                                this.setSimilarityAlgorithmToUse(algorithm);
                                this.printSuccess("Similarity Algorithm set to: " + algorithm.toString());
                            }));
        }
        this.addMenuItemList(itemList);
    }

    public SimilarityAlgorithm getSimilarityAlgorithmToUse() {
        String similarityAlgorithmToUse = this.preferences.get(
                SIMILARITY_ALGORITHM_KEY,
                SIMILARITY_ALGORITHM_DEFAULT);

        return SimilarityAlgorithm.valueOf(similarityAlgorithmToUse);
    }

    public void setSimilarityAlgorithmToUse(SimilarityAlgorithm similarityAlgorithm) {
        this.preferences.put(SIMILARITY_ALGORITHM_KEY, similarityAlgorithm.name());
    }

}
