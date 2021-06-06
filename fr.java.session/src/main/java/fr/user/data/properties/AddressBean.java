package fr.user.data.properties;

import java.io.Serializable;

import fr.java.user.data.properties.Address;

public class AddressBean implements Address, Serializable {
	private static final long serialVersionUID = 1899112023837930557L;

	private String streetNumber;
	private String streetAddress;
	private String location;
	private String zipCode;
	private String state;
	private String country;

	public AddressBean() {
		super();
	}
	public AddressBean(String _streetNumber, String _streetAddress, 
					   String _location, String _zipCode, 
					   String _country, String _state) {
		super();
		setStreetAddress(_streetAddress);
		setLocation(_location);
		setZipCode(_zipCode);
		setCountry(_country);
		setState(_state);
	}

	public void   setStreetNumber(String _streetNumber) {
		streetNumber = _streetNumber;
	}
	public String getStreetNumber() {
		return streetNumber;
	}

	public void   setStreetAddress(String _streetAddress) {
		streetAddress = _streetAddress;
	}
	public String getStreetAddress() {
		return streetAddress;
	}

	public void   setLocation(String _location) {
		location = _location;
	}
	public String getLocation() {
		return location;
	}

	public void   setZipCode(String _zipCode) {
		zipCode = _zipCode;
	}
	public String getZipCode() {
		return zipCode;
	}

	public void   setState(String _state) {
		state = _state;
	}
	public String getState() {
		return state;
	}

	public void   setCountry(String _country) {
		country = _country;
	}
	public String getCountry() {
		return country;
	}

	public class Builder {
		private String streetAddress;
		private String location;
		private String zipCode;
		private String state;
		private String country;

		public Builder() {
			super();
		}
		
		public Builder 	setStreetAddress(String _streetAddress) {
			streetAddress = _streetAddress;
			return this;
		}
		public Builder 	setLocation(String _location) {
			location = _location;
			return this;
		}
		public Builder  setZipCode(String _zipCode) {
			zipCode = _zipCode;
			return this;
		}
		public Builder  setState(String _state) {
			state = _state;
			return this;
		}
		public Builder  setCountry(String _country) {
			country = _country;
			return this;
		}

		public Address build() {
			AddressBean bean = new AddressBean();
			bean.setStreetAddress(streetAddress);
			bean.setLocation(location);
			bean.setZipCode(zipCode);
			bean.setCountry(country);
			bean.setState(state);
			return bean;
		}
	}

}
