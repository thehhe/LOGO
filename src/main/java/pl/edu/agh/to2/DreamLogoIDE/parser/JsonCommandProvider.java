package pl.edu.agh.to2.DreamLogoIDE.parser;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import pl.edu.agh.to2.DreamLogoIDE.command.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;

public class JsonCommandProvider implements CommandProvider {
    private HashMap<String, CommandData> commandsData = new HashMap<>();

    public JsonCommandProvider(String commandsPath) throws IOException {
        loadCommandsFromJsonFile(commandsPath);
    }

    @Override
    public Command getCommand(String[] args) throws ParseException {
        CommandData commandData = commandsData.get(args[0]);
        if (commandData == null)
            throw new ParseException("Command not found: " + args[0], 0);

        if ((args.length - 1) != commandData.argsNumber)
            throw new ParseException("Incorrect number of arguments. Correct number: " + commandData.argsNumber, 0);

        return getCommandInstance(commandData.className, args);
    }

    private Command getCommandInstance(String className, String[] arguments) throws ParseException {
        try {
            Class<?> clazz = Class.forName(className);
            Constructor<?> constructor = clazz.getConstructor(String[].class);
            return (Command) constructor.newInstance(new Object[]{arguments});
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                ClassNotFoundException | NoSuchMethodException e) {
            throw new ParseException("Not implemented", 0);
        }
    }

    @Override
    public boolean isSupported(String keyword) {
        return commandsData.containsKey(keyword);
    }

    @Override
    public int getCommandArgumentsNumber(String keyword) {
        return commandsData.get(keyword).argsNumber;
    }

    private void loadCommandsFromJsonFile(String path) throws IOException {
        try (InputStream in = getClass().getResourceAsStream("/" + path)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            Gson gson = new Gson();
            JsonReader jsonReader = new JsonReader(br);
            CommandData[] commands = gson.fromJson(jsonReader, CommandData[].class);

            Arrays.stream(commands).forEach(c ->
                    Arrays.stream(c.keywords).forEach(k ->
                            commandsData.put(k, c))
            );
        } catch (IOException e) {
            throw new IOException(path + " file not found.");
        }
    }

    class CommandData {
        private String[] keywords;
        private String className;
        private int argsNumber;

        public void setKeywords(String[] keywords) {
            this.keywords = keywords;
        }

        public void setArgsNumber(int argsNumber) {
            this.argsNumber = argsNumber;
        }

        public void setClassName(String className) {
            this.className = className;
        }
    }
}

