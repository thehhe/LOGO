package pl.edu.agh.to2.DreamLogoIDE.model;

import javafx.beans.property.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Turtle {
    private ObjectProperty<Position> position = new SimpleObjectProperty<>();
    private Area area;
    private Image icon;
    private BooleanProperty hidden = new SimpleBooleanProperty(false);
    private boolean penDown = true;
    private DoubleProperty penSize = new SimpleDoubleProperty(1.0);
    private ObjectProperty<Color> penColor = new SimpleObjectProperty<>(Color.BLACK);
    private Position initialPosition;

    public Turtle(Position position, Area area, Image icon) {
        setPosition(position);
        setInitialPosition(position);
        this.area = area;
        this.icon = icon;
    }

    public Position getInitialPosition() {
        return initialPosition;
    }

    private void setInitialPosition(Position initialPosition) {
        this.initialPosition = initialPosition;
    }

    public Position getPosition() {
        return position.getValue();
    }

    public void setPosition(Position position) {
        this.position.setValue(position);
    }

    public ObjectProperty<Position> positionProperty() {
        return position;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Image getIcon() {
        return icon;
    }

    public boolean isHidden() {
        return hidden.getValue();
    }

    public void setHidden(boolean hidden) {
        this.hidden.setValue(hidden);
    }

    public BooleanProperty isHiddenProperty() {
        return hidden;
    }

    public boolean isPenDown() {
        return penDown;
    }

    public void setPenDown(boolean penDown) {
        this.penDown = penDown;
    }

    public Color getPenColor() {
        return penColor.getValue();
    }

    public void setPenColor(Color penColor) {
        this.penColor.setValue(penColor);
    }

    public ObjectProperty<Color> penColorProperty() {
        return penColor;
    }

    public double getPenSize() {
        return penSize.getValue();
    }

    public void setPenSize(double penSize) {
        this.penSize.setValue(penSize);
    }

    public DoubleProperty penSizeProperty() {
        return penSize;
    }

    public void move(double distance) throws IllegalStateException {
        Position newPosition = getPosition().addDistance(distance);
        if (!area.isCorrectPosition(newPosition))
            throw new IllegalStateException("New position is outside the area");
        setPosition(newPosition);
    }

    public void rotate(double angle) {
        setPosition(getPosition().addRotation(angle));
    }


}
