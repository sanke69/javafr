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

import java.util.function.Consumer;
import java.util.function.Predicate;

import fr.javafx.scene.chart.XY;
import fr.javafx.scene.chart.axis.NumericAxis;
import fr.javafx.utils.MouseEvents;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.chart.Axis;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class ChartClicker extends AbstractBehaviorPlugin<Number, Number> {
    public static final String 	STYLE_CLASS_CLICKER_CROSSLINE	= "chart-clicker-crossline";

    private final ObjectProperty<XY.Constraint> clickMode 		= new SimpleObjectProperty<XY.Constraint>(XY.Constraint.BOTH) {
        @Override
        protected void invalidated() {
        	if(get() == null || get() == XY.Constraint.NONE) {
        		set( XY.Constraint.BOTH );
        		throw new RuntimeException("The " + getName() + " must not be null or set to NONE");
        	}
        }
    };
    private final ObjectProperty<Point2D> 		clickedPoint	= new SimpleObjectProperty<Point2D>();

    private final Line 							horizontalLine 	= new Line();
    private final Line 							verticalLine 	= new Line();

    public ChartClicker() {
        this(	evt -> { if(evt instanceof MouseEvent) return MouseEvents.isOnlyPrimaryButtonDown((MouseEvent) evt) && MouseEvents.modifierKeysUp((MouseEvent) evt); return false; }, 
        		null
        	);
    }
    public ChartClicker(Consumer<Rectangle2D> _onSelection) {
    	this(	evt -> { if(evt instanceof MouseEvent) return MouseEvents.isOnlyPrimaryButtonDown((MouseEvent) evt) && MouseEvents.modifierKeysUp((MouseEvent) evt); return false; }, 
    			_onSelection
        	);
    }
    public ChartClicker(Predicate<InputEvent> _mouseFilter) {
    	this(_mouseFilter, null);
    }
    public ChartClicker(Predicate<InputEvent> _mouseFilter, Consumer<Rectangle2D> _onSelection) {
    	this(XY.Constraint.BOTH, _mouseFilter, _onSelection, false);
    }
    public ChartClicker(XY.Constraint _mode, Predicate<InputEvent> _filter, Consumer<Rectangle2D> _onSelection, boolean _isRemaining) {
    	super(_mode, _filter, Cursor.DISAPPEAR /*Cursor.CROSSHAIR*/);

    	interactionModeProperty().addListener((im) -> {
        	if(getInteractionMode() == null || getInteractionMode() == XY.Constraint.NONE) {
        		setInteractionMode( XY.Constraint.BOTH );
        		throw new RuntimeException("The 'InteractionMode' can't not be null or set to NONE with ChartSelecter");
        	}});

    	horizontalLine.setManaged(false);
    	horizontalLine.getStyleClass().add(STYLE_CLASS_CLICKER_CROSSLINE);
        getChartChildren().add(horizontalLine);

        verticalLine.setManaged(false);
    	verticalLine.getStyleClass().add(STYLE_CLASS_CLICKER_CROSSLINE);
        getChartChildren().add(verticalLine);

        registerMouseEventHandler(MouseEvent.MOUSE_PRESSED,  this::onMousePressed);
        registerMouseEventHandler(MouseEvent.MOUSE_RELEASED, this::onMouseReleased);
        registerMouseEventHandler(MouseEvent.MOUSE_MOVED,    this::onMouseMotion);
        registerMouseEventHandler(MouseEvent.MOUSE_DRAGGED,  this::onMouseMotion);

    }

    public final void 							setClickerMode(XY.Constraint mode) {
    	clickMode.set(mode);
    }
    public final XY.Constraint 					getClickerMode() {
        return clickMode.get();
    }
    public final ObjectProperty<XY.Constraint> 	clickerModeProperty() {
        return clickMode;
    }

	public ObjectProperty<Point2D> 				clickedPointProperty() {
		return clickedPoint;
	}

    private void 								onMousePressed(MouseEvent _me) {
        if (getDefaultInputFilter() != null && !getDefaultInputFilter().test(_me)) {
        	hideCrossLines();
            _me.consume();
        	return ;
        }

        showCrossLines();
        _me.consume();
    }
    private void 								onMouseReleased(MouseEvent _me) {
        if(getDefaultInputFilter() != null && !getDefaultInputFilter().test(_me)) {
        	hideCrossLines();
            _me.consume();
        	return ;
        }

        performClick(_me.getX(), _me.getY());

        hideCrossLines();
        _me.consume();
    }

    private void 								onMouseMotion(MouseEvent _me) {
        if (getDefaultInputFilter() != null && !getDefaultInputFilter().test(_me)) {
        	hideCrossLines();
            _me.consume();
        	return ;
        }

        showCrossLines();
        updateCrossLines(_me.getX(), _me.getY());
        _me.consume();
    }

    private void 								performClick(double _x, double _y) {
    	clickedPoint.set( getPlotValue(_x, _y) );
    }

    private void showCrossLines() {
    	installCursor();
        horizontalLine . setVisible(true);
        verticalLine   . setVisible(true);
    }
    private void hideCrossLines() {
        verticalLine   . setVisible(false);
        horizontalLine . setVisible(false);
        uninstallCursor();
    }
    private void updateCrossLines(double _x, double _y) {
    	Point2D pt = getPlotValue(_x, _y);
    	
    	if(pt == null)
    		return ;

		double X     = getChartPane().getPlotAreaBounds().getMinX();
		double Y     = getChartPane().getPlotAreaBounds().getMinY();
		double W     = getChartPane().getPlotAreaBounds().getWidth();
		double H     = getChartPane().getPlotAreaBounds().getHeight();

		horizontalLine . setStartX (X);
		horizontalLine . setEndX   (X+W);
		horizontalLine . setStartY (_y);
		horizontalLine . setEndY   (_y);

		verticalLine   . setStartX (_x);
		verticalLine   . setEndX   (_x);
		verticalLine   . setStartY (Y);
		verticalLine   . setEndY   (Y+H);

    }

    private Point2D getPlotValue(double _cx, double _cy) {
    	Axis<Number> xaxis = getChartPane().getXAxis();
    	Axis<Number> yaxis = getChartPane().getYAxis();

    	if(xaxis instanceof NumericAxis && yaxis instanceof NumericAxis) {
    		NumericAxis xAxis = (NumericAxis) xaxis;
    		NumericAxis yAxis = (NumericAxis) yaxis;

			double xmin  = xAxis.getRange().lowerBound();
			double xmax  = xAxis.getRange().upperBound();
			double ymin  = yAxis.getRange().lowerBound();
			double ymax  = yAxis.getRange().upperBound();

			Point2D pt   = getChartPane().toPlotArea(new Point2D(_cx, _cy));
			double x     = pt.getX();
			double y     = pt.getY();
			double W     = getChartPane().getPlotAreaBounds().getWidth();
			double H     = getChartPane().getPlotAreaBounds().getHeight();

			double xplot = xmin + x / W * (xmax - xmin);
			double yplot = ymax - y / H * (ymax - ymin);

			return new Point2D(xplot, yplot);
    	}
    	
    	return null;
    }

}
