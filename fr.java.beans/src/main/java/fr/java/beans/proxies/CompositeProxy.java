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
 * @file     CompositeProxy.java
 * @version  0.0.0.1
 * @date     2017/04/27
 * 
**/
package fr.java.beans.proxies;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import fr.java.beans.reflect.Reflector;
import fr.java.patterns.adapter.Adapter;
import fr.java.patterns.composite.Component;
import fr.java.patterns.composite.Composite;

class CompositeProxy implements InvocationHandler, Composite.Single, Component, Adapter {
	private static final Map<Object, CompositeProxy> proxyMapper;

	static {
		proxyMapper = new HashMap<Object, CompositeProxy>();
	}

	public static CompositeProxy of(Object _o)  throws InstantiationException {
		if(proxyMapper.containsKey(_o))
			return proxyMapper.get(_o);
		return new CompositeProxy(_o);
	}
	public static CompositeProxy of(Object _o, Supplier<?> _getter, Consumer<?> _setter)  throws InstantiationException {
		if(proxyMapper.containsKey(_o))
			return proxyMapper.get(_o);
		return new CompositeProxy(_o);
	}
	public static CompositeProxy of(Composite _composite) {
		if(proxyMapper.containsKey(_composite))
			return proxyMapper.get(_composite);
		return new CompositeProxy(_composite);
	}
	public static CompositeProxy of(Component _component) {
		if(proxyMapper.containsKey(_component)) {
			System.out.println("Proxy found!");
			return proxyMapper.get(_component);
		}
		return new CompositeProxy(_component);
	}

	final boolean 		isComposite,
						isComponent;
	final Object    	reference;

	Composite	 		proxy;
	InvocationHandler 	handler;

	CompositeProxy(Object _object) throws InstantiationException {
		super();

		Object object = _object instanceof Adapter ? ((Adapter) _object).getReference() : _object;

		isComposite = Reflector.isComposite(object);
		isComponent = Reflector.isComponent(object);
		reference   = _object;

		initializeProxy();
	}
	CompositeProxy(Composite _composite) {
		super();

		isComposite = true;
		isComponent = true;
		reference   = _composite;

		initializeProxy();
	}
	CompositeProxy(Component _component) {
		super();

		isComposite = Reflector.isComposite(_component);
		isComponent = true;
		reference   = _component;

		initializeProxy();
	}

	@Override
    public Object invoke(Object _proxy, Method _method, Object[] _args) throws Throwable {
    	switch(_method.getName()) {
    	case "hasParent"   : return !isComponent ? false : getParent() != null;
    	case "hasChildren" : return !isComposite ? false : getChildren() != null && !getChildren().isEmpty();
    	case "getParent"   : return getParent();
    	case "getChildren" : return getChildren();
    	default            : System.err.println("What??? " + _method.getName());
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
	public boolean 					hasParent() {
		if(isComponent) {
			if(reference instanceof Component)
				return ((Component) reference).hasParent();
			else
				return Reflector.getParent(reference) != null;
		}

		return false;
	}
	@Override
	public Composite 				getParent() {
		if(isComponent) {
			if(reference instanceof Component)
				return ((Component.Single) reference).getParent();
			else {
				Object o = Reflector.getParent(reference);
				try {
					return CompositeProxy.of(o);
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	@Override
	public boolean 					hasChildren() {
		if(isComposite) {
			if(reference instanceof Composite)
				return ((Composite) reference).hasChildren();
			else {
				Object realReference = reference instanceof Adapter ? ((Adapter) reference).getReference() : reference;
				return Reflector.getChildren(realReference) != null && !Reflector.getChildren(realReference).isEmpty();
			}
		}

		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Set<? extends Component.Single> getChildren() {
		if(isComposite) {
			if(reference instanceof Composite)
				return ((Composite.Single) reference).getChildren();
			else {
				Object         realReference = reference instanceof Adapter ? ((Adapter) reference).getReference() : reference;
				Set<?>         objs          = Reflector.getChildren(realReference);
				Set<Component.Single> composites    = new HashSet<Component.Single>();
				try {
					for(Object obj : objs)
						composites.add(CompositeProxy.of(obj));
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
				return composites;
			}
		}

		return (Set<? extends Component.Single>) Collections.EMPTY_SET;
	}

	private void initializeProxy() {
		proxy = (Composite) Proxy.newProxyInstance(CompositeProxy.class.getClassLoader(), new Class[] { Composite.class, Component.class }, handler = this );
		
		proxyMapper.put(reference, this);
	}

	public String toString() {
		if(reference instanceof Adapter)
			return "<C> " + reference.toString() + (proxy != null ? "*" : "");

		return "<C> " + reference.getClass().getSimpleName() + (proxy != null ? "*" : "");
	}
	
}
