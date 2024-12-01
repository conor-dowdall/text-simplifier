package ie.atu.sw.wordembedding;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import ie.atu.sw.util.FileParser;
import ie.atu.sw.util.WordEmbeddingMapInterface;

public record WordEmbeddingMap(Map<String, WordEmbedding> wordEmbeddingMap) implements WordEmbeddingMapInterface {

    public static WordEmbeddingMap getMapFromFile(String fileName, String delimiter)
            throws IOException, NumberFormatException {
        Map<String, WordEmbedding> map = FileParser.parseMapFile(fileName, delimiter);
        return new WordEmbeddingMap(map);
    }

    @Override
    public Map<String, WordEmbedding> getWordEmbeddingMap() {
        return wordEmbeddingMap;
    }

    @Override
    public int getSize() {
        return wordEmbeddingMap.size();
    }

    @Override
    public Set<String> getWords() {
        return wordEmbeddingMap.keySet();
    }

    @Override
    public Collection<WordEmbedding> getEmbeddings() {
        return wordEmbeddingMap.values();
    }

    @Override
    public WordEmbedding getWordEmbedding(String word) {
        return wordEmbeddingMap.get(word);
    }
}
