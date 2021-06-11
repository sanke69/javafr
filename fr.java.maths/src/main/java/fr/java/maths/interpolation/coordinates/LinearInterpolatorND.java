package fr.java.maths.interpolation.coordinates;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.StringJoiner;

import fr.java.math.interpolation.CoordinateInterpolator;
import fr.java.math.topology.Coordinate;
import fr.java.math.topology.CoordinateSystem;

public class LinearInterpolatorND implements CoordinateInterpolator.MultiDims {
	private int        nbDims, nbPoints;
	private double[][] xn;

	public LinearInterpolatorND(double[]... _xn) {
		super();
		nbDims   = xn.length - 1;
		nbPoints = xn[0].length - 1;
		xn = _xn.clone();
	}

	public Coordinate.MultiDims 	evaluate(double _t) {
		int    T  = (int) Math.floor(_t);
		double dt = _t - T;

		double[] x = new double[nbDims];
		if(T >= nbPoints - 1)
			for(int i = 0; i < nbDims; ++i)
				x[i] = xn[i][nbPoints - 1];
		else {
			for(int i = 0; i < nbDims; ++i)
				x[i] = (1d - dt) * xn[i][T] + dt * xn[i][T+1];
		}

		return new Coordinate.MultiDims() {
			@Override public CoordinateSystem 	getCoordinateSystem() 	{ return null; }
			@Override public int  				getDims() 				{ return nbDims; }
			@Override public double 			get(int _i) 			{ return x[_i]; }
			@Override public String 			toString(DecimalFormat _df) {
				StringJoiner sj = new StringJoiner(", ");
				Arrays.stream(x).forEach(x -> sj.add(String.valueOf(x)));
				return "( " + sj.toString() + " )";
			}
		};
	}

	@Override
	public Coordinate.MultiDims[] 	interpolate(int _nbPoints) {
		Coordinate.MultiDims[]  pts = new Coordinate.MultiDims[_nbPoints];
		double    				dt  = (nbPoints - 1d) / (_nbPoints - 1d);
		
		for(int i = 0; i < _nbPoints; ++i)
			pts[i] = evaluate(i * dt);
	
		return pts;
	}

}
