package fr.java.maths.geometry.plane.transformations.generic;

import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.algebra.matrices.DoubleMatrix33;
import fr.java.maths.geometry.plane.transformations.Transformation2D;
import fr.java.maths.geometry.types.Points;

public final class Rotation2D extends Transformation2D {
	double  angle;
	Point2D pivot;

	public Rotation2D() {
		super();
	}
	public Rotation2D(double _angle) {
		super();

		angle = _angle;

		double Cos = Math.cos(_angle);
		double Sin = Math.sin(_angle);

		m.m00 =  Cos; m.m01 = -Sin; m.m02 = 0.0f;
		m.m10 =  Sin; m.m11 =  Cos; m.m12 = 0.0f;
		m.m20 = 0.0f; m.m21 = 0.0f; m.m22 = 1.0f;
	}

	public double getAngle() {
		return angle;
	}
	public void setAngle(double _angle) {
		angle = _angle;

		double Cos = Math.cos(_angle);
		double Sin = Math.sin(_angle);

		m.m00 =  Cos; m.m01 = -Sin;
		m.m10 =  Sin; m.m11 =  Cos;
	}

	public Point2D getPivot() {
		return pivot;
	}
	public void setPivot(Point2D _pivot) {
		pivot = _pivot;
	}

	@Override
	public DoubleMatrix33 asUniformMatrix() {
		if(pivot != null && pivot != Points.zero2()) {
			Translation2D 	Tr1 = new Translation2D(pivot.getX(), pivot.getY()),
							Tr2 = new Translation2D(-pivot.getX(), -pivot.getY());
			return Tr1.asUniformMatrix().times(m.times(Tr2.asUniformMatrix()));
		}

		return m;
	}

}
