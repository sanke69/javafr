package fr.javafx.temp.properties;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ScalarPropertyControlSkin extends HBox implements Skin<ScalarPropertyControl> {
	private final ScalarPropertyControl control;

	private final Label  label, value;
	private final Slider slider;

	public ScalarPropertyControlSkin(ScalarPropertyControl _control) {
		super();
		control = _control;

		label  = new Label(control.getName());
		value  = new Label(String.valueOf(control.getValue()));
		slider = new Slider(control.getMin(), control.getMax(), control.getValue());
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
	public ScalarPropertyControl getSkinnable() {
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
