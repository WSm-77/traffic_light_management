package app;

import model.input.InputReader;
import model.output.OutputCollector;
import model.simulation.Simulation;
import model.simulation.SimulationRunner;

import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The `TrafficLightManager` class is responsible for managing the simulation of traffic lights.
 * It reads input commands from a file, executes the simulation, and writes the output to a file.
 */
public class TrafficLightManager {
    private static final String COMMANDS = "commands";

    /**
     * Runs the traffic light manager by reading input commands, executing the simulation, and writing the output.
     *
     * @param pathToInputFile The path to the input file containing simulation commands in JSON format.
     * @param pathToOutputFile The path to the output file where simulation results will be written.
     * @param simulation The simulation object that will be controlled by the commands.
     */
    public static void runManager(String pathToInputFile, String pathToOutputFile, Simulation simulation) {
        // Read commands
        Optional<Map<String, List<Map<String, String>>>> optionalInput = InputReader.readJsonCommands(pathToInputFile);

        if (optionalInput.isEmpty()) {
            return;
        }

        Map<String, List<Map<String, String>>> commands = optionalInput.get();
        List<Map<String, String>> commandsList = commands.get(COMMANDS);

        if (commandsList == null) {
            System.out.println("No commands found!!!");
            return;
        }

        // Collect the output of the simulation
        OutputCollector outputCollector = new OutputCollector();
        simulation.subscribe(outputCollector);

        // Run simulation
        SimulationRunner simulationRunner = new SimulationRunner(simulation);
        simulationRunner.runSimulation(commandsList);

        // Write output
        try (FileWriter outputFile = new FileWriter(pathToOutputFile)) {
            outputFile.write(outputCollector.toString());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
