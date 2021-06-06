package fr.user.data;

import fr.java.user.data.Organisation;
import fr.java.user.data.properties.Address;

public class OrganisationBean implements Organisation {
	private String  name;
	private String  unit;
	private Address address;

	public void    setName(String _name) {
		name = _name;
	}
	@Override
	public String  getName() {
		return name;
	}

	public void    setUnit(String _unit) {
		unit = _unit;
	}
	@Override
	public String  getUnit() {
		return unit;
	}

	public void    setAddress(Address _address) {
		address = _address;
	}
	@Override
	public Address getAddress() {
		return address;
	}

}
