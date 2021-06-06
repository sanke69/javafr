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
 * @file     Instances.java
 * @version  0.0.0.1
 * @date     2015/04/27
 * 
**/
package fr.java.beans.reflect.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Instances {
    private static final String PROPERTY_SEPARATOR = ".";


	public static Object newInstance(Class<?> _class, Object... _args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(_args == null || _args.length == 0)
			return _class.newInstance();

//		Class<?>[] argt  = getAssociatedClasses(_args);
		/*
		new Class<?>[_args.length];
		for(int i = 0; i < _args.length; ++i) {
			argt[i] = _args[i].getClass();
			if(isWrapperType(argt[i]))
				argt[i] = getPrimitiveType(argt[i]);
		}
		*/

		Class<?>[]     argTypes    = Parameters.asClasses(_args);
		Constructor<?> constructor = Constructors.get(_class, argTypes);

		if(constructor == null)
			System.err.println("Failed to find associated Constructor !!!"
					+ _class.getName() + "(" + argTypes.getClass().toGenericString() + ")");

		return constructor.newInstance(_args);
	}

    public static boolean equal(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }
    public static int hashCode(Object... objects) {
        return Arrays.hashCode(objects);
    }
    
    
    
	public static boolean compare(Object o1, Object o2) {
		if(o1 == null) {
			return o2 == null;
		} else {
			if(o2 == null) {
				return false;
			} else {
				return o1.equals(o2);
			}
		}
	}

    private static final PropertyDescriptor getAttributeDescriptor(String attributeName, Class<?> objectClass) {

        PropertyDescriptor resultPropertyDescriptor = null;


        char[] pNameArray = attributeName.toCharArray();
        pNameArray[0] = Character.toLowerCase(pNameArray[0]);
        attributeName = new String(pNameArray);

        try {
            resultPropertyDescriptor =
                new PropertyDescriptor(attributeName, objectClass);
        } catch (IntrospectionException e1) {
            // Read-only and write-only properties will throw this
            // exception.  The properties must be looked up using
            // brute force.

            // This will get the list of all properties and iterate
            // through looking for one that matches the property
            // name passed into the method.
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(objectClass);

                PropertyDescriptor[] propertyDescriptors =
                    beanInfo.getPropertyDescriptors();

                for (int i = 0; i < propertyDescriptors.length; i++) {
                    if (propertyDescriptors[i]
                        .getName()
                        .equals(attributeName)) {

                        // If the names match, this this describes the
                        // property being searched for.  Break out of
                        // the iteration.
                        resultPropertyDescriptor = propertyDescriptors[i];
                        break;
                    }
                }
            } catch (IntrospectionException e2) {
                e2.printStackTrace();
            }
        }

        // If no property descriptor was found, then this bean does not
        // have a property matching the name passed in.
        if (resultPropertyDescriptor == null) {
            System.out.println("resultPropertyDescriptor == null");
        }

        return resultPropertyDescriptor;
    }

    /**
     * Gets the specified attribute from the specified object.  For example,
     * <code>getObjectAttribute(o, "address.line1")</code> will return
     * the result of calling <code>o.getAddress().getLine1()</code>.
     *
     * The attribute specified may contain as many levels as you like. If at
     * any time a null reference is acquired by calling one of the successive
     * getter methods, then the return value from this method is also null.
     *
     * When reading from a boolean property the underlying bean introspector
     * first looks for an is&lt;Property&gt; read method, not finding one it will
     * still look for a get&lt;Property&gt; read method.  Not finding either, the
     * property is considered write-only.
     *
     * @param bean the bean to set the property on
     * @param propertyNames the name of the propertie(s) to retrieve.  If this is
     *        null or the empty string, then <code>bean</code> will be returned.
     * @return the object value of the bean attribute
     *
     * @throws PropertyNotFoundException indicates the the given property
     *         could not be found on the bean
     * @throws NoSuchMethodException Not thrown
     * @throws InvocationTargetException if a specified getter method throws an
     *   exception.
     * @throws IllegalAccessException if a getter method is
     *   not public or property is write-only.
     */
    public static final Object 				getObjectAttribute(Object bean, String propertyNames)
        throws
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException {


        Object result = bean;

        StringTokenizer propertyTokenizer =
            new StringTokenizer(propertyNames, PROPERTY_SEPARATOR);

        // Run through the tokens, calling get methods and
        // replacing result with the new object each time.
        // If the result equals null, then simply return null.
        while (propertyTokenizer.hasMoreElements() && result != null) {
        	Class<?> resultClass = result.getClass();
            String currentPropertyName = propertyTokenizer.nextToken();

            PropertyDescriptor propertyDescriptor =
                getAttributeDescriptor(currentPropertyName, resultClass);

            Method readMethod = propertyDescriptor.getReadMethod();
            if (readMethod == null) {
                throw new IllegalAccessException(
                    "User is attempting to "
                        + "read from a property that has no read method.  "
                        + " This is likely a write-only bean property.  Caused "
                        + "by property ["
                        + currentPropertyName
                        + "] on class ["
                        + resultClass
                        + "]");
            }

            result = readMethod.invoke(result, new Object[] {});
        }

        return result;
    }

    /**
     * Sets the specified attribute on the specified object.  For example,
     * <code>getObjectAttribute(o, "address.line1", value)</code> will call
     * <code>o.getAddress().setLine1(value)</code>.
     *
     * The attribute specified may contain as many levels as you like. If at
     * any time a null reference is acquired by calling one of the successive
     * getter methods, then a <code>NullPointerException</code> is thrown.
     *
     * @param bean the bean to call the getters on
     * @param propertyNames the name of the attribute(s) to set.  If this is
     *        null or the empty string, then an exception is thrown.
     * @param value the value of the object to set on the bean property
     *
     * @throws PropertyNotFoundException indicates the the given property
     *         could not be found on the bean
     * @throws IllegalArgumentException if the supplied parameter is not of
     *   a valid type
     * @throws NoSuchMethodException never
     * @throws IllegalAccessException if a getter or setter method is
     *   not public or property is read-only.
     * @throws InvocationTargetException if a specified getter method throws an
     *   exception.
     */
    public static final void 				setObjectAttribute(Object bean, String propertyNames, Object value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {


        Object result = bean;
        String propertyName = propertyNames;

        // Check if this has some embedded properties.  If it does, use the
        // getObjectAttribute to get the proper object to call this on.
        int indexOfLastPropertySeparator =
            propertyName.lastIndexOf(PROPERTY_SEPARATOR);

        if (indexOfLastPropertySeparator >= 0) {
            String embeddedProperties =
                propertyName.substring(0, indexOfLastPropertySeparator);

            // Grab the final property name after the last property separator
            propertyName =
                propertyName.substring(
                    indexOfLastPropertySeparator + PROPERTY_SEPARATOR.length());

            result = getObjectAttribute(result, embeddedProperties);
        }

        Class<?> resultClass = result.getClass();

        PropertyDescriptor propertyDescriptor =
            getAttributeDescriptor(propertyName, resultClass);

        Method writeMethod = propertyDescriptor.getWriteMethod();
        if (writeMethod == null) {
            throw new IllegalAccessException(
                "User is attempting to write "
                    + "to a property that has no write method.  This is likely "
                    + "a read-only bean property.  Caused by property ["
                    + propertyName
                    + "] on class ["
                    + resultClass
                    + "]");
        }

        writeMethod.invoke(result, new Object[] { value });
    }


}
