package ie.atu.sw.util;

import java.util.Set;

/**
 * Interface for managing a set of replacement words.
 * Provides methods for accessing, querying, and checking properties of the word
 * set.
 */
public interface ReplacementWordSetInterface {
    /**
     * Retrieves the set of replacement words.
     *
     * @return a {@link Set} of strings representing the replacement words
     */
    Set<String> getReplacementWordSet();

    /**
     * Retrieves the size of the replacement word set.
     *
     * @return the number of words in the set
     */
    int getSize();

    /**
     * Checks whether a specific word is contained in the replacement word set.
     *
     * @param word the word to check
     * @return {@code true} if the word is present in the set, {@code false}
     *         otherwise
     */
    boolean containsWord(String word);

}
