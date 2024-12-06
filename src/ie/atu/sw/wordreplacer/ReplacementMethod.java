package ie.atu.sw.wordreplacer;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Enum representing different replacement methods used for selecting a word
 * from a list based on specific criteria.
 */
public enum ReplacementMethod {

    /**
     * Replacement method that returns the most similar word, which is assumed
     * to be the last word in the list after the priority queue has been processed.
     * Time Complexity: O(1) for accessing the last element of the list.
     */
    MOST_SIMILAR("Most Similar Word") {
        @Override
        public String getReplacement(List<String> strings) {
            return strings.getLast(); // the last is the most similar after popping off the PriorityQueue
        }
    },

    /**
     * Replacement method that returns the least similar word, which is assumed
     * to be the first word in the list after the priority queue has been processed.
     * Accessing the first element of a list using getFirst() is O(1).
     */
    LEAST_SIMILAR("Least Similar Word") {
        @Override
        public String getReplacement(List<String> strings) {
            return strings.getFirst(); // the last is the most similar after popping off the PriorityQueue
        }
    },

    /**
     * Replacement method that returns a random word from the list. The method
     * performs constant-time operations for each step. Checking if the list is null
     * or empty, generating a random index, and accessing an element by index in a
     * list are all O(1) operations.
     */
    RANDOM("Random Word from List") {
        @Override
        public String getReplacement(List<String> strings) {
            if (strings == null || strings.isEmpty()) {
                return "";
            }
            int randomIndex = ThreadLocalRandom.current().nextInt(strings.size());
            return strings.get(randomIndex);
        }
    },

    /**
     * Replacement method that biases the selection toward the most similar word in
     * the list, which is assumed to be the last word in the list after the priority
     * queue has been processed. The two loops in this method run independently, and
     * each has O(n) complexity. The total time complexity is O(n)+O(n)=O(n).
     */
    BIASED_MOST_SIMILAR("Biased Toward Most Similar Word from List") {
        @Override
        public String getReplacement(List<String> strings) {
            if (strings == null || strings.isEmpty()) {
                return "";
            }

            double totalWeight = 0.0;
            for (int i = 0; i < strings.size(); i++) {
                totalWeight += getBiasTowardEndWeight(i);
            }

            // Generate a random number in the range [0, totalWeight)
            double randomValue = ThreadLocalRandom.current().nextDouble(totalWeight);

            // Traverse the list and find the corresponding element
            double cumulativeWeight = 0.0;
            for (int i = 0; i < strings.size(); i++) {
                cumulativeWeight += getBiasTowardEndWeight(i);
                if (randomValue < cumulativeWeight) {
                    return strings.get(i);
                }
            }

            return "";
        }
    },

    /**
     * Replacement method that returns the entire list of words as a string.
     * The toString method of a List internally uses a StringBuilder to efficiently
     * handle string concatenation, so the overhead is minimized and remains
     * O(n).
     */
    ARRAY("Array/List of Words") {
        @Override
        public String getReplacement(List<String> strings) {
            return strings.toString();
        }
    };

    /**
     * Returns the weight used for biasing toward the end of the list.
     * The weight increases as the index increases, favoring words toward the end.
     *
     * @param index the index of the word in the list
     * @return the weight based on the index
     */
    private static double getBiasTowardEndWeight(int index) {
        return (index + 1);
    }

    private final String name;

    /**
     * Constructor for initializing the ReplacementMethod with a name.
     *
     * @param name the name of the replacement method
     */
    ReplacementMethod(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the replacement method.
     *
     * @return the name of the replacement method
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Abstract method to get the replacement string from a list of strings based on
     * the replacement method.
     *
     * @param strings the list of strings from which the replacement word is chosen
     * @return the selected replacement string
     */
    public abstract String getReplacement(List<String> strings);

}
