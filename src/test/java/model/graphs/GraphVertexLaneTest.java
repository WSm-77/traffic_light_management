package model.graphs;

import model.enums.Lane;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GraphVertexLaneTest {

    @Test
    void addVertex_AddsVertexCorrectly() {
        Graph<Vertex<Lane>> graph = new Graph<>();
        Vertex<Lane> vertex1 = new Vertex<>(Lane.NORTH_RIGHT, 1);
        Vertex<Lane> vertex2 = new Vertex<>(Lane.NORTH_STRAIGHT, 1);
        Vertex<Lane> vertex3 = new Vertex<>(Lane.NORTH_LEFT, 1);

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex3);
        Set<Vertex<Lane>> vertices = graph.getVertices();

        assertEquals(3, vertices.size());
        assertTrue(vertices.contains(vertex1));
        assertTrue(vertices.contains(vertex2));
        assertTrue(vertices.contains(vertex3));
    }

    @Test
    void addDirectedEdge_AddsEdgeCorrectly() {
        Graph<Vertex<Lane>> graph = new Graph<>();
        Vertex<Lane> vertex1 = new Vertex<>(Lane.NORTH_STRAIGHT, 1);
        Vertex<Lane> vertex2 = new Vertex<>(Lane.SOUTH_STRAIGHT, 1);

        graph.addDirectedEdge(vertex1, vertex2);

        assertTrue(graph.getNeighbours(vertex1).contains(vertex2));
        assertFalse(graph.getNeighbours(vertex2).contains(vertex1));
    }

    @Test
    void addEdge_AddsBidirectionalEdgeCorrectly() {
        Graph<Vertex<Lane>> graph = new Graph<>();
        Vertex<Lane> vertex1 = new Vertex<>(Lane.NORTH_STRAIGHT, 1);
        Vertex<Lane> vertex2 = new Vertex<>(Lane.SOUTH_STRAIGHT, 1);

        graph.addEdge(vertex1, vertex2);

        assertTrue(graph.getNeighbours(vertex1).contains(vertex2));
        assertTrue(graph.getNeighbours(vertex2).contains(vertex1));
    }

    @Test
    void getVertices_ReturnsAllVertices() {
        Graph<Vertex<Lane>> graph = new Graph<>();
        Vertex<Lane> vertex1 = new Vertex<>(Lane.NORTH_STRAIGHT, 1);
        Vertex<Lane> vertex2 = new Vertex<>(Lane.SOUTH_STRAIGHT, 1);

        graph.addEdge(vertex1, vertex2);

        Set<Vertex<Lane>> vertices = graph.getVertices();
        assertEquals(2, vertices.size());
        assertTrue(vertices.contains(vertex1));
        assertTrue(vertices.contains(vertex2));
    }

    @Test
    void getNeighbours_ThrowsExceptionForNonExistentVertex() {
        Graph<Vertex<Lane>> graph = new Graph<>();
        Vertex<Lane> vertex = new Vertex<>(Lane.NORTH_STRAIGHT, 1);

        assertThrows(IllegalArgumentException.class, () -> graph.getNeighbours(vertex));
    }

    @Test
    void getNeighbours_ReturnsCorrectNeighbours() {
        Graph<Vertex<Lane>> graph = new Graph<>();
        Vertex<Lane> vertex1 = new Vertex<>(Lane.NORTH_STRAIGHT, 1);
        Vertex<Lane> vertex2 = new Vertex<>(Lane.SOUTH_STRAIGHT, 1);
        Vertex<Lane> vertex3 = new Vertex<>(Lane.EAST_LEFT, 1);

        graph.addEdge(vertex1, vertex2);
        graph.addDirectedEdge(vertex1, vertex3);

        Set<Vertex<Lane>> neighbours = graph.getNeighbours(vertex1);
        assertEquals(2, neighbours.size());
        assertTrue(neighbours.contains(vertex2));
        assertTrue(neighbours.contains(vertex3));
    }

    @Test
    void containsEdge_ChecksEdgesExistenceCorrectly() {
        Graph<Vertex<Lane>> graph = new Graph<>();
        Vertex<Lane> vertex1 = new Vertex<>(Lane.NORTH_STRAIGHT, 1);
        Vertex<Lane> vertex2 = new Vertex<>(Lane.SOUTH_STRAIGHT, 1);
        Vertex<Lane> vertex3 = new Vertex<>(Lane.EAST_LEFT, 1);

        graph.addEdge(vertex1, vertex2);
        graph.addDirectedEdge(vertex1, vertex3);

        assertTrue(graph.containsEdge(vertex1, vertex2));
        assertTrue(graph.containsEdge(vertex2, vertex1));
        assertTrue(graph.containsEdge(vertex1, vertex3));
        assertFalse(graph.containsEdge(vertex3, vertex1));
    }

    @Test
    void fromLanes_CreatesGraphCorrectly() {
        Vertex<Lane> vertex1 = new Vertex<>(Lane.NORTH_STRAIGHT, 1);
        Vertex<Lane> vertex2 = new Vertex<>(Lane.EAST_STRAIGHT, 3);     // collides with v1 and v4
        Vertex<Lane> vertex3 = new Vertex<>(Lane.SOUTH_RIGHT, 2);
        Vertex<Lane> vertex4 = new Vertex<>(Lane.SOUTH_STRAIGHT, 1);
        List<Vertex<Lane>> verticesList = List.of(vertex1, vertex2, vertex3, vertex4);

        Graph<Vertex<Lane>> graph = Graph.fromLanes(verticesList);
        Set<Vertex<Lane>> vertices = graph.getVertices();

        assertEquals(4, vertices.size());
        assertTrue(vertices.contains(vertex1));
        assertTrue(vertices.contains(vertex2));
        assertTrue(vertices.contains(vertex3));
        assertTrue(vertices.contains(vertex4));
        assertTrue(graph.containsEdge(vertex1, vertex3));
        assertTrue(graph.containsEdge(vertex1, vertex4));
        assertTrue(graph.containsEdge(vertex3, vertex4));
        assertTrue(graph.containsEdge(vertex3, vertex2));

        assertFalse(graph.containsEdge(vertex1, vertex2));
        assertFalse(graph.containsEdge(vertex4, vertex2));
    }
}
