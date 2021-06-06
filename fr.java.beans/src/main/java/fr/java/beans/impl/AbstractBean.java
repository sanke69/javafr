/**
 * Copyright (C) 2007-?XYZ Steve PECHBERTI <steve.pechberti@laposte.net>
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
 * @file     SimpleBean.java
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
import java.io.Serializable;

import fr.java.beans.ListenableBean;
import fr.java.beans.properties.listeners.BeanPropertyChangeListener;

public abstract class AbstractBean implements ListenableBean, Serializable {
	private static final long serialVersionUID = 1L;

	private transient PropertyChangeSupport	propertyChangeSupport;
	private transient VetoableChangeSupport	vetoableChangeSupport;

	protected AbstractBean() {
		propertyChangeSupport = new PropertyChangeSupport(this);
		vetoableChangeSupport = new VetoableChangeSupport(this);
	}
	protected AbstractBean(PropertyChangeSupport _propertyListener, VetoableChangeSupport _vetoableListener) {
		if (_propertyListener == null)
			throw new NullPointerException("PropertyChangeSupport must not be null");
		if (_vetoableListener == null)
			throw new NullPointerException("VetoableChangeSupport must not be null");

		propertyChangeSupport = _propertyListener;
		vetoableChangeSupport = _vetoableListener;
	}

	@Override
	public final boolean 					hasPropertyChangeListeners(String propertyName) {
		return propertyChangeSupport.hasListeners(propertyName);
	}
	@Override
	public final boolean 					hasPropertyVetoableListeners(String propertyName) {
		return vetoableChangeSupport.hasListeners(propertyName);
	}

	@Override
	public final PropertyChangeListener[] 	getPropertyListeners() {
		return propertyChangeSupport.getPropertyChangeListeners();
	}
	@Override
	public final PropertyChangeListener[] 	getPropertyListeners(String propertyName) {
		return propertyChangeSupport.getPropertyChangeListeners(propertyName);
	}
/*
	@Override
	public final void 						addListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
	@Override
	public final void 						removeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
*/
	public final void 						addListener(BeanPropertyChangeListener<?> listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	@Override
	public final void 						addListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}
	@Override
	public final void 						removeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	@Override
	public final VetoableChangeListener[] 	getVetoableChangeListeners() {
		return vetoableChangeSupport.getVetoableChangeListeners();
	}
	@Override
	public final VetoableChangeListener[] 	getVetoableChangeListeners(String propertyName) {
		return vetoableChangeSupport.getVetoableChangeListeners(propertyName);
	}
/*
	@Override
	public final void 						addVetoer(VetoableChangeListener listener) {
		vetoableChangeSupport.addVetoableChangeListener(listener);
	}
*/
	@Override
	public final void 						addVetoer(String propertyName, VetoableChangeListener listener) {
		vetoableChangeSupport.addVetoableChangeListener(propertyName, listener);
	}
/*
	@Override
	public final void 						removeVetoer(VetoableChangeListener listener) {
		vetoableChangeSupport.removeVetoableChangeListener(listener);
	}
*/
	@Override
	public final void 						removeVetoer(String propertyName, VetoableChangeListener listener) {
		vetoableChangeSupport.removeVetoableChangeListener(propertyName, listener);
	}

	protected final void 					firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
	protected final void 					firePropertyChange(PropertyChangeEvent evt) {
		propertyChangeSupport.firePropertyChange(evt);
	}

	protected final void 					fireIndexedPropertyChange(String propertyName, int _index, Object oldValue, Object newValue) {
		propertyChangeSupport.fireIndexedPropertyChange(propertyName, _index, oldValue, newValue);
	}

	protected final void 					fireVetoableChange(String propertyName, Object oldValue, Object newValue) throws PropertyVetoException {
		vetoableChangeSupport.fireVetoableChange(propertyName, oldValue, newValue);
	}
	protected final void 					fireVetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
		vetoableChangeSupport.fireVetoableChange(evt);
	}

	PropertyChangeSupport 					propertyChangeSupport() {
		return propertyChangeSupport;
	}
	VetoableChangeSupport 					vetoableChangeSupport() {
		return vetoableChangeSupport;
	}

	@Override
	public Object clone() 					throws CloneNotSupportedException {
		AbstractBean result = (AbstractBean) super.clone();
		result.propertyChangeSupport = new PropertyChangeSupport(result);
		result.vetoableChangeSupport = new VetoableChangeSupport(result);
		return result;
	}

}