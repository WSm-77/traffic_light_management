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

/**
 * Simulation class represents a traffic simulation system.
 * It manages the roads and vehicles in the simulation and allows for
 * simulating the movement of vehicles through the traffic lanes.
 */

public class Simulation {
    private final Map<Direction, Road> roadsMap = new HashMap<>();
    private final List<SimulationObserver> subscribersList = new ArrayList<>();

    public Simulation(List<Road> roads) {
        roads.forEach(this::addRoad);
    }

    /**
     * Adds a road to the simulation. Ensures that no duplicate roads with the same direction are added.
     *
     * @param road The road to be added to the simulation.
     * @throws IllegalArgumentException If a road with the same direction already exists.
     */
    private void addRoad(Road road) throws IllegalArgumentException {
        Direction roadDirection = road.getDirection();
        if (this.roadsMap.containsKey(roadDirection)) {
            throw new IllegalArgumentException(
                String.format("Road of direction %s already exists!!!", roadDirection.toString())
            );
        }

        this.roadsMap.put(roadDirection, road);
    }

    /**
     * Adds vehicle to the simulation.
     *
     * @param car Car that should be added to the simulation
     */
    public void addVehicle(Car car) {
        Direction carDirection = car.lane().getDirection();
        Road road = this.roadsMap.get(carDirection);
        road.addCar(car);
    }

    /**
     * Simulates traffic lights switch. Selects maximum number of non-colliding cars that are allowed to leave
     * crossing in current simulation state.
     *
     * @return List of Cars that leave crossing during current simulation step
     */
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

        this.notifySimulationStep(carsLeavingCrossing);

        return carsLeavingCrossing;
    }

    /**
     * Combines mappings from all roads into single Lanes to List of TrafficLanes mapping that represents which
     * traffic lanes serve a specific Lane role during current simulation step.
     *
     * @return Lanes to List of TrafficLanes mapping
     */
    private Map<Lane, List<TrafficLane>> getAllLanesMapping() {
        Map<Lane, List<TrafficLane>> allLanesMapping = new HashMap<>();

        for (Road road : this.roadsMap.values()) {
            Map<Lane, List<TrafficLane>> laneMapping = road.getLaneMapping();
            allLanesMapping.putAll(laneMapping);
        }

        return allLanesMapping;
    }

    /**
     * Creates list of graph vertices useful for finding maximum set of non-colliding cars. Each vertex represents
     * how many cars can potentially leave crossing from each Lane type.
     *
     * @param allLanesMapping All Lanes to List of TrafficLanes mapping
     * @return List of Vertices useful for Graph creation
     */
    private List<Vertex<Lane>> getVerticesList(Map<Lane, List<TrafficLane>> allLanesMapping) {
        return allLanesMapping.entrySet().stream()
                .map(entry -> new Vertex<Lane>(entry.getKey(), entry.getValue().size()))
                .toList();
    }

    /**
     * Notify subscribers about cars leaving crossing during current simulation step.
     *
     * @param carsLeavingCrossing List of cars that leave crossing during current simulation step
     */
    private void notifySimulationStep(List<Car> carsLeavingCrossing) {
        for (SimulationObserver subscriber : this.subscribersList) {
            subscriber.notifySimulationStep(carsLeavingCrossing);
        }
    }

    /**
     * Registers an observer to receive notifications about future simulation status changes.
     *
     * @param simulationObserver SimulationObserver object to register
     */
    public void subscribe(SimulationObserver simulationObserver) {
        this.subscribersList.add(simulationObserver);
    }
}
