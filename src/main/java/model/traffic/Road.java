package model.traffic;

import model.enums.Direction;
import model.enums.Lane;
import model.enums.Move;

import java.util.*;

/**
 * Road class represents a road in the traffic simulation.
 * It contains information about the road's direction and the traffic lanes it consists of.
 */
public class Road {
    private final Direction direction;
    private final List<TrafficLane> trafficLaneList;

    public Road(Direction direction, List<TrafficLane> trafficLaneList) {
        this.direction = direction;
        this.trafficLaneList = trafficLaneList;
    }

    /**
     * Gets the direction of the road.
     *
     * @return The direction of the road.
     */
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * Adds a car to the appropriate traffic lane on the road based on the car's move type.
     *
     * @param car The car to be added to the road.
     * @throws IllegalStateException If no traffic lane supports the car's move type.
     */
    public void addCar(Car car) throws IllegalStateException {
        Move carMove = car.lane().getMove();
        TrafficLane choice = trafficLaneList.stream()
                .filter(trafficLane -> trafficLane.supportsMove(carMove))
                .min((trafficLane1, trafficLan2) ->
                        Integer.compare(trafficLane1.size(), trafficLan2.size())
                )
                .orElseThrow(() -> new IllegalStateException(
                        String.format("There is no line that supports %s move", carMove.name())
                ));

        choice.addCar(car);
    }

    /**
     * Creates a mapping of lanes to their corresponding traffic lanes.
     *
     * @return Lanes to List of TrafficLanes mapping
     */
    public Map<Lane, List<TrafficLane>> getLaneMapping() {
        Map<Lane, List<TrafficLane>> laneMapping = new HashMap<>();

        for (TrafficLane trafficLane : this.trafficLaneList) {
            Optional<Lane> optionalLane = trafficLane.currentLaneType();

            if (optionalLane.isEmpty()) {
                continue;
            }

            Lane lane = optionalLane.get();

            if ( ! laneMapping.containsKey(lane)) {
                laneMapping.put(lane, new ArrayList<>());
            }

            laneMapping.get(lane).add(trafficLane);
        }

        return  laneMapping;
    }
}
