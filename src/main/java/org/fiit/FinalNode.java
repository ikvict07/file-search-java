package org.fiit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FinalNode extends Node {
    List<String> paths;

    public FinalNode(char letter) {
        super(letter);
        this.paths = new ArrayList<>();
    }
    public FinalNode() {
    }
}