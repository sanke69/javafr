package fr.javafx.sdk.controls.transforms;

import fr.java.maths.geometry.plane.transformations.generic.Euclidian2D;
import fr.javafx.scene.control.actionner.FxDPad;
import fr.javafx.scene.control.indicator.FxPotentiometer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class Euclidian2DControl extends Control {
	private Euclidian2DControlBehavior behavior;

	private BooleanProperty 			translationEnabled;
	private FxDPad 						translation;
	private DoubleProperty 				translationXProperty;
	private DoubleProperty 				translationYProperty;

	private BooleanProperty 			rotationEnabled;
	private FxPotentiometer 				rotation;
	private DoubleProperty 				rotationProperty;
	
	private ObjectProperty<Euclidian2D> transform;

	public Euclidian2DControl() {
		super();
		translation 		 = new FxDPad();
		rotation 			 = new FxPotentiometer();

		translationEnabled   = new SimpleBooleanProperty(true);
		rotationEnabled      = new SimpleBooleanProperty(true);

		translationXProperty = new SimpleDoubleProperty(0);
		translationYProperty = new SimpleDoubleProperty(0);
		rotationProperty     = new SimpleDoubleProperty(0);

		transform            = new SimpleObjectProperty<Euclidian2D>(new Euclidian2D());

		translationXProperty.addListener((_obs, _old, _new) -> {
			transform.set(Euclidian2D.of(translationXProperty.get(), translationYProperty.get(), rotationProperty.get()));
		});
		translationYProperty.addListener((_obs, _old, _new) -> {
			transform.set(Euclidian2D.of(translationXProperty.get(), translationYProperty.get(), rotationProperty.get()));
		});
		rotationProperty.addListener((_obs, _old, _new) -> {
			transform.set(Euclidian2D.of(translationXProperty.get(), translationYProperty.get(), rotationProperty.get()));
		});

		rotation.valueProperty().bindBidirectional(rotationProperty);

		behavior = new Euclidian2DControlBehavior(this);
	}

	@Override
	protected Skin<Euclidian2DControl> createDefaultSkin() {
		return new Euclidian2DControlSkin(this);
	}

	public FxDPad 					getTranslationControl() {
		return translation;
	}
	public FxPotentiometer 			getRotationControl() {
		return rotation;
	}

	public BooleanProperty 			translationEnabledProperty() {
		return translationEnabled;
	}
	public boolean 					isTranslationEnabled() {
		return translationEnabled.get();
	}
	public void 					setTranslationEnabled(boolean _enable) {
		translationEnabled.set(_enable);
	}

	public BooleanProperty 			rotationEnabledProperty() {
		return rotationEnabled;
	}
	public boolean 					isRotationEnabled() {
		return rotationEnabled.get();
	}
	public void 					setRotationEnabled(boolean _enable) {
		rotationEnabled.set(_enable);
	}
	
	
	public ReadOnlyDoubleProperty 	translationXProperty() {
		return translationXProperty;
	}
	public double 					getTranslationX() {
		return translationXProperty.doubleValue();
	}
	public void 					setTranslationX(double _x) {
		translationXProperty.set(_x);
	}

	public ReadOnlyDoubleProperty 	translationYProperty() {
		return translationYProperty;
	}
	public double 					getTranslationY() {
		return translationYProperty.doubleValue();
	}
	public void 					setTranslationY(double _y) {
		translationYProperty.set(_y);
	}

	public ReadOnlyDoubleProperty 	rotationProperty() {
		return rotationProperty;
	}
	public double 					getRotation() {
		return rotationProperty.doubleValue();
	}
	public void 					setRotation(double _inDegree) {
		rotationProperty.set(_inDegree);
	}

	public ReadOnlyObjectProperty<Euclidian2D> 	transformProperty() {
		return transform;
	}
	public Euclidian2D 							getTransformation() {
		return transform.get();
	}

}
