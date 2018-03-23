package pl.edu.agh.to2.DreamLogoIDE.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import pl.edu.agh.to2.DreamLogoIDE.command.Command;
import pl.edu.agh.to2.DreamLogoIDE.command.CommandRegistry;
import pl.edu.agh.to2.DreamLogoIDE.drawer.ShapeCanvasDrawer;
import pl.edu.agh.to2.DreamLogoIDE.drawer.ShapeDrawer;
import pl.edu.agh.to2.DreamLogoIDE.drawer.TurtleDrawer;
import pl.edu.agh.to2.DreamLogoIDE.model.Area;
import pl.edu.agh.to2.DreamLogoIDE.model.Position;
import pl.edu.agh.to2.DreamLogoIDE.model.Turtle;
import pl.edu.agh.to2.DreamLogoIDE.parser.CommandParser;
import pl.edu.agh.to2.DreamLogoIDE.parser.CommandParserImp;
import pl.edu.agh.to2.DreamLogoIDE.parser.JsonCommandProvider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.stream.Stream;


public class MainWindowController {
    @FXML
    private Canvas shapesCanvas;

    @FXML
    private Canvas turtleCanvas;

    @FXML
    private ListView<Command> commandHistoryView;

    @FXML
    private TextField commandInputField;

    @FXML
    private TextField errorMessageField;

    @FXML
    private BorderPane mainWindow;

    private CommandParser commmandParser;
    private CommandRegistry commandRegistry;
    private Turtle turtle;
    private ShapeDrawer shapeDrawer;
    private TurtleDrawer turtleDrawer;
    private LogoAppController logoAppController;
    private File currentFile;


    public void initialize() {
        try {
            commmandParser = new CommandParserImp(new JsonCommandProvider("pl.edu.agh.to2.DreamLogoIDE/commands.json"));
        } catch (IOException e) {
            setErrorMessage(e.getMessage());
        }

        commandRegistry = new CommandRegistry();

        turtle = new Turtle(
                new Position(shapesCanvas.getWidth() / 2, shapesCanvas.getHeight() / 2, 270),
                new Area(shapesCanvas.getWidth(), shapesCanvas.getHeight()),
                new Image(getClass().getResource("/pl.edu.agh.to2.DreamLogoIDE/img/turtle.png").toString()));

        shapeDrawer = new ShapeCanvasDrawer(turtle, shapesCanvas);
        turtleDrawer = new TurtleDrawer(turtle, turtleCanvas);

        setCommandHistoryView();
    }


    private void setCommandHistoryView() {
        commandHistoryView.setItems(commandRegistry.getCommandStack());
        commandHistoryView.setCellFactory(lv -> new ListCell<Command>() {
            protected void updateItem(Command item, boolean empty) {
                super.updateItem(item, empty);
                setText((item != null && !empty) ? item.getText() : null);
            }
        });
    }

    public void setLogoAppController(LogoAppController logoAppController) {
        this.logoAppController = logoAppController;
    }

    @FXML
    public void executeCommand() {
        errorMessageField.clear();
        try {
            Command command = commmandParser.getCommand(commandInputField.getText());
            if (command != null)
                commandRegistry.executeCommand(command, turtle, shapeDrawer);
            else
                setErrorMessage("Not ended command");
        } catch (ParseException | IllegalStateException e) {
            setErrorMessage(e.getMessage());
        } finally {
            commandInputField.clear();
        }
    }

    @FXML
    private void handleOpenFileAction(final ActionEvent event) {
        File f = logoAppController.showFileChooserAndReturnFile();
        if (f == null)
            return;

        try {
            Stream<String> stream = Files.lines(Paths.get(f.getAbsolutePath()));
            stream.forEach(s -> executeCommandFile(s));

        } catch (IOException e) {
            setErrorMessage(e.getMessage());
        }
    }

    @FXML
    private void handleSaveFileAction(final ActionEvent event) {
        if(this.currentFile == null)
            this.currentFile = logoAppController.showFileChooserAndSaveFile();
        try {
            FileWriter writer = new FileWriter(currentFile);
            for(Command command : commandRegistry.getCommandStack())
                writer.write(command.getText() + "\n");
            writer.close();

        } catch (Exception e) {
            return;
        }
    }

    @FXML
    private void handleSaveFileAsAction(final ActionEvent event) {
        try {
            this.currentFile = logoAppController.showFileChooserAndSaveFile();
            FileWriter writer = new FileWriter(currentFile);
            for(Command command : commandRegistry.getCommandStack())
                writer.write(command.getText() + "\n");
            writer.close();
        } catch(Exception e) {
            return;
        }
    }

    @FXML
    private void handleNewFileAction(final ActionEvent event) {
        try {
            this.currentFile = logoAppController.showFileChooserAndSaveFile();
            executeCommandFile("cs");
            initialize();
        } catch(Exception e) {
            return;
        }
    }

    public void undoCommand() {
        commandRegistry.undo();
    }

    public void redoCommand() {
        commandRegistry.redo();
    }

    private void setErrorMessage(String text) {
        errorMessageField.setText(text);
    }

    private void executeCommandFile(String cmd) {
        try {
            Command command = commmandParser.getCommand(cmd);
            commandRegistry.executeCommand(command, turtle, shapeDrawer);
        } catch (ParseException | IllegalStateException e) {
            setErrorMessage(e.getMessage());
        }
    }
}
