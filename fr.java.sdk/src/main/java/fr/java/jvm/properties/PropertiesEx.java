package fr.java.jvm.properties;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;
import java.util.function.Function;

import fr.java.utils.LocalFiles;

public class PropertiesEx extends Properties {
	private static final long serialVersionUID = 8904709563073950956L;

	public static final <T> T applyOrElse(String _name, Function<String, T> _valueConsumer, T _defaultValue) {
		if( System.getProperty(_name) == null || System.getProperty(_name).isEmpty() )
			return _defaultValue;
		return _valueConsumer.apply( System.getProperty(_name) );
	}

	public static final PropertiesEx get() {
		return new PropertiesEx(System.getProperties());
	}
	public static final PropertiesEx loadFromFile(Path _properties, boolean _withSystemProperties) {
		PropertiesEx properties = new PropertiesEx();
		try(InputStream is = LocalFiles.getContent(_properties)) {
			properties.load(is);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		if(_withSystemProperties)
			properties.putAll(System.getProperties());
		
		return properties;
	}

	public PropertiesEx() {
		super();
	}
	public PropertiesEx(Properties defs) {
		super(defs);
	}

	@Override
	public String getProperty(String key) {
		String result = super.getProperty(key);
		return (result == null) ? null : expandValue(result);
	}
	@Override
	public String getProperty(String key, String defaultValue) {
		String result = getProperty(key);
		return (result == null) ? expandValue(defaultValue) : result;
	}

	public PropertiesEx getSubset(final String prefix) {
		return getSubset(prefix, "");
	}
	public PropertiesEx getSubset(final String prefix, final String newPrefix) {
		PropertiesEx result = new PropertiesEx();
		for(Object object : keySet()) {
			String key = object.toString();
			if(!key.startsWith(prefix) || key.equals(prefix))
				continue;

			result.put(key.substring(prefix.length()) + newPrefix, getProperty(key));
		}
		return result;
	}

	private String expandValue(final String value) {
		if((value == null) || (value.length() < 4))
			return value;

		StringBuilder result = new StringBuilder(value.length());
		result.append(value);
		int p1 = result.indexOf("${");
		int p2 = result.indexOf("}", p1 + 2);
		while((p1 >= 0) && (p2 > p1)) {
			String paramName = result.substring(p1 + 2, p2);
			String paramValue = getProperty(paramName);
			if(paramValue != null) {
				result.replace(p1, p2 + 1, paramValue);
				p1 += paramValue.length();
			} else {
				p1 = p2 + 1;
			}
			p1 = result.indexOf("${", p1);
			p2 = result.indexOf("}", p1 + 2);
		}
		return result.toString();
	}

}