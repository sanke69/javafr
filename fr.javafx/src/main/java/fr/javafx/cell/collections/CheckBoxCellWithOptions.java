package fr.javafx.cell.collections;

import java.util.Collection;

import fr.javafx.scene.control.checkcombobox.CheckComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public class CheckBoxCellWithOptions extends VBox {
	private final CheckBox              	checkBox;
	
	private final boolean					enableMultiSelection;
	private final CheckComboBox<String> 	checkComboBox;
	private final ComboBox<String> 			comboBox;

	private ObservableList<String> 			optionList;
//  private SimpleListProperty<String> 		optionList;

	public CheckBoxCellWithOptions(boolean _enableMultiSelection) {
		super();

		enableMultiSelection = _enableMultiSelection;

		optionList    = FXCollections.observableArrayList();
//		optionList    = new SimpleListProperty(FXCollections.<String>observableArrayList());

		checkBox      = new CheckBox("VISUAL");
		checkComboBox = new CheckComboBox<String>();
		comboBox      = new ComboBox<String>();
		getChildren().addAll(checkBox, checkComboBox, comboBox);

		checkBox.setSelected(false);
		checkComboBox.setManaged(false);
		checkComboBox.setVisible(false);
		comboBox.setManaged(false);
		comboBox.setVisible(false);
		
		checkBox.selectedProperty().addListener((_obs, _old, _new) -> {
			if(enableMultiSelection) {
				checkComboBox.setManaged(_new); 
				checkComboBox.setVisible(_new);
			} else {
				comboBox.setManaged(_new); 
				comboBox.setVisible(_new);
			}
		 });
		optionList.addListener((ListChangeListener<String>) (e) -> { updateOptions(); });
	};

	public void 					setText(String _text) {
		checkBox.setText(_text);
	}
	public String 					getText() {
		return checkBox.getText();
	}

	public void 					setTextOptions(Collection<String> _options) {
		optionList.clear();
		optionList.addAll(_options);
	}
	public void 					setTextOptions(String... _options) {
		optionList.clear();
		optionList.addAll(_options);
	}
	public ObservableList<String> 	getTextOptions() {
		return optionList;
	}

	public void 					updateOptions() {
		if(enableMultiSelection) {
			checkComboBox.getItems().clear();
			checkComboBox.getItems().addAll(optionList);
		} else {
			comboBox.getItems().clear();
			comboBox.getItems().addAll(optionList);
		}
	}

};