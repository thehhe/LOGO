package pl.edu.agh.to2.DreamLogoIDE.command;

import pl.edu.agh.to2.DreamLogoIDE.drawer.ShapeDrawer;
import pl.edu.agh.to2.DreamLogoIDE.model.Turtle;

import java.text.ParseException;
import java.util.Stack;

public class PuCommand extends Command {
    private Stack<Boolean> prevPenState = new Stack<>();

    public PuCommand(String[] arguments) throws ParseException {
        super(arguments);
    }

    @Override
    public void execute(Turtle turtle, ShapeDrawer shapeDrawer) {
        prevPenState.push(turtle.isPenDown());
        turtle.setPenDown(false);
    }

    @Override
    public void undo(Turtle turtle, ShapeDrawer shapeDrawer) {
        turtle.setPenDown(prevPenState.pop());
    }
}
