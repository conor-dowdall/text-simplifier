package ie.atu.sw.wordembedding;

import java.util.List;

public record WordEmbedding(String word, double[] embedding, List<String> similarWords) {

    public void addSimilarWord(String word) {
        similarWords.add(word);
    }

    public void emptySimilarWordList() {
        similarWords.clear();
    }

}
