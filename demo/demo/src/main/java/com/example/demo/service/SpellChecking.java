package com.example.demo.service;

import com.example.demo.model.CreditCard;
import java.util.*;

/**
 * SpellChecking utility to provide:
 * - Word frequency counts from card data
 * - Spelling suggestions based on Levenshtein distance
 */
public class SpellChecking {

    // Map to store word frequencies
    private final Map<String, Integer> wordFrequency = new HashMap<>();

    /**
     * Initialize the spell checker by parsing credit card data.
     * Builds a frequency map of all words appearing in titles, value props, and benefits.
     *
     * @param cards List of credit cards to index words from
     */
    public SpellChecking(List<CreditCard> cards) {
        for (CreditCard card : cards) {
            String combined = (card.getCardTitle() + " " +
                               card.getProductValueProp() + " " +
                               card.getProductBenefits()).toLowerCase();

            // Split on any non-word character
            String[] tokens = combined.split("\\W+");
            for (String token : tokens) {
                if (!token.isEmpty()) {
                    wordFrequency.put(token, wordFrequency.getOrDefault(token, 0) + 1);
                }
            }
        }
    }

    /**
     * Get spelling suggestions for a given word.
     * Suggestions are limited by max Levenshtein distance and max number of suggestions.
     * Suggestions are sorted by descending word frequency.
     *
     * @param word          The word to check spelling for
     * @param maxDistance   Maximum Levenshtein distance allowed for suggestions
     * @param maxSuggestions Maximum number of suggestions to return
     * @return List of suggested words
     */
    public List<String> getSuggestions(String word, int maxDistance, int maxSuggestions) {
        word = word.toLowerCase();
        List<String> suggestions = new ArrayList<>();

        for (String candidate : wordFrequency.keySet()) {
            if (calculateLevenshteinDistance(word, candidate) <= maxDistance) {
                suggestions.add(candidate);
            }
        }

        // Sort suggestions by frequency descending
        suggestions.sort((a, b) -> wordFrequency.getOrDefault(b, 0) - wordFrequency.getOrDefault(a, 0));

        // Return limited list of suggestions
        return suggestions.stream().limit(maxSuggestions).toList();
    }

    /**
     * Get the frequency count of a word in the indexed credit card data.
     *
     * @param word Word to look up
     * @return Frequency count (0 if not found)
     */
    public int getWordFrequency(String word) {
        return wordFrequency.getOrDefault(word.toLowerCase(), 0);
    }

    /**
     * Calculates the Levenshtein distance between two strings.
     * This is the minimum number of single-character edits (insertions, deletions, or substitutions)
     * required to change one word into the other.
     *
     * @param a First string
     * @param b Second string
     * @return Levenshtein distance
     */
    private int calculateLevenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= b.length(); j++) dp[0][j] = j;

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(
                        dp[i - 1][j - 1],
                        Math.min(dp[i - 1][j], dp[i][j - 1])
                    );
                }
            }
        }

        return dp[a.length()][b.length()];
    }
}
