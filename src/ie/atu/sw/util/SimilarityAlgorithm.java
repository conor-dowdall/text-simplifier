package ie.atu.sw.util;

/**
 * Enum defining various similarity algorithms for comparing word embeddings.
 * Each algorithm implements a method for calculating similarity or distance
 * between two vectors.
 */
public enum SimilarityAlgorithm {

    /**
     * Dot Product similarity algorithm.
     * Higher values indicate greater similarity.
     */
    DOT_PRODUCT("Dot Product", true) {
        @Override
        public double calculate(double[] vector1, double[] vector2) {
            return Vector.dotProduct(vector1, vector2);
        }
    },

    /**
     * Euclidean Distance algorithm without taking the square root.
     * Lower values indicate greater similarity.
     */
    EUCLIDEAN_DISTANCE_NO_SQRT("Euclidean Distance (No Square Root)", false) {
        @Override
        public double calculate(double[] vector1, double[] vector2) {
            return Vector.euclideanDistanceNoSqrt(vector1, vector2);
        }
    },

    /**
     * Euclidean Distance algorithm.
     * Lower values indicate greater similarity.
     */
    EUCLIDEAN_DISTANCE("Euclidean Distance", false) {
        @Override
        public double calculate(double[] vector1, double[] vector2) {
            return Vector.euclideanDistance(vector1, vector2);

        }
    },

    /**
     * Cosine Similarity algorithm.
     * Higher values indicate greater similarity.
     */
    COSINE_SIMILARITY("Cosine Similarity", true) {
        @Override
        public double calculate(double[] vector1, double[] vector2) {
            return Vector.cosineSimilarity(vector1, vector2);
        }
    };

    private final String name;
    private final boolean isHigherMoreSimilar;

    /**
     * Constructor for initializing a similarity algorithm with its properties.
     *
     * @param name                the name of the similarity algorithm
     * @param isHigherMoreSimilar whether higher values indicate greater similarity
     */
    SimilarityAlgorithm(String name, boolean isHigherMoreSimilar) {
        this.name = name;
        this.isHigherMoreSimilar = isHigherMoreSimilar;
    }

    /**
     * Retrieves the name of the similarity algorithm.
     *
     * @return the name of the algorithm
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Checks whether higher values indicate greater similarity for this algorithm.
     *
     * @return {@code true} if higher values indicate greater similarity,
     *         {@code false} otherwise
     */
    public boolean isHigherMoreSimilar() {
        return isHigherMoreSimilar;
    }

    /**
     * Calculates the similarity or distance between two vectors using the
     * algorithm.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return the calculated similarity or distance
     */
    public abstract double calculate(double[] vector1, double[] vector2);

}
