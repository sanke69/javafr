package fr.javafx.temp.windows.components;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;

public class WindowProperties {

	public static final PseudoClass	FLOATING_PSEUDO_CLASS	= PseudoClass.getPseudoClass("floating");
	public static final PseudoClass	DOCKED_PSEUDO_CLASS		= PseudoClass.getPseudoClass("docked");
	public static final PseudoClass	MAXIMIZED_PSEUDO_CLASS	= PseudoClass.getPseudoClass("maximized");

	public static enum Mode {
		UNKNOWN, FLOATING, DOCKED, STAGED, DISABLED, HIDDEN;
	}

	public static enum Position {
		CENTER,
		LEFT, RIGHT, TOP, BOTTOM;
	}

	public static enum ResizeMode { 
		RESIZE, SCALE 
	};

	// CAPACITY
	private BooleanProperty	mutableProperty		= new SimpleBooleanProperty(true) {
													@Override
													public String getName() {
														return "mutable";
													}
												};
	private BooleanProperty	closableProperty	= new SimpleBooleanProperty(true) {
													@Override
													public String getName() {
														return "closable";
													}
												};
	private BooleanProperty	resizableProperty	= new SimpleBooleanProperty(true) {
													@Override
													public String getName() {
														return "resizable";
													}
												};
	
	private BooleanProperty	floatableProperty	= new SimpleBooleanProperty(true) {
													@Override
													public String getName() {
														return "floatable";
													}
												};
	private BooleanProperty	dockableProperty	= new SimpleBooleanProperty(true) {
													@Override
													public String getName() {
														return "dockable";
													}
												};
	private BooleanProperty	stageableProperty	= new SimpleBooleanProperty(true) {
													@Override
													public String getName() {
														return "stageable";
													}
												};

	// PROPERTY
	private SimpleDoubleProperty	prefWidthProperty	= new SimpleDoubleProperty(0.0) {
															@Override
															public String getName() {
																return "prefWidth";
															}
														};
	private SimpleDoubleProperty	prefHeightProperty	= new SimpleDoubleProperty(0.0) {
															@Override
															public String getName() {
																return "prefHeight";
															}
														};

	private ObjectProperty<ResizeMode>	resizeProperty	= new SimpleObjectProperty<ResizeMode>() {
														@Override
														public String getName() {
															return "resizeMode";
														}
													};

	public final boolean isMutable() {
		return mutableProperty.get();
	}
	public final void setMutable(boolean _mutable) {
		this.mutableProperty.set(_mutable);
	}
	public final BooleanProperty mutableProperty() {
		return mutableProperty;
	}

	public final boolean isClosable() {
		return closableProperty.get();
	}
	public final void setClosable(boolean closable) {
		this.closableProperty.set(closable);
	}
	public final BooleanProperty closableProperty() {
		return closableProperty;
	}

	public final void setResizable(boolean resizable) {
		resizableProperty.set(resizable);
	}
	public final boolean isResizable() {
		return resizableProperty.get();
	}
	public final BooleanProperty resizableProperty() {
		return resizableProperty;
	}

	public final boolean isFloatable() {
		return floatableProperty.get();
	}
	public final void setFloatable(boolean floatable) {
		floatableProperty.set(floatable);
	}
	public final BooleanProperty floatableProperty() {
		return floatableProperty;
	}

	public final boolean isDockable() {
		return dockableProperty.get();
	}
	public final void setDockable(boolean dockable) {
		dockableProperty.set(dockable);
	}
	public final BooleanProperty dockableProperty() {
		return dockableProperty;
	}

	public final boolean isStageable() {
		return stageableProperty.get();
	}
	public final void setStageable(boolean stageable) {
		stageableProperty.set(stageable);
	}
	public final BooleanProperty stageableProperty() {
		return stageableProperty;
	}

	public void setPrefSize(double _w, double _h) {
		prefWidthProperty.set(_w);
		prefHeightProperty.set(_h);
	}

	public double getPrefWidth() {
		return prefWidthProperty.get();
	}
	public void setPrefWidth(double _prefWidth) {
		prefWidthProperty.set(_prefWidth);
	}
	public SimpleDoubleProperty prefWidthProperty() {
		return prefWidthProperty;
	}

	public double getPrefHeight() {
		return prefHeightProperty.get();
	}
	public void getPrefHeight(double _prefHeight) {
		prefHeightProperty.set(_prefHeight);
	}
	public final SimpleDoubleProperty prefHeightProperty() {
		return prefHeightProperty;
	}

	public void setResizeMode(ResizeMode _mode) {
		resizeProperty.set(_mode);
	}
	public ResizeMode getResizeMode() {
		return resizeProperty.get();
	}
	public ObjectProperty<ResizeMode> resizeModeProperty() {
		return resizeProperty;
	}

}
