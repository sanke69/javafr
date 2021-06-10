package fr.java.maths.series;

import java.util.ArrayList;
import java.util.List;

import fr.java.math.stats.MathSerie;

public class SimpleSerie3D implements MathSerie.ThreeDims {

	List<Double>   x;
	List<Double[]> y;
	
	SimpleSerie3D() {
		super();

		x = new ArrayList<Double>();
		y = new ArrayList<Double[]>();
	}
	SimpleSerie3D(List<Double> _x, List<Double[]> _y) {
		super();

		assert(_x.size() == _y.size());
		for(Double[] valY : _y)
			assert(valY.length == 3);

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
		double[][] ys = new double[size()][3];
		
		for(int i = 0; i < size(); ++i) {
			ys[i][0] = y.get(i)[0];
			ys[i][1] = y.get(i)[1];
			ys[i][2] = y.get(i)[2];
		}
		
		return ys;
	}
	@Override
	public double[]   getY(int _idx) {
		double[] ys = new double[3];
		
		ys[0] = y.get(_idx)[0];
		ys[1] = y.get(_idx)[1];
		ys[2] = y.get(_idx)[2];
		
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
	@Override
	public double 	getY3(int _idx) {
		return y.get(_idx)[2];
	}

}
