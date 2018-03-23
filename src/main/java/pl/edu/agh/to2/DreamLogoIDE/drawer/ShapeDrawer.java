package pl.edu.agh.to2.DreamLogoIDE.drawer;

import pl.edu.agh.to2.DreamLogoIDE.model.Position;

public interface ShapeDrawer {
    void undoDrawing();

    void clearCanvas();

    void drawLine(Position a, Position b);

    void drawCircle(Position a, double r);

    void setDrawingHistory(boolean enable);
}
