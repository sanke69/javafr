package fr.java.maths.geometry.line.types;

import java.text.NumberFormat;
import java.util.Set;

import fr.java.math.algebra.vector.generic.Vector1D;
import fr.java.math.geometry.linear.Dimension1D;
import fr.java.math.geometry.linear.Point1D;
import fr.java.maths.algebra.Vectors;

public class SimpleDimension1D implements Dimension1D.Editable {
	private static final long serialVersionUID = 8937625281096499680L;

	protected double width;

    public SimpleDimension1D() {
        super();
        width = 1.0;
    }
    public SimpleDimension1D(double _width) {
        width  = _width;
    }
	public SimpleDimension1D(Set<Point1D> _bounds) {
		if (_bounds == null || _bounds.size() < 2)
			throw new IllegalArgumentException("The parameter '_bounds' cannot be null and must have 2 or more elements.");

		double minX = Integer.MAX_VALUE;
		double maxX = Integer.MIN_VALUE;
		for(Point1D position : _bounds) {
			minX = Math.min(minX, position.getX());
			maxX = Math.max(maxX, position.getX());
		}
		width  = maxX - minX;
	}

	public boolean 							isEmpty() 					{ return width == 0; }
    
	@Override public void 					set(double _w) 				{ width  = _w; }

	@Override public void 					setWidth(double _w) 		{ width  = _w; }

	@Override public double 				getWidth() 					{ return width; }

    @Override public String 				toString() 					{ return "[" + getWidth() + "]"; }
	@Override public String 				toString(NumberFormat _nf) 	{ return "[" + _nf.format( getWidth() ) + "]"; }

    public double[] 						asArray() 					{ return new double[] { width }; }
    public Vector1D 						asVector() 					{ return Vectors.of(width); }

    @Override public Dimension1D.Editable 	clone() 					{ return new SimpleDimension1D(width); }

}
