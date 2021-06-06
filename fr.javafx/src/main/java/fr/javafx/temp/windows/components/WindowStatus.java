package fr.javafx.temp.windows.components;

import fr.java.math.geometry.plane.Point2D;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class WindowStatus {
	// STATE
	private BooleanProperty	dockingProperty		= new SimpleBooleanProperty(false);
	private BooleanProperty	floatingProperty	= new SimpleBooleanProperty(false);
	private BooleanProperty	stagingProperty		= new SimpleBooleanProperty(false);

	private BooleanProperty	minimizedProperty	= new SimpleBooleanProperty(false);
	private BooleanProperty	maximizedProperty	= new SimpleBooleanProperty(false);
	private BooleanProperty	fullscreenProperty	= new SimpleBooleanProperty(false);

	private BooleanProperty	draggingProperty	= new SimpleBooleanProperty(false);

	private ObjectProperty<Point2D>	dragStart	= new SimpleObjectProperty<Point2D>();

	public final boolean isDocked() {
		return dockingProperty.get();
	}
	public final void setDocked_unsafe(boolean _docking) {
		dockingProperty.set(_docking);
	}
	public final ReadOnlyBooleanProperty dockingProperty() {
		return dockingProperty;
	}
	
	public final boolean isFloating() {
		return floatingProperty.get();
	}
	public final void setFloating_unsafe(boolean _floating) {
		floatingProperty.set(_floating);
	}
	public final ReadOnlyBooleanProperty floatingProperty() {
		return floatingProperty;
	}

	public final boolean isStaged() {
		return stagingProperty.get();
	}
	public final void setStaged_unsafe(boolean _staging) {
		stagingProperty.set(_staging);
	}
	final ReadOnlyBooleanProperty stagingProperty() {
		return stagingProperty;
	}

	public final boolean isMinimized() {
		return minimizedProperty.get();
	}
	public final void setMinimized(boolean _minimized) {
		minimizedProperty.set(_minimized);
	}
	public final BooleanProperty minimizedProperty() {
		return minimizedProperty;
	}

	public final boolean isMaximized() {
		return maximizedProperty.get();
	}
	public final void setMaximized(boolean _maximized) {
		maximizedProperty.set(_maximized);
	}
	public final BooleanProperty maximizedProperty() {
		return maximizedProperty;
	}

	public final boolean isFullScreen() {
		return fullscreenProperty.get();
	}
	public final void setFullScreen(boolean _fullScreen) {
		fullscreenProperty.set(_fullScreen);
	}
	public final BooleanProperty fullScreenProperty() {
		return fullscreenProperty;
	}

	public final boolean isDragging() {
		return draggingProperty.get();
	}
	public final void setDragging(boolean _dragging) {
		draggingProperty.set(_dragging);
	}
	public final BooleanProperty draggingProperty() {
		return draggingProperty;
	}
	
	public final Point2D getDragOrigin() {
		return dragStart.get();
	}
	public final void setDragOrigin(Point2D _dragStart) {
		dragStart.set(_dragStart);
	}
	public final ObjectProperty<Point2D> dragOriginProperty() {
		return dragStart;
	}

}
