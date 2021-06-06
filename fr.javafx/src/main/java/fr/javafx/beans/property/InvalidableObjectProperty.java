package fr.javafx.beans.property;

import javafx.beans.property.SimpleObjectProperty;

public class InvalidableObjectProperty<T> extends SimpleObjectProperty<T> {

    public InvalidableObjectProperty() {
        super();
    }
    public InvalidableObjectProperty(T initialValue) {
        super(initialValue);
    }

    public void invalidate() {
        super.fireValueChangedEvent();
    }

}
