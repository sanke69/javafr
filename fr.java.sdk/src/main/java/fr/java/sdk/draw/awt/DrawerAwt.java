package fr.java.sdk.draw.awt;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import fr.drawer.awt.Drawer4Awt;
import fr.java.draw.Drawer;
import fr.java.draw.Drawer.ArcMode;
import fr.java.draw.styles.LineStyle;
import fr.java.draw.styles.PointSkin;
import fr.java.draw.tools.Color;
import fr.java.draw.tools.Colors;
import fr.java.draw.tools.Font;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Dimension.TwoDims;
import fr.java.math.geometry.plane.Point2D;
import fr.java.sdk.math.Points;
import javafx.scene.image.Image;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.VLineTo;
import javafx.scene.text.Text;

public class DrawerAwt {

	public static java.awt.Color 		   awtColor(fr.java.draw.tools.Color _color) {
		return new java.awt.Color((float) (_color.toRGB().getRed()),
					              (float) (_color.toRGB().getGreen()),
					              (float) (_color.toRGB().getBlue()),
					              (float) (_color.getOpacity()));
	}

	public static Drawer newDrawer(Graphics _g) {
		return new Drawer4Awt() {

			@Override
			public void setDrawStyle(String string) {
				;
			}

			@Override
			public void setLineWidth(double _width) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setFill		(Color _color) {}
			@Override
			public void setStroke	(Color _color) {}

			@Override
			public void clear(Color _c) {
				DrawerAwt.clear(_g, _c);
			}

			@Override
			public void drawPoint(double _x, double _y) {
				DrawerAwt.drawPoint(_g, _x, _y);
			}
			@Override
			public void drawPoint(double _x, double _y, double _strokeWidth) {
				DrawerAwt.drawPoint(_g, _x, _y, _strokeWidth);
			}
			@Override
			public void drawPoint(double _x, double _y, double _strokeWidth, Color _strokePaint) {
				DrawerAwt.drawPoint(_g, _x, _y, _strokeWidth, _strokePaint);
			}

			@Override
			public void drawPoints(double[] _x, double[] _y) {
				DrawerAwt.drawPoints(_g, _x, _y);
			}
			@Override
			public void drawPoints(double[] _x, double[] _y, double _strokeWidth) {
				DrawerAwt.drawPoints(_g, _x, _y, _strokeWidth);
			}
			@Override
			public void drawPoints(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
				DrawerAwt.drawPoints(_g, _x, _y, _strokeWidth, _strokePaint);
			}

			@Override
			public void drawSegment(double _x0, double _y0, double _x1, double _y1) {
				DrawerAwt.drawSegment(_g, _x0, _y0, _x1, _y1);
			}
			@Override
			public void drawSegment(double _x0, double _y0, double _x1, double _y1, double _strokeWidth) {
				DrawerAwt.drawSegment(_g, _x0, _y0, _x1, _y1, _strokeWidth);
			}
			@Override
			public void drawSegment(double _x0, double _y0, double _x1, double _y1, double _strokeWidth, Color _strokePaint) {
				DrawerAwt.drawSegment(_g, _x0, _y0, _x1, _y1, _strokeWidth, _strokePaint);
			}

			@Override
			public void drawSegments(double[] _x, double[] _y) {
				DrawerAwt.drawSegments(_g, _x, _y);
			}
			@Override
			public void drawSegments(double[] _x, double[] _y, double _strokeWidth) {
				DrawerAwt.drawSegments(_g, _x, _y, _strokeWidth);
			}
			@Override
			public void drawSegments(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
				DrawerAwt.drawSegments(_g, _x, _y, _strokeWidth, _strokePaint);
			}

			@Override
			public void drawPolyLine(double[] _x, double[] _y) {
				DrawerAwt.drawPolyLine(_g, _x, _y);
			}
			@Override
			public void drawPolyLine(double[] _x, double[] _y, double _strokeWidth) {
				DrawerAwt.drawPolyLine(_g, _x, _y, _strokeWidth);
			}
			@Override
			public void drawPolyLine(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
				DrawerAwt.drawPolyLine(_g, _x, _y, _strokeWidth, _strokePaint);
			}

			@Override
			public void drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2) {
				DrawerAwt.drawTriangle(_g, _x0, _y0, _x1, _y1, _x2, _y2);
			}
			@Override
			public void drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth) {
				DrawerAwt.drawTriangle(_g, _x0, _y0, _x1, _y1, _x2, _y2, _strokeWidth);
			}
			@Override
			public void drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth, Color _strokePaint) {
				DrawerAwt.drawTriangle(_g, _x0, _y0, _x1, _y1, _x2, _y2, _strokeWidth, _strokePaint);
			}
			@Override
			public void drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
				DrawerAwt.drawTriangle(_g, _x0, _y0, _x1, _y1, _x2, _y2, _strokeWidth, _strokePaint, _fillPaint);
			}

			@Override
			public void drawTriangles(double[] _x, double[] _y) {
				DrawerAwt.drawTriangles(_g, _x, _y);
			}
			@Override
			public void drawTriangles(double[] _x, double[] _y, double _strokeWidth) {
				DrawerAwt.drawTriangles(_g, _x, _y, _strokeWidth);
			}
			@Override
			public void drawTriangles(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
				DrawerAwt.drawTriangles(_g, _x, _y, _strokeWidth, _strokePaint);
			}
			@Override
			public void drawTriangles(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
				DrawerAwt.drawTriangles(_g, _x, _y, _strokeWidth, _strokePaint, _fillPaint);
			}
			
			public void drawRectangle(double _x, double _y, double _w, double _h) {
				DrawerAwt.drawRectangle(_g, _x, _y, _w, _h);
			}
			public void drawRectangle(double _x, double _y, double _w, double _h, double _strokeWidth) {
				DrawerAwt.drawRectangle(_g, _x, _y, _w, _h, _strokeWidth);
			}
			public void drawRectangle(double _x, double _y, double _w, double _h, double _strokeWidth, Color _strokePaint) {
				DrawerAwt.drawRectangle(_g, _x, _y, _w, _h, _strokeWidth, _strokePaint);
			}
			@Override
			public void drawRectangle(double _x, double _y, double _w, double _h, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
				DrawerAwt.drawRectangle(_g, _x, _y, _w, _h, _strokeWidth, _strokePaint, _fillPaint);
			}

			public void drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h) {
				DrawerAwt.drawRectangles(_g, _x, _y, _w, _h);
			}
			public void drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth) {
				DrawerAwt.drawRectangles(_g, _x, _y, _w, _h, _strokeWidth);
			}
			public void drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint) {
				DrawerAwt.drawRectangles(_g, _x, _y, _w, _h, _strokeWidth, _strokePaint);
			}
			@Override
			public void drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
				DrawerAwt.drawRectangles(_g, _x, _y, _w, _h, _strokeWidth, _strokePaint, _fillPaint);
			}

			@Override
			public void drawEllipse(double _cx, double _cy, double _w, double _h) {
				DrawerAwt.drawEllipse(_g, _cx, _cy, _w, _h);
			}
			@Override
			public void drawEllipse(double _cx, double _cy, double _w, double _h, double _strokeWidth) {
				DrawerAwt.drawEllipse(_g, _cx, _cy, _w, _h, _strokeWidth);
			}
			@Override
			public void drawEllipse(double _cx, double _cy, double _w, double _h, double _strokeWidth, Color _strokePaint) {
				DrawerAwt.drawEllipse(_g, _cx, _cy, _w, _h, _strokeWidth, _strokePaint);
			}
			@Override
			public void drawEllipse(double _cx, double _cy, double _w, double _h, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
				DrawerAwt.drawEllipse(_g, _cx, _cy, _w, _h, _strokeWidth, _strokePaint, _fillPaint);
			}

			@Override
			public void drawEllipses(double[] _cx, double[] _cy, double[] _w, double[] _h) {
				DrawerAwt.drawEllipses(_g, _cx, _cy, _w, _h);
			}
			@Override
			public void drawEllipses(double[] _cx, double[] _cy, double[] _w, double[] _h, double _strokeWidth) {
				DrawerAwt.drawEllipses(_g, _cx, _cy, _w, _h, _strokeWidth);
			}
			@Override
			public void drawEllipses(double[] _cx, double[] _cy, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint) {
				DrawerAwt.drawEllipses(_g, _cx, _cy, _w, _h, _strokeWidth, _strokePaint);
			}
			@Override
			public void drawEllipses(double[] _cx, double[] _cy, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
				DrawerAwt.drawEllipses(_g, _cx, _cy, _w, _h, _strokeWidth, _strokePaint, _fillPaint);
			}

			@Override
			public void drawPolygon(double[] _x, double[] _y) {
				DrawerAwt.drawPolygon(_g, _x, _y);
			}
			@Override
			public void drawPolygon(double[] _x, double[] _y, double _strokeWidth) {
				DrawerAwt.drawPolygon(_g, _x, _y, _strokeWidth);
			}
			@Override
			public void drawPolygon(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
				DrawerAwt.drawPolygon(_g, _x, _y, _strokeWidth, _strokePaint);
			}
			@Override
			public void drawPolygon(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
				DrawerAwt.drawPolygon(_g, _x, _y, _strokeWidth, _strokePaint, _fillPaint);
			}

			@Override
			public void drawImage(BufferedImage _img, double _x, double _y) {
				DrawerAwt.drawImage(_g, _img, _x, _y);
			}
			@Override
			public void drawImage(BufferedImage _img, double _x, double _y, double _angle) {
				DrawerAwt.drawImage(_g, _img, _x, _y, _angle);
			}
			@Override
			public void drawImage(BufferedImage _img, BoundingBox.TwoDims _in, BoundingBox.TwoDims _out) {
				DrawerAwt.drawImage(_g, _img, _in, _out);
			}

//			@Override
			public void drawShape(Shape _shape) {
				DrawerAwt.drawShape(_g, _shape);
			}
//			@Override
			public void drawPath(Path _path) {
				DrawerAwt.drawPath(_g, _path);
			}

			@Override
			public void drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, Color _fillPaint) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void drawTriangles(double[] _x, double[] _y, Color _fillPaint) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void drawRectangle(double _x, double _y, double _w, double _h, Color _fillPaint) {
				DrawerAwt.drawRectangle(_g, _x, _y, _w, _h, 1, Colors.BLACK, _fillPaint);
			}

			@Override
			public void drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, Color _strokePaint) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, Color _fillPaint) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, double _strokeWidth) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, double _strokeWidth, Color _strokePaint) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void drawEllipse(double _cx, double _cy, double _w, double _h, Color _fillPaint) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void drawEllipses(double[] _cx, double[] _cy, double[] _w, double[] _h, Color _fillPaint) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void drawPolygon(double[] _x, double[] _y, Color _fillPaint) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void drawString(String string, double _x, double _y) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void drawString(String string, double _x, double _y, double _fontSize) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void drawString(String string, double _x, double _y, String _fontName, double _fontSize) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void drawString(String string, double _x, double _y, Font _font) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void drawPoint(double _x, double _y, PointSkin _skin) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void drawPoint(double _x, double _y, PointSkin _skin, double _ptSize) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void drawPoint(double _x, double _y, PointSkin _skin, double _ptSize, double _strokeWidth, Color _strokePaint) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void drawPoint(double _x, double _y, PointSkin _skin, double _ptSize, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public TwoDims getTextDimension(String _text, Font _font) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void drawSegment(double _x0, double _y0, double _x1, double _y1, LineStyle _style) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void drawSegments(double[] _x, double[] _y, LineStyle _style) {
				// TODO Auto-generated method stub
				
			}

			
		};
	}

	public static void clear(Graphics _g, Color _c) {
		int W = (int) _g.getClip().getBounds().getWidth();
		int H = (int) _g.getClip().getBounds().getHeight();

		setColor(_g, _c);
		_g.clearRect(0, 0, W, H);
		_g.fillRect(0, 0, W, H);
		resetColor(_g);
	}

	public static void setColor(Graphics _g, Color _paint) {
		_g.setColor(awtColor(_paint));
	}
	public static void resetColor(Graphics _g) {
		_g.setColor(awtColor(Colors.BLACK));
	}


	// Primitives - 1D
	public static void drawPoint(Graphics _g, double _x, double _y) {
		_g.fillRect((int) _x, (int) _y, 1, 1);
	}
	public static void drawPoint(Graphics _g, double _x, double _y, double _strokeWidth) {
		_g.fillRect((int) (_x - (_strokeWidth - 1) / 2.0), (int) (_y - (_strokeWidth - 1) / 2.0), (int) _strokeWidth, (int) _strokeWidth);
	}
	public static void drawPoint(Graphics _g, double _x, double _y, double _strokeWidth, Color _strokePaint) {
		_g.setColor(awtColor(_strokePaint));
		_g.fillRect((int) (_x - (_strokeWidth - 1) / 2.0), (int) (_y - (_strokeWidth - 1) / 2.0), (int) _strokeWidth, (int) _strokeWidth);
	}

	public static void drawPoints(Graphics _g, double[] _x, double[] _y) {
		assert(_x.length == _y.length);

		for(int i = 0; i < _x.length; ++i)
			drawPoint(_g, _x[i], _y[i]);
	}
	public static void drawPoints(Graphics _g, double[] _x, double[] _y, double _strokeWidth) {
		assert(_x.length == _y.length);

		for(int i = 0; i < _x.length; ++i)
			drawPoint(_g, _x[i], _y[i], _strokeWidth);
	}
	public static void drawPoints(Graphics _g, double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
		assert(_x.length == _y.length);

		setColor(_g, _strokePaint);
		drawPoints(_g, _x, _y, _strokeWidth);
		resetColor(_g);
	}

	public static void drawSegment(Graphics _g, double _x0, double _y0, double _x1, double _y1) {
		_g.drawLine((int) _x0, (int) _y0, (int) _x1, (int) _y1);
	}
	public static void drawSegment(Graphics _g, double _x0, double _y0, double _x1, double _y1, double _strokeWidth) {
		// TODO:: Find solution for strokeWidth
		_g.drawLine((int) _x0, (int) _y0, (int) _x1, (int) _y1);
	}
	public static void drawSegment(Graphics _g, double _x0, double _y0, double _x1, double _y1, double _strokeWidth, Color _strokePaint) {
		// TODO:: Find solution for strokeWidth
		setColor(_g, _strokePaint);
		_g.drawLine((int) _x0, (int) _y0, (int) _x1, (int) _y1);
		resetColor(_g);
	}

	public static void drawSegments(Graphics _g, double[] _x, double[] _y) {
		assert(_x.length == _y.length);
		for(int i = 0; i < _x.length - 1; ++i)
			_g.drawLine((int) _x[i], (int) _y[i], (int) _x[i+1], (int) _y[i+1]);
	}
	public static void drawSegments(Graphics _g, double[] _x, double[] _y, double _strokeWidth) {
		// TODO:: Find solution for strokeWidth
		assert(_x.length == _y.length);
		for(int i = 0; i < _x.length - 1; ++i)
			_g.drawLine((int) _x[i], (int) _y[i], (int) _x[i+1], (int) _y[i+1]);
	}
	public static void drawSegments(Graphics _g, double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
		setColor(_g, _strokePaint);
		drawSegments(_g, _x, _y, _strokeWidth);
		resetColor(_g);
	}

	public static void drawPolyLine(Graphics _g, double[] _x, double[] _y) {
		assert(_x.length == _y.length);
		for(int i = 0; i < _x.length - 1; ++i)
			_g.drawLine((int) _x[i], (int) _y[i], (int) _x[i+1], (int) _y[i+1]);
	}
	public static void drawPolyLine(Graphics _g, double[] _x, double[] _y, double _strokeWidth) {
		// TODO:: Find solution for strokeWidth
		assert(_x.length == _y.length);
		for(int i = 0; i < _x.length - 1; ++i)
			_g.drawLine((int) _x[i], (int) _y[i], (int) _x[i+1], (int) _y[i+1]);
	}
	public static void drawPolyLine(Graphics _g, double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
		setColor(_g, _strokePaint);
		drawPolyLine(_g, _x, _y, _strokeWidth);
		resetColor(_g);
	}

	// Primitives - 2D
	public static void drawTriangle(Graphics _g, double _x0, double _y0, double _x1, double _y1, double _x2, double _y2) {
		_g.drawPolygon(new int[] { (int) _x0, (int) _x1, (int) _x2 }, new int[] { (int) _y0, (int) _y1, (int) _y2 }, 3);
	}
	public static void drawTriangle(Graphics _g, double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth) {
		// TODO:: Find solution for strokeWidth
		_g.drawPolygon(new int[] { (int) _x0, (int) _x1, (int) _x2 }, new int[] { (int) _y0, (int) _y1, (int) _y2 }, 3);
	}
	public static void drawTriangle(Graphics _g, double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth, Color _strokePaint) {
		setColor(_g, _strokePaint);
		drawTriangle(_g, _x0, _y0, _x1, _y1, _x2, _y2, _strokeWidth);
		resetColor(_g);
	}
	public static void drawTriangle(Graphics _g, double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		// TODO:: Find solution for strokeWidth
		setColor(_g, _fillPaint);
		_g.fillPolygon(new int[] { (int) _x0, (int) _x1, (int) _x2 }, new int[] { (int) _y0, (int) _y1, (int) _y2 }, 3);
		setColor(_g, _strokePaint);
		_g.drawPolygon(new int[] { (int) _x0, (int) _x1, (int) _x2 }, new int[] { (int) _y0, (int) _y1, (int) _y2 }, 3);
		resetColor(_g);
	}

	public static void drawTriangles(Graphics _g, double[] _x, double[] _y) {
		assert(_x.length == _y.length);
		assert(_x.length % 3 == 0);
		
		for(int i = 0; i < _x.length / 3; i += 3)
			_g.drawPolygon(new int[] { (int) _x[i], (int) _x[i+1], (int) _x[i+2] }, new int[] { (int) _y[i], (int) _y[i+1], (int) _y[i+2] }, 3);
	}
	public static void drawTriangles(Graphics _g, double[] _x, double[] _y, double _strokeWidth) {
		// TODO:: Find solution for strokeWidth
		assert(_x.length == _y.length);
		assert(_x.length % 3 == 0);

		for(int i = 0; i < _x.length / 3; i += 3)
			_g.drawPolygon(new int[] { (int) _x[i], (int) _x[i+1], (int) _x[i+2] }, new int[] { (int) _y[i], (int) _y[i+1], (int) _y[i+2] }, 3);
	}
	public static void drawTriangles(Graphics _g, double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
		setColor(_g, _strokePaint);
		drawTriangles(_g, _x, _y, _strokeWidth);
		resetColor(_g);
	}
	public static void drawTriangles(Graphics _g, double[] _x, double[] _y, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		assert(_x.length == _y.length);
		assert(_x.length % 3 == 0);

		setColor(_g, _fillPaint);
		for(int i = 0; i < _x.length / 3; i += 3)
			_g.fillPolygon(new int[] { (int) _x[i], (int) _x[i+1], (int) _x[i+2] }, new int[] { (int) _y[i], (int) _y[i+1], (int) _y[i+2] }, 3);
		setColor(_g, _strokePaint);
		drawTriangles(_g, _x, _y, _strokeWidth);
		resetColor(_g);
		
		
		for(int i = 0; i < _x.length / 3; i += 3)
			_g.drawPolygon(new int[] { (int) _x[i], (int) _x[i+1], (int) _x[i+2] }, new int[] { (int) _y[i], (int) _y[i+1], (int) _y[i+2] }, 3);
	}

	public static void drawRectangle(Graphics _g, double _x, double _y, double _w, double _h) {
		_g.drawRect((int) _x, (int) _y, (int) _w, (int) _h);
	}
	public static void drawRectangle(Graphics _g, double _x, double _y, double _w, double _h, double _strokeWidth) {
		// TODO:: Find solution for strokeWidth
		_g.drawRect((int) _x, (int) _y, (int) _w, (int) _h);
	}
	public static void drawRectangle(Graphics _g, double _x, double _y, double _w, double _h, double _strokeWidth, Color _strokePaint) {
		setColor(_g, _strokePaint);
		_g.drawRect((int) _x, (int) _y, (int) _w, (int) _h);
		resetColor(_g);
	}
	public static void drawRectangle(Graphics _g, double _x, double _y, double _w, double _h, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		setColor(_g, _strokePaint);
		_g.drawRect((int) _x, (int) _y, (int) _w, (int) _h);
		setColor(_g, _fillPaint);
		_g.fillRect((int) (_x + _strokeWidth), (int) (_y + _strokeWidth), (int) (_w - 2 * _strokeWidth), (int) (_h - 2 * _strokeWidth));
		resetColor(_g);
	}

	// TODO:: Continue...
	
	public static void drawRectangles(Graphics _g, double[] _x, double[] _y, double[] _w, double[] _h) {
		assert(_x.length == _y.length && _x.length == _w.length && _x.length == _h.length);
		
		for(int i = 0; i < _x.length; ++i)
			_g.drawRect((int) _x[i], (int) _y[i], (int) _w[i], (int) _h[i]);
	}
	public static void drawRectangles(Graphics _g, double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth) {
		assert(_x.length == _y.length && _x.length == _w.length && _x.length == _h.length);
		
		for(int i = 0; i < _x.length; ++i)
			_g.drawRect((int) _x[i], (int) _y[i], (int) _w[i], (int) _h[i]);
	}
	public static void drawRectangles(Graphics _g, double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint) {
		assert(_x.length == _y.length && _x.length == _w.length && _x.length == _h.length);
		
		for(int i = 0; i < _x.length; ++i)
			_g.drawRect((int) _x[i], (int) _y[i], (int) _w[i], (int) _h[i]);
	}
	public static void drawRectangles(Graphics _g, double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		assert(_x.length == _y.length && _x.length == _w.length && _x.length == _h.length);
		
		for(int i = 0; i < _x.length; ++i)
			_g.drawRect((int) _x[i], (int) _y[i], (int) _w[i], (int) _h[i]);
	}

	public static void drawEllipse(Graphics _g, double _cx, double _cy, double _w, double _h) {
		_g.drawOval((int) _cx, (int) _cy, (int) _w, (int) _h);
	}
	public static void drawEllipse(Graphics _g, double _cx, double _cy, double _w, double _h, double _strokeWidth) {
		_g.drawOval((int) _cx, (int) _cy, (int) _w, (int) _h);
	}
	public static void drawEllipse(Graphics _g, double _cx, double _cy, double _w, double _h, double _strokeWidth, Color _strokePaint) {
		_g.drawOval((int) _cx, (int) _cy, (int) _w, (int) _h);
	}
	public static void drawEllipse(Graphics _g, double _cx, double _cy, double _w, double _h, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		_g.drawOval((int) _cx, (int) _cy, (int) _w, (int) _h);
	}

	public static void drawEllipses(Graphics _g, double[] _cx, double[] _cy, double[] _w, double[] _h) {
		assert(_cx.length == _cy.length && _cx.length == _w.length && _cx.length == _h.length);

		for(int i = 0; i < _cx.length; ++i)
			_g.drawOval((int) _cx[i], (int) _cy[i], (int) _w[i], (int) _h[i]);
	}
	public static void drawEllipses(Graphics _g, double[] _cx, double[] _cy, double[] _w, double[] _h, double _strokeWidth) {
		assert(_cx.length == _cy.length && _cx.length == _w.length && _cx.length == _h.length);

		for(int i = 0; i < _cx.length; ++i)
			_g.drawOval((int) _cx[i], (int) _cy[i], (int) _w[i], (int) _h[i]);
	}
	public static void drawEllipses(Graphics _g, double[] _cx, double[] _cy, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint) {
		assert(_cx.length == _cy.length && _cx.length == _w.length && _cx.length == _h.length);

		for(int i = 0; i < _cx.length; ++i)
			_g.drawOval((int) _cx[i], (int) _cy[i], (int) _w[i], (int) _h[i]);
	}
	public static void drawEllipses(Graphics _g, double[] _cx, double[] _cy, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		assert(_cx.length == _cy.length && _cx.length == _w.length && _cx.length == _h.length);

		for(int i = 0; i < _cx.length; ++i)
			_g.drawOval((int) _cx[i], (int) _cy[i], (int) _w[i], (int) _h[i]);
	}

	public static void drawPolygon(Graphics _g, double[] _x, double[] _y) {
		assert(_x.length == _y.length);

		int[] X = Arrays.stream(_x).mapToInt(d -> (int) d).toArray();
		int[] Y = Arrays.stream(_y).mapToInt(d -> (int) d).toArray();
		_g.drawPolygon((int[]) X, (int[]) Y, _x.length);
	}
	public static void drawPolygon(Graphics _g, double[] _x, double[] _y, double _strokeWidth) {
		assert(_x.length == _y.length);

		int[] X = Arrays.stream(_x).mapToInt(d -> (int) d).toArray();
		int[] Y = Arrays.stream(_y).mapToInt(d -> (int) d).toArray();
		_g.drawPolygon((int[]) X, (int[]) Y, _x.length);
	}
	public static void drawPolygon(Graphics _g, double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
		assert(_x.length == _y.length);

		int[] X = Arrays.stream(_x).mapToInt(d -> (int) d).toArray();
		int[] Y = Arrays.stream(_y).mapToInt(d -> (int) d).toArray();
		_g.drawPolygon((int[]) X, (int[]) Y, _x.length);
	}
	public static void drawPolygon(Graphics _g, double[] _x, double[] _y, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		assert(_x.length == _y.length);

		int[] X = Arrays.stream(_x).mapToInt(d -> (int) d).toArray();
		int[] Y = Arrays.stream(_y).mapToInt(d -> (int) d).toArray();
		_g.drawPolygon((int[]) X, (int[]) Y, _x.length);
	}

	public static void drawString(Graphics _g, String _string, double _x, double _y) {
		_g.drawString(_string, (int) _x, (int) _y);
	}
	public static void 		drawString(Graphics _g, String _string, double _x, double _y, double _fontSize) {
		_g.drawString(_string, (int) _x, (int) _y);
	}
	public static void 		drawString(Graphics _g, String _string, double _x, double _y, String _fontName, double _fontSize) {
		_g.drawString(_string, (int) _x, (int) _y);
	}
	public static void 		drawString(Graphics _g, String _string, double _x, double _y, Font _font) {
		_g.drawString(_string, (int) _x, (int) _y);
	}

	// AWT Objects

	public static void drawImage(Graphics _g, BufferedImage _img, double _x, double _y) {
		if(_img != null)
			_g.drawImage(_img, (int) _x, (int) _y, null);
	}
	public static void drawImage(Graphics _g, BufferedImage _img, double _x, double _y, double _angle) {
		if(_img != null) {
			BufferedImage bgImage = new BufferedImage((int) (1.5 * _img.getWidth()), (int) (1.5 * _img.getHeight()), BufferedImage.TYPE_INT_ARGB);
	        
			Graphics2D      g2dbf = bgImage.createGraphics();
		    AffineTransform af    = new AffineTransform();
	        af.setToIdentity();
	        af.translate(.5 * _img.getWidth(), .5 * _img.getHeight());
	        af.rotate(Math.toRadians(_angle), _img.getWidth()/2, _img.getHeight()/2);

		    g2dbf.setTransform(new AffineTransform());
	        g2dbf.drawImage(_img,af,null);
	         
	        _g.drawImage(bgImage, (int) _x, (int) _y, null);
		}
	}
	public static void drawImage(Graphics _g, BufferedImage _img, BoundingBox.TwoDims _in, BoundingBox.TwoDims _out) {
		if(_img != null)
			_g.drawImage(_img,
					(int) _out.getMinX(), (int) _out.getMinY(), (int) _out.getMaxX(), (int) _out.getMaxY(),
					(int)  _in.getMinX(), (int)  _in.getMinY(), (int)  _in.getMaxX(), (int)  _in.getMaxY(),
				    null);
	}

	// JavaFX Objects
	public static void drawShapes(Graphics _g, Shape... _shapes) {
		for(Shape s : _shapes)
			drawShape(_g, s);
	}

	public static void drawShape(Graphics _g, Shape _shape) {
//		_g.setStroke(_shape.getStroke());
//		_g.setLineWidth(_shape.getStrokeWidth());

		if(_shape instanceof Arc) {
			drawShape_Arc(_g, (Arc) _shape);
		}
		else if(_shape instanceof Circle) {
			drawShape_Circle(_g, (Circle) _shape);
		}
		else if(_shape instanceof Ellipse) {
			drawShape_Ellipse(_g, (Ellipse) _shape);
		}
		else if(_shape instanceof Line) {
			drawShape_Line(_g, (Line) _shape);
		}
		else if(_shape instanceof Polygon) {
			drawShape_Polygon(_g, (Polygon) _shape);
		}
		else if(_shape instanceof Polyline) {
			drawShape_Polyline(_g, (Polyline) _shape);
		}
		else if(_shape instanceof Rectangle) {
			drawShape_Rectangle(_g, (Rectangle) _shape);
		}
		else if(_shape instanceof Text) {
			drawShape_Text(_g, (Text) _shape);
		}
		else if(_shape instanceof CubicCurve) {
			drawShape_CubicCurve(_g, (CubicCurve) _shape);
		}
		else if(_shape instanceof QuadCurve) {
			drawShape_QuadCurve(_g, (QuadCurve) _shape);
		}

		else if(_shape instanceof Path) {
			drawPath(_g, (Path) _shape);
		}
	}
	static void drawShape_Arc(Graphics _g, Arc _arc) {
		Point2D u  = Points.of(_arc.getCenterX() - _arc.getRadiusX(), _arc.getCenterY() + _arc.getRadiusY());
		Point2D v  = Points.of(2.0 * _arc.getRadiusX(), 2.0 * _arc.getRadiusY());
		double  e0 = _arc.getStartAngle();
		double  e1 = e0 + _arc.getLength();

		if(_arc.getFill() != null) {
//			_g.setFill(_arc.getFill());
			_g.drawArc((int) u.getX(), (int) u.getY(), (int) v.getX(), (int) v.getY(), (int) e0, (int) e1);
		} else
			_g.drawArc((int) u.getX(), (int) u.getY(), (int) v.getX(), (int) v.getY(), (int) e0, (int) e1);
	}
	static void drawShape_Circle(Graphics _g, Circle _circle) {
		Point2D u = Points.of(_circle.getCenterX() - _circle.getRadius(), _circle.getCenterY() - _circle.getRadius());
		Point2D v = Points.of(2.0 * _circle.getRadius(), 2.0 * _circle.getRadius());

		if(_circle.getFill() != null) {
//			_g.setFill(_circle.getFill());
			_g.drawOval((int) u.getX(), (int) u.getY(), (int) v.getX(), (int) v.getY());
		} else
			_g.drawOval((int) u.getX(), (int) u.getY(), (int) v.getX(), (int) v.getY());		
	}
	static void drawShape_CubicCurve(Graphics _g, CubicCurve _curve) {
		// c.getControlX1(), c.getControlY1() ???
		// c.getControlX2(), c.getControlY2() ???

		if(_curve.getFill() != null) {
//			_g.setFill(_curve.getFill());
			_g.drawOval((int) _curve.getStartX(), (int) _curve.getStartY(), (int) _curve.getEndX(), (int) _curve.getEndY());
		} else
			_g.drawOval((int) _curve.getStartX(), (int) _curve.getStartY(), (int) _curve.getEndX(), (int) _curve.getEndY());
	}
	static void drawShape_QuadCurve(Graphics _g, QuadCurve _curve) {
		// c.getControlX1(), c.getControlY1() ???
		// c.getControlX2(), c.getControlY2() ???

		if(_curve.getFill() != null) {
//			_g.setFill(_curve.getFill());
			_g.drawOval((int) _curve.getStartX(), (int) _curve.getStartY(), (int) _curve.getEndX(), (int) _curve.getEndY());
		} else
			_g.drawOval((int) _curve.getStartX(), (int) _curve.getStartY(), (int) _curve.getEndX(), (int) _curve.getEndY());
	}
	static void drawShape_Ellipse(Graphics _g, Ellipse _ellipse) {
		Point2D u = Points.of(_ellipse.getCenterX() - _ellipse.getRadiusX(), _ellipse.getCenterY() + _ellipse.getRadiusY());
		Point2D v = Points.of(2.0 * _ellipse.getRadiusX(), 2.0 * _ellipse.getRadiusY());

		if(_ellipse.getFill() != null) {
//			_g.setFill(_ellipse.getFill());
			_g.drawOval((int) u.getX(), (int) u.getY(), (int) v.getX(), (int) v.getY());
		} else
			_g.drawOval((int) u.getX(), (int) u.getY(), (int) v.getX(), (int) v.getY());	
	}
	static void drawShape_Line(Graphics _g, Line _line) {
		_g.drawLine((int) _line.getStartX(), (int) _line.getStartY(), (int) _line.getEndX(), (int) _line.getEndY());
	}
	static void drawShape_Polygon(Graphics _g, Polygon _polygon) {
		int nbPoints = _polygon.getPoints().size() / 2;
		int[] x = new int[nbPoints];
		int[] y = new int[nbPoints];
		for(int i = 0; i < nbPoints; ++i) {
			x[i] = (int) _polygon.getPoints().get(2*i).intValue();
			y[i] = (int) _polygon.getPoints().get(2*i+1).intValue();
		}

		if(_polygon.getFill() != null) {
//			_g.setFill(_polygon.getFill());
			_g.drawPolygon(x, y, nbPoints);
		} else
			_g.drawPolygon(x, y, nbPoints);
	}
	static void drawShape_Polyline(Graphics _g, Polyline _polyline) {
		int nbPoints = _polyline.getPoints().size() / 2;
		int[] x = new int[nbPoints];
		int[] y = new int[nbPoints];
		for(int i = 0; i < nbPoints; ++i) {
			x[i] = (int) _polyline.getPoints().get(2*i).intValue();
			y[i] = (int) _polyline.getPoints().get(2*i+1).intValue();
		}

		_g.drawPolyline(x, y, nbPoints);
	}
	static void drawShape_Rectangle(Graphics _g, Rectangle _rectangle) {
		if(_rectangle.getFill() != null) {
//			_g.setFill(_rectangle.getFill());
			_g.drawRect((int) _rectangle.getX(), (int) _rectangle.getY(), (int) _rectangle.getWidth(), (int) _rectangle.getHeight());
//			gc2d.fillRoundRect(r.getX(), r.getY(), r.getWidth(), r.getHeight(), 10, 10);
		} else
			_g.drawRect((int) _rectangle.getX(), (int) _rectangle.getY(), (int) _rectangle.getWidth(), (int) _rectangle.getHeight());
//			gc2d.strokeRoundRect(r.getX(), r.getY(), r.getWidth(), r.getHeight(), 10, 10);
	}
	static void drawShape_Text(Graphics _g, Text _text) {
		if(_text.getFill() != null) {
//			_g.setFill(_text.getFill());
			_g.drawString(_text.getText(), (int) _text.getX(), (int) _text.getY());
		} else
			_g.drawString(_text.getText(), (int) _text.getX(), (int) _text.getY());
	}


	public static void drawPath(Graphics _g, Path _p) {
		Point2D lastp = Points.zero2();

		for(PathElement pe : _p.getElements()) {
			if(pe instanceof MoveTo) {
				lastp = drawPath_MoveTo(_g, (MoveTo) pe);
			}
			if(pe instanceof ArcTo) {
				lastp = drawPath_ArcTo(_g, (ArcTo) pe);
			}
			if(pe instanceof LineTo) {
				lastp = drawPath_LineTo(_g, (LineTo) pe);
			}
			if(pe instanceof HLineTo) {
				lastp = drawPath_HLineTo(_g, (HLineTo) pe, lastp);
			}
			if(pe instanceof VLineTo) {
				lastp = drawPath_VLineTo(_g, (VLineTo) pe, lastp);
			}
			if(pe instanceof CubicCurveTo) {
				lastp = drawPath_CubicCurveTo(_g, (CubicCurveTo) pe);
			}
			if(pe instanceof QuadCurveTo) {
				lastp = drawPath_QuadCurveTo(_g, (QuadCurveTo) pe);
			}
			if(pe instanceof ClosePath) {
				// _g.closePath();
			}

		}
	}
	static Point2D drawPath_MoveTo(Graphics _g, MoveTo _moveTo) {
		return Points.of(_moveTo.getX(), _moveTo.getY());
	}
	static Point2D drawPath_ArcTo(Graphics _g, ArcTo _arcTo) {
//		_g.arcTo(_arcTo.getX(), _arcTo.getY(), _arcTo.getX() + _arcTo.getRadiusX(), _arcTo.getY() + _arcTo.getRadiusY(), _arcTo.getRadiusX());
		return Points.of(_arcTo.getX(), _arcTo.getY());
	}
	static Point2D drawPath_LineTo(Graphics _g, LineTo _lineTo) {
//		_g.lineTo(_lineTo.getX(), _lineTo.getY());
		return Points.of(_lineTo.getX(), _lineTo.getY());
	}
	static Point2D drawPath_HLineTo(Graphics _g, HLineTo _hLineTo, Point2D _lastP) {
//		_g.lineTo(_hLineTo.getX(), _lastP.getY());
		return Points.of(_hLineTo.getX(), _lastP.getY());
	}
	static Point2D drawPath_VLineTo(Graphics _g, VLineTo _vLineTo, Point2D _lastP) {
//		_g.lineTo(_lastP.getX(), _vLineTo.getY());
		return Points.of(_lastP.getX(), _vLineTo.getY());
	}
	static Point2D drawPath_CubicCurveTo(Graphics _g, CubicCurveTo _cubicCurveTo) {
//		_g.bezierCurveTo(_cubicCurveTo.getControlX1(), _cubicCurveTo.getControlY1(), _cubicCurveTo.getControlX2(), _cubicCurveTo.getControlY2(), _cubicCurveTo.getX(), _cubicCurveTo.getY());
		return Points.of(_cubicCurveTo.getX(), _cubicCurveTo.getY());
	}
	static Point2D drawPath_QuadCurveTo(Graphics _g, QuadCurveTo _quadCurveTo) {
//		_g.quadraticCurveTo(_quadCurveTo.getControlX(), _quadCurveTo.getControlY(), _quadCurveTo.getX(), _quadCurveTo.getY());
		return Points.of(_quadCurveTo.getX(), _quadCurveTo.getY());
	}

}
