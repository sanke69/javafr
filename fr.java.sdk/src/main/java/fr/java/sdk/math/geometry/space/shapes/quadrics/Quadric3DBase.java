package fr.java.sdk.math.geometry.space.shapes.quadrics;

// General Equation: F(x, y, z) = Ax2 + By2 + Cz2 + Dxy+ Exz + Fyz + Gx + Hy + Iz + J = 0
public abstract class Quadric3DBase implements Quadric3D {
	private static final long serialVersionUID = -939601348897459238L;

	private Double a, b, c, d, e, f, g, h, i, j;

	public Quadric3DBase(double _a, double _b, double _c, double _d, double _e, double _f, double _g, double _h, double _i, double _j) {
		super();
		a = _a; b = _b; c = _c; d = _d; e = _e; f = _f; g = _g; h = _h; i = _i; j = _j;
	}

	protected void   setCoef(COEF _coef, double _value) {
		switch(_coef) {
		case X2:		a = _value;
		case Y2:		b = _value;
		case Z2:		c = _value;
		case XY:		d = _value;
		case XZ:		e = _value;
		case YZ:		f = _value;
		case X:			g = _value;
		case Y:			h = _value;
		case Z:			i = _value;
		case UN:		j = _value;
		default:		throw new IllegalAccessError();
		}
	}
	public    double getCoef(COEF _coef) {
		switch(_coef) {
		case X2:		return a;
		case Y2:		return b;
		case Z2:		return c;
		case XY:		return d;
		case XZ:		return e;
		case YZ:		return f;
		case X:			return g;
		case Y:			return h;
		case Z:			return i;
		case UN:		return j;
		default:		throw new IllegalAccessError();
		}
	}

}
