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

import fr.java.draw.tools.Color;
import fr.java.math.geometry.plane.Ellipse2D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Polygon2D;
import fr.java.math.geometry.plane.Rectangle2D;
import fr.java.math.geometry.plane.Segment2D;
import fr.java.math.geometry.plane.Shape2D;
import fr.java.math.geometry.plane.Triangle2D;

public interface Drawable {

	public static Drawable of(Shape2D _shape) {
		return new Drawable() {

			@Override
			public void draw(Drawer _drawer) {
				if(_shape instanceof Point2D)
					_drawer.drawPoint((Point2D) _shape);
				else if(_shape instanceof Segment2D)
					_drawer.drawSegment((Segment2D) _shape);
				else if(_shape instanceof Triangle2D)
					_drawer.drawTriangle((Triangle2D) _shape);
				else if(_shape instanceof Rectangle2D)
					_drawer.drawRectangle((Rectangle2D) _shape);
				else if(_shape instanceof Ellipse2D)
					_drawer.drawEllipse((Ellipse2D) _shape);
				else if(_shape instanceof Polygon2D)
					_drawer.drawPolygon((Polygon2D) _shape);
			}
			
		};
	}
	public static Drawable of(Shape2D _shape, double _strokeWidth) {
		return new Drawable() {

			@Override
			public void draw(Drawer _drawer) {
				if(_shape instanceof Point2D)
					_drawer.drawPoint((Point2D) _shape, _strokeWidth);
				else if(_shape instanceof Segment2D)
					_drawer.drawSegment((Segment2D) _shape, _strokeWidth);
				else if(_shape instanceof Triangle2D)
					_drawer.drawTriangle((Triangle2D) _shape, _strokeWidth);
				else if(_shape instanceof Rectangle2D)
					_drawer.drawRectangle((Rectangle2D) _shape, _strokeWidth);
				else if(_shape instanceof Ellipse2D)
					_drawer.drawEllipse((Ellipse2D) _shape, _strokeWidth);
				else if(_shape instanceof Polygon2D)
					_drawer.drawPolygon((Polygon2D) _shape, _strokeWidth);
			}
			
		};
	}
	public static Drawable of(Shape2D _shape, double _strokeWidth, Color _strokeColor) {
		return new Drawable() {

			@Override
			public void draw(Drawer _drawer) {
				if(_shape instanceof Point2D)
					_drawer.drawPoint((Point2D) _shape, _strokeWidth, _strokeColor);
				else if(_shape instanceof Segment2D)
					_drawer.drawSegment((Segment2D) _shape, _strokeWidth, _strokeColor);
				else if(_shape instanceof Triangle2D)
					_drawer.drawTriangle((Triangle2D) _shape, _strokeWidth, _strokeColor);
				else if(_shape instanceof Rectangle2D)
					_drawer.drawRectangle((Rectangle2D) _shape, _strokeWidth, _strokeColor);
				else if(_shape instanceof Ellipse2D)
					_drawer.drawEllipse((Ellipse2D) _shape, _strokeWidth, _strokeColor);
				else if(_shape instanceof Polygon2D)
					_drawer.drawPolygon((Polygon2D) _shape, _strokeWidth, _strokeColor);
			}
			
		};
	}
	public static Drawable of(Shape2D _shape, double _strokeWidth, Color _strokeColor, Color _fillColor) {
		return new Drawable() {

			@Override
			public void draw(Drawer _drawer) {
				if(_shape instanceof Point2D)
					_drawer.drawPoint((Point2D) _shape, _strokeWidth, _strokeColor);
				else if(_shape instanceof Segment2D)
					_drawer.drawSegment((Segment2D) _shape, _strokeWidth, _strokeColor);
				else if(_shape instanceof Triangle2D)
					_drawer.drawTriangle((Triangle2D) _shape, _strokeWidth, _strokeColor, _fillColor);
				else if(_shape instanceof Rectangle2D)
					_drawer.drawRectangle((Rectangle2D) _shape, _strokeWidth, _strokeColor, _fillColor);
				else if(_shape instanceof Ellipse2D)
					_drawer.drawEllipse((Ellipse2D) _shape, _strokeWidth, _strokeColor, _fillColor);
				else if(_shape instanceof Polygon2D)
					_drawer.drawPolygon((Polygon2D) _shape, _strokeWidth, _strokeColor, _fillColor);
			}
			
		};
	}

//	public void draw(DrawableArea _area);
	public void draw(Drawer _drawer);
	

}
