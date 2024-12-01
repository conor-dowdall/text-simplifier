package ie.atu.sw.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import ie.atu.sw.wordembedding.WordEmbedding;

public class FileParser {

    private FileParser() {
        // Prevent instantiation
    }

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
