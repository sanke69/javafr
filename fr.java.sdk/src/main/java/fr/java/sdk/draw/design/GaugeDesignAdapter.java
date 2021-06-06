package fr.java.sdk.draw.design;

import fr.java.draw.Drawer;
import fr.java.draw.tools.Brush;
import fr.java.draw.tools.Colors;
import fr.java.math.Interval;
import fr.java.math.geometry.plane.Point2D;
import fr.java.sdk.math.Angles;
import fr.java.sdk.math.BoundingBoxes;

public class GaugeDesignAdapter extends GraphDesignAdapter {
	public static final Brush BACKGROUND_BRUSH  = Brush.of(1.0, Colors.BLACK, Colors.of(0.2f, 0.2f, 0.2f, 0.2f));
	public static final Brush HAND_NORTH_BRUSH  = Brush.of(1.0, Colors.WHITE, Colors.WHITE);
	public static final Brush HAND_BLACK_BRUSH  = Brush.of(1.0, Colors.BLACK, Colors.BLACK);

	double 				zeroAngle, rangeAngle;

	Interval<Double> 	range;
	double 				value;
	
	public GaugeDesignAdapter(double _x, double _y, double _w, double _h) {
		super(_x, _y, _w, _h);
		graph().setDomain(BoundingBoxes.fromBounds(-1, -1, 1, 1));

		zeroAngle = 235; rangeAngle = 210;
		range     = Interval.of(0d,  100d);
	}

	public void  			setAngleRange(double _min, double _range) {
		zeroAngle  = _min;
		rangeAngle = _range;
	}
	public double 			getAngleMin() {
		return zeroAngle;
	}
	public double 			getAngleRange() {
		return rangeAngle;
	}

	public void  			setValueRange(double _min, double _max) {
		range = Interval.of(_min, _max);
	}
	public Interval<Double> getValueRange() {
		return range;
	}

	public void 			setValue(double _value) {
		value = _value;
	}
	public double 			getValue() {
		return value;
	}

	double 					getDegreeFromValue(double _value) {
		if(_value < getValueRange().getMin())
			return getAngleMin();
		if(_value > getValueRange().getMax())
			return getAngleMin() - getAngleRange() - 10;

		double dVAL = getValueRange().getMax() - getValueRange().getMin();
		double RAD  = getAngleMin() - _value / dVAL * getAngleRange();
		return RAD;
	}

	@Override
	public void draw(Drawer _drawer) {
		super.draw(_drawer);

		drawGauge	(_drawer);
		drawHand	(_drawer);
	}

	private void drawGauge(Drawer _drawer) {
		double dVAL = getValueRange().getMax() - getValueRange().getMin();
		
		double nmark = dVAL / 10;

        _drawer.drawEllipse(graph().Xg2d(-1), graph().Yg2d(1), graph().dXg2d(2), graph().dYg2d(2), BACKGROUND_BRUSH);
//        _drawer.drawEllipse(PX(0), PY(0), dPX(1.8), dPY(1.8), BACKGROUND_BRUSH);
		
    	for (int i = 0; i <= nmark; i++) {
    		double SpeedMarker = (double) i;
    		double angle = getAngleMin() - getAngleRange() * ((double) (SpeedMarker / nmark));
    		double rx0, ry0, rx1, ry1;
    		double  x0,  y0,  x1,  y1;

    		if(i % 5 == 0) {
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

    		x0 = rx0 * Math.cos(Angles.Degree2Radian(angle));
    		y0 = ry0 * Math.sin(Angles.Degree2Radian(angle));
    		x1 = rx1 * Math.cos(Angles.Degree2Radian(angle));
    		y1 = ry1 * Math.sin(Angles.Degree2Radian(angle));

    		Point2D pt0 = graph().Pg2d(x0, y0);
    		Point2D pt1 = graph().Pg2d(x1, y1);

    		_drawer.drawLine(pt0.getX(), pt0.getY(), pt1.getX(), pt1.getY(), HAND_NORTH_BRUSH);
    	}
	}
	
	private void drawHand(Drawer _drawer) {
	    double Needle_angle = Angles.Degree2Radian(getDegreeFromValue(getValue()));

	    double BigAxeX   = 0.8;
	    double BigAxeY   = 0.8;
	    double SmallAxeX = 0.05;
	    double SmallAxeY = 0.05;

	    double Nx = BigAxeX   * Math.cos(Needle_angle);
	    double Ny = BigAxeY   * Math.sin(Needle_angle);
	    double Sx = SmallAxeX * Math.cos(Needle_angle - Math.PI);
	    double Sy = SmallAxeX * Math.sin(Needle_angle - Math.PI);
	    double Ex = SmallAxeX * Math.cos(Needle_angle - Math.PI/2.0);
	    double Ey = SmallAxeY * Math.sin(Needle_angle - Math.PI/2.0);
	    double Wx = SmallAxeX * Math.cos(Needle_angle + Math.PI/2.0);
	    double Wy = SmallAxeY * Math.sin(Needle_angle + Math.PI/2.0);

		Point2D N = graph().Pg2d(Nx, Ny);
		Point2D S = graph().Pg2d(Sx, Sy);
		Point2D E = graph().Pg2d(Ex, Ey);
		Point2D W = graph().Pg2d(Wx, Wy);

	    _drawer.drawPolygon(new double[] { N.getX(), E.getX(), S.getX(), W.getX() }, new double[] { N.getY(), E.getY(), S.getY(), W.getY() }, HAND_NORTH_BRUSH);
	}

}
