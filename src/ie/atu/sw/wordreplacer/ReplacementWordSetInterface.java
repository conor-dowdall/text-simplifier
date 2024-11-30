package ie.atu.sw.wordreplacer;

import java.util.Set;

public interface ReplacementWordSetInterface {

    Set<String> getReplacementWordSet();

    int getSize();

    boolean containsWord(String word);

}
