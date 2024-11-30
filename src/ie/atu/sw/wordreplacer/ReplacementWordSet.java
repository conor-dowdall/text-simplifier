package ie.atu.sw.wordreplacer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public record ReplacementWordSet(Set<String> replacementWordSet) {

    public static HashSet<String> getSet(String fileName, String delimiter)
            throws IOException {

        HashSet<String> replacementWordSet = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(delimiter);
                for (int i = 0; i < parts.length; i++) {
                    replacementWordSet.add(parts[i]);
                }
            }
        }

        return replacementWordSet;

    }

    public int getSize() {
        return replacementWordSet.size();
    }

    public boolean containsWord(String word) {
        return replacementWordSet.contains(word);
    }
}
