package pl.edu.agh.to2.DreamLogoIDE.drawer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import pl.edu.agh.to2.DreamLogoIDE.model.Position;
import pl.edu.agh.to2.DreamLogoIDE.model.Turtle;

import java.util.Stack;

public class ShapeCanvasDrawer implements ShapeDrawer {
    private Turtle turtle;
    private Canvas currentCanvas;
    private GraphicsContext graphicsContext;
    private Stack<WritableImage> prevSnapshots = new Stack<>();
    private boolean drawingHistory = true;

    public ShapeCanvasDrawer(Turtle turtle, Canvas canvas) {
        this.turtle = turtle;
        currentCanvas = canvas;
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setStroke(turtle.getPenColor());
        graphicsContext.setLineWidth(turtle.getPenSize());

        turtle.penColorProperty().addListener(penColorChangeListener());
        turtle.penSizeProperty().addListener(penSizeChangeListener());
    }

    public void undoDrawing() {
        graphicsContext.drawImage(prevSnapshots.pop(), 0.0, 0.0);
    }

    public void clearCanvas() {
        saveSnapshot();
        graphicsContext.clearRect(0, 0, currentCanvas.getWidth(), currentCanvas.getHeight());
    }

    public void drawLine(Position a, Position b) {
        if (!turtle.isPenDown())
            return;

        saveSnapshot();
        graphicsContext.strokeLine(a.getX(), a.getY(), b.getX(), b.getY());
    }

    public void drawCircle(Position a, double r) {
        if (!turtle.isPenDown())
            return;

        saveSnapshot();
        graphicsContext.strokeOval(a.getX(), a.getY(), r, r);
    }

    public void setDrawingHistory(boolean enable) {
        drawingHistory = enable;
    }

    private void saveSnapshot() {
        if (drawingHistory)
            prevSnapshots.push(currentCanvas.snapshot(new SnapshotParameters(), null));
    }

    private ChangeListener<Color> penColorChangeListener() {
        return new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                graphicsContext.setStroke(newValue);
            }
        };
    }

    private ChangeListener<Number> penSizeChangeListener() {
        return new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                graphicsContext.setLineWidth((Double) newValue);
            }
        };
    }
}

