package fr.java.maths.geometry.plane.types;

import java.text.NumberFormat;
import java.util.Set;

import fr.java.math.algebra.vector.generic.Vector2D;
import fr.java.math.geometry.plane.Dimension2D;
import fr.java.maths.algebra.Vectors;

public class SimpleDimension2D implements Dimension2D.Editable {
	private static final long serialVersionUID = 8937625281096499680L;

	public static SimpleDimension2D unit() {
		return new SimpleDimension2D(1, 1);
	}
	public static SimpleDimension2D of(int _w, int _h) {
		return new SimpleDimension2D(_w, _h);
	}
	public static SimpleDimension2D of(double _w, double _h) {
		return new SimpleDimension2D(_w, _h);
	}

	protected double width, height;

    public SimpleDimension2D() {
        super();
        width = height = 1.0;
    }
    public SimpleDimension2D(double _width, double _height) {
        width  = _width;
        height = _height;
    }
	public SimpleDimension2D(Set<Vector2D> _bounds) {
		if (_bounds == null || _bounds.size() < 2)
			throw new IllegalArgumentException("The parameter '_bounds' cannot be null and must have 2 or more elements.");

		double minY = Integer.MAX_VALUE;
		double minX = Integer.MAX_VALUE;
		double maxY = Integer.MIN_VALUE;
		double maxX = Integer.MIN_VALUE;
		for(Vector2D position : _bounds) {
			minY = Math.min(minY, position.getY());
			minX = Math.min(minX, position.getX());
			maxY = Math.max(maxY, position.getY());
			maxX = Math.max(maxX, position.getX());
		}
		width  = maxX - minX;
		height = maxY - minY;
	}

	public boolean 							isEmpty() 					{ return width == 0 || height == 0; }
    
	@Override public void 					set(double _w, double _h) 	{ width  = _w; height = _h; }

	@Override public void 					setWidth(double _w) 		{ width  = _w; }
	@Override public void 					setHeight(double _h) 		{ height = _h; }

	@Override public double 				getWidth() 					{ return width; }
	@Override public double 				getHeight() 				{ return height; }

    @Override public String 				toString() 					{ return "[" + getWidth() + "x" + getHeight() + "]"; }
	@Override public String 				toString(NumberFormat _nf) 	{ return "[" + _nf.format( getWidth() ) + "x" + _nf.format( getHeight() ) + "]"; }

    public double[] 						asArray() 					{ return new double[] { width, height }; }
    public Vector2D 						asVector() 					{ return Vectors.of(width, height); }

    @Override public Dimension2D.Editable 	clone() 					{ return new SimpleDimension2D(width, height); }

}
