package fr.java.plugins.impl;

import java.beans.BeanInfo;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Path;
import java.util.Random;

import fr.java.beans.Bean;
import fr.java.patterns.adapter.Adapter;
import fr.java.plugins.lang.Plugin;
import fr.java.plugins.lang.PluginDescriptor;

@Plugin(name="PluginModuleProxy")
public class PluginModuleProxy implements InvocationHandler, PluginDescriptor, Adapter {
	private static final long serialVersionUID = 1L;

	Object    			reference;
	Bean 				proxy;
	InvocationHandler 	handler;
	
	BeanInfo 			beanInfo;

	public PluginModuleProxy(Path _path) throws InstantiationException {
		super();

		reference = _path;
		proxy     = (Bean) Proxy.newProxyInstance(Bean.class.getClassLoader(), new Class[] { Bean.class }, handler = this );
	}

	@Override
    public Object invoke(Object _proxy, Method _method, Object[] _args) throws Throwable {
    	switch(_method.getName()) {
    	case "getName"   				: return false;
    	case "getBeanDescriptor" 		: return false;
    	case "getPropertyNames"   		: return null;
    	case "getPropertyDescriptors" 	: return null;
    	case "getProperty" 				: return null;
    	case "getReference" 			: return _args != null ? getReference((boolean) _args[0]) : getReference();
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

	public String toString() {
		return "<B> " + reference.getClass().getSimpleName() + (proxy != null ? "*" : "");
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
