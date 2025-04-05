package model.input;

import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;

public class InputReader {
    public static Optional<Map<String, List<Map<String, String>>>> readJsonCommands(String pathToJson) {
        Gson gson = new Gson();
        Map<String, List<Map<String, String>>> map = null;

        try (Reader reader = new FileReader(pathToJson)) {
            Type type = new TypeToken<Map<String, List<Map<String, String>>>>(){}.getType();

            map = gson.fromJson(reader, type);
        } catch (Exception e) {
            System.out.println("Error while commands from json file!!!");
            System.out.println("Error message: " + e.getMessage());
        }

        return Optional.ofNullable(map);
    }
}
