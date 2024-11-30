package ie.atu.sw.wordreplacer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ie.atu.sw.util.SimilarityAlgorithm;
import ie.atu.sw.wordembedding.WordEmbedding;
import ie.atu.sw.wordembedding.WordEmbeddingMap;
import ie.atu.sw.wordembedding.WordEmbeddingSimilarity;

public class WordReplacer {

    public static PriorityQueue<WordEmbeddingSimilarity> getTopNSimilar(int n, boolean isHigherMoreSimilar) {
        if (isHigherMoreSimilar) {
            return new PriorityQueue<WordEmbeddingSimilarity>(n, Comparator.comparingDouble(o -> o.similarity()));
        } else {
            return new PriorityQueue<WordEmbeddingSimilarity>(n, Comparator.comparingDouble(o -> -o.similarity()));
        }
    }

    public static Object[] extractWordObject(String string) {
        // Group 1 (\\w+): Captures one or more word characters.
        // Group 2 (\\p{Punct}*): Captures zero or more punctuation characters.
        Pattern pattern = Pattern.compile("^(\\w+)(\\p{Punct}*)$");
        Matcher matcher = pattern.matcher(string);

        if (matcher.matches()) {
            String word = matcher.group(1);
            String punctuation = matcher.group(2);
            boolean isCapitalized = Character.isUpperCase(word.charAt(0));
            return new Object[] { word, punctuation, isCapitalized };
        } else {
            // If no match, return the word, empty punctuation, and isCapitalized
            boolean isCapitalized = Character.isUpperCase(string.charAt(0));
            return new Object[] { string, "", isCapitalized };
        }
    }

    private WordEmbeddingMapInterface wordEmbeddingMap;
    private ReplacementWordSetInterface replacementWordSet;

    private int similarReplacementWords = 1;
    private SimilarityAlgorithm similarityAlgorithm = SimilarityAlgorithm.COSINE_SIMILARITY;
    private ReplacementMethod replacementMethod = ReplacementMethod.MOST_SIMILAR;

    public boolean isWordEmbeddingMapNull() {
        if (wordEmbeddingMap == null)
            return true;
        return false;
    }

    private void emptyWordEmbeddingMapSimilarWordList() {
        if (wordEmbeddingMap != null) {
            for (WordEmbedding wordEmbedding : wordEmbeddingMap.getEmbeddings()) {
                wordEmbedding.emptySimilarWordList();
            }
        }
    }

    public boolean isReplacementWordSetNull() {
        if (replacementWordSet == null)
            return true;
        return false;
    }

    public void setWordEmbeddingsMap(WordEmbeddingMap wordEmbeddingMap) {
        this.wordEmbeddingMap = wordEmbeddingMap;
    }

    public void setReplacementWordsSet(ReplacementWordSet replacementWordSet) {
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

    public String replaceString(String string) {

        StringBuilder replacedString = new StringBuilder();
        String[] stringParts = string.split(" ");

        for (int i = 0; i < stringParts.length; i++) {
            if (!stringParts[i].isEmpty()) {
                Object[] wordObject = WordReplacer.extractWordObject(stringParts[i]);
                String word = (String) wordObject[0];
                String punctuation = (String) wordObject[1];
                boolean isCapitalized = (Boolean) wordObject[2];

                String replacedStringPart = replaceStringIfNotInReplacementSet(word.toLowerCase());

                // this doesn't work when replacedStringPart is a List.toString(),
                // which is achieved by using ReplacementMethod.ARRAY
                if (isCapitalized) {
                    StringBuilder sb = new StringBuilder(replacedStringPart);
                    sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
                    replacedStringPart = sb.toString();
                }

                replacedString
                        .append(replacedStringPart)
                        .append(punctuation);

            }

            // Add a space if this is not the last word
            if (i < stringParts.length - 1) {
                replacedString.append(" ");
            }
        }

        return replacedString.toString();

    }

    public String replaceStringIfNotInReplacementSet(String string) {
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

}