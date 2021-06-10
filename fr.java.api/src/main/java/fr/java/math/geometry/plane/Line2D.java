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

public interface Line2D extends Shape2D {

	Point2D getPoint();
	Point2D getPoint(boolean _random);

	Vector2D getDirection();
	Vector2D getNormal();

	boolean contains(Point2D _pt);
	boolean contains(Point2D _pt, double _epsilon);
	boolean contains(Segment2D _seg);
	boolean contains(Segment2D _seg, double _epsilon);
	boolean contains(Line2D _line);
	boolean contains(Line2D _line, double _epsilon);

	boolean intersect(Point2D _a, Point2D _b);
	boolean intersect(Point2D _a, Point2D _b, double _epsilon);
	boolean intersect(Segment2D _seg);
	boolean intersect(Segment2D _seg, double _epsilon);
	boolean intersect(Line2D _line);
	boolean intersect(Line2D _line, double _epsilon);

}
