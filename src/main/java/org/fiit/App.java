package org.fiit;

import java.io.BufferedReader;

public class App {
    public static void main(String[] args) {
//        FileIndexer fileIndexer = new FileIndexer();
//        fileIndexer.startIndexing();
//        Trie trie = fileIndexer.getTrie();
//        SerializationUtils.serialize(trie, "trie.ser");

//        System.out.println("All paths: " + trie.root.children);
//        printAllPaths(trie.root);
//        System.out.println("FOUND: " + trie.search("LICENSE"));

        Trie trie1 = SerializationUtils.deserialize("trie.ser");

        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));
        String line = null;
        while (true) {
            try {
                line = reader.readLine();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            if (line.equals("exit")) {
                break;
            }
            System.out.println("FOUND: " + trie1.search(line));
        }
    }

    public static void printAllPaths(Node node) {
        if (node instanceof FinalNode) {
            for (String path : ((FinalNode) node).paths) {
                System.out.println("Path: " + path);
            }
        }
        for (Node child : node.children.values()) {
            printAllPaths(child);
        }
    }
}
