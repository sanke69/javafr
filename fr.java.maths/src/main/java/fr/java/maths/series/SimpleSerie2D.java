package fr.java.maths.series;

import java.util.ArrayList;
import java.util.List;

import fr.java.math.stats.MathSerie;

public class SimpleSerie2D implements MathSerie.TwoDims {

	List<Double>   x;
	List<Double[]> y;
	
	SimpleSerie2D() {
		super();

		x = new ArrayList<Double>();
		y = new ArrayList<Double[]>();
	}
	SimpleSerie2D(List<Double> _x, List<Double[]> _y) {
		super();

		assert(_x.size() == _y.size());
		for(Double[] valY : _y)
			assert(valY.length == 2);

		x = new ArrayList<Double>(_x);
		y = new ArrayList<Double[]>(_y);
	}

	@Override
	public int 		size() {
		return x.size();
	}
	@Override
	public int 		getLength() {
		return x.size();
	}

	@Override
	public double[] getX() {
		return x.stream().mapToDouble(Double::valueOf).toArray();
	}
	@Override
	public double 	getX(int _Idx) {
		return x.get(_Idx);
	}

	@Override
	public double[][] getY() {
		double[][] ys = new double[size()][2];
		
		for(int i = 0; i < size(); ++i) {
			ys[i][0] = y.get(i)[0];
			ys[i][1] = y.get(i)[1];
		}
		
		return ys;
	}
	@Override
	public double[]   getY(int _idx) {
		double[] ys = new double[2];
		
		ys[0] = y.get(_idx)[0];
		ys[1] = y.get(_idx)[1];
		
		return ys;
	}
	@Override
	public double 	getY1(int _idx) {
		return y.get(_idx)[0];
	}
	@Override
	public double 	getY2(int _idx) {
		return y.get(_idx)[1];
	}

}
