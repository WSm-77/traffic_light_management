package model.output;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.simulation.SimulationObserver;
import model.traffic.Car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputCollector implements SimulationObserver {
    private final List<Map<String, List<String>>> stepStatuses = new ArrayList<>();

    @Override
    public void notifySimulationStep(List<Car> carsLeavingCrossing) {
        List<String> listOfLeavingCarsNames = this.getListOfLeavingCarsNames(carsLeavingCrossing);
        Map<String, List<String>> stepMap = this.createStepMap(listOfLeavingCarsNames);

        this.stepStatuses.add(stepMap);
    }

    private List<String> getListOfLeavingCarsNames(List<Car> carsLeavingCrossing) {
        return carsLeavingCrossing.stream()
                .map(Car::name)
                .toList();
    }

    private Map<String, List<String>> createStepMap(List<String> listOfLeavingCarsNames) {
        HashMap<String, List<String>> stepMap = new HashMap<>();
        stepMap.put("leftVehicles", listOfLeavingCarsNames);

        return stepMap;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();

        return gson.toJson(this);
    }
}
