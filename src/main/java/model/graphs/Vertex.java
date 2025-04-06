package model.graphs;

import model.enums.Lane;

public record Vertex<T>(T id, int weight) {
}
