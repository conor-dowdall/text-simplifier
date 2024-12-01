package ie.atu.sw.wordreplacer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

import ie.atu.sw.util.ReplacementWordSetInterface;
import ie.atu.sw.util.SimilarityAlgorithm;
import ie.atu.sw.util.WordEmbeddingMapInterface;
import ie.atu.sw.wordembedding.WordEmbedding;
import ie.atu.sw.wordembedding.WordEmbeddingSimilarity;

public abstract class WordReplacerAbstract {

    private WordEmbeddingMapInterface wordEmbeddingMap;
    private ReplacementWordSetInterface replacementWordSet;

    private int similarReplacementWords = 1;
    private SimilarityAlgorithm similarityAlgorithm = SimilarityAlgorithm.COSINE_SIMILARITY;
    private ReplacementMethod replacementMethod = ReplacementMethod.MOST_SIMILAR;

    private PriorityQueue<WordEmbeddingSimilarity> getTopNSimilar(int n, boolean isHigherMoreSimilar) {
        if (isHigherMoreSimilar) {
            return new PriorityQueue<WordEmbeddingSimilarity>(n, Comparator.comparingDouble(o -> o.similarity()));
        } else {
            return new PriorityQueue<WordEmbeddingSimilarity>(n, Comparator.comparingDouble(o -> -o.similarity()));
        }
    }

    private void addSimilarReplacementWords(WordEmbedding wordEmbedding) {
        PriorityQueue<WordEmbeddingSimilarity> topNWords = getTopNSimilar(
                similarReplacementWords,
                similarityAlgorithm.isHigherMoreSimilar());

        for (String replacementWord : replacementWordSet.getReplacementWordSet()) {
            WordEmbedding replacementWordEmbedding = wordEmbeddingMap.getWordEmbedding(replacementWord);

            if (replacementWordEmbedding != null) {
                topNWords.offer(
                        new WordEmbeddingSimilarity(
                                replacementWord,
                                similarityAlgorithm.calculate(
                                        replacementWordEmbedding.embedding(),
                                        wordEmbedding.embedding())));

                if (topNWords.size() > similarReplacementWords) {
                    topNWords.poll();
                }
            }
        }

        while (!topNWords.isEmpty()) {
            wordEmbedding.addSimilarWord(topNWords.poll().word());
        }
    }

    private void emptyWordEmbeddingMapSimilarWordList() {
        if (wordEmbeddingMap != null) {
            for (WordEmbedding wordEmbedding : wordEmbeddingMap.getEmbeddings()) {
                wordEmbedding.emptySimilarWordList();
            }
        }
    }

    protected String replaceStringIfNotInReplacementSet(String string) {
        if (!replacementWordSet.containsWord(string)) {
            WordEmbedding stringEmbedding = wordEmbeddingMap.getWordEmbedding(string);

            if (stringEmbedding != null) {
                if (stringEmbedding.similarWords().isEmpty()) {
                    addSimilarReplacementWords(stringEmbedding);
                }
                return replacementMethod.getReplacement(stringEmbedding.similarWords());
            }

        }

        return string;
    }

    public void writeReplacedFile(String inputTextFileName, String outputTextFileName) throws IOException {
        if (wordEmbeddingMap == null) {
            throw new IllegalStateException("The Word-Embedding Map has not been initialized.");
        }
        if (replacementWordSet == null) {
            throw new IllegalStateException("The Replacement-Word Set has not been initialized.");
        }
        try (
                BufferedReader inputReader = new BufferedReader(new FileReader(inputTextFileName));
                BufferedWriter outputWriter = new BufferedWriter(new FileWriter(outputTextFileName));) {

            String inputTextLine;
            while ((inputTextLine = inputReader.readLine()) != null) {
                outputWriter.write(replaceString(inputTextLine));
                outputWriter.newLine();
            }
        }
    }

    public boolean isWordEmbeddingMapNull() {
        if (wordEmbeddingMap == null)
            return true;
        return false;
    }

    public boolean isReplacementWordSetNull() {
        if (replacementWordSet == null)
            return true;
        return false;
    }

    public void setWordEmbeddingsMap(WordEmbeddingMapInterface wordEmbeddingMap) throws IllegalArgumentException {
        this.wordEmbeddingMap = wordEmbeddingMap;
    }

    public void setReplacementWordsSet(ReplacementWordSetInterface replacementWordSet) {
        this.replacementWordSet = replacementWordSet;
    }

    public void setSimilarReplacementWords(int similarReplacementWords) {
        this.similarReplacementWords = similarReplacementWords;
        emptyWordEmbeddingMapSimilarWordList();
    }

    public void setSimilarityAlgorithm(SimilarityAlgorithm similarityAlgorithm) {
        this.similarityAlgorithm = similarityAlgorithm;
        emptyWordEmbeddingMapSimilarWordList();
    }

    public void setReplacementMethod(ReplacementMethod replacementMethod) {
        this.replacementMethod = replacementMethod;
        emptyWordEmbeddingMapSimilarWordList();
    }

    public abstract String replaceString(String string);

}
