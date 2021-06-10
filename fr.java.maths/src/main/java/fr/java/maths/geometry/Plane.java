package fr.java.maths.geometry;

import fr.java.math.geometry.plane.Frame2D;
import fr.java.math.geometry.plane.Rectangle2D;
import fr.java.maths.geometry.plane.shapes.SimpleRectangle2D;
import fr.java.maths.geometry.plane.types.SimpleFrame2D;

public class Plane {

	public static final Frame2D newFrame() {
		return new SimpleFrame2D();
	}

	public static final Rectangle2D newRectangle() {
		return new SimpleRectangle2D();
	}
	public static final Rectangle2D newRectangle(float _x, float _y, float _w, float _h) {
		return new SimpleRectangle2D(_x, _y, _w, _h);
	}
	public static final Rectangle2D newRectangle(double _x, double _y, double _w, double _h) {
		return new SimpleRectangle2D(_x, _y, _w, _h);
	}
	public static final Rectangle2D newRectangle(Rectangle2D _rect) {
		return new SimpleRectangle2D(_rect);
	}

}
