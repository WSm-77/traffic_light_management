package model.traffic;

import model.enums.Lane;
import model.enums.Move;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TrafficLaneTest {

    @Test
    void supportsMove() {
        TrafficLane trafficLane = new TrafficLane(Set.of(Move.LEFT, Move.RIGHT));
        assertTrue(trafficLane.supportsMove(Move.LEFT));
        assertTrue(trafficLane.supportsMove(Move.RIGHT));
        assertFalse(trafficLane.supportsMove(Move.STRAIGHT));
    }

    @Test
    void currentLaneType_ReturnsLaneTypeOfFirstCar() {
        Car car = new Car("", Lane.NORTH_STRAIGHT);

        TrafficLane trafficLane = new TrafficLane(Set.of(Move.STRAIGHT));
        trafficLane.addCar(car);

        assertEquals(Optional.of(Lane.NORTH_STRAIGHT), trafficLane.currentLaneType());
    }

    @Test
    void currentLaneType_ReturnsEmptyWhenNoCars() {
        TrafficLane trafficLane = new TrafficLane(Set.of(Move.STRAIGHT));

        assertEquals(Optional.empty(), trafficLane.currentLaneType());
    }

    @Test
    void addCar_IncreasesQueueSize() {
        TrafficLane trafficLane = new TrafficLane(Set.of(Move.STRAIGHT));
        Car car = new Car("", Lane.NORTH_STRAIGHT);

        trafficLane.addCar(car);

        assertEquals(1, trafficLane.size());
    }

    @Test
    void moveFirstCar_RemovesAndReturnsFirstCar() {
        Car car1 = new Car("", Lane.NORTH_STRAIGHT);
        Car car2 = new Car("", Lane.NORTH_STRAIGHT);

        TrafficLane trafficLane = new TrafficLane(Set.of(Move.STRAIGHT));
        trafficLane.addCar(car1);
        trafficLane.addCar(car2);

        assertEquals(car1, trafficLane.moveFirstCar());
        assertEquals(1, trafficLane.size());
    }

    @Test
    void moveFirstCar_ReturnsNullWhenQueueIsEmpty() {
        TrafficLane trafficLane = new TrafficLane(Set.of(Move.STRAIGHT));
        assertThrows(
                IllegalStateException.class,
                () -> trafficLane.moveFirstCar()
        );
    }
}
