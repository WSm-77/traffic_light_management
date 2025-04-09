package model.graphs;

import model.enums.Lane;

import java.util.HashSet;
import java.util.Set;

/**
 * WeightedBronKerbosh class serves a role of solver for Bron-Kerbosh algorithm for maximum weighted clique finding.
 * To find maximum weighted clique in weighted graph initialize solver with graph instance and then
 * call <i>findMaximumWeightClique()</i> method.
 *
 * <p>Example</p>
 * <pre>
 *     WeightedBronKerbosch weightedBronKerbosch = new WeightedBronKerbosch(graph);
 *     Set<Vertex<Lane>> maxCliqueVertices = weightedBronKerbosch.findMaximumWeightClique();
 * </pre>
 */
public class WeightedBronKerbosch {
    private final Graph<Vertex<Lane>> graph;
    private int maxCliqueWeight = -1;
    private Set<Vertex<Lane>> maxClique = new HashSet<>();

    public WeightedBronKerbosch(Graph<Vertex<Lane>> graph) {
        this.graph = graph;
    }

    /**
     * Finds clique with maximum sum of vertices weights.
     *
     * @return Set of vertices creating maximum weight clique
     */
    public Set<Vertex<Lane>> findMaximumWeightClique() {
        Set<Vertex<Lane>> currentClique = new HashSet<>();
        Set<Vertex<Lane>> prospective = this.graph.getVertices();
        Set<Vertex<Lane>> excluded = new HashSet<>();

        bronKerbosch(currentClique, prospective, excluded);

        return this.maxClique;
    }

    /**
     * Recursive Bron-Kerbosch algorithm with pivoting for Maximum Vertex-Weighted Clique.
     *
     * @param currentClique Current clique
     * @param prospective Prospective vertices that can extend the current clique
     * @param excluded Excluded vertices that have been processed
     */
    private void bronKerbosch(Set<Vertex<Lane>> currentClique, Set<Vertex<Lane>> prospective, Set<Vertex<Lane>> excluded) {
        // check if currentClique is max weighted clique
        if (prospective.isEmpty() && excluded.isEmpty()) {
            int currentCliqueWeight = this.calculateCliqueWeight(currentClique);

            if (this.maxCliqueWeight < currentCliqueWeight) {
                this.maxCliqueWeight = currentCliqueWeight;
                this.maxClique = new HashSet<>(currentClique);
            }

            return;
        }

        if ( ! prospective.isEmpty()) {
            // select pivot
            Vertex<Lane> pivot = this.choosePivotVertex(prospective);
            Set<Vertex<Lane>> pivotNeighbours = this.graph.getNeighbours(pivot);

            // determine set: prospective \ neighbours(pivot)
            Set<Vertex<Lane>> prospectiveWithoutPivotNeighbours = new HashSet<>(prospective);
            prospectiveWithoutPivotNeighbours.removeAll(pivotNeighbours);

            // make sure pivot belongs to this set
            prospectiveWithoutPivotNeighbours.add(pivot);

            for (Vertex<Lane> vertex : prospectiveWithoutPivotNeighbours) {
                Set<Vertex<Lane>> vertexNeighbours = this.graph.getNeighbours(vertex);

                Set<Vertex<Lane>> newCurrentClique = new HashSet<>(currentClique);
                newCurrentClique.add(vertex);

                HashSet<Vertex<Lane>> newProspective = new HashSet<>(prospective);
                newProspective.retainAll(vertexNeighbours);

                HashSet<Vertex<Lane>> newExcluded = new HashSet<>(excluded);
                newExcluded.retainAll(vertexNeighbours);

                this.bronKerbosch(newCurrentClique, newProspective, newExcluded);

                prospective.remove(vertex);
                excluded.add(vertex);
            }
        }
    }

    /**
     * Calculates the total weight of a given set of vertices that create clique.
     *
     * @param vertices The set of vertices creating clique.
     * @return The total weight of the vertices, or 0 if the set is empty.
     */
    private int calculateCliqueWeight(Set<Vertex<Lane>> vertices) {
        if (vertices.isEmpty()) {
            return 0;
        }

        return vertices.stream()
                .mapToInt(Vertex::weight)
                .sum();
    }

    /**
     * Chooses the pivot vertex with the maximum weight from a set of prospective vertices.
     *
     * @param prospective The non-empty set of vertices to choose the pivot from.
     * @return The vertex with the maximum weight.
     * @throws IllegalStateException If set of prospective vertices is empty
     */
    private Vertex<Lane> choosePivotVertex(Set<Vertex<Lane>> prospective) throws IllegalStateException {
        return prospective.stream()
                .max((vertex1, vertex2) -> Integer.compare(vertex1.weight(), vertex2.weight()))
                .orElseThrow(() -> new IllegalStateException(
                        "Can not choose Pivot because set of prospective vertices is empty!!!")
                );
    }
}
