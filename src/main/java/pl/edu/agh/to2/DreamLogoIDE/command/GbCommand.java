package pl.edu.agh.to2.DreamLogoIDE.command;

import pl.edu.agh.to2.DreamLogoIDE.drawer.ShapeDrawer;
import pl.edu.agh.to2.DreamLogoIDE.model.Position;
import pl.edu.agh.to2.DreamLogoIDE.model.Turtle;

import java.text.ParseException;
import java.util.Stack;

public class GbCommand extends Command {
    private Stack<Position> prevPositionsStack = new Stack<>();

    public GbCommand(String[] arguments) throws ParseException {
        super(arguments);
    }

    @Override
    public void execute(Turtle turtle, ShapeDrawer shapeDrawer) {
        prevPositionsStack.push(turtle.getPosition());
        turtle.setPosition(turtle.getInitialPosition());
    }

    @Override
    public void undo(Turtle turtle, ShapeDrawer shapeDrawer) {
        turtle.setPosition(prevPositionsStack.pop());
    }
}
