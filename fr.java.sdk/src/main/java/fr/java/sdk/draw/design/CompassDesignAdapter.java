package fr.java.sdk.draw.design;

import fr.java.draw.Drawer;
import fr.java.draw.tools.Brush;
import fr.java.draw.tools.Colors;
import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.BoundingBoxes;

public class CompassDesignAdapter extends GraphDesignAdapter {
	public static final Brush BACKGROUND_BRUSH  = Brush.of(1.0, Colors.BLACK, Colors.of(0.2f, 0.2f, 0.2f, 0.2f));
	public static final Brush WIND_ROSE_BRUSH   = Brush.of(1.0, Colors.GREEN);
	public static final Brush HAND_NORTH_BRUSH  = Brush.of(1.0, Colors.WHITE, Colors.WHITE);
	public static final Brush HAND_BLACK_BRUSH  = Brush.of(1.0, Colors.BLACK, Colors.BLACK);

	double  northAngle = 90, handAngle;
	boolean clockwise;
	
	public CompassDesignAdapter(double _x, double _y, double _w, double _h) {
		super(_x, _y, _w, _h);
		graph().setDomain(BoundingBoxes.fromBounds(-1, -1, 1, 1));
	}

	public void setNorthPosition(int _degree) {
		northAngle = _degree;
	}
	public double 	getNorthPosition() {
		return northAngle;
	}

	public void 	setClockWise(boolean _true) {
		clockwise = _true;
	}
	public boolean 	isClockWise() {
		return clockwise;
	}

	public void 	setHandAngle(double _angle) {
		handAngle = _angle;
	}
	public double 	getHandAngle() {
		return handAngle;
	}

	@Override
	public void draw(Drawer _drawer) {
		super.draw(_drawer);

		drawWindRose	(_drawer);
		drawHand		(_drawer);
	}

	void 	drawWindRose(Drawer _drawer) {
		double nmark = 8;

        _drawer.drawEllipse(graph().Xg2d(-1), graph().Yg2d(1), graph().dXg2d(2), graph().dYg2d(2), BACKGROUND_BRUSH);

    	for (int i = 0; i < nmark; i++) {
    		double CardinalPoint = i;
    		double angle = 2.0 * Math.PI * (nmark - CardinalPoint) / nmark - Math.PI;
    		double rx0, ry0, rx1, ry1;
    		double  x0,  y0,  x1,  y1;

    		if(i % 2 == 0) {
				rx0 = 0.55;
				ry0 = 0.55;
				rx1 = 0.85;
				ry1 = 0.85;
    		} else {
				rx0 = 0.75;
				ry0 = 0.75;
				rx1 = 0.85;
				ry1 = 0.85;
    		}

    		x0 = rx0 * Math.sin(angle);
    		y0 = ry0 * Math.cos(angle);
    		x1 = rx1 * Math.sin(angle);
    		y1 = ry1 * Math.cos(angle);

    		Point2D pt0 = graph().Pg2d(x0, y0);
    		Point2D pt1 = graph().Pg2d(x1, y1);

    		_drawer.drawLine(pt0.getX(), pt0.getY(), pt1.getX(), pt1.getY(), WIND_ROSE_BRUSH);
    	}
	}
	void 	drawHand(Drawer _drawer) {
	    double North_angle = 2.0 * Math.PI * (180 - northAngle + getHandAngle()) / 360.0 - Math.PI;
	    double West_angle  = 2.0 * Math.PI * (180 - northAngle + getHandAngle()) / 360.0 - Math.PI / 2.0;
	    double East_angle  = 2.0 * Math.PI * (180 - northAngle + getHandAngle()) / 360.0 + Math.PI / 2.0;
	    double South_angle = 2.0 * Math.PI * (180 - northAngle + getHandAngle()) / 360.0;

	    double BigAxeX   = 0.8;
	    double BigAxeY   = 0.8;
	    double SmallAxeX = 0.2;
	    double SmallAxeY = 0.2;

	    double Nx = BigAxeX   * Math.sin(North_angle);
	    double Ny = BigAxeY   * Math.cos(North_angle);
	    double Sx = BigAxeX   * Math.sin(South_angle);
	    double Sy = BigAxeY   * Math.cos(South_angle);
	    double Ex = SmallAxeX * Math.sin(East_angle);
	    double Ey = SmallAxeY * Math.cos(East_angle);
	    double Wx = SmallAxeX * Math.sin(West_angle);
	    double Wy = SmallAxeY * Math.cos(West_angle);

		Point2D N = graph().Pg2d(Nx, Ny);
		Point2D S = graph().Pg2d(Sx, Sy);
		Point2D E = graph().Pg2d(Ex, Ey);
		Point2D W = graph().Pg2d(Wx, Wy);

	    _drawer.drawPolygon(new double[] { N.getX(), E.getX(), W.getX() }, new double[] { N.getY(), E.getY(), W.getY() }, HAND_NORTH_BRUSH);
	    _drawer.drawPolygon(new double[] { S.getX(), E.getX(), W.getX() }, new double[] { S.getY(), E.getY(), W.getY() }, HAND_BLACK_BRUSH);

	}


}
