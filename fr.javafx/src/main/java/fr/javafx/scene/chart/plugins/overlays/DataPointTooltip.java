/**
 * JavaFR
 * Copyright (C) 2007-?XYZ  Steve PECHBERTI <steve.pechberti@laposte.net>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.javafx.scene.chart.plugins.overlays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import javafx.util.StringConverter;

import fr.javafx.scene.chart.XY;
import fr.javafx.scene.chart.XYChartUtils;
import fr.javafx.scene.chart.plugins.AbstractDataFormattingPlugin;

public class DataPointTooltip<X, Y> extends AbstractDataFormattingPlugin<X, Y> {
	public static final String STYLE_CLASS_LABEL = "chart-datapoint-tooltip-label";

	public class DataPoint {
		final  Series<X, Y>  series;
		final  Data<X, Y>    data;
		double               distanceFromMouse;

		DataPoint(Series<X, Y> _series, Data<X, Y> _data) {
			super();

			series = _series;
			data   = _data;
		}

	}

	public final BiFunction<Series<X, Y>, Pair<X, Y>, String> DEFAULT_LABEL_UPDATER    = (s, pt) -> {
		Axis<X> xAxis = s.getChart().getXAxis();
		Axis<Y> yAxis = s.getChart().getYAxis();

		StringConverter<X>      xValueFormatter;
		StringConverter<Number> xNumberFormatter;
        if(XYChartUtils.Axes.isValueAxis(xAxis))
        	xNumberFormatter = (StringConverter<Number>) XYChartUtils.Axes.toValueAxis(xAxis).getTickLabelFormatter();
        else if(xAxis instanceof XY.Axis)
        	xValueFormatter  = null;

		StringConverter<Y>      yValueFormatter;
		StringConverter<Number> yNumberFormatter;
        if(XYChartUtils.Axes.isValueAxis(yAxis))
        	yNumberFormatter = (StringConverter<Number>) XYChartUtils.Axes.toValueAxis(yAxis).getTickLabelFormatter();

		
		
		
		
		
		StringBuilder sb = new StringBuilder();

		sb  . append( s.getName() )
			. append("\n");

		StringConverter<?> scX = null;

		return sb.toString();
	};
	public static final double 								DEFAULT_PICKING_DISTANCE = 5d;

	
	private <T> StringConverter<T> 					getValueFormatter(Axis<T> axis, StringConverter<T> formatter, StringConverter<T> defaultFormatter) {
        StringConverter<T> valueFormatter = formatter;

        if (valueFormatter == null && XYChartUtils.Axes.isValueAxis(axis))
            valueFormatter = (StringConverter<T>) XYChartUtils.Axes.toValueAxis(axis).getTickLabelFormatter();

        if (valueFormatter == null)
            valueFormatter = defaultFormatter;

        return valueFormatter;
    }
	
	
	
	private static final int LABEL_X_OFFSET = 15;
	private static final int LABEL_Y_OFFSET = 5;

	private final Label 										label;
	private final ObjectProperty<Function<DataPoint, String>> 	labelUpdater;
	private final DoubleProperty 								pickingDistance;

	public DataPointTooltip() {
		this(DEFAULT_PICKING_DISTANCE, null);
	}
	public DataPointTooltip(double _pickingDistance) {
		this(_pickingDistance, null);
	}
	public DataPointTooltip(double _pickingDistance, Function<DataPoint, String> _labelUpdater) {
		super();

		labelUpdater    = new SimpleObjectProperty<Function<DataPoint, String>>(_labelUpdater);
		pickingDistance = new SimpleDoubleProperty(this, "pickingDistance", _pickingDistance) {
			@Override
			protected void invalidated() {
				if (get() <= 0)
					throw new IllegalArgumentException("The " + getName() + " must be a positive value");
			}
		};

		label = new Label();
		label . getStyleClass().add(STYLE_CLASS_LABEL);

		registerMouseEventHandler(MouseEvent.MOUSE_MOVED, this::updateToolTip);
	}

	public final void 												setLabelUpdater(Function<DataPoint, String> _labelUpdater) {
		labelUpdater.set(_labelUpdater);
	}
	public final Function<DataPoint, String> 					getLabelUpdater() {
		return labelUpdater.get();
	}
	public final ObjectProperty<Function<DataPoint, String>> 	labelUpdaterProperty() {
		return labelUpdater;
	}

	public final void 												setPickingDistance(double distance) {
		pickingDistance.set(distance);
	}
	public final double 											getPickingDistance() {
		return pickingDistance.get();
	}
	public final DoubleProperty 									pickingDistanceProperty() {
		return pickingDistance;
	}

	private void 							updateToolTip(MouseEvent event) {
		Bounds plotAreaBounds = getChartPane().getPlotAreaBounds();
		DataPoint dataPoint = findDataPoint(event, plotAreaBounds);

		if (dataPoint == null) {
			getChartChildren().remove(label);
			return;
		}

		updateLabel(event, plotAreaBounds, dataPoint);

		if (!getChartChildren().contains(label)) {
			getChartChildren().add(label);
			label.requestLayout();
		}
	}
	private void 							updateLabel(MouseEvent event, Bounds plotAreaBounds, DataPoint dataPoint) {
		if(labelUpdater != null) {
			label.setText(dataPoint.series.getName() + "\n" + formatDataPoint(dataPoint));
			
		} else 
			label.setText(dataPoint.series.getName() + "\n" + formatDataPoint(dataPoint));

		double mouseX    = event.getX();
		double mouseY    = event.getY();
		double width     = label.prefWidth(-1);
		double height    = label.prefHeight(width);

		double xLocation = mouseX + LABEL_X_OFFSET;
		double yLocation = mouseY - LABEL_Y_OFFSET - height;

		if (xLocation + width > plotAreaBounds.getMaxX())
			xLocation = mouseX - LABEL_X_OFFSET - width;

		if (yLocation < plotAreaBounds.getMinY())
			yLocation = mouseY + LABEL_Y_OFFSET;

		label.resizeRelocate(xLocation, yLocation, width, height);
	}

	private DataPoint 						findDataPoint(MouseEvent event, Bounds plotAreaBounds) {
		if (!plotAreaBounds.contains(event.getX(), event.getY()))
			return null;

		Point2D mouseLocation = getLocationInPlotArea(event);
		DataPoint nearestDataPoint = null;

		List<XYChart<X, Y>> charts = new ArrayList<>(getCharts());

		Collections.reverse(charts);
		for (XYChart<X, Y> chart : charts) {
			DataPoint point = findNearestDataPointWithinPickingDistance(chart, mouseLocation);
			if (nearestDataPoint == null
			|| (point != null && point.distanceFromMouse < nearestDataPoint.distanceFromMouse))
				nearestDataPoint = point;
		}
		return nearestDataPoint;
	}
	private DataPoint 						findNearestDataPointWithinPickingDistance(XYChart<X, Y> chart, Point2D mouseLocation) {
		DataPoint nearestDataPoint = null;

		X xValue = toDataPoint(chart.getYAxis(), mouseLocation).getXValue();
		for (DataPoint dataPoint : findPointsToCheck(chart, xValue)) {
			Node node = dataPoint.data.getNode();
			if (node != null && node.isVisible()) {
				if (node.getBoundsInParent().contains(mouseLocation)) {
					dataPoint.distanceFromMouse = 0;
					return dataPoint;
				}
			} else {
				Point2D displayPoint = toDisplayPoint(chart.getYAxis(), dataPoint.data);
				dataPoint.distanceFromMouse = displayPoint.distance(mouseLocation);

				if (displayPoint.distance(mouseLocation) <= getPickingDistance() && (nearestDataPoint == null
						|| dataPoint.distanceFromMouse < nearestDataPoint.distanceFromMouse))
					nearestDataPoint = dataPoint;
			}
		}
		return nearestDataPoint;
	}
	private List<DataPoint> 				findPointsToCheck(XYChart<X, Y> chart, X xValue) {
		if (xValue instanceof Number)
			return findNeighborPoints(chart, ((Number) xValue).doubleValue());

		List<DataPoint> points = new LinkedList<>();
		for (Series<X, Y> series : chart.getData())
			for (Data<X, Y> data : series.getData())
				points.add(new DataPoint(series, data));

		return points;
	}
	private List<DataPoint> 				findNeighborPoints(XYChart<X, Y> chart, double searchedX) {
		List<DataPoint> points = new LinkedList<>();
		for (Series<X, Y> series : chart.getData()) {
			Pair<DataPoint, DataPoint> neighborPoints = findNeighborPoints(series, searchedX);
			if (neighborPoints.getKey() != null)
				points.add(neighborPoints.getKey());

			if (neighborPoints.getValue() != null)
				points.add(neighborPoints.getValue());

		}

		return points;
	}
	private Pair<DataPoint, DataPoint> 		findNeighborPoints(Series<X, Y> series, double searchedX) {
		List<Data<? extends Number, Y>> seriesData = castXToNumber(series);

		int prevIndex = -1;
		int nextIndex = -1;
		double prevX = Double.MIN_VALUE;
		double nextX = Double.MAX_VALUE;

		for (int i = 0, size = seriesData.size(); i < size; i++) {
			double currentX = seriesData.get(i).getXValue().doubleValue();

			if (currentX < searchedX) {
				if (prevX < currentX) {
					prevIndex = i;
					prevX = currentX;
				}
			} else if (nextX > currentX) {
				nextIndex = i;
				nextX = currentX;
			}
		}
		DataPoint prevPoint = (prevIndex == -1) ? null : new DataPoint(series, series.getData().get(prevIndex));
		DataPoint nextPoint = (nextIndex == -1 || nextIndex == prevIndex) ? null : new DataPoint(series, series.getData().get(nextIndex));

		return new Pair<>(prevPoint, nextPoint);
	}

	private String 							formatDataPoint(DataPoint dataPoint) {
		return formatData(dataPoint.series.getChart().getYAxis(), dataPoint.data);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Data<? extends Number, Y>> castXToNumber(Series series) {
		return series.getData();
	}

}
