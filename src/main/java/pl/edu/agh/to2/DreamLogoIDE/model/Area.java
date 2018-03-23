package pl.edu.agh.to2.DreamLogoIDE.model;

public class Area {
    private final double width;
    private final double height;

    public Area(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public boolean isCorrectPosition(Position position) {
        return ((position.getX() <= width) && (position.getY() <= height) &&
                (position.getX()) >= 0 && (position.getY() >= 0));
    }
}
