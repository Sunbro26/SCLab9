/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import graph.Graph;

/**
 * A graph-based poetry generator.
 */
public class GraphPoet {

    private final Graph<String> graph = Graph.empty();

    // Abstraction function:
    //   Represents a graph where vertices are words and edges represent word adjacency
    //   in the corpus, with weights equal to the frequency of the adjacency.
    // Representation invariant:
    //   All vertices and edges must be non-null.
    // Safety from rep exposure:
    //   The graph is encapsulated and never directly exposed.

    /**
     * Create a new poet with the graph from corpus.
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        List<String> words = new ArrayList<>();
        try (Scanner scanner = new Scanner(corpus)) {
            while (scanner.hasNextLine()) {
                String[] tokens = scanner.nextLine().toLowerCase().split("\\W+");
                for (String token : tokens) {
                    if (!token.isEmpty()) {
                        words.add(token);
                    }
                }
            }
        }

        // Build the graph
        for (int i = 0; i < words.size() - 1; i++) {
            String word1 = words.get(i);
            String word2 = words.get(i + 1);
            int weight = graph.set(word1, word2, 0) + 1; // Increment edge weight
            graph.set(word1, word2, weight);
        }

        checkRep();
    }

    // Check representation invariant
    private void checkRep() {
        assert graph != null;
        for (String vertex : graph.vertices()) {
            assert vertex != null;
        }
    }

    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        String[] words = input.split("\\s+");
        StringBuilder poem = new StringBuilder();

        for (int i = 0; i < words.length - 1; i++) {
            String w1 = words[i].toLowerCase();
            String w2 = words[i + 1].toLowerCase();
            String bridge = null;
            int maxWeight = 0;

            // Find bridge word with max weight
            for (String b : graph.targets(w1).keySet()) {
                if (graph.targets(b).containsKey(w2)) {
                    int weight = graph.targets(w1).get(b) + graph.targets(b).get(w2);
                    if (weight > maxWeight) {
                        maxWeight = weight;
                        bridge = b;
                    }
                }
            }

            // Append words and bridge
            poem.append(words[i]);
            if (bridge != null) {
                poem.append(" ").append(bridge.toLowerCase());
            }
            poem.append(" ");
        }
        poem.append(words[words.length - 1]); // Append the last word
        return poem.toString().trim();
    }

    @Override
    public String toString() {
        return "GraphPoet with affinity graph: " + graph;
    }
}
