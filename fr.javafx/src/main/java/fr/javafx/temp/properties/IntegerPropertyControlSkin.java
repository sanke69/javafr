package fr.javafx.temp.properties;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class IntegerPropertyControlSkin extends HBox implements Skin<IntegerPropertyControl> {
	private final IntegerPropertyControl control;

	private final Label  label, value;
	private final Slider slider;

	public IntegerPropertyControlSkin(IntegerPropertyControl _control) {
		super();
		control = _control;

		label  = new Label(control.getName());
		value  = new Label(String.valueOf(control.getValue()));
		slider = new Slider(control.getMin(), control.getMax(), control.getValue());
		slider.valueProperty().addListener((obs, oldval, newVal) -> slider.setValue(newVal.intValue()));
		HBox.setHgrow(slider, Priority.ALWAYS);

		label.setPrefWidth(175);
		value.setPrefWidth(50);

		value.textProperty().bind(Bindings.convert(slider.valueProperty()));

		slider.minProperty().bind(control.minProperty());
		slider.maxProperty().bind(control.maxProperty());
		slider.valueProperty().bindBidirectional(control.valueProperty());

		getChildren().addAll(label, value, slider);
	}

	@Override
	public IntegerPropertyControl getSkinnable() {
		return control;
	}

	@Override
	public Node getNode() {
		return this;
	}

	@Override
	public void dispose() {
		
	}

}
