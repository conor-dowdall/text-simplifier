package ie.atu.sw.util;

/**
 * Utility class providing static methods for various vector operations such as
 * addition, subtraction, dot product, Euclidean distance, and cosine
 * similarity.
 * This class is not meant to be instantiated.
 */
public class Vector {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Vector() {
        // Prevent instantiation
    }

    /**
     * Adds two vectors element-wise.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return a new vector representing the element-wise sum of the input vectors
     */
    public static double[] add(double[] vector1, double[] vector2) {
        double[] result = new double[vector1.length];

        for (int i = 0; i < vector1.length; i++)
            result[i] = vector1[i] + vector2[i];

        return result;
    }

    /**
     * Subtracts the second vector from the first vector element-wise.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return a new vector representing the element-wise difference of the input
     *         vectors
     */
    public static double[] subtract(double[] vector1, double[] vector2) {
        double[] result = new double[vector1.length];

        for (int i = 0; i < vector1.length; i++)
            result[i] = vector1[i] - vector2[i];

        return result;
    }

    /**
     * Multiplies two vectors element-wise.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return a new vector representing the element-wise product of the input
     *         vectors
     */
    public static double[] multiply(double[] vector1, double[] vector2) {
        double[] result = new double[vector1.length];

        for (int i = 0; i < vector1.length; i++)
            result[i] = vector1[i] * vector2[i];

        return result;
    }

    /**
     * Divides the first vector by the second vector element-wise.
     *
     * @param vector1 the numerator vector
     * @param vector2 the denominator vector
     * @return a new vector representing the element-wise division of the input
     *         vectors
     */
    public static double[] divide(double[] vector1, double[] vector2) {
        double[] result = new double[vector1.length];

        for (int i = 0; i < vector1.length; i++)
            result[i] = vector1[i] / vector2[i];

        return result;
    }

    /**
     * Squares each element of the input vector.
     *
     * @param vector the input vector
     * @return a new vector with each element squared
     */
    public static double[] square(double[] vector) {
        double[] result = new double[vector.length];

        for (int i = 0; i < vector.length; i++)
            result[i] = vector[i] * vector[i];

        return result;
    }

    /**
     * Computes the sum of all elements in a vector.
     *
     * @param vector the input vector
     * @return the sum of all elements in the vector
     */
    public static double sum(double[] vector) {
        double vectorSum = 0.0;
        for (double d : vector)
            vectorSum += d;

        return vectorSum;
    }

    /**
     * Computes the dot product of two vectors. Time Complexity: O(n).
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return the dot product of the two vectors
     */
    public static double dotProduct(double[] vector1, double[] vector2) {
        double dotProduct = 0.0;

        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
        }

        return dotProduct;
    }

    /**
     * Computes the Euclidean distance squared (without the square root) between two
     * vectors. Time Complexity: O(n).
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return the squared Euclidean distance between the two vectors
     */
    public static double euclideanDistanceNoSqrt(double[] vector1, double[] vector2) {
        double distance = 0.0;

        for (int i = 0; i < vector1.length; i++) {
            distance += Math.pow(vector1[i] - vector2[i], 2);
        }

        return distance;
    }

    /**
     * Computes the Euclidean distance between two vectors. Time Complexity: O(n).
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return the Euclidean distance between the two vectors
     */
    public static double euclideanDistance(double[] vector1, double[] vector2) {
        double distance = 0.0;

        for (int i = 0; i < vector1.length; i++) {
            distance += Math.pow(vector1[i] - vector2[i], 2);
        }

        return Math.sqrt(distance);
    }

    /**
     * Computes the cosine similarity between two vectors. Time Complexity: O(n).
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return the cosine similarity of the two vectors
     */
    public static double cosineSimilarity(double[] vector1, double[] vector2) {
        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;

        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
            magnitude1 += vector1[i] * vector1[i];
            magnitude2 += vector2[i] * vector2[i];
        }

        return dotProduct / (Math.sqrt(magnitude1) * Math.sqrt(magnitude2));
    }

}
