package model.simulation;

import model.enums.Lane;
import model.traffic.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * SimulationRunner class serves a role of tool for controlling simulation behaviour with text commands.
 * This tool is used to convert text commands to correct simulation actions.
 */
public class SimulationRunner {
    private static final String COMMAND_TYPE = "type";
    private static final String ADD_VEHICLE = "addVehicle";
    private static final String STEP = "step";

    private final Simulation simulation;

    public SimulationRunner(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Runs simulation.
     *
     * @param commands Text commands that control simulation behaviour
     * @throws IllegalArgumentException If encounters unknown or incorrectly structured command
     */
    public void runSimulation(List<Map<String, String>> commands) throws IllegalArgumentException {
        for (Map<String, String> command : commands) {
            String commandType = command.get(COMMAND_TYPE);

            switch (commandType) {
                case ADD_VEHICLE -> {
                    Optional<Car> optionalCar = this.mapToCar(command);

                    if (optionalCar.isEmpty()) {
                        throw new IllegalArgumentException(
                                String.format("Wrong command structure: %s", command)
                        );
                    }

                    this.simulation.addVehicle(optionalCar.get());
                }
                case STEP -> this.simulation.step();
                default -> throw new IllegalArgumentException(String.format("Unknown command type: %s", commandType));
            }
        }
    }

    /**
     * Creates car object based on the provided command parameters.
     *
     * @param command Map that contain command with all its parameters
     * @return An `Optional` containing the car if correct command is provided, otherwise an empty `Optional`.
     */
    private Optional<Car> mapToCar(Map<String, String> command) {
        String vehicleName = command.get("vehicleId");

        if (vehicleName == null) {
            return Optional.empty();
        }

        String startRoad = command.get("startRoad");
        String endRoad = command.get("endRoad");
        Lane lane;

        try {
            lane = Lane.stringToLane(startRoad, endRoad);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }

        return Optional.of(new Car(vehicleName, lane));
    }
}
