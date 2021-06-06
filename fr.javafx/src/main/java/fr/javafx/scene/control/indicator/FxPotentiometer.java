package fr.javafx.scene.control.indicator;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Side;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Skin;
import javafx.scene.control.Slider;

import fr.javafx.behavior.AdvancedSkin;

public class FxPotentiometer extends Slider {
    private Side 			initialOrientation			= null;
	private DoubleProperty 	rotationBoundaryMinInDegree = new SimpleDoubleProperty(0);
	private DoubleProperty 	rotationBoundaryMaxInDegree = new SimpleDoubleProperty(0);
	private DoubleProperty 	sensibility;

	private SimpleObjectProperty<ButtonBase> centralButton = new SimpleObjectProperty<>();

	public FxPotentiometer() {
		super(0.0, 360.0, 0.5);
		initialOrientation = Side.TOP;
		sensibility  = new SimpleDoubleProperty(1d);
	}

	@Override
	protected  Skin<FxPotentiometer> 			createDefaultSkin() {
		return new AdvancedSkin<FxPotentiometer>(this, FxPotentiometerVisual::new);
	}

	public  SimpleObjectProperty<ButtonBase> 	centralButtonProperty() {
		return centralButton;
	}

	public  void 								setCentralButton(ButtonBase centralNode) {
		this.centralButton.set(centralNode);
	}
	public  ButtonBase 							getCentralButton() {
		return centralButton.get();
	}

	public  DoubleProperty 						sensibilityProperty() {
		return sensibility;
	}

	public  void 								setSensibility(double _sensibility) {
		sensibility.set(_sensibility);
	}
	public  double 								getSensibility() {
		return sensibility.get();
	}

	public  DoubleProperty 						rotationBoundaryMinInDegreeProperty() {
		return rotationBoundaryMinInDegree;
	}

	public  void 								setRotationBoundaryMinInDegree(double rotationBoundaryMinInDegree) {
		this.rotationBoundaryMinInDegree.set(rotationBoundaryMinInDegree);
	}
	public  double 								getRotationBoundaryMinInDegree() {
		return rotationBoundaryMinInDegree.get();
	}

	public  DoubleProperty 						rotationBoundaryMaxInDegreeProperty() {
		return rotationBoundaryMaxInDegree;
	}

	public  void 								setRotationBoundaryMaxInDegree(double rotationBoundaryMaxInDegree) {
		this.rotationBoundaryMaxInDegree.set(rotationBoundaryMaxInDegree);
	}
	public  double 								getRotationBoundaryMaxInDegree() {
		return rotationBoundaryMaxInDegree.get();
	}

	public  void 								forceLayout() {
		setNeedsLayout(true);
		layout();
	}

}