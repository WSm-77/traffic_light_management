package model.traffic;

import model.enums.Lane;
import model.enums.Move;

import java.util.*;

public class TrafficLane {
    private final Set<Move> supportedMoves;
    private final Queue<Car> waitingCars = new ArrayDeque<>();

    public TrafficLane(Set<Move> supportedMoves) {
        this.supportedMoves = supportedMoves;
    }

    public boolean supportsMove(Move move) {
        return supportedMoves.contains(move);
    }

    public Optional<Lane> currentLaneType() {
        if (this.waitingCars.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(this.waitingCars.peek().lane());
    }

    public void addCar(Car car) {
        this.waitingCars.offer(car);
    }

    public int size() {
        return this.waitingCars.size();
    }

    public Car moveFirstCar() {
        return this.waitingCars.poll();
    }
}
