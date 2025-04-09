package model.simulation;

import model.traffic.Car;

import java.util.List;

public interface SimulationObserver {
    /**
     * Notify subscriber about cars that leave crossing during current simulation step.
     *
     * @param carsLeavingCrossing List of cars that leave crossing during current simulation step
     */
    void notifySimulationStep(List<Car> carsLeavingCrossing);
}
