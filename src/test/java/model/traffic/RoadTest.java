package model.traffic;

import model.enums.Direction;
import model.enums.Lane;
import model.enums.Move;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoadTest {
    private static final Set<Move> ALL = Set.of(Move.RIGHT, Move.STRAIGHT, Move.LEFT);

    @Test
    void addCar_AddsCarToCorrectTrafficLane() {
        TrafficLane lane1 = new TrafficLane(ALL);
        TrafficLane lane2 = new TrafficLane(ALL);
        Car car = new Car("", Lane.SOUTH_LEFT);

        Road road = new Road(Direction.SOUTH, List.of(lane1, lane2));
        road.addCar(car);

        assertEquals(1, lane1.size());
        assertEquals(0, lane2.size());
    }

    @Test
    void addCar_ThrowsExceptionWhenNoLaneSupportsMove() {
        Move expectedCarMove = Move.RIGHT;
        TrafficLane lane1 = new TrafficLane(Set.of(Move.STRAIGHT));
        TrafficLane lane2 = new TrafficLane(Set.of(Move.STRAIGHT));
        Car car = new Car("", Lane.stringToLane("south", "east"));

        Road road = new Road(Direction.SOUTH, List.of(lane1, lane2));

        assertEquals(expectedCarMove, car.lane().getMove());
        assertFalse(lane1.supportsMove(expectedCarMove));
        assertFalse(lane2.supportsMove(expectedCarMove));
        assertThrows(
                IllegalStateException.class,
                () -> road.addCar(car)
        );
    }

    @Test
    void getLaneMapping_ReturnsCorrectMapping() {
        TrafficLane lane1 = new TrafficLane(Set.of(Move.STRAIGHT));
        TrafficLane lane2 = new TrafficLane(Set.of(Move.STRAIGHT));
        TrafficLane lane3 = new TrafficLane(Set.of(Move.LEFT));

        Car car1 = new Car("", Lane.SOUTH_STRAIGHT);
        Car car2 = new Car("", Lane.SOUTH_STRAIGHT);
        Car car3 = new Car("", Lane.SOUTH_LEFT);

        Road road = new Road(Direction.SOUTH, List.of(lane1, lane2, lane3));
        road.addCar(car1);
        road.addCar(car2);
        road.addCar(car3);
        Map<Lane, List<TrafficLane>> laneMapping = road.getLaneMapping();

        assertEquals(1, lane1.size());
        assertEquals(1, lane2.size());
        assertEquals(1, lane3.size());
        assertEquals(2, laneMapping.size());
        assertTrue(laneMapping.get(Lane.SOUTH_STRAIGHT).containsAll(List.of(lane1, lane2)));
        assertTrue(laneMapping.get(Lane.SOUTH_LEFT).contains(lane3));
    }

    @Test
    void getLaneMapping_ExcludesLanesWithoutCurrentLaneType() {
        TrafficLane lane1 = new TrafficLane(Set.of(Move.RIGHT));
        TrafficLane lane2 = new TrafficLane(Set.of(Move.STRAIGHT));
        Car car = new Car("", Lane.SOUTH_RIGHT);

        Road road = new Road(Direction.SOUTH, List.of(lane1, lane2));
        road.addCar(car);
        Map<Lane, List<TrafficLane>> laneMapping = road.getLaneMapping();

        assertEquals(1, laneMapping.size());
        assertTrue(laneMapping.get(Lane.SOUTH_RIGHT).contains(lane1));
    }
}
