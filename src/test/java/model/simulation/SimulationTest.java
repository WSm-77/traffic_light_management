package model.simulation;

import model.enums.Direction;
import model.enums.Lane;
import model.enums.Move;
import model.traffic.Car;
import model.traffic.Road;
import model.traffic.TrafficLane;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {
    private final static Set<Move> left = Set.of(Move.LEFT);
    private final static Set<Move> straight = Set.of(Move.STRAIGHT);
    private final static Set<Move> right = Set.of(Move.RIGHT);
    private final static Set<Move> straightRight = Set.of(Move.STRAIGHT, Move.RIGHT);
    private final static Set<Move> straightLeft = Set.of(Move.STRAIGHT, Move.LEFT);
    private final static Set<Move> all = Set.of(Move.STRAIGHT, Move.RIGHT, Move.LEFT);

    @Test
    void simulation_SingleTrafficLanes() {
        Simulation simulation = getSingleLanesSimulation();

        Car car1 = new Car("vehicle1", Lane.stringToLane("south", "north"));
        Car car2 = new Car("vehicle2", Lane.stringToLane("north", "south"));

        simulation.addVehicle(car1);
        simulation.addVehicle(car2);

        List<Car> leftAfterStep1 = simulation.step();
        List<Car> leftAfterStep2 = simulation.step();

        Car car3 = new Car("vehicle3", Lane.stringToLane("west", "south"));
        Car car4 = new Car("vehicle4", Lane.stringToLane("west", "south"));

        simulation.addVehicle(car3);
        simulation.addVehicle(car4);

        List<Car> leftAfterStep3 = simulation.step();
        List<Car> leftAfterStep4 = simulation.step();

        assertEquals(2, leftAfterStep1.size());
        assertEquals(0, leftAfterStep2.size());
        assertEquals(1, leftAfterStep3.size());
        assertEquals(1, leftAfterStep4.size());

        assertEquals(Set.of(car1, car2), new HashSet<>(leftAfterStep1));
        assertEquals(Set.of(), new HashSet<>(leftAfterStep2));
        assertEquals(Set.of(car3), new HashSet<>(leftAfterStep3));
        assertEquals(Set.of(car4), new HashSet<>(leftAfterStep4));
    }

    @Test
    void simulation_SeparateLaneForEachDirection() {
        // given
        Simulation simulation = getSeparateLaneForEachDirectionSimulation();
        Car car1 = new Car("vehicle1", Lane.stringToLane("south", "north"));
        Car car2 = new Car("vehicle2", Lane.stringToLane("north", "south"));
        Car car3 = new Car("vehicle3", Lane.stringToLane("west", "south"));
        Car car4 = new Car("vehicle4", Lane.stringToLane("west", "south"));

        // when
        simulation.addVehicle(car1);
        simulation.addVehicle(car2);

        List<Car> leftAfterStep1 = simulation.step();
        List<Car> leftAfterStep2 = simulation.step();

        simulation.addVehicle(car3);
        simulation.addVehicle(car4);

        List<Car> leftAfterStep3 = simulation.step();
        List<Car> leftAfterStep4 = simulation.step();

        // then
        assertEquals(2, leftAfterStep1.size());
        assertEquals(0, leftAfterStep2.size());
        assertEquals(1, leftAfterStep3.size());
        assertEquals(1, leftAfterStep4.size());

        assertEquals(Set.of(car1, car2), new HashSet<>(leftAfterStep1));
        assertEquals(Set.of(), new HashSet<>(leftAfterStep2));
        assertEquals(Set.of(car3), new HashSet<>(leftAfterStep3));
        assertEquals(Set.of(car4), new HashSet<>(leftAfterStep4));
    }

    @Test
    void simulation_MultipleCarsOnTheSameRoad() {
        // given
        Simulation simulation = getMultipleLanesForMultipleDirectionsSimulation();
        Car car1 = new Car("vehicle1", Lane.stringToLane("south", "east"));
        Car car2 = new Car("vehicle2", Lane.stringToLane("south", "east"));

        Car car3 = new Car("vehicle3", Lane.stringToLane("south", "north"));
        Car car4 = new Car("vehicle4", Lane.stringToLane("south", "north"));

        Car car5 = new Car("vehicle5", Lane.stringToLane("south", "east"));
        Car car6 = new Car("vehicle6", Lane.stringToLane("south", "east"));

        // when
        simulation.addVehicle(car1);
        simulation.addVehicle(car2);
        simulation.addVehicle(car3);
        simulation.addVehicle(car4);
        simulation.addVehicle(car5);
        simulation.addVehicle(car6);

        List<Car> leftAfterStep1 = simulation.step();
        List<Car> leftAfterStep2 = simulation.step();
        List<Car> leftAfterStep3 = simulation.step();
        List<Car> leftAfterStep4 = simulation.step();

        // then
        assertEquals(3, leftAfterStep1.size());
        assertEquals(2, leftAfterStep2.size());
        assertEquals(1, leftAfterStep3.size());
        assertEquals(0, leftAfterStep4.size());

        assertEquals(Set.of(car1, car2, car3), new HashSet<>(leftAfterStep1));
        assertEquals(Set.of(car4, car5), new HashSet<>(leftAfterStep2));
        assertEquals(Set.of(car6), new HashSet<>(leftAfterStep3));
        assertEquals(Set.of(), new HashSet<>(leftAfterStep4));
    }

    @Test
    void simulation_MultipleLanesForMultipleDirections() {
        // given
        Simulation simulation = getMultipleLanesForMultipleDirectionsSimulation();
        Car car1 = new Car("vehicle1", Lane.stringToLane("north", "east"));     // red light

        Car car2 = new Car("vehicle2", Lane.stringToLane("east", "north"));     // green light
        Car car3 = new Car("vehicle3", Lane.stringToLane("east", "west"));      // green light

        Car car4 = new Car("vehicle4", Lane.stringToLane("south", "east"));     // green light
        Car car5 = new Car("vehicle5", Lane.stringToLane("south", "east"));     // green light
        Car car6 = new Car("vehicle6", Lane.stringToLane("south", "north"));    // red light

        Car car7 = new Car("vehicle7", Lane.stringToLane("west", "south"));     // green light
        Car car8 = new Car("vehicle8", Lane.stringToLane("west", "east"));      // red light
        Car car9 = new Car("vehicle9", Lane.stringToLane("west", "north"));     // red light

        // when
        simulation.addVehicle(car1);
        simulation.addVehicle(car2);
        simulation.addVehicle(car3);
        simulation.addVehicle(car4);
        simulation.addVehicle(car5);
        simulation.addVehicle(car6);
        simulation.addVehicle(car7);
        simulation.addVehicle(car8);
        simulation.addVehicle(car9);

        List<Car> leftAfterStep1 = simulation.step();

        // then
        assertEquals(5, leftAfterStep1.size());

        assertEquals(Set.of(car2, car3, car4, car5, car7), new HashSet<>(leftAfterStep1));
    }

    private static Simulation getSeparateLaneForEachDirectionSimulation() {
        Road roadNorth = new Road(
                Direction.NORTH,
                List.of(
                    new TrafficLane(left),
                    new TrafficLane(straight),
                    new TrafficLane(right)
                ));
        Road roadEast = new Road(
                Direction.EAST,
                List.of(
                        new TrafficLane(left),
                        new TrafficLane(straight),
                        new TrafficLane(right)
                ));
        Road roadSouth = new Road(
                Direction.SOUTH,
                List.of(
                        new TrafficLane(left),
                        new TrafficLane(straight),
                        new TrafficLane(right)
                ));
        Road roadWest = new Road(
                Direction.WEST,
                List.of(
                        new TrafficLane(left),
                        new TrafficLane(straight),
                        new TrafficLane(right)
                ));

        return new Simulation(List.of(
                roadNorth, roadEast, roadSouth, roadWest
        ));
    }

    private static Simulation getSingleLanesSimulation() {
        Road roadNorth = new Road(
                Direction.NORTH,
                List.of(
                        new TrafficLane(all)
                ));
        Road roadEast = new Road(
                Direction.EAST,
                List.of(
                        new TrafficLane(all)
                ));
        Road roadSouth = new Road(
                Direction.SOUTH,
                List.of(
                        new TrafficLane(all)
                ));
        Road roadWest = new Road(
                Direction.WEST,
                List.of(
                        new TrafficLane(all)
                ));

        return new Simulation(List.of(
                roadNorth, roadEast, roadSouth, roadWest
        ));
    }

    private static Simulation getMultipleLanesForMultipleDirectionsSimulation() {
        Road roadNorth = new Road(
                Direction.NORTH,
                List.of(
                        new TrafficLane(right),
                        new TrafficLane(right),
                        new TrafficLane(straight),
                        new TrafficLane(left)
                ));
        Road roadEast = new Road(
                Direction.EAST,
                List.of(
                        new TrafficLane(straightRight),
                        new TrafficLane(straightLeft)
                ));
        Road roadSouth = new Road(
                Direction.SOUTH,
                List.of(
                        new TrafficLane(right),
                        new TrafficLane(straightRight),
                        new TrafficLane(straight),
                        new TrafficLane(left)
                ));
        Road roadWest = new Road(
                Direction.WEST,
                List.of(
                        new TrafficLane(right),
                        new TrafficLane(straight),
                        new TrafficLane(left)
                ));

        return new Simulation(List.of(
                roadNorth, roadEast, roadSouth, roadWest
        ));
    }
}
