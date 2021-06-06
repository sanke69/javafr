package fr.javafx.sdk.controls.charts;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class TimeSignalViewer extends Control {

	int timeWindow;

	final LineChart<Number, Number> 		chart;
	final SlidingAxis 						xAxis;
	final NumberAxis  						yAxis;

	XYChart.Series<Number, Number>       	dataChart;
    ObservableList<Data<Number, Number>> 	data;

    public TimeSignalViewer(int _timeWindow) {
    	super();
    	timeWindow = _timeWindow;

    	xAxis = new SlidingAxis();
        xAxis.setAutoRanging(false);
        xAxis.setTickUnit(1000);

        yAxis = new NumberAxis(0, 15, 1);
        yAxis.setAutoRanging(false);

        chart = new LineChart<Number, Number>(xAxis, yAxis);
    	chart.setAnimated(false);
        chart.setCreateSymbols(false);
        chart.setLegendVisible(false);
        
        dataChart = new XYChart.Series<Number, Number>();
        chart.getData().add(dataChart);

        data      = dataChart.getData();
        
        data.addListener((ListChangeListener<? super XYChart.Data<Number,Number>>) lst -> {
            ObservableList<Data<Number, Number>> data = dataChart.getData();
            double t_0 = data.get(0).getXValue().doubleValue();
            xAxis.setLowerBound(t_0);
            xAxis.setUpperBound(t_0 + timeWindow);
        });
    }

    public Skin<TimeSignalViewer> createDefaultSkin() {
    	return new Skin<TimeSignalViewer>() {

			@Override
			public TimeSignalViewer getSkinnable() {
				return TimeSignalViewer.this;
			}

			@Override
			public Node getNode() {
				return chart;
			}

			@Override
			public void dispose() {
				;
			}
    		
    	};
    }

    public void addValue(Number _value) {
        if(data.size() > timeWindow)
            data.remove(0);

        data.add(new XYChart.Data<Number, Number>(System.currentTimeMillis(), _value));
    }
    public void addValue(long _timestamp, Number _value) {
        if(data.size() > timeWindow)
            data.remove(0);

        data.add(new XYChart.Data<Number, Number>(_timestamp, _value));
    }

    private class SlidingAxis extends ValueAxis<Number> {
        private SimpleObjectProperty<Double> tickUnitProperty = new SimpleObjectProperty<Double>(
                5d);

        @Override
        protected List<Number> calculateMinorTickMarks() {
            List<Number> ticks = new ArrayList<Number>();
            double tickUnit = tickUnitProperty.get() / getMinorTickCount();
            double start = Math.floor(getLowerBound() / tickUnit) * tickUnit;
            for (double value = start; value < getUpperBound(); value += tickUnit) {
                ticks.add(value);
            }
            return ticks;
        }

        @Override
        protected List<Number> calculateTickValues(double arg0, Object arg1) {
            List<Number> ticks = new ArrayList<Number>();
            double tickUnit = tickUnitProperty.get();
            double start = Math.floor(getLowerBound() / tickUnit) * tickUnit;
            for (double value = start; value < getUpperBound(); value += tickUnit) {
                ticks.add(value);
            }
            return ticks;
        }

        @Override
        protected void setRange(Object range, boolean arg1) {
            // not sure how this is used??
        }
        @Override
        protected Object getRange() {
            return null;
        }

        @Override
        protected String getTickMarkLabel(Number label) {
            return label.toString();
        }

        public SimpleObjectProperty<Double> getTickUnitProperty() {
            return tickUnitProperty;
        }

        public void setTickUnit(double tickUnit) {
            tickUnitProperty.set(tickUnit);
        }

    }

}
