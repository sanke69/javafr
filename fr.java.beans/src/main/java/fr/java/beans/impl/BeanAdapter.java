package fr.java.beans.impl;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.List;

import fr.java.beans.utils.Beans;

public class BeanAdapter extends AbstractBean {
	private static final long serialVersionUID = -1L;

	public BeanAdapter(Object _o) {
		super();
		
		if(Beans.isTaggedBean(_o.getClass())) {
			BeanInfo nfo = Beans.getBeanInfo(_o.getClass());

			for(PropertyDescriptor prop : nfo.getPropertyDescriptors()) {
				String propertyName = prop.getName();

				System.out.println("Add property: " + propertyName);
			}
			
		}
	}

	public List<String>					getPropertyNames() { return null; }

	public <TYPE> void					setProperty(String _name, TYPE _value) throws IllegalArgumentException {}
	public <TYPE> TYPE					getProperty(String _name) { return null; }

}
