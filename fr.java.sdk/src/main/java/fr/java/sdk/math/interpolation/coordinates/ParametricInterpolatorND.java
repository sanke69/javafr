package fr.java.sdk.math.interpolation.coordinates;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.IntStream;

import fr.java.math.topology.Coordinate;
import fr.java.math.topology.CoordinateSystem;
import fr.java.sdk.math.interpolation.CoordinateInterpolator;
import fr.java.sdk.math.interpolation.FunctionInterpolator;
import fr.java.sdk.math.interpolation.functions.splines.cubic.CubicSpline;
import fr.java.sdk.math.interpolation.functions.splines.cubic.CubicSplineMonotone;
import fr.java.sdk.math.interpolation.functions.splines.cubic.CubicSplineWithWeight;

public class ParametricInterpolatorND implements CoordinateInterpolator.MultiDims {
	int							  nbDims, nbPoints;
	FunctionInterpolator.OneVar[] xn_t;

	public ParametricInterpolatorND(FunctionInterpolator.OneVar... _xn_t) {
		super();
		nbDims = _xn_t.length;
		xn_t   = _xn_t;
	}
	public ParametricInterpolatorND(FunctionInterpolator.OneVar[] _xn_t, double[][] _xn) {
		this(_xn_t);
		nbPoints = _xn[0].length;
		resetData(_xn);
	}
	public ParametricInterpolatorND(double[]... _xn) {
		this(new CubicSplineWithWeight(1), new CubicSplineWithWeight(1), new CubicSplineWithWeight(1));
		nbPoints = _xn[0].length;
		resetData(_xn);
	}

	public void 	 				resetData(double[][] _xn) {
		double[] t = IntStream.rangeClosed(1, _xn.length).mapToDouble(Double::valueOf).toArray();

		nbPoints = t.length;
		
		for(int i = 0; i < nbDims; ++i)
			if(xn_t[i] instanceof CubicSpline)
				((CubicSpline) xn_t[i]).init(t, _xn[i]);
			else if(xn_t[i] instanceof CubicSplineMonotone)
				((CubicSplineMonotone) xn_t[i]).init(t, _xn[i]);
			else if(xn_t[i] instanceof CubicSplineWithWeight)
				((CubicSplineWithWeight) xn_t[i]).init(t, _xn[i]);
			else 
				System.err.println("Unable to update X[" + i + "] Interpolator");

	}

	@Override
	public Coordinate.MultiDims 	evaluate(double _t) {
		double[] y = new double[nbDims];

		for(int i = 0; i < nbDims; ++i)
			y[i] = xn_t[i].evaluate(_t);

		return new Coordinate.MultiDims() {
			@Override public CoordinateSystem 	getCoordinateSystem() 	{ return null; }
			@Override public int  				getDims() 				{ return nbDims; }
			@Override public double 			get(int _i) 			{ return y[_i]; }
			@Override public String 			toString(DecimalFormat _df) {
				StringJoiner sj = new StringJoiner(", ");
				Arrays.stream(y).forEach(y -> sj.add(String.valueOf(y)));
				return "( " + sj.toString() + " )";
			}
		};
	}

	@Override
	public Coordinate.MultiDims[] 	interpolate(int _nbPoints) {
		Coordinate.MultiDims[] interps = new Coordinate.MultiDims[_nbPoints];

		double dt = (nbPoints - 1) / (_nbPoints - 1);
		for(int j = 0; j < _nbPoints; ++j) {
			double   t = 1 + (nbPoints - 1) * (j * dt);
			interps[j] = evaluate(t);
		}

		return interps;
	}

}
