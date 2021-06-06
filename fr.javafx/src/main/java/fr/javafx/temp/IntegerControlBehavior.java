package fr.javafx.temp;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.input.KeyEvent;

public class IntegerControlBehavior {

	void toto() {

		//newSpinner.setOnScroll((e) -> { newSpinner.getValueFactory().setValue(newSpinner.getValue() + (e.getDeltaY() > 0 ? 1 : - 1)); e.consume(); });

	}
	
	private EventHandler<? super KeyEvent> restrictToDigitalInput() {
		return (keyEvent) -> {
			if(!"0123456789".contains(keyEvent.getCharacter()))
				keyEvent.consume();
		};
	}
	private ChangeListener<Integer> assertDigitalInputInRange(final Spinner<Integer> _spinner) {
		return (_obs, _old, _new) -> {
			IntegerSpinnerValueFactory factory = (IntegerSpinnerValueFactory) _spinner.getValueFactory();

			if(_new == null) {
				factory.setValue(0);
				return;
			}

			if(factory.getMin() > _new || _new > factory.getMax())
				factory.setValue(_old);

			factory.setValue(_new);
		};
	}

}
