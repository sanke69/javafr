package fr.javafx.scene.shape;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import fr.drawer.fx.DrawerFx;
import fr.java.draw.Drawable;
import fr.java.draw.Drawer;
import fr.java.math.geometry.plane.Point2D;

public class ArrowShape extends Shape implements Drawable {

    private final Line line, arrow1, arrow2;

    public ArrowShape() {
        this(new Line(), new Line(), new Line());
    }

    private static final double arrowLength = 20;
    private static final double arrowWidth = 7;

    private ArrowShape(Line line, Line arrow1, Line arrow2) {
        super();
        this.line = line;
        this.arrow1 = arrow1;
        this.arrow2 = arrow2;
        InvalidationListener updater = o -> {
            double ex = getEndX();
            double ey = getEndY();
            double sx = getStartX();
            double sy = getStartY();

            arrow1.setEndX(ex);
            arrow1.setEndY(ey);
            arrow2.setEndX(ex);
            arrow2.setEndY(ey);

            if (ex == sx && ey == sy) {
                // arrow parts of length 0
                arrow1.setStartX(ex);
                arrow1.setStartY(ey);
                arrow2.setStartX(ex);
                arrow2.setStartY(ey);
            } else {
                double factor = arrowLength / Math.hypot(sx-ex, sy-ey);
                double factorO = arrowWidth / Math.hypot(sx-ex, sy-ey);

                // part in direction of main line
                double dx = (sx - ex) * factor;
                double dy = (sy - ey) * factor;

                // part ortogonal to main line
                double ox = (sx - ex) * factorO;
                double oy = (sy - ey) * factorO;

                arrow1.setStartX(ex + dx - oy);
                arrow1.setStartY(ey + dy + ox);
                arrow2.setStartX(ex + dx + oy);
                arrow2.setStartY(ey + dy - ox);
            }
        };

        // add updater to properties
        startXProperty().addListener(updater);
        startYProperty().addListener(updater);
        endXProperty().addListener(updater);
        endYProperty().addListener(updater);
        updater.invalidated(null);
    }

    // start/end properties

    public final void setStart(Point2D _start) {
        line.setStartX(_start.getX());
        line.setStartY(_start.getY());
    }
    public final void setStart(double valueX, double valueY) {
        line.setStartX(valueX);
        line.setStartY(valueY);
    }

    public final void setEnd(Point2D _end) {
        line.setEndX(_end.getX());
        line.setEndY(_end.getY());
    }
    public final void setEnd(double valueX, double valueY) {
        line.setEndX(valueX);
        line.setEndY(valueY);
    }
    
    public final void setStartX(double value) {
        line.setStartX(value);
    }

    public final double getStartX() {
        return line.getStartX();
    }

    public final DoubleProperty startXProperty() {
        return line.startXProperty();
    }

    public final void setStartY(double value) {
        line.setStartY(value);
    }

    public final double getStartY() {
        return line.getStartY();
    }

    public final DoubleProperty startYProperty() {
        return line.startYProperty();
    }

    public final void setEndX(double value) {
        line.setEndX(value);
    }

    public final double getEndX() {
        return line.getEndX();
    }

    public final DoubleProperty endXProperty() {
        return line.endXProperty();
    }

    public final void setEndY(double value) {
        line.setEndY(value);
    }

    public final double getEndY() {
        return line.getEndY();
    }

    public final DoubleProperty endYProperty() {
        return line.endYProperty();
    }

//	@Override
	public void draw(GraphicsContext _gc) {
		DrawerFx.drawShapes(_gc, line, arrow1, arrow2);
	}

	@Override
	public void draw(Drawer _drawer) {
		// TODO Auto-generated method stub
		
	}

}