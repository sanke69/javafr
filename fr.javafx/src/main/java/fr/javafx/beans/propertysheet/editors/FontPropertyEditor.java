package fr.javafx.beans.propertysheet.editors;

import java.util.Optional;

import fr.javafx.beans.propertysheet.PropertySheet.Item;
import fr.javafx.beans.propertysheet.api.BasePropertyEditor;
import fr.javafx.beans.propertysheet.api.BasePropertyEditorDialog;
import fr.javafx.stage.impl.dialogs.FontSelectorDialog;

import javafx.beans.value.ObservableValue;
import javafx.scene.text.Font;

public class FontPropertyEditor extends BasePropertyEditor<Font, BasePropertyEditorDialog<Font>> {
	
	public FontPropertyEditor(Item _property) {
		super(_property, new BasePropertyEditorDialog<Font>() {
			@Override
			protected Class<Font> getType() {
				return Font.class;
			}

			@Override
			protected String objectToString(Font font) {
				return font == null ? "" : String.format("%s, %.1f", font.getName(), font.getSize());
			}

			@Override
			protected Font edit(Font font) {
				FontSelectorDialog dlg = new FontSelectorDialog(font);
				Optional<Font> optionalFont = dlg.showAndWait();
				return optionalFont.get();
			}
		});
	}

	@Override
	protected ObservableValue<Font> getObservableValue() {
		return getEditor().getObjectProperty();
	}

	@Override
	public void setValue(Font value) {
		getEditor().getObjectProperty().set(value);
	}

}
