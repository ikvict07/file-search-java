package org.fiit;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileIndexer {
    private Trie trie;
    private ExecutorService executor;

    public FileIndexer() {
        trie = new Trie();
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void startIndexing() {
        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
        for (Path root : rootDirectories) {
            try {
                Files.list(root).forEach(subDir -> {
                    executor.submit(() -> {
                        try {
                            Files.walkFileTree(subDir, new SimpleFileVisitor<>() {
                                @Override
                                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                                    addIndex(file.getFileName().toString(), file.toString());
                                    return FileVisitResult.CONTINUE;
                                }

                                @Override
                                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                                    if (exc instanceof AccessDeniedException) {
                                        System.err.println("Access denied to: " + file.toString());
                                        return FileVisitResult.SKIP_SUBTREE;
                                    }
                                    return FileVisitResult.CONTINUE;
                                }
                            });
                        } catch (IOException e) {
                            throw new UncheckedIOException("Error reading files from directory", e);
                        }
                    });
                });
            } catch (IOException e) {
                throw new UncheckedIOException("Error reading subdirectories from root directory", e);
            }
        }
        executor.shutdown();
        try {
            // Ожидание завершения всех задач
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Indexing interrupted", e);
        }
    }

    private void addIndex(String fileName, String filePath) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex != -1) {
            fileName = fileName.substring(0, lastDotIndex);
        }
        trie.insert(fileName, filePath);
    }

    public Trie getTrie() {
        return trie;
    }
}