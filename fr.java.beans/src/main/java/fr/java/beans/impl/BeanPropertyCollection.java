package fr.java.beans.impl;

import java.util.Collection;
import java.util.List;

import fr.java.beans.impl.events.CollectionPropertyChangeEvent;
import fr.java.beans.properties.BeanProperty;
import fr.java.beans.properties.listeners.BeanPropertyCollectionChangeListener;

public abstract class BeanPropertyCollection<T> extends AbstractBeanProperty<Collection<T>> implements BeanProperty<Collection<T>>, Collection<T> { // , ListenableList<T>

	protected BeanPropertyCollection() {
		this(null, "collection", null);
	}
	protected BeanPropertyCollection(List<T> _initialValue) {
		this(null, "collection", _initialValue);
	}
	protected BeanPropertyCollection(String _propertyName) {
		this(null, _propertyName, null);
	}
	protected BeanPropertyCollection(String _propertyName, List<T> _initialValue) {
		this(null, _propertyName, _initialValue);
	}
	protected BeanPropertyCollection(Object _bean, String _propertyName) {
		this(_bean, _propertyName, null);
	}
	protected BeanPropertyCollection(Object _bean, String _propertyName, List<T> _initialValue) {
		super(_bean, _propertyName);
	}

	public void 				addListener(BeanPropertyCollectionChangeListener<T> listener) {
		propertyChangeSupport().addPropertyChangeListener(getName(), listener);
	}
	public void 				removeListener(BeanPropertyCollectionChangeListener<T> listener) {
		propertyChangeSupport().removePropertyChangeListener(getName(), listener);
	}

	protected final void 		fireCollectionPropertyChange(CollectionPropertyChangeEvent.Change<T> _changes) {
		propertyChangeSupport().firePropertyChange( new CollectionPropertyChangeEvent<T>(getBean() != null ? getBean() : this, getName(), _changes) );
	}

}
