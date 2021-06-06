package fr.java.sdk.draw.design;

import fr.java.draw.Drawer;
import fr.java.draw.styles.PointSkin;
import fr.java.draw.tools.Brush;
import fr.java.draw.tools.Colors;
import fr.java.math.Interval;
import fr.java.math.geometry.plane.Point2D;
import fr.java.math.topology.Coordinate;
import fr.java.sdk.math.BoundingBoxes;
import fr.java.sdk.math.Points;

public class RadarDesignAdapter extends GraphDesignAdapter {

	public static final class RadarStyle {
		public static final Brush RADAR_BOUNDS_BRUSH         = Brush.of(   2d, Colors.GREEN);

		public static final Brush RADAR_ANGLE_TICKS_BRUSH    = Brush.of(   1d, Colors.LIGHTGRAY);
		public static final Brush RADAR_ANGLE_GRID_BRUSH     = Brush.of(   1d, Colors.LIME);
		public static final Brush RADAR_ANGLE_SUB_GRID_BRUSH = Brush.of(0.25d, Colors.LIME);

		public static final Brush RADAR_RANGE_TICKS_BRUSH    = Brush.of(   1d, Colors.LIGHTGRAY);
		public static final Brush RADAR_RANGE_GRID_BRUSH     = Brush.of(   3d, Colors.LIGHTGREEN);
		public static final Brush RADAR_RANGE_SUB_GRID_BRUSH = Brush.of( 0.5d, Colors.LIGHTCORAL);

		public static final Brush TARGET_BRUSH               = Brush.of( 0.5d, Colors.LIGHTCORAL);

		Brush boundsBrush, targetBrush;
		Brush rangeTicksBrush, rangeGridBrush, rangeSubGridBrush;
		Brush angleTicksBrush, angleGridBrush, angleSubGridBrush;

		public RadarStyle() {
			super();
		}
		
		public Brush getBoundBrush() {
			return boundsBrush == null ? RADAR_BOUNDS_BRUSH : boundsBrush;
		}

		public Brush getRangeTicksBrush() {
			return rangeTicksBrush == null ? RADAR_RANGE_TICKS_BRUSH : rangeTicksBrush;
		}
		public Brush getRangeGridBrush() {
			return rangeGridBrush == null ? RADAR_RANGE_GRID_BRUSH : rangeGridBrush;
		}
		public Brush getRangeSubGridBrush() {
			return rangeSubGridBrush == null ? RADAR_RANGE_SUB_GRID_BRUSH : rangeSubGridBrush;
		}
		public Brush getAngleTicksBrush() {
			return angleTicksBrush == null ? RADAR_ANGLE_TICKS_BRUSH : angleTicksBrush;
		}
		public Brush getAngleGridBrush() {
			return angleGridBrush == null ? RADAR_ANGLE_GRID_BRUSH : angleGridBrush;
		}
		public Brush getAngleSubGridBrush() {
			return angleSubGridBrush == null ? RADAR_ANGLE_SUB_GRID_BRUSH : angleSubGridBrush;
		}

		public Brush getTargetBrush() {
			return targetBrush == null ? TARGET_BRUSH : targetBrush;
		}
	}
	public static final class RadarConfig {
		boolean useAngularGrid, useRangeGrid;
		
	}

	class 					  RadarAttributes {
		RadarStyle style;

		Interval<Double> range;
		double           openAngle;

		double           rangeGrid, rangeSubGrid;
		double           angleGrid, angleSubGrid;

		RadarAttributes() {
			super();
			
			style = new RadarStyle();
			range     = Interval.of(0d, 100d);
			openAngle = 90;
		}
		
		public RadarStyle style() {
			return style;
		}

		void configureGraphDomain() {
			double left, right, top, bottom;

			top    = radar().getRange().getMax();
			bottom = radar().getOpenAngle() < 180.0 ? 0d : - Math.abs(top * Math.sin((radar().getOpenAngle() - 180d) * Math.PI/360));
			left   = radar().getOpenAngle() < 180.0 ? - Math.abs(top * Math.sin(radar().getOpenAngle() * Math.PI/360)) : - radar().getRange().getMax();
			right = - left;
			
			graph().setDomain(BoundingBoxes.fromBounds(left, bottom, right, top));
		}

		public void 						setOpenAngle(double _openAngle) {
			openAngle = _openAngle;
			configureGraphDomain();
		}
		public double 						getOpenAngle() {
			return openAngle;
		}

		public void 						setRange(double _minRange, double _maxRange) {
			range = Interval.of(_minRange, _maxRange);
			configureGraphDomain();
		}
		public Interval<Double> 			getRange() {
			return range;
		}

		public void 						setRangeGrid(double _majorTicks, double minorTicks) {
			rangeGrid    = _majorTicks;
			rangeSubGrid = minorTicks;
		}
		public void 						setRangeGrid(double _value) {
			rangeGrid = _value;
		}
		public void 						setRangeSubGrid(double _value) {
			rangeSubGrid = _value;
		}
		public double 						getRangeGrid() {
			return rangeGrid;
		}
		public double 						getRangeSubGrid() {
			return rangeSubGrid;
		}

		public void 						setAngularGrid(double _majorTicks, double minorTicks) {
			angleGrid    = _majorTicks;
			angleSubGrid = minorTicks;
		}
		public void 						setAngularGrid(double _value) {
			angleGrid = _value;
		}
		public void 						setAngularSubGrid(double _value) {
			angleSubGrid = _value;
		}
		public double 						getAngularGrid() {
			return angleGrid;
		}
		public double 						getAngularSubGrid() {
			return angleSubGrid;
		}

		Point2D 							polar2pixel(double _range, double _angle) {
			double x = _range * Math.sin(_angle * Math.PI/180d);
			double y = _range * Math.cos(_angle * Math.PI/180d);

			double cx = graph().getBounds().getLeft();
			double gx = graph().getDomain().getLeft();

			double px = cx + ( x - gx ) / graph().QWg();

			double cy = graph().getBounds().getTop();
			double gy = graph().getDomain().getBottom();

			double py = cy + ( gy - y ) / graph().QHg();

			return Points.of(px, py);
		}
		Point2D 							cartesian2pixel(double _x, double _y) {
			double cx = graph().getBounds().getLeft();
			double gx = graph().getDomain().getLeft();

			double px = cx + ( _x - gx ) / graph().QWg();

			double cy = graph().getBounds().getTop();
			double gy = graph().getDomain().getBottom();

			double py = cy + ( gy - _y ) / graph().QHg();

			return Points.of(px, py);
		}

	}
	class 		  			  RadarProperties extends RadarAttributes {

	}
	public final class 		  Radar extends RadarProperties {
		RadarStyle style;
		
		Radar() {
			super();

			style = new RadarStyle();
		}

		public RadarStyle style() {
			return style;
		}

	}

	public final class 		  RadarDesign {

		public void 						drawRadarBounds(Drawer _drawer) {
			Point2D  Pa, Pb, PA, PB, Pfront, Pleft, Pright, Prear, PFront, PLeft, PRight, PRear;

			PA     = radar().polar2pixel(radar().getRange().getMax(), - radar().getOpenAngle() / 2d);
			PB     = radar().polar2pixel(radar().getRange().getMax(), + radar().getOpenAngle() / 2d);
			PFront = radar().polar2pixel(radar().getRange().getMax(),    0);
			PLeft  = radar().polar2pixel(radar().getRange().getMax(), - 90);
			PRight = radar().polar2pixel(radar().getRange().getMax(),   90);
			PRear  = radar().polar2pixel(radar().getRange().getMax(),  180);

			Pa     = radar().polar2pixel(radar().getRange().getMin(), - radar().getOpenAngle() / 2d);
			Pb     = radar().polar2pixel(radar().getRange().getMin(), + radar().getOpenAngle() / 2d);
			Pfront = radar().polar2pixel(radar().getRange().getMin(),    0);
			Pleft  = radar().polar2pixel(radar().getRange().getMin(), - 90);
			Pright = radar().polar2pixel(radar().getRange().getMin(),   90);
			Prear  = radar().polar2pixel(radar().getRange().getMin(),  180);

			_drawer.drawLine(Pa.getX(), Pa.getY(), PA.getX(), PA.getY(), radar().style().getBoundBrush());
			_drawer.drawLine(Pb.getX(), Pb.getY(), PB.getX(), PB.getY(), radar().style().getBoundBrush());
			_drawer.drawArc(PLeft.getX(), PFront.getY(), PRight.getX() - PLeft.getX(), PRear.getY() - PFront.getY(), 
							90 - radar().getOpenAngle()/2,  radar().getOpenAngle(), Drawer.ARC_OPEN, radar().style().getBoundBrush());
			_drawer.drawArc(Pleft.getX(), Pfront.getY(), Pright.getX() - Pleft.getX(), Prear.getY() - Pfront.getY(), 
							90 - radar().getOpenAngle()/2,  radar().getOpenAngle(), Drawer.ARC_OPEN, radar().style().getBoundBrush());
		}
		public void 						drawAngleTicks(Drawer _drawer) {
			for(double a = - radar().getOpenAngle() / 2; a < radar().getOpenAngle() / 2; ++a) {
				Point2D  Pa, PA;

				PA     = radar().polar2pixel(radar().getRange().getMax(), a);
				Pa     = radar().polar2pixel(radar().getRange().getMax() - 3, a);
				_drawer.drawLine(Pa.getX(), Pa.getY(), PA.getX(), PA.getY(), radar().style().getAngleTicksBrush());
			}
		}

		public void 						drawRadarRangeGrid(Drawer _drawer) {
			if(radar().getRangeGrid() <= 0d)
				return ;

			for(double r = radar().getRangeGrid(); r < radar().getRange().getMax(); r += radar().getRangeGrid()) {
				Point2D PT = radar().polar2pixel(r,    0);
				Point2D PL = radar().polar2pixel(r, - 90);
				Point2D PR = radar().polar2pixel(r,   90);
				Point2D PB = radar().polar2pixel(r,  180);

				_drawer.drawArc(PL.getX(), PT.getY(), PR.getX() - PL.getX(), PB.getY() - PT.getY(), 
								90 - radar().getOpenAngle()/2,  radar().getOpenAngle(), Drawer.ARC_OPEN, radar().style().getRangeGridBrush());
			}
		}
		public void 						drawRadarRangeSubGrid(Drawer _drawer) {
			if(radar().getRangeSubGrid() <= 0d)
				return ;

			for(double r = radar().getRangeSubGrid(); r < radar().getRange().getMax(); r += radar().getRangeSubGrid()) {
				Point2D PT = radar().polar2pixel(r,    0);
				Point2D PL = radar().polar2pixel(r, - 90);
				Point2D PR = radar().polar2pixel(r,   90);
				Point2D PB = radar().polar2pixel(r,  180);

				_drawer.drawArc(PL.getX(), PT.getY(), PR.getX() - PL.getX(), PB.getY() - PT.getY(), 
								90 - radar().getOpenAngle()/2,  radar().getOpenAngle(), Drawer.ARC_OPEN, radar().style().getRangeSubGridBrush());
			}
		}

		public void 						drawRadarAngleGrid(Drawer _drawer) {

			if(radar().getAngularGrid() <= 0d)
				return ;

			for(double a = 0; a < radar().getOpenAngle() / 2; a += radar().getAngularGrid()) {
				Point2D  Pa, PA;

				PA     = radar().polar2pixel(radar().getRange().getMax(), - a);
				Pa     = radar().polar2pixel(radar().getRange().getMin(), - a);
				_drawer.drawLine(Pa.getX(), Pa.getY(), PA.getX(), PA.getY(), radar().style().getAngleGridBrush());
				
				PA     = radar().polar2pixel(radar().getRange().getMax(), a);
				Pa     = radar().polar2pixel(radar().getRange().getMin(), a);
				_drawer.drawLine(Pa.getX(), Pa.getY(), PA.getX(), PA.getY(), radar().style().getAngleGridBrush());
			}
		}
		public void 						drawRadarAngleSubGrid(Drawer _drawer) {
			if(radar().getAngularSubGrid() <= 0d)
				return ;

			for(double a = 0; a < radar().getOpenAngle() / 2; a += radar().getAngularSubGrid()) {
				Point2D  Pa, PA;

				PA     = radar().polar2pixel(radar().getRange().getMax(), - a);
				Pa     = radar().polar2pixel(radar().getRange().getMin(), - a);
				_drawer.drawLine(Pa.getX(), Pa.getY(), PA.getX(), PA.getY(), radar().style().getAngleSubGridBrush());
				
				PA     = radar().polar2pixel(radar().getRange().getMax(), a);
				Pa     = radar().polar2pixel(radar().getRange().getMin(), a);
				_drawer.drawLine(Pa.getX(), Pa.getY(), PA.getX(), PA.getY(), radar().style().getAngleSubGridBrush());
			}
		}

	}

	
	Radar radarProperties;
	RadarDesign     radarDesign;

	boolean 		 useAngularGrid, useRangeGrid;
	
	public RadarDesignAdapter(double _x, double _y, double _w, double _h) {
		super(_x, _y, _w, _h);
		
		radarProperties = new Radar();
		radarDesign   = new RadarDesign();
	}

	public Radar radar() {
		return radarProperties;
	}
	
	
	
	public void 						draw(Drawer _drawer) {
		super.draw(_drawer);

		radarDesign.drawRadarBounds(_drawer);

		radarDesign.drawAngleTicks(_drawer);
		radarDesign.drawRadarAngleGrid(_drawer);
		radarDesign.drawRadarAngleSubGrid(_drawer);

//		radarDesign.drawRangeTicks(_drawer);
		radarDesign.drawRadarRangeGrid(_drawer);
		radarDesign.drawRadarRangeSubGrid(_drawer);
	}

	public void 						drawCartesianTarget(Drawer _drawer, double _cx, double _cy, double _w, double _h) {
		Point2D P = radar().cartesian2pixel(_cx, _cy);
		_drawer.drawEllipse(P.getX(), P.getY(), _w, _h, radar().style().getTargetBrush());
	}
	public void 						drawPolarTarget(Drawer _drawer, double _r, double _a, double _w, double _h) {
		Point2D P = radar().polar2pixel(_r, _a);
		_drawer.drawEllipse(P.getX(), P.getY(), _w, _h, radar().style().getTargetBrush());
	}

	public void 						drawCartesian(Drawer _drawer, double _x, double _y) {
		Point2D P = radar().cartesian2pixel(_x, _y);
		_drawer.drawPoint(P.getX(), P.getY(), PointSkin.Star, 5d, Brush.of(Colors.RED));
	}
	public void 						drawCartesian(Drawer _drawer, Coordinate.Cartesian2D _pt) {
//		Point2D P = radar().cartesian2pixel(_pt.getX(), _pt.getY());
		Point2D P = radar().cartesian2pixel(_pt.getY(), - _pt.getX());
		_drawer.drawPoint(P.getX(), P.getY(), Brush.of( 5d, Colors.RED));
	}

}
