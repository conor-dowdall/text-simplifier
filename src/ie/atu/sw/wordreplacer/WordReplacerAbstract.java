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

/**
 * An abstract class for replacing words in a text string based on word
 * embeddings and a replacement word set. It supports various similarity
 * algorithms and replacement methods.
 */
public abstract class WordReplacerAbstract {

    private WordEmbeddingMapInterface wordEmbeddingMap;
    private ReplacementWordSetInterface replacementWordSet;

    private int similarReplacementWords = 1;
    private SimilarityAlgorithm similarityAlgorithm = SimilarityAlgorithm.COSINE_SIMILARITY;
    private ReplacementMethod replacementMethod = ReplacementMethod.MOST_SIMILAR;

    /**
     * Returns a {@link PriorityQueue} of top N similar words based on a similarity
     * algorithm.
     * The queue orders the words according to their similarity score.
     * 
     * @param n                   the number of similar words to retrieve
     * @param isHigherMoreSimilar indicates if higher similarity scores should be
     *                            considered more similar
     * @return a {@link PriorityQueue} of top N similar words
     */
    private PriorityQueue<WordEmbeddingSimilarity> getTopNSimilar(int n, boolean isHigherMoreSimilar) {
        if (isHigherMoreSimilar) {
            return new PriorityQueue<WordEmbeddingSimilarity>(n, Comparator.comparingDouble(o -> o.similarity()));
        } else {
            return new PriorityQueue<WordEmbeddingSimilarity>(n, Comparator.comparingDouble(o -> -o.similarity()));
        }
    }

    /**
     * Adds similar replacement words to the provided word embedding based on the
     * similarity algorithm.
     * The method calculates the similarity score between the word embedding and the
     * words in the replacement word set.
     * <p>
     * Total Time Complexity: O(R⋅(d+logk)+klogk)
     * </p>
     * <p>
     * Explanation:
     * 
     * O(R⋅d): Iterating through R replacement words and calculating
     * similarity for each embedding.
     * O(R⋅log⁡k): Inserting into and maintaining the priority queue.
     * O(klog⁡k): Polling the final k elements from the priority queue.
     * 
     * In practice, k is typically small (e.g., top 5 or 10), so the dominant term
     * is: O(R⋅d)
     * </p>
     * 
     * @param wordEmbedding the word embedding to find similar words for
     */
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

    /**
     * Clears the list of similar words in all word embeddings within the word
     * embedding map. The method iterates over all embeddings in wordEmbeddingMap,
     * which takes O(n). The emptySimilarWordList operation is assumed to be O(1) if
     * it simply clears the list or resets it.
     */
    private void emptyWordEmbeddingMapSimilarWordList() {
        if (wordEmbeddingMap != null) {
            for (WordEmbedding wordEmbedding : wordEmbeddingMap.getEmbeddings()) {
                wordEmbedding.emptySimilarWordList();
            }
        }
    }

    /**
     * Replaces a word in the string if it is not present in the replacement set.
     * The replacement is based on the similarity score of the word embeddings using
     * the configured similarity algorithm and replacement method.
     * 
     * R: Number of replacement words in replacementWordSet.
     * d: Dimensionality of the embeddings.
     * k: The size limit of similar words .
     * Worst Case: O(R⋅d+k). Thus, the overall time complexity is dominated by
     * O(R⋅d) in the worst case.
     * 
     * @param string the word to be replaced
     * @return the replaced word, or the original word if no replacement is found
     */

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

    /**
     * Reads the input text file and writes the modified version (with word
     * replacements) to an output file.
     * 
     * Key Variables:
     * 
     * L: Number of lines in the input file.
     * C: Average number of characters per line.
     * n: Average number of words per line.
     * m: Average length of a word.
     * R: Number of replacement words in replacementWordSet.
     * d: Dimensionality of the embeddings.
     * k: Size limit of similar words (similarWords()).
     * 
     * Reading the Input File:
     * Using BufferedReader.readLine(), the entire file is read line by line. This
     * is O(T), where T=L⋅CT is the total number of characters in the file.
     * 
     * Processing Each Line with replaceString:
     * The replaceString method operates on n words in a line with complexity
     * O(n⋅m+R⋅d) in the worst case.
     * Over L lines, this becomes O(L⋅n⋅(m+R⋅d)).
     * 
     * Writing the Output File:
     * Writing each line with BufferedWriter.write() is proportional to the size of
     * the replaced string, so it adds O(T).
     * 
     * The dominant term is the processing of each line via replaceString. Hence,
     * the overall time complexity is: O(L⋅n⋅(m+R⋅d)).
     * 
     * L⋅n: Total number of words in the file.
     * m+R⋅d: Time complexity for replacing a single word, including similarity
     * calculations if triggered.
     * 
     * @param inputTextFileName  the input text file to read
     * @param outputTextFileName the output text file to write the replaced content
     * @throws IOException if an I/O error occurs while reading or writing files
     */
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

    /**
     * Checks if the word embedding map is null.
     * 
     * @return true if the word embedding map is null, false otherwise
     */
    public boolean isWordEmbeddingMapNull() {
        if (wordEmbeddingMap == null)
            return true;
        return false;
    }

    /**
     * Checks if the replacement word set is null.
     * 
     * @return {@code true} if the replacement word set is null, {@code false}
     *         otherwise
     */
    public boolean isReplacementWordSetNull() {
        if (replacementWordSet == null)
            return true;
        return false;
    }

    /**
     * Sets the word embeddings map to be used by the word replacer.
     * 
     * @param wordEmbeddingMap the word embedding map
     * @throws IllegalArgumentException if the word embedding map is null
     */
    public void setWordEmbeddingsMap(WordEmbeddingMapInterface wordEmbeddingMap) throws IllegalArgumentException {
        this.wordEmbeddingMap = wordEmbeddingMap;
    }

    /**
     * Sets the replacement word set to be used by the word replacer.
     * 
     * @param replacementWordSet the replacement word set
     */
    public void setReplacementWordsSet(ReplacementWordSetInterface replacementWordSet) {
        this.replacementWordSet = replacementWordSet;
    }

    /**
     * Sets the number of similar replacement words to consider when replacing a
     * word.
     * 
     * @param similarReplacementWords the number of similar replacement words
     */
    public void setSimilarReplacementWords(int similarReplacementWords) {
        this.similarReplacementWords = similarReplacementWords;
        emptyWordEmbeddingMapSimilarWordList();
    }

    /**
     * Sets the similarity algorithm to be used for calculating word similarity.
     * 
     * @param similarityAlgorithm the similarity algorithm
     */
    public void setSimilarityAlgorithm(SimilarityAlgorithm similarityAlgorithm) {
        this.similarityAlgorithm = similarityAlgorithm;
        emptyWordEmbeddingMapSimilarWordList();
    }

    /**
     * Sets the replacement method to be used for selecting a replacement word.
     * 
     * @param replacementMethod the replacement method
     */
    public void setReplacementMethod(ReplacementMethod replacementMethod) {
        this.replacementMethod = replacementMethod;
        emptyWordEmbeddingMapSimilarWordList();
    }

    /**
     * An abstract method to be implemented by subclasses to replace a word in a
     * string.
     * 
     * @param string the input string
     * @return the string with the word replaced
     */
    public abstract String replaceString(String string);

}
