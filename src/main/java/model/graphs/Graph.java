package model.graphs;

import java.util.*;

public class Graph {
    private final Map<Vertex, Set<Vertex>> graphMap = new HashMap<>();

    /**
     * Adds directed edge between given vertices
     *
     * @param vertex The beginning of an edge
     * @param neighbour The end of an edge
     */
    public void addDirectedEdge(Vertex vertex, Vertex neighbour) {
        if ( ! graphMap.containsKey(vertex)) {
            graphMap.put(vertex, new HashSet<>());
        }

        if ( ! graphMap.containsKey(neighbour)) {
            graphMap.put(neighbour, new HashSet<>());
        }

        graphMap.get(vertex).add(neighbour);
    }

    /**
     * Adds bidirectional edge between given vertices
     *
     * @param vertex1 First vertex
     * @param vertex2 Second vertex
     */
    public void addEdge(Vertex vertex1, Vertex vertex2) {
        this.addDirectedEdge(vertex1, vertex2);
        this.addDirectedEdge(vertex2, vertex1);
    }

    public void addVertex(Vertex vertex) {
        if (this.graphMap.containsKey(vertex)) {
            return;
        }

        this.graphMap.put(vertex, new HashSet<>());
    }

    public Set<Vertex> getVertices() {
        return this.graphMap.keySet();
    }

    public Set<Vertex> getNeighbours(Vertex vertex) throws IllegalArgumentException {
        if ( ! this.graphMap.containsKey(vertex)) {
            throw new IllegalArgumentException(String.format("Vertex of id: %s does not exist!!!", vertex.id().name()));
        }

        return Collections.unmodifiableSet(this.graphMap.get(vertex));
    }
}
