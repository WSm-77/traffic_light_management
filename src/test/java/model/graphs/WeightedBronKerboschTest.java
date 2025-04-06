package model.graphs;

import model.enums.Lane;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WeightedBronKerboschTest {

    @Test
    void findMaximumWeightClique_AllVerticesInClique() {
        Graph<Vertex<Lane>> graph = new Graph<>();
        Vertex<Lane> v1 = new Vertex<>(Lane.SOUTH_RIGHT, 1);
        Vertex<Lane> v2 = new Vertex<>(Lane.EAST_RIGHT, 2);
        Vertex<Lane> v3 = new Vertex<>(Lane.NORTH_RIGHT, 3);
        Vertex<Lane> v4 = new Vertex<>(Lane.WEST_RIGHT, 4);

        graph.addEdge(v1, v2);
        graph.addEdge(v2, v3);
        graph.addEdge(v3, v4);
        graph.addEdge(v4, v1);

        graph.addEdge(v1, v3);
        graph.addEdge(v2, v4);

        WeightedBronKerbosch algorithm = new WeightedBronKerbosch(graph);
        Set<Vertex<Lane>> maxClique = algorithm.findMaximumWeightClique();

        assertEquals(4, maxClique.size());
        assertTrue(maxClique.contains(v1));
        assertTrue(maxClique.contains(v2));
        assertTrue(maxClique.contains(v3));
        assertTrue(maxClique.contains(v4));
    }

    @Test
    void findMaximumWeightClique_MaxWeightCliqueHasLessVerticesThanMaxSizeClique() {
        Graph<Vertex<Lane>> graph = new Graph<>();
        Vertex<Lane> v1 = new Vertex<>(Lane.SOUTH_RIGHT, 3);
        Vertex<Lane> v2 = new Vertex<>(Lane.EAST_RIGHT, 100);
        Vertex<Lane> v3 = new Vertex<>(Lane.NORTH_RIGHT, 2);
        Vertex<Lane> v4 = new Vertex<>(Lane.WEST_RIGHT, 1);
        Vertex<Lane> v5 = new Vertex<>(Lane.NORTH_STRAIGHT, 2);

        graph.addEdge(v1, v3);
        graph.addEdge(v1, v4);
        graph.addEdge(v1, v5);

        graph.addEdge(v2, v3);

        graph.addEdge(v4, v5);

        WeightedBronKerbosch algorithm = new WeightedBronKerbosch(graph);
        Set<Vertex<Lane>> maxClique = algorithm.findMaximumWeightClique();

        assertEquals(2, maxClique.size());
        assertTrue(maxClique.contains(v2));
        assertTrue(maxClique.contains(v3));
    }

    @Test
    void findMaximumWeightClique_EmptyGraph() {
        Graph<Vertex<Lane>> graph = new Graph<>();
        WeightedBronKerbosch algorithm = new WeightedBronKerbosch(graph);
        Set<Vertex<Lane>> maxClique = algorithm.findMaximumWeightClique();

        assertTrue(maxClique.isEmpty());
    }

    @Test
    void findMaximumWeightClique_SingleVertex() {
        Graph<Vertex<Lane>> graph = new Graph<>();
        Vertex<Lane> v1 = new Vertex<>(Lane.WEST_RIGHT, 1);
        graph.addVertex(v1);

        WeightedBronKerbosch algorithm = new WeightedBronKerbosch(graph);
        Set<Vertex<Lane>> maxClique = algorithm.findMaximumWeightClique();

        assertEquals(1, maxClique.size());
        assertTrue(maxClique.contains(v1));
    }

    @Test
    void findMaximumWeightClique_DisconnectedGraph() {
        Graph<Vertex<Lane>> graph = new Graph<>();
        Vertex<Lane> v1 = new Vertex<>(Lane.WEST_RIGHT, 1);
        Vertex<Lane> v2 = new Vertex<>(Lane.NORTH_RIGHT, 2);
        Vertex<Lane> v3 = new Vertex<>(Lane.SOUTH_RIGHT, 3);
        Vertex<Lane> v4 = new Vertex<>(Lane.EAST_RIGHT, 4);

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);

        WeightedBronKerbosch algorithm = new WeightedBronKerbosch(graph);
        Set<Vertex<Lane>> maxClique = algorithm.findMaximumWeightClique();

        assertEquals(1, maxClique.size());
        assertTrue(maxClique.contains(v4));
    }
}
