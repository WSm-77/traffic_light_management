package model.traffic;

import model.enums.Direction;
import model.enums.Lane;

import java.util.*;

public class Road {
    private final Direction direction;
    private final List<TrafficLane> trafficLaneList;

    public Road(Direction direction, List<TrafficLane> trafficLaneList) {
        this.direction = direction;
        this.trafficLaneList = trafficLaneList;
    }

    public void addTrafficLane(TrafficLane trafficLane) {
        this.trafficLaneList.add(trafficLane);
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void addCar(Car car) {
        TrafficLane choice = trafficLaneList.stream()
                .filter(trafficLane -> trafficLane.supportsMove(car.lane().getMove()))
                .min((trafficLane1, trafficLan2) ->
                        Integer.compare(trafficLane1.size(), trafficLan2.size()))
                .orElseThrow();

        choice.addCar(car);
    }

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
