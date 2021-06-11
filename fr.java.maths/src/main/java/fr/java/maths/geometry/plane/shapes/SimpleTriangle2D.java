package fr.java.maths.geometry.plane.shapes;

import java.util.function.Supplier;

import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Triangle2D;

public class SimpleTriangle2D implements Triangle2D {
	private static final long serialVersionUID = 6112340687137795838L;

	protected Point2D a, b, c;

    final Supplier<Double> minX = () -> a.getFirst()  < b.getFirst()  ? a.getFirst()  : b.getFirst()  < c.getFirst()  ? b.getFirst()  : c.getFirst();
    final Supplier<Double> minY = () -> a.getSecond() < b.getSecond() ? a.getSecond() : b.getSecond() < c.getSecond() ? b.getSecond() : c.getSecond();
    final Supplier<Double> maxX = () -> a.getFirst()  > b.getFirst()  ? a.getFirst()  : b.getFirst()  > c.getFirst()  ? b.getFirst()  : c.getFirst(); 
    final Supplier<Double> maxY = () -> a.getSecond() > b.getSecond() ? a.getSecond() : b.getSecond() > c.getSecond() ? b.getSecond() : c.getSecond();

    public SimpleTriangle2D() {
        super();
        a = b = c = null;
    }
    public SimpleTriangle2D(Point2D _a, Point2D _b, Point2D _c) {
        this();
        a = _a;
        b = _b;
        c = _c;
    }

    public Point2D getA() {
    	return a;
    }
    public Point2D getB() {
    	return b;
    }
    public Point2D getC() {
    	return c;
    }

	@Override
	public double getX() {
		return minX.get();
	}
	@Override
	public double getY() {
		return minY.get();
	}

	@Override
	public double getWidth() {
		return maxX.get() - minX.get();
	}
	@Override public double getHeight() {
		return maxY.get() - minY.get();
	}

}
