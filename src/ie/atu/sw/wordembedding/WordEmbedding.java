package ie.atu.sw.wordembedding;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record WordEmbedding(String word, double[] embedding, List<String> similarWords) {

    public static Map<String, WordEmbedding> getWordEmbeddingMap(String fileName)
            throws IOException {
        // String delimiter = " ";
        String delimiter = ", ";

        Map<String, WordEmbedding> wordEmbeddingMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(delimiter);
                String word = parts[0];
                double[] embedding = new double[parts.length - 1];

                for (int i = 1; i < parts.length; i++) {
                    embedding[i - 1] = Double.parseDouble(parts[i]);
                }

                wordEmbeddingMap.put(word, new WordEmbedding(word, embedding, new ArrayList<String>()));
            }
        }

        return wordEmbeddingMap;
    }

    public void addSimilarWord(String word) {
        this.similarWords.add(word);
    }

    public void emptySimilarWordList() {
        this.similarWords.clear();
    }

}
