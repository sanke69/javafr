package fr.java.sdk.math.interpolation.coordinates;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.StringJoiner;

import fr.java.math.polynoms.Polynom;
import fr.java.math.topology.Coordinate;
import fr.java.math.topology.CoordinateSystem;
import fr.java.sdk.math.interpolation.CoordinateInterpolator;
import fr.java.sdk.math.interpolation.FunctionInterpolator;

public class PolynomialInterpolatorND implements CoordinateInterpolator.MultiDims {

	int							  nbDims, nbPoints;
	FunctionInterpolator.OneVar[] interpolatorDim;

	public PolynomialInterpolatorND(double[]... _xi) {
		super();

		for(int i = 1; i < _xi.length - 1; ++i)
			if(_xi[0].length != _xi[i].length)
				throw new IllegalArgumentException();

		nbDims   = _xi.length;
		nbPoints = _xi[0].length;

		Polynom.Real[] 
				PIdx  = PolynomialInterpolators.computeIndexPolynomials ( nbPoints );
		for(int i = 0; i < _xi.length - 1; ++i)
			interpolatorDim[i] = PolynomialInterpolators.computeValuePolynomial(PIdx, _xi[i]);
	}

	public Coordinate.MultiDims 	evaluate(double _t) {
		double[] y = new double[nbDims];

		for(int i = 0; i < nbDims; ++i)
			y[i] = interpolatorDim[i].evaluate(_t);

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
