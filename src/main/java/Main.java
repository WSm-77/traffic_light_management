import model.input.InputReader;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println(String.format("Invalid number of arguments: %d!!!", args.length));
            System.out.println("Please provide name of input file and output file");

            return;
        }

        Optional<Map<String, List<Map<String, String>>>> optionalInput = InputReader.readJsonCommands(args[0]);

        if (optionalInput.isEmpty()) {
            return;
        }

        Map<String, List<Map<String, String>>> commands = optionalInput.get();
        List<Map<String, String>> commandsList = commands.get("commands");

        System.out.println(commands.get("commands"));

        for (Map<String, String> command : commandsList) {
            System.out.println(command.get("type"));
        }
    }


}
