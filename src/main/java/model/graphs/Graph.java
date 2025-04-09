package model.graphs;

import model.enums.Lane;

import java.util.*;

/**
 * Graph class represents a generic graph structure with vertices and edges.
 * It supports directed and bidirectional edges, as well as utility methods for graph creation.
 *
 * @param <VertexType> The type of vertices in the graph.
 */
public class Graph<VertexType> {
    private final Map<VertexType, Set<VertexType>> graphMap = new HashMap<>();

    /**
     * Adds directed edge between given vertices
     *
     * @param vertex The beginning of an edge
     * @param neighbour The end of an edge
     */
    public void addDirectedEdge(VertexType vertex, VertexType neighbour) {
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
    public void addEdge(VertexType vertex1, VertexType vertex2) {
        this.addDirectedEdge(vertex1, vertex2);
        this.addDirectedEdge(vertex2, vertex1);
    }

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex The vertex to add.
     */
    public void addVertex(VertexType vertex) {
        if (this.graphMap.containsKey(vertex)) {
            return;
        }

        this.graphMap.put(vertex, new HashSet<>());
    }

    /**
     * Retrieves all vertices in the graph.
     *
     * @return A set of all vertices in the graph.
     */
    public Set<VertexType> getVertices() {
        return this.graphMap.keySet();
    }

    /**
     * Retrieves the neighbors of a given vertex.
     *
     * @param vertex The vertex whose neighbors are to be retrieved.
     * @return A set of neighbors of the given vertex.
     * @throws IllegalArgumentException If the vertex does not exist in the graph.
     */
    public Set<VertexType> getNeighbours(VertexType vertex) throws IllegalArgumentException {
        if ( ! this.graphMap.containsKey(vertex)) {
            throw new IllegalArgumentException(String.format("Vertex %s does not exist!!!", vertex));
        }

        return Collections.unmodifiableSet(this.graphMap.get(vertex));
    }

    /**
     * Checks if an edge exists between two vertices.
     *
     * @param vertex The starting vertex of the edge.
     * @param neighbour The ending vertex of the edge.
     * @return `true` if the edge exists, otherwise `false`.
     */
    public boolean containsEdge(VertexType vertex, VertexType neighbour) {
        if ( ! this.graphMap.containsKey(vertex)) {
            return false;
        }

        return this.graphMap.get(vertex).contains(neighbour);
    }

    /**
     * Creates weighted graph from Vertices list. Vertices of result graph are Lanes with weight of vehicles count which
     * occupy these Lanes. Edges are created only between vertices that contain non-colliding Lanes.
     *
     * @param lanesList List of Vertex Objects that create graph
     * @return Graph
     */
    public static Graph<Vertex<Lane>> fromLanes(List<Vertex<Lane>> lanesList) {
        int lanesCount = lanesList.size();
        Graph<Vertex<Lane>> graph = new Graph<>();

        for (int vertexId = 0; vertexId < lanesCount; vertexId++) {
            Vertex<Lane> vertex = lanesList.get(vertexId);
            graph.addVertex(vertex);
            Lane vertexLane = vertex.id();

            for (int neighbourId = vertexId + 1; neighbourId < lanesCount; neighbourId++) {
                Vertex<Lane> neighbour = lanesList.get(neighbourId);
                Lane neighbourLane = neighbour.id();

                if ( ! vertexLane.collide(neighbourLane)) {
                    graph.addEdge(vertex, neighbour);
                }
            }
        }

        return graph;
    }

    @Override
    public String toString() {
        return this.graphMap.toString();
    }
}
