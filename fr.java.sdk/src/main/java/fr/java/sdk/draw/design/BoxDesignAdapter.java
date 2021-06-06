package fr.java.sdk.draw.design;

import fr.java.draw.Drawer;
import fr.java.draw.tools.Brush;
import fr.java.draw.tools.Colors;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.window.Edges2D;
import fr.java.patterns.drawable.Drawable;
import fr.java.sdk.math.BoundingBoxes;
import fr.java.sdk.math.Points;
import fr.java.sdk.math.window.SimpleEdges2D;

public class BoxDesignAdapter implements Drawable {

	public static final class BoxStyle {
		static final Brush DEFAULT_CLEAR         = Brush.of(Colors.TRANSPARENT);
		static final Brush DEFAULT_BORDER_BRUSH  = Brush.of(Colors.BLACK);
		static final Brush DEFAULT_INNER_BRUSH   = Brush.of(Colors.DARKGRAY);
		static final Brush DEFAULT_CONTENT_BRUSH = Brush.of(Colors.GRAY);

		Brush clearBrush;
		Brush borderBrush;
		Brush innerBrush;
		Brush contentBrush;

		BoxStyle() {
			super();
		}
		
		public final void 		setClearBrush(Brush _brush) {
			clearBrush = _brush;
		}
		public final Brush 		getClearBrush() {
			return clearBrush   == null ? DEFAULT_CLEAR         : clearBrush;
		}

		public final void 		setBorderBrush(Brush _brush) {
			borderBrush = _brush;
		}
		public final Brush 		getBorderBrush() {
			return borderBrush  == null ? DEFAULT_BORDER_BRUSH  : borderBrush;
		}

		public final void 		setInnerBrush(Brush _brush) {
			innerBrush = _brush;
		}
		public final Brush 		getInnerBrush() {
			return innerBrush   == null ? DEFAULT_INNER_BRUSH   : innerBrush;
		}

		public final void 		setContentBrush(Brush _brush) {
			contentBrush = _brush;
		}
		public final Brush 		getContentBrush() {
			return contentBrush == null ? DEFAULT_CONTENT_BRUSH : contentBrush;
		}

	}
	public static final class BoxConfig {
		boolean drawBox;

		BoxConfig() {
			super();
			drawBox = true;
		}

		public void drawBox(boolean _true) {
			drawBox = _true;
		}

	}
	
	class 		  	  		  BoxAttributes {
		double X,  Y,  W,  H;  // 
		double LM, RM, TM, BM; // LeftMargin,  RightMargin,  TopMargin,  BottomMargin		- Unused for now...
		double LB, RB, TB, BB; // LeftBorder,  RightBorder,  TopBorder,  BottomBorder
		double LP, RP, TP, BP; // LeftPadding, RightPadding, TopPadding, BottomPadding

		BoxAttributes(double _x, double _y, double _w, double _h) {
			super();

			X = _x;
			Y = _y;
			W = _w;
			H = _h; 
			LM = RM = TM = BM = 0d;
			LB = RB = TB = BB = 0d;
			LP = RP = TP = BP = 0d;

		}
		
		public final void 					setMargin(double _left, double _right, double _top, double _bottom)		{ LM = _left; RM = _right; TM = _top; BM = _bottom; }
		public final Edges2D				getMarginEdges() 														{ return new SimpleEdges2D(LM, RM, TM, BM); }
		public final void 					setMarginLeft(double _value)   											{ LM = _value; }
		public final void 					setMarginRight(double _value)  											{ RM = _value; }
		public final void 					setMarginTop(double _value)    											{ TM = _value; }
		public final void 					setMarginBottom(double _value) 											{ BM = _value; }

		public final void 					setBorder(double _left, double _right, double _top, double _bottom) 	{ LB = _left; RB = _right; TB = _top; BB = _bottom; }
		public final Edges2D				getBorderEdges() 														{ return new SimpleEdges2D(LB, RB, TB, BB); }
		public final void 					setBorderLeft(double _value)   											{ LB = _value; }
		public final void 					setBorderRight(double _value)  											{ RB = _value; }
		public final void 					setBorderTop(double _value)    											{ TB = _value; }
		public final void 					setBorderBottom(double _value) 											{ BB = _value; }

		public final void 					setPadding(double _left, double _right, double _top, double _bottom)	{ LP = _left; RP = _right; TP = _top; BP = _bottom; }
		public final Edges2D				getPaddingEdges() 														{ return new SimpleEdges2D(LP, RP, TP, BP); }
		public final void 					setPaddingLeft(double _value)   										{ LP = _value; }
		public final void 					setPaddingRight(double _value)  										{ RP = _value; }
		public final void 					setPaddingTop(double _value)    										{ TP = _value; }
		public final void 					setPaddingBottom(double _value) 										{ BP = _value; }

		public final BoundingBox.TwoDims 	getOuterBounds() 														{ return BoundingBoxes.of(X - LM, Y - TM, W + RM, H + BM); }
		public final BoundingBox.TwoDims 	getBounds() 															{ return BoundingBoxes.of(X, Y, W, H); }
		public final BoundingBox.TwoDims 	getInnerBounds() 														{ return BoundingBoxes.of(X + LB, Y + TB, W - LB - RB, H - TB - BB); }
		public final BoundingBox.TwoDims 	getContentBounds() 														{ return BoundingBoxes.of(X + LB + LP, Y + TB + TP, W - LB - LP - RB - RP, H - TB - TP - BB - BP); }

		final Point2D 						Pb2d(double _x, double _y) {
			if(_x > 0 && _x < W && _y > 0 && _y < H)
				return Points.of(X + _x, Y + _y);
			return Points.NaN2;
		}
		final double  						Xb2d(double _x) {
			if(_x > 0 && _x < W)
				return X + _x;
			return Double.NaN;
		}
		final double  						Yb2d(double _y) {
			if(_y > 0 && _y < H)
				return Y + _y;
			return Double.NaN;
		}

		final Point2D 						Pd2b(double _x, double _y) {
			if(_x > X && _x < X + W && _y > Y && _y < Y + H)
				return Points.of(_x - X, _y - Y);
			return Points.NaN2;
		}
		final double  						Xd2b(double _x) {
			if(_x > X && _x < X + W)
				return X + _x;
			return Double.NaN;
		}
		final double  						Yd2b(double _y) {
			if(_y > Y && _y < Y + H)
				return Y + _y;
			return Double.NaN;
		}

		final Point2D 						Pc2d(double _x, double _y) {
			if(_x > 0 && _x < W - LB - RB - LP - RP && _y > 0 && _y < H - TB - TB - BP - BP)
				return Points.of(X + LB + LP + _x, Y + TB + TP + _y);
			return Points.NaN2;
		}
		final double  						Xc2d(double _x) {
			if(_x > 0 && _x < W - LB - RB - LP - RP)
				return X + LB + LP + _x;
			return Double.NaN;
		}
		final double  						Yc2d(double _y) {
			if(_y > 0 && _y < H - TB - TB - BP - BP)
				return Y + TB + TP + _y;
			return Double.NaN;
		}

	}
	public final class 		  Box extends BoxAttributes {
		BoxConfig config;
		BoxStyle  style;

		Box(double _x, double _y, double _w, double _h) {
			super(_x, _y, _w, _h);
			config = new BoxConfig();
			style  = new BoxStyle();
		}

		public final BoxConfig config() {
			return config;
		}
		public final BoxStyle  style() {
			return style;
		}

	}
	
	final class        		  BoxDesign { boolean useClearMethod = false;

		final void clear(Drawer _drawer) {
			if(useClearMethod)
				_drawer.clear(box().style().getClearBrush().toColor());
			else
			_drawer.drawRectangle(box().getBounds().getX(), 
								  box().getBounds().getY(), 
								  box().getBounds().getWidth(), 
								  box().getBounds().getHeight(), 
								  box().style().getClearBrush().toColor());
		}
		final void fillBorder(Drawer _drawer) {
/**/
			_drawer.drawRectangle(box().getBounds().getX(), 
								  box().getBounds().getY(), 
								  box().getBounds().getWidth(), 
								  box().getBounds().getHeight(), 
								  box().style().getBorderBrush());
/*/
			if(LB > 0) 				_drawer.drawRectangle(X,          Y + TB,     LB,          H - LB - TB, getBorderBrush());
			if(TB > 0) 				_drawer.drawRectangle(X + LB,     Y,          W - RB - LB, Y + TB, 		getBorderBrush());
			if(RB > 0) 				_drawer.drawRectangle(X + W - RB, Y + LB,     LB,          H - LB - TB, getBorderBrush());
			if(BB > 0) 				_drawer.drawRectangle(X + LB,     Y + H - BB, W - LB - RB, H, 			getBorderBrush());

			if(LB > 0 && TB > 0) 	_drawer.drawRectangle(X,          Y,          LB,          TB, 			getBorderBrush());
			if(RB > 0 && TB > 0)	_drawer.drawRectangle(X + W - RB, Y,          RB,          TB, 			getBorderBrush());
			if(LB > 0 && BB > 0) 	_drawer.drawRectangle(X,          Y + H - BB, LB,          BB, 			getBorderBrush());
			if(RB > 0 && BB > 0)	_drawer.drawRectangle(X + W - RB, Y + H - BB, RB,          BB, 			getBorderBrush());
/**/
		}
		final void fillInnerBackground(Drawer _drawer) {
			_drawer.drawRectangle(box().getInnerBounds().getLeft(), 
								  box().getInnerBounds().getBottom(), 
								  box().getInnerBounds().getWidth(), 
								  box().getInnerBounds().getHeight(), 
								  box().style().getInnerBrush());
		}
		final void fillContentBackground(Drawer _drawer) {
			_drawer.drawRectangle(box().getContentBounds().getLeft(), 
								  box().getContentBounds().getBottom(), 
								  box().getContentBounds().getWidth(), 
								  box().getContentBounds().getHeight(), 
								  box().style().getContentBrush());
		}

	}

	Box 		boxProperties;
	BoxDesign   boxDesign;

	public BoxDesignAdapter(double _x, double _y, double _w, double _h) {
		super();
		boxProperties = new Box(_x, _y, _w, _h);
		boxDesign     = new BoxDesign();
	}
	
	public Box  box() {
		return boxProperties;
	}

	@Override
	public void draw(Drawer _drawer) {
		boxDesign.clear(_drawer);

		if(box().config().drawBox) {
			boxDesign.fillBorder(_drawer);
			boxDesign.fillInnerBackground(_drawer);
			boxDesign.fillContentBackground(_drawer);
		}
	}

}
