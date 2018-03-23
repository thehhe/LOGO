package pl.edu.agh.to2.DreamLogoIDE.command;

import pl.edu.agh.to2.DreamLogoIDE.drawer.ShapeDrawer;
import pl.edu.agh.to2.DreamLogoIDE.model.Turtle;

import java.text.ParseException;

public abstract class Command {
    protected final String[] arguments;

    public Command(String[] arguments) throws ParseException {
        this.arguments = arguments;
    }

    public String getText() {
        StringBuilder builder = new StringBuilder();
        for (String arg : arguments)
            builder.append(arg.toUpperCase()).append(" ");
        return builder.toString();
    }

    public abstract void execute(Turtle turtle, ShapeDrawer shapeDrawer);

    public abstract void undo(Turtle turtle, ShapeDrawer shapeDrawer);

    public void redo(Turtle turtle, ShapeDrawer shapeDrawer) {
        execute(turtle, shapeDrawer);
    }
}
