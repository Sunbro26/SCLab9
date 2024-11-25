/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * A mutable weighted directed graph with labeled vertices.
 * Vertices are of type L.
 * Edges are directed and have non-negative integer weights.
 */
public class Graph<L> {

    private final Map<L, Map<L, Integer>> adjacencyMap = new HashMap<>();

    /**
     * Create an empty graph.
     *
     * @param <L> the type of vertex labels in the graph
     * @return a new empty graph
     */
    public static <L> Graph<L> empty() {
        return new Graph<>();
    }

    /**
     * Add a vertex to the graph.
     *
     * @param vertex the vertex to add
     * @return true if the vertex was added, false if it was already in the graph
     */
    public boolean add(L vertex) {
        if (adjacencyMap.containsKey(vertex)) {
            return false;
        }
        adjacencyMap.put(vertex, new HashMap<>());
        return true;
    }

    /**
     * Set the weight of an edge.
     *
     * @param source the source vertex
     * @param target the target vertex
     * @param weight the weight of the edge
     * @return the previous weight of the edge, or 0 if there was no edge
     */
    public int set(L source, L target, int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight must be non-negative");
        }

        add(source); // Ensure vertices exist
        add(target);

        Map<L, Integer> edges = adjacencyMap.get(source);
        int previousWeight = edges.getOrDefault(target, 0);

        if (weight == 0) {
            edges.remove(target); // Remove the edge if weight is zero
        } else {
            edges.put(target, weight);
        }

        return previousWeight;
    }

    /**
     * Remove a vertex from the graph.
     *
     * @param vertex the vertex to remove
     * @return true if the vertex was removed, false if it was not in the graph
     */
    public boolean remove(L vertex) {
        if (!adjacencyMap.containsKey(vertex)) {
            return false;
        }

        adjacencyMap.remove(vertex);
        for (Map<L, Integer> edges : adjacencyMap.values()) {
            edges.remove(vertex);
        }
        return true;
    }

    /**
     * Get all vertices in the graph.
     *
     * @return a set of all vertices in the graph
     */
    public Set<L> vertices() {
        return new HashSet<>(adjacencyMap.keySet());
    }

    /**
     * Get the targets and their weights from a given vertex.
     *
     * @param source the source vertex
     * @return a map of target vertices and their weights
     */
    public Map<L, Integer> targets(L source) {
        return adjacencyMap.getOrDefault(source, new HashMap<>());
    }

    @Override
    public String toString() {
        return adjacencyMap.toString();
    }
}
