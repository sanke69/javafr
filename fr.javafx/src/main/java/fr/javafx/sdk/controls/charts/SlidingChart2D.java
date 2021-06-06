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

@Deprecated
public class SlidingChart2D extends Control {

	int slidingSize;

	final LineChart<Number, Number> chart;
	final SlidingAxis 				xAxis;
	final NumberAxis  				yAxis;

	XYChart.Series<Number, Number>       dataChart;
    ObservableList<Data<Number, Number>> data;

    public SlidingChart2D(int _slidingSize) {
    	super();
    	slidingSize = _slidingSize;

    	xAxis = new SlidingAxis();
        xAxis.setAutoRanging(false);
//        xAxis.setTickUnit(5);

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
            xAxis.setLowerBound(data.get(0).getXValue().doubleValue());
            xAxis.setUpperBound(data.get(data.size() - 1).getXValue().doubleValue());
        });
        

        chart.setTitle("Sliding Window");
        xAxis.setLabel("T_ms");
        yAxis.setLabel("Amplitude");

        getStylesheets().add(SlidingChart2D.class.getResource("slidingChart2d.css").toExternalForm());
//        getStyleClass().add("radar");
    }

    public Skin<SlidingChart2D> createDefaultSkin() {
    	return new Skin<SlidingChart2D>() {

			@Override
			public SlidingChart2D getSkinnable() {
				return SlidingChart2D.this;
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
    
    public void addValue(Number _x, Number _y) {
        if(data.size() > slidingSize)
            data.remove(0);

        data.add(new XYChart.Data<Number, Number>(_x, _y));
    }

    private class SlidingAxis extends ValueAxis<Number> {
        private SimpleObjectProperty<Double> tickUnitProperty = new SimpleObjectProperty<Double>(1000d);

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
