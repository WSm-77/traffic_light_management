package model.output;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.simulation.SimulationObserver;
import model.traffic.Car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class collects vehicles that left crossing after each step of simulation.
 * Output is combined into single map that can be easily converted into json format by calling toString() method.
 * To use <b>OutputCollector</b> remember to subscribe it to simulation instance.
 *
 * <p>Example</p>
 * <pre>
 *     Simulation simulation = new Simulation(...);
 *     OutputCollector collector = new OutputCollector();
 *
 *     simulation.subscribe(collector);
 *     System.out.println(collector);   // prints data in json file structure
 * </pre>
 */
public class OutputCollector implements SimulationObserver {
    private static final String LEFT_VEHICLES = "leftVehicles";
    private final List<Map<String, List<String>>> stepStatuses = new ArrayList<>();

    @Override
    public void notifySimulationStep(List<Car> carsLeavingCrossing) {
        List<String> listOfLeavingCarsNames = this.getListOfLeavingCarsNames(carsLeavingCrossing);
        Map<String, List<String>> stepMap = this.createStepMap(listOfLeavingCarsNames);

        this.stepStatuses.add(stepMap);
    }

    @Override
    public void notifyAddVehicle(Car vehicle) {
        /* empty */
    }

    private List<String> getListOfLeavingCarsNames(List<Car> carsLeavingCrossing) {
        return carsLeavingCrossing.stream()
                .map(Car::name)
                .toList();
    }

    private Map<String, List<String>> createStepMap(List<String> listOfLeavingCarsNames) {
        HashMap<String, List<String>> stepMap = new HashMap<>();
        stepMap.put(LEFT_VEHICLES, listOfLeavingCarsNames);

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
