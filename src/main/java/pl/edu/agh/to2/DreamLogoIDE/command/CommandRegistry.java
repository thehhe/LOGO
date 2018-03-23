package pl.edu.agh.to2.DreamLogoIDE.command;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.edu.agh.to2.DreamLogoIDE.drawer.ShapeDrawer;
import pl.edu.agh.to2.DreamLogoIDE.model.Turtle;

import java.util.Stack;

public class CommandRegistry {
    private ObservableList<Command> commandStack = FXCollections.observableArrayList();
    private ObservableList<Command> undoCommandStack = FXCollections.observableArrayList();
    private Stack<Turtle> turtleStack = new Stack<>();
    private Stack<Turtle> undoTurtleStack = new Stack<>();
    private Stack<ShapeDrawer> shapeDrawerStack = new Stack<>();
    private Stack<ShapeDrawer> undoShapeDrawerStack = new Stack<>();

    public void executeCommand(Command command, Turtle turtle, ShapeDrawer shapeDrawer) throws IllegalStateException {
        command.execute(turtle, shapeDrawer);

        commandStack.add(command);
        turtleStack.add(turtle);
        shapeDrawerStack.add(shapeDrawer);

        undoCommandStack.clear();
        undoTurtleStack.clear();
        undoShapeDrawerStack.clear();
    }

    public void redo() {
        if (undoCommandStack.isEmpty())
            return;

        Command lastUndoCommand = undoCommandStack.remove(undoCommandStack.size() - 1);
        lastUndoCommand.redo(undoTurtleStack.peek(), undoShapeDrawerStack.peek());
        commandStack.add(lastUndoCommand);
        turtleStack.push(undoTurtleStack.pop());
        shapeDrawerStack.push(undoShapeDrawerStack.pop());
    }

    public void undo() {
        if (commandStack.isEmpty())
            return;

        Command lastCommand = commandStack.remove(commandStack.size() - 1);
        lastCommand.undo(turtleStack.peek(), shapeDrawerStack.peek());
        undoCommandStack.add(lastCommand);
        undoTurtleStack.push(turtleStack.pop());
        undoShapeDrawerStack.push(shapeDrawerStack.pop());
    }

    public ObservableList<Command> getCommandStack() {
        return commandStack;
    }
}
