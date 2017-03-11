package com.dev;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class MarkovChain {
    private Map<String, List<String>> dictionary = new HashMap<>();
    private final String tokenSeparator = " ";
    private static final long DEFAULT_MAX_TOKENS = 30;

    public MarkovChain(String base) {
        if (base == null) {
            throw new IllegalArgumentException("Base cannot be null");
        }
        buildDictionary(base);
    }

    private void buildDictionary(String base) {
        String[] words = Arrays.stream(base.split("[,;:.!?\\s]+")).map(String::toLowerCase).toArray(String[]::new);

        for (int i = 0; i < words.length - 1; i++) {
            String prefix = words[i];
            String suffix = words[i + 1];
            List<String> suffixes = dictionary.get(prefix);
            if (suffixes == null) {
                suffixes = new ArrayList<>();
                suffixes.add(suffix);
                dictionary.put(prefix, suffixes);
            } else {
                if (!suffixes.contains(suffix)) {
                    suffixes.add(suffix);
                    dictionary.put(prefix, suffixes);
                }
            }
        }

        buildTerminateStates(words);
    }

    private void buildTerminateStates(String[] words) {
        //Add start transition
        dictionary.put(null, Collections.singletonList(words[0]));

        //Add finish transition
        String lastPrefix = words[words.length - 1];
        List<String> lastSuffixes = dictionary.get(lastPrefix);
        if (lastSuffixes != null) {
            lastSuffixes.add(null);
        } else {
            lastSuffixes = Collections.singletonList(null);
        }
        dictionary.put(lastPrefix, lastSuffixes);
    }

    public String generateText() {
        return generateText(DEFAULT_MAX_TOKENS);
    }

    public String generateText(long maxTokens) {
        StringBuilder sb = new StringBuilder();
        String currentState = null;
        long count = 0;
        do {
            currentState = getNextState(currentState);
            if (currentState == null) {
                break;
            }
            sb.append(currentState).append(tokenSeparator);
            count++;
        } while (count < maxTokens);
        return StringUtils.capitalize(sb.toString());
    }

    private String getNextState(String currentState) {
        String nextState = null;
        List<String> nextStates = dictionary.get(currentState);
        if (nextStates != null) {
            int size = nextStates.size();
            int randIndex = (int) (Math.floor(Math.random() * size));
            nextState = nextStates.get(randIndex);
        }
        return nextState;
    }
}
