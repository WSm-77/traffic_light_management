package model.graphs;

import java.util.HashSet;
import java.util.Set;

public class WeightedBronKerbosch {
    private final Graph graph;
    private int maxCliqueWeight = -1;
    private Set<Vertex> maxClique = new HashSet<>();

    public WeightedBronKerbosch(Graph graph) {
        this.graph = graph;
    }

    public Set<Vertex> findMaximumWeightClique() {
        Set<Vertex> currentClique = new HashSet<>();
        Set<Vertex> prospective = this.graph.getVertices();
        Set<Vertex> excluded = new HashSet<>();

        broneKerbosch(currentClique, prospective, excluded);

        return this.maxClique;
    }

    /**
     * Recursive Bron-Kerbosch algorithm with pivoting for Maximum Vertex-Weighted Clique.
     *
     * @param currentClique Current clique
     * @param prospective Prospective vertices that can extend the current clique
     * @param excluded Excluded vertices that have been processed
     */
    private void broneKerbosch(Set<Vertex> currentClique, Set<Vertex> prospective, Set<Vertex> excluded) {
        // check if currentClique is max weighted clique
        if (prospective.isEmpty() && excluded.isEmpty()) {
            int currentCliqueWeight = this.calculateCliqueWeight(currentClique);

            if (this.maxCliqueWeight < currentCliqueWeight) {
                this.maxCliqueWeight = currentCliqueWeight;
                this.maxClique = new HashSet<>(currentClique);
            }

            return;
        }

        if ( ! prospective.isEmpty() && this.calculateCliqueWeight(currentClique) +
                this.estimateRemainingWeight(prospective) > this.maxCliqueWeight) {
            // select pivot
            Vertex pivot = this.choosePivotVertex(prospective);
            Set<Vertex> pivotNeighbours = this.graph.getNeighbours(pivot);

            // determine set: prospective \ neighbours(pivot)
            Set<Vertex> prospectiveWithoutPivotNeighbours = new HashSet<>(prospective);
            prospectiveWithoutPivotNeighbours.removeAll(pivotNeighbours);

            // make sure pivot belongs to this set
            prospectiveWithoutPivotNeighbours.add(pivot);

            for (Vertex vertex : prospectiveWithoutPivotNeighbours) {
                Set<Vertex> vertexNeighbours = this.graph.getNeighbours(vertex);

                Set<Vertex> newCurrentClique = new HashSet<>(currentClique);
                newCurrentClique.add(vertex);

                HashSet<Vertex> newProspective = new HashSet<>(prospective);
                newProspective.retainAll(vertexNeighbours);

                HashSet<Vertex> newExcluded = new HashSet<>(excluded);
                newExcluded.retainAll(vertexNeighbours);

                this.broneKerbosch(newCurrentClique, newProspective, newExcluded);

                prospective.remove(vertex);
                excluded.add(vertex);
            }
        }
    }

    private int calculateCliqueWeight(Set<Vertex> vertices) {
        if (vertices.isEmpty()) {
            return 0;
        }

        return vertices.stream()
                .mapToInt(Vertex::weight)
                .sum();
    }

    private int estimateRemainingWeight(Set<Vertex> vertices) {
        return vertices.stream()
                .mapToInt(this::weightedDegree)
                .max()
                .orElse(0);
    }

    private int weightedDegree(Vertex vertex) {
        return vertex.weight();
//                this.graph.getNeighbours(vertex).stream()
//                        .mapToInt(Vertex::weight)
//                        .sum();
    }

    private Vertex choosePivotVertex(Set<Vertex> prospective) {
        return prospective.stream()
                .max((vertex1, vertex2) -> Integer.compare(vertex1.weight(), vertex2.weight()))
                .orElseThrow();
    }

}
