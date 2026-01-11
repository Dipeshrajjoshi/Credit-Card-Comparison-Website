package com.example.demo.service;

import com.example.demo.model.CreditCard;
import com.example.demo.util.ExcelReader;
import com.example.demo.util.SpellChecking;
import com.example.demo.util.Trie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class providing business logic for Credit Card operations.
 */
@Service
public class CreditCardService {

    private final List<CreditCard> creditCards;
    private final Trie trie;
    private final SpellChecking spellChecker;

    @Autowired
    private SearchHistoryService searchHistoryService;

    /**
     * Constructor loads credit card data from Excel, initializes trie and spell checker.
     */
    public CreditCardService() {
        try {
            InputStream fileStream = getClass().getClassLoader()
                    .getResourceAsStream("Credit_Card_Details.xlsx");
            if (fileStream == null) {
                throw new RuntimeException("Excel file not found in resources folder.");
            }
            this.creditCards = ExcelReader.readCreditCardsFromExcel(fileStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read Excel file: " + e.getMessage(), e);
        }

        this.trie = new Trie();
        this.spellChecker = new SpellChecking(creditCards);
        buildTrie();
    }

    /**
     * Returns all loaded credit cards.
     */
    public List<CreditCard> getAllCards() {
        return creditCards;
    }

    /**
     * Filters cards by exact bank name match (case-insensitive).
     */
    public List<CreditCard> getCardsByBank(List<CreditCard> cards, String bankName) {
        return cards.stream()
                .filter(card -> card.getBankName() != null &&
                        card.getBankName().trim().equalsIgnoreCase(bankName.trim()))
                .collect(Collectors.toList());
    }

    /**
     * Filters cards by annual fee range.
     */
    public List<CreditCard> getCardsByAnnualFees(List<CreditCard> cards, Double minFee, Double maxFee) {
        return cards.stream()
                .filter(card -> {
                    try {
                        double fee = Double.parseDouble(card.getAnnualFees().replace("$", "").trim());
                        return fee >= minFee && fee <= maxFee;
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid annual fee format: " + card.getAnnualFees());
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * Filters cards by purchase interest rate range.
     * Assumes interest rate is represented as a decimal (e.g., 0.15 for 15%).
     */
    public List<CreditCard> getCardsByPurchaseInterestRate(List<CreditCard> cards,
                                                           Double minInterest, Double maxInterest) {
        return cards.stream()
                .filter(card -> {
                    try {
                        double interestRate = Double.parseDouble(card.getPurchaseInterestRate()) * 100;
                        return interestRate >= minInterest && interestRate <= maxInterest;
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid purchase interest rate format: " + card.getPurchaseInterestRate());
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * Filters cards by search term within the card title (case-insensitive).
     * Also records the search term in search history.
     */
    public List<CreditCard> getCardsBySearchTerm(List<CreditCard> cards, String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return cards;
        }

        searchHistoryService.recordSearch(searchTerm);
        String lowerSearch = searchTerm.toLowerCase();

        return cards.stream()
                .filter(card -> {
                    String title = card.getCardTitle() != null ? card.getCardTitle().toLowerCase() : "";
                    return title.contains(lowerSearch);
                })
                .collect(Collectors.toList());
    }

    /**
     * Builds the trie for autocomplete based on tokens from card titles,
     * value propositions, and benefits.
     */
    private void buildTrie() {
        for (CreditCard card : creditCards) {
            String combinedText = (card.getCardTitle() + " " +
                    card.getProductValueProp() + " " + card.getProductBenefits()).toLowerCase();
            String[] tokens = combinedText.split("\\s+");
            for (String token : tokens) {
                trie.insert(token);
            }
        }
    }

    /**
     * Returns autocomplete suggestions for a given prefix.
     */
    public List<String> getAutocompleteSuggestions(String prefix) {
        return trie.searchPrefix(prefix.toLowerCase());
    }

    /**
     * Returns spelling correction suggestions for a given word.
     */
    public List<String> getSpellingSuggestions(String word) {
        return spellChecker.getSuggestions(word, 2, 3);
    }

    /**
     * Returns the frequency of a given word in all credit card data.
     */
    public int getWordFrequency(String word) {
        return spellChecker.getWordFrequency(word);
    }
}
