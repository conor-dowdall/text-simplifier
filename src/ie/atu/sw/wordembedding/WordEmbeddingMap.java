package ie.atu.sw.wordembedding;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public record WordEmbeddingMap(Map<String, WordEmbedding> wordEmbeddingMap) {

    public static HashMap<String, WordEmbedding> getMap(String fileName, String delimiter)
            throws IOException {

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

                wordEmbeddingMap
                        .put(
                                word,
                                new WordEmbedding(
                                        word,
                                        embedding, new ArrayList<String>()));
            }

            return wordEmbeddingMap;

        }

    }

    public int getSize() {
        return wordEmbeddingMap.size();
    }

    public Set<String> getWords() {
        return wordEmbeddingMap.keySet();
    }

    public Collection<WordEmbedding> getEmbeddings() {
        return wordEmbeddingMap.values();
    }

    public WordEmbedding getWordEmbedding(String word) {
        return wordEmbeddingMap.get(word);
    }
}
