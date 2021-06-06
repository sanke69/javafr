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
 * @file     BoundingBox.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.math.geometry;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.java.lang.exceptions.IllegalAccessArgument;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.topology.Coordinate;
import fr.java.math.topology.CoordinateSystem;

public interface BoundingBox extends Dimension, /*Duplicable, Comparable<Object>,*/ Serializable {

	public static interface Editable extends BoundingBox {

		public void set(BoundingBox _source);

	}

	public static interface OneDim extends BoundingBox, 
										   Dimension.OneDim {

		public static interface Editable extends BoundingBox.Editable, 
												 Dimension.OneDim.Editable, 
												 BoundingBox.OneDim {

			@Override
			public default void 	set(BoundingBox _source) {
				if(!(_source instanceof BoundingBox.OneDim))
					throw new IllegalArgumentException();
				
				set((BoundingBox.OneDim) _source);
			}
			public default void 	set(BoundingBox.OneDim _source) {
				setLeft(_source.getLeft());
				setRight(_source.getRight());
			}
			public default void 	set(double _width) { set(0, _width); }
			public void 			set(double _x, double _width);

		    public void 			setX(double _x);
		    public void 			setMinX(double _x);
		    public void 			setMaxX(double _x);
		    public void 			setCenterX(double _x);
		    public void 			setWidth(double _w);

			public void  			setLeft(double _left);
		    public void  			setRight(double _right);

		}

		@Override
		public default boolean 	isEmpty()        { return getWidth() == 0; }

		public double  			getX();
		public double  			getWidth();

		public default double  	getMinX()        { return getX(); }
	    public default double  	getMaxX()        { return getMinX() + getWidth(); }
	    public default double  	getCenterX()     { return (getMinX() + getMaxX()) / 2; }

		public default double  	getLeft()        { return getMinX(); }
	    public default double  	getRight()       { return getMaxX(); }

		public default double 	getPerimeter()   { return getWidth(); }

		public default boolean 	contains(double _x) {
			if(isEmpty())
				return false;
			return _x >= getMinX() && _x <= getMaxX();
		}
		public default boolean 	contains(double _x, double _w) {
			return contains(_x) && contains(_x + _w);
		}
		public default boolean 	contains(Coordinate _pt) {
			if(_pt instanceof Coordinate.OneDim)
				return contains(((Coordinate.OneDim) _pt).getFirst());
			throw new IllegalAccessArgument();
		}
		public default boolean 	contains(Coordinate.OneDim _pt) {
			return contains(_pt.getFirst());
		}
		public default boolean 	contains(BoundingBox _pt) {
			if(_pt instanceof BoundingBox.OneDim)
				return contains(((BoundingBox.OneDim) _pt));
			throw new IllegalAccessArgument();
		}
		public default boolean 	contains(BoundingBox.OneDim _bb) {
			return _bb.getMinX() > getMinX() && _bb.getMaxX() < getMaxX();
		}

		public default boolean 	intersects(double _x, double _w) {
			if (isEmpty() || _w <= 0)
				return false;

			return (getMinX() < _x + _w && getMaxX() > _x);
		}
		public default boolean 	intersects(BoundingBox _pt) {
			if(_pt instanceof BoundingBox.OneDim)
				return contains(((BoundingBox.OneDim) _pt));
			throw new IllegalAccessArgument();
		}
		public default boolean 	intersects(BoundingBox.OneDim _bb) {
			if(isEmpty() || _bb == null || _bb.isEmpty())
				return false;

			boolean inside = false, outside = false;

			if(_bb.getMinX() > getMinX() && _bb.getMinX() < getMaxX()) inside = true; else outside = true;
			if(_bb.getMaxX() > getMinX() && _bb.getMaxX() < getMaxX()) inside = true; else outside = true;

			return inside && outside;
			
		}

		@Override
		public default String 	toString(NumberFormat _nf) { return "(" + _nf.format(getMinX()) + "), [" + _nf.format(getWidth()) + "]"; }

//		@Override
//		public default BoundingBox.OneDim clone() { throw new NotYetImplementedException(); }

	}
	public static interface TwoDims extends BoundingBox.OneDim, 
	   										Dimension.TwoDims {

		public static interface Editable extends BoundingBox.OneDim.Editable, 
												 Dimension.TwoDims.Editable, 
												 BoundingBox.TwoDims {

			@Override
			public default void 	set(BoundingBox _source) {
				if(!(_source instanceof BoundingBox.TwoDims))
					throw new IllegalArgumentException();
				
				set((BoundingBox.TwoDims) _source);
			}
			@Override // Just in case...
			public default void set(double _w) {
				throw new IllegalArgumentException();
			}
			@Override // Just in case...
			public default void 	set(BoundingBox.OneDim _source) {
				throw new IllegalArgumentException();
			}
			public default void 	set(BoundingBox.TwoDims _source) {
				setLeft(_source.getLeft());
				setRight(_source.getRight());
				setTop(_source.getTop());
				setBottom(_source.getBottom());
			}
			@Override
			public default void 	set(double _width, double _height) { set(0, 0, _width, _height); }
			public void 			set(double _x, double _y, double _width, double _height);

		    public void 			setY(double _y);
		    public void 			setMinY(double _y);
		    public void 			setMaxY(double _y);
		    public void 			setCenterY(double _y);
		    public void 			setHeight(double _h);

		    public void  			setTop(double _top);
		    public void  			setBottom(double _bottom);

			public default double 			getIntersectionArea(BoundingBox.TwoDims _bbox) {
				throw new NotYetImplementedException();
			}
			public default double 			getUnionArea(BoundingBox.TwoDims _bbox) {
				throw new NotYetImplementedException();
			}
			public default double 			getIOU(BoundingBox.TwoDims _bbox) {
				return getIntersectionArea(_bbox) / getUnionArea(_bbox);
			}

		}

		@Override
		public default boolean 	isEmpty()        { return getWidth() == 0 || getHeight() == 0; }

		public double  			getY();
		public double  			getHeight();

		public default double  	getMinY()        { return getY(); }
	    public default double  	getMaxY()        { return getMinY() + getHeight(); }
	    public default double  	getCenterY()     { return (getMinY() + getMaxY()) / 2; }

	    public default double  	getLeft()        { return getMinX(); }
	    public default double  	getRight()       { return getMaxX(); }

	    public default double  	getTop()         { return getMaxY(); }
	    public default double  	getBottom()      { return getMinY(); }

		public default double 	getPerimeter()   { return 2 * (getWidth() + getHeight()); }
		public default double 	getArea() 		 { return getWidth() * getHeight(); }

		public default boolean 	contains(double _x, double _y) {
			if(isEmpty() || Double.isNaN(_x) || Double.isNaN(_y))
				return false;
			return _x >= getMinX() && _x <= getMaxX()
				&& _y >= getMinY() && _y <= getMaxY();
		}
		public default boolean 	contains(double _x, double _y, double _w, double _h) {
			return contains(_x, _y)
				&& contains(_x + _w, _y + _h);
		}
		public default boolean 	contains(Coordinate _pt) {
			if(_pt instanceof Coordinate.TwoDims)
				return contains((Coordinate.TwoDims) _pt);
			throw new IllegalAccessArgument();
		}
		public default boolean 	contains(Coordinate.TwoDims _pt) {
			return contains( _pt.getFirst(), _pt.getSecond());
		}
		public default boolean 	contains(BoundingBox _pt) {
			if(_pt instanceof BoundingBox.TwoDims)
				return contains(((BoundingBox.TwoDims) _pt));
			throw new IllegalAccessArgument();
		}
		public default boolean 	contains(BoundingBox.TwoDims _bbox) {
			return contains(_bbox.getMinX(), _bbox.getMinY())
				&& contains(_bbox.getMaxX(), _bbox.getMaxY());
		}

		public default boolean 	intersects(double _x, double _y, double _w, double _h) {
			if (isEmpty() || _w < 0 || _h < 0)
				return false;

			return getMinX() < _x + _w && getMaxX() > _x 
				&& getMaxY() > _y      && getMinY() < _y + _h;
		}
		public default boolean 	intersects(BoundingBox _pt) {
			if(_pt instanceof BoundingBox.TwoDims)
				return contains(((BoundingBox.TwoDims) _pt));
			throw new IllegalAccessArgument();
		}
		public default boolean 	intersects(BoundingBox.TwoDims _bbox) {
//			if ((_bbox == null) || _bbox.isEmpty())
			if (_bbox == null)
				return false;
			return intersects(_bbox.getMinX(), _bbox.getMinY(), _bbox.getWidth(), _bbox.getHeight());
		}

		public default double 	getIntersectionArea(BoundingBox.TwoDims _bbox) {
			throw new NotYetImplementedException();
		}
		public default double 	getUnionArea(BoundingBox.TwoDims _bbox) {
			throw new NotYetImplementedException();
		}
		public default double 	getIOU(BoundingBox.TwoDims _bbox) {
			return getIntersectionArea(_bbox) / getUnionArea(_bbox);
		}

		@Override
		public default String 	toString(NumberFormat _nf) { return "(" + _nf.format(getMinX()) + ", " + _nf.format(getMinY()) + "), [" + _nf.format(getWidth()) + "x" + _nf.format(getHeight()) + "]"; }

//		@Override
//		public default BoundingBox.TwoDims clone() { throw new NotYetImplementedException(); }

	}
	public static interface ThreeDims extends BoundingBox.TwoDims, 
											  Dimension.ThreeDims {

		public static interface Editable extends BoundingBox.ThreeDims, BoundingBox.TwoDims.Editable, BoundingBox.Editable, Dimension.ThreeDims.Editable {

			@Override
			public default void 	set(double _w) {
				fr.java.math.geometry.Dimension.ThreeDims.Editable.super.set(_w);
			}
			@Override
			public default void 	set(double _w, double _h) {
				fr.java.math.geometry.Dimension.ThreeDims.Editable.super.set(_w, _h);
			}
			@Override
			public default void 	set(BoundingBox _source) {
				if(!(_source instanceof BoundingBox.ThreeDims))
					throw new IllegalArgumentException();
				
				set((BoundingBox.ThreeDims) _source);
			}
			@Override // Just in case...
			public default void 	set(BoundingBox.OneDim _source) {
				throw new IllegalArgumentException();
			}
			@Override // Just in case...
			public default void 	set(BoundingBox.TwoDims _source) {
				throw new IllegalArgumentException();
			}
			public default void 	set(BoundingBox.ThreeDims _source) {
				setLeft(_source.getLeft());
				setRight(_source.getRight());
				setTop(_source.getTop());
				setBottom(_source.getBottom());
				setFront(_source.getFront());
				setBack(_source.getBack());
			}

			public default void 	set(double _width, double _height, double _depth) 									{ set(0, 0, 0, _width, _height, _depth); }
			public default void 	set(double _x, double _y, double _width, double _height) 							{ set(_x, _y, getZ(), _width, _height, getDepth()); }
			public void 			set(double _x, double _y, double _z, double _width, double _height, double _depth);

		    public void 			setZ(double _y);
		    public void 			setMinZ(double _y);
		    public void 			setMaxZ(double _y);
		    public void 			setCenterZ(double _y);
		    public void 			setDepth(double _h);

		    public void  			setFront(double _top);
		    public void  			setBack(double _bottom);


		}

		@Override
		public default boolean 	isEmpty()        { return getWidth() == 0 || getHeight() == 0 || getDepth() == 0; }

		public double  			getZ();
		public double  			getDepth();

		public default double  	getMinZ()        { return getZ(); }
	    public default double  	getMaxZ()        { return getMinZ() + getDepth(); }
	    public default double  	getCenterZ()     { return (getMinZ() + getMaxZ()) / 2; }

	    public default double  	getFront()       { return getMaxZ(); }
	    public default double  	getBack()        { return getMinZ(); }

		public default double 	getPerimeter()   { return 2 * (getWidth() + getHeight()); }
		public default double 	getArea() 		 { return 2 * getWidth() * getHeight() + 2 * getWidth() * getDepth() + 2 * getHeight() * getDepth(); }
		public default double 	getVolume()      { return getWidth() * getHeight() * getDepth(); }

		public default boolean 	contains(double _x, double _y, double _z) {
			if(isEmpty())
				return false;
			return _x >= getMinX() && _x <= getMaxX()
				&& _y >= getMinY() && _y <= getMaxY()
				&& _z >= getMinZ() && _z <= getMaxZ();
		}
		public default boolean 	contains(double _x, double _y, double _z, double _w, double _h, double _d) {
			return contains(_x, _y, _z) && contains(_x + _w, _y + _h, _z + _d);
		}
		public default boolean 	contains(Coordinate _pt) {
			if(_pt instanceof Coordinate.ThreeDims)
				return contains((Coordinate.ThreeDims) _pt);
			throw new IllegalAccessArgument();
		}
		public default boolean 	contains(Coordinate.ThreeDims _pt) {
			if (_pt == null)
				return false;
			return _pt.getFirst()  >= getMinX() && _pt.getFirst()  <= getMaxX()  
				&& _pt.getSecond() >= getMinY() && _pt.getSecond() <= getMaxY() 
				&& _pt.getThird()  >= getMinZ() && _pt.getThird()  <= getMaxZ();
		}
		public default boolean 	contains(BoundingBox _pt) {
			if(_pt instanceof BoundingBox.ThreeDims)
				return contains(((BoundingBox.ThreeDims) _pt));
			throw new IllegalAccessArgument();
		}
		public default boolean 	contains(BoundingBox.ThreeDims _bbox) {
			return contains(_bbox.getMinX(), _bbox.getMinY(), _bbox.getMinZ())
				&& contains(_bbox.getMaxX(), _bbox.getMaxY(), _bbox.getMaxZ());
		}

		public default boolean 	intersects(double _x, double _y, double _z, double _w, double _h, double _d) {
			if (isEmpty() || _w < 0 || _h < 0)
				return false;

			return getMinX() < _x + _w && getMaxX() > _x
				&& getMinY() < _y + _h && getMaxY() > _y
				&& getMinZ() < _z + _d && getMaxY() < _z;
		}
		public default boolean 	intersects(BoundingBox _pt) {
			if(_pt instanceof BoundingBox.ThreeDims)
				return contains(((BoundingBox.ThreeDims) _pt));
			throw new IllegalAccessArgument();
		}
		public default boolean 	intersects(BoundingBox.ThreeDims _bbox) {
			if ((_bbox == null) || _bbox.isEmpty())
				return false;
			return intersects(_bbox.getMinX(), _bbox.getMinY(), _bbox.getMinZ(), _bbox.getWidth(), _bbox.getHeight(), _bbox.getDepth());
		}

		@Override 
		public default String 	toString(NumberFormat _nf) 	{ return "(" + _nf.format(getX()) + ", " + _nf.format(getY()) + ", " + _nf.format(getZ()) + ") [" + _nf.format(getWidth()) + "x" + _nf.format(getHeight()) + "x" + _nf.format(getDepth()) + "]"; }

//		@Override
//		public default BoundingBox.ThreeDims clone() { throw new NotYetImplementedException(); }

	}

	public static interface WithTops<TOPS extends Coordinate> extends BoundingBox {

		public Coordinate 			getPosition();
		public Coordinate		 	getCenter();
		public Set<TOPS> 			getTops();

		public default boolean 		contains(WithTops<TOPS> _bb) {
			for(TOPS top : _bb.getTops())
				if(!contains(top))
					return false;
			return true;
		}

		public default boolean 		intersects(WithTops<TOPS> _bb) {
			boolean inside = false, outside = false;
			for(TOPS top : _bb.getTops())
				if(contains(top)) 	inside  = true;
				else 				outside = true;
			return inside && outside;
			
		}
		
	}
	public static interface OneDimWithTops<TOPS extends Coordinate.OneDim> extends BoundingBox.OneDim, WithTops<TOPS> {

		@Override
		public default Coordinate.OneDim 	getPosition() {
			return new Coordinate.OneDim() {
				@Override public CoordinateSystem 	getCoordinateSystem() 		{ return CoordinateSystem.Linear; }
				@Override public double 			getFirst() 					{ return getLeft(); }
				@Override public Coordinate.OneDim 	clone() 					{ return getPosition(); }
				@Override public String 			toString(DecimalFormat _df) { return "(" + _df.format(getFirst()) + ")"; }
			};
		}
		@Override
		public default Coordinate.OneDim 	getCenter() {
			return new Coordinate.OneDim() {
				@Override public CoordinateSystem 	getCoordinateSystem() 		{ return CoordinateSystem.Linear; }
				@Override public double 			getFirst() 					{ return (getLeft() + getRight()) / 2d; }
				@Override public Coordinate.OneDim 	clone() 					{ return getCenter(); }
				@Override public String 			toString(DecimalFormat _df) { return "(" + _df.format(getFirst()) + ")"; }
			};
		}
		@Override
		public default Set<TOPS> 			getTops() {
			Set<TOPS> tops = new HashSet<TOPS>();
			tops.add(getMin());
			tops.add(getMax());
			return tops;
		}

		public TOPS getMin();
		public TOPS getMax();

	}
	public static interface TwoDimsWithTops<TOPS extends Coordinate.TwoDims> extends BoundingBox.TwoDims, WithTops<TOPS> {

		@Override
		public default Coordinate.TwoDims 	getPosition() {
			return new Coordinate.TwoDims() {
				@Override public CoordinateSystem 	getCoordinateSystem() 		{ return CoordinateSystem.Planar; }
				@Override public double 			getFirst() 					{ return getLeft(); }
				@Override public double 			getSecond() 				{ return getTop(); }
				@Override public Coordinate.OneDim 	clone() 					{ return getPosition(); }
				@Override public String 			toString(DecimalFormat _df) { return "(" + _df.format(getFirst()) + ", " +  _df.format(getSecond()) + ")"; }
			};
		}
		@Override
		public default Coordinate.TwoDims 	getCenter() {
			return new Coordinate.TwoDims() {
				@Override public CoordinateSystem 	getCoordinateSystem() 		{ return CoordinateSystem.Planar; }
				@Override public double 			getFirst() 					{ return (getLeft() + getRight()) / 2d; }
				@Override public double 			getSecond() 				{ return (getTop()  + getBottom()) / 2d; }
				@Override public Coordinate.OneDim 	clone() 					{ return getCenter(); }
				@Override public String 			toString(DecimalFormat _df) { return "(" + _df.format(getFirst()) + ", " +  _df.format(getSecond()) + ")"; }
			};
		}
		@Override
		public default Set<TOPS> 			getTops() {
			Set<TOPS> tops = new HashSet<TOPS>();
			tops.add(getTopLeft());
			tops.add(getTopRight());
			tops.add(getBottomRight());
			tops.add(getBottomLeft());
			return tops;
		}

		public TOPS getTopLeft();
		public TOPS getTopRight();
		public TOPS getBottomRight();
		public TOPS getBottomLeft();

	}
	public static interface ThreeDimsWithTops<TOPS extends Coordinate.ThreeDims> extends BoundingBox.ThreeDims, WithTops<TOPS> {

		@Override
		public default Coordinate.ThreeDims 	getPosition() {
			return new Coordinate.ThreeDims() {
				@Override public CoordinateSystem 	getCoordinateSystem() 		{ return CoordinateSystem.Spatial; }
				@Override public double 			getFirst() 					{ return getLeft(); }
				@Override public double 			getSecond() 				{ return getTop(); }
				@Override public double 			getThird() 					{ return getFront(); }
				@Override public Coordinate.OneDim 	clone() 					{ return getPosition(); }
				@Override public String 			toString(DecimalFormat _df) { return "(" + _df.format(getFirst()) + ", " +  _df.format(getSecond()) + ", " +  _df.format(getThird()) + ")"; }
			};
		}
		@Override
		public default Coordinate.ThreeDims 	getCenter() {
			return new Coordinate.ThreeDims() {
				@Override public CoordinateSystem 	getCoordinateSystem() 		{ return CoordinateSystem.Spatial; }
				@Override public double 			getFirst() 					{ return (getLeft()  + getRight()) / 2d; }
				@Override public double 			getSecond() 				{ return (getTop()   + getBottom()) / 2d; }
				@Override public double 			getThird() 					{ return (getFront() + getBack()) / 2d; }
				@Override public Coordinate.OneDim 	clone() 					{ return getCenter(); }
				@Override public String 			toString(DecimalFormat _df) { return "(" + _df.format(getFirst()) + ", " +  _df.format(getSecond()) + ", " +  _df.format(getThird()) + ")"; }
			};
		}

		@Override
		public default Set<TOPS> 				getTops() {
			Set<TOPS> tops = new HashSet<TOPS>();
			tops.add(getFrontTopLeft());
			tops.add(getFrontTopRight());
			tops.add(getFrontBottomRight());
			tops.add(getFrontBottomLeft());
			tops.add(getBackTopLeft());
			tops.add(getBackTopRight());
			tops.add(getBackBottomRight());
			tops.add(getBackBottomLeft());
			return tops;
		}

		public TOPS getFrontTopLeft();
		public TOPS getFrontTopRight();
		public TOPS getFrontBottomRight();
		public TOPS getFrontBottomLeft();

		public TOPS getBackTopLeft();
		public TOPS getBackTopRight();
		public TOPS getBackBottomRight();
		public TOPS getBackBottomLeft();

	}

	public boolean 				isEmpty();

	public boolean 				contains(Coordinate _pt);
	public default boolean 		contains(List<Coordinate> _pts) {
		for(Coordinate pt : _pts)
			if(!contains(pt))
				return false;
		return true;
	}
	public default boolean 		contains(Set<Coordinate> _pts) {
		for(Coordinate pt : _pts)
			if(!contains(pt))
				return false;
		return true;
	}
	public boolean 				contains(BoundingBox _bb);

	public default boolean 		intersects(List<Coordinate> _pts) {
		boolean inside = false, outside = false;
		for(Coordinate pt : _pts)
			if(contains(pt)) inside = true; else outside = true;
		return inside && outside;
	}
	public default boolean 		intersects(Set<Coordinate> _pts) {
		boolean inside = false, outside = false;
		for(Coordinate pt : _pts)
			if(contains(pt)) inside = true; else outside = true;
		return inside && outside;
	}
	public boolean 				intersects(BoundingBox _bb);

	public String 				toString(NumberFormat _nf);

//	@Override
//	public default BoundingBox clone() { throw new NotYetImplementedException(); }

}