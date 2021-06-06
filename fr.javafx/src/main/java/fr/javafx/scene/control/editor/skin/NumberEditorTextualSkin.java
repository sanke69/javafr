package fr.javafx.scene.control.editor.skin;

import fr.java.lang.enums.Primitive;
import fr.javafx.scene.control.editor.NumberEditor;
import fr.javafx.scene.control.editor.validator.DoubleValidator;
import fr.javafx.scene.control.editor.validator.LongValidator;
import fr.javafx.scene.control.editor.validator.NumericValidator;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class NumberEditorTextualSkin extends TextField implements Skin<NumberEditor> {
	private final NumberEditor     control;
	private final NumericValidator validator;

	public NumberEditorTextualSkin(NumberEditor _skinnable) {
		this(_skinnable, null);
	}
	public NumberEditorTextualSkin(NumberEditor _skinnable, Font _font) {
		super(_skinnable.getValue()+"");
		control   = _skinnable;
		validator = getValidator( control.getNumberType(), control.getMin(), control.getMax() );

		if(_font != null)
			setFont(_font);

		textProperty()	        . addListener(validator::handleChangeText);
		control.valueProperty()	. addListener(validator::handleChangeValue);
		
		prefWidthProperty().bind(control.widthProperty());
	}

	@Override
	public NumberEditor 					getSkinnable() {
		return control;
	}
	@Override
	public Node 							getNode() {
		return this;
	}
	@Override
	public void 							dispose() {
		;
	}

	private NumericValidator 				getValidator(Primitive _primitive) {
		switch(_primitive) {
		case BYTE    : return new LongValidator(control.valueProperty(), textProperty(),   false, (long) Byte.MIN_VALUE, (long) Byte.MAX_VALUE);
		case SHORT   : return new LongValidator(control.valueProperty(), textProperty(),   false, (long) Short.MIN_VALUE, (long) Short.MAX_VALUE);
		case INTEGER : return new LongValidator(control.valueProperty(), textProperty(),   false, (long) Integer.MIN_VALUE, (long) Integer.MAX_VALUE);
		case LONG    : return new LongValidator(control.valueProperty(), textProperty(),   false);
		case FLOAT   : return new DoubleValidator(control.valueProperty(), textProperty(), false, (double) Float.MIN_VALUE, (double) Float.MAX_VALUE);
		case DOUBLE  : return new DoubleValidator(control.valueProperty(), textProperty(), false);
		default      : throw new IllegalArgumentException();
		}
	}
	private NumericValidator 				getValidator(Primitive _primitive, Number _min, Number _max) {
		switch(_primitive) {
		case BYTE    : return new LongValidator(control.valueProperty(), textProperty(),   false, _min.byteValue(), _max.byteValue());
		case SHORT   : return new LongValidator(control.valueProperty(), textProperty(),   false, _min.shortValue(), _max.shortValue());
		case INTEGER : return new LongValidator(control.valueProperty(), textProperty(),   false, _min.intValue(), _max.intValue());
		case LONG    : return new LongValidator(control.valueProperty(), textProperty(),   false, _min.longValue(), _max.longValue());
		case FLOAT   : return new DoubleValidator(control.valueProperty(), textProperty(), false, _min.floatValue(), _max.floatValue());
		case DOUBLE  : return new DoubleValidator(control.valueProperty(), textProperty(), false, _min.doubleValue(), _max.doubleValue());
		default      : throw new IllegalArgumentException();
		}
	}

}
