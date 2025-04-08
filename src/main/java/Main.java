import app.TrafficLightManager;
import model.enums.Direction;
import model.enums.Move;
import model.input.InputReader;
import model.output.OutputCollector;
import model.simulation.Simulation;
import model.simulation.SimulationRunner;
import model.traffic.Road;
import model.traffic.TrafficLane;

import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println(String.format("Invalid number of arguments: %d!!!", args.length));
            System.out.println("Please provide name of input file and output file");

            return;
        }

        Simulation singleLanesSimulation = getSingleLanesSimulation();
        TrafficLightManager.runManager(args[0], args[1], singleLanesSimulation);
    }

    private static Simulation getSingleLanesSimulation() {
        Set<Move> allMoves = Set.of(Move.RIGHT, Move.STRAIGHT, Move.LEFT);

        Road roadNorth = new Road(
                Direction.NORTH,
                List.of(
                        new TrafficLane(allMoves)
                ));
        Road roadEast = new Road(
                Direction.EAST,
                List.of(
                        new TrafficLane(allMoves)
                ));
        Road roadSouth = new Road(
                Direction.SOUTH,
                List.of(
                        new TrafficLane(allMoves)
                ));
        Road roadWest = new Road(
                Direction.WEST,
                List.of(
                        new TrafficLane(allMoves)
                ));

        return new Simulation(List.of(
                roadNorth, roadEast, roadSouth, roadWest
        ));
    }
}
