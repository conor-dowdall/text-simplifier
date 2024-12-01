package ie.atu.sw.wordembedding;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import ie.atu.sw.util.FileParser;
import ie.atu.sw.util.WordEmbeddingMapInterface;

/**
 * Record representing a map of words to their embeddings and implementing
 * {@link WordEmbeddingMapInterface}.
 * Provides methods to interact with the word embeddings, such as loading from a
 * file and retrieving embeddings.
 *
 * @param wordEmbeddingMap the map of words to their corresponding embeddings
 */
public record WordEmbeddingMap(Map<String, WordEmbedding> wordEmbeddingMap)
        implements WordEmbeddingMapInterface {

    /**
     * Loads a word embedding map from a file with a specified delimiter.
     *
     * @param fileName  the name of the file containing word embeddings
     * @param delimiter the delimiter used in the file to separate values
     * @return a {@link WordEmbeddingMap} created from the file
     * @throws IOException           if there is an error reading the file
     * @throws NumberFormatException if a number in the file cannot be parsed
     */
    public static WordEmbeddingMap getMapFromFile(String fileName, String delimiter)
            throws IOException, NumberFormatException {
        Map<String, WordEmbedding> map = FileParser.parseMapFile(fileName, delimiter);
        return new WordEmbeddingMap(map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, WordEmbedding> getWordEmbeddingMap() {
        return wordEmbeddingMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return wordEmbeddingMap.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getWords() {
        return wordEmbeddingMap.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<WordEmbedding> getEmbeddings() {
        return wordEmbeddingMap.values();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WordEmbedding getWordEmbedding(String word) {
        return wordEmbeddingMap.get(word);
    }
}
