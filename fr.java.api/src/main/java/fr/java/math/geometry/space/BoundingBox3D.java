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

import java.util.Set;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.BoundingBox;

public interface BoundingBox3D extends BoundingBox.ThreeDims {

	public static interface Editable extends BoundingBox3D, BoundingBox.ThreeDims.Editable {

		public default void 	set(BoundingBox3D _bbox) { set(_bbox.getX(), _bbox.getY(), _bbox.getZ(), _bbox.getWidth(), _bbox.getHeight(), _bbox.getDepth()); }
		public default void 	set(Point3D _p, Dimension3D _d) { set(_p.getX(), _p.getY(), _p.getZ(), _d.getWidth(), _d.getHeight(), _d.getDepth()); }
		public default void 	set(double _width, double _height, double _depth) { set(0, 0, 0, _width, _height, _depth); }
		public void 			set(double _x, double _y, double _z, double _width, double _height, double _depth);

		public void 			setPosition(Point3D _p);
		public void				setSize(Dimension3D _d);

//		public void 			setTopLeft(Point2D _topLeft);
//		public void 			setTopRight(Point2D _topRight);
//		public void 			setBottomLeft(Point2D _bottomLeft);
//		public void 			setBottomRight(Point2D _bottomRight);

	    public void 			setX(double _x);
	    public void 			setMinX(double _x);
	    public void 			setMaxX(double _x);
	    public void 			setCenterX(double _x);
	    public void 			setWidth(double _w);

	    public void 			setY(double _y);
	    public void 			setMinY(double _y);
	    public void 			setMaxY(double _y);
	    public void 			setCenterY(double _y);
	    public void 			setHeight(double _h);

	    public void 			setZ(double _y);
	    public void 			setMinZ(double _y);
	    public void 			setMaxZ(double _y);
	    public void 			setCenterZ(double _y);
	    public void 			setDepth(double _h);

		public void  			setLeft(double _left);
	    public void  			setRight(double _right);
	    public void  			setTop(double _top);
	    public void  			setBottom(double _bottom);
	    public void  			setFront(double _front);
	    public void  			setBack(double _back);

	}

	public default boolean 	contains(Point3D _pt) {
		return contains(_pt.getX(), _pt.getY(), _pt.getZ());
	}
	@Override
	public default boolean 	contains(BoundingBox _bbox) {
		if(!(_bbox instanceof BoundingBox.TwoDims))
			throw new NotYetImplementedException();
		
		BoundingBox.TwoDims bbox = (BoundingBox.TwoDims) _bbox;
		return contains(bbox.getMinX(), bbox.getMinY()) && contains(bbox.getMaxX(), bbox.getMaxY());
	}
	public boolean 			contains(BoundingBox3D _bb);

	public default boolean 	intersects(BoundingBox _bbox) {
		if(!(_bbox instanceof BoundingBox3D))
			throw new NotYetImplementedException();
		
		BoundingBox3D bbox = (BoundingBox3D) _bbox;
		return intersects(bbox);
	}
	public boolean 			intersects(BoundingBox3D _bb);

	public Set<Point3D> 	getTops();

	public Point3D 			getPosition();
	public Point3D 			getCenter();
	public Dimension3D		getSize();

}
