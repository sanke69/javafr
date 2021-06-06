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
 * @file     ListenableBean.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.beans;

import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;
import java.io.Serializable;

import fr.java.lang.Listenable;

public interface ListenableBean extends /*Bean,*/ Listenable, Serializable {

	public boolean 						hasPropertyChangeListeners(String _propertyName);
	public boolean 						hasPropertyVetoableListeners(String _propertyName);

	public PropertyChangeListener[] 	getPropertyListeners();
	public PropertyChangeListener[] 	getPropertyListeners(String _propertyName);

	public VetoableChangeListener[] 	getVetoableChangeListeners();
	public VetoableChangeListener[] 	getVetoableChangeListeners(String _propertyName);

//	public void 						addListener		(PropertyChangeListener _listener);
//	public void 						removeListener	(PropertyChangeListener _listener);

	public void 						addListener		(String _propertyName, PropertyChangeListener _listener);
	public void 						removeListener	(String _propertyName, PropertyChangeListener _listener);

//	public void 						addVetoer		(VetoableChangeListener _listener);
//	public void 						removeVetoer	(VetoableChangeListener _listener);

	public void 						addVetoer		(String _propertyName, VetoableChangeListener _listener);
	public void 						removeVetoer	(String _propertyName, VetoableChangeListener _listener);

}
