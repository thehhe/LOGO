package pl.edu.agh.to2.DreamLogoIDE.command;

import pl.edu.agh.to2.DreamLogoIDE.drawer.ShapeDrawer;
import pl.edu.agh.to2.DreamLogoIDE.model.Turtle;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class RepeatCommand extends Command {
    private int n;
    private List<Command> commands = new ArrayList<>();

    public RepeatCommand(String[] arguments) throws ParseException {
        super(arguments);

        try {
            n = Integer.valueOf(arguments[1]);
        } catch (NumberFormatException e) {
            throw new ParseException("Number of iterations must be an INTEGER", 0);
        }
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    @Override
    public String getText() {
        StringBuilder builder = new StringBuilder();
        for (String arg : arguments)
            builder.append(arg.toUpperCase()).append(" ");
        builder.append("[ ");
        for (Command command : commands)
            builder.append(command.getText());
        builder.append("]");

        return builder.toString();
    }

    @Override
    public void execute(Turtle turtle, ShapeDrawer shapeDrawer) {
        for (int i = 0; i < n; i++)
            commands.forEach(c -> c.execute(turtle, shapeDrawer));
    }

    @Override
    public void undo(Turtle turtle, ShapeDrawer shapeDrawer) {
        for (int i = 0; i < n; i++)
            for (int j = commands.size() - 1; j >= 0; j--)
                commands.get(j).undo(turtle, shapeDrawer);
    }
}
