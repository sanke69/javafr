package fr.java.sdk.math.geometry.line;

import java.text.DecimalFormat;

import fr.java.math.topology.Coordinate;
import fr.java.math.topology.CoordinateSystem;

public class Point1D implements Coordinate.OneDim {

	public static class Editable extends Point1D implements Coordinate.OneDim.Editable {

		public Editable() {
			super(0d);
		}
		public Editable(double _value) {
			super(_value);
		}

		@Override
		public void setFirst(double _value) {
			value = _value;
		}
//		@Override
		public void setX(double _value) {
			value = _value;
		}
//		@Override
		public void setValue(double _value) {
			value = _value;
		}

	}

	double value;

	public Point1D() {
		this(0d);
	}
	public Point1D(double _value) {
		super();
		value = _value;
	}

	@Override
	public CoordinateSystem getCoordinateSystem() {
		return CoordinateSystem.Cartesian1D;
	}

	@Override
	public double 			getFirst() {
		return value;
	}
//	@Override
	public double 			getX() {
		return value;
	}
//	@Override
	public double 			getValue() {
		return value;
	}
	
	@Override
	public String 			toString(DecimalFormat _df) {
		return "( " + value + " )";
	}

}
