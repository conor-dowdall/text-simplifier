package ie.atu.sw.wordreplacer;

import java.io.IOException;
import java.util.Set;

import ie.atu.sw.util.FileParser;
import ie.atu.sw.util.ReplacementWordSetInterface;

/**
 * A record representing a set of replacement words, implementing the
 * ReplacementWordSetInterface.
 */
public record ReplacementWordSet(Set<String> replacementWordSet) implements ReplacementWordSetInterface {

    /**
     * Creates a {@link ReplacementWordSet} from a file by parsing the file into a
     * set of words using a specified delimiter.
     * 
     * @param fileName  the name of the file containing the replacement words
     * @param delimiter the delimiter used to separate the words in the file
     * @return a new ReplacementWordSet containing the words from the file
     * @throws IOException if an error occurs while reading the file
     */
    public static ReplacementWordSet getSetFromFile(String fileName, String delimiter) throws IOException {
        Set<String> set = FileParser.parseSetFile(fileName, delimiter);
        return new ReplacementWordSet(set);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getReplacementWordSet() {
        return replacementWordSet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return replacementWordSet.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsWord(String word) {
        return replacementWordSet.contains(word);
    }
}
