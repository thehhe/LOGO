package pl.edu.agh.to2.DreamLogoIDE.command;

import pl.edu.agh.to2.DreamLogoIDE.drawer.ShapeDrawer;
import pl.edu.agh.to2.DreamLogoIDE.model.Turtle;

import java.text.ParseException;
import java.util.*;

public class ProcedureDefinitionCommand extends Command {
    private List<String> commands = new ArrayList<>();
    private String name;
    private List<String> args = new ArrayList<>();
    private boolean isDefined = false;
    private Set<String> localVariablesKeywords = new HashSet<>(Arrays.asList("make", "niech"));
    private Map<String, String> localVariables = new HashMap<>();
    private Map<String, Integer> localVariablesStartLine = new HashMap<>();

    public ProcedureDefinitionCommand(String[] arguments) throws ParseException {
        super(arguments);
        name = arguments[1];
    }

    @Override
    public void execute(Turtle turtle, ShapeDrawer shapeDrawer) {
        isDefined = true;
    }

    @Override
    public void undo(Turtle turtle, ShapeDrawer shapeDrawer) {
        isDefined = false;
    }

    public void addTextLine(String line) throws ParseException {
        for (String var : localVariablesKeywords)
            if (line.startsWith(var)) {
                addLocalVariable(line);
                return;
            }

        commands.add(line);
    }

    public String getName() {
        return name;
    }

    public int getCommandArgumentsNumber() {
        return args.size();
    }

    public void setCommandArgs(String[] arguments) throws ParseException {
        for (String arg : arguments) {
            if (!arg.startsWith(":"))
                throw new ParseException("Procedure arguments must start with ':'", 0);
            args.add(arg);
        }
    }

    public boolean isDefined() {
        return isDefined;
    }

    private void addLocalVariable(String line) throws ParseException {
        String[] splitted = line.split("\\s+");
        if (splitted.length != 3 || !splitted[1].startsWith("\""))
            throw new ParseException("Incorrect local varible definition, correct: KEYWORD \"NAME VALUE", 0);

        String localVarName = splitted[1].replace("\"", ":").toLowerCase();
        localVariables.put(localVarName, splitted[2]);
        localVariablesStartLine.put(localVarName, commands.size());
    }

    public Iterable<String> getProcedure(String... values) throws ParseException {
        if (values.length != args.size())
            throw new ParseException("Invalid number of arguments", 0);

        List<String> commandsCopy = new ArrayList<>(commands);

        for (int i = 0; i < commandsCopy.size(); i++) {
            String line = commands.get(i);
            if (line.contains(":"))
                for (String arg : args) {
                    commands.set(i, line.replace(arg, values[i]));
                }
            for (String var : localVariables.keySet()) {
                if (localVariablesStartLine.get(var) >= i)
                    commands.set(i, line.replace(var, values[i]));
            }
        }
        return commandsCopy;
    }
}
