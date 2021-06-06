package fr.java.sdk.math.series;

import java.util.ArrayList;
import java.util.List;

import fr.java.math.stats.MathSerie;
import fr.java.sdk.lang.Asserts;

public class SimpleSerie1D implements MathSerie.OneDim {

	List<Double> x, y;
	
	public SimpleSerie1D() {
		super();

		x = new ArrayList<Double>();
		y = new ArrayList<Double>();
	}
	public SimpleSerie1D(List<Double> _x, List<Double> _y) {
		super();

		x = new ArrayList<Double>(_x);
		y = new ArrayList<Double>(_y);
	}

	@Override
	public int 		size() {
		return x.size();
	}
	@Override
	public int 		getLength() {
		return x.size();
	}

	public void add(double _x, double _y) {
		x.add(_x);
		y.add(_y);
	}
	public void add(double[] _x, double[] _y) {
		Asserts.assertTrue(_x.length == _y.length);

		for(int i = 0; i < _x.length; ++i) {
			x.add(_x[i]);
			y.add(_y[i]);
		}
	}


	@Override
	public double[] getX() {
		return x.stream().mapToDouble(Double::valueOf).toArray();
	}
	@Override
	public double 	getX(int _idx) {
		return x.get(_idx);
	}

	@Override
	public double[] getY() {
		return y.stream().mapToDouble(Double::valueOf).toArray();
	}
	@Override
	public double 	getY(int _idx) {
		return y.get(_idx);
	}

}
