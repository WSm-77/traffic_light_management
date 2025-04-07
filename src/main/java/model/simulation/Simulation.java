package model.simulation;

import model.enums.Direction;
import model.enums.Lane;
import model.graphs.Graph;
import model.graphs.Vertex;
import model.graphs.WeightedBronKerbosch;
import model.traffic.Car;
import model.traffic.Road;
import model.traffic.TrafficLane;

import java.util.*;

public class Simulation {
    private final Map<Direction, Road> roadsMap = new HashMap<>();

    public Simulation(List<Road> roads) {
        roads.forEach(this::addRoad);
    }

    private void addRoad(Road road) {
        Direction roadDirection = road.getDirection();
        if (this.roadsMap.containsKey(roadDirection)) {
            throw new IllegalArgumentException(String.format("Road of direction %s already exists!!!", roadDirection.toString()));
        }

        this.roadsMap.put(roadDirection, road);
    }

    public void addVehicle(Car car) {
        Direction carDirection = car.lane().getDirection();
        Road road = this.roadsMap.get(carDirection);
        road.addCar(car);
    }

    private Map<Lane, List<TrafficLane>> getAllLanesMapping() {
        Map<Lane, List<TrafficLane>> allLanesMapping = new HashMap<>();

        for (Road road : this.roadsMap.values()) {
            Map<Lane, List<TrafficLane>> laneMapping = road.getLaneMapping();
            allLanesMapping.putAll(laneMapping);
        }

        return allLanesMapping;
    }

    private List<Vertex<Lane>> getVerticesList(Map<Lane, List<TrafficLane>> allLanesMapping) {
        return allLanesMapping.entrySet().stream()
                .map(entry -> new Vertex<Lane>(entry.getKey(), entry.getValue().size()))
                .toList();
    }

    public List<Car> step() {
        Map<Lane, List<TrafficLane>> allLanesMapping = this.getAllLanesMapping();
        List<Vertex<Lane>> verticesList = this.getVerticesList(allLanesMapping);
        Graph<Vertex<Lane>> graph = Graph.fromLanes(verticesList);

        WeightedBronKerbosch weightedBronKerbosch = new WeightedBronKerbosch(graph);
        Set<Vertex<Lane>> maximumWeightClique = weightedBronKerbosch.findMaximumWeightClique();

        List<Car> carsLeavingCrossing = new ArrayList<>();

        for (Vertex<Lane> vertex : maximumWeightClique) {
            Lane lane = vertex.id();
            List<TrafficLane> trafficLaneWithGreenLightList = allLanesMapping.get(lane);

            trafficLaneWithGreenLightList.stream()
                    .map(TrafficLane::moveFirstCar)
                    .forEach(car -> carsLeavingCrossing.add(car));
        }

        return carsLeavingCrossing;
    }
}
