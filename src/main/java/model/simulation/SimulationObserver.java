package model.simulation;

import model.traffic.Car;

import java.util.List;

public interface SimulationObserver {
    void notifySimulationStep(List<Car> carsLeavingCrossing);
}
