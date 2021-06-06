package fr.javafx.temp.editors.distribution;

import java.util.Optional;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;

import fr.javafx.beans.propertysheet.PropertyItems;
import fr.javafx.beans.propertysheet.PropertySheet;
import fr.javafx.beans.propertysheet.api.PropertyEditor;

public class DistributionPropertyEditor<T> implements PropertyEditor<T> {

    private final Button btnEditor;
    private final PropertySheet.Item item;
    private final ObjectProperty<T> value = new SimpleObjectProperty<>();

    public DistributionPropertyEditor(PropertySheet.Item item) {
        this.item = item;
        if (item.getValue() != null) {
            btnEditor = new Button(item.getValue().toString());
            value.set((T) item.getValue());
        } else {
            btnEditor = new Button("<empty>");
        }
        btnEditor.setAlignment(Pos.CENTER_LEFT);
        btnEditor.setOnAction((ActionEvent event) -> {
            displayPopupEditor();
        });
    }

    private void displayPopupEditor() {
        PopupPropertySheet<T> sheet = new PopupPropertySheet<>(item, this);
        sheet.setPrefWidth(500);
        Alert alert = new Alert(Alert.AlertType.NONE);
//        alert.setWidth(700);
//        alert.setResizable(true);
        alert.setResizable(false);
        alert.getDialogPane().setContent(sheet);
        alert.setTitle("Popup Property Editor");
        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType testButton = new ButtonType("Change Postcode", ButtonBar.ButtonData.OTHER);
        alert.getButtonTypes().addAll(ButtonType.CANCEL, saveButton, testButton);
        /*
        final Button btTest = (Button) alert.getDialogPane().lookupButton(testButton);
        btTest.addEventFilter(ActionEvent.ACTION, event -> {
            Address addr = null;
            if (item.getValue() != null && item.getValue() instanceof Address) {
                addr = (Address) item.getValue();
            } else if (sheet.getBean() != null && sheet.getBean() instanceof Address) {
                addr = (Address) sheet.getBean();
            }
            if (addr != null) {
                int pc = (int) (Math.random() * 8000);
                addr.setPostcode(Integer.toString(pc));
            }
            event.consume();
        });
        */
        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && saveButton.equals(response.get())) {
            item.setValue(sheet.getBean());
            btnEditor.setText(sheet.getBean().toString());
        }
    }

    @Override
    public Node getEditor() {
        return btnEditor;
    }

    @Override
    public T getValue() {
        return value.get();
    }

    @Override
    public void setValue(T t) {
        value.set(t);
        if (t != null) {
            btnEditor.setText(t.toString());
        }
    }

    private class PopupPropertySheet<T> extends BorderPane {

        private final PropertyEditor<T> owner;
        private final PropertySheet sheet;
        private final PropertySheet.Item item;
        private T bean;

        public PopupPropertySheet(PropertySheet.Item item, PropertyEditor<T> owner) {

            this.item = item;
            this.owner = owner;
            sheet = new PropertySheet();
            setCenter(sheet);
//            installButtons();
            setMinHeight(500);

            initSheet();

        }

        public T getBean() {
            return bean;
        }

        private void initSheet() {
            if (item.getValue() == null) {

                bean = null;
                try {
                    bean = (T) item.getType().newInstance();
                } catch (InstantiationException | IllegalAccessException ex) {
                    ex.printStackTrace();
                    return;
                }
                if (bean == null) {
                    return;
                }
            } else {
                bean = (T) item.getValue();
            }

            Service<?> service = new Service<ObservableList<PropertySheet.Item>>() {
                @Override
                protected Task<ObservableList<PropertySheet.Item>> createTask() {
                    return new Task<ObservableList<PropertySheet.Item>>() {
                        @Override
                        protected ObservableList<PropertySheet.Item> call() throws Exception {
                            return PropertyItems.getProperties(bean);
                        }
                    };
                }

            };
            service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @SuppressWarnings("unchecked")
                @Override
                public void handle(WorkerStateEvent e) {
                	/*
                    for (PropertySheet.Item i : (ObservableList<PropertySheet.Item>) e.getSource().getValue()) {
                    	/*
                        if (i instanceof ItemForProperty && ((ItemForProperty) i).getPropertyDescriptor() instanceof ExtPropertyDescriptor) {
                            ItemForProperty bi = (ItemForProperty) i;
                            bi.setEditable(((ExtPropertyDescriptor) bi.getPropertyDescriptor()).isEditable());
                        }* /
                    }*/
                    sheet.getItems().setAll((ObservableList<PropertySheet.Item>) e.getSource().getValue());
                }
            });
            service.start();

        }
    }

}
