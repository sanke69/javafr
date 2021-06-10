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
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of     *
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.               *
 *    [FR] Ce programme est distribué dans l'espoir qu'il sera utile,         *
 *         mais SANS AUCUNE GARANTIE, sans même la garantie implicite de      *
 *         VALEUR MARCHANDE ou FONCTIONNALITE POUR UN BUT PARTICULIER.        *
 *                                                                            *
\** ************************************************************************ **/
package fr.java.maths.geometry.space.shapes;

import java.text.NumberFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import fr.java.math.geometry.space.BoundingBox3D;
import fr.java.math.geometry.space.Dimension3D;
import fr.java.math.geometry.space.Point3D;
import fr.java.math.geometry.space.shapes.Cube3D;
import fr.java.maths.Points;
import fr.java.maths.geometry.space.types.SimpleDimension3D;
import fr.java.nio.buffer.Point3DBufferX;

// Oriented World Axis Cube
public class SimpleCube3D implements Cube3D {
	private static final long serialVersionUID = -4298538292966277165L;

	public static SimpleCube3D empty() {
		return new SimpleCube3D(0, 0, 0, 0, 0, 0);
	}
	public static SimpleCube3D unit() {
		return new SimpleCube3D(0, 0, 0, 1, 1, 1);
	}

	public static SimpleCube3D of(double _x, double _y, double _z, double _w, double _h, double _d) {
		return new SimpleCube3D(_x, _y, _z, _w, _h, _d);
	}
	
	protected double	minX, minY, minZ;
	protected double	width, height, depth;

	public SimpleCube3D() {
		super();
		minX = minY = minZ = 0.0;
		width = height = depth = 1.0;
	}
	public SimpleCube3D(double _width, double _height, double _depth) {
		this();
		width = _width;
		height = _height;
		depth = _depth;
	}
	public SimpleCube3D(Dimension3D _dimensions) {
		this(_dimensions.getWidth(), _dimensions.getHeight(), _dimensions.getDepth());
	}
	public SimpleCube3D(double _x, double _y, double _z, double _width, double _height, double _depth) {
		super();
		minX = _x;
		minY = _y;
		minZ = _z;
		width = _width;
		height = _height;
		depth = _depth;
	}
	public SimpleCube3D(Point3D _position, Dimension3D _dimensions) {
		this(_position.getX(), _position.getY(), _position.getZ(), _dimensions.getWidth(), _dimensions.getHeight(), _dimensions.getDepth());
	}
	public SimpleCube3D(BoundingBox3D _clone) {
		this();
		minX = _clone.getLeft();
		minY = _clone.getBottom();
		minZ = _clone.getFront();
		width = _clone.getWidth();
		height = _clone.getHeight();
		depth = _clone.getDepth();
	}
	public SimpleCube3D(Set<Point3D> _points) {
		if(_points == null || _points.size() < 2)
			throw new IllegalArgumentException(
					"The parameter '_bounds' cannot be null and must "
							+ "have 2 or more elements.");

		minX = Integer.MAX_VALUE;
		minY = Integer.MAX_VALUE;
		minZ = Integer.MAX_VALUE;
		double maxX = Integer.MIN_VALUE;
		double maxY = Integer.MIN_VALUE;
		double maxZ = Integer.MIN_VALUE;
		for(Point3D pt : _points) {
			minX = Math.min(minX, pt.getX());
			minY = Math.min(minY, pt.getY());
			minZ = Math.min(minZ, pt.getX());
			maxX = Math.max(maxX, pt.getX());
			maxY = Math.max(maxY, pt.getY());
			maxZ = Math.max(maxZ, pt.getY());
		}

		width = maxX - minX;
		height = maxY - minY;
		depth = maxZ - minZ;
	}
	public SimpleCube3D(Point3DBufferX _points) {
		if(_points == null || _points.remaining() < 2)
			throw new IllegalArgumentException(
					"The parameter '_bounds' cannot be null and must "
							+ "have 2 or more elements.");

		Point3D pt;

		minX = Integer.MAX_VALUE;
		minY = Integer.MAX_VALUE;
		minZ = Integer.MAX_VALUE;
		double maxX = Integer.MIN_VALUE;
		double maxY = Integer.MIN_VALUE;
		double maxZ = Integer.MIN_VALUE;
		while(_points.hasRemaining()) {
			pt = _points.get();
			minX = Math.min(minX, pt.getX());
			minY = Math.min(minY, pt.getY());
			minZ = Math.min(minZ, pt.getZ());
			maxX = Math.max(maxX, pt.getX());
			maxY = Math.max(maxY, pt.getY());
			maxZ = Math.max(maxZ, pt.getZ());
		}

		width  = maxX - minX;
		height = maxY - minY;
		depth  = maxZ - minZ;
	}

	public void set(BoundingBox3D _clone) {
		minX   = _clone.getLeft();
		minY   = _clone.getBottom();
		minZ   = _clone.getFront();
		width  = _clone.getWidth();
		height = _clone.getHeight();
		depth  = _clone.getDepth();
	}
	public void set(Collection<Point3D> _points) {
		if(_points == null || _points.size() < 2)
			throw new IllegalArgumentException(
					"The parameter '_bounds' cannot be null and must "
							+ "have 2 or more elements.");

		minX = Integer.MAX_VALUE;
		minY = Integer.MAX_VALUE;
		minZ = Integer.MAX_VALUE;
		double maxX = Integer.MIN_VALUE;
		double maxY = Integer.MIN_VALUE;
		double maxZ = Integer.MIN_VALUE;
		for(Point3D pt : _points) {
			minX = Math.min(minX, pt.getX());
			minY = Math.min(minY, pt.getY());
			minZ = Math.min(minZ, pt.getZ());
			maxX = Math.max(maxX, pt.getX());
			maxY = Math.max(maxY, pt.getY());
			maxZ = Math.max(maxZ, pt.getZ());
		}

		width  = maxX - minX;
		height = maxY - minY;
		depth  = maxZ - minZ;
	}
	public void set(Point3DBufferX _points) {
		if(_points == null || _points.remaining() < 2)
			throw new IllegalArgumentException(
					"The parameter '_bounds' cannot be null and must "
							+ "have 2 or more elements.");

		Point3D pt;

		minX = Integer.MAX_VALUE;
		minY = Integer.MAX_VALUE;
		minZ = Integer.MAX_VALUE;
		double maxX = Integer.MIN_VALUE;
		double maxY = Integer.MIN_VALUE;
		double maxZ = Integer.MIN_VALUE;
		while(_points.hasRemaining()) {
			pt = _points.get();
			minX = Math.min(minX, pt.getX());
			minY = Math.min(minY, pt.getY());
			minZ = Math.min(minZ, pt.getZ());
			maxX = Math.max(maxX, pt.getX());
			maxY = Math.max(maxY, pt.getY());
			maxZ = Math.max(maxZ, pt.getZ());
		}

		width  = maxX - minX;
		height = maxY - minY;
		depth  = maxZ - minZ;
	}
	@Override
	public void set(double _x, double _y, double _width, double _height) {
		minX   = _x;
		minY   = _y;
		width  = _width;
		height = _height;
	}
	@Override
	public void set(double _x, double _y, double _z, double _width, double _height, double _depth) {
		minX   = _x;
		minY   = _y;
		minZ   = _z;
		width  = _width;
		height = _height;
		depth  = _depth;
	}
	@Override
	public void setLeft(double _left) {
		minX   = _left;
	}
	@Override
	public void setRight(double _right) {
		width  = _right - minY;
	}
	@Override
	public void setTop(double _top) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setBottom(double _bottom) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setFront(double _top) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setBack(double _bottom) {
		// TODO Auto-generated method stub
		
	}

//	@Override
	public Set<Point3D> getTops() {
		Set<Point3D> corners = new HashSet<Point3D>();
		corners.add(Points.of(getLeft(),  getBottom(), getFront()));
		corners.add(Points.of(getRight(), getBottom(), getFront()));
		corners.add(Points.of(getRight(), getTop(),    getFront()));
		corners.add(Points.of(getLeft(),  getTop(),    getFront()));
		corners.add(Points.of(getLeft(),  getBottom(), getBack()));
		corners.add(Points.of(getRight(), getBottom(), getBack()));
		corners.add(Points.of(getRight(), getTop(),    getBack()));
		corners.add(Points.of(getLeft(),  getTop(),    getBack()));
		return corners;
	}

	@Override
	public Point3D getPosition() {
		return Points.of(getMinX(), getMinY(), getMinZ());
	}
	@Override
	public Point3D getCenter() {
		return Points.of(getMinX() + getWidth() / 2.0, getMinY() + getHeight() / 2.0, getMinZ() + getDepth() / 2.0);
	}
	@Override
	public Dimension3D getSize() {
		return SimpleDimension3D.of(getWidth(), getHeight(), getDepth());
	}

	public void setPosition(double _x, double _y, double _z) {
		minX = _x;
		minY = _y;
		minZ = _z;
	}
	public void setPosition(Point3D _pt) {
		setPosition(_pt.getX(), _pt.getY(), _pt.getZ());
	}

	public void setCenter(double _x, double _y, double _z) {
		minX = _x - getWidth() / 2.0;
		minY = _y - getHeight() / 2.0;
		minZ = _z - getDepth() / 2.0;
	}
	public void setCenter(Point3D _pt) {
		setCenter(_pt.getX(), _pt.getY(), _pt.getZ());
	}

	public void setSize(double _w, double _h, double _d) {
		width = _w;
		height = _h;
		depth = _d;
	}
	public void setSize(Dimension3D _dim) {
		setSize(_dim.getWidth(), _dim.getHeight(), _dim.getDepth());
	}

	@Override
	public boolean isEmpty() {
		return getWidth() <= 0 || getHeight() <= 0 || getDepth() <= 0;
	}

	@Override
	public double getLeft() {
		return getMinX();
	}
	@Override
	public double getRight() {
		return getMinX() + getWidth();
	}
	@Override
	public double getTop() {
		return getMinY() + getHeight();
	}
	@Override
	public double getBottom() {
		return getMinY();
	}
	@Override
	public double getFront() {
		return getMinZ();
	}
	@Override
	public double getBack() {
		return getMinZ() + getDepth();
	}

	public void setX(double _x) {
		minX = _x;
	}
	public void setMinX(double _x) {
		minX = _x;
	}
	public void setMaxX(double _x) {
		width = _x - minX;
	}
	public void setCenterX(double _x) {
		minX = _x - getWidth() / 2.0;
	}
	public void setWidth(double _w) {
		width = _w;
	}

	public void setY(double _y) {
		minY = _y;
	}
	public void setMinY(double _y) {
		minY = _y;
	}
	public void setMaxY(double _y) {
		height = _y - minY;
	}
	public void setCenterY(double _y) {
		minY = _y - getHeight() / 2.0;
	}
	public void setHeight(double _h) {
		height = _h;
	}

	public void setZ(double _z) {
		minZ = _z;
	}
	public void setMinZ(double _z) {
		minZ = _z;
	}
	public void setMaxZ(double _z) {
		depth = _z - minZ;
	}
	public void setCenterZ(double _z) {
		minZ = _z - getDepth() / 2.0;
	}
	public void setDepth(double _d) {
		depth = _d;
	}

	public double getX() {
		return getMinX();
	}
	@Override
	public double getMinX() {
		return minX;
	}
	@Override
	public double getMaxX() {
		return minX + width;
	}
	@Override
	public double getCenterX() {
		return minX + width / 2.0;
	}
	@Override
	public double getWidth() {
		return width;
	}

	public double getY() {
		return getMinY();
	}
	@Override
	public double getMinY() {
		return minY;
	}
	@Override
	public double getMaxY() {
		return minY + height;
	}
	@Override
	public double getCenterY() {
		return minY + height / 2.0;
	}
	@Override
	public double getHeight() {
		return height;
	}

	public double getZ() {
		return getMinZ();
	}
	@Override
	public double getMinZ() {
		return minZ;
	}
	@Override
	public double getMaxZ() {
		return minZ + depth;
	}
	@Override
	public double getCenterZ() {
		return minZ + depth / 2.0;
	}
	@Override
	public double getDepth() {
		return depth;
	}

	/*
	public Point2D getTopRight() {
		return new Point2D.Double(maxX, maxY);
	}
	public void setTopRight(Point2D _tr) {
		setTopRight(_tr.getY(), _tr.getX());
	}
	public void setTopRight(double _top, double _right) {
		minX += (_right - maxX);
		maxX += (_right - maxX);
		minY += (_top - maxY);
		maxY += (_top - maxY);
	}
	
	public Point2D getTopLeft() {
		return new Point2D.Double(minX, maxY);
	}
	public void setTopLeft(Point2D _tl) {
		setTopLeft(_tl.getY(), _tl.getX());
	}
	public void setTopLeft(double _top, double _left) {
		minX += (_left - minX);
		maxX += (_left - minX);
		minY += (_top - maxY);
		maxY += (_top - maxY);
	}
	
	public Point2D getBottomRight() {
		return new Point2D.Double(maxX, minY);
	}
	public void setBottomRight(Point2D _br) {
		setBottomRight(_br.getY(), _br.getX());
	}
	public void setBottomRight(double _bottom, double _right) {
		minX += (_right - maxX);
		maxX += (_right - maxX);
		minY += (_bottom - minY);
		maxY += (_bottom - minY);
	}
	
	public Point2D getBottomLeft() {
		return new Point2D.Double(minX, minY);
	}
	public void setBottomLeft(Point2D _bl) {
		setBottomLeft(_bl.getY(), _bl.getX());
	}
	public void setBottomLeft(double _bottom, double _left) {
		minX += (_left - minX);
		maxX += (_left - minX);
		minY += (_bottom - minY);
		maxY += (_bottom - minY);
	}
	*/
	public void add(double _x, double _y, double _z) {
		double x1 = Math.min(getMinX(), _x);
		double x2 = Math.max(getMaxX(), _x);
		double y1 = Math.min(getMinY(), _y);
		double y2 = Math.max(getMaxY(), _y);
		double z1 = Math.min(getMinZ(), _z);
		double z2 = Math.max(getMaxZ(), _z);
		minX   = x1;
		width  = x2 - x1;
		minY   = y1;
		height = y2 - y1;
		minZ   = z1;
		depth  = z2 - z1;
	}
	public void add(Point3D p) {
		add(p.getX(), p.getY(), p.getZ());
	}

	@Override
	public boolean contains(double x, double y, double z) {
		if(isEmpty())
			return false;
		return x >= getMinX() && x <= getMinX() && y >= getMinY() && y <= getMaxY() && z >= getMinZ() && z <= getMaxZ();
	}
	@Override
	public boolean contains(double x, double y, double z, double w, double h, double d) {
		return contains(x, y, z) && contains(x + w, y + h, z + d);
	}

	@Override
	public boolean contains(Point3D p) {
		if(p == null)
			return false;
		return contains(p.getX(), p.getY(), p.getZ());
	}
	@Override
	public boolean contains(BoundingBox3D _bb) {
		return contains(_bb.getMinX(), _bb.getMinY(), _bb.getMinZ()) && contains(_bb.getMaxX(), _bb.getMaxY(), _bb.getMaxZ());
	}

	@Override
	public boolean intersects(double x, double y, double z, double w, double h, double d) {
		if(isEmpty() || w < 0 || h < 0 || d < 0)
			return false;
		return (x + w >= getMinX() &&
				y + h >= getMinY() &&
				z + d >= getMinZ() &&
				x <= getMinX() &&
				y <= getMaxY() &&
				z <= getMaxZ());
	}
	@Override
	public boolean intersects(BoundingBox3D b) {
		if((b == null) || b.isEmpty())
			return false;
		//return intersects(b.getBarycenter().getX(), b.getBarycenter().getY(), b.getBarycenter().getZ(), b.getWidth(), b.getHeight(), b.getDepth());
		return false;
	}

	@Override
	public SimpleCube3D clone() {
		return new SimpleCube3D(minX, minY, minZ, width, height, depth);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		if(obj instanceof SimpleCube3D) {
			SimpleCube3D other = (SimpleCube3D) obj;
			return getMinX() == other.getMinX()
					&& getMinY() == other.getMinY()
					&& getMinZ() == other.getMinZ()
					&& getWidth() == other.getWidth()
					&& getHeight() == other.getHeight()
					&& getDepth() == other.getDepth();
		} else
			return false;
	}

//	@Override
	public int compareTo(Object _o) {
		if(_o instanceof BoundingBox3D) {
			BoundingBox3D object = (BoundingBox3D) _o;
			double volumeThis = getWidth() * getHeight() * getDepth();
			double volumeObject = object.getWidth() * object.getHeight() * object.getDepth();

			return volumeThis - volumeObject > 0 ? 1 : volumeThis - volumeObject < 0 ? -1 : 0;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "(" + getMinX() + ", " + getMinY() + ", " + getMinZ() + "), [" + getWidth() + "x" + getHeight() + "x" + getDepth() + "]";
	}
	@Override
	public String toString(NumberFormat _nf) {
		return toString();
	}


	public double[] asArray() {
		return new double[] { minX, minY, minZ, width, height, depth };
	}

}
