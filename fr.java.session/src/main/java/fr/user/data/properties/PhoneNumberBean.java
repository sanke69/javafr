package fr.user.data.properties;

import java.io.Serializable;

import fr.java.lang.exceptions.InvalidArgumentException;
import fr.java.user.data.properties.PhoneNumber;

public class PhoneNumberBean implements PhoneNumber, Serializable {
	private static final long serialVersionUID = 7386861114350700792L;

	private String value;

	public PhoneNumberBean() {
		super();
	}
	public PhoneNumberBean(String _value) {
		super();
	}

	public void   setValue(String _value) {
		try {
			validatePhoneNumber(_value);
			value = _value;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public String getValue() {
		return value;
	}

	private void validatePhoneNumber(String _value) {
		throw new InvalidArgumentException(_value + " is not a valid phone number");
	}

}
