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
package fr.javafx.scene.chart.plugins.behavior;

import java.util.function.Predicate;

import fr.javafx.scene.chart.XY;
import fr.javafx.scene.chart.XYChartUtils;
import fr.javafx.utils.MouseEvents;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;

public class ChartPanner extends AbstractBehaviorPlugin<Number, Number> {

    private Point2D previousMouseLocation = null;

    public ChartPanner() {
        this(evt -> { if(evt instanceof MouseEvent) return MouseEvents.isOnlyPrimaryButtonDown((MouseEvent) evt) && MouseEvents.modifierKeysUp((MouseEvent) evt); return false; });
    }
    public ChartPanner(Predicate<InputEvent> _mouseFilter) {
    	super(_mouseFilter, Cursor.CLOSED_HAND);

    	interactionModeProperty().addListener((_mode) -> {
        	if(getInteractionMode() == null || getInteractionMode() == XY.Constraint.NONE) {
        		setInteractionMode( XY.Constraint.BOTH );
        		throw new RuntimeException("The 'InteractionMode' can't not be null or set to NONE with ChartPanner");
        	}});

        registerMouseEventHandler(MouseEvent.MOUSE_PRESSED,  this::onMousePressed);
        registerMouseEventHandler(MouseEvent.MOUSE_DRAGGED,  this::onMouseDragged);
        registerMouseEventHandler(MouseEvent.MOUSE_RELEASED, this::onMouseReleased);
    }

    private void 	onMousePressed(MouseEvent _me) {
    	if(getDefaultInputFilter() != null && !getDefaultInputFilter().test(_me))
    		return ;

        installCursor();

        previousMouseLocation = getLocationInPlotArea(_me);

        _me.consume();
    }
    private void 	onMouseDragged(MouseEvent _me) {
        if(previousMouseLocation == null)
        	return ;

        XY.Constraint mode   = getInteractionMode().AND( getInteractionModeInContext(_me) );
        Point2D       cursor = getLocationInPlotArea(_me);

        for(XYChart<Number, Number> chart : getCharts())
            panChart(chart, cursor, mode);

        previousMouseLocation = cursor;

        _me.consume();
    }
    private void 	onMouseReleased(MouseEvent _me) {
        if(previousMouseLocation == null)
        	return ;

        uninstallCursor();

        previousMouseLocation = null;

        _me.consume();
    }

    private void 	panChart(XYChart<Number, Number> _chart, Point2D _mouseLocation, XY.Constraint _mode) {
    	panChartNumber(_chart, _mouseLocation, _mode);
    }

    private void 	panChartNumber(XYChart<Number, Number> _chart, Point2D _mouseLocation, XY.Constraint _mode) {
        Data<Number, Number> prevData = toDataPoint(_chart.getYAxis(), previousMouseLocation);
        Data<Number, Number> data     = toDataPoint(_chart.getYAxis(), _mouseLocation);

        double xOffset = prevData.getXValue().doubleValue() - data.getXValue().doubleValue();
        double yOffset = prevData.getYValue().doubleValue() - data.getYValue().doubleValue();

        ValueAxis<?> xAxis = XYChartUtils.Axes.toValueAxis(_chart.getXAxis());
        if (!XYChartUtils.Axes.hasBoundedRange(xAxis) && _mode.allowsHor()) {
            xAxis.setAutoRanging(false);
            xAxis.setLowerBound(xAxis.getLowerBound() + xOffset);
            xAxis.setUpperBound(xAxis.getUpperBound() + xOffset);
        }
        ValueAxis<?> yAxis = XYChartUtils.Axes.toValueAxis(_chart.getYAxis());
        if (!XYChartUtils.Axes.hasBoundedRange(yAxis) && _mode.allowsVer()) {
            yAxis.setAutoRanging(false);
            yAxis.setLowerBound(yAxis.getLowerBound() + yOffset);
            yAxis.setUpperBound(yAxis.getUpperBound() + yOffset);
        }
    }

}
