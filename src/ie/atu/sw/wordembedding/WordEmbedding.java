package ie.atu.sw.wordembedding;

import java.util.List;

/**
 * Record representing a word and its associated embedding. Includes methods to
 * manage a list of similar words.
 *
 * @param word         the word represented by this embedding
 * @param embedding    the vector representing the word's embedding
 * @param similarWords a list of similar words associated with this embedding
 */
public record WordEmbedding(String word, double[] embedding, List<String> similarWords) {

    /**
     * Adds a word to the list of similar words.
     *
     * @param word the similar word to add
     */
    public void addSimilarWord(String word) {
        similarWords.add(word);
    }

    /**
     * Clears the list of similar words.
     */
    public void emptySimilarWordList() {
        similarWords.clear();
    }

}
