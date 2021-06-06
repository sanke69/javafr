package fr.java.sdk.math.geometry.space.shapes.quadrics.shapes;

import fr.java.math.geometry.space.BoundingBox3D;
import fr.java.math.geometry.space.Dimension3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.sdk.math.Points;
import fr.java.sdk.math.geometry.space.shapes.quadrics.Quadric3DBase;
import fr.java.sdk.math.geometry.space.shapes.quadrics.QuadricShape3D;
import fr.java.sdk.math.geometry.space.types.SimpleDimension3D;
import fr.java.sdk.math.geometry.space.types.SimpleRay3D;

public class Cylinder3D extends Quadric3DBase implements QuadricShape3D {
	private static final long serialVersionUID = -5050064419568631832L;

	public static Cylinder3D alongX(double _height, double _depth) {
		Cylinder3D result = new Cylinder3D(0, - 1.0 / _height*_height, - 1.0 / _depth*_depth, -1);
		result.center     = Points.zero3();
		result.dimensions = SimpleDimension3D.unit();
		return result;
	}
	public static Cylinder3D alongY(double _width, double _depth)  { return new Cylinder3D(- 1.0 / _width*_width, 0, - 1.0 / _depth*_depth, -1); }
	public static Cylinder3D alongZ(double _width, double _height) { return new Cylinder3D(- 1.0 / _width*_width, - 1.0 / _height*_height, 0, -1); }

	Point3D     center;
	Dimension3D dimensions;

	private Cylinder3D(double _x2, double _y2, double _z2, double _cst) {
		super(_x2, _y2, _z2, 0, 0, 0, 0, 0, 0, _cst);
	}
	public  Cylinder3D() {
		super(0, 1, 1, 0, 0, 0, 0, 0, 0, -1);
		center     = Points.zero3();
		dimensions = SimpleDimension3D.unit();
	}

	public Point3D getCenter() 			{ return center; }

	public QuadricEquation 				getEquation() {
		return (_x, _y, _z) -> {
			return getCoef(COEF.X2) * _x*_x + getCoef(COEF.Y2) * _y*_y + getCoef(COEF.Z2) * _z*_z 
					+ getCoef(COEF.UN);
		};
	}
	public QuadricShapeParametrization  getParametrization() {
		return (_altitude, _azimuth) -> {	// azimuth, altitude
			double w  = dimensions.getWidth(),	h  = dimensions.getHeight();
			double cx = center.getX(), 			cy = center.getY(), 			cz = center.getZ();

			double x = cx + w * Math.cos(_azimuth);
			double y = cy + h * Math.sin(_azimuth);
			double z = cz + _altitude;

			return Points.of(x, y, z);
		};
	}
	public Point3D 						getSurfacePoint(double _altitude, double _azimuth) { return getParametrization().get(_altitude, _azimuth); }

	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("[CYLINDER: c= ]");
		
		return sb.toString();
	}

	@Override
	public boolean intersects(SimpleRay3D _ray) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean contains(Point3D _pt) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean contains(BoundingBox3D _bbox) {
		// TODO Auto-generated method stub
		return false;
	}

}
