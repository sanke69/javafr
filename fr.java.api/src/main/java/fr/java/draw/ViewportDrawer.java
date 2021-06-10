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
package fr.java.draw;

import fr.java.math.geometry.Viewport;
import fr.java.math.geometry.plane.Ellipse2D;
import fr.java.math.geometry.plane.Line2D;
import fr.java.math.geometry.plane.Polygon2D;
import fr.java.math.geometry.plane.Polyline2D;
import fr.java.math.geometry.plane.Rectangle2D;
import fr.java.math.geometry.plane.Segment2D;
import fr.java.math.geometry.plane.Triangle2D;
import fr.java.math.topology.Coordinate;

public interface ViewportDrawer extends Drawer {

	public Viewport.TwoDims<?, ?> viewport();

	public double[]			 	  mod2win(double _x, double _y);
	public double[][] 			  mod2win(double[] _x, double[] _y);
	public Coordinate.TwoDims 	  mod2win(Coordinate.TwoDims _pt);

	public Segment2D 			  mod2win(Segment2D _seg);
	public Triangle2D 			  mod2win(Triangle2D _seg);
	public Rectangle2D 			  mod2win(Rectangle2D _seg);
	public Ellipse2D 			  mod2win(Ellipse2D _seg);
	public Polygon2D 			  mod2win(Polygon2D _seg);
	public Line2D 				  mod2win(Line2D _seg);
	public Polyline2D 			  mod2win(Polyline2D _seg);

}
