package ie.atu.sw.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import ie.atu.sw.wordembedding.WordEmbedding;

/**
 * Interface defining the structure and operations for managing a map of word
 * embeddings.
 * Provides methods to retrieve word embeddings, their associated words, and
 * general information about the collection.
 */
public interface WordEmbeddingMapInterface {

    /**
     * Retrieves the complete map of words and their associated embeddings.
     *
     * @return a map where the key is a word (String) and the value is its
     *         corresponding {@link WordEmbedding}
     */
    Map<String, WordEmbedding> getWordEmbeddingMap();

    /**
     * Gets the total number of word embeddings in the map.
     *
     * @return the size of the word embedding map
     */
    int getSize();

    /**
     * Retrieves the set of words that have embeddings in the map.
     *
     * @return a set of words (keys) in the word embedding map
     */
    Set<String> getWords();

    /**
     * Retrieves all word embeddings in the map as a collection.
     *
     * @return a collection of {@link WordEmbedding} objects in the map
     */
    Collection<WordEmbedding> getEmbeddings();

    /**
     * Retrieves the word embedding for a specific word.
     *
     * @param word the word for which the embedding is to be retrieved
     * @return the {@link WordEmbedding} object associated with the given word,
     *         or null if the word is not found
     */
    WordEmbedding getWordEmbedding(String word);

}
