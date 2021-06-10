package fr.java.maths.geometry.space.types;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import fr.java.math.algebra.matrix.specials.Matrix44D;
import fr.java.math.geometry.Frame;
import fr.java.math.geometry.space.Frame3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.math.geometry.space.Vector3D;
import fr.java.maths.algebra.Vectors;
import fr.java.maths.algebra.matrices.Matrix44d;
import fr.java.maths.geometry.Geometry;
import fr.java.maths.geometry.space.utils.Quaternion;

public class SimpleFrame3D implements Frame3D {
	private static final long serialVersionUID = -3996339218987082333L;

	public static final Frame3D of(Point3D _o, Vector3D _i, Vector3D _j, Vector3D _k) {
		return new SimpleFrame3D(_o, _i, _j, _k);
	}

	private Frame3D parentFrame;

	public final Point3D.Editable  o;
	public final Vector3D.Editable i, j, k;
	
	public final Matrix44d m; // ModelMatrix

	public SimpleFrame3D() {
		super();
		o = Geometry.Space.WorldOrigin.cloneEditable();
		i = Geometry.Space.WorldXAxis.cloneEditable();
		j = Geometry.Space.WorldYAxis.cloneEditable();
		k = Geometry.Space.WorldZAxis.cloneEditable();

		m = new Matrix44d();

		parentFrame = null; // ie. world!
	}
	public SimpleFrame3D(Point3D _o) {
		super();
		o = _o.cloneEditable();
		i = Geometry.Space.WorldXAxis.cloneEditable();
		j = Geometry.Space.WorldYAxis.cloneEditable();
		k = Geometry.Space.WorldZAxis.cloneEditable();

		m = new Matrix44d();

		parentFrame = null; // ie. world!
	}
	public SimpleFrame3D(Point3D _o, Vector3D _i, Vector3D _j, Vector3D _k) {
		super();
		o = _o.cloneEditable();
		i = _i.normalized().cloneEditable();
		j = _j.normalized().cloneEditable();
		k = _k.normalized().cloneEditable();

		m = new Matrix44d();

		parentFrame = null; // ie. world!
	}

	@Override
	public void 		setParentFrame(Frame3D _other) {
		parentFrame = _other;
	}
	@Override
	public Frame3D 		getParentFrame() {
		return parentFrame;
	}

	@Override
	public void 		set(Point3D _o,Vector3D _i, Vector3D _j, Vector3D _k) {
		assert(Vectors.dotProduct(_i, _j) == 0);
		assert(Vectors.dotProduct(_i, _k) == 0);
		assert(Vectors.dotProduct(_j, _k) == 0);

		o.set(_o);
		i.set(_i.normalized());
		j.set(_j.normalized());
		k.set(_k.normalized());
	}

	@Override
	public void 		setOrigin(Point3D _o) {
		o.set(_o);
	}
	@Override
	public void 		setAxis(Vector3D _i, Vector3D _j, Vector3D _k) {
		assert(Vectors.dotProduct(_i, _j) == 0);
		assert(Vectors.dotProduct(_i, _k) == 0);
		assert(Vectors.dotProduct(_j, _k) == 0);

		i.set(_i.normalized());
		j.set(_j.normalized());
		k.set(_k.normalized());
	}
	
	@Override
	public Point3D  	getOrigin() {
		return o;
	}
	@Override
	public Vector3D 	getXAxis() {
		return i;
	}
	@Override
	public Vector3D 	getYAxis() {
		return j;
	}
	@Override
	public Vector3D 	getZAxis() {
		return k;
	}

	public Matrix44D 	getModelMatrix() {
		m.m00 = i.getX(); 	m.m01 = j.getX();	m.m02 = k.getX();	m.m03 = o.getX();
		m.m10 = i.getY(); 	m.m11 = j.getY(); 	m.m12 = k.getY();	m.m13 = o.getY();
		m.m20 = i.getZ();	m.m21 = j.getZ();	m.m22 = k.getZ();	m.m23 = o.getZ();
		m.m30 = 0.0f; 		m.m31 =  0.0f; 		m.m32 = 0.0f; 		m.m33 = 1.0f;
		return m;
	}

	@Override
	public void 		rotateOrigin(double _dax, double _day, double _daz) {
		Quaternion Q = Quaternion.fromEulerAngle(_dax, _day, _daz);

		o.set( Q.transformPoint(o) );
	}
	@Override
	public void 		rotateAxes(double _dax, double _day, double _daz) {
		Quaternion Q = Quaternion.fromEulerAngle(_dax, _day, _daz);

		i.set( Q.transformVector(i).normalized() );
		j.set( Q.transformVector(j).normalized() );
		k.set( Q.transformVector(k).normalized() );
	}
	@Override
	public void 		rotateOriginAndAxes(double _dax, double _day, double _daz) {
		Quaternion Q = Quaternion.fromEulerAngle(_dax, _day, _daz);

		o.set( Q.transformPoint(o) );
		i.set( Q.transformVector(i).normalized() );
		j.set( Q.transformVector(j).normalized() );
		k.set( Q.transformVector(k).normalized() );
	}

	@Override
	public void 		rotateOrigin(double _dax, double _day, double _daz, Frame3D _ref) {
		Quaternion 	qx = Quaternion.fromAxisAngle(_ref.getXAxis(), _dax), 
					qy = Quaternion.fromAxisAngle(_ref.getYAxis(), _day),
					qz = Quaternion.fromAxisAngle(_ref.getZAxis(), _daz), 
					Q  = qx.times(qy).times(qz);

		o.set( Q.transformPoint(o) );
	}
	@Override
	public void rotateAxes(double _dax, double _day, double _daz, Frame3D _ref) {
		Quaternion  qx = Quaternion.fromAxisAngle(_ref.getXAxis(), _dax), 
					qy = Quaternion.fromAxisAngle(_ref.getYAxis(), _day),
					qz = Quaternion.fromAxisAngle(_ref.getZAxis(), _daz), 
					Q  = qx.times(qy).times(qz);

		i.set( Q.transformVector(i).normalized() );
		j.set( Q.transformVector(j).normalized() );
		k.set( Q.transformVector(k).normalized() );
	}
	@Override
	public void rotateOriginAndAxes(double _dax, double _day, double _daz, Frame3D _ref) {
		Quaternion  qx = Quaternion.fromAxisAngle(_ref.getXAxis(), _dax), 
					qy = Quaternion.fromAxisAngle(_ref.getYAxis(), _day),
					qz = Quaternion.fromAxisAngle(_ref.getZAxis(), _daz), 
					Q  = qx.times(qy).times(qz);

		o.set( Q.transformPoint(o) );
		i.set( Q.transformVector(i).normalized() );
		j.set( Q.transformVector(j).normalized() );
		k.set( Q.transformVector(k).normalized() );
	}

/*
	@Override
	public void 		moveTo(Point3D _position) {
		o = _position;
	}
	@Override
	public void 		moveTo(double _px, double _py, double _pz) {
		o.set(_px, _py, _pz);
	}

	@Override
	public void translate(Vector3D _vect_speed) {
		o.setX(o.getX() + _vect_speed.getX());
		o.setY(o.getY() + _vect_speed.getY());
		o.setZ(o.getZ() + _vect_speed.getZ());
	}
	@Override
	public void translate(double _tx, double _ty, double _tz) {
		o.setX(o.getX() + _tx);
		o.setY(o.getY() + _ty);
		o.setZ(o.getZ() + _tz);
	}

	@Override
	public void moveTo(Point3D _position, Frame3D _ref) {
		o = _ref.getOrigin().plus(_ref.getXAxis().times(_position.getX())).plus(j.times(_position.getY())).plus(k.times(_position.getZ()));
	}
	@Override
	public void moveTo(double _px, double _py, double _pz, Frame3D _ref) {
		o = _ref.getOrigin().plus(_ref.getXAxis().times(_px)).plus(j.times(_py)).plus(k.times(_pz));
	}

	@Override
	public void translate(Vector3D _vect_speed, Frame3D _ref) {
		o.plusEquals(_ref.getOrigin().plus(_ref.getXAxis().times(_vect_speed.getX())).plus(j.times(_vect_speed.getY())).plus(k.times(_vect_speed.getZ())));
	}
	@Override
	public void translate(double _tx, double _ty, double _tz, Frame3D _ref) {
		o.plusEquals(_ref.getOrigin().plus(_ref.getXAxis().times(_tx)).plus(j.times(_ty)).plus(k.times(_tz)));
	}
/*
	public void rotateOrigin(double _dax, double _day, double _daz) {
		Quaternion Q = Quaternion.FromEulerAngle(_dax, _day, _daz);

		o = Q.transformPoint(o);
	}
	public void rotateAxes(double _dax, double _day, double _daz) {
		Quaternion Q = Quaternion.FromEulerAngle(_dax, _day, _daz);

		i = Q.transformVector(i).normalized();
		j = Q.transformVector(j).normalized();
		k = Q.transformVector(k).normalized();
	}
	public void rotateOriginAndAxes(double _dax, double _day, double _daz) {
		Quaternion Q = Quaternion.FromEulerAngle(_dax, _day, _daz);

		o = Q.transformPoint(o);
		i = Q.transformVector(i).normalized();
		j = Q.transformVector(j).normalized();
		k = Q.transformVector(k).normalized();
	}

	public void rotateOrigin(double _dax, double _day, double _daz, Frame3D _ref) {
		Quaternion 	qx = Quaternion.FromAxisAngle(_ref.getXAxis(), _dax), 
					qy = Quaternion.FromAxisAngle(_ref.getYAxis(), _day),
					qz = Quaternion.FromAxisAngle(_ref.getZAxis(), _daz), 
					Q  = qx.times(qy).times(qz);

		o = Q.transformPoint(o);
	}
	public void rotateAxes(double _dax, double _day, double _daz, Frame3D _ref) {
		Quaternion  qx = Quaternion.FromAxisAngle(_ref.getXAxis(), _dax), 
					qy = Quaternion.FromAxisAngle(_ref.getYAxis(), _day),
					qz = Quaternion.FromAxisAngle(_ref.getZAxis(), _daz), 
					Q  = qx.times(qy).times(qz);

		i = Q.transformVector(i).normalized();
		j = Q.transformVector(j).normalized();
		k = Q.transformVector(k).normalized();
	}
	public void rotateOriginAndAxes(double _dax, double _day, double _daz, Frame3D _ref) {
		Quaternion  qx = Quaternion.FromAxisAngle(_ref.getXAxis(), _dax), 
					qy = Quaternion.FromAxisAngle(_ref.getYAxis(), _day),
					qz = Quaternion.FromAxisAngle(_ref.getZAxis(), _daz), 
					Q  = qx.times(qy).times(qz);

		o = Q.transformPoint(o);
		i = Q.transformVector(i).normalized();
		j = Q.transformVector(j).normalized();
		k = Q.transformVector(k).normalized();
	}
*/
	@Override
	public int compareTo(Object o) {
		return 0;
	}
	public int compareTo(Frame<Point3D, Vector3D> o) {
		return 0;
	}

	public String toString() {
		NumberFormat df = new DecimalFormat("0.00");
		return "locate [o=" + o.toString(df) + ", i=" + i.toString(df) + ", j=" + j.toString(df) + ", k=" + k.toString(df) + "]";
	}

	@Override
	public Frame3D  	clone() {
		return new SimpleFrame3D(o, i, j, k);
	}

}
