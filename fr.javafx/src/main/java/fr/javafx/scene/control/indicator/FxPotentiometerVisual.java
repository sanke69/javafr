package fr.javafx.scene.control.indicator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

import fr.javafx.behavior.Visual;

public class FxPotentiometerVisual implements Visual<FxPotentiometer> {
	private final FxPotentiometer 	control;

	private final AnchorPane 	anchorPane = new AnchorPane();

	private final AnchorPane	potentiometerView;
	private final Circle		circle;
	private final Rotate		rotate	= new Rotate(0, 25, 25, 0, Rotate.Z_AXIS);
	
	private final Effect      	normal, hover;

	private double	dragStart;
	private double	initialValue;

	public FxPotentiometerVisual(FxPotentiometer _control) {
		super();
		control = _control;

		anchorPane.resize(100, 100);

		potentiometerView = new AnchorPane();
		potentiometerView.resize(50, 50);
		circle = new Circle(25);
		circle.setFill(new Color(0.215d, 0.215d, 0.215d, 1));

		DropShadow shadow = new DropShadow();
		shadow.setRadius(2);
		circle.setEffect(shadow);

		potentiometerView.getChildren().add(circle);
		circle.relocate(0, 0);

		Polygon arrow = new Polygon();
		arrow.getPoints().setAll(0d, 0d, 10d, 0d, 5d, 10d);
		potentiometerView.getChildren().add(arrow);
		arrow.relocate(20, 41);

		normal = createPotentiometerLighting();
		hover  = createPotentiometerLightingOver();

		control.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				getSkinnable().requestLayout();
			}
		});

		control.setEffect(normal);

		anchorPane.getChildren().add(potentiometerView);
		potentiometerView.relocate(25, 0);
		potentiometerView.getTransforms().add(rotate);

		control.centralButtonProperty().addListener(new ChangeListener<ButtonBase>() {
			@Override
			public void changed(ObservableValue<? extends ButtonBase> observable, ButtonBase oldValue,
					ButtonBase newValue) {
				if(oldValue != null) {
					anchorPane.getChildren().remove(oldValue);
				}

				if(newValue != null) {
					anchorPane.getChildren().add(0, newValue);
					getSkinnable().forceLayout();
				}

			}
		});
		if(control.getCentralButton() != null) {
			shadow = new DropShadow();

			control.getCentralButton().setEffect(shadow);
			control.getCentralButton().setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, new CornerRadii(16), Insets.EMPTY)));
			control.getCentralButton().relocate(10, 60);

			anchorPane.getChildren().add(0, control.getCentralButton());
		}

//		getChildren().add(anchorPane);
		getSkinnable().forceLayout();
	}

	@Override
	public FxPotentiometer getSkinnable() {
		return control;
	}

	@Override
	public Node getNode() {
		return anchorPane;
	}

//	@Override
	protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
		anchorPane.resize(contentWidth, contentHeight);

		potentiometerView.resize(50, 50);

		FxPotentiometer potentiometer = getSkinnable();

		/*
		double potentiometerViewX = contentX + contentWidth / 2 - potentiometerView.getWidth() / 2;
		double potentiometerViewY = contentY + contentHeight / 2 - potentiometerView.getHeight() / 2;
		potentiometerView.relocate(potentiometerViewX, potentiometerViewY);
		*/
		double unitePerDegree = (360 
									- potentiometer.getRotationBoundaryMinInDegree() 
									- potentiometer.getRotationBoundaryMaxInDegree()) 
										/ (potentiometer.getMax() - potentiometer.getMin());

		double angle = unitePerDegree * (potentiometer.getValue() - potentiometer.getMin())
									+ potentiometer.getRotationBoundaryMinInDegree();

		rotate.setAngle(angle);

		if(dragStart == 0) {
			potentiometerView.setOnMousePressed(me -> {
				dragStart = me.getSceneX();
				initialValue = potentiometer.getValue();
			});

			potentiometerView.setOnMouseDragged(me -> {
				double move = dragStart - me.getSceneX();
				double newValue = initialValue - (move * potentiometer.getSensibility());
				potentiometer.setValue(newValue);
				me.consume();
			});
		}

		// ButtonBase centralButton = potentiometer.getCentralButton();
		// if (centralButton != null) {
		// // centralButton.resize(24, 24);
		//
		// centralButton.setBackground(new Background(new
		// BackgroundFill(Color.LIGHTYELLOW, new CornerRadii(16),
		// Insets.EMPTY)));
		//
		// double buttonHeight = centralButton.getLayoutBounds().getHeight();
		//
		// centralButton.relocate(potentiometerViewX - 7, potentiometerViewY +
		// potentiometerView.getHeight()
		// - buttonHeight + 7);
		// }
		//
		// if (valueLabel != null) {
		// valueLabel.relocate(15, 15);
		// }

	}

	private Lighting createPotentiometerLighting() {
		Lighting potentiometerLighting = new Lighting();
		Light light = new Light.Point(80, 40, 100, Color.WHITE);
		potentiometerLighting.setLight(light);
		return potentiometerLighting;
	}

	private Lighting createPotentiometerLightingOver() {
		Lighting potentiometerLightingOver = new Lighting();
		Light light = new Light.Point(80, 40, 200, Color.WHITE);
		potentiometerLightingOver.setLight(light);
		return potentiometerLightingOver;
	}

}
