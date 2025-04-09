package model.traffic;

import model.enums.Lane;
import model.enums.Move;

import java.util.*;

/**
 * TrafficLane class represents a lane in traffic that supports specific moves and manages a queue of cars waiting
 * for green light.
 */
public class TrafficLane {
    private final Set<Move> supportedMoves;
    private final Queue<Car> waitingCars = new ArrayDeque<>();

    public TrafficLane(Set<Move> supportedMoves) {
        this.supportedMoves = supportedMoves;
    }

    /**
     * Checks if the traffic lane supports a specific move.
     *
     * @param move The move to check.
     * @return `true` if the move is supported, otherwise `false`.
     */
    public boolean supportsMove(Move move) {
        return supportedMoves.contains(move);
    }

    /**
     * Retrieves the type of lane currently being served for first car in the queue.
     *
     * @return An `Optional` containing the lane type if cars are waiting, otherwise an empty `Optional`.
     */
    public Optional<Lane> currentLaneType() {
        if (this.waitingCars.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(this.waitingCars.peek().lane());
    }

    /**
     * Adds a car to the queue of waiting cars in the traffic lane.
     *
     * @param car The car to add to the queue.
     */
    public void addCar(Car car) {
        this.waitingCars.offer(car);
    }

    /**
     * Returns the number of cars currently waiting in the traffic lane.
     *
     * @return The size of the queue of waiting cars.
     */
    public int size() {
        return this.waitingCars.size();
    }

    /**
     * Removes and returns the first car in the queue of waiting cars.
     *
     * @return The first car in the queue.
     * @throws IllegalStateException If there are no cars waiting in the queue.
     */
    public Car moveFirstCar() throws IllegalStateException {
        if (this.waitingCars.isEmpty()) {
            throw new IllegalStateException("moveFirstCar() called when no cars waiting!!!");
        }

        return this.waitingCars.poll();
    }
}
