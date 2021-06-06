package fr.java.sdk.math.series;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.java.math.stats.DataSerie;
import fr.java.math.stats.MathSerie;
import fr.java.math.stats.StatSerie;

public class SimpleDataSerie implements DataSerie, StatSerie {

	List<Double> values;
	double sum = 0.0d, sum2 = 0.0d, min = 1e99, max = -1e99;
	
	SimpleDataSerie() {
		super();

		values = new ArrayList<Double>();
	}
	SimpleDataSerie(List<Double> _values) {
		super();

		values = new ArrayList<Double>();
		values.addAll(_values);
		initStats();
	}

	public int valueCount() {
		return values.size();
	}

	public void 	add(double _value) {
		values.add(_value);
		updateStats(_value);
	}
	public void 	addAll(double[] _values) {
		for(double val : _values)
			values.add(val);
		updateStats(_values);
	}
	public void 	addAll(List<Double> _values) {
		values.addAll(_values);
		updateStats(_values);
	}

	public void 	setValues(double[] _values) {
		values.clear();
		for(double val : _values)
			values.add(val);
		initStats();
	}
	public void 	setValues(List<Double> _values) {
		values.clear();
		values.addAll(_values);
		initStats();
	}
	@Override
	public double[] getValues() {
		return null;
	}
	@Override
	public double 	getValue(int _idx) {
		return values.get(_idx);
	}

	@Override
	public double 	getMin() {
		return min;
	}
	@Override
	public double 	getMax() {
		return max;
	}

	@Override
	public double 	getMedian() {
		return 0;
	}

	@Override
	public double 	getMean() {
		return sum / (values.size() - 1);
	}
	@Override
	public double 	getVar() {
		return (sum2 / (values.size() - 1)) - ((sum * sum) / ((values.size() - 1) * (values.size() - 1)));
	}
	@Override
	public double 	getStd() {
		return Math.sqrt(getVar());
	}

	@Override
	public Optional<WhiskerBox> getWhiskerBox() {
		return Optional.empty();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		DecimalFormat df = new DecimalFormat("#.###");
		sb.append("<DataSerie>[N= " + values.size() + ", mean= " + df.format( getMean() ) + ", std= " + df.format( getStd() ) + ", min= " + df.format( getMin() ) + ", max= " + df.format( getMax() ) + "]");

		return sb.toString();
	}

	public static void main(String[] args) {
		SimpleDataSerie serie = new SimpleDataSerie();
		
		int N = 2_000_000_000, n = 0;
		while(n < N) {
			double newValue = Math.random();
			serie.add(newValue);

			DecimalFormat df = new DecimalFormat("#.#######");
			System.out.println("new= " + df.format( newValue ) + "\t" + serie + "\t" + serie.sum + "\t" + serie.sum2);
		}
	}
	
	private void initStats() {
		sum = 0.0d; sum2 = 0.0d; min = 1e99; max = -1e99;

		for(int i = 0; i < values.size(); ++i) {
			double value = values.get(i);
			sum  += value;
			sum2 += value * value;
	
			if(value < min) min = value;
			if(value > max) max = value;
		}
	}
	private void initStats(double[] _values) {
		sum = 0.0d; sum2 = 0.0d; min = 1e99; max = -1e99;

		for(int i = 0; i < _values.length; ++i) {
			sum  += _values[i];
			sum2 += _values[i] * _values[i];
	
			if(_values[i] < min) min = _values[i];
			if(_values[i] > max) max = _values[i];
		}

		sum  /= (values.size() - 1);
		sum2 /= (values.size() - 1);
	}
	private void initStats(List<Double> _values) {
		sum = 0.0d; sum2 = 0.0d; min = 1e99; max = -1e99;

		for(int i = 0; i < _values.size(); ++i) {
			double value = _values.get(i);
			sum  += value;
			sum2 += value * value;
	
			if(value < min) min = value;
			if(value > max) max = value;
		}

		sum  /= (values.size() - 1);
		sum2 /= (values.size() - 1);
	}
	private void updateStats(double _newValue) {
		sum  += _newValue;
		sum2 += _newValue * _newValue;

		if(_newValue < min) min = _newValue;
		if(_newValue > max) max = _newValue;
	}
	private void updateStats(double[] _newValues) {
		for(double val : _newValues)
			updateStats(val);
	}
	private void updateStats(List<Double> _newValues) {
		for(Double val : _newValues)
			updateStats(val);
	}

}
