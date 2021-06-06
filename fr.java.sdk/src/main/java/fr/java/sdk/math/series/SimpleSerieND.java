package fr.java.sdk.math.series;

import java.util.ArrayList;
import java.util.List;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.math.stats.DataSerie;
import fr.java.math.stats.MathSerie;
import fr.java.sdk.lang.Asserts;

public class SimpleSerieND implements MathSerie.NDims {

	List<Double>   x;
	List<SimpleDataSerie> y;
	
	SimpleSerieND() {
		super();

		x = new ArrayList<Double>();
		y = new ArrayList<SimpleDataSerie>();
	}
	SimpleSerieND(List<Double> _x, List<Double[]> _y) {
		super();

		Asserts.assertTrue(_x.size() == _y.size());
		for(Double[] valY : _y)
			Asserts.assertTrue(valY.length == 3);

		x = new ArrayList<Double>(_x);
		y = new ArrayList<SimpleDataSerie>();
	}

	@Override
	public int 		  size() {
		return x.size();
	}
	@Override
	public int 		  getLength() {
		return x.size();
	}

	@Override
	public double[]   getX() {
		return x.stream().mapToDouble(Double::valueOf).toArray();
	}
	@Override
	public double 	  getX(int _Idx) {
		return x.get(_Idx);
	}

	@Override
	public double[][] getY() {
		throw new NotYetImplementedException();
	}
	@Override
	public double[]   getY(int _idx) {
		double[] ys = new double[y.get(_idx).valueCount()];

		for(int i = 0; i < y.get(_idx).valueCount(); ++i)
			ys[0] = y.get(_idx).getValue(i);

		return ys;
	}
	@Override
	public double 	  getY(int _xIdx, int _yIdx) {
		return y.get(_xIdx).getValue(_yIdx);
	}
	
	public DataSerie  getSerie(int _idx) {
		return y.get(_idx);
	}
	
}
