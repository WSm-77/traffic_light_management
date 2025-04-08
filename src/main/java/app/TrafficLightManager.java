package app;

import model.input.InputReader;
import model.output.OutputCollector;
import model.simulation.Simulation;
import model.simulation.SimulationRunner;

import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TrafficLightManager {
    private static final String COMMANDS = "commands";

    public static void runManager(String pathToInputFile, String pathToOutputFile, Simulation simulation) {
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

        OutputCollector outputCollector = new OutputCollector();
        simulation.subscribe(outputCollector);

        SimulationRunner simulationRunner = new SimulationRunner(simulation);
        simulationRunner.runSimulation(commandsList);

        try (FileWriter outputFile = new FileWriter(pathToOutputFile)) {
            outputFile.write(outputCollector.toString());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
