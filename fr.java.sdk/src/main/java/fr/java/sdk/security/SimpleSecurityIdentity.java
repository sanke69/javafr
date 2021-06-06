package fr.java.sdk.security;

import java.util.Collection;
import java.util.Set;

import fr.java.lang.exceptions.NotYetImplementedException;
import fr.java.security.SecurityIdentity;
import fr.java.user.data.properties.Address;
import fr.java.user.data.properties.EmailAddress;
import fr.java.user.data.properties.PhoneNumber;

public class SimpleSecurityIdentity implements SecurityIdentity {
	private String firstName, lastName, commonName;
	private String emailAddress;

	private String organisationName, organisationUnit;

	private String streetAddress, location, zipCode, state, country;

	public SimpleSecurityIdentity() {
		super();
	}
	public SimpleSecurityIdentity(String _firstName, String _lastName) {
		super();
		firstName = _firstName;
		lastName  = _lastName;
	}
	public SimpleSecurityIdentity(SecurityIdentity _identity) {
		super();
		firstName        = _identity.getFirstName();
		lastName         = _identity.getLastName();
		commonName       = _identity.getCommonName();
		emailAddress     = _identity.getEmailAddresses().iterator().next().getValue();

		organisationName = _identity.getOrganisation().getName();
		organisationUnit = _identity.getOrganisation().getUnit();

		streetAddress    = _identity.getStreetAddress();
		location         = _identity.getLocation();
		zipCode          = _identity.getZipCode();
		state            = _identity.getState();
		country          = _identity.getCountry();
	}

	public String 					getFirstName() {
		return firstName;
	}
	public SimpleSecurityIdentity	setFirstName(String _firstName) {
		firstName = _firstName;
		return this;
	}

	public String 					getLastName() {
		return lastName;
	}
	public SimpleSecurityIdentity	setLastName(String _lastName) {
		lastName = _lastName;
		return this;
	}

	public String 					getCommonName() {
		return commonName;
	}
	public SimpleSecurityIdentity	setCommonName(String _commonName) {
		commonName = _commonName;
		return this;
	}

	public String 					getEmailAddress() {
		return emailAddress;
	}
	public SimpleSecurityIdentity	setEmailAddress(String _emailAddress) {
		emailAddress = _emailAddress;
		return this;
	}

	public String 					getName() {
		return organisationName;
	}
	public SimpleSecurityIdentity	setOrganisationName(String _organisationName) {
		organisationName = _organisationName;
		return this;
	}

	public String 					getUnit() {
		return organisationUnit;
	}
	public SimpleSecurityIdentity	setOrganisationUnit(String _organisationUnit) {
		organisationUnit = _organisationUnit;
		return this;
	}

	public String 					getStreetAddress() {
		return streetAddress;
	}
	public SimpleSecurityIdentity	setStreetAddress(String _streetAddress) {
		streetAddress = _streetAddress;
		return this;
	}

	public String 					getLocation() {
		return location;
	}
	public SimpleSecurityIdentity	setLocation(String _location) {
		location = _location;
		return this;
	}

	public String 					getZipCode() {
		return zipCode;
	}
	public SimpleSecurityIdentity	setZipCode(String _zipCode) {
		zipCode = _zipCode;
		return this;
	}

	public String 					getState() {
		return state;
	}
	public SimpleSecurityIdentity	setState(String _state) {
		state = _state;
		return this;
	}

	public String 					getCountry() {
		return country;
	}
	public SimpleSecurityIdentity	setCountry(String _country) {
		country = _country;
		return this;
	}
	@Override
	public String getSurname() {
		throw new NotYetImplementedException();
	}

	@Override
	public Address getAddress() {
		throw new NotYetImplementedException();
	}
	@Override
	public Address getDefaultAddress() {
		throw new NotYetImplementedException();
	}
	@Override
	public Set<Address> getAddresses() {
		throw new NotYetImplementedException();
	}
	@Override
	public EmailAddress getDefaultEmailAddress() {
		throw new NotYetImplementedException();
	}
	@Override
	public Set<EmailAddress> getEmailAddresses() {
		throw new NotYetImplementedException();
	}
	@Override
	public PhoneNumber getDefaultPhoneNumber() {
		throw new NotYetImplementedException();
	}
	@Override
	public Set<PhoneNumber> getPhoneNumbers() {
		throw new NotYetImplementedException();
	}

}
