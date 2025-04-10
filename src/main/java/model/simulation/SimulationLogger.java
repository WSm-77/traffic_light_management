package model.simulation;

import model.enums.Lane;
import model.traffic.Car;
import model.traffic.Road;
import model.traffic.TrafficLane;

import java.util.Comparator;
import java.util.List;

/**
 * SimulationLogger serves a role of diagnostic tool that allows simulation tracking. After each change in simulation
 * SimulationLogger will display current simulation status to the console. SimulationLogger lists all roads and for
 * each road it creates lanes visualization with information about these lanes current mode and size.
 *
 * <p>Example</p>
 *
 * Output:
 *
 * <pre>
 *      Road: SOUTH
 *
 *      | ---: 0 | SOUTH_STRAIGHT: 1 | SOUTH_RIGHT: 1 | SOUTH_RIGHT: 3 |
 * </pre>
 *
 * means that there is no car in the first lane, one car goes straight, one car turns right from lane with one car and
 * one car turns right from lane with 3 cars.
 *
 * <p>CAUTION!!!</p>
 *
 * SOUTH_RIGHT on the last lane indicates only that the first car turns right. It does not mean that all cars waiting
 * in this lane will turn right, as this lane can support multiple maneuvers.
 */
public class SimulationLogger implements SimulationObserver {
    private final Simulation simulation;

    public SimulationLogger(Simulation simulation) {
        this.simulation = simulation;
    }

    public void displayRoadStates() {
        System.out.println();
        System.out.println("CURRENT ROAD STATES");
        System.out.println();

        for (Road road : this.simulation.getRoadsMap().values()) {
            System.out.println("\nRoad: " + road.getDirection().name());
            System.out.println();

            List<TrafficLane> lanes = road.getTrafficLaneList().stream()
                    .sorted(Comparator.comparingInt(
                            (TrafficLane lane) -> lane.currentLaneType()
                                    .map(Lane::ordinal)
                                    .orElse(Integer.MAX_VALUE)
                        ).reversed()
                    )
                    .toList();

            System.out.print("|");

            for (TrafficLane lane : lanes) {
                String laneStrRepr = lane.currentLaneType().map(Lane::name).orElse("---");
                System.out.print(String.format(" %s: %d |", laneStrRepr, lane.size()));
            }
            System.out.println();
        }
        System.out.println("\n==================================\n");
    }

    @Override
    public void notifySimulationStep(List<Car> carsLeavingCrossing) {
        System.out.println("==================================");
        System.out.println("SIMULATION STEP");
        System.out.println("==================================");

        this.displayRoadStates();
    }

    @Override
    public void notifyAddVehicle(Car car) {
        System.out.println("==================================");
        System.out.println("ADD VEHICLE");
        System.out.println("==================================");
        System.out.println();
        System.out.println("Added Vehicle:");
        System.out.println(car);

        this.displayRoadStates();
    }
}
