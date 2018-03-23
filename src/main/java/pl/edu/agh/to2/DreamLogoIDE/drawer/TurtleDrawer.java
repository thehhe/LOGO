package pl.edu.agh.to2.DreamLogoIDE.drawer;

import javafx.beans.value.ChangeListener;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import pl.edu.agh.to2.DreamLogoIDE.model.Position;
import pl.edu.agh.to2.DreamLogoIDE.model.Turtle;


public class TurtleDrawer {
    private Turtle turtle;
    private Canvas canvas;
    private GraphicsContext graphicsContext;

    public TurtleDrawer(Turtle turtle, Canvas canvas) {
        this.turtle = turtle;
        this.canvas = canvas;
        graphicsContext = canvas.getGraphicsContext2D();

        turtle.positionProperty().addListener(positionChangeListener());
        turtle.isHiddenProperty().addListener(isHiddenListener());
        drawTurtleIcon();
    }

    private ChangeListener<Position> positionChangeListener() {
        return (observable, oldValue, newValue) -> {
            if (turtle.isHidden())
                return;

            clearCanvas();
            drawTurtleIcon();
        };
    }

    private ChangeListener<Boolean> isHiddenListener() {
        return (observable, oldValue, newValue) -> {
            if (newValue)
                clearCanvas();
            else
                drawTurtleIcon();
        };
    }

    private void clearCanvas() {
        graphicsContext.clearRect(0.0, 0.0, canvas.getWidth(), canvas.getHeight());
    }

    private void drawTurtleIcon() {
        graphicsContext.drawImage(
                rotateImage(turtle.getIcon(), turtle.getPosition().getRotation()),
                getXCenter(),
                getYCenter());
    }

    private double getXCenter() {
        return turtle.getPosition().getX() - turtle.getIcon().getWidth() / 2;
    }

    private double getYCenter() {
        return turtle.getPosition().getY() - turtle.getIcon().getHeight() / 2;
    }

    private Image rotateImage(Image image, double angle) {
        double START_ANGLE = 90;

        ImageView imageView = new ImageView(image);
        imageView.setRotate(angle + START_ANGLE);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return imageView.snapshot(params, null);
    }
}
