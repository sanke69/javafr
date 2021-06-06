package fr.user.data.properties;

import java.io.Serializable;

import fr.java.user.data.properties.EmailAddress;

public class EmailAddressBean implements EmailAddress, Serializable {
	private static final long serialVersionUID = 5475009477596928694L;

	private String value;

	public EmailAddressBean() {
		super();
	}
	public EmailAddressBean(String _value) {
		super();
		setValue(_value);
	}

	public void setValue(String _email) {
		try {
			validateEmail(_email);
			value = _email;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public String getValue() {
		return value;
	}

	private void validateEmail(String _email) {
//		throw new InvalidArgumentException(_email + " is not a valid email");
	}

}
