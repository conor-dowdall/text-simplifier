package ie.atu.sw.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import ie.atu.sw.wordembedding.WordEmbedding;

/**
 * Utility class for parsing files into collections and mappings.
 * Provides methods for parsing files into a {@link HashSet} of strings or a
 * {@link HashMap} mapping strings to {@link WordEmbedding} objects.
 * <p>
 * This class cannot be instantiated.
 * </p>
 */
public class FileParser {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private FileParser() {
        // Prevent instantiation
    }

    /**
     * Parses a file into a {@link HashSet} of strings.
     * Each line in the file is split by the specified delimiter, and all resulting
     * parts are added to the set.
     *
     * @param fileName  the name of the file to parse
     * @param delimiter the delimiter used to split each line
     * @return a {@link HashSet} containing all unique parts from the file
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static HashSet<String> parseSetFile(String fileName, String delimiter)
            throws IOException {

        HashSet<String> replacementWordSet = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(delimiter);
                for (int i = 0; i < parts.length; i++) {
                    replacementWordSet.add(parts[i]);
                }
            }
        }

        return replacementWordSet;

    }

    /**
     * Parses a file into a {@link HashMap} mapping strings to {@link WordEmbedding}
     * objects.
     * The first part of each line is treated as the key (a word), and the remaining
     * parts are parsed as numerical values to create a {@link WordEmbedding}.
     *
     * @param fileName  the name of the file to parse
     * @param delimiter the delimiter used to split each line
     * @return a {@link HashMap} mapping words to their corresponding
     *         {@link WordEmbedding} objects
     * @throws IOException           if an I/O error occurs while reading the file
     * @throws NumberFormatException if a non-numeric value is encountered in the
     *                               embedding data
     */
    public static HashMap<String, WordEmbedding> parseMapFile(String fileName, String delimiter)
            throws IOException, NumberFormatException {

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            HashMap<String, WordEmbedding> wordEmbeddingMap = new HashMap<>();

            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(delimiter);
                String word = parts[0];
                double[] embedding = new double[parts.length - 1];

                for (int i = 1; i < parts.length; i++) {
                    embedding[i - 1] = Double.parseDouble(parts[i]);
                }

                wordEmbeddingMap.put(
                        word,
                        new WordEmbedding(
                                word,
                                embedding, new ArrayList<String>()));
            }

            return wordEmbeddingMap;

        }

    }

}
