package org.fiit;

import java.io.Serializable;
import java.util.List;


public class Trie implements Serializable {
    public Node root;

    public Trie() {
        root = new Node('\0');
    }

    public void insert(String word, String filePath) {
        Node current = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!current.children.containsKey(c)) {
                if (i == word.length() - 1) {
                    current.children.put(c, new FinalNode(c));
                } else {
                    current.children.put(c, new Node(c));
                }
            }
            current = current.children.get(c);
            if (current.remainingWord.length > 0) {
                char[] remaining = current.remainingWord;
                current.remainingWord = new char[0];
                insertRemainingWord(current, remaining);
            }
        }
        if (current instanceof FinalNode) {
            ((FinalNode) current).paths.add(filePath);
        }
    }

    private void insertRemainingWord(Node current, char[] remainingWord) {
        for (char c : remainingWord) {
            current.children.put(c, new Node(c));
            current = current.children.get(c);
        }
    }

    public List<String> search(String word) {
        Node current = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!current.children.containsKey(c)) {
                return null;
            }
            current = current.children.get(c);
            if (current.remainingWord.length > 0) {
                if (word.substring(i + 1).equals(new String(current.remainingWord))) {
                    return current instanceof FinalNode ? ((FinalNode) current).paths : null;
                } else {
                    return null;
                }
            }
        }
        return current instanceof FinalNode ? ((FinalNode) current).paths : null;
    }
}