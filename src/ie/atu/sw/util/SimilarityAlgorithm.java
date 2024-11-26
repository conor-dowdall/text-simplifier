package ie.atu.sw.util;

/**
 * enum defining different similarity algorithms, which each implement a
 * calculate method
 */
public enum SimilarityAlgorithm {
    DOT_PRODUCT("Dot Product", true) {
        @Override
        public double calculate(double[] vector1, double[] vector2) {
            return Vector.dotProduct(vector1, vector2);
        }
    },
    EUCLIDEAN_DISTANCE_NO_SQRT("Euclidean Distance (No Square Root)", false) {
        @Override
        public double calculate(double[] vector1, double[] vector2) {
            return Vector.euclideanDistanceNoSqrt(vector1, vector2);
        }
    },
    EUCLIDEAN_DISTANCE("Euclidean Distance", false) {
        @Override
        public double calculate(double[] vector1, double[] vector2) {
            return Vector.euclideanDistance(vector1, vector2);

        }
    },
    COSINE_SIMILARITY("Cosine Similarity", true) {
        @Override
        public double calculate(double[] vector1, double[] vector2) {
            return Vector.cosineSimilarity(vector1, vector2);
        }
    };

    private final String name;
    private final boolean isHigherMoreSimilar;

    SimilarityAlgorithm(String name, boolean isHigherMoreSimilar) {
        this.name = name;
        this.isHigherMoreSimilar = isHigherMoreSimilar;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public boolean isHigherMoreSimilar() {
        return this.isHigherMoreSimilar;
    }

    public abstract double calculate(double[] vector1, double[] vector2);
}
