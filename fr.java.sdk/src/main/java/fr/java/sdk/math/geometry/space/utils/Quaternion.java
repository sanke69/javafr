/** ************************************************************************ **\
 * Copyright (c) 2007-?XYZ Steve PECHBERTI                                    *
 *                                                                            *
 * @author <a href='mailto:steve.pechberti@gmail.com'> Steve PECHBERTI </a>   *
 *                                                                            *
 * @section license License                                                   *
 *    [EN] This program is free software:                                     *
 *         you can redistribute it and/or modify it under the terms of        * 
 *         the GNU General Public License as published by                     *
 *         the Free Software Foundation, either version 3 of the License, or  *
 *         (at your option) any later version.                                *
 *         You should have received a copy of the GNU General Public License  *
 *         along with this program. If not, see                               *
 *            <http://www.gnu.org/licenses/gpl.html>                          *
 *    [FR] Ce programme est un logiciel libre ; vous pouvez le redistribuer   * 
 *         ou le modifier suivant les termes de la GNU General Public License *
 *         telle que publiée par la Free Software Foundation ;                *
 *         soit la version 3 de la licence, soit (à votre gré) toute version  *
 *         ultérieure.                                                        *
 *         Vous devez avoir reçu une copie de la GNU General Public License   *
 *         en même temps que ce programme ; si ce n'est pas le cas, consultez *
 *            <http://www.gnu.org/licenses/gpl.html>                          *
 *                                                                            *
 * @section disclaimer Disclaimer                                             *
 *    [EN] This program is distributed in the hope that it will be useful,    *
 *         but WITHOUdouble ANY WARRANTY; without even the implied warranty of     *
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.               *
 *    [FR] Ce programme est distribué dans l'espoir qu'il sera utile,         *
 *         mais SANS AUCUNE GARANTIE, sans même la garantie implicite de      *
 *         VALEUR MARCHANDE ou FONCTIONNALITE POUR UN BUdouble PARTICULIER.        *
 *                                                                            *
\** ************************************************************************ **/
package fr.java.sdk.math.geometry.space.utils;

import fr.java.math.geometry.space.Point3D;
import fr.java.math.geometry.space.Vector3D;
import fr.java.sdk.math.Points;
import fr.java.sdk.math.algebra.Vectors;
import fr.java.sdk.math.algebra.matrices.Matrix44d;
import fr.java.sdk.math.algebra.vectors.DoubleVector3D;
import fr.java.sdk.math.algebra.vectors.DoubleVector4D;


// cf. Tait-Bryan for "Euler"
public class Quaternion {

	public static Quaternion identity() {
		return new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);
	}
	public static Quaternion fromMatrix(final Matrix44d Matrix) {
		Quaternion Q = new Quaternion();
		double Trace = Matrix.get(0, 0) + Matrix.get(1, 1) + Matrix.get(2, 2) + 1;

		if(Trace > 0) {
			double s = 0.5f / Math.sqrt(Trace);
			Q.x = (Matrix.get(2, 1) - Matrix.get(1, 2)) * s;
			Q.y = (Matrix.get(0, 2) - Matrix.get(2, 0)) * s;
			Q.z = (Matrix.get(1, 0) - Matrix.get(0, 1)) * s;
			Q.w = 0.25f / s;
		} else {
			if((Matrix.get(0, 0) > Matrix.get(1, 1)) && (Matrix.get(0, 0) > Matrix.get(2, 2))) {
				double s = Math.sqrt(1 + Matrix.get(0, 0) - Matrix.get(1, 1) - Matrix.get(2, 2)) * 2;
				Q.x = 0.5f / s;
				Q.y = (Matrix.get(0, 1) + Matrix.get(1, 0)) / s;
				Q.z = (Matrix.get(0, 2) + Matrix.get(2, 0)) / s;
				Q.w = (Matrix.get(1, 2) + Matrix.get(2, 1)) / s;
			} else if(Matrix.get(1, 1) > Matrix.get(2, 2)) {
				double s = Math.sqrt(1 - Matrix.get(0, 0) + Matrix.get(1, 1) - Matrix.get(2, 2)) * 2;
				Q.x = (Matrix.get(0, 1) + Matrix.get(1, 0)) / s;
				Q.y = 0.5f / s;
				Q.z = (Matrix.get(1, 2) + Matrix.get(2, 1)) / s;
				Q.w = (Matrix.get(0, 2) + Matrix.get(2, 0)) / s;
			} else {
				double s = Math.sqrt(1 - Matrix.get(0, 0) - Matrix.get(1, 1) + Matrix.get(2, 2)) * 2;
				Q.x = (Matrix.get(0, 2) + Matrix.get(2, 0)) / s;
				Q.y = (Matrix.get(1, 2) + Matrix.get(2, 1)) / s;
				Q.z = 0.5f / s;
				Q.w = (Matrix.get(0, 1) + Matrix.get(1, 0)) / s;
			}
		}

		return Q;
	}
	public static Quaternion fromAxisAngle(final Vector3D _v, final double _angle) {
		double A = _angle, sinA;
		A *= 0.5f;
		Vector3D vn = _v.normalized();

		sinA = (double) Math.sin(A);

		return new Quaternion(vn.getX() * sinA, vn.getY() * sinA, vn.getZ() * sinA, (double) Math.cos(A));
	}
	// TODO:: BUG:: MUST BE: Yaw - Pitch - Roll
	public static Quaternion fromEulerAngle(final double _pitch, final double _yaw, final double _roll) {
		double PIOVER180 = (double) (Math.PI / 180.0f);
		double p = _pitch * PIOVER180 / 2.0f;
		double y = _yaw * PIOVER180 / 2.0f;
		double r = _roll * PIOVER180 / 2.0f;

		double sinp = (double) Math.sin(p);
		double siny = (double) Math.sin(y);
		double sinr = (double) Math.sin(r);
		double cosp = (double) Math.cos(p);
		double cosy = (double) Math.cos(y);
		double cosr = (double) Math.cos(r);

		Quaternion q = new Quaternion(
				sinr * cosp * cosy - cosr * sinp * siny,
				cosr * sinp * cosy + sinr * cosp * siny,
				cosr * cosp * siny - sinr * sinp * cosy,
				cosr * cosp * cosy + sinr * sinp * siny);

		q.normalize();
		return q;

		/*
				Quaternion Qx = new Quaternion(new Vector3d(1, 0, 0), _pitch);
				Quaternion Qy = new Quaternion(new Vector3d(0, 1, 0), _yaw);
				Quaternion Qz = new Quaternion(new Vector3d(0, 0, 1), _roll);
				Quaternion Q  = Qx.multiply(Qy).multiply(Qz);
				return Q;
		*/
	}

	protected double x, y, z, w;

	public Quaternion() {
		super();

		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
		w = 1.0f;
	}
	public Quaternion(final double _x, final double _y, final double _z, final double _w) {
		super();

		x = _x;
		y = _y;
		z = _z;
		w = _w;
	}
	public Quaternion(final Vector3D _axis, final double _angle) {
		super();

		double cos = Math.cos(_angle / 2.0);
		double sin = Math.sin(_angle / 2.0);

		x = _axis.getX() * sin;
		y = _axis.getY() * sin;
		z = _axis.getZ() * sin;
		w = cos;

		double norm = x * x + y * y + z * z + w * w;

		if(Math.abs(norm) > 1e-13) {
			x /= norm;
			y /= norm;
			z /= norm;
			w /= norm;
		}
	}
	public Quaternion(final Matrix44d _m) {
		super();

		double trace = _m.m00 + _m.m11 + _m.m22 + 1.0f;

		if(trace > 0) {
			double s = 0.5f / (double) Math.sqrt(trace);
			x = (_m.m21 - _m.m12) * s;
			y = (_m.m02 - _m.m20) * s;
			z = (_m.m10 - _m.m01) * s;
			w = 0.25f / s;
		} else {
			if((_m.m00 > _m.m11) && (_m.m00 > _m.m22)) {
				double s = (double) Math.sqrt(1 + _m.m00 - _m.m11 - _m.m22) * 2;
				x = 0.5f / s;
				y = (_m.m01 + _m.m10) / s;
				z = (_m.m02 + _m.m20) / s;
				w = (_m.m12 + _m.m21) / s;
			} else if(_m.m11 > _m.m22) {
				double s = (double) Math.sqrt(1 - _m.m00 + _m.m11 - _m.m22) * 2;
				x = (_m.m01 + _m.m10) / s;
				y = 0.5f / s;
				z = (_m.m12 + _m.m21) / s;
				w = (_m.m02 + _m.m20) / s;
			} else {
				double s = (double) Math.sqrt(1 - _m.m00 - _m.m11 + _m.m22) * 2;
				x = (_m.m02 + _m.m20) / s;
				y = (_m.m12 + _m.m21) / s;
				z = 0.5f / s;
				w = (_m.m01 + _m.m10) / s;
			}
		}
	}
	public Quaternion(final Quaternion _q) {
		super();

		x = _q.x;
		y = _q.y;
		z = _q.z;
		w = _q.w;
	}

	public final void set(final Quaternion _q) {
		x = _q.x;
		y = _q.y;
		z = _q.z;
		w = _q.w;
	}

	// OK
	public final Quaternion times(final Quaternion _r) {
		return new Quaternion(w * _r.x + x * _r.w + y * _r.z - z * _r.y,
							  w * _r.y + y * _r.w + z * _r.x - x * _r.z,
							  w * _r.z + z * _r.w + x * _r.y - y * _r.x,
							  w * _r.w - x * _r.x - y * _r.z - z * _r.z);
	}
	public final Quaternion setIdentity() {
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
		w = 1.0f;
		return this;
	}

	// OK
	public final void normalize() {
		double norm = x * x + y * y + z * z + w * w;

		if(Math.abs(norm) > 1e-13) {
			x /= norm;
			y /= norm;
			z /= norm;
			w /= norm;
		}
	}
	// OK
	public final Quaternion conjugate() {
		return new Quaternion(-x, -y, -z, w);
	}
	// OK
	public final Quaternion inverse() {
		double norm = x * x + y * y + z * z + w * w;
		return new Quaternion(x / norm, -y / norm, -z / norm, -w / norm);
	}
	// OK
	public final Matrix44d toMatrix() {
		double xx = x * x, xy = x * y, xz = x * z,
				xw = x * w, yy = y * y, yz = y * z,
				yw = y * w, zz = z * z, zw = z * w;

		return new Matrix44d(1 - 2 * (yy + zz), 2 * (xy - zw), 2 * (xz + yw), 0,
				2 * (xy + zw), 1 - 2 * (xx + zz), 2 * (yz - xw), 0,
				2 * (xz - yw), 2 * (yz + xw), 1 - 2 * (xx + yy), 0,
				0, 0, 0, 1);
	}
	public final DoubleVector4D toAxisAngle() {
		DoubleVector4D axisAngle = new DoubleVector4D();

		axisAngle.setW( (double) Math.acos(w) * 2.0f );

		double norm = (double) Math.sqrt(x * x + y * y + z * z);
		if(Math.abs(norm) > 1e-6) {
			axisAngle.set(x / norm, y / norm, z / norm, 1.0);
		} else {
			axisAngle.set(0.0, 1.0, 0.0, 1.0);
		}

		return axisAngle;
	}

	public final Point3D transformPoint(final Point3D _p) {
		double m0 = 1.0f - 2.0f * (y * y + z * z);
		double m1 = 2.0f * (x * y + z * w);
		double m2 = 2.0f * (x * z - y * w);
		double m4 = 2.0f * (x * y - z * w);
		double m5 = 1.0f - 2.0f * (x * x + z * z);
		double m6 = 2.0f * (z * y + x * w);
		double m8 = 2.0f * (x * z + y * w);
		double m9 = 2.0f * (y * z - x * w);
		double m10 = 1.0f - 2.0f * (x * x + y * y);

		return Points.of(	_p.getX() * m0 + _p.getY() * m4 + _p.getZ() * m8, 
							_p.getX() * m1 + _p.getY() * m5 + _p.getZ() * m9, 
							_p.getX() * m2 + _p.getY() * m6 + _p.getZ() * m10 );
	}
	public final Vector3D transformVector(final Vector3D _p) {
		double m0 = 1.0f - 2.0f * (y * y + z * z);
		double m1 = 2.0f * (x * y + z * w);
		double m2 = 2.0f * (x * z - y * w);
		double m4 = 2.0f * (x * y - z * w);
		double m5 = 1.0f - 2.0f * (x * x + z * z);
		double m6 = 2.0f * (z * y + x * w);
		double m8 = 2.0f * (x * z + y * w);
		double m9 = 2.0f * (y * z - x * w);
		double m10 = 1.0f - 2.0f * (x * x + y * y);

		return Vectors.of(	_p.getX() * m0 + _p.getY() * m4 + _p.getZ() * m8,
	                       	_p.getX() * m1 + _p.getY() * m5 + _p.getZ() * m9,
                           	_p.getX() * m2 + _p.getY() * m6 + _p.getZ() * m10 );
	}
	// OK
	public final Vector3D transform(final Vector3D _p) {
		Vector3D p = _p;
		p.normalized();

		Quaternion vecQuat = new Quaternion(p.getX(), p.getY(), p.getZ(), 0.0f), resQuat = new Quaternion();
		resQuat = vecQuat.times(conjugate());
		resQuat = this.times(resQuat);

		return new DoubleVector3D(resQuat.x, resQuat.y, resQuat.z);
	}

	public final Quaternion clone() {
		return new Quaternion(this);
	}

	// http://stackoverflow.com/questions/30145414/rotate-a-3d-object-on-3-axis-in-javafx-properly
	/*
	protected void matrixRotateNode0(Node n, double alf, double bet, double gam){
	    double A11=Math.cos(alf)*Math.cos(gam);
	    double A12=Math.cos(bet)*Math.sin(alf)+Math.cos(alf)*Math.sin(bet)*Math.sin(gam);
	    double A13=Math.sin(alf)*Math.sin(bet)-Math.cos(alf)*Math.cos(bet)*Math.sin(gam);
	    double A21=-Math.cos(gam)*Math.sin(alf);
	    double A22=Math.cos(alf)*Math.cos(bet)-Math.sin(alf)*Math.sin(bet)*Math.sin(gam);
	    double A23=Math.cos(alf)*Math.sin(bet)+Math.cos(bet)*Math.sin(alf)*Math.sin(gam);
	    double A31=Math.sin(gam);
	    double A32=-Math.cos(gam)*Math.sin(bet);
	    double A33=Math.cos(bet)*Math.cos(gam);
	
	    double d = Math.acos((A11+A22+A33-1d)/2d);
	    if(d!=0d){
	        double den=2d*Math.sin(d);
	        Point3D p= new Point3D((A32-A23)/den,(A13-A31)/den,(A21-A12)/den);
	        n.setRotationAxis(p);
	        n.setRotate(Math.toDegrees(d));                    
	    }
	}
	*/
}
