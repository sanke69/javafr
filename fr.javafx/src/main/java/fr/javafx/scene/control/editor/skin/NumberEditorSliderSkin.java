package fr.javafx.scene.control.editor.skin;

import fr.javafx.scene.control.editor.NumberEditor;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class NumberEditorSliderSkin  extends HBox implements Skin<NumberEditor> {
	private final NumberEditor control;

	private final Label		value;
	private final Slider	slider;

	public NumberEditorSliderSkin(final NumberEditor _control) {
		super();

		control = _control;

		value  = new Label(_control.getValue().doubleValue() + "");
		slider = new Slider(_control.getMin().doubleValue(), _control.getMax().doubleValue(), _control.getValue().doubleValue());

		HBox.setHgrow(slider, Priority.ALWAYS);
		value.setPrefWidth(50);
		value.textProperty().bind(Bindings.convert(slider.valueProperty()));

		getChildren().addAll(value, slider);
		
		if(_control.getNumberType().isInteger())
			slider.valueProperty().addListener((obs, oldval, newVal) -> control.setValue(newVal.intValue()));
	}

	@Override
	public NumberEditor		getSkinnable() {
		return control;
	}
	@Override
	public Node 			getNode() {
		return this;
	}
	@Override
	public void 			dispose() {
		;
	}

}
