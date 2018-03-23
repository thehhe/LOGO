package pl.edu.agh.to2.DreamLogoIDE.command;

import pl.edu.agh.to2.DreamLogoIDE.drawer.ShapeDrawer;
import pl.edu.agh.to2.DreamLogoIDE.model.Turtle;

import java.text.ParseException;
import java.util.Stack;

public class StCommand extends Command {
    private Stack<Boolean> prevHiddenState = new Stack<>();

    public StCommand(String[] arguments) throws ParseException {
        super(arguments);
    }

    @Override
    public void execute(Turtle turtle, ShapeDrawer shapeDrawer) {
        prevHiddenState.push(turtle.isHidden());
        turtle.setHidden(true);
    }

    @Override
    public void undo(Turtle turtle, ShapeDrawer shapeDrawer) {
        turtle.setHidden(prevHiddenState.pop());
    }
}
