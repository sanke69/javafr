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
package fr.java.math.geometry.plane;

import java.util.Set;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.BoundingBox;

public interface BoundingBox2D extends BoundingBox.TwoDims {

	public static interface Editable extends BoundingBox2D, BoundingBox.TwoDims.Editable {

		public default void 	set(BoundingBox2D _bbox) { set(_bbox.getX(), _bbox.getY(), _bbox.getWidth(), _bbox.getHeight()); }
		public default void 	set(Point2D _p, Dimension2D _d) { set(_p.getX(), _p.getY(), _d.getWidth(), _d.getHeight()); }
		public default void 	set(double _width, double _height) { set(0, 0, _width, _height); }
		public void 			set(double _x, double _y, double _width, double _height);

		public void 			setPosition(Point2D _p);
		public void				setSize(Dimension2D _d);

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

		public void  			setLeft(double _left);
	    public void  			setRight(double _right);
	    public void  			setTop(double _top);
	    public void  			setBottom(double _bottom);

	}

	public default boolean 	contains(Point2D _pt) {
		return contains(_pt.getX(), _pt.getY());
	}
	@Override
	public default boolean 	contains(BoundingBox _bbox) {
		if(!(_bbox instanceof BoundingBox.TwoDims))
			throw new NotYetImplementedException();
		
		BoundingBox.TwoDims bbox = (BoundingBox.TwoDims) _bbox;
		return contains(bbox.getMinX(), bbox.getMinY()) && contains(bbox.getMaxX(), bbox.getMaxY());
	}
	public boolean 			contains(BoundingBox2D _bb);

	public default boolean 	intersects(BoundingBox _bbox) {
		if(!(_bbox instanceof BoundingBox2D))
			throw new NotYetImplementedException();
		
		BoundingBox2D bbox = (BoundingBox2D) _bbox;
		return intersects(bbox);
	}
	public boolean 			intersects(BoundingBox2D _bb);

	public Set<Point2D> 	getTops();

	public Point2D 			getPosition();
	public Point2D 			getCenter();
	public Dimension2D		getSize();

}
