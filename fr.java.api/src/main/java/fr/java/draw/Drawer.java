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
 * @file     Drawer.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.draw;

import java.util.Arrays;
import java.util.List;

import fr.java.draw.styles.LineStyle;
import fr.java.draw.styles.PointSkin;
import fr.java.draw.styles.PointStyle;
import fr.java.draw.tools.Brush;
import fr.java.draw.tools.Color;
import fr.java.draw.tools.Font;
import fr.java.draw.tools.Paint;
import fr.java.draw.tools.Pen;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.plane.Ellipse2D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Polygon2D;
import fr.java.math.geometry.plane.Polyline2D;
import fr.java.math.geometry.plane.Rectangle2D;
import fr.java.math.geometry.plane.Segment2D;
import fr.java.math.geometry.plane.Shape2D;
import fr.java.math.geometry.plane.Triangle2D;
import fr.java.math.geometry.plane.Vector2D;

public interface Drawer {
	public static final ArcMode ARC_OPEN  = ArcMode.OPEN;
	public static final ArcMode ARC_CHORD = ArcMode.CHORD;
	public static final ArcMode ARC_ROUND = ArcMode.ROUND;

	public enum ArcMode {
		OPEN, CHORD, ROUND;
	}

	/*** Drawing Surface Aspects ***/
	public void 				clear(Color _c);
	
	public void 				setLineWidth(double _width);
	public void 				setFill		(Color _color);
	public void 				setStroke	(Color _color);
//	public void 				setFont		(Font _font);

	public void 				setDrawStyle(String _style);

	public Dimension.TwoDims 	getTextDimension(String _text, Font _font);

	/*** Drawing Object/Technique Aspects ***/
	// Primitives - 1D
	public void 		drawPoint(double _x, double _y);
	public void 		drawPoint(double _x, double _y, double _ptSize);
	public void 		drawPoint(double _x, double _y, double _ptSize, Color _strokePaint);
	public default void drawPoint(double _x, double _y, Pen _pen) {
		drawPoint(_x, _y, _pen.getWidth(), _pen.toColor());
	}
	public void 		drawPoint(double _x, double _y, PointSkin _skin);
	public void 		drawPoint(double _x, double _y, PointSkin _skin, double _ptSize);
	public void 		drawPoint(double _x, double _y, PointSkin _skin, double _ptSize, double _strokeWidth, Color _strokePaint);
	public void 		drawPoint(double _x, double _y, PointSkin _skin, double _ptSize, double _strokeWidth, Color _strokePaint, Color _fillPaint);
	public default void drawPoint(double _x, double _y, PointSkin _skin, double _ptSize, Pen _pen) {
		drawPoint(_x, _y, _skin, _ptSize, _pen.getWidth(), _pen.toColor());
	}
	public default void drawPoint(double _x, double _y, PointStyle _style) {
		drawPoint(_x, _y, _style.getSkin(), _style.getSize(), _style.getPen().getWidth(), _style.getPen().toColor());
	}

	public void 		drawPoints(double[] _x, double[] _y);
	public void 		drawPoints(double[] _x, double[] _y, double _ptSize);
	public void 		drawPoints(double[] _x, double[] _y, double _ptSize, Color _strokePaint);
	public default void drawPoints(double[] _x, double[] _y, Pen _pen) {
		drawPoints(_x, _y, _pen.getWidth(), _pen.toColor());
	}

	public void 		drawSegment(double _x0, double _y0, double _x1, double _y1);
	public void 		drawSegment(double _x0, double _y0, double _x1, double _y1, double _strokeWidth);
	public void 		drawSegment(double _x0, double _y0, double _x1, double _y1, double _strokeWidth, Color _strokePaint);
	public default void drawSegment(double _x0, double _y0, double _x1, double _y1, Pen _pen) {
		drawSegment(_x0, _y0, _x1, _y1, _pen.getWidth(), _pen.toColor());
	}
	public void 		drawSegment(double _x0, double _y0, double _x1, double _y1, LineStyle _style);

	public default void drawLine(double _x0, double _y0, double _x1, double _y1) {
		drawSegment(_x0, _y0, _x1, _y1);
	}
	public default void drawLine(double _x0, double _y0, double _x1, double _y1, double _strokeWidth) {
		drawSegment(_x0, _y0, _x1, _y1, _strokeWidth);
	}
	public default void drawLine(double _x0, double _y0, double _x1, double _y1, double _strokeWidth, Color _strokePaint) {
		drawSegment(_x0, _y0, _x1, _y1, _strokeWidth, _strokePaint);
	}
	public default void drawLine(double _x0, double _y0, double _x1, double _y1, Pen _pen) {
		drawSegment(_x0, _y0, _x1, _y1, _pen);
	}
	public default void drawLine(double _x, double _y, double _w, double _h, Brush _brush) {
		if(_brush.getPaint() != null)
			drawSegment(_x, _y, _w, _h, _brush.getWidth(), _brush.getPaint().toColor());
		else if(_brush.getPaintIn() != null)
			drawSegment(_x, _y, _w, _h, _brush.getWidth(), _brush.getPaintIn().toColor());
		else
			throw new RuntimeException();
	}
	public default void drawLine(double _x0, double _y0, double _x1, double _y1, LineStyle _style) {
		drawSegment(_x0, _y0, _x1, _y1, _style);
	}

	public void 		drawSegments(double[] _x, double[] _y);
	public void 		drawSegments(double[] _x, double[] _y, double _strokeWidth);
	public void 		drawSegments(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint);
	public default void drawSegments(double[] _x, double[] _y, Pen _pen) {
		drawSegments(_x, _y, _pen.getWidth(), _pen.getPaint().toColor());
	}
	public void 		drawSegments(double[] _x, double[] _y, LineStyle _style);

	public default void drawLines(double[] _x, double[] _y) {
		drawSegments(_x, _y);
	}
	public default void drawLines(double[] _x, double[] _y, double _strokeWidth) {
		drawSegments(_x, _y, _strokeWidth);
	}
	public default void drawLines(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
		drawSegments(_x, _y, _strokeWidth, _strokePaint);
	}
	public default void drawLines(double[] _x, double[] _y, Pen _pen) {
		drawSegments(_x, _y, _pen);
	}
	public default void drawLines(double[] _x, double[] _y, LineStyle _style) {
		drawSegments(_x, _y, _style);
	}

	public default void drawPolyLine(double[] _x, double[] _y) {
		drawSegments(_x, _y);
	}
	public default void drawPolyLine(double[] _x, double[] _y, double _strokeWidth) {
		drawSegments(_x, _y, _strokeWidth);
	}
	public default void drawPolyLine(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
		drawSegments(_x, _y, _strokeWidth, _strokePaint);
	}
	public default void drawPolyLine(double[] _x, double[] _y, Pen _pen) {
		drawSegments(_x, _y, _pen.getWidth(), _pen.getPaint().toColor());
	}
	public default void drawPolyLine(double[] _x, double[] _y, LineStyle _style) {
		drawSegments(_x, _y, _style);
	}

	// Primitives - 2D
	public void 		drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2);
	public void 		drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, Color _fillPaint);
	public default void drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, Paint _fillPaint) {
		drawTriangle(_x0, _y0, _x1, _y1, _x2, _y2, _fillPaint.toColor());
	}
	public void 		drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth);
	public void 		drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth, Color _strokePaint);
	public default void drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth, Paint _strokePaint) {
		drawTriangle(_x0, _y0, _x1, _y1, _x2, _y2, _strokePaint.toColor());
	}
	public void 		drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth, Color _strokePaint, Color _fillPaint);
	public default void drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth, Paint _strokePaint, Paint _fillPaint) {
		drawTriangle(_x0, _y0, _x1, _y1, _x2, _y2, _strokeWidth, _strokePaint.toColor(), _fillPaint.toColor());
	}
	public default void drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, Pen _pen) {
		drawTriangle(_x0, _y0, _x1, _y1, _x2, _y2, _pen.getWidth(), _pen.getPaint().toColor());
	}
	public default void drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, Pen _pen, Color _fillPaint) {
		drawTriangle(_x0, _y0, _x1, _y1, _x2, _y2, _pen.getWidth(), _pen.getPaint().toColor(), _fillPaint);
	}
	public default void drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, Pen _pen, Paint _paintin) {
		drawTriangle(_x0, _y0, _x1, _y1, _x2, _y2, _pen.getWidth(), _pen.getPaint().toColor(), _paintin.toColor());
	}
	public default void drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, Brush _brush) {
		if(_brush.getPaint() != null && _brush.getPaintIn() != null)
			drawTriangle(_x0, _y0, _x1, _y1, _x2, _y2, _brush.getWidth(), _brush.getPaint().toColor(), _brush.getPaintIn().toColor());
		else if(_brush.getPaintIn() != null)
			drawTriangle(_x0, _y0, _x1, _y1, _x2, _y2, _brush.getPaintIn().toColor());
		else
			drawTriangle(_x0, _y0, _x1, _y1, _x2, _y2, _brush.getWidth(), _brush.getPaint().toColor());
	}

	public void 		drawTriangles(double[] _x, double[] _y);
	public void 		drawTriangles(double[] _x, double[] _y, Color _fillPaint);
	public default void drawTriangles(double[] _x, double[] _y, Paint _fillPaint) {
		drawTriangles(_x, _y, _fillPaint.toColor());
	}
	public void 		drawTriangles(double[] _x, double[] _y, double _strokeWidth);
	public void 		drawTriangles(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint);
	public default void drawTriangles(double[] _x, double[] _y, double _strokeWidth, Paint _strokePaint) {
		drawTriangles(_x, _y, _strokeWidth, _strokePaint.toColor());
	}
	public void 		drawTriangles(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint, Color _fillPaint);
	public default void drawTriangles(double[] _x, double[] _y, double _strokeWidth, Paint _strokePaint, Paint _fillPaint) {
		drawTriangles(_x, _y, _strokeWidth, _strokePaint.toColor(), _fillPaint.toColor());
	}
	public default void drawTriangles(double[] _x, double[] _y, Pen _pen) {
		drawTriangles(_x, _y, _pen.getWidth(), _pen.getPaint().toColor());
	}
	public default void drawTriangles(double[] _x, double[] _y, Pen _pen, Color _fillPaint) {
		drawTriangles(_x, _y, _pen.getWidth(), _pen.getPaint().toColor(), _fillPaint);
	}
	public default void drawTriangles(double[] _x, double[] _y, Pen _pen, Paint _paintin) {
		drawTriangles(_x, _y, _pen.getWidth(), _pen.getPaint().toColor(), _paintin.toColor());
	}
	public default void drawTriangles(double[] _x, double[] _y, Brush _brush) {
		if(_brush.getPaint() != null && _brush.getPaintIn() != null)
			drawTriangles(_x, _y, _brush.getWidth(), _brush.getPaint().toColor(), _brush.getPaintIn().toColor());
		else if(_brush.getPaintIn() != null)
			drawTriangles(_x, _y, _brush.getPaintIn().toColor());
		else
			drawTriangles(_x, _y, _brush.getWidth(), _brush.getPaint().toColor());
	}

	public void 		drawRectangle(double _x, double _y, double _w, double _h);
	public void 		drawRectangle(double _x, double _y, double _w, double _h, Color _fillPaint);
	public default void drawRectangle(double _x, double _y, double _w, double _h, Paint _fillPaint) {
		drawRectangle(_x, _y, _w, _h, _fillPaint.toColor());
	}
	public void 		drawRectangle(double _x, double _y, double _w, double _h, double _strokeWidth);
	public void 		drawRectangle(double _x, double _y, double _w, double _h, double _strokeWidth, Color _strokePaint);
	public default void drawRectangle(double _x, double _y, double _w, double _h, double _strokeWidth, Paint _strokePaint) {
		drawRectangle(_x, _y, _w, _h, _strokeWidth, _strokePaint.toColor());
	}
	public void 		drawRectangle(double _x, double _y, double _w, double _h, double _strokeWidth, Color _strokePaint, Color _fillPaint);
	public default void drawRectangle(double _x, double _y, double _w, double _h, double _strokeWidth, Paint _strokePaint, Paint _fillPaint) {
		drawRectangle(_x, _y, _w, _h, _strokeWidth, _strokePaint.toColor(), _fillPaint.toColor());
	}
	public default void drawRectangle(double _x, double _y, double _w, double _h, Pen _pen) {
		drawRectangle(_x, _y, _w, _h, _pen.getWidth(), _pen.getPaint().toColor());
	}
	public default void drawRectangle(double _x, double _y, double _w, double _h, Pen _pen, Color _fillPaint) {
		drawRectangle(_x, _y, _w, _h, _pen.getWidth(), _pen.getPaint().toColor(), _fillPaint);
	}
	public default void drawRectangle(double _x, double _y, double _w, double _h, Pen _pen, Paint _paintin) {
		drawRectangle(_x, _y, _w, _h, _pen.getWidth(), _pen.getPaint().toColor(), _paintin.toColor());
	}
	public default void drawRectangle(double _x, double _y, double _w, double _h, Brush _brush) {
		if(_brush.getPaint() != null && _brush.getPaintIn() != null)
			drawRectangle(_x, _y, _w, _h, _brush.getWidth(), _brush.getPaint(), _brush.getPaintIn());
		else if(_brush.getPaintIn() != null)
			drawRectangle(_x, _y, _w, _h, _brush.getPaintIn());
		else
			drawRectangle(_x, _y, _w, _h, _brush.getWidth(), _brush.getPaint());
	}

	public void 		drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h);
	public void 		drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, Color _strokePaint);
	public default void drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, Paint _fillPaint) {
		drawRectangles(_x, _y, _w, _h, _fillPaint.toColor());
	}
	public void 		drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth);
	public void 		drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint);
	public default void drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Paint _strokePaint) {
		drawRectangles(_x, _y, _w, _h, _strokeWidth, _strokePaint.toColor());
	}
	public void 		drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint, Color _fillPaint);
	public default void drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Paint _strokePaint, Paint _fillPaint) {
		drawRectangles(_x, _y, _w, _h, _strokeWidth, _strokePaint.toColor(), _fillPaint.toColor());
	}
	public default void drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, Pen _pen) {
		drawRectangles(_x, _y, _w, _h, _pen.getWidth(), _pen.getPaint().toColor());
	}
	public default void drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, Pen _pen, Color _fillPaint) {
		drawRectangles(_x, _y, _w, _h, _pen.getWidth(), _pen.getPaint().toColor(), _fillPaint);
	}
	public default void drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, Pen _pen, Paint _paintin) {
		drawRectangles(_x, _y, _w, _h, _pen.getWidth(), _pen.getPaint().toColor(), _paintin.toColor());
	}
	public default void drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, Brush _brush) {
		if(_brush.getPaint() != null && _brush.getPaintIn() != null)
			drawRectangles(_x, _y, _w, _h, _brush.getWidth(), _brush.getPaint().toColor(), _brush.getPaintIn().toColor());
		else if(_brush.getPaintIn() != null)
			drawRectangles(_x, _y, _w, _h, _brush.getPaintIn().toColor());
		else
			drawRectangles(_x, _y, _w, _h, _brush.getWidth(), _brush.getPaint().toColor());
	}

	public void 		drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode);
	public void 		drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, Color _fillPaint);
	public default void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, Paint _fillPaint) {
		drawArc(_x, _y, _w, _h, _startAngle, _angle, _mode, _fillPaint.toColor());
	}
	public void 		drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, double _strokeWidth);
	public void 		drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, double _strokeWidth, Color _strokePaint);
	public default void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, double _strokeWidth, Paint _strokePaint) {
		drawArc(_x, _y, _w, _h, _startAngle, _angle, _mode, _strokeWidth, _strokePaint.toColor());
	}
	public void 		drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, double _strokeWidth, Color _strokePaint, Color _fillPaint);
	public default void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, double _strokeWidth, Paint _strokePaint, Paint _fillPaint) {
		drawArc(_x, _y, _w, _h, _startAngle, _angle, _mode, _strokeWidth, _strokePaint.toColor(), _fillPaint.toColor());
	}
	public default void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, Pen _pen) {
		drawArc(_x, _y, _w, _h, _startAngle, _angle, _mode, _pen.getWidth(), _pen.getPaint().toColor());
	}
	public default void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, Pen _pen, Color _fillPaint) {
		drawArc(_x, _y, _w, _h, _startAngle, _angle, _mode, _pen.getWidth(), _pen.getPaint().toColor(), _fillPaint);
	}
	public default void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, Pen _pen, Paint _paintin) {
		drawArc(_x, _y, _w, _h, _startAngle, _angle, _mode, _pen.getWidth(), _pen.getPaint().toColor(), _paintin.toColor());
	}
	public default void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, Brush _brush) {
		if(_brush.getPaint() != null && _brush.getPaintIn() != null)
			drawArc(_x, _y, _w, _h, _startAngle, _angle, _mode, _brush.getWidth(), _brush.getPaint().toColor(), _brush.getPaintIn().toColor());
		else if(_brush.getPaintIn() != null)
			drawArc(_x, _y, _w, _h, _startAngle, _angle, _mode, _brush.getPaintIn().toColor());
		else
			drawArc(_x, _y, _w, _h, _startAngle, _angle, _mode, _brush.getWidth(), _brush.getPaint().toColor());
	}

	public void 		drawEllipse(double _x, double _y, double _w, double _h);
	public void 		drawEllipse(double _x, double _y, double _w, double _h, Color _fillPaint);
	public default void drawEllipse(double _x, double _y, double _w, double _h, Paint _fillPaint) {
		drawEllipse(_x, _y, _w, _h, _fillPaint.toColor());
	}
	public void 		drawEllipse(double _x, double _y, double _w, double _h, double _strokeWidth);
	public void 		drawEllipse(double _x, double _y, double _w, double _h, double _strokeWidth, Color _strokePaint);
	public default void drawEllipse(double _x, double _y, double _w, double _h, double _strokeWidth, Paint _strokePaint) {
		drawEllipse(_x, _y, _w, _h, _strokeWidth, _strokePaint.toColor());
	}
	public void 		drawEllipse(double _x, double _y, double _w, double _h, double _strokeWidth, Color _strokePaint, Color _fillPaint);
	public default void drawEllipse(double _x, double _y, double _w, double _h, double _strokeWidth, Paint _strokePaint, Paint _fillPaint) {
		drawEllipse(_x, _y, _w, _h, _strokeWidth, _strokePaint.toColor(), _fillPaint.toColor());
	}
	public default void drawEllipse(double _x, double _y, double _w, double _h, Pen _pen) {
		drawEllipse(_x, _y, _w, _h, _pen.getWidth(), _pen.getPaint().toColor());
	}
	public default void drawEllipse(double _x, double _y, double _w, double _h, Pen _pen, Color _fillPaint) {
		drawEllipse(_x, _y, _w, _h, _pen.getWidth(), _pen.getPaint().toColor(), _fillPaint);
	}
	public default void drawEllipse(double _x, double _y, double _w, double _h, Pen _pen, Paint _paintin) {
		drawEllipse(_x, _y, _w, _h, _pen.getWidth(), _pen.getPaint().toColor(), _paintin.toColor());
	}
	public default void drawEllipse(double _x, double _y, double _w, double _h, Brush _brush) {
		if(_brush.getPaint() != null && _brush.getPaintIn() != null)
			drawEllipse(_x, _y, _w, _h, _brush.getWidth(), _brush.getPaint().toColor(), _brush.getPaintIn().toColor());
		else if(_brush.getPaintIn() != null)
			drawEllipse(_x, _y, _w, _h, _brush.getPaintIn().toColor());
		else
			drawEllipse(_x, _y, _w, _h, _brush.getWidth(), _brush.getPaint().toColor());
	}

	public default void drawCircle(double _x, double _y, double _r) {
		drawEllipse(_x, _y, _r, _r);
	}
	public default void drawCircle(double _x, double _y, double _r, Color _fillPaint) {
		drawEllipse(_x, _y, _r, _r, _fillPaint);
	}
	public default void drawCircle(double _x, double _y, double _r, Paint _fillPaint) {
		drawEllipse(_x, _y, _r, _r, _fillPaint);
	}
	public default void drawCircle(double _x, double _y, double _r, double _strokeWidth) {
		drawEllipse(_x, _y, _r, _r, _strokeWidth);
	}
	public default void drawCircle(double _x, double _y, double _r, double _strokeWidth, Color _strokePaint) {
		drawEllipse(_x, _y, _r, _r, _strokeWidth, _strokePaint);
	}
	public default void drawCircle(double _x, double _y, double _r, double _strokeWidth, Paint _strokePaint) {
		drawEllipse(_x, _y, _r, _r, _strokeWidth, _strokePaint);
	}
	public default void drawCircle(double _x, double _y, double _r, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		drawEllipse(_x, _y, _r, _r, _strokeWidth, _strokePaint, _fillPaint);
	}
	public default void drawCircle(double _x, double _y, double _r, double _strokeWidth, Paint _strokePaint, Paint _fillPaint) {
		drawEllipse(_x, _y, _r, _r, _strokeWidth, _strokePaint, _fillPaint);
	}
	public default void drawCircle(double _x, double _y, double _r, Pen _pen) {
		drawEllipse(_x, _y, _r, _r, _pen.getWidth(), _pen.getPaint().toColor());
	}
	public default void drawCircle(double _x, double _y, double _r, Pen _pen, Color _fillPaint) {
		drawEllipse(_x, _y, _r, _r, _pen.getWidth(), _pen.getPaint().toColor(), _fillPaint);
	}
	public default void drawCircle(double _x, double _y, double _r, Pen _pen, Paint _paintin) {
		drawEllipse(_x, _y, _r, _r, _pen.getWidth(), _pen.getPaint().toColor(), _paintin.toColor());
	}
	public default void drawCircle(double _x, double _y, double _r, Brush _brush) {
		if(_brush.getPaint() != null && _brush.getPaintIn() != null)
			drawEllipse(_x, _y, _r, _r, _brush.getWidth(), _brush.getPaint().toColor(), _brush.getPaintIn().toColor());
		else if(_brush.getPaintIn() != null)
			drawEllipse(_x, _y, _r, _r, _brush.getPaintIn().toColor());
		else
			drawEllipse(_x, _y, _r, _r, _brush.getWidth(), _brush.getPaint().toColor());
	}

	public void 		drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h);
	public void 		drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h, Color _fillPaint);
	public default void drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h, Paint _fillPaint) {
		drawEllipses(_x, _y, _w, _h, _fillPaint.toColor());
	}
	public void 		drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth);
	public void 		drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint);
	public default void drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Paint _strokePaint) {
		drawEllipses(_x, _y, _w, _h, _strokeWidth, _strokePaint.toColor());
	}
	public void 		drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint, Color _fillPaint);
	public default void drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Paint _strokePaint, Paint _fillPaint) {
		drawEllipses(_x, _y, _w, _h, _strokeWidth, _strokePaint.toColor(), _fillPaint.toColor());
	}
	public default void drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h, Pen _pen) {
		drawEllipses(_x, _y, _w, _h, _pen.getWidth(), _pen.getPaint().toColor());
	}
	public default void drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h, Pen _pen, Color _fillPaint) {
		drawEllipses(_x, _y, _w, _h, _pen.getWidth(), _pen.getPaint().toColor(), _fillPaint);
	}
	public default void drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h, Pen _pen, Paint _paintin) {
		drawEllipses(_x, _y, _w, _h, _pen.getWidth(), _pen.getPaint().toColor(), _paintin.toColor());
	}
	public default void drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h, Brush _brush) {
		if(_brush.getPaint() != null && _brush.getPaintIn() != null)
			drawEllipses(_x, _y, _w, _h, _brush.getWidth(), _brush.getPaint().toColor(), _brush.getPaintIn().toColor());
		else if(_brush.getPaintIn() != null)
			drawEllipses(_x, _y, _w, _h, _brush.getPaintIn().toColor());
		else
			drawEllipses(_x, _y, _w, _h, _brush.getWidth(), _brush.getPaint().toColor());
	}

	public default void drawCircles(double[] _x, double[] _y, double[] _r) {
		drawEllipses(_x, _y, _r, _r);
	}
	public default void drawCircles(double[] _x, double[] _y, double[] _r, Color _fillPaint) {
		drawEllipses(_x, _y, _r, _r, _fillPaint);
	}
	public default void drawCircles(double[] _x, double[] _y, double[] _r, Paint _fillPaint) {
		drawEllipses(_x, _y, _r, _r, _fillPaint.toColor());
	}
	public default void drawCircles(double[] _x, double[] _y, double[] _r, double _strokeWidth) {
		drawEllipses(_x, _y, _r, _r, _strokeWidth);
	}
	public default void drawCircles(double[] _x, double[] _y, double[] _r, double _strokeWidth, Color _strokePaint) {
		drawEllipses(_x, _y, _r, _r, _strokeWidth, _strokePaint);
	}
	public default void drawCircles(double[] _x, double[] _y, double[] _r, double _strokeWidth, Paint _strokePaint) {
		drawEllipses(_x, _y, _r, _r, _strokeWidth, _strokePaint);
	}
	public default void drawCircles(double[] _x, double[] _y, double[] _r, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		drawEllipses(_x, _y, _r, _r, _strokeWidth, _strokePaint, _fillPaint);
	}
	public default void drawCircles(double[] _x, double[] _y, double[] _r, double _strokeWidth, Paint _strokePaint, Paint _fillPaint) {
		drawEllipses(_x, _y, _r, _r, _strokeWidth, _strokePaint, _fillPaint);
	}
	public default void drawCircles(double[] _x, double[] _y, double[] _w, double[] _h, Pen _pen) {
		drawEllipses(_x, _y, _w, _h, _pen.getWidth(), _pen.getPaint().toColor());
	}
	public default void drawCircles(double[] _x, double[] _y, double[] _w, double[] _h, Pen _pen, Color _fillPaint) {
		drawEllipses(_x, _y, _w, _h, _pen.getWidth(), _pen.getPaint().toColor(), _fillPaint);
	}
	public default void drawCircles(double[] _x, double[] _y, double[] _w, double[] _h, Pen _pen, Paint _paintin) {
		drawEllipses(_x, _y, _w, _h, _pen.getWidth(), _pen.getPaint().toColor(), _paintin.toColor());
	}
	public default void drawCircles(double[] _x, double[] _y, double[] _w, double[] _h, Brush _brush) {
		if(_brush.getPaint() != null && _brush.getPaintIn() != null)
			drawEllipses(_x, _y, _w, _h, _brush.getWidth(), _brush.getPaint().toColor(), _brush.getPaintIn().toColor());
		else if(_brush.getPaintIn() != null)
			drawEllipses(_x, _y, _w, _h, _brush.getPaintIn().toColor());
		else
			drawEllipses(_x, _y, _w, _h, _brush.getWidth(), _brush.getPaint().toColor());
	}

	public void 		drawPolygon(double[] _x, double[] _y);
	public void 		drawPolygon(double[] _x, double[] _y, Color _fillPaint);
	public default void drawPolygon(double[] _x, double[] _y, Paint _fillPaint) {
		drawPolygon(_x, _y, _fillPaint.toColor());
	}
	public void 		drawPolygon(double[] _x, double[] _y, double _strokeWidth);
	public void 		drawPolygon(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint);
	public default void drawPolygon(double[] _x, double[] _y, double _strokeWidth, Paint _strokePaint) {
		drawPolygon(_x, _y, _strokeWidth, _strokePaint.toColor());
	}
	public void 		drawPolygon(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint, Color _fillPaint);
	public default void drawPolygon(double[] _x, double[] _y, double _strokeWidth, Paint _strokePaint, Paint _fillPaint) {
		drawPolygon(_x, _y, _strokeWidth, _strokePaint.toColor(), _fillPaint.toColor());
	}
	public default void drawPolygon(double[] _x, double[] _y, Pen _pen) {
		drawPolygon(_x, _y, _pen.getWidth(), _pen.getPaint().toColor());
	}
	public default void drawPolygon(double[] _x, double[] _y, Pen _pen, Color _fillPaint) {
		drawPolygon(_x, _y, _pen.getWidth(), _pen.getPaint().toColor(), _fillPaint);
	}
	public default void drawPolygon(double[] _x, double[] _y, Pen _pen, Paint _paintin) {
		drawPolygon(_x, _y, _pen.getWidth(), _pen.getPaint().toColor(), _paintin.toColor());
	}
	public default void drawPolygon(double[] _x, double[] _y, Brush _brush) {
		if(_brush.getPaint() != null && _brush.getPaintIn() != null)
			drawPolygon(_x, _y, _brush.getWidth(), _brush.getPaint().toColor(), _brush.getPaintIn().toColor());
		else if(_brush.getPaintIn() != null)
			drawPolygon(_x, _y, _brush.getPaintIn().toColor());
		else
			drawPolygon(_x, _y, _brush.getWidth(), _brush.getPaint().toColor());
	}

	public void 		drawString(String string, double _x, double _y);
	public void 		drawString(String string, double _x, double _y, double _fontSize);
	public void 		drawString(String string, double _x, double _y, String _fontName, double _fontSize);
	public void 		drawString(String string, double _x, double _y, Font _font);

	// =============================================================
	// JavaFR Objects - ALL ' default ' methods
	// ===========================================
	public default void drawObject(Shape2D _object) {
		if(_object instanceof Point2D) {
			drawPoint((Point2D) _object);
		}
		else if(_object instanceof Segment2D) {
			drawSegment((Segment2D) _object);
		}
		else if(_object instanceof Triangle2D) {
			drawTriangle((Triangle2D) _object);
		}
		else if(_object instanceof Rectangle2D) {
			drawRectangle((Rectangle2D) _object);
		}
		else if(_object instanceof Ellipse2D) {
			drawEllipse((Ellipse2D) _object);
		}
		else if(_object instanceof Polyline2D) {
			drawPolyLine((Polyline2D) _object);
		}
		else if(_object instanceof Polygon2D) {
			drawPolygon((Polygon2D) _object);
		}
/*
		else if(_object instanceof Line2D l) {
			drawLine(l);
		}
		else if(_object instanceof Ray2D ray) {
			drawRay(ray);
		}
		else if(_object instanceof Vector2D vec) {
			drawVector(vec);
		}
*/
	}
	public default void drawObjects(Shape2D... _objects) {
		for(Shape2D o : _objects)
			drawObject(o);
	}

	public default void drawPoint(Point2D _pt) 																								{ drawPoint(_pt.getX(), _pt.getY()); }
	public default void drawPoint(Point2D _pt, double _strokeWidth) 																		{ drawPoint(_pt.getX(), _pt.getY(), _strokeWidth); }
	public default void drawPoint(Point2D _pt, double _strokeWidth, Color _strokePaint) 													{ drawPoint(_pt.getX(), _pt.getY(), _strokeWidth, _strokePaint); }

	public default void drawPoint(Point2D _pt, PointSkin _skin)																				{ drawPoint(_pt.getX(), _pt.getY(), _skin); }
	public default void drawPoint(Point2D _pt, PointSkin _skin, double _ptSize)																{ drawPoint(_pt.getX(), _pt.getY(), _skin, _ptSize); }
	public default void drawPoint(Point2D _pt, PointSkin _skin, double _ptSize, double _strokeWidth, Color _strokePaint)					{ drawPoint(_pt.getX(), _pt.getY(), _skin, _ptSize, _strokeWidth, _strokePaint); }
	public default void drawPoint(Point2D _pt, PointSkin _skin, double _ptSize, double _strokeWidth, Color _strokePaint, Color _fillPaint)	{ drawPoint(_pt.getX(), _pt.getY(), _skin, _ptSize, _strokeWidth, _strokePaint, _fillPaint); }
	public default void drawPoint(Point2D _pt, PointSkin _skin, double _ptSize, Pen _pen) 													{ drawPoint(_pt.getX(), _pt.getY(), _skin, _ptSize, _pen.getWidth(), _pen.toColor()); }
	public default void drawPoint(Point2D _pt, PointStyle _style) 																			{ drawPoint(_pt.getX(), _pt.getY(), _style.getSkin(), _style.getSize(), _style.getPen().getWidth(), _style.getPen().toColor()); }
	
	public default void drawPoints(List<Point2D> _pts) {
		double[] X = _pts.stream().map(Point2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _pts.stream().map(Point2D::getY).mapToDouble(Double::doubleValue).toArray();
		drawPoints(X, Y);
	}
	public default void drawPoints(List<Point2D> _pts, double _strokeWidth) {
		double[] X = _pts.stream().map(Point2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _pts.stream().map(Point2D::getY).mapToDouble(Double::doubleValue).toArray();
		drawPoints(X, Y, _strokeWidth);
	}
	public default void drawPoints(List<Point2D> _pts, double _strokeWidth, Color _strokePaint) {
		double[] X = _pts.stream().map(Point2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _pts.stream().map(Point2D::getY).mapToDouble(Double::doubleValue).toArray();
		drawPoints(X, Y, _strokeWidth, _strokePaint);
	}

	public default void drawSegment(Point2D _beg, Point2D _end) 																			{ drawSegment(_beg.getX(), _beg.getY(), _end.getX(), _end.getY()); }
	public default void drawSegment(Point2D _beg, Point2D _end, double _strokeWidth) 														{ drawSegment(_beg.getX(), _beg.getY(), _end.getX(), _end.getY(), _strokeWidth); }
	public default void drawSegment(Point2D _beg, Point2D _end, double _strokeWidth, Color _strokePaint)									{ drawSegment(_beg.getX(), _beg.getY(), _end.getX(), _end.getY(), _strokeWidth, _strokePaint); }

	public default void drawSegment(Segment2D _segment) 																					{ drawSegment(_segment.getBegin().getX(), _segment.getBegin().getY(), _segment.getEnd().getX(), _segment.getEnd().getY()); }
	public default void drawSegment(Segment2D _segment, double _strokeWidth) 																{ drawSegment(_segment.getBegin().getX(), _segment.getBegin().getY(), _segment.getEnd().getX(), _segment.getEnd().getY(), _strokeWidth); }
	public default void drawSegment(Segment2D _segment, double _strokeWidth, Color _strokePaint)											{ drawSegment(_segment.getBegin().getX(), _segment.getBegin().getY(), _segment.getEnd().getX(), _segment.getEnd().getY(), _strokeWidth, _strokePaint); }

	public default void drawLine(Point2D _beg, Point2D _end) 																				{ drawSegment(_beg.getX(), _beg.getY(), _end.getX(), _end.getY()); }
	public default void drawLine(Point2D _beg, Point2D _end, double _strokeWidth) 															{ drawSegment(_beg.getX(), _beg.getY(), _end.getX(), _end.getY(), _strokeWidth); }
	public default void drawLine(Point2D _beg, Point2D _end, double _strokeWidth, Color _strokePaint)										{ drawSegment(_beg.getX(), _beg.getY(), _end.getX(), _end.getY(), _strokeWidth, _strokePaint); }

	public default void drawSegments(List<Segment2D> _segments) {
		double[] X = _segments.stream().map(t -> new double[] { t.getBegin().getX(), t.getBegin().getX() }).flatMapToDouble(array -> Arrays.stream(array)).toArray();
		double[] Y = _segments.stream().map(t -> new double[] { t.getBegin().getY(), t.getBegin().getY() }).flatMapToDouble(array -> Arrays.stream(array)).toArray();
		drawSegments(X, Y);
	}
	public default void drawSegments(List<Segment2D> _segments, double _strokeWidth) {
		double[] X = _segments.stream().map(t -> new double[] { t.getBegin().getX(), t.getBegin().getX() }).flatMapToDouble(array -> Arrays.stream(array)).toArray();
		double[] Y = _segments.stream().map(t -> new double[] { t.getBegin().getY(), t.getBegin().getY() }).flatMapToDouble(array -> Arrays.stream(array)).toArray();
		drawSegments(X, Y, _strokeWidth);
	}
	public default void drawSegments(List<Segment2D> _segments, double _strokeWidth, Color _strokePaint) {
		double[] X = _segments.stream().map(t -> new double[] { t.getBegin().getX(), t.getBegin().getX() }).flatMapToDouble(array -> Arrays.stream(array)).toArray();
		double[] Y = _segments.stream().map(t -> new double[] { t.getBegin().getY(), t.getBegin().getY() }).flatMapToDouble(array -> Arrays.stream(array)).toArray();
		drawSegments(X, Y, _strokeWidth, _strokePaint);
	}

	public default void drawVector(Vector2D _vector, Point2D _origin, double _lineWidth, Color _color) {
		if(_vector == null || _origin == null)
        	return ;

    	Point2D to = _origin.plus(_vector);

    	double theta = 45 * Math.PI / 180d;
    	double cos_t = Math.cos(theta);
    	double sin_t = Math.sin(theta);
    	double x1  = _origin.getX();
    	double y1  = _origin.getY();
    	double x2  =      to.getX();
    	double y2  =      to.getY();
    	double dx  =          x2-x1;
    	double dy  =          y2-y1;
    	double L1  = Math.sqrt(dx*dx + dy*dy);
    	double L2  = L1 / 10;

    	double x_l = x2 + L2/L1 * ((x1-x2)*cos_t+(y1-y2)*sin_t);
    	double y_l = y2 + L2/L1 * ((y1-y2)*cos_t-(x1-x2)*sin_t);
    	double x_r = x2 + L2/L1 * ((x1-x2)*cos_t-(y1-y2)*sin_t);
    	double y_r = y2 + L2/L1 * ((y1-y2)*cos_t+(x1-x2)*sin_t);

        drawLine(_origin.getX(), _origin.getY(), to.getX(), to.getY(), _lineWidth, _color);
        drawLine(to.getX(), to.getY(), x_l, y_l, _lineWidth, _color);
        drawLine(to.getX(), to.getY(), x_r, y_r, _lineWidth, _color);
	}
	
	public default void drawPolyLine(List<Point2D> _pts) {
		double[] X = _pts.stream().map(Point2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _pts.stream().map(Point2D::getY).mapToDouble(Double::doubleValue).toArray();
		drawSegments(X, Y);
	}
	public default void drawPolyLine(List<Point2D> _pts, double _strokeWidth) {
		double[] X = _pts.stream().map(Point2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _pts.stream().map(Point2D::getY).mapToDouble(Double::doubleValue).toArray();
		drawSegments(X, Y, _strokeWidth);
	}
	public default void drawPolyLine(List<Point2D> _pts, double _strokeWidth, Color _strokePaint) {
		double[] X = _pts.stream().map(Point2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _pts.stream().map(Point2D::getY).mapToDouble(Double::doubleValue).toArray();
		drawSegments(X, Y, _strokeWidth, _strokePaint);
	}

	public default void drawPolyLine(Polyline2D _pl) {
		double[] X = _pl.getPoints().stream().map(Point2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _pl.getPoints().stream().map(Point2D::getY).mapToDouble(Double::doubleValue).toArray();
		drawSegments(X, Y);
	}
	public default void drawPolyLine(Polyline2D _pl, double _strokeWidth) {
		double[] X = _pl.getPoints().stream().map(Point2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _pl.getPoints().stream().map(Point2D::getY).mapToDouble(Double::doubleValue).toArray();
		drawSegments(X, Y, _strokeWidth);
	}
	public default void drawPolyLine(Polyline2D _pl, double _strokeWidth, Color _strokePaint) {
		double[] X = _pl.getPoints().stream().map(Point2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _pl.getPoints().stream().map(Point2D::getY).mapToDouble(Double::doubleValue).toArray();
		drawSegments(X, Y, _strokeWidth, _strokePaint);
	}

	public default void drawTriangle(Point2D _a, Point2D _b, Point2D _c) 															{ drawTriangle(_a.getX(), _a.getY(), _b.getX(), _b.getY(), _c.getX(), _c.getY()); }
	public default void drawTriangle(Point2D _a, Point2D _b, Point2D _c, double _strokeWidth)										{ drawTriangle(_a.getX(), _a.getY(), _b.getX(), _b.getY(), _c.getX(), _c.getY(), _strokeWidth); }
	public default void drawTriangle(Point2D _a, Point2D _b, Point2D _c, double _strokeWidth, Color _strokePaint)					{ drawTriangle(_a.getX(), _a.getY(), _b.getX(), _b.getY(), _c.getX(), _c.getY(), _strokeWidth, _strokePaint); }
	public default void drawTriangle(Point2D _a, Point2D _b, Point2D _c, double _strokeWidth, Color _strokePaint, Color _fillPaint)	{ drawTriangle(_a.getX(), _a.getY(), _b.getX(), _b.getY(), _c.getX(), _c.getY(), _strokeWidth, _strokePaint, _fillPaint); }

	public default void drawTriangle(Triangle2D _t) 																				{ drawTriangle(_t.getA().getX(), _t.getA().getY(), _t.getB().getX(), _t.getB().getY(), _t.getC().getX(), _t.getC().getY()); }
	public default void drawTriangle(Triangle2D _t, double _strokeWidth)															{ drawTriangle(_t.getA().getX(), _t.getA().getY(), _t.getB().getX(), _t.getB().getY(), _t.getC().getX(), _t.getC().getY(), _strokeWidth); }
	public default void drawTriangle(Triangle2D _t, double _strokeWidth, Color _strokePaint)										{ drawTriangle(_t.getA().getX(), _t.getA().getY(), _t.getB().getX(), _t.getB().getY(), _t.getC().getX(), _t.getC().getY(), _strokeWidth, _strokePaint); }
	public default void drawTriangle(Triangle2D _t, double _strokeWidth, Color _strokePaint, Color _fillPaint)						{ drawTriangle(_t.getA().getX(), _t.getA().getY(), _t.getB().getX(), _t.getB().getY(), _t.getC().getX(), _t.getC().getY(), _strokeWidth, _strokePaint, _fillPaint); }

	public default void drawTriangles(List<Triangle2D> _triangles) {
		double[] X = _triangles.stream().map(t -> new double[] { t.getA().getX(), t.getB().getX(), t.getC().getX() }).flatMapToDouble(array -> Arrays.stream(array)).toArray();
		double[] Y = _triangles.stream().map(t -> new double[] { t.getA().getY(), t.getB().getY(), t.getC().getY() }).flatMapToDouble(array -> Arrays.stream(array)).toArray();
		drawTriangles(X, Y);
	}
	public default void drawTriangles(List<Triangle2D> _triangles, double _strokeWidth) {
		double[] X = _triangles.stream().map(t -> new double[] { t.getA().getX(), t.getB().getX(), t.getC().getX() }).flatMapToDouble(array -> Arrays.stream(array)).toArray();
		double[] Y = _triangles.stream().map(t -> new double[] { t.getA().getY(), t.getB().getY(), t.getC().getY() }).flatMapToDouble(array -> Arrays.stream(array)).toArray();
		drawTriangles(X, Y, _strokeWidth);
	}
	public default void drawTriangles(List<Triangle2D> _triangles, double _strokeWidth, Color _strokePaint) {
		double[] X = _triangles.stream().map(t -> new double[] { t.getA().getX(), t.getB().getX(), t.getC().getX() }).flatMapToDouble(array -> Arrays.stream(array)).toArray();
		double[] Y = _triangles.stream().map(t -> new double[] { t.getA().getY(), t.getB().getY(), t.getC().getY() }).flatMapToDouble(array -> Arrays.stream(array)).toArray();
		drawTriangles(X, Y, _strokeWidth, _strokePaint);
	}
	public default void drawTriangles(List<Triangle2D> _triangles, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		double[] X = _triangles.stream().map(t -> new double[] { t.getA().getX(), t.getB().getX(), t.getC().getX() }).flatMapToDouble(array -> Arrays.stream(array)).toArray();
		double[] Y = _triangles.stream().map(t -> new double[] { t.getA().getY(), t.getB().getY(), t.getC().getY() }).flatMapToDouble(array -> Arrays.stream(array)).toArray();
		drawTriangles(X, Y, _strokeWidth, _strokePaint, _fillPaint);
	}

	public default void drawRectangle(Rectangle2D _r) {
		drawRectangle(_r.getX(), _r.getY(), _r.getWidth(), _r.getHeight());
	}
	public default void drawRectangle(Rectangle2D _r, double _strokeWidth) {
		drawRectangle(_r.getX(), _r.getY(), _r.getWidth(), _r.getHeight(), _strokeWidth);
	}
	public default void drawRectangle(Rectangle2D _r, double _strokeWidth, Color _strokePaint) {
		drawRectangle(_r.getX(), _r.getY(), _r.getWidth(), _r.getHeight(), _strokeWidth, _strokePaint);
	}
	public default void drawRectangle(Rectangle2D _r, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		drawRectangle(_r.getX(), _r.getY(), _r.getWidth(), _r.getHeight(), _strokeWidth, _strokePaint, _fillPaint);
	}

	public default void drawRectangles(List<Rectangle2D> _rectangles) {
		double[] X = _rectangles.stream().map(Rectangle2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _rectangles.stream().map(Rectangle2D::getY).mapToDouble(Double::doubleValue).toArray();
		double[] W = _rectangles.stream().map(Rectangle2D::getWidth).mapToDouble(Double::doubleValue).toArray();
		double[] H = _rectangles.stream().map(Rectangle2D::getHeight).mapToDouble(Double::doubleValue).toArray();
		drawRectangles(X, Y, W, H);
	}
	public default void drawRectangles(List<Rectangle2D> _rectangles, double _strokeWidth) {
		double[] X = _rectangles.stream().map(Rectangle2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _rectangles.stream().map(Rectangle2D::getY).mapToDouble(Double::doubleValue).toArray();
		double[] W = _rectangles.stream().map(Rectangle2D::getWidth).mapToDouble(Double::doubleValue).toArray();
		double[] H = _rectangles.stream().map(Rectangle2D::getHeight).mapToDouble(Double::doubleValue).toArray();
		drawRectangles(X, Y, W, H, _strokeWidth);
	}
	public default void drawRectangles(List<Rectangle2D> _rectangles, double _strokeWidth, Color _strokePaint) {
		double[] X = _rectangles.stream().map(Rectangle2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _rectangles.stream().map(Rectangle2D::getY).mapToDouble(Double::doubleValue).toArray();
		double[] W = _rectangles.stream().map(Rectangle2D::getWidth).mapToDouble(Double::doubleValue).toArray();
		double[] H = _rectangles.stream().map(Rectangle2D::getHeight).mapToDouble(Double::doubleValue).toArray();
		drawRectangles(X, Y, W, H, _strokeWidth, _strokePaint);
	}
	public default void drawRectangles(List<Rectangle2D> _rectangles, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		double[] X = _rectangles.stream().map(Rectangle2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _rectangles.stream().map(Rectangle2D::getY).mapToDouble(Double::doubleValue).toArray();
		double[] W = _rectangles.stream().map(Rectangle2D::getWidth).mapToDouble(Double::doubleValue).toArray();
		double[] H = _rectangles.stream().map(Rectangle2D::getHeight).mapToDouble(Double::doubleValue).toArray();
		drawRectangles(X, Y, W, H, _strokeWidth, _strokePaint, _fillPaint);
	}

	public default void drawEllipse(Ellipse2D _e) 																					{ drawEllipse(_e.getX(), _e.getY(), _e.getWidth(), _e.getHeight()); }
	public default void drawEllipse(Ellipse2D _e, double _strokeWidth)																{ drawEllipse(_e.getX(), _e.getY(), _e.getWidth(), _e.getHeight(), _strokeWidth); }
	public default void drawEllipse(Ellipse2D _e, double _strokeWidth, Color _strokePaint)											{ drawEllipse(_e.getX(), _e.getY(), _e.getWidth(), _e.getHeight(), _strokeWidth, _strokePaint); }
	public default void drawEllipse(Ellipse2D _e, double _strokeWidth, Color _strokePaint, Color _fillPaint)						{ drawEllipse(_e.getX(), _e.getY(), _e.getWidth(), _e.getHeight(), _strokeWidth, _strokePaint, _fillPaint); }

	public default void drawEllipses(List<Ellipse2D> _ellipses) {
		double[] X = _ellipses.stream().map(Ellipse2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _ellipses.stream().map(Ellipse2D::getY).mapToDouble(Double::doubleValue).toArray();
		double[] W = _ellipses.stream().map(Ellipse2D::getWidth).mapToDouble(Double::doubleValue).toArray();
		double[] H = _ellipses.stream().map(Ellipse2D::getHeight).mapToDouble(Double::doubleValue).toArray();
		drawEllipses(X, Y, W, H);
	}
	public default void drawEllipses(List<Ellipse2D> _ellipses, double _strokeWidth) {
		double[] X = _ellipses.stream().map(Ellipse2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _ellipses.stream().map(Ellipse2D::getY).mapToDouble(Double::doubleValue).toArray();
		double[] W = _ellipses.stream().map(Ellipse2D::getWidth).mapToDouble(Double::doubleValue).toArray();
		double[] H = _ellipses.stream().map(Ellipse2D::getHeight).mapToDouble(Double::doubleValue).toArray();
		drawEllipses(X, Y, W, H, _strokeWidth);
	}
	public default void drawEllipses(List<Ellipse2D> _ellipses, double _strokeWidth, Color _strokePaint) {
		double[] X = _ellipses.stream().map(Ellipse2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _ellipses.stream().map(Ellipse2D::getY).mapToDouble(Double::doubleValue).toArray();
		double[] W = _ellipses.stream().map(Ellipse2D::getWidth).mapToDouble(Double::doubleValue).toArray();
		double[] H = _ellipses.stream().map(Ellipse2D::getHeight).mapToDouble(Double::doubleValue).toArray();
		drawEllipses(X, Y, W, H, _strokeWidth, _strokePaint);
	}
	public default void drawEllipses(List<Ellipse2D> _ellipses, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		double[] X = _ellipses.stream().map(Ellipse2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _ellipses.stream().map(Ellipse2D::getY).mapToDouble(Double::doubleValue).toArray();
		double[] W = _ellipses.stream().map(Ellipse2D::getWidth).mapToDouble(Double::doubleValue).toArray();
		double[] H = _ellipses.stream().map(Ellipse2D::getHeight).mapToDouble(Double::doubleValue).toArray();
		drawEllipses(X, Y, W, H, _strokeWidth, _strokePaint, _fillPaint);
	}

	public default void drawPolygon(List<Point2D> _polygon) {
		double[] X = _polygon.stream().map(Point2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _polygon.stream().map(Point2D::getY).mapToDouble(Double::doubleValue).toArray();
		drawPolygon(X, Y);
	}
	public default void drawPolygon(List<Point2D> _polygon, double _strokeWidth) {
		double[] X = _polygon.stream().map(Point2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _polygon.stream().map(Point2D::getY).mapToDouble(Double::doubleValue).toArray();
		drawPolygon(X, Y, _strokeWidth);
	}
	public default void drawPolygon(List<Point2D> _polygon, double _strokeWidth, Color _strokePaint) {
		double[] X = _polygon.stream().map(Point2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _polygon.stream().map(Point2D::getY).mapToDouble(Double::doubleValue).toArray();
		drawPolygon(X, Y, _strokeWidth, _strokePaint);
	}
	public default void drawPolygon(List<Point2D> _polygon, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		double[] X = _polygon.stream().map(Point2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _polygon.stream().map(Point2D::getY).mapToDouble(Double::doubleValue).toArray();
		drawPolygon(X, Y, _strokeWidth, _strokePaint, _fillPaint);
	}

	public default void drawPolygon(Polygon2D _polygon) {
		double[] X = _polygon.getTops().stream().map(Point2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _polygon.getTops().stream().map(Point2D::getY).mapToDouble(Double::doubleValue).toArray();
		drawPolygon(X, Y);
	}
	public default void drawPolygon(Polygon2D _polygon, double _strokeWidth) {
		double[] X = _polygon.getTops().stream().map(Point2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _polygon.getTops().stream().map(Point2D::getY).mapToDouble(Double::doubleValue).toArray();
		drawPolygon(X, Y, _strokeWidth);
	}
	public default void drawPolygon(Polygon2D _polygon, double _strokeWidth, Color _strokePaint) {
		double[] X = _polygon.getTops().stream().map(Point2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _polygon.getTops().stream().map(Point2D::getY).mapToDouble(Double::doubleValue).toArray();
		drawPolygon(X, Y, _strokeWidth, _strokePaint);
	}
	public default void drawPolygon(Polygon2D _polygon, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		double[] X = _polygon.getTops().stream().map(Point2D::getX).mapToDouble(Double::doubleValue).toArray();
		double[] Y = _polygon.getTops().stream().map(Point2D::getY).mapToDouble(Double::doubleValue).toArray();
		drawPolygon(X, Y, _strokeWidth, _strokePaint, _fillPaint);
	}
/*
	public void 		drawLine(Line2D _line);
	public void 		drawLine(Line2D _line, double _strokeWidth);
	public void 		drawLine(Line2D _line, double _strokeWidth, Color _strokePaint);

	public void 		drawRay(Ray2D _ray);
	public void 		drawRay(Ray2D _ray, double _strokeWidth);
	public void 		drawRay(Ray2D _ray, double _strokeWidth, Color _strokePaint);

	public void 		drawVector(Vector2D _ray);
	public void 		drawVector(Vector2D _ray, double _strokeWidth);
	public void 		drawVector(Vector2D _ray, double _strokeWidth, Color _strokePaint);
*/
//	public void 		drawImage(BufferedImage image, double _x, double _y);
//	public void 		drawImage(BufferedImage image, double _x, double _y, double _angle);

//	public void 		drawImage(Image image, TwoDims object, TwoDims of);

}
