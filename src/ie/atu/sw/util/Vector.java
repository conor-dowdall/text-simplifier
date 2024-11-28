package ie.atu.sw.util;

/**
 * utility class with static methods for vector operations like add, subtract,
 * dot product
 */
public class Vector {

    /**
     * add two vectors
     * 
     * @param vector1
     * @param vector2
     * @return the result of the vector operation
     */
    public static double[] add(double[] vector1, double[] vector2) {
        double[] result = new double[vector1.length];

        for (int i = 0; i < vector1.length; i++)
            result[i] = vector1[i] + vector2[i];

        return result;
    }

    /**
     * subtract two vectors
     * 
     * @param vector1
     * @param vector2
     * @return the result of the vector operation
     */
    public static double[] subtract(double[] vector1, double[] vector2) {
        double[] result = new double[vector1.length];

        for (int i = 0; i < vector1.length; i++)
            result[i] = vector1[i] - vector2[i];

        return result;
    }

    /**
     * multiply two vectors
     * 
     * @param vector1
     * @param vector2
     * @return the result of the vector operation
     */
    public static double[] multiply(double[] vector1, double[] vector2) {
        double[] result = new double[vector1.length];

        for (int i = 0; i < vector1.length; i++)
            result[i] = vector1[i] * vector2[i];

        return result;
    }

    /**
     * divide a vector by another
     * 
     * @param vector1
     * @param vector2
     * @return the result of the vector operation
     */
    public static double[] divide(double[] vector1, double[] vector2) {
        double[] result = new double[vector1.length];

        for (int i = 0; i < vector1.length; i++)
            result[i] = vector1[i] / vector2[i];

        return result;
    }

    /**
     * multiply a vector by itself
     * 
     * @param vector
     * @return the result of the vector operation
     */
    public static double[] square(double[] vector) {
        double[] result = new double[vector.length];

        for (int i = 0; i < vector.length; i++)
            result[i] = vector[i] * vector[i];

        return result;
    }

    /**
     * sum all the values in a vector
     * 
     * @param vector
     * @return the value of the sum of the vector
     */
    public static double sum(double[] vector) {
        double vectorSum = 0.0;
        for (double d : vector)
            vectorSum += d;

        return vectorSum;
    }

    /**
     * calculate the dot product of two vectors
     * 
     * @param vector1
     * @param vector2
     * @return the dot product of two vectors
     */
    public static double dotProduct(double[] vector1, double[] vector2) {
        double dotProduct = 0.0;

        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
        }

        return dotProduct;
    }

    /**
     * calculate the euclidean distance between two vectors, without applying the
     * final square-root operation
     * 
     * @param vector1
     * @param vector2
     * @return the euclidean distance between two vectors (no square root)
     */
    public static double euclideanDistanceNoSqrt(double[] vector1, double[] vector2) {
        double distance = 0.0;

        for (int i = 0; i < vector1.length; i++) {
            distance += Math.pow(vector1[i] - vector2[i], 2);
        }

        return distance;
    }

    /**
     * calculate the euclidean distance between two vectors
     * 
     * @param vector1
     * @param vector2
     * @return the euclidean distance between two vectors
     */
    public static double euclideanDistance(double[] vector1, double[] vector2) {
        double distance = 0.0;

        for (int i = 0; i < vector1.length; i++) {
            distance += Math.pow(vector1[i] - vector2[i], 2);
        }

        return Math.sqrt(distance);
    }

    /**
     * calculate the cosine similarity of two vectors
     * 
     * @param vector1
     * @param vector2
     * @return the cosine similarity of two vectors
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
