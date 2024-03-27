package org.fiit;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.*;

public class SerializationUtils {
    private static final Kryo kryo = new Kryo();

    static {
        kryo.register(Trie.class);
        kryo.register(Node.class);
        kryo.register(FinalNode.class);
        kryo.register(java.util.HashMap.class);
        kryo.register(char[].class);
        kryo.register(java.util.ArrayList.class);
    }

    public static void serialize(Trie trie, String fileName) {
        try (Output output = new Output(new FileOutputStream(fileName))) {
            kryo.writeClassAndObject(output, trie);
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize Trie", e);
        }
    }

    public static Trie deserialize(String fileName) {
        try (Input input = new Input(new FileInputStream(fileName))) {
            return (Trie) kryo.readClassAndObject(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize Trie", e);
        }
    }
}