/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
**/
package fr.java.math.geometry.space;

import fr.java.math.algebra.matrix.generic.Matrix44D;
import fr.java.math.algebra.vector.generic.Vector3D;
import fr.java.math.geometry.Frame;

public interface Frame3D extends Frame<Point3D, Vector3D> {

	public Matrix44D 			getModelMatrix	();

	public void 				setParentFrame		(final Frame3D _other);
	public Frame3D 				getParentFrame		();

	public void 				set					(final Point3D _o,final Vector3D _x, final Vector3D _y, final Vector3D _z);
	public void 				setOrigin			(final Point3D _o);
	public void 				setAxis				(final Vector3D _i, final Vector3D _j, final Vector3D _k);

	public Point3D  			getOrigin			();
	public Vector3D 			getXAxis			();
	public Vector3D 			getYAxis			();
	public Vector3D 			getZAxis			();

	public void 				rotateOrigin		(double _ax, double _ay, double _az);
	public void 				rotateAxes			(double _ax, double _ay, double _az);
	public void 				rotateOriginAndAxes	(double _ax, double _ay, double _az);

	public void 				rotateOrigin		(double _ax, double _ay, double _az, Frame3D _in_frame);
	public void 				rotateAxes			(double _ax, double _ay, double _az, Frame3D _in_frame);
	public void 				rotateOriginAndAxes	(double _ax, double _ay, double _az, Frame3D _in_frame);

	public Frame3D  			clone				();
	
/*
	public void 				translate			(double _dx, double _dy, double _dz);
	public void 				translate			(double _dx, double _dy, double _dz, Frame3D _in_frame);
	public void 				translate			(Vector3D _vect_speed);
	public void 				translate			(Vector3D _vect_speed, Frame3D _in_locate);
/*
	public void 				rotateOrigin		(double _ax, double _ay, double _az);
	public void 				rotateAxes			(double _ax, double _ay, double _az);
	public void 				rotateOriginAndAxes	(double _ax, double _ay, double _az);

	public void 				rotateOrigin		(double _ax, double _ay, double _az, Frame3D _in_frame);
	public void 				rotateAxes			(double _ax, double _ay, double _az, Frame3D _in_frame);
	public void 				rotateOriginAndAxes	(double _ax, double _ay, double _az, Frame3D _in_frame);
*/
/*
	public static Point3D  local2world(Point3D _pt_local, Frame3D _local) {
//		Point3D world = null;
/*
		if(_local.getWorld().isPresent()) {
			Locate3D subWorld = _local.getWorld().get();
			return local2world(_pt_local, subWorld);
		} else {
			Matrix4d T = Transformation3D.getTransformation(_local).inverse();
			return Transformation3D.transform(T, (Vector3D) _pt_local);
		}
* /
		Point3D  world = _pt_local;
		Matrix33d m    = new Matrix33d(_local.getXAxis().getX(), _local.getXAxis().getY(), _local.getZAxis().getX(), 
									  _local.getYAxis().getX(), _local.getYAxis().getY(), _local.getYAxis().getZ(), 
									  _local.getZAxis().getX(), _local.getZAxis().getY(), _local.getZAxis().getZ()),
				 inv   = m.inverse();

		if(inv == null)
		  return null;
		
		world = inv.times(_pt_local);
		world.plusEquals(_local.getOrigin());

		return world;
	};
	public static Point3D  world2local(Point3D _pt_world, Frame3D _local) { 
//		Point3D local = null;

		if(_local.getParentFrame() != null) {
			Frame3D subWorld = _local.getParentFrame();
			return local2world(_pt_world, subWorld);
		} else {
			Transformation3D T = fromLocate(_local);
			return Transformation3D.transform(T, _pt_world);
		}
	};
	public static Point3D  local2local(Point3D  _pt_from, Frame3D _from, Frame3D _to) { return null; };

	public static Vector3D local2world(Vector3D _vec_local, Frame3D _local) { 
//		Point3D local = null;
/*
		if(_local.getWorld().isPresent()) {
			Locate3D subWorld = _local.getWorld().get();
			return local2world(_vec_local, subWorld);
		} else {
			Matrix4d T = Transformation3D.getTransformation(_local).inverse();

			return Transformation3D.transform(T, _vec_local);
		}
* /
		Vector3D world = _vec_local;
		Matrix33d m    = new Matrix33d(_local.getXAxis().getX(), _local.getXAxis().getY(), _local.getZAxis().getX(), 
									  _local.getYAxis().getX(), _local.getYAxis().getY(), _local.getYAxis().getZ(), 
									  _local.getZAxis().getX(), _local.getZAxis().getY(), _local.getZAxis().getZ()),
				 inv   = m.inverse();
		
		if(inv == null)
		  return null;
		
		world = inv.times(_vec_local);
		
		return world;
	};
	public static Vector3D world2local(Vector3D _vec__world, Frame3D _local) { 
//		Point3D local = null;

		if(_local.getParentFrame() != null) {
			Frame3D subWorld = _local.getParentFrame();
			return local2world(_vec__world, subWorld);
		} else {
			Transformation3D T = fromLocate(_local);
			return Transformation3D.transform(T, _vec__world);
		}
	};
	public static Vector3D local2local(Vector3D  _pt_from, Frame3D _from, Frame3D _to) { return null; };

	public static Frame3D local2world(Frame3D _locate, Frame3D _parent) {
		return null;
	}
	@Deprecated
	public default Point3D get_world_coordinate(Point3D _local, Frame3D _land, boolean _vector) {
		Point3D  world = _local;
		Matrix33d m    = new Matrix33d(_land.getXAxis().getX(), _land.getXAxis().getY(), _land.getZAxis().getX(), _land.getYAxis().getX(), _land.getYAxis().getY(), _land.getYAxis().getZ(), _land.getZAxis().getX(), _land.getZAxis().getY(), _land.getZAxis().getZ()),
				 inv   = m.inverse();

		if(inv == null)
		  return null;
		
		world = inv.times(_local);
		if(!_vector)
			world.plusEquals(_land.getOrigin());

		return world;
	}
	@Deprecated
	public default Point3D get_local_coordinate(Point3D _world, Frame3D _land, boolean _vector) {
		Point3D  local = _world;
		Matrix33d m    = new Matrix33d(_land.getXAxis().getX(), _land.getXAxis().getY(), _land.getZAxis().getX(), _land.getYAxis().getX(), _land.getYAxis().getY(), _land.getYAxis().getZ(), _land.getZAxis().getX(), _land.getZAxis().getY(), _land.getZAxis().getZ()),
				 inv   = m.inverse();

		if(inv == null)
		  return null;
		
		if(!_vector)
			local = _world.minus(_land.getOrigin());
		local = m.times(local);

		return local;
	}
*/
}
