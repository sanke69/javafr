package fr.java.maths.geometry.space.types;

import java.text.NumberFormat;
import java.util.Set;

import fr.java.math.geometry.space.Dimension3D;
import fr.java.math.geometry.space.Vector3D;
import fr.java.maths.algebra.Vectors;

public class SimpleDimension3D implements Dimension3D.Editable {
	private double width, height, depth;

	public static Dimension3D empty() {
		return new SimpleDimension3D(0, 0, 0);
	}

	public static Dimension3D unit() {
		return new SimpleDimension3D(1, 1, 1);
	}
	public static Dimension3D of(double _width, double _height, double _depth) {
		return new SimpleDimension3D(_width, _height, _depth);
	}

	public SimpleDimension3D() {
		super();
		width = height = depth = 0.0; //Double.NaN;
	}
	public SimpleDimension3D(double _width, double _height, double _depth) {
		super();
		width  = _width;
		height = _height;
		depth  = _depth;
	}
	public SimpleDimension3D(Set<? extends Vector3D> _bounds) {
		if (_bounds == null || _bounds.size() < 2)
			throw new IllegalArgumentException("The parameter '_bounds' cannot be null and must have 2 or more elements.");

		double minX = Integer.MAX_VALUE;
		double minY = Integer.MAX_VALUE;
		double minZ = Integer.MAX_VALUE;
		double maxX = Integer.MIN_VALUE;
		double maxY = Integer.MIN_VALUE;
		double maxZ = Integer.MIN_VALUE;
		for(Vector3D position : _bounds) {
			minX = Math.min(minX, position.getX());
			minY = Math.min(minY, position.getY());
			minZ = Math.min(minZ, position.getZ());
			maxX = Math.max(maxX, position.getX());
			maxY = Math.max(maxY, position.getY());
			maxZ = Math.max(maxZ, position.getZ());
		}
		width = maxX - minX;
		height = maxY - minY;
		depth = maxZ - minZ;
	}

	public void 	set(double _w, double _h, double _d) { width = _w; height = _h; depth = _d; }

	public void 	setWidth(double _w) { width = _w; }
	public void 	setHeight(double _h) { height = _h; }
	public void 	setDepth(double _d) { depth = _d; }

	public double 	getWidth() { return width; }
	public double 	getHeight() { return height; }
	public double 	getDepth() { return depth; }

	public boolean 	isEmpty() { return (width == 0 && (height == 0 || depth == 0)) || (height == 0 && (width == 0 || depth == 0)) || (depth == 0 && (width == 0 || height == 0)); }

	public double[] asArray() { return new double[] { width, height, depth }; }
	public Vector3D asVector() { return Vectors.of(width, height, depth); }

	@Override
	public String 	toString() { return "[" + getWidth() + "x" + getHeight() + "x" + getDepth() + "]"; }

	@Override
	public String 	toString(NumberFormat _nf) {
		return "[" + _nf.format( getWidth() ) + "x" + _nf.format( getHeight() ) + "x" + _nf.format( getDepth() ) + "]";
	}

    @Override public Dimension3D.Editable 		clone() { return new SimpleDimension3D(width, height, depth); }


}
