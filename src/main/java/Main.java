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

        Optional<Map<String, List<Map<String, String>>>> optionalInput = InputReader.readJsonCommands(args[0]);

        if (optionalInput.isEmpty()) {
            return;
        }

        Map<String, List<Map<String, String>>> commands = optionalInput.get();
        List<Map<String, String>> commandsList = commands.get("commands");

        if (commandsList == null) {
            System.out.println("Invalid input!!!");
            return;
        }

        OutputCollector outputCollector = new OutputCollector();
        Simulation singleLanesSimulation = getSingleLanesSimulation();
        singleLanesSimulation.subscribe(outputCollector);

        SimulationRunner simulationRunner = new SimulationRunner(singleLanesSimulation);
        simulationRunner.runSimulation(commandsList);

        String pathToOutputFile = args[1];

        try (FileWriter outputFile = new FileWriter(pathToOutputFile)) {
            outputFile.write(outputCollector.toString());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static Simulation getSingleLanesSimulation() {
        Set<Move> all = Set.of(Move.RIGHT, Move.STRAIGHT, Move.LEFT);

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
}
