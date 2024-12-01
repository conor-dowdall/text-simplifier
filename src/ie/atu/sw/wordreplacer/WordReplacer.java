package ie.atu.sw.wordreplacer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class responsible for replacing words in a string based on a replacement
 * set. It extends the abstract class {@link WordReplacerAbstract} and
 * implements the logic for replacing words while preserving capitalization and
 * punctuation.
 */
public class WordReplacer extends WordReplacerAbstract {

    /**
     * Constructs a new {@link WordReplacer}.
     * This constructor is used to initialize the word replacer object.
     */
    public WordReplacer() {
        // Default constructor
    }

    /**
     * Extracts the word and its associated punctuation from a string.
     * The word is matched using the pattern (\\w+) and punctuation is captured
     * using the pattern (\\p{Punct}*). Additionally, the method checks if the word
     * is capitalized.
     * 
     * @param string the input string (a word, possibly with punctuation)
     * @return an Object array containing the word, punctuation, and a boolean
     *         indicating if the word is capitalized
     */
    private Object[] extractWordObject(String string) {
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

    /**
     * Replaces words in a given string with their corresponding replacements,
     * while preserving capitalization and punctuation.
     * 
     * @param string the input string to be processed
     * @return the string with words replaced, maintaining capitalization and
     *         punctuation
     */
    @Override
    public String replaceString(String string) {

        StringBuilder replacedString = new StringBuilder();
        String[] stringParts = string.split(" ");

        for (int i = 0; i < stringParts.length; i++) {
            if (!stringParts[i].isEmpty()) {
                Object[] wordObject = extractWordObject(stringParts[i]);
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

}