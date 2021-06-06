package fr.java.sdk.draw.design.plots;

import java.util.ArrayList;
import java.util.List;

import fr.java.draw.Drawer;
import fr.java.draw.styles.LineStyle;
import fr.java.draw.tools.Brush;
import fr.java.draw.tools.Colors;
import fr.java.math.stats.MathSerie;
import fr.java.sdk.draw.design.GraphDesignAdapter;
import fr.java.sdk.math.BoundingBoxes;

// SlidingWindow
public class MathSeriePlotDesignAdapter extends GraphDesignAdapter {

	final class MathSeriePlotDesign {
		
		public void drawSerie(Drawer _drawer, MathSerie.OneDim _serie) {
			int PX0      = (int) graph().getBounds().getLeft();
			int PX1      = (int) graph().getBounds().getRight();
			int PY0      = (int) graph().getBounds().getTop();
			int PY1      = (int) graph().getBounds().getBottom();
			int X0       = (int) graph().getDomain().getLeft();
			int X1       = (int) graph().getDomain().getRight();
			int Y0       = (int) graph().getDomain().getTop();
			int Y1       = (int) graph().getDomain().getBottom();
			int nbPoints = PX1 - PX0;
			double[] xs, ys;

			if(_serie.size() < nbPoints) {
				double[] drawedX = new double[_serie.size()];
				double[] drawedY = new double[_serie.size()];
				
				int addedCount = 0;
				for(int i = 0; i < _serie.size(); ++i) {
					double x = _serie.getX(i);
					if(x > X0 && x < X1) {
						drawedX[addedCount] = x;
						drawedY[addedCount] = _serie.getY(i);
						
						addedCount++;
					}
				}

				xs = new double[addedCount];
				ys = new double[addedCount];
				System.arraycopy(drawedX, 0, xs, 0, addedCount);
				System.arraycopy(drawedY, 0, ys, 0, addedCount);

			} else {
				double[] drawedX = new double[nbPoints];
				double[] drawedY = new double[nbPoints];
				
				int addedCount = 0;
				for(int i = 0; i < _serie.size() && addedCount < nbPoints; ++i) {
					double x = _serie.getX(i);
					if(x > X0 && x < X1) {
						drawedX[addedCount] = x;
						drawedY[addedCount] = _serie.getY(i);
						
						addedCount++;
					}
				}

				xs = new double[addedCount];
				ys = new double[addedCount];
				System.arraycopy(drawedX, 0, xs, 0, addedCount);
				System.arraycopy(drawedY, 0, ys, 0, addedCount);
			}

			_drawer.drawPoints (graph().Xg2d(xs), graph().Yg2d(ys), 1d);
			_drawer.drawLines  (graph().Xg2d(xs), graph().Yg2d(ys), LineStyle.of(1d, Brush.of(1d, Colors.LIGHTCORAL), 3, 2, 1));
			
			// With interpolation
/*
			MathFunction.OneDim spline = new SmoothingCubicSpline();

			int      nbPoints = PX1 - PX0;
			double[] xs       = new double[nbPoints];
			double[] ys       = new double[nbPoints];

			for(int i = 0; i < nbPoints; ++i) {
				xs[i] = X0 + (X1 - X0) * i/((double) _nbPoints - 1d);
				ys[i] = spline.evaluate(xs[i]);
			}

			_drawer.drawLines(graph().Xg2d(xs), graph().Yg2d(ys), LineStyle.of(1d, Brush.of(1d, Colors.LIGHTCORAL), 3, 2, 1));
*/			
		}

	}

	final class StatSeriePlotDesign {

		final class WhiskeyBoxDesgin {
		
		}

	}

	MathSeriePlotDesign			plotDesign;
	List<MathSerie.OneDim> 		series;
	
	public MathSeriePlotDesignAdapter(double _x, double _y, double _w, double _h) {
		super(_x, _y, _w, _h);
		plotDesign = new MathSeriePlotDesign();

		series = new ArrayList<MathSerie.OneDim>();
	}

	public void 				setInterval(double _x_min, double _x_max) {
		graph().setDomain(BoundingBoxes.fromBounds(_x_min, graph().getBounds().getMinY(), _x_max, graph().getBounds().getMaxY()));
	}

	public void 				addSerie(MathSerie.OneDim _mf) {
		addSerie(_mf, false);
	}
	public void 				addSerie(MathSerie.OneDim _mf, boolean _useForDomainComputation) {
		series.add(_mf);

		if(_useForDomainComputation || series.size() == 1) {
			double minX = graph().getDomain().getMinX();
			double maxX = graph().getDomain().getMaxX();
			double minY = graph().getDomain().getMinY();
			double maxY = graph().getDomain().getMaxY();

			for(int i = 0; i < _mf.size(); ++i) {

				if(_mf.getX(i) < minX) minX = _mf.getX(i);
				if(_mf.getX(i) > maxX) maxX = _mf.getX(i);
				if(_mf.getY(i) < minY) minY = _mf.getY(i);
				if(_mf.getY(i) > maxY) maxY = _mf.getY(i);

			}

			graph().setDomain(BoundingBoxes.fromBounds(minX, minY, maxX, maxY));
		}
	}

	public void draw(Drawer _drawer) {
		super.draw(_drawer);

		for(MathSerie.OneDim serie : series)
			plotDesign.drawSerie(_drawer, serie);
	}

}
