package ie.atu.sw.wordreplacer;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import ie.atu.sw.wordembedding.WordEmbedding;

public interface WordEmbeddingMapInterface {

    Map<String, WordEmbedding> getWordEmbeddingMap();

    int getSize();

    Set<String> getWords();

    Collection<WordEmbedding> getEmbeddings();

    WordEmbedding getWordEmbedding(String word);

}
