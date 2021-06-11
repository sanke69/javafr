package fr.java.sdk.draw.design;

import fr.java.draw.Drawer;
import fr.java.draw.styles.LineStyle;
import fr.java.draw.styles.PointSkin;
import fr.java.draw.tools.Brush;
import fr.java.draw.tools.Colors;
import fr.java.draw.tools.Font;
import fr.java.draw.tools.Fonts;
import fr.java.math.geometry.BoundingBox;
import fr.java.math.geometry.Dimension;
import fr.java.math.geometry.plane.Point2D;
import fr.java.maths.Numbers;
import fr.java.maths.geometry.types.BoundingBoxes;
import fr.java.maths.geometry.types.Points;
import fr.java.sdk.draw.tools.DrawUtils;
import fr.java.sdk.lang.Asserts;
import fr.java.utils.Strings;

public class GraphDesignAdapter extends BoxDesignAdapter {

	public static final class   GraphStyle {
		public static final Brush DEFAULT_GRAPH_BG_BRUSH     = Brush.of(Colors.BLACK.opacity(0.5));
		public static final Brush DEFAULT_GRAPH_CORNER_BRUSH = Brush.of(Colors.WHITE);
		public static final Brush DEFAULT_GRAPH_AXIS_BRUSH   = Brush.of(Colors.WHITE);

		public static final Font  DEFAULT_TITLE_FONT         = Font.of(14);
		public static final Font  DEFAULT_SUB_TITLE_FONT     = Font.of(12);
		public static final Font  DEFAULT_X_LABEL_FONT       = Font.of(10);
		public static final Font  DEFAULT_X_TICKS_FONT       = Font.of(8);
		public static final Font  DEFAULT_Y_LABEL_FONT       = Font.of(10);
		public static final Font  DEFAULT_Y_TICKS_FONT       = Font.of(8);

		public static final LineStyle DEFAULT_ZERO_AXIS_LINE 		= LineStyle.of(0.5d, Brush.of(Colors.FORESTGREEN), 3, 2, 3);
		public static final LineStyle DEFAULT_CENTERED_GRID_LINE    = LineStyle.of(1.5d, Brush.of(Colors.FORESTGREEN));
		public static final LineStyle DEFAULT_CENTERED_SUBGRID_LINE = LineStyle.of(0.5d, Brush.of(Colors.FORESTGREEN));

		Brush backgroundBrush;
		Brush cornerBrush;

		Font  titleFont, subTitleFont, xLabelFont, yLabelFont, xTicksFont, yTicksFont;

		GraphStyle() {
			super();
		}

		public void setBackgroundBrush(Brush _brush) {
			backgroundBrush = _brush;
		}
		public Brush getBackgroundBrush() {
			return backgroundBrush != null ? backgroundBrush : DEFAULT_GRAPH_BG_BRUSH;
		}

		public Brush getCornerBrush() {
			return cornerBrush != null ? cornerBrush : DEFAULT_GRAPH_CORNER_BRUSH;
		}

		public Brush getAxisBrush() {
			return cornerBrush != null ? cornerBrush : DEFAULT_GRAPH_CORNER_BRUSH;
		}

		public Font	getTitleFont() {
			return titleFont != null ? titleFont : DEFAULT_TITLE_FONT;
		}
		public Font getSubTitleFont() {
			return subTitleFont != null ? subTitleFont : DEFAULT_SUB_TITLE_FONT;
		}

		public Font	getLabelXFont() {
			return xLabelFont != null ? xLabelFont : DEFAULT_X_LABEL_FONT;
		}
		public Font	getLabelYFont() {
			return yLabelFont != null ? yLabelFont : DEFAULT_Y_LABEL_FONT;
		}

		public Font	getTicksXFont() {
			return xTicksFont != null ? xTicksFont : DEFAULT_X_TICKS_FONT;
		}
		public Font	getTicksYFont() {
			return yTicksFont != null ? yTicksFont : DEFAULT_Y_TICKS_FONT;
		}


	}
	public static final class   GraphConfig {
		boolean useTitle,    			useSubTitle;
		boolean	useCenteredGridX,   	useCenteredGridY;
		boolean	useCenteredSubGridX,   	useCenteredSubGridY;

		boolean	useLabelX,   			useLabelY;
		boolean	useBottomAxisX,    		useLeftAxisY;
		boolean	useTopAxisX,    		useRightAxisY;
		boolean	useZeroAxisX,    		useZeroAxisY;
		boolean	useTicksX,   			useTicksY;
		boolean	useGridX,    			useGridY;
		boolean	useSubGridX, 			useSubGridY;

		boolean	configChanged = true;

		GraphConfig() {
			super();
		}
		GraphConfig(boolean _allTrue) {
			super();
		}

		public final void useTitle(boolean _true) 				{ useTitle            = _true; configChanged = true; }
		public final void useSubTitle(boolean _true) 			{ useSubTitle         = _true; configChanged = true; }

		public final void useCenteredGridX(boolean _true) 		{ useCenteredGridX    = _true; configChanged = true; }
		public final void useCenteredSubGridX(boolean _true) 	{ useCenteredSubGridX = _true; configChanged = true; }
		public final void useCenteredGridY(boolean _true) 		{ useCenteredGridY    = _true; configChanged = true; }
		public final void useCenteredSubGridY(boolean _true) 	{ useCenteredSubGridY = _true; configChanged = true; }

		public final void useLabelX(boolean _true) 				{ useLabelX           = _true; configChanged = true; }
		public final void useAxisX(boolean _true) 				{ useBottomAxisX      = _true; configChanged = true; }
		public final void useTicksX(boolean _true) 				{ useTicksX           = _true; configChanged = true; }
		public final void useBottomAxisX(boolean _true) 		{ useBottomAxisX      = _true; configChanged = true; }
		public final void useTopAxisX(boolean _true) 			{ useTopAxisX         = _true; configChanged = true; }
		public final void useGridX(boolean _true) 				{ useGridX            = _true; configChanged = true; }
		public final void useSubGridX(boolean _true) 			{ useSubGridX         = _true; configChanged = true; }
		
		public final void useLabelY(boolean _true) 				{ useLabelY           = _true; configChanged = true; }
		public final void useAxisY(boolean _true) 				{ useLeftAxisY        = _true; configChanged = true; }
		public final void useTicksY(boolean _true) 				{ useTicksY           = _true; configChanged = true; }
		public final void useLeftAxisY(boolean _true) 			{ useLeftAxisY        = _true; configChanged = true; }
		public final void useRightAxisY(boolean _true) 			{ useRightAxisY       = _true; configChanged = true; }
		public final void useGridY(boolean _true) 				{ useGridY            = _true; configChanged = true; }
		public final void useSubGridY(boolean _true) 			{ useSubGridY         = _true; configChanged = true; }

		public void enableAll() {
			useTitle            = true;  useSubTitle         = true;
			useCenteredGridX    = true;  useCenteredGridY    = true;
			useCenteredSubGridX = true;  useCenteredSubGridY = true;
			useLabelX           = true;  useLabelY           = true;
			useBottomAxisX      = true;  useLeftAxisY        = true;
			useTopAxisX         = true;  useRightAxisY       = true;
			useZeroAxisX        = true;  useZeroAxisY        = true;
			useTicksX           = true;  useTicksY           = true;
			useGridX            = true;  useGridY            = true;
			useSubGridX         = true;  useSubGridY         = true;
			configChanged = true;
		}
		public void disableAll() {
			useTitle            = false; useSubTitle         = false;
			useCenteredGridX    = false; useCenteredGridY    = false;
			useCenteredSubGridX = false; useCenteredSubGridY = false;
			useLabelX           = false; useLabelY           = false;
			useBottomAxisX      = false; useLeftAxisY        = false;
			useTopAxisX         = false; useRightAxisY       = false;
			useZeroAxisX        = false; useZeroAxisY        = false;
			useTicksX           = false; useTicksY           = false;
			useGridX            = false; useGridY            = false;
			useSubGridX         = false; useSubGridY         = false;
			configChanged = true;
		}

	}
	
	class 						GraphAttributes {
		// 
		double 				LS, TS, RS, BS;

		BoundingBox.TwoDims domain;

		GraphAttributes() {
			super();
			LS = TS = RS = BS = 0d;

			domain = BoundingBoxes.fromBounds(-1,  -1,  1,  1);
		}

		public final BoundingBox.TwoDims 	getBounds() {
			return BoundingBoxes.of(box().getContentBounds().getX() + LS, 
									box().getContentBounds().getY() + TS, 
									box().getContentBounds().getWidth() - LS - RS, 
									box().getContentBounds().getHeight() - TS - BS);
		}

		public final void 					setDomain(BoundingBox.TwoDims _domain) {
			domain = _domain;
		}
		public final BoundingBox.TwoDims 	getDomain() {
			return domain;
		}

		final void 							clearMargin() {
			LS = TS = RS = BS = 0d;
		}
		final void 							computeMargin(Drawer _drawer) {
			clearMargin();

			if(graph().config().useTitle)
				if(graph().getTitle() != null) {
					Font titleFont = graph().style().getTitleFont();
					TS += Fonts.getHeight(titleFont) + 12d;
				} else {
					TS += 25;
				}
			if(graph().config().useSubTitle)
				if(graph().getSubTitle() != null) {
					Font subTitleFont = graph().style().getSubTitleFont();

					TS += Fonts.getHeight(subTitleFont) + 6d;
				} else {
					TS += 25;
				}

			if(graph().config().useLabelX)
				if(graph().getLabelX() != null) {
					String xLabel     = graph().getLabelX();
					Font   xLabelFont = graph().style().getLabelXFont();

					BS += Fonts.getHeight(xLabelFont) + 12d;
					RS += Fonts.getWidth(xLabel, xLabelFont) / 2d + 6;

				} else {
					BS += 32;
					RS += 24;
				}

			if(graph().config().useBottomAxisX) {
				Font   xTicksFont = graph().style().getTicksXFont();

				BS += Fonts.getHeight(xTicksFont) + 12;
				RS += RS == 0 ? Fonts.getWidth("000000", xTicksFont) / 2d : 0d;
			}

			if(graph().config().useLabelY)
				if(graph().getLabelX() != null) {
					String yLabel     = graph().getLabelY();
					Font   yLabelFont = graph().style().getLabelYFont();

					TS += TS == 0 ? Fonts.getHeight(yLabelFont) + 20 : 0;
					LS += Fonts.getWidth(yLabel, yLabelFont)  + 12;

				} else {
					TS += 20;
					LS += 12;
				}

			if(graph().config().useLeftAxisY) {
				Font   yTicksFont = graph().style().getTicksXFont();

				double minWidth = Fonts.getWidth("000000", yTicksFont) + 5 + 10;	// Text + Ticks + Space

				BS += Fonts.getHeight(yTicksFont) / 2d;
				LS += LS < minWidth ? minWidth : 0d;
			}

			graph().config().configChanged = false;
		}

		final double 						QWg()  		{ return getDomain().getWidth()  / getBounds().getWidth(); }
		final double 						QHg() 		{ return getDomain().getHeight() / getBounds().getHeight(); }

		public final Point2D 				Pg2d(double _x, double _y) {
			if(_x > getDomain().getLeft() && _x < getDomain().getRight() && _y > getDomain().getBottom() && _y < getDomain().getTop())
				return Points.of(
						getBounds().getLeft() + ( _x - getDomain().getMinX() ) / QWg(), 
						getBounds().getTop()  + ( getDomain().getMinY() - _y ) / QHg());
			return Points.NaN2;
		}
		public final Point2D[] 				Pg2d(double[] _x, double[] _y) {
			Asserts.assertTrue(_x.length == _y.length);

			Point2D[] pts = new Point2D[_x.length];

			for(int i = 0;i < _x.length; ++i)
				if(_x[i] > getDomain().getLeft() && _x[i] < getDomain().getRight() && _y[i] > getDomain().getBottom() && _y[i] < getDomain().getTop())
					pts[i] = Points.of(
							getBounds().getLeft() + ( _x[i] - getDomain().getMinX() ) / QWg(), 
							getBounds().getTop()  + ( getDomain().getMinY() - _y[i] ) / QHg());
				else
					pts[i] = Points.NaN2;
			
			return pts;
		}
		public final double  				Xg2d(double _x) {
			if(_x >= getDomain().getLeft() && _x <= getDomain().getRight())
				return getBounds().getLeft() + ( _x - getDomain().getMinX() ) / QWg();
			return Double.NaN;
		}
		public final double[]  				Xg2d(double[] _x) {
			double[] X = new double[_x.length];

			for(int i = 0; i < _x.length; ++i)
				if(_x[i] >= getDomain().getLeft() && _x[i] <= getDomain().getRight())
					X[i] = getBounds().getLeft() + ( _x[i] - getDomain().getMinX() ) / QWg();
				else
					X[i] = Double.NaN;

			return X;
		}
		public final double  				dXg2d(double _dx) {
			return _dx / QWg();
		}
		public final double  				Yg2d(double _y) {
			if(_y >= getDomain().getBottom() && _y <= getDomain().getTop())
				return getBounds().getTop()  + ( getDomain().getMinY() - _y ) / QHg();
			return Double.NaN;
		}
		public final double[]  				Yg2d(double[] _y) {
			double[] Y = new double[_y.length];

			for(int i = 0; i < _y.length; ++i)
				if(_y[i] >= getDomain().getBottom() && _y[i] <= getDomain().getTop())
					Y[i] = getBounds().getTop()  + ( getDomain().getMinY() - _y[i] ) / QHg();
				else
					Y[i] = Double.NaN;

			return Y;
		}
		public final double  				dYg2d(double _dy) {
				return _dy / QHg();
		}

		public final Point2D 				Pd2g(double _x, double _y) {
			if(_x >= getBounds().getLeft() && _x <= getBounds().getRight() && _y >= getBounds().getBottom() && _y <= getBounds().getTop())
				return Points.of(_x - box().getBounds().getX(), _y - box().getBounds().getY());
			return Points.NaN2;
		}
		public final Point2D[] 				Pd2g(double[] _x, double[] _y) {
			Asserts.assertTrue(_x.length == _y.length);

			Point2D[] pts = new Point2D[_x.length];

			for(int i = 0;i < _x.length; ++i)
				if(_x[i] >= getBounds().getLeft() && _x[i] <= getBounds().getRight() && _y[i] >= getBounds().getBottom() && _y[i] <= getBounds().getTop())
					pts[i] = Points.of(_x[i] - box().getBounds().getX(), _y[i] - box().getBounds().getY());
				else
					pts[i] = Points.NaN2;
			
			return pts;
		}
		public final double  				Xd2g(double _x) {
			if(_x >= getBounds().getLeft() && _x <= getBounds().getRight())
				return getDomain().getMinX() + ( _x - getBounds().getMinX() ) * QWg();
			return Double.NaN;
		}
		public final double[]  				Xd2g(double[] _x) {
			double[] X = new double[_x.length];

			for(int i = 0; i < _x.length; ++i)
				if(_x[i] >= getBounds().getLeft() && _x[i] <= getBounds().getRight())
					X[i] = getDomain().getMinX() + ( _x[i] - getBounds().getMinX() ) * QWg();
				else
					X[i] = Double.NaN;

			return X;
		}
		public final double  				Yd2g(double _y) {
			if(_y >= getBounds().getBottom() && _y <= getBounds().getTop())
				return getDomain().getMinY() + ( getBounds().getTop() - _y ) * QHg();
			return Double.NaN;
		}
		public final double[]  				Yd2g(double[] _y) {
			double[] Y = new double[_y.length];

			for(int i = 0; i < _y.length; ++i)
				if(_y[i] >= getBounds().getBottom() && _y[i] <= getBounds().getTop())
					Y[i] = getDomain().getMinY() + ( getBounds().getTop() - _y[i] ) * QHg();
				else
					Y[i] = Double.NaN;

			return Y;
		}

	}
	class 						GraphProperties extends GraphAttributes {
		String  title, subTitle;
		String  xLabel, yLabel;

		double  xGrid = 1d, xSubGrid = 0.25;
		double  yGrid = 1d, ySubGrid = 0.25;

		GraphProperties() {
			super();
		}

		public final void 	setTitle(String _title) {
			title = _title;
			graph().config().useTitle( title != null );
		}
		public final void 	setTitle(String _title, String _subTitle) {
			title = _title;
			subTitle = _subTitle;
			graph().config().useTitle( title != null );
			graph().config().useSubTitle( subTitle != null );
		}
		public final String getTitle() {
			return title;
		}
		public final void 	setSubTitle(String _subTitle) {
			subTitle = _subTitle;
			graph().config().useSubTitle( subTitle != null );
		}
		public final String getSubTitle() {
			return subTitle;
		}

		public final void 	setLabelX(String _text) {
			xLabel = _text;
			graph().config().useLabelX( xLabel != null );
			graph().config().useTicksX( xLabel != null );
			graph().config().useBottomAxisX( xLabel != null );
		}
		public final String getLabelX() {
			return xLabel;
		}

		public final void 	setLabelY(String _text) {
			yLabel = _text;
			graph().config().useLabelY( yLabel != null );
			graph().config().useTicksY( xLabel != null );
			graph().config().useLeftAxisY( xLabel != null );
		}
		public final String getLabelY() {
			return yLabel;
		}

	}
	public final class 			Graph extends GraphProperties {
		GraphConfig config;
		GraphStyle style;
		
		Graph() {
			super();
			config = new GraphConfig();
			style = new GraphStyle();
		}

		public final GraphConfig config() {
			return config;
		}
		public final GraphStyle  style() {
			return style;
		}

	}
	
	public final class 			GraphDesign {

		public final void fillGraphBackground(Drawer _drawer) {
			BoundingBox.TwoDims graphBounds = graph().getBounds();

			_drawer.drawRectangle(graphBounds.getX(), 
								  graphBounds.getY(), 
								  graphBounds.getWidth(), 
								  graphBounds.getHeight(), 
								  graph().style().getBackgroundBrush());
		}

		public final void drawTitle(Drawer _drawer) {
			String text = graph().getTitle();
			Font   font = graph().style().getTitleFont();
			
			Dimension.TwoDims t_dims = _drawer.getTextDimension(text, font);

			double textWidth    = t_dims.getWidth();
			double textHeight   = t_dims.getHeight();
			double contentWidth = box().getContentBounds().getWidth();

			double x = box().Xc2d( (contentWidth - textWidth) / 2d );
			double y = box().Yc2d( textHeight + 3d );

			_drawer.drawString(text, x, y, font);

			if(graph().getSubTitle() != null) {
				double titleHeight = textHeight + 2d;
				
				text = graph().getSubTitle();
				font = graph().style().getSubTitleFont();
				
				t_dims = _drawer.getTextDimension(text, font);
	
				textWidth    = t_dims.getWidth();
				textHeight   = t_dims.getHeight();
				contentWidth = box().getContentBounds().getWidth();
	
				x = box().Xc2d( (contentWidth - textWidth) / 2d );
				y = box().Yc2d( titleHeight + textHeight /*+ 6d*/ );
	
				_drawer.drawString(text, x, y, font);
			}
		}

		public final void drawGraphCorner(Drawer _drawer) {
			BoundingBox.TwoDims gBox  = graph().getBounds();
			Brush               brush = graph().style().getCornerBrush();
			
			double L = gBox.getMinX(), R = gBox.getMaxX(), T = gBox.getMinY(), B = gBox.getMaxY();

			_drawer.drawLine(L, T, L, T + 5, brush);
			_drawer.drawLine(L, T, L + 5, T, brush);

			_drawer.drawLine(R, T, R, T + 5, brush);
			_drawer.drawLine(R, T, R - 5, T, brush);

			_drawer.drawLine(L, B, L, B - 5, brush);
			_drawer.drawLine(L, B, L + 5, B, brush);

			_drawer.drawLine(R, B, R, B - 5, brush);
			_drawer.drawLine(R, B, R - 5, B, brush);
		}
		
		public final void drawLabelX(Drawer _drawer) {
			String text = graph().getLabelX();
			Font   font = graph().style().getLabelXFont();
			
			Dimension.TwoDims t_dims = _drawer.getTextDimension(text, font);

			double textWidth     = t_dims.getWidth();
			double textHeight    = t_dims.getHeight();
			double contentWidth  = box().getContentBounds().getWidth();
			double contentHeight = box().getContentBounds().getHeight();

			double x = box().Xc2d( contentWidth - textWidth - 6d );
			double y = box().Yc2d( contentHeight - textHeight + 3d );

			_drawer.drawString(text, x, y, font);
		}
		public final void drawTopAxisX(Drawer _drawer) {
			// ABSCISSA
			BoundingBox.TwoDims gBox = graph().getBounds();
//			_drawer.drawLine( gBox.getLeft(), gBox.getTop(), gBox.getRight(), gBox.getTop(), graphStyle.getAxisBrush());

			int    N        = 10;
			double X0       = gBox.getLeft(),
				   X1       = gBox.getRight();
			double dX       = (X1 - X0) / N;
			double Y        = gBox.getMinY();
			
			for(int i = 0; i <= N; ++i) {
				String            label  = Strings.getFormattedValue(graph().getDomain().getMinX() + (double)((double)i/(double)N) * graph().getDomain().getWidth());
				Dimension.TwoDims t_dims = _drawer.getTextDimension(label, graph().style().getTicksXFont());

				// Draw Ticks
				_drawer.drawLine(X0 + i * dX, Y, X0 + i * dX, Y - 5, graph().style().getAxisBrush());
				// Draw Ticks Value
				_drawer.drawString(label, 
									X0 + i * dX - t_dims.getWidth() / 2d, 
									Y - 5 - t_dims.getHeight(), 
									graph().style().getTicksXFont());
			}
		}
		public final void drawBottomAxisX(Drawer _drawer) {
			// ABSCISSA
			BoundingBox.TwoDims gBox = graph().getBounds();
//			_drawer.drawLine( gBox.getLeft(), gBox.getTop(), gBox.getRight(), gBox.getTop(), graphStyle.getAxisBrush());

			int    N        = 10;
			double X0       = gBox.getLeft(),
				   X1       = gBox.getRight();
			double dX       = (X1 - X0) / N;
			double Y        = gBox.getMaxY();
			
			for(int i = 0; i <= N; ++i) {
				String            label  = Strings.getFormattedValue(graph().getDomain().getMinX() + (double)((double)i/(double)N) * graph().getDomain().getWidth());
				Dimension.TwoDims t_dims = _drawer.getTextDimension(label, graph().style().getTicksXFont());

				// Draw Ticks
				_drawer.drawLine(X0 + i * dX, Y, X0 + i * dX, Y + 5, graph().style().getAxisBrush());
				// Draw Ticks Value
				_drawer.drawString(label, 
									X0 + i * dX - t_dims.getWidth() / 2d, 
									Y + 10 + t_dims.getHeight(), 
									graph().style().getTicksXFont());
			}
		}
		public final void drawZeroAxisX(Drawer _drawer) {
			if(!(graph().getDomain().getMinX() < 0 && graph().getDomain().getMaxX() > 0))
				return ;

			double x_l = graph().getBounds().getLeft();
			double x_r = graph().getBounds().getRight();
			double y_0 = graph().Yg2d(0.0);

			double[] Xarrow = new double[] {x_r, x_r - 7, x_r - 7}, 
					 Yarrow = new double[] {y_0, y_0 + 3, y_0 - 3};

			_drawer.drawLine(x_l, y_0, x_r, y_0, GraphStyle.DEFAULT_ZERO_AXIS_LINE);
			_drawer.drawPolygon(Xarrow, Yarrow, GraphStyle.DEFAULT_GRAPH_AXIS_BRUSH);
		}

		public final void drawCenteredGridHor(Drawer _drawer) {
			int nbSlices    = 4;
			int nbSubSlices = 3 * nbSlices;

			double Xc = graph().getBounds().getCenterX();
			double Y0 = graph().getBounds().getTop();
			double Y1 = graph().getBounds().getBottom();

			double step    = graph().getBounds().getWidth() / (2 * nbSlices);
			double subStep = graph().getBounds().getWidth() / (2 * nbSubSlices);

			_drawer.drawLine(Xc, Y0, Xc, Y1, graph().config().useCenteredGridX ? GraphStyle.DEFAULT_CENTERED_GRID_LINE : GraphStyle.DEFAULT_CENTERED_SUBGRID_LINE);
			if(graph().config().useCenteredGridX)
			for(int i = 1; i <= nbSlices; ++i) {
				_drawer.drawLine(Xc - i * step, Y0, Xc - i * step, Y1, GraphStyle.DEFAULT_CENTERED_GRID_LINE);
				_drawer.drawLine(Xc + i * step, Y0, Xc + i * step, Y1, GraphStyle.DEFAULT_CENTERED_GRID_LINE);
			}
			if(graph().config().useCenteredSubGridX)
			for(int i = 1; i < nbSubSlices; ++i) {
				_drawer.drawLine(Xc - i * subStep, Y0, Xc - i * subStep, Y1, GraphStyle.DEFAULT_CENTERED_SUBGRID_LINE);
				_drawer.drawLine(Xc + i * subStep, Y0, Xc + i * subStep, Y1, GraphStyle.DEFAULT_CENTERED_SUBGRID_LINE);
			}
		}
		public final void drawCenteredGridVer(Drawer _drawer) {
			int nbSlices    = 4;
			int nbSubSlices = 3 * nbSlices;

			double Yc = graph().getBounds().getCenterY();
			double X0 = graph().getBounds().getLeft();
			double X1 = graph().getBounds().getRight();

			double step    = graph().getBounds().getHeight() / (2 * nbSlices);
			double subStep = graph().getBounds().getHeight() / (2 * nbSubSlices);

			_drawer.drawLine(X0, Yc, X1, Yc, graph().config().useCenteredGridY ? GraphStyle.DEFAULT_CENTERED_GRID_LINE : GraphStyle.DEFAULT_CENTERED_SUBGRID_LINE);
			if(graph().config().useCenteredGridY)
			for(int i = 1; i <= nbSlices; ++i) {
				_drawer.drawLine(X0, Yc - i * step, X1, Yc - i * step, GraphStyle.DEFAULT_CENTERED_GRID_LINE);
				_drawer.drawLine(X0, Yc + i * step, X1, Yc + i * step, GraphStyle.DEFAULT_CENTERED_GRID_LINE);
			}
			if(graph().config().useCenteredSubGridY)
			for(int i = 1; i < nbSubSlices; ++i) {
				_drawer.drawLine(X0, Yc - i * subStep, X1, Yc - i * subStep, GraphStyle.DEFAULT_CENTERED_SUBGRID_LINE);
				_drawer.drawLine(X0, Yc + i * subStep, X1, Yc + i * subStep, GraphStyle.DEFAULT_CENTERED_SUBGRID_LINE);
			}
		}
		public final void drawGridX(Drawer _drawer) {
			double nextRamp = Numbers.getNextMultiple(graph().getDomain().getLeft(), graph().xGrid);

			double Y0 = graph().getBounds().getMinY();
			double Y1 = graph().getBounds().getMaxY();

			while(nextRamp < graph().getDomain().getRight()) {
				double X = graph().Xg2d(nextRamp);
				_drawer.drawLine(X, Y0, X, Y1, GraphStyle.DEFAULT_CENTERED_SUBGRID_LINE);
				nextRamp += graph().xGrid;
			}
		}
		public final void drawSubGridX(Drawer _drawer) {
			double nextRamp = Numbers.getNextMultiple(graph().getDomain().getLeft(), graph().xSubGrid);

			double Y0 = graph().getBounds().getTop();
			double Y1 = graph().getBounds().getBottom();

			while(nextRamp < graph().getDomain().getRight()) {
				double X = graph().Xg2d(nextRamp);
				_drawer.drawLine(X, Y0, X, Y1, GraphStyle.DEFAULT_CENTERED_SUBGRID_LINE);
				nextRamp += graph().xSubGrid;
			}
		}

		public final void drawLabelY(Drawer _drawer) {
			Dimension.TwoDims t_dims = _drawer.getTextDimension(graph().getLabelY(), graph().style().getLabelYFont());

			_drawer.drawString(graph().getLabelY(), 
								box().Xc2d(5), 
								graph().getBounds().getMinY() - t_dims.getHeight() - 5, 
								graph().style().getLabelYFont());
		}
		public final void drawLeftAxisY(Drawer _drawer) {
			// ORDINATE
			BoundingBox.TwoDims gBox = graph().getBounds();
			_drawer.drawLine( gBox.getLeft(), gBox.getTop(), gBox.getLeft(), gBox.getBottom(), graph().style().getAxisBrush());

			int    N        = 10;
			double Y0       = gBox.getMaxY(),
				   Y1       = gBox.getMinY();
			double dY       = (Y1 - Y0) / N;
			double X        = gBox.getLeft();
			
			for(int i = 0; i <= N; ++i) {
				// Draw Ticks
				_drawer.drawLine(X, Y0 + i * dY, X - 5, Y0 + i * dY, graph().style().getAxisBrush());

				// Draw Ticks Value
				String            label  = DrawUtils.getFormattedValue(graph().getDomain().getMinY() + (double)((double)i/(double)N) * graph().getDomain().getHeight());
				Dimension.TwoDims t_dims = _drawer.getTextDimension(label, graph().style().getTicksYFont());

				_drawer.drawString(label, 
									X - 10 - t_dims.getWidth(), 
									Y0 + i * dY + t_dims.getHeight() / 2d, 
									graph().style().getTicksYFont());
			}
		}
		public final void drawRightAxisY(Drawer _drawer) {
			// ORDINATE
			BoundingBox.TwoDims gBox = graph().getBounds();
			_drawer.drawLine( gBox.getLeft(), gBox.getTop(), gBox.getLeft(), gBox.getBottom(), graph().style().getAxisBrush());

			int    N        = 10;
			double Y0       = gBox.getMaxY(),
				   Y1       = gBox.getMinY();
			double dY       = (Y1 - Y0) / N;
			double X        = gBox.getRight();
			
			for(int i = 0; i <= N; ++i) {
				// Draw Ticks
				_drawer.drawLine(X, Y0 + i * dY, X + 5, Y0 + i * dY, graph().style().getAxisBrush());

				// Draw Ticks Value
				String            label  = DrawUtils.getFormattedValue(graph().getDomain().getMinY() + (double)((double)i/(double)N) * graph().getDomain().getHeight());
				Dimension.TwoDims t_dims = _drawer.getTextDimension(label, graph().style().getTicksYFont());

				_drawer.drawString(label, 
									X + 10, 
									Y0 + i * dY + t_dims.getHeight() / 2d, 
									graph().style().getTicksYFont());
			}
		}
		public final void drawZeroAxisY(Drawer _drawer) {
			if(!(graph().getDomain().getMinY() < 0 && graph().getDomain().getMaxY() > 0))
				return ;

			double y_t = graph().getBounds().getMinY();
			double y_b = graph().getBounds().getMaxY();
			double x_0 = graph().Xg2d(0.0);

			double[] Xarrow = new double[] {x_0, x_0 - 3, x_0 + 3}, 
					 Yarrow = new double[] {y_t, y_t + 7, y_t + 7};

			_drawer.drawLine(x_0, y_b, x_0, y_t, GraphStyle.DEFAULT_ZERO_AXIS_LINE);
			_drawer.drawPolygon(Xarrow, Yarrow, GraphStyle.DEFAULT_GRAPH_AXIS_BRUSH);
		}
		public final void drawGridY(Drawer _drawer) {
			double nextRamp = Numbers.getNextMultiple(graph().getDomain().getMinX(), graph().yGrid);

			double X0 = graph().getBounds().getMaxY();
			double X1 = graph().getBounds().getMinY();
			
			while(nextRamp < graph().getDomain().getRight()) {
				double Y = graph().Yg2d(nextRamp);
				_drawer.drawLine(X0, Y, X1, Y, GraphStyle.DEFAULT_CENTERED_GRID_LINE);
				nextRamp += graph().yGrid;
			}
		}
		public final void drawSubGridY(Drawer _drawer) {
			double nextRamp = Numbers.getNextMultiple(graph().getDomain().getLeft(), graph().ySubGrid);

			double X0 = graph().getBounds().getMaxY();
			double X1 = graph().getBounds().getMinY();

			while(nextRamp < graph().getDomain().getRight()) {
				double Y = graph().Yg2d(nextRamp);
				_drawer.drawLine(X0, Y, X1, Y, GraphStyle.DEFAULT_CENTERED_SUBGRID_LINE);
				nextRamp += graph().ySubGrid;
			}
		}
	}

	Graph				graphProperties;
	GraphDesign			graphDesign;

	public GraphDesignAdapter(double _x, double _y, double _w, double _h) {
		super(_x, _y, _w, _h);
		graphProperties = new Graph();
		graphDesign     = new GraphDesign();
	}

	public Graph graph() {
		return graphProperties;
	}

	@Override
	public void  draw(Drawer _drawer) {
		super.draw(_drawer);
//		graph().clearMargin();
		
		if(graph().config().configChanged)
		graph().computeMargin(_drawer);

		if(graph().config().useTitle)
			graphDesign.drawTitle(_drawer);

		if(graph().config().useLabelX)
			graphDesign.drawLabelX(_drawer);

		if(graph().config().useLabelY)
			graphDesign.drawLabelY(_drawer);

		if(graph().config().useBottomAxisX)
			graphDesign.drawBottomAxisX(_drawer);
		if(graph().config().useTopAxisX)
			graphDesign.drawTopAxisX(_drawer);
		if(graph().config().useLeftAxisY)
			graphDesign.drawLeftAxisY(_drawer);
		if(graph().config().useRightAxisY)
			graphDesign.drawRightAxisY(_drawer);

		graphDesign.fillGraphBackground(_drawer);

		graphDesign.drawGraphCorner(_drawer);

		if(graph().config().useCenteredGridX || graph().config().useCenteredSubGridX)
			graphDesign.drawCenteredGridHor(_drawer);
		if(graph().config().useCenteredGridY || graph().config().useCenteredSubGridY)
			graphDesign.drawCenteredGridVer(_drawer);

		if(graph().config().useGridX)
			graphDesign.drawGridX(_drawer);
		if(graph().config().useSubGridX)
			graphDesign.drawSubGridX(_drawer);
		if(graph().config().useGridY)
			graphDesign.drawGridY(_drawer);
		if(graph().config().useSubGridY)
			graphDesign.drawSubGridY(_drawer);

		if(graph().config().useZeroAxisX)
			graphDesign.drawZeroAxisX(_drawer);
		if(graph().config().useZeroAxisY)
			graphDesign.drawZeroAxisY(_drawer);
	}

	public void  drawPoint(Drawer _drawer, double _x_graph, double _y_graph) {
		_drawer.drawPoint(graph().Xg2d(_x_graph), graph().Yg2d(_y_graph), 1, Colors.YELLOW);
		_drawer.drawPoint(graph().Pg2d(_x_graph, _y_graph), 2, Colors.CYAN);
	}
	public void  drawPoint(Drawer _drawer, double _x_graph, double _y_graph, PointSkin _skin) {
		_drawer.drawPoint(graph().Xg2d(_x_graph), graph().Yg2d(_y_graph), _skin, 1d, Brush.of(Colors.CYAN));
		_drawer.drawPoint(graph().Pg2d(_x_graph, _y_graph), _skin, 2d, Brush.of(Colors.CYAN));
	}
	public void  drawPoint(Drawer _drawer, double _x_graph, double _y_graph, PointSkin _skin, double _ptSize) {
		_drawer.drawPoint(graph().Xg2d(_x_graph), graph().Yg2d(_y_graph), _skin, _ptSize, Brush.of(1d, Colors.YELLOW));
		_drawer.drawPoint(graph().Pg2d(_x_graph, _y_graph), _skin, _ptSize, Brush.of(2d, Colors.CYAN));
	}

}
