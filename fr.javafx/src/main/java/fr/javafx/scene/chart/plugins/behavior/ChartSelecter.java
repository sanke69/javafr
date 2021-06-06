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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import fr.javafx.scene.chart.XY;
import fr.javafx.scene.chart.XY.SelectionEvent;
import fr.javafx.scene.chart.XYChartUtils;
import fr.javafx.utils.MouseEvents;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class ChartSelecter extends AbstractBehaviorPlugin<Number, Number> {
    public static final String 	STYLE_CLASS_SELECTION_RECT	= "chart-selection-rect";
    public static final int 	DEFAULT_SELECTION_MIN_SIZE	= 3;

    private final ObjectProperty<XY.Constraint> 		selectionMode 					= new SimpleObjectProperty<XY.Constraint>(XY.Constraint.BOTH) {
        @Override
        protected void invalidated() {
        	if(get() == null || get() == XY.Constraint.NONE) {
        		set( XY.Constraint.BOTH );
        		throw new RuntimeException("The " + getName() + " must not be null or set to NONE");
        	}
        }
    };
    private final ObjectProperty<Rectangle2D> 			selectionProperty				= new SimpleObjectProperty<Rectangle2D>();

    private final BooleanProperty 						selectionRemaining 				= new SimpleBooleanProperty(false);
//  private final ObjectProperty<Consumer<Rectangle2D>> selectionAction 				= new SimpleObjectProperty<Consumer<Rectangle2D>>();
//  private EventHandler<SelectionEvent> 				selectionHandler				= null;

    private final Rectangle 							selectionRectangle 				= new Rectangle();
    private Point2D 									selectionStartPoint 			= null;
    private Point2D 									selectionEndPoint 				= null;

    private Paint 										originalPaint;

    public ChartSelecter() {
        this(	evt -> { if(evt instanceof MouseEvent) return MouseEvents.isOnlyPrimaryButtonDown((MouseEvent) evt) && MouseEvents.modifierKeysUp((MouseEvent) evt); return false; }, 
        		null
        	);
    }
    public ChartSelecter(Consumer<Rectangle2D> _onSelection) {
    	this(	evt -> { if(evt instanceof MouseEvent) return MouseEvents.isOnlyPrimaryButtonDown((MouseEvent) evt) && MouseEvents.modifierKeysUp((MouseEvent) evt); return false; }, 
    			_onSelection
        	);
    }
    public ChartSelecter(Predicate<InputEvent> _mouseFilter) {
    	this(_mouseFilter, null);
    }
    public ChartSelecter(Predicate<InputEvent> _mouseFilter, Consumer<Rectangle2D> _onSelection) {
    	this(XY.Constraint.BOTH, _mouseFilter, _onSelection, false);
    }
    public ChartSelecter(XY.Constraint _mode, Predicate<InputEvent> _filter, Consumer<Rectangle2D> _onSelection, boolean _isRemaining) {
    	super(_mode, _filter, Cursor.CROSSHAIR);

    	interactionModeProperty().addListener((im) -> {
        	if(getInteractionMode() == null || getInteractionMode() == XY.Constraint.NONE) {
        		setInteractionMode( XY.Constraint.BOTH );
        		throw new RuntimeException("The 'InteractionMode' can't not be null or set to NONE with ChartSelecter");
        	}});

        selectionRectangle.setManaged(false);
        selectionRectangle.getStyleClass().add(STYLE_CLASS_SELECTION_RECT);
        getChartChildren().add(selectionRectangle);

        registerMouseEventHandler(MouseEvent.MOUSE_PRESSED,  this::onMousePressed);
        registerMouseEventHandler(MouseEvent.MOUSE_DRAGGED,  this::onMouseDragged);
        registerMouseEventHandler(MouseEvent.MOUSE_RELEASED, this::onMouseReleased);

        selectionRemaining.set(_isRemaining);
/*
        selectionAction.addListener((_obs, _old, _new) -> {
        	if(_old != null)
        		selectionRectangle.removeEventHandler(XY.ON_SELECTION, selectionHandler);

        	selectionHandler = null;

        	if(_new != null)
        		selectionRectangle.addEventHandler(XY.ON_SELECTION, selectionHandler = se -> _new.accept(se.getSelection()));
        });

        if(_onSelection != null)
        	selectionAction.set(_onSelection);
*/
    }

    public final void 									setSelectionMode(XY.Constraint mode) {
    	selectionMode.set(mode);
    }
    public final XY.Constraint 							getSelectionMode() {
        return selectionMode.get();
    }
    public final ObjectProperty<XY.Constraint> 			selectionModeProperty() {
        return selectionMode;
    }

	public ObservableValue<Rectangle2D> 				selectionProperty() {
		return selectionProperty;
	}
/*
    public final void 									selectionAction(Consumer<Rectangle2D> mode) {
    	selectionAction.set(mode);
    }
    public final Consumer<Rectangle2D> 					getSelectionAction() {
        return selectionAction.get();
    }
    public final ObjectProperty<Consumer<Rectangle2D>> 	selectionActionProperty() {
        return selectionAction;
    }
*/
    public final <T extends Event> void 				addEventHandler(final EventType<T> eventType, final EventHandler<? super T> eventHandler) {
    	selectionRectangle.addEventHandler(eventType, eventHandler);
    }
    public final <T extends Event> void 				removeEventHandler(final EventType<T> eventType, final EventHandler<? super T> eventHandler) {
    	selectionRectangle.removeEventHandler(eventType, eventHandler);
    }

    private void 										onMousePressed(MouseEvent _me) {
        if (getDefaultInputFilter() != null && !getDefaultInputFilter().test(_me)) {
        	undoSelection();
            _me.consume();
        	return ;
        }
        
        if(originalPaint == null)
        	originalPaint = selectionRectangle.getFill();

        selectionStartPoint = new Point2D(_me.getX(), _me.getY());

        selectionRectangle.setX(selectionStartPoint.getX());
        selectionRectangle.setY(selectionStartPoint.getY());
        selectionRectangle.setWidth(0);
        selectionRectangle.setHeight(0);
        selectionRectangle.setVisible(true);
        selectionRectangle.setFill(originalPaint);

        installCursor();

        _me.consume();
    }
    private void 										onMouseDragged(MouseEvent _me) {
        if (getDefaultInputFilter() != null && !getDefaultInputFilter().test(_me)) {
        	undoSelection();
            _me.consume();
        	return ;
        }

        if(selectionStartPoint == null)
        	return ;

        Bounds plotAreaBounds      = getChartPane().getPlotAreaBounds();
        double selectionRectX      = plotAreaBounds.getMinX();
        double selectionRectY      = plotAreaBounds.getMinY();
        double selectionRectWidth  = plotAreaBounds.getWidth();
        double selectionRectHeight = plotAreaBounds.getHeight();

        selectionEndPoint = XYChartUtils.limitToPlotArea(getChartPane(), _me);

        if(getSelectionMode().allowsHor()) {
            selectionRectX      = Math.min(selectionStartPoint.getX(), selectionEndPoint.getX());
            selectionRectWidth  = Math.abs(selectionEndPoint.getX() - selectionStartPoint.getX());
        }
        if(getSelectionMode().allowsVer()) {
        	selectionRectY      = Math.min(selectionStartPoint.getY(), selectionEndPoint.getY());
        	selectionRectHeight = Math.abs(selectionEndPoint.getY() - selectionStartPoint.getY());
        }

        selectionRectangle.setX(selectionRectX);
        selectionRectangle.setY(selectionRectY);
        selectionRectangle.setWidth(selectionRectWidth);
        selectionRectangle.setHeight(selectionRectHeight);

        _me.consume();
    }
    private void 										onMouseReleased(MouseEvent _me) {
        if (getDefaultInputFilter() != null && !getDefaultInputFilter().test(_me)) {
        	undoSelection();
            _me.consume();
        	return ;
        }

        if(selectionStartPoint == null)
        	return ;

        if(selectionRectangle.getWidth() > DEFAULT_SELECTION_MIN_SIZE && selectionRectangle.getHeight() > DEFAULT_SELECTION_MIN_SIZE)
            performSelection();

        selectionStartPoint = selectionEndPoint = null;

        if(selectionRemaining.get())
        	selectionRectangle.setFill(Color.LIGHTGREEN); 
        else
        	selectionRectangle.setVisible(false);

        uninstallCursor();

        selectionProperty.set(new Rectangle2D(selectionRectangle.getX(), selectionRectangle.getY(), selectionRectangle.getWidth(), selectionRectangle.getHeight()));
        _me.consume();
    }

    private void 										performSelection() {
        Map<XYChart<Number, Number>, Rectangle2D> selectionWindows = new HashMap<>();
        for(XYChart<Number, Number> chart : getCharts())
            selectionWindows.put(chart, getSelectionWindow(chart));

        for(Entry<XYChart<Number, Number>, Rectangle2D> entry : selectionWindows.entrySet())
            performSelectionOn(entry.getKey(), entry.getValue());

        getChartPane().requestLayout();
    }
    private void 										undoSelection() {
    	selectionRectangle.setVisible(false);
        uninstallCursor();
    }

    private Rectangle2D 								getSelectionWindow(XYChart<Number, Number> chart) {
        double minX = selectionRectangle.getX();
        double minY = selectionRectangle.getY() + selectionRectangle.getHeight();
        double maxX = selectionRectangle.getX() + selectionRectangle.getWidth();
        double maxY = selectionRectangle.getY();

        Data<Number, Number> dataMin = toDataPoint(chart.getYAxis(), getChartPane().toPlotArea(minX, minY));
        Data<Number, Number> dataMax = toDataPoint(chart.getYAxis(), getChartPane().toPlotArea(maxX, maxY));

        double dataMinX = dataMin.getXValue().doubleValue();
        double dataMinY = dataMin.getYValue().doubleValue();
        double dataMaxX = dataMax.getXValue().doubleValue();
        double dataMaxY = dataMax.getYValue().doubleValue();

        double dataRectWidth = dataMaxX - dataMinX;
        double dataRectHeight = dataMaxY - dataMinY;

        return new Rectangle2D(dataMinX, dataMinY, dataRectWidth, dataRectHeight);
    }
    private void 										performSelectionOn(XYChart<Number, Number> _chart, Rectangle2D _selection) {
    	selectionRectangle.fireEvent(new XY.SelectionEvent(_selection));
    }

}
