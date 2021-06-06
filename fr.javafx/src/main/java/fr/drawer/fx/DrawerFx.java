package fr.drawer.fx;

import java.util.List;

import fr.java.draw.Drawer;
import fr.java.draw.styles.LineStyle;
import fr.java.draw.styles.PointSkin;
import fr.java.draw.tools.Color;
import fr.java.draw.tools.Colors;
import fr.java.draw.tools.Font;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Dimension.TwoDims;
import fr.java.math.geometry.plane.Point2D;
import fr.java.sdk.math.Dimensions;
import fr.java.sdk.math.Points;
import fr.java.sdk.math.geometry.plane.shapes.SimpleRectangle2D;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ArcType;
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
import javafx.scene.transform.Rotate;

public class DrawerFx implements Drawer4Fx {
	protected Canvas canvas;

	public DrawerFx() {
		super();
	}
	public DrawerFx(Canvas _canvas) {
		super();
		canvas = _canvas;
	}
	public DrawerFx(DrawerFx _drawer) {
		super();
		canvas = _drawer.canvas;
	}

	public void 		   setCanvas(Canvas _canvas) {
		if(_canvas == null)
			throw new NullPointerException();
		canvas = _canvas;
	}
	public Canvas 		   canvas() {
		return canvas;
	}

	public GraphicsContext gc() {
		return canvas.getGraphicsContext2D();
	}

	@Override
	public TwoDims getTextDimension(String _text, Font _font) {
		javafx.scene.text.Font font = null;
		if(_font.getName().isPresent())
			font = new javafx.scene.text.Font(_font.getName().get(), _font.getSize());
		else
			font = new javafx.scene.text.Font(_font.getSize());

	    Text text = new Text(_text);
	    text.setFont(font);
	    Bounds tb = text.getBoundsInLocal();
	    Rectangle stencil = new Rectangle( tb.getMinX(), tb.getMinY(), tb.getWidth(), tb.getHeight() );

	    Shape intersection = Shape.intersect(text, stencil);

	    Bounds ib = intersection.getBoundsInLocal();
	    
	    return Dimensions.of(ib.getWidth(), ib.getHeight());
	}

	@Override
	public void setDrawStyle(String _cssStyle) {
		DrawerFx.setDrawStyle(gc(), _cssStyle);
	}

	@Override
	public void setLineWidth(double _width) {
		gc().setLineWidth(_width);
	}
	
	@Override
	public void setFill		(Color _color) { gc().setFill(Drawer4Fx.fxColor(_color)); }
	@Override
	public void setStroke	(Color _color) { gc().setStroke(Drawer4Fx.fxColor(_color)); }

	@Override
	public void clear(Color _c) {
		DrawerFx.clear(gc(), _c);
	}

	@Override
	public void drawPoint(double _x, double _y) {
		DrawerFx.drawPoint(gc(), _x, _y);
	}
	@Override
	public void drawPoint(double _x, double _y, double _strokeWidth) {
		DrawerFx.drawPoint(gc(), _x, _y, _strokeWidth);
	}
	@Override
	public void drawPoint(double _x, double _y, double _strokeWidth, Color _strokePaint) {
		DrawerFx.drawPoint(gc(), _x, _y, _strokeWidth, _strokePaint);
	}
	@Override
	public void drawPoint(double _x, double _y, PointSkin _skin) {
		DrawerFx.drawPoint(gc(), _x, _y, _skin);
	}
	@Override
	public void drawPoint(double _x, double _y, PointSkin _skin, double _ptSize) {
		DrawerFx.drawPoint(gc(), _x, _y, _skin, _ptSize);
	}
	@Override
	public void drawPoint(double _x, double _y, PointSkin _skin, double _ptSize, double _strokeWidth, Color _strokePaint) {
		DrawerFx.drawPoint(gc(), _x, _y, _skin, _ptSize, _strokeWidth, _strokePaint);
	}
	@Override
	public void drawPoint(double _x, double _y, PointSkin _skin, double _ptSize, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		DrawerFx.drawPoint(gc(), _x, _y, _skin, _ptSize, _strokeWidth, _strokePaint, _fillPaint);
	}

	@Override
	public void drawPoints(double[] _x, double[] _y) {
		DrawerFx.drawPoints(gc(), _x, _y);
	}
	@Override
	public void drawPoints(double[] _x, double[] _y, double _strokeWidth) {
		DrawerFx.drawPoints(gc(), _x, _y, _strokeWidth);
	}
	@Override
	public void drawPoints(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
		DrawerFx.drawPoints(gc(), _x, _y, _strokeWidth, _strokePaint);
	}

	@Override
	public void drawSegment(double _x0, double _y0, double _x1, double _y1) {
		DrawerFx.drawSegment(gc(), _x0, _y0, _x1, _y1);
	}
	@Override
	public void drawSegment(double _x0, double _y0, double _x1, double _y1, double _strokeWidth) {
		DrawerFx.drawSegment(gc(), _x0, _y0, _x1, _y1, _strokeWidth);
	}
	@Override
	public void drawSegment(double _x0, double _y0, double _x1, double _y1, double _strokeWidth, Color _strokePaint) {
		DrawerFx.drawSegment(gc(), _x0, _y0, _x1, _y1, _strokeWidth, _strokePaint);
	}
	@Override
	public void drawSegment(double _x0, double _y0, double _x1, double _y1, LineStyle _style) {
		DrawerFx.drawSegment(gc(), _x0, _y0, _x1, _y1, _style);
	}

	@Override
	public void drawSegments(double[] _x, double[] _y) {
		DrawerFx.drawSegments(gc(), _x, _y);
	}
	@Override
	public void drawSegments(double[] _x, double[] _y, double _strokeWidth) {
		DrawerFx.drawSegments(gc(), _x, _y, _strokeWidth);
	}
	@Override
	public void drawSegments(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
		DrawerFx.drawSegments(gc(), _x, _y, _strokeWidth, _strokePaint);
	}
	@Override
	public void drawSegments(double[] _x, double[] _y, LineStyle _style) {
		drawSegments(gc(), _x, _y, _style);
	}

	@Override
	public void drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2) {
		DrawerFx.drawTriangle(gc(), _x0, _y0, _x1, _y1, _x2, _y2);
	}
	@Override
	public void drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, Color _fillPaint) {
		DrawerFx.drawTriangle(gc(), _x0, _y0, _x1, _y1, _x2, _y2, _fillPaint);
	}
	@Override
	public void drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth) {
		DrawerFx.drawTriangle(gc(), _x0, _y0, _x1, _y1, _x2, _y2, _strokeWidth);
	}
	@Override
	public void drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth, Color _strokePaint) {
		DrawerFx.drawTriangle(gc(), _x0, _y0, _x1, _y1, _x2, _y2, _strokeWidth, _strokePaint);
	}
	@Override
	public void drawTriangle(double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		DrawerFx.drawTriangle(gc(), _x0, _y0, _x1, _y1, _x2, _y2, _strokeWidth, _strokePaint, _fillPaint);
	}

	@Override
	public void drawTriangles(double[] _x, double[] _y) {
		DrawerFx.drawTriangles(gc(), _x, _y);
	}
	@Override
	public void drawTriangles(double[] _x, double[] _y, Color _fillPaint) {
		DrawerFx.drawTriangles(gc(), _x, _y, _fillPaint);
	}
	@Override
	public void drawTriangles(double[] _x, double[] _y, double _strokeWidth) {
		DrawerFx.drawTriangles(gc(), _x, _y, _strokeWidth);
	}
	@Override
	public void drawTriangles(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
		DrawerFx.drawTriangles(gc(), _x, _y, _strokeWidth, _strokePaint);
	}
	@Override
	public void drawTriangles(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		DrawerFx.drawTriangles(gc(), _x, _y, _strokeWidth, _strokePaint, _fillPaint);
	}

	@Override
	public void drawRectangle(double _x, double _y, double _w, double _h) {
		DrawerFx.drawRectangle(gc(), _x, _y, _w, _h);
	}
	@Override
	public void drawRectangle(double _x, double _y, double _w, double _h, Color _fillPaint) {
		DrawerFx.drawRectangle(gc(), _x, _y, _w, _h, _fillPaint);
	}
	@Override
	public void drawRectangle(double _x, double _y, double _w, double _h, double _strokeWidth) {
		DrawerFx.drawRectangle(gc(), _x, _y, _w, _h, _strokeWidth);
	}
	@Override
	public void drawRectangle(double _x, double _y, double _w, double _h, double _strokeWidth, Color _strokePaint) {
		DrawerFx.drawRectangle(gc(), _x, _y, _w, _h, _strokeWidth, _strokePaint);
	}
	@Override
	public void drawRectangle(double _x, double _y, double _w, double _h, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		DrawerFx.drawRectangle(gc(), _x, _y, _w, _h, _strokeWidth, _strokePaint, _fillPaint);
	}

	@Override
	public void drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h) {
		DrawerFx.drawRectangles(gc(), _x, _y, _w, _h);
	}
	@Override
	public void drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, Color _strokePaint) {
		DrawerFx.drawRectangles(gc(), _x, _y, _w, _h, _strokePaint);
	}
	@Override
	public void drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth) {
		DrawerFx.drawRectangles(gc(), _x, _y, _w, _h, _strokeWidth);
	}
	@Override
	public void drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint) {
		DrawerFx.drawRectangles(gc(), _x, _y, _w, _h, _strokeWidth, _strokePaint);
	}
	@Override
	public void drawRectangles(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		DrawerFx.drawRectangles(gc(), _x, _y, _w, _h, _strokeWidth, _strokePaint, _fillPaint);
	}

	@Override
	public void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, Drawer.ArcMode _mode) {
		DrawerFx.drawArc(gc(), _x, _y, _w, _h, _startAngle, _angle, _mode);
	}
	@Override
	public void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, Drawer.ArcMode _mode, Color _fillPaint) {
		DrawerFx.drawArc(gc(), _x, _y, _w, _h, _startAngle, _angle, _mode, _fillPaint);
	}
	@Override
	public void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, double _strokeWidth) {
		DrawerFx.drawArc(gc(), _x, _y, _w, _h, _startAngle, _angle, _mode, _strokeWidth);
	}
	@Override
	public void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, double _strokeWidth, Color _strokePaint) {
		DrawerFx.drawArc(gc(), _x, _y, _w, _h, _startAngle, _angle, _mode, _strokeWidth, _strokePaint);
	}
	@Override
	public void drawArc(double _x, double _y, double _w, double _h, double _startAngle, double _angle, ArcMode _mode, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		DrawerFx.drawArc(gc(), _x, _y, _w, _h, _startAngle, _angle, _mode, _strokeWidth, _strokePaint, _fillPaint);
	}
	
	@Override
	public void drawEllipse(double _x, double _y, double _w, double _h) {
		DrawerFx.drawEllipse(gc(), _x, _y, _w, _h);
	}
	@Override
	public void drawEllipse(double _x, double _y, double _w, double _h, Color _fillPaint) {
		DrawerFx.drawEllipse(gc(), _x, _y, _w, _h, _fillPaint);
	}
	@Override
	public void drawEllipse(double _x, double _y, double _w, double _h, double _strokeWidth) {
		DrawerFx.drawEllipse(gc(), _x, _y, _w, _h, _strokeWidth);
	}
	@Override
	public void drawEllipse(double _x, double _y, double _w, double _h, double _strokeWidth, Color _strokePaint) {
		DrawerFx.drawEllipse(gc(), _x, _y, _w, _h, _strokeWidth, _strokePaint);
	}
	@Override
	public void drawEllipse(double _x, double _y, double _w, double _h, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		DrawerFx.drawEllipse(gc(), _x, _y, _w, _h, _strokeWidth, _strokePaint, _fillPaint);
	}

	@Override
	public void drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h) {
		DrawerFx.drawEllipses(gc(), _x, _y, _w, _h);
	}
	@Override
	public void drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h, Color _fillPaint) {
		DrawerFx.drawEllipses(gc(), _x, _y, _w, _h, _fillPaint);
	}
	@Override
	public void drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth) {
		DrawerFx.drawEllipses(gc(), _x, _y, _w, _h, _strokeWidth);
	}
	@Override
	public void drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint) {
		DrawerFx.drawEllipses(gc(), _x, _y, _w, _h, _strokeWidth, _strokePaint);
	}
	@Override
	public void drawEllipses(double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		DrawerFx.drawEllipses(gc(), _x, _y, _w, _h, _strokeWidth, _strokePaint, _fillPaint);
	}

	@Override
	public void drawPolygon(double[] _x, double[] _y) {
		DrawerFx.drawPolygon(gc(), _x, _y);
	}
	@Override
	public void drawPolygon(double[] _x, double[] _y, Color _fillPaint) {
		DrawerFx.drawPolygon(gc(), _x, _y, _fillPaint);
	}
	@Override
	public void drawPolygon(double[] _x, double[] _y, double _strokeWidth) {
		DrawerFx.drawPolygon(gc(), _x, _y, _strokeWidth);
	}
	@Override
	public void drawPolygon(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
		DrawerFx.drawPolygon(gc(), _x, _y, _strokeWidth, _strokePaint);
	}
	@Override
	public void drawPolygon(double[] _x, double[] _y, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		DrawerFx.drawPolygon(gc(), _x, _y, _strokeWidth, _strokePaint, _fillPaint);
	}

	@Override
	public void drawString(String string, double _x, double _y) {
		DrawerFx.drawString(gc(), string, _x, _y);
	}
	@Override
	public void drawString(String string, double _x, double _y, double _fontSize) {
		DrawerFx.drawString(gc(), string, _x, _y, _fontSize);
	}
	@Override
	public void drawString(String string, double _x, double _y, String _fontName, double _fontSize) {
		DrawerFx.drawString(gc(), string, _x, _y, _fontName, _fontSize);
	}
	@Override
	public void drawString(String string, double _x, double _y, Font _font) {
		DrawerFx.drawString(gc(), string, _x, _y, _font);
	}
	
	@Override
	public void drawImage(Image _img, double _x, double _y) {
		DrawerFx.drawImage(gc(), _img, _x, _y);
	}
	@Override
	public void drawImage(Image _img, double _x, double _y, double _angle) {
		DrawerFx.drawImage(gc(), _img, _x, _y, _angle);
	}

	@Override
	public void drawImage(Image _img, BoundingBox.TwoDims _in, BoundingBox.TwoDims _out) {
		DrawerFx.drawImage(gc(), _img, _in, _out);
	}

	@Override
	public void drawShape(Shape _shape) {
		DrawerFx.drawShape(gc(), _shape);
	}
	@Override
	public void drawPath(Path _path) {
		DrawerFx.drawPath(gc(), _path);
	}

	//                                                               \\
	// ############################################################# \\
	//                                                               \\
	
	static void paint(Canvas _canvas, List<Shape> _shapes) {
		GraphicsContext gc = _canvas.getGraphicsContext2D();

		for(Shape s : _shapes) {
			gc.setStroke(s.getStroke());
			gc.setLineWidth(s.getStrokeWidth());

			drawShape(gc, s);
		}
	}

	public static void clear(GraphicsContext _gc, javafx.scene.paint.Color _c) {
		_gc.setTransform(1, 0, 0, 1, 0, 0);
		_gc.setFill(_c);
		_gc.clearRect(0, 0, _gc.getCanvas().getWidth(), _gc.getCanvas().getHeight());
		_gc.fillRect(0, 0, _gc.getCanvas().getWidth(), _gc.getCanvas().getHeight());
	}
	public static void clear(GraphicsContext _gc, Color _c) {
		_gc.setTransform(1, 0, 0, 1, 0, 0);
		_gc.setFill(Drawer4Fx.fxColor(_c));
		_gc.clearRect(0, 0, _gc.getCanvas().getWidth(), _gc.getCanvas().getHeight());
		_gc.fillRect(0, 0, _gc.getCanvas().getWidth(), _gc.getCanvas().getHeight());
	}

	static void setDrawStyle(GraphicsContext _gc, Double _strokeWidth, Paint _strokePaint, Paint _fillPaint) {
		if(_strokeWidth != null)
			_gc.setLineWidth	(_strokeWidth);
		if(_strokePaint != null)
			_gc.setStroke		(_strokePaint);
		if(_fillPaint != null)
			_gc.setFill			(_fillPaint);
	}
	static void setDrawStyle(GraphicsContext _gc, String string) {
		String[] args = string.split(";|\n");
		
		for(String arg : args) {
			String[] style  = arg.split(":");

			switch(style[0].trim()){
			case "-fx-fill"          : _gc.setFill(javafx.scene.paint.Color.web(style[1].trim())); break;
			case "-fx-stroke"        : _gc.setStroke(javafx.scene.paint.Color.web(style[1].trim())); break;
			case "-fx-stroke-width:" : _gc.setLineWidth(Double.parseDouble(style[1].trim())); break;
			}
		}
	}
	static void resetDrawStyle(GraphicsContext _gc) {
		_gc.setLineWidth	(1.0);
		_gc.setStroke		(Drawer4Fx.fxColor(Colors.BLACK));
		_gc.setFill			(Drawer4Fx.fxColor(Colors.BLACK));
	}
	
	public static TwoDims getTextDimensions(String _text, Font _font) {
		javafx.scene.text.Font font = null;
		if(_font.getName().isPresent())
			font = new javafx.scene.text.Font(_font.getName().get(), _font.getSize());
		else
			font = new javafx.scene.text.Font(_font.getSize());

	    Text text = new Text(_text);
	    text.setFont(font);
	    Bounds tb = text.getBoundsInLocal();
	    Rectangle stencil = new Rectangle( tb.getMinX(), tb.getMinY(), tb.getWidth(), tb.getHeight() );

	    Shape intersection = Shape.intersect(text, stencil);

	    Bounds ib = intersection.getBoundsInLocal();
	    
	    return Dimensions.of(ib.getWidth(), ib.getHeight());
	}

	// Primitives - 1D
	static void drawPoint(GraphicsContext _gc, double _x, double _y) {
		_gc.fillOval(_x, _y, 1d, 1d);
	}
	static void drawPoint(GraphicsContext _gc, double _x, double _y, double _strokeWidth) {
		_gc.fillOval(_x, _y, _strokeWidth, _strokeWidth);
	}
	static void drawPoint(GraphicsContext _gc, double _x, double _y, double _strokeWidth, Color _strokePaint) {
		drawPoint(_gc, _x, _y, _strokeWidth, Drawer4Fx.fxColor(_strokePaint));
	}
	static void drawPoint(GraphicsContext _gc, double _x, double _y, double _strokeWidth, Paint _strokePaint) {
		Paint oldStroke = _gc.getStroke();
		Paint oldFill   = _gc.getFill();

		_gc.setStroke(_strokePaint);
		_gc.setFill(_strokePaint);
		_gc.fillOval(_x, _y, _strokeWidth, _strokeWidth);
		_gc.setStroke(oldStroke);
		_gc.setFill(oldFill);
	}
	static void drawPoint(GraphicsContext _gc, double _x, double _y, PointSkin _skin) {
		drawPoint(_gc, _x, _y, _skin, 1d, 1d, null, null);
	}
	static void drawPoint(GraphicsContext _gc, double _x, double _y, PointSkin _skin, double _ptSize) {
		drawPoint(_gc, _x, _y, _skin, _ptSize, 1d, null, null);
	}
	static void drawPoint(GraphicsContext _gc, double _x, double _y, PointSkin _skin, double _ptSize, double _strokeWidth, Color _strokePaint) {
		drawPoint(_gc, _x, _y, _skin, _ptSize, _strokeWidth, _strokePaint, null);
	}
	static void drawPoint(GraphicsContext _gc, double _x, double _y, PointSkin _skin, double _ptSize, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		double oldWidth  = _gc.getLineWidth();
		Paint  oldStroke = _gc.getStroke();
		Paint  oldFill   = _gc.getFill();

		if(_strokeWidth != 0d)
			_gc.setLineWidth(_strokeWidth);
		if(_strokePaint != null)
			_gc.setStroke(Drawer4Fx.fxColor(_strokePaint));
		if(_fillPaint != null)
			_gc.setFill(Drawer4Fx.fxColor(_fillPaint));
		
		// Diamond
		double[] x = new double[] { _x - _ptSize/2d, _x, _x + _ptSize/2d, _x };
		double[] y = new double[] { _y, _y + _ptSize/2d, _y, _y - _ptSize/2d };

		switch(_skin) {
		case Invisible     :	return ;
		default            :
		case Dot           :	_gc.setFill(Drawer4Fx.fxColor(_strokePaint)); _gc.fillOval(_x, _y, _ptSize, _ptSize); break;
		case Plus          :	_gc.strokeLine(_x - _ptSize/2d, _y,              _x + _ptSize/2d, _y);
						 		_gc.strokeLine(             _x, _y - _ptSize/2d, _x,              _y + _ptSize/2d);
						 		break;

		case Cross         : 	_gc.strokeLine(_x - _ptSize/2d, _y - _ptSize/2d, _x + _ptSize/2d, _y + _ptSize/2d);
						 		_gc.strokeLine(_x - _ptSize/2d, _y + _ptSize/2d, _x + _ptSize/2d, _y - _ptSize/2d);
						 		break;

		case Star          : 	_gc.strokeLine(_x - _ptSize/2d, _y,              _x + _ptSize/2d, _y);
						 		_gc.strokeLine(             _x, _y - _ptSize/2d, _x,              _y + _ptSize/2d);
						 		_gc.strokeLine(_x - _ptSize/2d, _y - _ptSize/2d, _x + _ptSize/2d, _y + _ptSize/2d);
						 		_gc.strokeLine(_x - _ptSize/2d, _y + _ptSize/2d, _x + _ptSize/2d, _y - _ptSize/2d);
						 		break;

		case FilledCircle  :	_gc.fillOval(_x, _y, _ptSize, _ptSize);
		case OpenedCircle  :	_gc.strokeOval(_x, _y, _ptSize, _ptSize);
								break;

		case FilledDiamond :	_gc.fillPolygon(x, y, 4);
		case OpenedDiamond :	_gc.strokePolygon(x, y, 4);
								break;

		case FilledSquare  :	_gc.fillRect(_x - _ptSize/2d, _y - _ptSize/2d, _ptSize, _ptSize);
		case OpenedSquare  :	_gc.strokeRect(_x - _ptSize/2d, _y - _ptSize/2d, _ptSize, _ptSize);
								break;
		}

		_gc.setFill(oldFill);
		_gc.setStroke(oldStroke);
		_gc.setLineWidth(oldWidth);
	}

	static void drawPoints(GraphicsContext _gc, double[] _x, double[] _y) {
		assert(_x.length == _y.length);

		for(int i = 0; i < _x.length; ++i)
			_gc.fillOval(_x[i], _y[i], 1d, 1d);
	}
	static void drawPoints(GraphicsContext _gc, double[] _x, double[] _y, double _strokeWidth) {
		assert(_x.length == _y.length);

		for(int i = 0; i < _x.length; ++i)
			_gc.fillOval(_x[i], _y[i], _strokeWidth, _strokeWidth);
	}
	static void drawPoints(GraphicsContext _gc, double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
		drawPoints(_gc, _x, _y, _strokeWidth, Drawer4Fx.fxColor(_strokePaint));
	}
	static void drawPoints(GraphicsContext _gc, double[] _x, double[] _y, double _strokeWidth, Paint _strokePaint) {
		assert(_x.length == _y.length);

		Paint oldStroke = _gc.getStroke();
		Paint oldFill   = _gc.getFill();

		_gc.setStroke(_strokePaint);
		_gc.setFill(_strokePaint);
		for(int i = 0; i < _x.length; ++i)
			_gc.fillOval(_x[i], _y[i], _strokeWidth, _strokeWidth);
		_gc.setStroke(oldStroke);
		_gc.setFill(oldFill);
	}


	static void drawSegment(GraphicsContext _gc, double _x0, double _y0, double _x1, double _y1) {
		_gc.strokeLine(_x0, _y0, _x1, _y1);
	}
	static void drawSegment(GraphicsContext _gc, double _x0, double _y0, double _x1, double _y1, double _strokeWidth) {
		double old = _gc.getLineWidth();

		_gc.setLineWidth(_strokeWidth);
		_gc.strokeLine(_x0, _y0, _x1, _y1);
		_gc.setLineWidth(old);
	}
	static void drawSegment(GraphicsContext _gc, double _x0, double _y0, double _x1, double _y1, double _strokeWidth, Color _strokePaint) {
		drawSegment(_gc, _x0, _y0, _x1, _y1, _strokeWidth, Drawer4Fx.fxColor(_strokePaint));
	}
	static void drawSegment(GraphicsContext _gc, double _x0, double _y0, double _x1, double _y1, double _strokeWidth, Paint _strokePaint) {////
		double   oldWidth  = _gc.getLineWidth();
		Paint    oldStroke = _gc.getStroke();

		if(_strokeWidth != 0d)
			_gc.setLineWidth(_strokeWidth);
		_gc.setStroke(_strokePaint);

//		System.out.println(oldWidth + " vs " + _strokeWidth + "\t" + oldStroke + " vs " + _strokePaint + "\t\t" + _gc.getLineWidth() + " & " + _gc.getStroke());
		
		_gc.strokeLine(_x0, _y0, _x1, _y1);

		_gc.setStroke(oldStroke);
		_gc.setLineWidth(oldWidth);
	}
	static void drawSegment(GraphicsContext _gc, double _x0, double _y0, double _x1, double _y1, LineStyle _style) {
		double   oldWidth  = _gc.getLineWidth();
		Paint    oldStroke = _gc.getStroke();
		double[] oldDashes = _gc.getLineDashes();

		_gc.setLineWidth(_style.getWidth());
		_gc.setStroke(Drawer4Fx.fxColor(_style.getPen().toColor()));
		if(_style.hasDashPattern())
			_gc.setLineDashes(_style.getDashPattern());

		_gc.strokeLine(_x0, _y0, _x1, _y1);

		_gc.setStroke(oldStroke);
		_gc.setLineWidth(oldWidth);
		_gc.setLineDashes(oldDashes);
	}


	static void drawSegments(GraphicsContext _gc, double[] _x, double[] _y) {
		assert(_x.length == _y.length);

//		for(int i = 0; i < _x.length - 1; ++i)
//			_gc.strokeLine(_x[i], _y[i], _x[i+1], _y[i+1]);
		_gc.strokePolyline(_x, _y, _x.length);
	}
	static void drawSegments(GraphicsContext _gc, double[] _x, double[] _y, double _strokeWidth) {
		assert(_x.length == _y.length);

		double old = _gc.getLineWidth();

		_gc.setLineWidth(_strokeWidth);
//		for(int i = 0; i < _x.length - 1; ++i)
//			_gc.strokeLine(_x[i], _y[i], _x[i+1], _y[i+1]);
		_gc.strokePolyline(_x, _y, _x.length);
		_gc.setLineWidth(old);
	}
	static void drawSegments(GraphicsContext _gc, double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
		drawSegments(_gc, _x, _y, _strokeWidth, Drawer4Fx.fxColor(_strokePaint));
	}
	static void drawSegments(GraphicsContext _gc, double[] _x, double[] _y, double _strokeWidth, Paint _strokePaint) {
		assert(_x.length == _y.length);

		double oldWidth  = _gc.getLineWidth();
		Paint  oldStroke = _gc.getStroke();

		_gc.setLineWidth(_strokeWidth);
		_gc.setStroke(_strokePaint);
//		for(int i = 0; i < _x.length - 1; ++i)
//			_gc.strokeLine(_x[i], _y[i], _x[i+1], _y[i+1]);
		_gc.strokePolyline(_x, _y, _x.length);
		_gc.setStroke(oldStroke);
		_gc.setLineWidth(oldWidth);
	}
	static void drawSegments(GraphicsContext _gc, double[] _x, double[] _y, LineStyle _style) {
		assert(_x.length == _y.length);

		double   oldWidth  = _gc.getLineWidth();
		double[] oldDashes = _gc.getLineDashes();
		Paint    oldStroke = _gc.getStroke();
		Paint    oldFill   = _gc.getFill();

		if(_style.getWidth() != 0d)
			_gc.setLineWidth(_style.getWidth());

		if(_style.getDashPattern() != null)
			_gc.setLineDashes(_style.getDashPattern());

		_gc.setStroke(Drawer4Fx.fxColor(_style.getBrush().getPaint().toColor()));

//		for(int i = 0; i < _x.length - 1; ++i)
//			_gc.strokeLine(_x[i], _y[i], _x[i+1], _y[i+1]);
		
		_gc.strokePolyline(_x, _y, _x.length);

		_gc.setFill(oldFill);
		_gc.setStroke(oldStroke);
		_gc.setLineDashes(oldDashes);
		_gc.setLineWidth(oldWidth);
	}


	// Primitives - 2D
	static void drawTriangle(GraphicsContext _gc, double _x0, double _y0, double _x1, double _y1, double _x2, double _y2) {
		_gc.strokePolyline(new double[] { _x0, _x1, _x2 }, new double[] { _y0, _y1, _y2 }, 3);
	}
	static void drawTriangle(GraphicsContext _gc, double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, Color _fillPaint) {
		drawTriangle(_gc, _x0, _y0, _x1, _y1, _x2, _y2, Drawer4Fx.fxColor(_fillPaint));
	}
	static void drawTriangle(GraphicsContext _gc, double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, Paint _fillPaint) {
		Paint  oldFill   = _gc.getFill();

		if(_fillPaint != null)
			_gc.setFill(_fillPaint);

		_gc.fillPolygon(new double[] { _x0, _x1, _x2 }, new double[] { _y0, _y1, _y2 }, 3);

		if(_fillPaint != null)
			_gc.setFill(oldFill);
	}
	static void drawTriangle(GraphicsContext _gc, double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth) {
		double oldWidth  = _gc.getLineWidth();

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);

		_gc.strokePolyline(new double[] { _x0, _x1, _x2 }, new double[] { _y0, _y1, _y2 }, 3);

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}
	static void drawTriangle(GraphicsContext _gc, double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth, Color _strokePaint) {
		drawTriangle(_gc, _x0, _y0, _x1, _y1, _x2, _y2, _strokeWidth, Drawer4Fx.fxColor(_strokePaint));
	}
	static void drawTriangle(GraphicsContext _gc, double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth, Paint _strokePaint) {
		double oldWidth  = _gc.getLineWidth();
		Paint  oldStroke = _gc.getStroke();

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);
		if(_strokePaint != null)
			_gc.setStroke(_strokePaint);

		_gc.strokePolyline(new double[] { _x0, _x1, _x2 }, new double[] { _y0, _y1, _y2 }, 3);

		if(_strokePaint != null)
			_gc.setStroke(oldStroke);
		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}
	static void drawTriangle(GraphicsContext _gc, double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		drawTriangle(_gc, _x0, _y0, _x1, _y1, _x2, _y2, _strokeWidth, Drawer4Fx.fxColor(_strokePaint), Drawer4Fx.fxColor(_fillPaint));
	}
	static void drawTriangle(GraphicsContext _gc, double _x0, double _y0, double _x1, double _y1, double _x2, double _y2, double _strokeWidth, Paint _strokePaint, Paint _fillPaint) {
		double oldWidth  = _gc.getLineWidth();
		Paint  oldStroke = _gc.getStroke();
		Paint  oldFill   = _gc.getFill();

		if(_fillPaint != null)
			_gc.setFill(_fillPaint);

		_gc.fillPolygon(new double[] { _x0, _x1, _x2 }, new double[] { _y0, _y1, _y2 }, 3);

		if(_fillPaint != null)
			_gc.setFill(oldFill);

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);
		if(_strokePaint != null)
			_gc.setStroke(_strokePaint);

		_gc.strokePolyline(new double[] { _x0, _x1, _x2 }, new double[] { _y0, _y1, _y2 }, 3);

		if(_strokePaint != null)
			_gc.setStroke(oldStroke);
		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}


	static void drawTriangles(GraphicsContext _gc, double[] _x, double[] _y) {
		assert(_x.length == _y.length);
		assert(_x.length % 3 == 0);
		
		for(int i = 0; i < _x.length / 3; i += 3)
			_gc.strokePolyline(new double[] { _x[i], _x[i+1], _x[i+2] }, new double[] { _y[i], _y[i+1], _y[i+2] }, 3);
	}
	static void drawTriangles(GraphicsContext _gc, double[] _x, double[] _y, Color _fillPaint) {
		drawTriangles(_gc, _x, _y, Drawer4Fx.fxColor(_fillPaint));
	}
	static void drawTriangles(GraphicsContext _gc, double[] _x, double[] _y, Paint _fillPaint) {
		Paint  oldFill   = _gc.getFill();

		if(_fillPaint != null)
			_gc.setFill(_fillPaint);

		for(int i = 0; i < _x.length / 3; i += 3)
			_gc.fillPolygon(new double[] { _x[i], _x[i+1], _x[i+2] }, new double[] { _y[i], _y[i+1], _y[i+2] }, 3);

		if(_fillPaint != null)
			_gc.setFill(oldFill);
	}
	static void drawTriangles(GraphicsContext _gc, double[] _x, double[] _y, double _strokeWidth) {
		assert(_x.length == _y.length);
		assert(_x.length % 3 == 0);

		double oldWidth  = _gc.getLineWidth();

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);

		for(int i = 0; i < _x.length / 3; i += 3)
			_gc.strokePolyline(new double[] { _x[i], _x[i+1], _x[i+2] }, new double[] { _y[i], _y[i+1], _y[i+2] }, 3);

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}
	static void drawTriangles(GraphicsContext _gc, double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
		drawTriangles(_gc, _x, _y, _strokeWidth, Drawer4Fx.fxColor(_strokePaint));
	}
	static void drawTriangles(GraphicsContext _gc, double[] _x, double[] _y, double _strokeWidth, Paint _strokePaint) {
		double oldWidth  = _gc.getLineWidth();
		Paint  oldStroke = _gc.getStroke();

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);
		if(_strokePaint != null)
			_gc.setStroke(_strokePaint);

		for(int i = 0; i < _x.length / 3; i += 3)
			_gc.strokePolyline(new double[] { _x[i], _x[i+1], _x[i+2] }, new double[] { _y[i], _y[i+1], _y[i+2] }, 3);

		if(_strokePaint != null)
			_gc.setStroke(oldStroke);
		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}
	static void drawTriangles(GraphicsContext _gc, double[] _x, double[] _y, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		drawTriangles(_gc, _x, _y, _strokeWidth, Drawer4Fx.fxColor(_strokePaint), Drawer4Fx.fxColor(_fillPaint));
	}
	static void drawTriangles(GraphicsContext _gc, double[] _x, double[] _y, double _strokeWidth, Paint _strokePaint, Paint _fillPaint) {
		double oldWidth  = _gc.getLineWidth();
		Paint  oldStroke = _gc.getStroke();
		Paint  oldFill   = _gc.getFill();

		if(_fillPaint != null)
			_gc.setFill(_fillPaint);

		for(int i = 0; i < _x.length / 3; i += 3)
			_gc.fillPolygon(new double[] { _x[i], _x[i+1], _x[i+2] }, new double[] { _y[i], _y[i+1], _y[i+2] }, 3);

		if(_fillPaint != null)
			_gc.setFill(oldFill);

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);
		if(_strokePaint != null)
			_gc.setStroke(_strokePaint);

		for(int i = 0; i < _x.length / 3; i += 3)
			_gc.strokePolyline(new double[] { _x[i], _x[i+1], _x[i+2] }, new double[] { _y[i], _y[i+1], _y[i+2] }, 3);

		if(_strokePaint != null)
			_gc.setStroke(oldStroke);
		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}


	static void drawRectangle(GraphicsContext _gc, double _x, double _y, double _w, double _h) {
		_gc.strokeRect(_x, _y, _w, _h);
	}
	static void drawRectangle(GraphicsContext _gc, double _x, double _y, double _w, double _h, Color _fillPaint) {
		drawRectangle(_gc, _x, _y, _w, _h, Drawer4Fx.fxColor(_fillPaint));
	}
	static void drawRectangle(GraphicsContext _gc, double _x, double _y, double _w, double _h, Paint _fillPaint) {
		Paint  oldFill   = _gc.getFill();

		if(_fillPaint != null)
			_gc.setFill(_fillPaint);

		_gc.fillRect(_x, _y, _w, _h);

		if(_fillPaint != null)
			_gc.setFill(oldFill);
	}
	static void drawRectangle(GraphicsContext _gc, double _x, double _y, double _w, double _h, double _strokeWidth) {
		double oldWidth  = _gc.getLineWidth();

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);

		_gc.strokeRect(_x, _y, _w, _h);

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}
	static void drawRectangle(GraphicsContext _gc, double _x, double _y, double _w, double _h, double _strokeWidth, Color _strokePaint) {
		drawRectangle(_gc, _x, _y, _w, _h, _strokeWidth, Drawer4Fx.fxColor(_strokePaint));
	}
	static void drawRectangle(GraphicsContext _gc, double _x, double _y, double _w, double _h, double _strokeWidth, Paint _strokePaint) {
		double oldWidth  = _gc.getLineWidth();
		Paint  oldStroke = _gc.getStroke();

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);
		if(_strokePaint != null)
			_gc.setStroke(_strokePaint);

		_gc.strokeRect(_x, _y, _w, _h);

		if(_strokePaint != null)
			_gc.setStroke(oldStroke);
		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}
	static void drawRectangle(GraphicsContext _gc, double _x, double _y, double _w, double _h, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		drawRectangle(_gc, _x, _y, _w, _h, _strokeWidth, Drawer4Fx.fxColor(_strokePaint), Drawer4Fx.fxColor(_fillPaint));
	}
	static void drawRectangle(GraphicsContext _gc, double _x, double _y, double _w, double _h, double _strokeWidth, Paint _strokePaint, Paint _fillPaint) {
		double oldWidth  = _gc.getLineWidth();
		Paint  oldStroke = _gc.getStroke();
		Paint  oldFill   = _gc.getFill();

		if(_fillPaint != null)
			_gc.setFill(_fillPaint);

		_gc.fillRect(_x, _y, _w, _h);

		if(_fillPaint != null)
			_gc.setFill(oldFill);

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);
		if(_strokePaint != null)
			_gc.setStroke(_strokePaint);

		_gc.strokeRect(_x, _y, _w, _h);

		if(_strokePaint != null)
			_gc.setStroke(oldStroke);
		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}


	static void drawRectangles(GraphicsContext _gc, double[] _x, double[] _y, double[] _w, double[] _h) {
		assert(_x.length == _y.length && _x.length == _w.length && _x.length == _h.length);
		
		for(int i = 0; i < _x.length; ++i)
			_gc.strokeRect(_x[i], _y[i], _w[i], _h[i]);
	}
	static void drawRectangles(GraphicsContext _gc, double[] _x, double[] _y, double[] _w, double[] _h, Color _fillPaint) {
		drawRectangles(_gc, _x, _y, _w, _h, Drawer4Fx.fxColor(_fillPaint));
	}
	static void drawRectangles(GraphicsContext _gc, double[] _x, double[] _y, double[] _w, double[] _h, Paint _fillPaint) {
		assert(_x.length == _y.length && _x.length == _w.length && _x.length == _h.length);

		Paint  oldFill   = _gc.getFill();

		if(_fillPaint != null)
			_gc.setFill(_fillPaint);

		for(int i = 0; i < _x.length; ++i)
			_gc.fillRect(_x[i], _y[i], _w[i], _h[i]);

		if(_fillPaint != null)
			_gc.setFill(oldFill);
	}
	static void drawRectangles(GraphicsContext _gc, double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth) {
		assert(_x.length == _y.length && _x.length == _w.length && _x.length == _h.length);

		double oldWidth  = _gc.getLineWidth();

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);

		for(int i = 0; i < _x.length; ++i)
			_gc.strokeRect(_x[i], _y[i], _w[i], _h[i]);

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}
	static void drawRectangles(GraphicsContext _gc, double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint) {
		drawRectangles(_gc, _x, _y, _w, _h, _strokeWidth, Drawer4Fx.fxColor(_strokePaint));
	}
	static void drawRectangles(GraphicsContext _gc, double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Paint _strokePaint) {
		assert(_x.length == _y.length && _x.length == _w.length && _x.length == _h.length);

		double oldWidth  = _gc.getLineWidth();
		Paint  oldStroke = _gc.getStroke();

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);
		if(_strokePaint != null)
			_gc.setStroke(_strokePaint);

		for(int i = 0; i < _x.length; ++i)
			_gc.strokeRect(_x[i], _y[i], _w[i], _h[i]);

		if(_strokePaint != null)
			_gc.setStroke(oldStroke);
		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}
	static void drawRectangles(GraphicsContext _gc, double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		drawRectangles(_gc, _x, _y, _w, _h, _strokeWidth, Drawer4Fx.fxColor(_strokePaint), Drawer4Fx.fxColor(_fillPaint));
	}
	static void drawRectangles(GraphicsContext _gc, double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Paint _strokePaint, Paint _fillPaint) {
		assert(_x.length == _y.length && _x.length == _w.length && _x.length == _h.length);

		double oldWidth  = _gc.getLineWidth();
		Paint  oldStroke = _gc.getStroke();
		Paint  oldFill   = _gc.getFill();

		if(_fillPaint != null)
			_gc.setFill(_fillPaint);

		for(int i = 0; i < _x.length; ++i)
			_gc.fillRect(_x[i], _y[i], _w[i], _h[i]);

		if(_fillPaint != null)
			_gc.setFill(oldFill);

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);
		if(_strokePaint != null)
			_gc.setStroke(_strokePaint);

		for(int i = 0; i < _x.length; ++i)
			_gc.strokeRect(_x[i], _y[i], _w[i], _h[i]);

		if(_strokePaint != null)
			_gc.setStroke(oldStroke);
		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}


	static final ArcType toFx(Drawer.ArcMode _mode) { return _mode == Drawer.ArcMode.OPEN ? ArcType.OPEN : _mode == Drawer.ArcMode.ROUND ? ArcType.ROUND : _mode == Drawer.ArcMode.CHORD ? ArcType.CHORD : ArcType.OPEN; }

	static void drawArc(GraphicsContext _gc, double _x, double _y, double _w, double _h, double _startAngle, double endAngle, Drawer.ArcMode _mode) {
		_gc.strokeArc(_x, _y, _w, _h, _startAngle, endAngle, toFx(_mode));
	}
	static void drawArc(GraphicsContext _gc, double _x, double _y, double _w, double _h, double _startAngle, double endAngle, Drawer.ArcMode _mode,Color _fillPaint) {
		drawArc(_gc, _x, _y, _w, _h, _startAngle, endAngle, _mode, Drawer4Fx.fxColor(_fillPaint));
	}
	static void drawArc(GraphicsContext _gc, double _x, double _y, double _w, double _h, double _startAngle, double endAngle, Drawer.ArcMode _mode, Paint _fillPaint) {
		Paint  oldFill   = _gc.getFill();

		if(_fillPaint != null)
			_gc.setFill(_fillPaint);

		_gc.fillArc(_x, _y, _w, _h, _startAngle, endAngle, toFx(_mode));

		if(_fillPaint != null)
			_gc.setFill(oldFill);
	}
	static void drawArc(GraphicsContext _gc, double _x, double _y, double _w, double _h, double _startAngle, double endAngle, Drawer.ArcMode _mode, double _strokeWidth) {
		double oldWidth  = _gc.getLineWidth();

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);

		_gc.strokeArc(_x, _y, _w, _h, _startAngle, endAngle, toFx(_mode));

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}
	static void drawArc(GraphicsContext _gc, double _x, double _y, double _w, double _h, double _startAngle, double endAngle, Drawer.ArcMode _mode, double _strokeWidth, Color _strokePaint) {
		drawArc(_gc, _x, _y, _w, _h, _startAngle, endAngle, _mode, _strokeWidth, Drawer4Fx.fxColor(_strokePaint));
	}
	static void drawArc(GraphicsContext _gc, double _x, double _y, double _w, double _h, double _startAngle, double endAngle, Drawer.ArcMode _mode, double _strokeWidth, Paint _strokePaint) {
		double oldWidth  = _gc.getLineWidth();
		Paint  oldStroke = _gc.getStroke();

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);
		if(_strokePaint != null)
			_gc.setStroke(_strokePaint);

		_gc.strokeArc(_x, _y, _w, _h, _startAngle, endAngle, toFx(_mode));

		if(_strokePaint != null)
			_gc.setStroke(oldStroke);
		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}
	static void drawArc(GraphicsContext _gc, double _x, double _y, double _w, double _h, double _startAngle, double endAngle, Drawer.ArcMode _mode, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		drawArc(_gc, _x, _y, _w, _h, _startAngle, endAngle, _mode, _strokeWidth, Drawer4Fx.fxColor(_strokePaint), Drawer4Fx.fxColor(_fillPaint));
	}
	static void drawArc(GraphicsContext _gc, double _x, double _y, double _w, double _h, double _startAngle, double endAngle, Drawer.ArcMode _mode, double _strokeWidth, Paint _strokePaint, Paint _fillPaint) {
		double oldWidth  = _gc.getLineWidth();
		Paint  oldStroke = _gc.getStroke();
		Paint  oldFill   = _gc.getFill();

		if(_fillPaint != null)
			_gc.setFill(_fillPaint);

		_gc.fillArc(_x, _y, _w, _h, _startAngle, endAngle, toFx(_mode));

		if(_fillPaint != null)
			_gc.setFill(oldFill);

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);
		if(_strokePaint != null)
			_gc.setStroke(_strokePaint);

		_gc.strokeArc(_x, _y, _w, _h, _startAngle, endAngle, toFx(_mode));

		if(_strokePaint != null)
			_gc.setStroke(oldStroke);
		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}


	static void drawEllipse(GraphicsContext _gc, double _x, double _y, double _w, double _h) {
		_gc.strokeOval(_x, _y, _w, _h);
//		_gc.strokeOval(_x - _w/2.0, _y - _h/2.0, _w, _h);
	}
	static void drawEllipse(GraphicsContext _gc, double _x, double _y, double _w, double _h, Color _fillPaint) {
		drawEllipse(_gc, _x, _y, _w, _h, Drawer4Fx.fxColor(_fillPaint));
	}
	static void drawEllipse(GraphicsContext _gc, double _x, double _y, double _w, double _h, Paint _fillPaint) {
		Paint  oldFill   = _gc.getFill();

		if(_fillPaint != null)
			_gc.setFill(_fillPaint);

//		_gc.fillOval(_x - _w/2.0, _y - _h/2.0, _w, _h);
		_gc.fillOval(_x, _y, _w, _h);

		if(_fillPaint != null)
			_gc.setFill(oldFill);
	}
	static void drawEllipse(GraphicsContext _gc, double _x, double _y, double _w, double _h, double _strokeWidth) {
		double oldWidth  = _gc.getLineWidth();

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);

		_gc.strokeOval(_x, _y, _w, _h);
//		_gc.strokeOval(_x - _w/2.0, _y - _h/2.0, _w, _h);

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}
	static void drawEllipse(GraphicsContext _gc, double _x, double _y, double _w, double _h, double _strokeWidth, Color _strokePaint) {
		drawEllipse(_gc, _x, _y, _w, _h, _strokeWidth, Drawer4Fx.fxColor(_strokePaint));
	}
	static void drawEllipse(GraphicsContext _gc, double _x, double _y, double _w, double _h, double _strokeWidth, Paint _strokePaint) {
		double oldWidth  = _gc.getLineWidth();
		Paint  oldStroke = _gc.getStroke();

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);
		if(_strokePaint != null)
			_gc.setStroke(_strokePaint);

		_gc.strokeOval(_x, _y, _w, _h);
//		_gc.strokeOval(_x - _w/2.0, _y - _h/2.0, _w, _h);

		if(_strokePaint != null)
			_gc.setStroke(oldStroke);
		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}
	static void drawEllipse(GraphicsContext _gc, double _x, double _y, double _w, double _h, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		drawEllipse(_gc, _x, _y, _w, _h, _strokeWidth, Drawer4Fx.fxColor(_strokePaint), Drawer4Fx.fxColor(_fillPaint));
	}
	static void drawEllipse(GraphicsContext _gc, double _x, double _y, double _w, double _h, double _strokeWidth, Paint _strokePaint, Paint _fillPaint) {
		double oldWidth  = _gc.getLineWidth();
		Paint  oldStroke = _gc.getStroke();
		Paint  oldFill   = _gc.getFill();

		if(_fillPaint != null)
			_gc.setFill(_fillPaint);

		_gc.fillOval(_x, _y, _w, _h);
//		_gc.fillOval(_x - _w/2.0, _y - _h/2.0, _w, _h);

		if(_fillPaint != null)
			_gc.setFill(oldFill);

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);
		if(_strokePaint != null)
			_gc.setStroke(_strokePaint);

		_gc.strokeOval(_x, _y, _w, _h);
//		_gc.strokeOval(_x - _w/2.0, _y - _h/2.0, _w, _h);

		if(_strokePaint != null)
			_gc.setStroke(oldStroke);
		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}


	static void drawEllipses(GraphicsContext _gc, double[] _x, double[] _y, double[] _w, double[] _h) {
		assert(_x.length == _y.length && _x.length == _w.length && _x.length == _h.length);

		for(int i = 0; i < _x.length; ++i)
			_gc.strokeOval(_x[i], _y[i], _w[i], _h[i]);
//			_gc.strokeOval(_x[i] - _w[i]/2.0, _y[i] - _h[i]/2.0, _w[i], _h[i]);
	}
	static void drawEllipses(GraphicsContext _gc, double[] _x, double[] _y, double[] _w, double[] _h, Color _fillPaint) {
		drawEllipses(_gc, _x, _y, _w, _h, Drawer4Fx.fxColor(_fillPaint));
	}
	static void drawEllipses(GraphicsContext _gc, double[] _x, double[] _y, double[] _w, double[] _h, Paint _fillPaint) {
		assert(_x.length == _y.length && _x.length == _w.length && _x.length == _h.length);

		Paint  oldFill   = _gc.getFill();

		if(_fillPaint != null)
			_gc.setFill(_fillPaint);

		for(int i = 0; i < _x.length; ++i)
			_gc.fillOval(_x[i], _y[i], _w[i], _h[i]);
//			_gc.fillOval(_x[i] - _w[i]/2.0, _y[i] - _h[i]/2.0, _w[i], _h[i]);

		if(_fillPaint != null)
			_gc.setFill(oldFill);
	}
	static void drawEllipses(GraphicsContext _gc, double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth) {
		assert(_x.length == _y.length && _x.length == _w.length && _x.length == _h.length);

		double oldWidth  = _gc.getLineWidth();

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);

		for(int i = 0; i < _x.length; ++i)
			_gc.strokeOval(_x[i], _y[i], _w[i], _h[i]);
//			_gc.strokeOval(_x[i] - _w[i]/2.0, _y[i] - _h[i]/2.0, _w[i], _h[i]);

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}
	static void drawEllipses(GraphicsContext _gc, double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint) {
		drawEllipses(_gc, _x, _y, _w, _h, _strokeWidth, Drawer4Fx.fxColor(_strokePaint));
	}
	static void drawEllipses(GraphicsContext _gc, double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Paint _strokePaint) {
		assert(_x.length == _y.length && _x.length == _w.length && _x.length == _h.length);

		double oldWidth  = _gc.getLineWidth();
		Paint  oldStroke = _gc.getStroke();

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);
		if(_strokePaint != null)
			_gc.setStroke(_strokePaint);

		for(int i = 0; i < _x.length; ++i)
			_gc.strokeOval(_x[i], _y[i], _w[i], _h[i]);
//			_gc.strokeOval(_x[i] - _w[i]/2.0, _y[i] - _h[i]/2.0, _w[i], _h[i]);

		if(_strokePaint != null)
			_gc.setStroke(oldStroke);
		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}
	static void drawEllipses(GraphicsContext _gc, double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		drawEllipses(_gc, _x, _y, _w, _h, _strokeWidth, Drawer4Fx.fxColor(_strokePaint), Drawer4Fx.fxColor(_fillPaint));
	}
	static void drawEllipses(GraphicsContext _gc, double[] _x, double[] _y, double[] _w, double[] _h, double _strokeWidth, Paint _strokePaint, Paint _fillPaint) {
		assert(_x.length == _y.length && _x.length == _w.length && _x.length == _h.length);

		double oldWidth  = _gc.getLineWidth();
		Paint  oldStroke = _gc.getStroke();
		Paint  oldFill   = _gc.getFill();

		if(_fillPaint != null)
			_gc.setFill(_fillPaint);

		for(int i = 0; i < _x.length; ++i)
			_gc.fillOval(_x[i], _y[i], _w[i], _h[i]);
//			_gc.fillOval(_x[i] - _w[i]/2.0, _y[i] - _h[i]/2.0, _w[i], _h[i]);

		if(_fillPaint != null)
			_gc.setFill(oldFill);

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);
		if(_strokePaint != null)
			_gc.setStroke(_strokePaint);

		for(int i = 0; i < _x.length; ++i)
			_gc.strokeOval(_x[i], _y[i], _w[i], _h[i]);
//			_gc.strokeOval(_x[i] - _w[i]/2.0, _y[i] - _h[i]/2.0, _w[i], _h[i]);

		if(_strokePaint != null)
			_gc.setStroke(oldStroke);
		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}


	static void drawPolygon(GraphicsContext _gc, double[] _x, double[] _y) {
		assert(_x.length == _y.length);

		_gc.strokePolygon(_x, _y, _x.length);
	}
	static void drawPolygon(GraphicsContext _gc, double[] _x, double[] _y, Color _fillPaint) {
		drawPolygon(_gc, _x, _y, Drawer4Fx.fxColor(_fillPaint));
	}
	static void drawPolygon(GraphicsContext _gc, double[] _x, double[] _y, Paint _fillPaint) {
		assert(_x.length == _y.length);

		Paint  oldFill   = _gc.getFill();

		if(_fillPaint != null)
			_gc.setFill(_fillPaint);

		_gc.fillPolygon(_x, _y, _x.length);

		if(_fillPaint != null)
			_gc.setFill(oldFill);
	}
	static void drawPolygon(GraphicsContext _gc, double[] _x, double[] _y, double _strokeWidth) {
		assert(_x.length == _y.length);

		double oldWidth  = _gc.getLineWidth();

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);

		_gc.strokePolygon(_x, _y, _x.length);

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}
	static void drawPolygon(GraphicsContext _gc, double[] _x, double[] _y, double _strokeWidth, Color _strokePaint) {
		drawPolygon(_gc, _x, _y, _strokeWidth, Drawer4Fx.fxColor(_strokePaint));
	}
	static void drawPolygon(GraphicsContext _gc, double[] _x, double[] _y, double _strokeWidth, Paint _strokePaint) {
		assert(_x.length == _y.length);

		double oldWidth  = _gc.getLineWidth();
		Paint  oldStroke = _gc.getStroke();

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);
		if(_strokePaint != null)
			_gc.setStroke(_strokePaint);

		_gc.strokePolygon(_x, _y, _x.length);

		if(_strokePaint != null)
			_gc.setStroke(oldStroke);
		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}
	static void drawPolygon(GraphicsContext _gc, double[] _x, double[] _y, double _strokeWidth, Color _strokePaint, Color _fillPaint) {
		drawPolygon(_gc, _x, _y, _strokeWidth, Drawer4Fx.fxColor(_strokePaint), Drawer4Fx.fxColor(_fillPaint));
	}
	static void drawPolygon(GraphicsContext _gc, double[] _x, double[] _y, double _strokeWidth, Paint _strokePaint, Paint _fillPaint) {
		assert(_x.length == _y.length);

		double oldWidth  = _gc.getLineWidth();
		Paint  oldStroke = _gc.getStroke();
		Paint  oldFill   = _gc.getFill();

		if(_fillPaint != null)
			_gc.setFill(_fillPaint);

		_gc.fillPolygon(_x, _y, _x.length);

		if(_fillPaint != null)
			_gc.setFill(oldFill);

		if(_strokeWidth >= 0d)
			_gc.setLineWidth(_strokeWidth);
		if(_strokePaint != null)
			_gc.setStroke(_strokePaint);

		_gc.strokePolygon(_x, _y, _x.length);

		if(_strokePaint != null)
			_gc.setStroke(oldStroke);
		if(_strokeWidth >= 0d)
			_gc.setLineWidth(oldWidth);
	}

	static void drawString(GraphicsContext _gc, String _string, double _x, double _y) {
		javafx.scene.text.Font old = _gc.getFont();

		_gc.strokeText(_string, _x, _y);
		
		_gc.setFont(old);
	}
	static void drawString(GraphicsContext _gc, String _string, double _x, double _y, double _fontSize) {
		javafx.scene.text.Font old = _gc.getFont();

		_gc.setFont(new javafx.scene.text.Font(_fontSize));
		_gc.strokeText(_string, _x, _y);
		
		_gc.setFont(old);
	}
	static void drawString(GraphicsContext _gc, String _string, double _x, double _y, String _fontName, double _fontSize) {
		javafx.scene.text.Font old = _gc.getFont();

		_gc.setFont(new javafx.scene.text.Font(_fontName, 12));
		_gc.strokeText(_string, _x, _y);
		
		_gc.setFont(old);
	}
	static void drawString(GraphicsContext _gc, String _string, double _x, double _y, Font _font) {
		javafx.scene.text.Font old = _gc.getFont();

		_gc.setFont(new javafx.scene.text.Font(_font.getName().orElseGet(() -> "Times New Roman"), _font.getSize()));
		_gc.strokeText(_string, _x, _y);
		
		_gc.setFont(old);
	}


	// AWT Objects
	static void drawImage(GraphicsContext _gc, Image image, double _x, double _y) {
		_gc.drawImage(image, _x, _y);
	}
	static void drawImage(GraphicsContext _gc, Image image, double _x, double _y, double _angle) {
        Rotate r = new Rotate(_angle, _x + image.getWidth() / 2, _y + image.getHeight() / 2);

        _gc.save();
        _gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
        _gc.drawImage(image, _x, _y);
      _gc.restore();
	}
	static void drawImage(GraphicsContext _gc, Image _img, BoundingBox.TwoDims _in, BoundingBox.TwoDims _out) {
		if(_img != null) {
			if(_in == null)
				_in = SimpleRectangle2D.fromBounds(0,  0, _img.getWidth(), _img.getHeight());
			if(_out == null)
				_out = SimpleRectangle2D.fromBounds(0,  0, _img.getWidth(), _img.getHeight());

			_gc.drawImage(_img,
						  _in.getMinX(),  _in.getMinY(),  _in.getWidth(),  _in.getHeight(),
						  _out.getMinX(), _out.getMinY(), _out.getWidth(), _out.getHeight());
		}
	}

	// JavaFX Objects
	public static void drawShapes(GraphicsContext _gc, Shape... _shapes) {
		for(Shape s : _shapes)
			drawShape(_gc, s);
	}

	static void drawShape(GraphicsContext _gc, Shape _shape) {
		_gc.setStroke(_shape.getStroke());
		_gc.setLineWidth(_shape.getStrokeWidth());

		if(_shape instanceof Arc) {
			drawShape_Arc(_gc, (Arc) _shape);
		}
		else if(_shape instanceof Circle) {
			drawShape_Circle(_gc, (Circle) _shape);
		}
		else if(_shape instanceof Ellipse) {
			drawShape_Ellipse(_gc, (Ellipse) _shape);
		}
		else if(_shape instanceof Line) {
			drawShape_Line(_gc, (Line) _shape);
		}
		else if(_shape instanceof Polygon) {
			drawShape_Polygon(_gc, (Polygon) _shape);
		}
		else if(_shape instanceof Polyline) {
			drawShape_Polyline(_gc, (Polyline) _shape);
		}
		else if(_shape instanceof Rectangle) {
			drawShape_Rectangle(_gc, (Rectangle) _shape);
		}
		else if(_shape instanceof Text) {
			drawShape_Text(_gc, (Text) _shape);
		}
		else if(_shape instanceof CubicCurve) {
			drawShape_CubicCurve(_gc, (CubicCurve) _shape);
		}
		else if(_shape instanceof QuadCurve) {
			drawShape_QuadCurve(_gc, (QuadCurve) _shape);
		}

		else if(_shape instanceof Path) {
			drawPath(_gc, (Path) _shape);
		}
	}
	static void drawShape_Arc(GraphicsContext _gc, Arc _arc) {
		Point2D u  = Points.of(_arc.getCenterX() - _arc.getRadiusX(), _arc.getCenterY() + _arc.getRadiusY());
		Point2D v  = Points.of(2.0 * _arc.getRadiusX(), 2.0 * _arc.getRadiusY());
		double  e0 = _arc.getStartAngle();
		double  e1 = e0 + _arc.getLength();

		if(_arc.getFill() != null) {
			_gc.setFill(_arc.getFill());
			_gc.fillArc(u.getX(), u.getY(), v.getX(), v.getY(), e0, e1, ArcType.OPEN);	// ArcType.CHORD - ArcType.ROUND
		} else
			_gc.strokeArc(u.getX(), u.getY(), v.getX(), v.getY(), e0, e1, ArcType.OPEN);	// ArcType.CHORD - ArcType.ROUND
	}
	static void drawShape_Circle(GraphicsContext _gc, Circle _circle) {
		Point2D u = Points.of(_circle.getCenterX() - _circle.getRadius(), _circle.getCenterY() - _circle.getRadius());
		Point2D v = Points.of(2.0 * _circle.getRadius(), 2.0 * _circle.getRadius());

		if(_circle.getFill() != null) {
			_gc.setFill(_circle.getFill());
			_gc.fillOval(u.getX(), u.getY(), v.getX(), v.getY());
		} else
			_gc.strokeOval(u.getX(), u.getY(), v.getX(), v.getY());		
	}
	static void drawShape_CubicCurve(GraphicsContext _gc, CubicCurve _curve) {
		// c.getControlX1(), c.getControlY1() ???
		// c.getControlX2(), c.getControlY2() ???

		if(_curve.getFill() != null) {
			_gc.setFill(_curve.getFill());
		} else
			_gc.strokeOval(_curve.getStartX(), _curve.getStartY(), _curve.getEndX(), _curve.getEndY());
	}
	static void drawShape_QuadCurve(GraphicsContext _gc, QuadCurve _curve) {
		// c.getControlX1(), c.getControlY1() ???
		// c.getControlX2(), c.getControlY2() ???

		if(_curve.getFill() != null) {
			_gc.setFill(_curve.getFill());
		} else
			_gc.strokeOval(_curve.getStartX(), _curve.getStartY(), _curve.getEndX(), _curve.getEndY());
	}
	static void drawShape_Ellipse(GraphicsContext _gc, Ellipse _ellipse) {
		Point2D u = Points.of(_ellipse.getCenterX() - _ellipse.getRadiusX(), _ellipse.getCenterY() + _ellipse.getRadiusY());
		Point2D v = Points.of(2.0 * _ellipse.getRadiusX(), 2.0 * _ellipse.getRadiusY());

		if(_ellipse.getFill() != null) {
			_gc.setFill(_ellipse.getFill());
			_gc.fillOval(u.getX(), u.getY(), v.getX(), v.getY());
		} else
			_gc.strokeOval(u.getX(), u.getY(), v.getX(), v.getY());	
	}
	static void drawShape_Line(GraphicsContext _gc, Line _line) {
		_gc.strokeLine(_line.getStartX(), _line.getStartY(), _line.getEndX(), _line.getEndY());
	}
	static void drawShape_Polygon(GraphicsContext _gc, Polygon _polygon) {
		int nbPoints = _polygon.getPoints().size() / 2;
		double[] x = new double[nbPoints];
		double[] y = new double[nbPoints];
		for(int i = 0; i < nbPoints; ++i) {
			x[i] = _polygon.getPoints().get(2*i);
			y[i] = _polygon.getPoints().get(2*i+1);
		}

		if(_polygon.getFill() != null) {
			_gc.setFill(_polygon.getFill());
			_gc.fillPolygon(x, y, nbPoints);
		} else
			_gc.strokePolygon(x, y, nbPoints);
	}
	static void drawShape_Polyline(GraphicsContext _gc, Polyline _polyline) {
		int nbPoints = _polyline.getPoints().size() / 2;
		double[] x = new double[nbPoints];
		double[] y = new double[nbPoints];
		for(int i = 0; i < nbPoints; ++i) {
			x[i] = _polyline.getPoints().get(2*i);
			y[i] = _polyline.getPoints().get(2*i+1);
		}

		_gc.strokePolyline(x, y, nbPoints);
	}
	static void drawShape_Rectangle(GraphicsContext _gc, Rectangle _rectangle) {
		if(_rectangle.getFill() != null) {
			_gc.setFill(_rectangle.getFill());
			_gc.fillRect(_rectangle.getX(), _rectangle.getY(), _rectangle.getWidth(), _rectangle.getHeight());
//			gc2d.fillRoundRect(r.getX(), r.getY(), r.getWidth(), r.getHeight(), 10, 10);
		} else
			_gc.strokeRect(_rectangle.getX(), _rectangle.getY(), _rectangle.getWidth(), _rectangle.getHeight());
//			gc2d.strokeRoundRect(r.getX(), r.getY(), r.getWidth(), r.getHeight(), 10, 10);
	}
	static void drawShape_Text(GraphicsContext _gc, Text _text) {
		if(_text.getFill() != null) {
			_gc.setFill(_text.getFill());
			_gc.fillText(_text.getText(), _text.getX(), _text.getY());
		} else
			_gc.strokeText(_text.getText(), _text.getX(), _text.getY());
	}

	static void drawArc(GraphicsContext _gc, Arc _arc) {
		drawShape_Arc(_gc, _arc);
	}
	static void drawCircle(GraphicsContext _gc, Circle _circle) {
		drawShape_Circle(_gc, _circle);
	}
	static void drawCubicCurve(GraphicsContext _gc, CubicCurve _cubicCurve) {
		drawShape_CubicCurve(_gc, _cubicCurve);
	}
	static void drawQuadCurve(GraphicsContext _gc, QuadCurve _quadCurve) {
		drawShape_QuadCurve(_gc, _quadCurve);
	}
	static void drawEllipse(GraphicsContext _gc, Ellipse _ellipse) {
		drawShape_Ellipse(_gc, _ellipse);
	}
	static void drawLine(GraphicsContext _gc, Line _line) {
		drawShape_Line(_gc, _line);
	}
	static void drawPolygon(GraphicsContext _gc, Polygon _polygon) {
		drawShape_Polygon(_gc, _polygon);
	}
	static void drawPolyline(GraphicsContext _gc, Polyline _polyline) {
		drawShape_Polyline(_gc, _polyline);
	}
	static void drawRectangle(GraphicsContext _gc, Rectangle _rectangle) {
		drawShape_Rectangle(_gc, _rectangle);
	}
	static void drawText(GraphicsContext _gc, Text _text) {
		drawShape_Text(_gc, _text);
	}
	

	static void drawPath(GraphicsContext _gc, Path _p) {
		Point2D lastp = Points.zero2();

		for(PathElement pe : _p.getElements()) {
			if(pe instanceof MoveTo) {
				lastp = drawPath_MoveTo(_gc, (MoveTo) pe);
			}
			if(pe instanceof ArcTo) {
				lastp = drawPath_ArcTo(_gc, (ArcTo) pe);
			}
			if(pe instanceof LineTo) {
				lastp = drawPath_LineTo(_gc, (LineTo) pe);
			}
			if(pe instanceof HLineTo) {
				lastp = drawPath_HLineTo(_gc, (HLineTo) pe, lastp);
			}
			if(pe instanceof VLineTo) {
				lastp = drawPath_VLineTo(_gc, (VLineTo) pe, lastp);
			}
			if(pe instanceof CubicCurveTo) {
				lastp = drawPath_CubicCurveTo(_gc, (CubicCurveTo) pe);
			}
			if(pe instanceof QuadCurveTo) {
				lastp = drawPath_QuadCurveTo(_gc, (QuadCurveTo) pe);
			}
			if(pe instanceof ClosePath) {
				_gc.closePath();
			}

		}
	}
	static Point2D drawPath_MoveTo(GraphicsContext _gc, MoveTo _moveTo) {
		_gc.moveTo(_moveTo.getX(), _moveTo.getY());
		return Points.of(_moveTo.getX(), _moveTo.getY());
	}
	static Point2D drawPath_ArcTo(GraphicsContext _gc, ArcTo _arcTo) {
		_gc.arcTo(_arcTo.getX(), _arcTo.getY(), _arcTo.getX() + _arcTo.getRadiusX(), _arcTo.getY() + _arcTo.getRadiusY(), _arcTo.getRadiusX());
		return Points.of(_arcTo.getX(), _arcTo.getY());
	}
	static Point2D drawPath_LineTo(GraphicsContext _gc, LineTo _lineTo) {
		_gc.lineTo(_lineTo.getX(), _lineTo.getY());
		return Points.of(_lineTo.getX(), _lineTo.getY());
	}
	static Point2D drawPath_HLineTo(GraphicsContext _gc, HLineTo _hLineTo, Point2D _lastP) {
		_gc.lineTo(_hLineTo.getX(), _lastP.getY());
		return Points.of(_hLineTo.getX(), _lastP.getY());
	}
	static Point2D drawPath_VLineTo(GraphicsContext _gc, VLineTo _vLineTo, Point2D _lastP) {
		_gc.lineTo(_lastP.getX(), _vLineTo.getY());
		return Points.of(_lastP.getX(), _vLineTo.getY());
	}
	static Point2D drawPath_CubicCurveTo(GraphicsContext _gc, CubicCurveTo _cubicCurveTo) {
		_gc.bezierCurveTo(_cubicCurveTo.getControlX1(), _cubicCurveTo.getControlY1(), _cubicCurveTo.getControlX2(), _cubicCurveTo.getControlY2(), _cubicCurveTo.getX(), _cubicCurveTo.getY());
		return Points.of(_cubicCurveTo.getX(), _cubicCurveTo.getY());
	}
	static Point2D drawPath_QuadCurveTo(GraphicsContext _gc, QuadCurveTo _quadCurveTo) {
		_gc.quadraticCurveTo(_quadCurveTo.getControlX(), _quadCurveTo.getControlY(), _quadCurveTo.getX(), _quadCurveTo.getY());
		return Points.of(_quadCurveTo.getX(), _quadCurveTo.getY());
	}





	


}
