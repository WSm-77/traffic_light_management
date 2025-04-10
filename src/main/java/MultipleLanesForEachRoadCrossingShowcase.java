import api.TrafficLightManager;
import model.enums.Direction;
import model.enums.Move;
import model.simulation.Simulation;
import model.simulation.SimulationLogger;
import model.traffic.Road;
import model.traffic.TrafficLane;

import java.util.List;
import java.util.Set;

public class MultipleLanesForEachRoadCrossingShowcase {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println(String.format("Invalid number of arguments: %d!!!", args.length));
            System.out.println("Please provide name of input file and output file");

            return;
        }

        Simulation singleLanesSimulation = getMultipleLanesForMultipleDirectionsSimulation();

        SimulationLogger simulationLogger = new SimulationLogger(singleLanesSimulation);
        singleLanesSimulation.subscribe(simulationLogger);

        TrafficLightManager.runManager(args[0], args[1], singleLanesSimulation);
    }

    private static Simulation getMultipleLanesForMultipleDirectionsSimulation() {
        Set<Move> left = Set.of(Move.LEFT);
        Set<Move> straight = Set.of(Move.STRAIGHT);
        Set<Move> right = Set.of(Move.RIGHT);
        Set<Move> straightRight = Set.of(Move.STRAIGHT, Move.RIGHT);
        Set<Move> straightLeft = Set.of(Move.STRAIGHT, Move.LEFT);

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
