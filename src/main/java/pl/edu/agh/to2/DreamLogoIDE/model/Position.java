package pl.edu.agh.to2.DreamLogoIDE.model;

import static java.lang.Math.*;

public class Position {
    private final double X;
    private final double Y;
    private final double ROTATION;

    public Position() {
        X = 0.0;
        Y = 0.0;
        ROTATION = 90.0;
    }

    public Position(double x, double y, double rotation) {
        this.X = x;
        this.Y = y;
        this.ROTATION = rotation;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getRotation() {
        return ROTATION;
    }

    public Position addDistance(double distance) {
        double newX = X + distance * cos(toRadians(ROTATION));
        double newY = Y + distance * sin(toRadians(ROTATION));

        return new Position(newX, newY, ROTATION);
    }

    public Position addRotation(double angle) {
        double newRotation = ROTATION + angle;

        return new Position(X, Y, newRotation);
    }

}
