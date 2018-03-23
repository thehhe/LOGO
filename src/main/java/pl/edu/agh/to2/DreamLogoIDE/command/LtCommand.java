package pl.edu.agh.to2.DreamLogoIDE.command;

import pl.edu.agh.to2.DreamLogoIDE.drawer.ShapeDrawer;
import pl.edu.agh.to2.DreamLogoIDE.model.Position;
import pl.edu.agh.to2.DreamLogoIDE.model.Turtle;

import java.text.ParseException;
import java.util.Stack;

public class LtCommand extends Command {
    private double angle;
    private Stack<Position> prevPositionsStack = new Stack<>();

    public LtCommand(String[] arguments) throws ParseException {
        super(arguments);
        try {
            angle = Double.parseDouble(arguments[1]);
        } catch (NumberFormatException e) {
            throw new ParseException("Incorrect argument. Argument must be a number.", 0);
        }
    }

    @Override
    public void execute(Turtle turtle, ShapeDrawer shapeDrawer) {
        prevPositionsStack.push(turtle.getPosition());
        turtle.rotate(-angle);
    }

    @Override
    public void undo(Turtle turtle, ShapeDrawer shapeDrawer) {
        turtle.setPosition(prevPositionsStack.pop());
    }
}
