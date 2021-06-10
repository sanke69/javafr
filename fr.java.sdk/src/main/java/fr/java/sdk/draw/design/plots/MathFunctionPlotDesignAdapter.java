package fr.java.sdk.draw.design.plots;

import java.util.ArrayList;
import java.util.List;

import fr.java.draw.Drawer;
import fr.java.draw.styles.LineStyle;
import fr.java.draw.styles.PointSkin;
import fr.java.draw.tools.Brush;
import fr.java.draw.tools.Colors;
import fr.java.math.Interval;
import fr.java.math.functions.MathFunction;
import fr.java.maths.BoundingBoxes;
import fr.java.sdk.draw.design.GraphDesignAdapter;

public class MathFunctionPlotDesignAdapter extends GraphDesignAdapter {

	final class MathFunctionPlotDesign {
		
		public void drawFunction(Drawer _drawer, MathFunction.OneVar _fn, int _nbPoints) {
			double[] xs = new double[_nbPoints];
			double[] ys = new double[_nbPoints];
			
			for(int i = 0; i < _nbPoints; ++i) {
				xs[i] = xMin + (xMax - xMin) * i/((double) _nbPoints - 1d);
				ys[i] = _fn.evaluate(xs[i]);

			}

			_drawer.drawLines(graph().Xg2d(xs), graph().Yg2d(ys), LineStyle.of(1d, Brush.of(1d, Colors.LIGHTCORAL), 3, 2, 1));
//			_drawer.drawLines(graph().Pg2d(xs, ys), LineStyle.of(1d, Brush.of(1d, Colors.RED), 3, 2, 1));
//			_drawer.drawLines(graph().Pg2d(xs, ys), PointSkin.Dot, 3d, 5d, Colors.RED);
//			_drawer.drawPoint(graph().Xg2d(_x_graph), graph().Yg2d(_y_graph), 1, Colors.YELLOW);
//			_drawer.drawPoint(graph().Pg2d(_x_graph, _y_graph), 2, Colors.CYAN);
		}
		public void drawFunctionOld(Drawer _drawer, MathFunction.OneVar _fn, int _nbPoints) {
			for(int i = 0; i < _nbPoints; ++i) {
				double _x = xMin + (xMax - xMin) * i/((double) _nbPoints - 1d);
				double _y = _fn.evaluate(_x);

				_drawer.drawPoint(graph().Pg2d(_x, _y), PointSkin.Dot, 3d, 5d, Colors.RED);
			}
		}


	}

	MathFunctionPlotDesign		plotDesign;

	List<MathFunction.OneVar> 	functions;
	Interval<Double>          	x, y;
	double          			xMin, xMax, yMin, yMax;
	
	public MathFunctionPlotDesignAdapter(double _x, double _y, double _w, double _h) {
		super(_x, _y, _w, _h);
		plotDesign = new MathFunctionPlotDesign();

		functions = new ArrayList<MathFunction.OneVar>();
		xMin =  0d;
		xMax =  1d;
		yMin = -1d;
		yMax =  1d;
	}
	
	public void 				setInterval(double _x_min, double _x_max) {
		x = Interval.of(_x_min,  _x_max);
		xMin = _x_min;
		xMax = _x_max;
		
		graph().setDomain(BoundingBoxes.fromBounds(xMin, yMin, xMax, yMax));
	}
	public Interval<Double> 	getInterval() {
		return x;
	}

	public void 				addFunction(MathFunction.OneVar _mf) {
		addFunction(_mf, false);
	}
	public void 				addFunction(MathFunction.OneVar _mf, boolean _useForDomainComputation) {
		functions.add(_mf);

		if(_useForDomainComputation || functions.size() == 1) {
			double xi = xMin, dx = (xMax - xMin) / 1e3;

			for(;(xi += dx) < xMax;) {

				if(_mf.evaluate(xi) < yMin) yMin = _mf.evaluate(xi);
				if(_mf.evaluate(xi) > yMax) yMax = _mf.evaluate(xi);

			}

			graph().setDomain(BoundingBoxes.fromBounds(xMin, yMin, xMax, yMax));
		}
	}

	public void draw(Drawer _drawer) {
		super.draw(_drawer);

		for(MathFunction.OneVar fn : functions)
			plotDesign.drawFunction(_drawer, fn, 500);
	}

}
