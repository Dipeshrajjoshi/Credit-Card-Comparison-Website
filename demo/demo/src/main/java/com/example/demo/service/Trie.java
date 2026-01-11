package com.example.demo.service;

import java.util.*;

public class WordCompletion {

    private class Node {
        String word;
        Node left, right;
        int height;

        Node(String word) {
            this.word = word;
            this.height = 1;
        }
    }

    private Node root;

    // Public insert method
    public void insert(String word) {
        root = insert(root, word);
    }

    // Recursive insert with AVL balancing
    private Node insert(Node node, String word) {
        if (node == null) return new Node(word);

        int cmp = word.compareTo(node.word);
        if (cmp < 0) {
            node.left = insert(node.left, word);
        } else if (cmp > 0) {
            node.right = insert(node.right, word);
        } else {
            // duplicate word, do nothing or handle frequency
            return node;
        }

        updateHeight(node);
        return balance(node);
    }

    // Get height helper
    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    // Update height helper
    private void updateHeight(Node node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    // Get balance factor
    private int getBalance(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // Balance node
    private Node balance(Node node) {
        int balance = getBalance(node);

        // Left heavy
        if (balance > 1) {
            if (getBalance(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        // Right heavy
        if (balance < -1) {
            if (getBalance(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }

        return node;
    }

    // Right rotation
    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        updateHeight(y);
        updateHeight(x);

        return x;
    }

    // Left rotation
    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        updateHeight(x);
        updateHeight(y);

        return y;
    }

    /**
     * Autocomplete: find up to maxSuggestions words that start with the prefix.
     */
    public List<String> autocomplete(String prefix, int maxSuggestions) {
        List<String> results = new ArrayList<>();
        autocomplete(root, prefix, results, maxSuggestions);
        return results;
    }

    // Helper recursive method to perform in-order traversal from smallest candidate >= prefix
    private void autocomplete(Node node, String prefix, List<String> results, int maxSuggestions) {
        if (node == null || results.size() >= maxSuggestions) return;

        int cmpPrefixNode = prefix.compareTo(node.word);

        if (cmpPrefixNode <= 0) {
            // If node.word >= prefix, search left subtree first
            autocomplete(node.left, prefix, results, maxSuggestions);
        }

        if (node.word.startsWith(prefix) && results.size() < maxSuggestions) {
            results.add(node.word);
        }

        if (cmpPrefixNode >= 0) {
            // Search right subtree if node.word <= prefix or if we want more results
            autocomplete(node.right, prefix, results, maxSuggestions);
        }
    }

    // Optional: method to print tree inorder for debugging
    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node node) {
        if (node == null) return;
        printInOrder(node.left);
        System.out.println(node.word);
        printInOrder(node.right);
    }
}
