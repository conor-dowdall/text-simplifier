package ie.atu.sw.wordreplacer;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public enum ReplacementMethod {
    MOST_SIMILAR("Most Similar Word") {
        @Override
        public String getReplacement(List<String> strings) {
            return strings.getLast(); // the last is the most similar after popping off the PriorityQueue
        }
    },
    LEAST_SIMILAR("Least Similar Word") {
        @Override
        public String getReplacement(List<String> strings) {
            return strings.getFirst(); // the last is the most similar after popping off the PriorityQueue
        }
    },
    RANDOM("Random Word from List") {
        @Override
        public String getReplacement(List<String> strings) {
            if (strings == null || strings.isEmpty()) {
                return "";
            }
            int randomIndex = ThreadLocalRandom.current().nextInt(strings.size());
            return strings.get(randomIndex);
        }
    },
    BIASED_MOST_SIMILAR("Biased Toward Most Similar Word from List") {
        @Override
        public String getReplacement(List<String> strings) {
            if (strings == null || strings.isEmpty()) {
                return "";
            }

            double totalWeight = 0.0;
            for (int i = 0; i < strings.size(); i++) {
                totalWeight += getBiasTowardEndWeight(i);
            }

            // Generate a random number in the range [0, totalWeight)
            double randomValue = ThreadLocalRandom.current().nextDouble(totalWeight);

            // Traverse the list and find the corresponding element
            double cumulativeWeight = 0.0;
            for (int i = 0; i < strings.size(); i++) {
                cumulativeWeight += getBiasTowardEndWeight(i);
                if (randomValue < cumulativeWeight) {
                    return strings.get(i);
                }
            }

            return "";
        }
    },
    ARRAY("Array/List of Words") {
        @Override
        public String getReplacement(List<String> strings) {
            return strings.toString();
        }
    };

    private static double getBiasTowardEndWeight(int index) {
        return (index + 1);
    }

    private final String name;

    ReplacementMethod(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public abstract String getReplacement(List<String> strings);
}
