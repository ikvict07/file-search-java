package org.fiit;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Node implements Serializable {
    char letter;
    char[] remainingWord;
    Map<Character, Node> children;

    public Node(char letter) {
        this.letter = letter;
        this.remainingWord = new char[0];
        this.children = new HashMap<>(5);
    }
    public Node() {
    }
}