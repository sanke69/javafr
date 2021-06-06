package fr.drawer.fx;

import java.util.Set;
import java.util.stream.Collectors;

import fr.java.draw.Drawer;
import fr.java.draw.ViewportDrawer;
import fr.java.draw.styles.LineStyle;
import fr.java.draw.styles.PointSkin;
import fr.java.draw.tools.Color;
import fr.java.draw.tools.Font;
import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Viewport;
import fr.java.math.geometry.plane.Ellipse2D;
import fr.java.math.geometry.plane.Line2D;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.geometry.plane.Polygon2D;
import fr.java.math.geometry.plane.Polyline2D;
import fr.java.math.geometry.plane.Rectangle2D;
import fr.java.math.geometry.plane.Segment2D;
import fr.java.math.geometry.plane.Triangle2D;
import fr.java.math.topology.Coordinate;
import fr.java.sdk.math.Ellipses;
import fr.java.sdk.math.Lines;
import fr.java.sdk.math.Points;
import fr.java.sdk.math.Polygons;
import fr.java.sdk.math.Polylines;
import fr.java.sdk.math.Rectangles;
import fr.java.sdk.math.Segments;
import fr.java.sdk.math.Triangles;
import fr.java.utils.primitives.Arrays;
import fr.javafx.scene.canvas.ResizableCanvas;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

@Deprecated
public class ViewportDrawerFx extends DrawerFx implements ViewportDrawer {
	protected Viewport.TwoDims<?, ?> viewport;
	protected Canvas canvas;

	public ViewportDrawerFx() {
		super();
	}
	public ViewportDrawerFx(Canvas _canvas) {
		super();
		canvas = _canvas;
	}
	public ViewportDrawerFx(Canvas _canvas, Viewport.TwoDims<?, ?> _viewport) {
		viewport = _viewport;
		canvas   = _canvas;
	}
	public ViewportDrawerFx(ResizableCanvas _canvas, Viewport.TwoDims<?, ?> _viewport) {
		viewport = _viewport;
		canvas   = _canvas;
	}
	public ViewportDrawerFx(ViewportDrawerFx _drawer) {
		super();
		viewport = _drawer.viewport;
		canvas   = _drawer.canvas;
	}

	public void 		   	setCanvas(Canvas _canvas) {
		if(_canvas == null)
			throw new NullPointerException();
		canvas = _canvas;
	}
	public Canvas 		   	canvas() {
		return canvas;
	}

	public void 			setViewport(Viewport.TwoDims<?, ?> _viewport) {
		viewport = _viewport;
	}
	public Viewport.TwoDims<?, ?> viewport() {
		return viewport;
	}

	public GraphicsContext 	gc() {
		return canvas.getGraphicsContext2D();
	}

	public double[] 			mod2win(double _x, double _y) {
		Coordinate.TwoDims pt = viewport().boundsInWindow(Points.of(_x, _y));
		return new double[] { pt.getFirst(), pt.getSecond() };
	}
	public double[][]			mod2win(double[] _x, double[] _y) {
		double[][] result = new double[2][_x.length];
		for(int i = 0; i < _x.length; ++i) {
			Coordinate.TwoDims pt = viewport().boundsInWindow(Points.of(_x[i], _y[i]));
			result[0][i] = pt.getFirst();
			result[1][i] = pt.getSecond();
		}
		return result;
	}
	public Coordinate.TwoDims 	mod2win(Coordinate.TwoDims _pt) {
		return viewport().boundsInWindow(_pt);
	}
	public Segment2D 			mod2win(Segment2D _seg) {
		Coordinate.TwoDims beg = viewport().boundsInWindow(_seg.getBegin());
		Coordinate.TwoDims end = viewport().boundsInWindow(_seg.getEnd());
		return Segments.of(beg, end);
	}
	public Triangle2D 			mod2win(Triangle2D _tri) {
		Coordinate.TwoDims A = viewport().boundsInWindow(_tri.getA());
		Coordinate.TwoDims B = viewport().boundsInWindow(_tri.getB());
		Coordinate.TwoDims C = viewport().boundsInWindow(_tri.getC());
		return Triangles.of(A, B, C);
	}
	public Rectangle2D 			mod2win(Rectangle2D _rec) {
		Set<Coordinate.TwoDims> tops = _rec.getTops().stream().map(viewport()::boundsInWindow).collect(Collectors.toSet());
		return Rectangles.of(tops);
	}
	public Ellipse2D 			mod2win(Ellipse2D _ell) {
		Coordinate.TwoDims A = viewport().boundsInWindow(Points.of(_ell.getX(), _ell.getY()));
		Coordinate.TwoDims B = viewport().boundsInWindow(Points.of(_ell.getX() + _ell.getWidth(), _ell.getY() + _ell.getHeight()));

		return Ellipses.of(A.getFirst(), A.getSecond(), B.getFirst() - A.getFirst(), B.getSecond() - A.getSecond());
	}
	public Polygon2D 			mod2win(Polygon2D _poly) {
		Set<Coordinate.TwoDims> tops = _poly.getTops().stream().map(viewport()::boundsInWindow).collect(Collectors.toSet());
		return Polygons.of(tops);
	}
	public Line2D 				mod2win(Line2D _l) {
		Point2D beg = Points.of( viewport().boundsInWindow(_l.getPoint()) );
		return Lines.of(beg, _l.getDirection());
	}
	public Polyline2D 			mod2win(Polyline2D _pl) {
		Set<Coordinate.TwoDims> tops = _pl.getPoints().stream().map(viewport()::boundsInWindow).collect(Collectors.toSet());
		return Polylines.of(tops);
	}

	@Override
	public void 			drawPoint(double _x, double _y) {
		double[] pt = mod2win(_x, _y);
		ViewportDrawerFx.drawPoint(gc(), pt[0], pt[1]);
	}
	@Override
	public void 			drawPoint(double _x, double _y, double _strokeWidth) {
		double[] pt = mod2win(_x, _y);
		ViewportDrawerFx.drawPoint(gc(), pt[0], pt[1], _strokeWidth);
	}
	@Override
	public void 			drawPoint(double _x, double _y, double _strokeWidth, Color _strokePaint) {
		double[] pt = mod2win(_x, _y);
		ViewportDrawerFx.drawPoint(gc(), pt[0], pt[1], _strokeWidth, _strokePaint);
	}
	@Override
	public void 			drawPoint(double _x, double _y, PointSkin _skin) {
		double[] pt = mod2win(_x, _y);
		ViewportDrawerFx.drawPoint(gc(), pt[0], pt[1], _skin);
	}
	@Override
	public void 			drawPoint(double _x, double _y, PointSkin _skin, double _ptSize) {
		double[] pt = mod2win(_x, _y);
		ViewportDrawerFx.drawPoint(gc(), pt[0], pt[1], _skin, _ptSize);
	}
	@Override
	public void 			drawPoint(double _x, double _y, PointSkin _skin, double _ptSize, double _strokeWidth, Color _strokePaint) {
		double[] pt = mod2win(_x, _y);
		ViewportDrawerFx.drawPoint(gc(), pt[0], pt[1], _skin, _ptSize, _strokeWidth, _strokePaint);
	}
	@Override
	public void 			drawPoint(double _x, double _y, PointSkin _skin, double _ptSize, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		double[] pt = mod2win(_x, _y);
		ViewportDrawerFx.drawPoint(gc(), pt[0], pt[1], _skin, _ptSize, _strokeWidth, _strokePaint, _fillPaint);
	}

	@Override
	public void 			drawPoints(double[] _x, double[] _y) {
		double[][] pts = mod2win(_x, _y);
		ViewportDrawerFx.drawPoints(gc(), pts[0], pts[1]);
	}
	@Override
	public void 			drawPoints(double[] _x, double[] _y, double _strokeWidth) {
		double[][] pts = mod2win(_x, _y);
		ViewportDrawerFx.drawPoints(gc(), pts[0], pts[1], _strokeWidth);
	}
	@Override
	public void 			drawPoints(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
		double[][] pts = mod2win(_x, _y);
		ViewportDrawerFx.drawPoints(gc(), pts[0], pts[1], _strokeWidth, _strokePaint);
	}

	@Override
	public void 			drawSegment(double _x0, double _y0, double _x1, double _y1) {
		double[] pt0 = mod2win(_x0, _y0);
		double[] pt1 = mod2win(_x1, _y1);
		ViewportDrawerFx.drawSegment(gc(), pt0[0], pt0[1], pt1[0], pt1[1]);
	}
	@Override
	public void 			drawSegment(double _x0, double _y0, double _x1, double _y1, double _strokeWidth) {
		double[] pt0 = mod2win(_x0, _y0);
		double[] pt1 = mod2win(_x1, _y1);
		ViewportDrawerFx.drawSegment(gc(), pt0[0], pt0[1], pt1[0], pt1[1], _strokeWidth);
	}
	@Override
	public void 			drawSegment(double _x0, double _y0, double _x1, double _y1, double _strokeWidth, Color _strokePaint) {
		double[] pt0 = mod2win(_x0, _y0);
		double[] pt1 = mod2win(_x1, _y1);
		ViewportDrawerFx.drawSegment(gc(), pt0[0], pt0[1], pt1[0], pt1[1], _strokeWidth, _strokePaint);
	}
	@Override
	public void 			drawSegment(double _x0, double _y0, double _x1, double _y1, LineStyle _style) {
		double[] pt0 = mod2win(_x0, _y0);
		double[] pt1 = mod2win(_x1, _y1);
		ViewportDrawerFx.drawSegment(gc(), pt0[0], pt0[1], pt1[0], pt1[1], _style);
	}

	@Override
	public void 			drawSegments(double[] _x, double[] _y) {
		double[][] pts = mod2win(_x, _y);
		ViewportDrawerFx.drawSegments(gc(), pts[0], pts[1]);
	}
	@Override
	public void 			drawSegments(double[] _x, double[] _y, double _strokeWidth) {
		double[][] pts = mod2win(_x, _y);
		ViewportDrawerFx.drawSegments(gc(), pts[0], pts[1], _strokeWidth);
	}
	@Override
	public void 			drawSegments(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
		double[][] pts = mod2win(_x, _y);
		ViewportDrawerFx.drawSegments(gc(), pts[0], pts[1], _strokeWidth, _strokePaint);
	}
	@Override
	public void 			drawSegments(double[] _x, double[] _y, LineStyle _style) {
		double[][] pts = mod2win(_x, _y);
		drawSegments(gc(), pts[0], pts[1], _style);
	}

	@Override
	public void 			drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2) {
		double[] pt0 = mod2win(_x0, _y0);
		double[] pt1 = mod2win(_x1, _y1);
		double[] pt2 = mod2win(_x2, _y2);
		ViewportDrawerFx.drawTriangle(gc(), pt0[0], pt0[1], pt1[0], pt1[1], pt2[0], pt2[1]);
	}
	@Override
	public void 			drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, Color _fillPaint) {
		double[] pt0 = mod2win(_x0, _y0);
		double[] pt1 = mod2win(_x1, _y1);
		double[] pt2 = mod2win(_x2, _y2);
		ViewportDrawerFx.drawTriangle(gc(), pt0[0], pt0[1], pt1[0], pt1[1], pt2[0], pt2[1], _fillPaint);
	}
	@Override
	public void 			drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth) {
		double[] pt0 = mod2win(_x0, _y0);
		double[] pt1 = mod2win(_x1, _y1);
		double[] pt2 = mod2win(_x2, _y2);
		ViewportDrawerFx.drawTriangle(gc(), pt0[0], pt0[1], pt1[0], pt1[1], pt2[0], pt2[1], _strokeWidth);
	}
	@Override
	public void 			drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth, Color _strokePaint) {
		double[] pt0 = mod2win(_x0, _y0);
		double[] pt1 = mod2win(_x1, _y1);
		double[] pt2 = mod2win(_x2, _y2);
		ViewportDrawerFx.drawTriangle(gc(), pt0[0], pt0[1], pt1[0], pt1[1], pt2[0], pt2[1], _strokeWidth, _strokePaint);
	}
	@Override
	public void 			drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		double[] pt0 = mod2win(_x0, _y0);
		double[] pt1 = mod2win(_x1, _y1);
		double[] pt2 = mod2win(_x2, _y2);
		ViewportDrawerFx.drawTriangle(gc(), pt0[0], pt0[1], pt1[0], pt1[1], pt2[0], pt2[1], _strokeWidth, _strokePaint, _fillPaint);
	}

	@Override
	public void 			drawTriangles(double[] _x, double[] _y) {
		double[][] pts = mod2win(_x, _y);
		ViewportDrawerFx.drawTriangles(gc(), pts[0], pts[1]);
	}
	@Override
	public void 			drawTriangles(double[] _x, double[] _y, Color _fillPaint) {
		double[][] pts = mod2win(_x, _y);
		ViewportDrawerFx.drawTriangles(gc(), pts[0], pts[1], _fillPaint);
	}
	@Override
	public void 			drawTriangles(double[] _x, double[] _y, double _strokeWidth) {
		double[][] pts = mod2win(_x, _y);
		ViewportDrawerFx.drawTriangles(gc(), pts[0], pts[1], _strokeWidth);
	}
	@Override
	public void 			drawTriangles(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
		double[][] pts = mod2win(_x, _y);
		ViewportDrawerFx.drawTriangles(gc(), pts[0], pts[1], _strokeWidth, _strokePaint);
	}
	@Override
	public void 			drawTriangles(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		double[][] pts = mod2win(_x, _y);
		ViewportDrawerFx.drawTriangles(gc(), pts[0], pts[1], _strokeWidth, _strokePaint, _fillPaint);
	}

	@Override
	public void 			drawRectangle(double _x, double _y, double _w, double _h) {
		double[] pt0 = mod2win(_x, _y);
		double[] pt1 = mod2win(_x+_w, _y+_h);
		ViewportDrawerFx.drawRectangle(gc(), pt0[0], pt0[1], pt1[0]-pt0[0], pt1[1]-pt0[1]);
	}
	@Override
	public void 			drawRectangle(double _x, double _y, double _w, double _h, Color _fillPaint) {
		double[] pt0 = mod2win(_x, _y);
		double[] pt1 = mod2win(_x+_w, _y+_h);
		ViewportDrawerFx.drawRectangle(gc(), pt0[0], pt0[1], pt1[0]-pt0[0], pt1[1]-pt0[1], _fillPaint);
	}
	@Override
	public void 			drawRectangle(double _x, double _y, double _w, double _h, double _strokeWidth) {
		double[] pt0 = mod2win(_x, _y);
		double[] pt1 = mod2win(_x+_w, _y+_h);
		ViewportDrawerFx.drawRectangle(gc(), pt0[0], pt0[1], pt1[0]-pt0[0], pt1[1]-pt0[1], _strokeWidth);
	}
	@Override
	public void 			drawRectangle(double _x, double _y, double _w, double _h, double _strokeWidth, Color _strokePaint) {
		double[] pt0 = mod2win(_x, _y);
		double[] pt1 = mod2win(_x+_w, _y+_h);
		ViewportDrawerFx.drawRectangle(gc(), pt0[0], pt0[1], pt1[0]-pt0[0], pt1[1]-pt0[1], _strokeWidth, _strokePaint);
	}
	@Override
	public void 			drawRectangle(double _x, double _y, double _w, double _h, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		double[] pt0 = mod2win(_x, _y);
		double[] pt1 = mod2win(_x+_w, _y+_h);
		ViewportDrawerFx.drawRectangle(gc(), pt0[0], pt0[1], pt1[0]-pt0[0], pt1[1]-pt0[1], _strokeWidth, _strokePaint, _fillPaint);
	}

	@Override
	public void 			drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h) {
		double[][] pt0 = mod2win(_x, _y);
		double[][] pt1 = mod2win(Arrays.plus(_x, _w), Arrays.plus(_y, _h));
		double[]   w   = Arrays.minus(pt1[0], pt0[0]);
		double[]   h   = Arrays.minus(pt1[1], pt0[1]);
		ViewportDrawerFx.drawRectangles(gc(), pt0[0], pt0[1], w, h);
	}
	@Override
	public void 			drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, Color _strokePaint) {
		double[][] pt0 = mod2win(_x, _y);
		double[][] pt1 = mod2win(Arrays.plus(_x, _w), Arrays.plus(_y, _h));
		double[]   w   = Arrays.minus(pt1[0], pt0[0]);
		double[]   h   = Arrays.minus(pt1[1], pt0[1]);
		ViewportDrawerFx.drawRectangles(gc(), pt0[0], pt0[1], w, h, _strokePaint);
	}
	@Override
	public void 			drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth) {
		double[][] pt0 = mod2win(_x, _y);
		double[][] pt1 = mod2win(Arrays.plus(_x, _w), Arrays.plus(_y, _h));
		double[]   w   = Arrays.minus(pt1[0], pt0[0]);
		double[]   h   = Arrays.minus(pt1[1], pt0[1]);
		ViewportDrawerFx.drawRectangles(gc(), pt0[0], pt0[1], w, h, _strokeWidth);
	}
	@Override
	public void 			drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint) {
		double[][] pt0 = mod2win(_x, _y);
		double[][] pt1 = mod2win(Arrays.plus(_x, _w), Arrays.plus(_y, _h));
		double[]   w   = Arrays.minus(pt1[0], pt0[0]);
		double[]   h   = Arrays.minus(pt1[1], pt0[1]);
		ViewportDrawerFx.drawRectangles(gc(), pt0[0], pt0[1], w, h, _strokeWidth, _strokePaint);
	}
	@Override
	public void 			drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		double[][] pt0 = mod2win(_x, _y);
		double[][] pt1 = mod2win(Arrays.plus(_x, _w), Arrays.plus(_y, _h));
		double[]   w   = Arrays.minus(pt1[0], pt0[0]);
		double[]   h   = Arrays.minus(pt1[1], pt0[1]);
		ViewportDrawerFx.drawRectangles(gc(), pt0[0], pt0[1], w, h, _strokeWidth, _strokePaint, _fillPaint);
	}


	@Override
	public void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, Drawer.ArcMode _mode) {
		ViewportDrawerFx.drawArc(gc(), _x, _y, _w, _h, _startAngle, _angle, _mode);
	}
	@Override
	public void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, Drawer.ArcMode _mode, Color _fillPaint) {
		ViewportDrawerFx.drawArc(gc(), _x, _y, _w, _h, _startAngle, _angle, _mode, _fillPaint);
	}
	@Override
	public void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, double _strokeWidth) {
		ViewportDrawerFx.drawArc(gc(), _x, _y, _w, _h, _startAngle, _angle, _mode, _strokeWidth);
	}
	@Override
	public void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, double _strokeWidth, Color _strokePaint) {
		ViewportDrawerFx.drawArc(gc(), _x, _y, _w, _h, _startAngle, _angle, _mode, _strokeWidth, _strokePaint);
	}
	@Override
	public void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		ViewportDrawerFx.drawArc(gc(), _x, _y, _w, _h, _startAngle, _angle, _mode, _strokeWidth, _strokePaint, _fillPaint);
	}

	
	@Override
	public void 			drawEllipse(double _x, double _y, double _w, double _h) {
		double[] pt0 = mod2win(_x, _y);
		double[] pt1 = mod2win(_x+_w, _y+_h);
		ViewportDrawerFx.drawEllipse(gc(), pt0[0], pt0[1], pt1[0]-pt0[0], pt1[1]-pt0[1]);
	}
	@Override
	public void 			drawEllipse(double _x, double _y, double _w, double _h, Color _fillPaint) {
		double[] pt0 = mod2win(_x, _y);
		double[] pt1 = mod2win(_x+_w, _y+_h);
		ViewportDrawerFx.drawEllipse(gc(), pt0[0], pt0[1], pt1[0]-pt0[0], pt1[1]-pt0[1], _fillPaint);
	}
	@Override
	public void 			drawEllipse(double _x, double _y, double _w, double _h, double _strokeWidth) {
		double[] pt0 = mod2win(_x, _y);
		double[] pt1 = mod2win(_x+_w, _y+_h);
		ViewportDrawerFx.drawEllipse(gc(), pt0[0], pt0[1], pt1[0]-pt0[0], pt1[1]-pt0[1], _strokeWidth);
	}
	@Override
	public void 			drawEllipse(double _x, double _y, double _w, double _h, double _strokeWidth, Color _strokePaint) {
		double[] pt0 = mod2win(_x, _y);
		double[] pt1 = mod2win(_x+_w, _y+_h);
		ViewportDrawerFx.drawEllipse(gc(), pt0[0], pt0[1], pt1[0]-pt0[0], pt1[1]-pt0[1], _strokeWidth, _strokePaint);
	}
	@Override
	public void 			drawEllipse(double _x, double _y, double _w, double _h, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		double[] pt0 = mod2win(_x, _y);
		double[] pt1 = mod2win(_x+_w, _y+_h);
		ViewportDrawerFx.drawEllipse(gc(), pt0[0], pt0[1], pt1[0]-pt0[0], pt1[1]-pt0[1], _strokeWidth, _strokePaint, _fillPaint);
	}


	@Override
	public void 			drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h) {
		double[][] pt0 = mod2win(_x, _y);
		double[][] pt1 = mod2win(Arrays.plus(_x, _w), Arrays.plus(_y, _h));
		double[]   w   = Arrays.minus(pt1[0], pt0[0]);
		double[]   h   = Arrays.minus(pt1[1], pt0[1]);
		ViewportDrawerFx.drawEllipses(gc(), pt0[0], pt0[1], w, h);
	}
	@Override
	public void 			drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h, Color _fillPaint) {
		double[][] pt0 = mod2win(_x, _y);
		double[][] pt1 = mod2win(Arrays.plus(_x, _w), Arrays.plus(_y, _h));
		double[]   w   = Arrays.minus(pt1[0], pt0[0]);
		double[]   h   = Arrays.minus(pt1[1], pt0[1]);
		ViewportDrawerFx.drawEllipses(gc(), pt0[0], pt0[1], w, h, _fillPaint);
	}
	@Override
	public void 			drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth) {
		double[][] pt0 = mod2win(_x, _y);
		double[][] pt1 = mod2win(Arrays.plus(_x, _w), Arrays.plus(_y, _h));
		double[]   w   = Arrays.minus(pt1[0], pt0[0]);
		double[]   h   = Arrays.minus(pt1[1], pt0[1]);
		ViewportDrawerFx.drawEllipses(gc(), pt0[0], pt0[1], w, h, _strokeWidth);
	}
	@Override
	public void 			drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint) {
		double[][] pt0 = mod2win(_x, _y);
		double[][] pt1 = mod2win(Arrays.plus(_x, _w), Arrays.plus(_y, _h));
		double[]   w   = Arrays.minus(pt1[0], pt0[0]);
		double[]   h   = Arrays.minus(pt1[1], pt0[1]);
		ViewportDrawerFx.drawEllipses(gc(), pt0[0], pt0[1], w, h, _strokeWidth, _strokePaint);
	}
	@Override
	public void 			drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		double[][] pt0 = mod2win(_x, _y);
		double[][] pt1 = mod2win(Arrays.plus(_x, _w), Arrays.plus(_y, _h));
		double[]   w   = Arrays.minus(pt1[0], pt0[0]);
		double[]   h   = Arrays.minus(pt1[1], pt0[1]);
		ViewportDrawerFx.drawEllipses(gc(), pt0[0], pt0[1], w, h, _strokeWidth, _strokePaint, _fillPaint);
	}


	@Override
	public void 			drawPolygon(double[] _x, double[] _y) {
		double[][] pts = mod2win(_x, _y);
		ViewportDrawerFx.drawPolygon(gc(), pts[0], pts[1]);
	}
	@Override
	public void 			drawPolygon(double[] _x, double[] _y, Color _fillPaint) {
		double[][] pts = mod2win(_x, _y);
		ViewportDrawerFx.drawPolygon(gc(), pts[0], pts[1], _fillPaint);
	}
	@Override
	public void 			drawPolygon(double[] _x, double[] _y, double _strokeWidth) {
		double[][] pts = mod2win(_x, _y);
		ViewportDrawerFx.drawPolygon(gc(), pts[0], pts[1], _strokeWidth);
	}
	@Override
	public void 			drawPolygon(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
		double[][] pts = mod2win(_x, _y);
		ViewportDrawerFx.drawPolygon(gc(), pts[0], pts[1], _strokeWidth, _strokePaint);
	}
	@Override
	public void 			drawPolygon(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		double[][] pts = mod2win(_x, _y);
		ViewportDrawerFx.drawPolygon(gc(), pts[0], pts[1], _strokeWidth, _strokePaint, _fillPaint);
	}


	@Override
	public void 			drawString(String string, double _x, double _y) {
		double[] pt = mod2win(_x, _y);
		ViewportDrawerFx.drawString(gc(), string, pt[0], pt[1]);
	}
	@Override
	public void 			drawString(String string, double _x, double _y, double _fontSize) {
		double[] pt = mod2win(_x, _y);
		ViewportDrawerFx.drawString(gc(), string, pt[0], pt[1], _fontSize);
	}
	@Override
	public void 			drawString(String string, double _x, double _y, String _fontName, double _fontSize) {
		double[] pt = mod2win(_x, _y);
		ViewportDrawerFx.drawString(gc(), string, pt[0], pt[1], _fontName, _fontSize);
	}
	@Override
	public void 			drawString(String string, double _x, double _y, Font _font) {
		double[] pt = mod2win(_x, _y);
		ViewportDrawerFx.drawString(gc(), string, pt[0], pt[1], _font);
	}


	@Override
	public void 			drawImage(Image _img, BoundingBox.TwoDims _in, BoundingBox.TwoDims _out) {
		throw new NotYetImplementedException();
	}

	@Override
	public void 			drawShape(Shape _shape) {
		throw new NotYetImplementedException();
	}
	@Override
	public void 			drawPath(Path _path) {
		throw new NotYetImplementedException();
	}

}
