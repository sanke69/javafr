package fr.java.sdk.math;

import fr.java.math.geometry.plane.Point2D;

public class Angles {
	public static boolean	FAST_ATAN2	= true;

	public static final double quartpi  =  0.7853981633974483096156608458198757;
	public static final double halfpi   =  1.5707963267948966192313216916397514;
	public static final double pi       =  3.1415926535897932384626433832795029;
	public static final double twopi    =  6.28318530717958;
	public static final double fourpi   = 12.5663706144;
	public static final double pisquare =  9.869604401089339;

	public static final float	PI				= (float) Math.PI;
	public static final float	TWOPI			= (float) (Math.PI * 2);
	public static final float	INV_PI			= 1f / PI;
	public static final float	HALF_PI			= PI / 2;
	public static final float	QUARTER_PI		= PI / 4;
	public static final float	THREE_HALVES_PI	= TWOPI - HALF_PI;

	private static final float 	DEG2RAD  		= PI / 180.0f;
//	private static final float 	RAD2DEG  		= 180.0f / PI;
	private static final float 	RAD2GRAD 		= PI / 200.0f;
	private static final float 	GRAD2RAD 		= 200.0f / PI;
//	private static final float 	DEG2GRAD 		= 180.0f / 200.0f;
//	private static final float 	GRAD2DEG 		= 200.0f / 180.0f;

	public static double 		angle(Point2D _a, Point2D _b, Point2D _c) {
		// Angle (in radians) subtended at coordinate C
		double ccos = cos(_a, _b, _c);
		return Math.acos(ccos);
	}
	public static double 		angle(double sideAC, double sideBC, double sideAB) {
		// Angle (in radians) between sides sideA and sideB given all side lengths of a triangle

		double ccos = cos(sideAC, sideBC, sideAB);
		return Math.acos(ccos);
	}

	public static double 		sin(Point2D _a, Point2D _b, Point2D _c) {
		// Sine of angle subtended at coordinate C
		double angle = angle(_a, _b, _c);
		return Math.sin(angle);
	}
	public static double 		sin(double sideAC, double sideBC, double sideAB) {
		// Sine of angle between sides sideA and sideB given all side lengths of a triangle
		double angle = angle(sideAC, sideBC, sideAB);
		return Math.sin(angle);
	}

	public static double 		cos(Point2D _a, Point2D _b, Point2D _c) {
		// Cosine of angle subtended at coordinate C
		double sideAC = Numbers.hypot(_a.getX() - _c.getX(), _a.getY() - _c.getY());
		double sideBC = Numbers.hypot(_b.getX() - _c.getX(), _b.getY() - _c.getY());
		double sideAB = Numbers.hypot(_a.getX() - _b.getX(), _a.getY() - _b.getY());
		return cos(sideAC, sideBC, sideAB);
	}
	public static double 		cos(double sideAC, double sideBC, double sideAB) {
		// Cosine of angle between sides sideA and sideB given all side lengths of a triangle
		return 0.5D * (sideAC / sideBC + sideBC / sideAC - (sideAB / sideAC) * (sideAB / sideBC));
	}

	public static double 		tan(Point2D _a, Point2D _b, Point2D _c) {
		// Tangent of angle subtended at coordinate C
		double angle = angle(_a, _b, _c);
		return Math.tan(angle);
	}
	public static double 		tan(double sideAC, double sideBC, double sideAB) {
		// Tangent of angle between sides sideA and sideB given all side lengths of a triangle
		double angle = angle(sideAC, sideBC, sideAB);
		return Math.tan(angle);
	}
    
	public static double getAngle(Point2D _o, Point2D _a, Point2D _b, boolean _acute) {
		double lOA   = Math.sqrt((_o.getX() - _a.getX())*(_o.getX() - _a.getX()) + (_o.getY() - _a.getY())*(_o.getY() - _a.getY()));
		double lOB   = Math.sqrt((_o.getX() - _b.getX())*(_o.getX() - _b.getX()) + (_o.getY() - _b.getY())*(_o.getY() - _b.getY()));
		double lAB   = Math.sqrt((_a.getX() - _b.getX())*(_a.getX() - _b.getX()) + (_a.getY() - _b.getY())*(_a.getY() - _b.getY()));
		double a_rad = Math.acos((lOA*lOA + lOB*lOB - lAB*lAB) / (2 * lOA * lOB));

		return _acute ? a_rad : 2d * Math.PI - a_rad;
	}
	public static double getAngle(Point2D _o, Point2D _a, Point2D _b) {
		double lOA   = Math.sqrt((_o.getX() - _a.getX())*(_o.getX() - _a.getX()) + (_o.getY() - _a.getY())*(_o.getY() - _a.getY()));
		double lOB   = Math.sqrt((_o.getX() - _b.getX())*(_o.getX() - _b.getX()) + (_o.getY() - _b.getY())*(_o.getY() - _b.getY()));
		double lAB   = Math.sqrt((_a.getX() - _b.getX())*(_a.getX() - _b.getX()) + (_a.getY() - _b.getY())*(_a.getY() - _b.getY()));
		double a_rad = Math.acos((lOA*lOA + lOB*lOB - lAB*lAB) / (2 * lOA * lOB));

		return a_rad;
	}
	public static double getAngleInDegree(Point2D _o, Point2D _a, Point2D _b, boolean _acute) {
		return getAngle(_o, _a, _b, _acute) * 180d / Math.PI;
	}
	public static double getAngleInDegree(Point2D _o, Point2D _a, Point2D _b) {
		return getAngle(_o, _a, _b) * 180d / Math.PI;
	}

	static public double   Grade2Radian(double _rad) {
		return _rad * RAD2GRAD;
	}
	static public double   Radian2Grade(double _grad) {
		return _grad * GRAD2RAD;
	}

	static public double   Degree2Radian(double _degree) {
		return _degree * DEG2RAD;
	}
	static public double   Radian2Degree(double _grad) {
		return 180 * _grad / Math.PI;
	}

	static public double   Degree2Grade(double _degree) {
		return 200 * _degree / 180;
	}
	static public double   Grade2Degree(double _grad) {
		return 180 * _grad / 200;
	}

	static public double   DMS2Degree(int _degree, int _minute, double _second) {
		return _degree + _minute / 60 + _second / 3600;
	}
	static public double   DMS2Radian(int _degree, int _minute, double _second) {
		return Math.PI * (_degree + _minute / 60 + _second / 3600) / 180;
	}
	
	static public double[] Degree2DMS(double _grad) { // TODO::
		double D = Math.floor(_grad);
		double M = Math.floor((_grad - D) * 60);
//		double S = Math.floor((_grad - D - M/60) * 3600);
		double S = (_grad - D - M/60) * 3600;

		return new double[] { D, M, S };
	}
	static public double   Radian2DMS(double _grad) { // TODO::
		return -1;
	}

	public static final double reduceRadian(double _theta) {
		_theta %= TWOPI;
		if(Math.abs(_theta) > PI)
			_theta = _theta - TWOPI;
//		if(Math.abs(theta) > HALF_PI)
//			theta = PI - theta;
		return _theta;
	}
	public static final double reduceDegree(double _theta) {
		_theta %= 360;
		if(_theta > 180)
			_theta = _theta - 360;
		if(_theta < - 180)
			_theta = _theta + 360;
		return _theta;
	}
	public static final double positiveDegree(double _theta) {
		return (_theta + 360) % 360;
	}

	public static String toString(int _deg, int _min, double _sec) {
		return (int) _deg + "°" + (int) _min + "'" + _sec + "\"";
	}
	public static String toString(double[] _dms) {
		return (int) _dms[0] + "°" + (int) _dms[1] + "'" + _dms[2] + "\"";
	}

	public static final float sin(float x) {
		return SINCOS_LUT_ENABLED ? sinLUT(x) : (float) StrictMath.sin(x);
	}
	public static final float cos(float x) {
		return SINCOS_LUT_ENABLED ? sinLUT(HALF_PI - x) : (float) StrictMath.cos(x);
	}

	public static final float atan2(final float y, final float x) {
		return FAST_ATAN2 ? __fastAtan2(y, x) : (float) StrictMath.atan2(y, x);
	}
	public static final float __fastAtan2(float y, float x) {
		if(x == 0.0f) {
			if(y > 0.0f)
				return HALF_PI;
			if(y == 0.0f)
				return 0.0f;
			return -HALF_PI;
		}
		float atan;
		final float z = y / x;
		if(Numbers.abs(z) < 1.0f) {
			atan = z / (1.0f + 0.28f * z * z);
			if(x < 0.0f) {
				if(y < 0.0f)
					return atan - PI;
				return atan + PI;
			}
		} else {
			atan = HALF_PI - z / (z * z + 0.28f);
			if(y < 0.0f)
				return atan - PI;
		}
		return atan;
	}

	public static boolean     SINCOS_LUT_ENABLED   = true;
	/**
	 * Use if the table's precision is large (eg .006 or greater). Although it is more expensive, it
	 * greatly increases accuracy. Look in the MathUtils source for some test results on the accuracy
	 * and speed of lerp vs non lerp. Or, run the tests yourself in  SinCosTest.
	**/
	public static boolean     SINCOS_LUT_LERP      = false;
	public static final float SINCOS_LUT_PRECISION = .00011f;
	public static final int   SINCOS_LUT_LENGTH    = (int) Math.ceil(Math.PI * 2 / SINCOS_LUT_PRECISION);
	  
	public static final float[] sinLUT = new float[SINCOS_LUT_LENGTH];

	static {
		for(int i = 0; i < SINCOS_LUT_LENGTH; i++) {
			sinLUT[i] = (float) Math.sin(i * SINCOS_LUT_PRECISION);
		}
	}

	public static final float sinLUT(float x) {
		x %= TWOPI;

		if(x < 0) {
			x += TWOPI;
		}

		if(SINCOS_LUT_LERP) {

			x /= SINCOS_LUT_PRECISION;

			final int index = (int) x;

			if(index != 0) {
				x %= index;
			}

			// the next index is 0
			if(index == SINCOS_LUT_LENGTH - 1) {
				return ((1 - x) * sinLUT[index] + x * sinLUT[0]);
			} else {
				return ((1 - x) * sinLUT[index] + x * sinLUT[index + 1]);
			}

		} else {
			return sinLUT[Numbers.round(x / SINCOS_LUT_PRECISION) % SINCOS_LUT_LENGTH];
		}
	}

	public static boolean 	orthogonal(double _angle0, double _angle1, double _epsilon) {
		if((Math.abs(reduceDegree(_angle0) - reduceDegree(_angle1)) < _epsilon) || (Math.abs(reduceDegree(_angle0) - reduceDegree(180 + _angle1)) < _epsilon))
			return false;
		return true;
	}
	public static int 		colinear(double _angle0, double _angle1, double _epsilon) {
		if((Math.abs(reduceDegree(_angle0) - reduceDegree(_angle1)) < _epsilon))
			return 1;
		if((Math.abs(reduceDegree(_angle0) - reduceDegree(180 + _angle1)) < _epsilon))
			return -1;
		return 0;
	}

	public static double   fromHeading(double _heading) {
		return Math.PI/2.0 - Angles.Degree2Radian(reduceDegree(_heading));
	}

	public static void main(String[] args) {
		System.out.println(Angles.reduceDegree(0));
		System.out.println(Angles.reduceDegree(90));
		System.out.println(Angles.reduceDegree(180));
		System.out.println(Angles.reduceDegree(270));
		System.out.println(Angles.reduceDegree(360));
		System.out.println(Angles.reduceDegree(-90));
		System.out.println(Angles.reduceDegree(-180));
		System.out.println(Angles.reduceDegree(-270));
		System.out.println(Angles.reduceDegree(-360));

		System.out.println(Angles.orthogonal(0, 45, 50));

		System.out.println(Angles.colinear(0, 15, 30));
		System.out.println(Angles.colinear(0, 45, 30));
		System.out.println(Angles.colinear(0, 135, 30));
		System.out.println(Angles.colinear(0, 190, 30));

		System.out.println("=== HEADING ===");
		System.out.println(Angles.fromHeading(0));
		System.out.println(Angles.fromHeading(90));
		System.out.println(Angles.fromHeading(180));
		System.out.println(Angles.fromHeading(270));
		System.out.println(Angles.fromHeading(360));
	}
}
