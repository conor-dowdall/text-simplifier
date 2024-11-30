package ie.atu.sw.wordreplacer;

import java.io.IOException;
import java.util.Set;

import ie.atu.sw.util.FileParser;
import ie.atu.sw.util.ReplacementWordSetInterface;

public record ReplacementWordSet(Set<String> replacementWordSet) implements ReplacementWordSetInterface {

    public static ReplacementWordSet getSetFromFile(String fileName, String delimiter) throws IOException {
        Set<String> set = FileParser.parseSetFile(fileName, delimiter);
        return new ReplacementWordSet(set);
    }

    @Override
    public Set<String> getReplacementWordSet() {
        return replacementWordSet;
    }

    @Override
    public int getSize() {
        return replacementWordSet.size();
    }

    @Override
    public boolean containsWord(String word) {
        return replacementWordSet.contains(word);
    }
}
