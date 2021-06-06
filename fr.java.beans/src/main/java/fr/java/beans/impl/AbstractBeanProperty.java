/**
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * @file     SimpleBeanProperty.java
 * @version  0.0.0.1
 * @date     2018/04/27
 * 
**/
package fr.java.beans.impl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

import fr.java.beans.properties.ReadOnlyBeanProperty;
import fr.java.beans.properties.listeners.BeanPropertyChangeListener;
import fr.java.beans.properties.listeners.vetoable.BeanPropertyVetoableListener;

public abstract class AbstractBeanProperty<T> implements ReadOnlyBeanProperty<T> {

	private final     Object          		bean;
	private final     String          		name;
	private transient PropertyChangeSupport changeSupport;
	private transient VetoableChangeSupport	vetoableSupport;

	protected AbstractBeanProperty(String _propertyName) {
		this(null, _propertyName);
	}
	protected AbstractBeanProperty(Object _bean, String _propertyName) {
		super();
		bean = _bean;
		name = _propertyName;
	}

	public Object 				getBean() {
		return bean != null ? bean : this;
	}
	@Override
	public String 				getName() {
		return name;
	}

	@Override
	public final void 			addListener(PropertyChangeListener _listener) {
		propertyChangeSupport().addPropertyChangeListener(getName(), _listener);
	}
	@Override
	public final void 			addListener(BeanPropertyChangeListener<T> _listener) {
		propertyChangeSupport().addPropertyChangeListener(getName(), _listener);
	}
	@Override
	public final void 			addListener(String _propertyName, PropertyChangeListener _listener) {
		propertyChangeSupport().addPropertyChangeListener(_propertyName, _listener);
	}
	@Override
	public final <U> void 		addListener(String _propertyName, BeanPropertyChangeListener<U> _listener) {
		propertyChangeSupport().addPropertyChangeListener(_propertyName, _listener);
	}

	@Override
	public final void 			removeListener(PropertyChangeListener _listener) {
		propertyChangeSupport().removePropertyChangeListener(getName(), _listener);
	}
	@Override
	public final void 			removeListener(BeanPropertyChangeListener<T> _listener) {
		propertyChangeSupport().removePropertyChangeListener(getName(), _listener);
	}
	@Override
	public final void 			removeListener(String _propertyName, PropertyChangeListener _listener) {
		propertyChangeSupport().removePropertyChangeListener(getName(), _listener);
	}
	@Override
	public final <U> void 		removeListener(String _propertyName, BeanPropertyChangeListener<U> _listener) {
		propertyChangeSupport().removePropertyChangeListener(getName(), _listener);
	}

	protected final void 		firePropertyChange(PropertyChangeEvent _evt) {
		propertyChangeSupport().firePropertyChange(_evt);
	}
	protected final void 		firePropertyChange(T _oldValue, T _newValue) {
		propertyChangeSupport().firePropertyChange(new PropertyChangeEvent(getBean(), getName(), _oldValue, _newValue));
	}
	protected final void 		firePropertyChange(String _property, PropertyChangeEvent _evt) {
		propertyChangeSupport().firePropertyChange(new PropertyChangeEvent(this, _property, _evt.getOldValue(), _evt.getNewValue()));
	}
	protected final <U> void 	firePropertyChange(String _property, U _oldValue, U _newValue) {
		propertyChangeSupport().firePropertyChange(new PropertyChangeEvent(this, _property, _oldValue, _newValue));
	}

	final PropertyChangeSupport propertyChangeSupport() {
		if(changeSupport == null)
			if((getBean() != null) && (getBean() instanceof AbstractBean))
				changeSupport = ((AbstractBean) getBean()).propertyChangeSupport();
			else if((getBean() != null) && (getBean() != this) && (getBean() instanceof AbstractBeanProperty) && (((AbstractBeanProperty<?>) getBean()).propertyChangeSupport() != null)) 
				changeSupport = ((AbstractBeanProperty<?>) getBean()).propertyChangeSupport();
			else changeSupport = new PropertyChangeSupport(this);

		return changeSupport;
	}

	
	// 
	// USED FOR NON READ ONLY INHERITAGE
	// 
	public final void 			addVetoer(VetoableChangeListener listener) {
		vetoableChangeSupport().addVetoableChangeListener(getName(), listener);
	}
	public final void 			removeVetoer(VetoableChangeListener listener) {
		vetoableChangeSupport().removeVetoableChangeListener(getName(), listener);
	}

	public final void 			addVetoer(BeanPropertyVetoableListener<T> listener) {
		vetoableChangeSupport().addVetoableChangeListener(getName(), listener);
	}

	protected final void 		fireVetoableChange(PropertyChangeEvent _evt) throws PropertyVetoException {
		vetoableChangeSupport().fireVetoableChange(_evt);
	}
	protected final void 		fireVetoableChange(T _oldValue, T _newValue) throws PropertyVetoException {
		vetoableChangeSupport().fireVetoableChange(new PropertyChangeEvent(getBean(), getName(), _oldValue, _newValue));
	}
	protected final void 		fireVetoableChange(String _property, PropertyChangeEvent _evt) throws PropertyVetoException {
		vetoableChangeSupport().fireVetoableChange(new PropertyChangeEvent(this, _property, _evt.getOldValue(), _evt.getNewValue()));
	}
	protected final <U> void 	fireVetoableChange(String _property, U _oldValue, U _newValue) throws PropertyVetoException {
		vetoableChangeSupport().fireVetoableChange(new PropertyChangeEvent(this, _property, _oldValue, _newValue));
	}

	final VetoableChangeSupport vetoableChangeSupport() {
		if(vetoableSupport == null)
			if((getBean() != null) && (getBean() instanceof AbstractBean))
				vetoableSupport = ((AbstractBean) getBean()).vetoableChangeSupport();
			else if((getBean() != null) && (getBean() != this) && (getBean() instanceof AbstractBeanProperty) && (((AbstractBeanProperty<?>) getBean()).vetoableChangeSupport() != null))
				vetoableSupport = ((AbstractBeanProperty<?>) getBean()).vetoableChangeSupport();
			else vetoableSupport = new VetoableChangeSupport(this);

		return vetoableSupport;
	}

}