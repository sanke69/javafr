package fr.javafx.temp.graphics.fx.api.charts;

import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import fr.javafx.temp.graphics.fx.api.charts.skins.DefaultPlotterCurveSkin;
import fr.javafx.temp.graphics.fx.api.charts.skins.DefaultPlotterDotsSkin;

public class ChartControl extends Control {

	public static class Curve {
		Double[] x, y;

		public Curve(double[] _x, double[] _y) {
			assert(_x.length == _y.length);

			x = new Double[_x.length];
			y = new Double[_y.length];
			for(int i = 0; i < _x.length; ++i) {
				x[i] = _x[i];
				y[i] = _y[i];
			}
		}
		public Curve(double[][] _xy) {
			assert(_xy[0].length >= 2);

			x = new Double[_xy.length];
			y = new Double[_xy.length];
			for(int i = 0; i < _xy.length; ++i) {
				x[i] = _xy[i][0];
				y[i] = _xy[i][1];
			}
		}
		public Curve(Double[] _x, Double[] _y) {
			x = _x;
			y = _y;
		}

		public Double[] getX() {
			return x;
		}
		public Double[] getY() {
			return y;
		}
	}

	DoubleProperty xMin, xMax, yMin, yMax;
	DoubleProperty xMajorTicks, xMinorTicks, yMajorTicks, yMinorTicks;

	MapProperty<String, Curve> curves;
	
	public enum Style { Curve, Dots };
	
	public ChartControl() {
		super();
		xMin = new SimpleDoubleProperty(-1.0);
		xMax = new SimpleDoubleProperty( 1.0);
		yMin = new SimpleDoubleProperty(-1.0);
		yMax = new SimpleDoubleProperty( 1.0);
		xMajorTicks = new SimpleDoubleProperty(0.5);
		xMinorTicks = new SimpleDoubleProperty(0.1);
		yMajorTicks = new SimpleDoubleProperty(0.5);
		yMinorTicks = new SimpleDoubleProperty(0.1);
		
		curves = new SimpleMapProperty<String, Curve>(FXCollections.observableHashMap());
	}
	public ChartControl(Style _style) {
		super();
		xMin = new SimpleDoubleProperty(-1.0);
		xMax = new SimpleDoubleProperty( 1.0);
		yMin = new SimpleDoubleProperty(-1.0);
		yMax = new SimpleDoubleProperty( 1.0);
		xMajorTicks = new SimpleDoubleProperty(0.5);
		xMinorTicks = new SimpleDoubleProperty(0.1);
		yMajorTicks = new SimpleDoubleProperty(0.5);
		yMinorTicks = new SimpleDoubleProperty(0.1);
		
		curves = new SimpleMapProperty<String, Curve>(FXCollections.observableHashMap());
		
		switch(_style) {
		case Curve: setSkin(new DefaultPlotterCurveSkin(this)); break;
		case Dots: setSkin(new DefaultPlotterDotsSkin(this)); break;
		default:
			break;
		
		}
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		return new DefaultPlotterCurveSkin(this);
	}

	public void setBoundaries(double _xMin, double _xMax, double _yMin, double _yMax) {
		xMin.set(_xMin);
		xMax.set(_xMax);
		yMin.set(_yMin);
		yMax.set(_yMax);
	}
	public void setXTicks(double _major, double _minor) {
		
	}
	public void setYTicks(double _major, double _minor) {
		
	}

	public void add(double[] _x, double[] _y) {
		curves.put("default", new Curve(_x, _y));
	}
	public void add(Double[] _x, Double[] _y) {
		curves.put("default", new Curve(_x, _y));
	}
	public void add(Curve _c) {
		curves.put("default", _c);
	}
	
	public void add(String _label, double[] _x, double[] _y) {
		curves.put(_label, new Curve(_x, _y));
	}
	public void add(String _label, Double[] _x, Double[] _y) {
		curves.put(_label, new Curve(_x, _y));
	}
	public void add(String _label, Curve _c) {
		curves.put(_label, _c);
	}
	
	public void remove(String _label) {
		curves.remove(_label);
	}
	public void removeAll(String... _labels) {
		for(String label : _labels)
			curves.remove(label);
	}
	public void removeAll() {
		curves.clear();
	}

	public double xMin() {
		return xMin.get();
	}
	public ReadOnlyDoubleProperty xMinProperty() {
		return xMin;
	}

	public double xMax() {
		return xMax.get();
	}
	public ReadOnlyDoubleProperty xMaxProperty() {
		return xMax;
	}

	public double yMin() {
		return yMin.get();
	}
	public ReadOnlyDoubleProperty yMinProperty() {
		return yMin;
	}

	public double yMax() {
		return yMax.get();
	}
	public ReadOnlyDoubleProperty yMaxProperty() {
		return yMax;
	}

	public double xMajorTicks() {
		return xMajorTicks.get();
	}
	public ReadOnlyDoubleProperty xMajorTicksProperty() {
		return xMajorTicks;
	}

	public double xMinorTicks() {
		return xMinorTicks.get();
	}
	public ReadOnlyDoubleProperty xMinorTicksProperty() {
		return xMinorTicks;
	}

	public double yMajorTicks() {
		return yMajorTicks.get();
	}
	public ReadOnlyDoubleProperty yMajorTicksProperty() {
		return yMajorTicks;
	}

	public double yMinorTicks() {
		return yMinorTicks.get();
	}
	public ReadOnlyDoubleProperty yMinorTicksProperty() {
		return yMinorTicks;
	}

	public Map<String, Curve> curves() {
		return curves.get();
	}
	public ReadOnlyMapProperty<String, Curve> curvesProperty() {
		return curves;
	}

}
