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
**/
package fr.java.beans.proxies;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import fr.java.beans.Bean;
import fr.java.beans.reflect.Reflector;
import fr.java.patterns.adapter.Adapter;

class BeanProxy implements InvocationHandler, Bean, Adapter {
	private static final long serialVersionUID = 1L;

	Object    			reference;
	Bean 				proxy;
	InvocationHandler 	handler;
	
	BeanInfo 			beanInfo;

	public BeanProxy(Object _object) throws InstantiationException {
		super();

		if(!Reflector.isBean(_object))
			throw new InstantiationException();

		reference = _object;
		proxy     = (Bean) Proxy.newProxyInstance(Bean.class.getClassLoader(), new Class[] { Bean.class }, handler = this );
	}

	@Override
    public Object invoke(Object _proxy, Method _method, Object[] _args) throws Throwable {
    	switch(_method.getName()) {
    	case "getBeanInfo"   			: return false;
    	case "getBeanDescriptor" 		: return false;
    	case "getPropertyNames"   		: return null;
    	case "getPropertyDescriptors" 	: return null;
    	case "getProperty" 				: return null;
    	case "setProperty" 				: return null;
    	default            				: System.err.println("What??? " + _method.getName());
    	}

    	return new Random().nextInt();
	}


	@Override
	public Object 	getReference() {
		return reference;
	}
	@Override
	public Object 	getReference(boolean _deepSearch) {
		if(!_deepSearch)
			return reference;

		Object realReference = reference;
		while(realReference instanceof Adapter)
			realReference = ((Adapter) realReference).getReference();

		return realReference;
	}

	@Override
	public BeanInfo getBeanInfo() {
		if(beanInfo == null)
			beanInfo = Reflector.getBeanInfo(reference);
		return beanInfo;
	}

	@Override
	public BeanDescriptor getBeanDescriptor() {
		return getBeanInfo().getBeanDescriptor();
	}

	@Override
	public Collection<String> getPropertyNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<PropertyDescriptor> getPropertyDescriptors() {
		return Arrays.asList( getBeanInfo().getPropertyDescriptors() );
	}

	@Override
	public Object getProperty(String _name) {
		return null;
	}

	@Override
	public <V> void setProperty(String _name, V _value) {
		// TODO Auto-generated method stub
		
	}

	public String toString() {
		return "<B> " + reference.getClass().getSimpleName() + (proxy != null ? "*" : "");
	}

}
