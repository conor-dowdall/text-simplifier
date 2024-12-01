package ie.atu.sw.wordembedding;

/**
 * Record representing a word and its similarity score, typically used for
 * comparing words based on their embeddings.
 *
 * @param word       the word being compared
 * @param similarity the similarity score of the word
 */
public record WordEmbeddingSimilarity(String word, double similarity) {

}
