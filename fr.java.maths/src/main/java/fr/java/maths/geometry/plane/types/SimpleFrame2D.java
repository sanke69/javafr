package fr.java.maths.geometry.plane.types;

import fr.java.math.algebra.matrix.specials.Matrix44D;
import fr.java.math.geometry.Frame;
import fr.java.math.geometry.plane.Frame2D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Vector2D;
import fr.java.maths.Points;
import fr.java.maths.algebra.Vectors;
import fr.java.maths.geometry.space.transformations.Transformation3D;

public class SimpleFrame2D implements Frame2D {
	private static final long serialVersionUID = -3996339218987082333L;

	public Point2D  o;
	public Vector2D i, j;
	
	public Frame2D w;
	
	Transformation3D 	m_Projection;
    boolean 			m_IsUpToDate;

	public SimpleFrame2D() {
		super();
		o = Points.O2();
		i = Vectors.of(1, 0);
		j = Vectors.of(0, 1);
        m_IsUpToDate = false;
	}
	public SimpleFrame2D(Point2D _o, Vector2D _i, Vector2D _j, Vector2D _k) {
		super();
		o = _o;
		i = (Vector2D) _i.normalized();
		j = (Vector2D) _j.normalized();
        m_IsUpToDate = false;
	}
	
	@Override
	public void moveTo(Point2D _position) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void translate(Vector2D _vect_speed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Point2D getOrigin() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Vector2D getXAxis() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Vector2D getYAxis() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void translate(double tx, double ty) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void moveTo(double tx, double ty) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void rotateOrigin(double ax) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void rotateAxes(double a) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void rotateOriginAndAxes(double ax) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	public int compareTo(Frame<Point2D, Vector2D> o) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Matrix44D getModelMatrix() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Frame<Point2D, Vector2D> getParentFrame() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void moveTo(double _px, double _py, Frame2D _frame) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void moveTo(Point2D _p, Frame2D _frame) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void translate(double _tx, double _ty, Frame2D _frame) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void translate(Vector2D _d, Frame2D _frame) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void rotateOrigin(double _a, Frame2D _frame) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void rotateAxes(double _a, Frame2D _frame) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void rotateOriginAndAxes(double _a, Frame2D _frame) {
		// TODO Auto-generated method stub
		
	}

/*
    public void translate(double tx, double ty, double tz) {
        o.x = tx;
        o.y = ty;

        m_IsUpToDate = false;

    }
    public void rotateOrigin(double ax, double ay, double az) {
        Quaternion Q = Quaternion.FromEulerAngle(ax, ay, az);

        Point3D op = Q.transformPoint(new Point3D(o.x, o.y, 0.0));
        o = new Point2D(op.x, op.y);

        m_IsUpToDate = false;

    }
    public void rotateAxes(double ax, double ay, double az) {
        Quaternion Q = Quaternion.FromEulerAngle(ax, ay, az);

        Vector3D ip = Q.transformVector(new Vector3D(i.x, i.y, 0.0));
        Vector3D jp = Q.transformVector(new Vector3D(j.x, j.y, 0.0));
        i = (Vector2D) new Vector2D(ip.x, ip.y).normalized();
        j = (Vector2D) new Vector2D(jp.x, jp.y).normalized();

        m_IsUpToDate = false;

    }
    public void rotateOriginAndAxes(double ax, double ay, double az) {
        Quaternion Q = Quaternion.FromEulerAngle(ax, ay, az);

        Point3D  op = Q.transformPoint(new Point3D(o.x, o.y, 0.0));
        Vector3D ip = Q.transformVector(new Vector3D(i.x, i.y, 0.0));
        Vector3D jp = Q.transformVector(new Vector3D(j.x, j.y, 0.0));
        o = new Point2D(op.x, op.y);
        i = (Vector2D) new Vector2D(ip.x, ip.y).normalized();
        j = (Vector2D) new Vector2D(jp.x, jp.y).normalized();

        m_IsUpToDate = false;

    }

    public void translateFrom(double tx, double ty, double tz, Locate2D r) {
        o = (Point2D) r.o.plus(r.i.times(tx)).plus(j.times(ty)).plus(k.times(tz));

        m_IsUpToDate = false;
    }
    public void rotateOriginFrom(double ax, double ay, double az, Locate2D r) {
        Quaternion qx = Quaternion.FromAxisAngle(r.i, ax), 
        		   qy = Quaternion.FromAxisAngle(r.j, ay), 
        		   qz = Quaternion.FromAxisAngle(r.k, az), Q;
        Q = qx.times(qy).times(qz);

        o = (Point2D) Q.transformPoint(o);

        m_IsUpToDate = false;

    }
    public void rotateAxesFrom(double ax, double ay, double az, Locate2D r) {
        Quaternion qx = Quaternion.FromAxisAngle(r.i, ax), 
	     		   qy = Quaternion.FromAxisAngle(r.j, ay), 
	     		   qz = Quaternion.FromAxisAngle(r.k, az), 
	     		   Q = qx.times(qy).times(qz);

        i = (Vector2D) Q.transformVector(i).normalized();
        j = (Vector2D) Q.transformVector(j).normalized();
        k = (Vector2D) Q.transformVector(k).normalized();

        m_IsUpToDate = false;
    }
    public void rotateOriginAndAxesFrom(double ax, double ay, double az, Locate2D r) {
        Quaternion qx = Quaternion.FromAxisAngle(r.i, ax), 
	     		   qy = Quaternion.FromAxisAngle(r.j, ay), 
	     		   qz = Quaternion.FromAxisAngle(r.k, az), 
	     		   Q = qx.times(qy).times(qz);

        o = (Point2D) Q.transformPoint(o);
        i = (Vector2D) Q.transformVector(i).normalized();
        j = (Vector2D) Q.transformVector(j).normalized();
        k = (Vector2D) Q.transformVector(k).normalized();

        m_IsUpToDate = false;
    }

    public Locate2D update() {
        if(m_IsUpToDate)
            return this;

        m00 = i.x;
        m10 = j.x;
        m20 = k.x;
        m30 = 0.0f;

        m01 = i.y;
        m11 = j.y;
        m21 = k.y;
        m31 = 0.0f;

        m02 = i.z;
        m12 = j.z;
        m22 = k.z;
        m32 = 0.0f;

        m03 = i.dotProduct(o);
        m13 = j.dotProduct(o);
        m23 = k.dotProduct(o);
        m33 = 1.0f;

        m_IsUpToDate = true;

        return this;
    }

    public Point2D transformPoint(Point2D p) {
        update();
        Point2D q = new Point2D();

        q.x = p.x * m00 + p.y * m01 + m03;
        q.y = p.x * m10 + p.y * m11 + m13;

        return q;

    }

    public Vector2D transformVector(Vector2D v) {
        update();
        Vector2D w = new Vector2D();

        w.x = v.x * m00 + v.y * m01;
        w.y = v.x * m10 + v.y * m11;

        return w;

    }
*/
}
