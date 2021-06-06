package fr.java.beans.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import fr.java.beans.impl.events.SetPropertyChangeEvent;
import fr.java.beans.properties.BeanProperty;
import fr.java.beans.properties.listeners.BeanPropertyCollectionChangeListener;
import fr.java.beans.properties.listeners.BeanPropertyListChangeListener;
import fr.java.beans.properties.listeners.BeanPropertySetChangeListener;

public /*abstract*/ class BeanPropertyListAdapter<T> extends BeanPropertySet<T> implements BeanProperty<Set<T>>, Set<T> {

	BeanPropertySet<T> 						setInstance;
	BeanPropertySetChangeListener<T> 		setListener;

	BeanPropertyList<T> 					listInstance;
	BeanPropertyListChangeListener<T> 		listListener;

	BeanPropertyCollection<T> 				collectionInstance;
	BeanPropertyCollectionChangeListener<T> collectionListener;

	public BeanPropertyListAdapter(BeanPropertySet<T> _setProperty) {
		this(null, "setProperty", _setProperty);
	}
	public BeanPropertyListAdapter(String _propertyName, BeanPropertySet<T> _setProperty) {
		this(null, _propertyName, _setProperty);
	}
	public BeanPropertyListAdapter(Object _bean, String _propertyName, BeanPropertySet<T> _setProperty) {
		super(_bean, _propertyName, null);
		setInstance = _setProperty;
		setListener = (prop, _change) -> fireSetPropertyChange(_change);
		
		setInstance.addListener(setListener);
	}

	/**
	 * Unsafe usage,
	 * DO NOT RESPECT 'SET' DESIGN PATTERN
	 */
	public BeanPropertyListAdapter(BeanPropertyList<T> _listProperty) {
		this(null, "setProperty", _listProperty);
	}
	public BeanPropertyListAdapter(String _propertyName, BeanPropertyList<T> _listProperty) {
		this(null, _propertyName, _listProperty);
	}
	public BeanPropertyListAdapter(Object _bean, String _propertyName, BeanPropertyList<T> _listProperty) {
		super(_bean, _propertyName, null);
		listInstance = _listProperty;
		listListener = (prop, _changes) -> {
			while(_changes.next()) {
				if(_changes.wasAdded()) {
//					for(int i = _changes.getFrom(); i < _changes.getTo(); ++i) {
//						System.out.println("ADD " + i + " -> " + _changes.getList().get(i));
//					}
					for(T i : _changes.getAddedSubList()) {
//						System.out.println("ADD " + i + "(" + _changes.getFrom() + ")" );
						fireSetPropertyChange(new SetPropertyChangeEvent.Change<T>(delegate) {
							@Override public T getElementAdded() 	{ return i; }
							@Override public T getElementRemoved() 	{ return null; }
						});
					}
				} else if(_changes.wasRemoved()) {
//					for(int i = _changes.getFrom(); i < _changes.getTo(); ++i) {
//						System.out.println("REMOVE " + i + " -> " + _changes.getList().get(i));
//					}
					for(T i : _changes.getRemoved()) {
//						System.out.println("REMOVE " + i + "(" + _changes.getFrom() + ")" );
						fireSetPropertyChange(new SetPropertyChangeEvent.Change<T>(delegate) {
							@Override public T getElementAdded() 	{ return null; }
							@Override public T getElementRemoved() 	{ return i; }
						});
					}
				}
				else if(_changes.wasPermutated())
					System.out.println("PERMUTE");
				else if(_changes.wasReplaced())
					System.out.println("REPLACED");
				else if(_changes.wasUpdated())
					System.out.println("UPDATE");
			}
			
		};
		listInstance.addListener(listListener);
	}

	/**
	 * Unsafe usage,
	 * DO NOT RESPECT 'SET' DESIGN PATTERN
	 */
	public BeanPropertyListAdapter(BeanPropertyCollection<T> _collectionProperty) {
		this(null, "setProperty", _collectionProperty);
	}
	public BeanPropertyListAdapter(String _propertyName, BeanPropertyCollection<T> _collectionProperty) {
		this(null, _propertyName, _collectionProperty);
	}
	public BeanPropertyListAdapter(Object _bean, String _propertyName, BeanPropertyCollection<T> _collectionProperty) {
		super(_bean, _propertyName, null);
		collectionInstance = _collectionProperty;
		collectionListener = (prop, _change) -> {
			if(_change.wasAdded()) {
				for(T i : _change.getAdded()) {
					fireSetPropertyChange(new SetPropertyChangeEvent.Change<T>(delegate) {
						@Override public T getElementAdded() 	{ return i; }
						@Override public T getElementRemoved() 	{ return null; }
					});
				}
			} else if(_change.wasRemoved()) {
				for(T i : _change.getRemoved()) {
					fireSetPropertyChange(new SetPropertyChangeEvent.Change<T>(delegate) {
						@Override public T getElementAdded() 	{ return null; }
						@Override public T getElementRemoved() 	{ return i; }
					});
				}
			}
		};
		collectionInstance.addListener(collectionListener);
	}

	@Override
	public void set(Set<T> _value) {
		throw new UnsupportedOperationException();
	}

	public int 					size() { 
    	if(setInstance != null)
    		return setInstance.size();
    	if(listInstance != null)
    		return listInstance.size();
    	if(collectionInstance != null)
    		return collectionInstance.size();
		throw new UnsupportedOperationException();
	}
    public boolean 				isEmpty() { 
    	if(setInstance != null)
    		return setInstance.isEmpty();
    	if(listInstance != null)
    		return listInstance.isEmpty();
    	if(collectionInstance != null)
    		return collectionInstance.isEmpty();
		throw new UnsupportedOperationException();
	}
    public boolean 				contains(Object o) { 
    	if(setInstance != null)
    		return setInstance.contains(o);
    	if(listInstance != null)
    		return listInstance.contains(o);
    	if(collectionInstance != null)
    		return collectionInstance.contains(o);
		throw new UnsupportedOperationException();
	}
    public Object[] 			toArray() { 
    	if(setInstance != null)
    		return setInstance.toArray();
    	if(listInstance != null)
    		return listInstance.toArray();
    	if(collectionInstance != null)
    		return collectionInstance.toArray();
		throw new UnsupportedOperationException();
	}
    public <U> U[] 				toArray(U[] a) { 
    	if(setInstance != null)
    		return setInstance.toArray(a);
    	if(listInstance != null)
    		return listInstance.toArray(a);
    	if(collectionInstance != null)
    		return collectionInstance.toArray(a);
		throw new UnsupportedOperationException();
	}
    public boolean 				containsAll(Collection<?> c) { 
    	if(setInstance != null)
    		return setInstance.containsAll(c);
    	if(listInstance != null)
    		return listInstance.containsAll(c);
    	if(collectionInstance != null)
    		return collectionInstance.containsAll(c);
		throw new UnsupportedOperationException();
	}

    @Override
	public Iterator<T> 			iterator() {
    	if(setInstance != null)
    		return new InnerIterator(setInstance.iterator());
    	if(listInstance != null)
    		return new InnerIterator(listInstance.iterator());
    	if(collectionInstance != null)
    		return new InnerIterator(collectionInstance.iterator());
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean 				add(T e) {
		if(setInstance != null)
			return setInstance.add(e);
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean 				remove(Object o) {
		if(setInstance != null)
			return setInstance.remove(o);
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean 				addAll(Collection<? extends T> c) {
		if(setInstance != null)
			return setInstance.addAll(c);
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean 				retainAll(Collection<?> c) {
		if(setInstance != null)
			return setInstance.retainAll(c);
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean 				removeAll(Collection<?> c) {
		if(setInstance != null)
			return setInstance.removeAll(c);
		throw new UnsupportedOperationException();
	}
	@Override
	public void 				clear() {
		if(setInstance != null)
			setInstance.clear();
		throw new UnsupportedOperationException();
	}

}
