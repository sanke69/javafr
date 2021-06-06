package fr.java.sdk.math.geometry.space.shapes.quadrics.shapes;

import fr.java.math.geometry.space.BoundingBox3D;
import fr.java.math.geometry.space.Dimension3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.sdk.math.Points;
import fr.java.sdk.math.geometry.space.shapes.quadrics.Quadric3DBase;
import fr.java.sdk.math.geometry.space.shapes.quadrics.QuadricShape3D;
import fr.java.sdk.math.geometry.space.types.SimpleDimension3D;
import fr.java.sdk.math.geometry.space.types.SimpleRay3D;

// http://www.les-mathematiques.net/phorum/read.php?4,655223,655223
public class Cone3D extends Quadric3DBase implements QuadricShape3D {
	private static final long serialVersionUID = -5050064419568631832L;

	public static Cone3D alongX(double _height, double _depth) {
		Cone3D result = new Cone3D(-1, - 1.0 / _height*_height, - 1.0 / _depth*_depth);
		result.hat        = Points.zero3();
		result.dimensions = SimpleDimension3D.unit();
		return result;
	}
	public static Cone3D alongY(double _width, double _depth)  { return new Cone3D(- 1.0 / _width*_width, -1, - 1.0 / _depth*_depth); }
	public static Cone3D alongZ(double _width, double _height) { return new Cone3D(- 1.0 / _width*_width, - 1.0 / _height*_height, -1); }

	Point3D     hat;
	Dimension3D dimensions;

	private Cone3D(double _x2, double _y2, double _z2) {
		super(_x2, _y2, _z2, 0, 0, 0, 0, 0, 0, 0);
	}
	public  Cone3D() {
		super(-1, 1, 1, 0, 0, 0, 0, 0, 0, 0);
		hat        = Points.zero3();
		dimensions = SimpleDimension3D.unit();
	}

	public QuadricEquation 				getEquation() {
		return (_x, _y, _z) -> {
			return getCoef(COEF.X2) * _x*_x + getCoef(COEF.Y2) * _y*_y + getCoef(COEF.Z2) * _z*_z 
					+ getCoef(COEF.UN);
		};
	}
	public QuadricShapeParametrization 	getParametrization() {
		return (_altitude, _azimuth) -> {
//			if(_altitude < 0)
//				return null;

			double w  = dimensions.getWidth()/2,	h  = dimensions.getHeight()/2, 	d  = dimensions.getDepth()/2;
			double cx = hat.getX(), 				cy = hat.getY(), 				cz = hat.getZ();

			double tanAlpha = w / d;
			double xyz = cx + w * _altitude * Math.cos(_azimuth);
			double yzx = cy + h * _altitude * Math.sin(_azimuth) / tanAlpha;
			double zxy = cz + d * _altitude / tanAlpha;

			return Points.of(xyz, yzx, zxy);
		};
	}
	public Point3D 						getSurfacePoint(double _altitude, double _azimuth) { return getParametrization().get(_altitude, _azimuth); }

	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("[CONE: c= ]");
		
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
