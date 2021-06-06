package fr.user.data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import fr.java.user.data.Identity;
import fr.java.user.data.properties.Address;
import fr.java.user.data.properties.EmailAddress;
import fr.java.user.data.properties.PhoneNumber;

public class IdentityBean implements Identity {
	private static final long serialVersionUID = 9041640948348180695L;

	private String 			  firstName;
	private String 			  lastName;

	private String 			  commonName;
	private String 			  surname;

	private Address 	      defaultAddress;
	private Set<Address> 	  addresses;
	private EmailAddress      defaultEmailAddress;
	private Set<EmailAddress> emailAddresses;
	private PhoneNumber       defaultPhoneNumber;
	private Set<PhoneNumber>  phoneNumbers;

	public IdentityBean() {
		super();
	}
	public IdentityBean(String _firstName, String _lastName, 
						String _commonName, 
						String _surname, 
						Set<Address>      _addresses, 
						Set<EmailAddress> _emailAddresses, 
						Set<PhoneNumber>  _phoneNumbers) {
		super();
		setFirstName      (_firstName);
		setLastName       (_lastName);
		setCommonName     (_commonName);
		setSurname        (_surname);
		setAddresses      (_addresses);
		setEmailAddresses (_emailAddresses);
		setPhoneNumbers   (_phoneNumbers);
	}
	public IdentityBean(Identity _identity) {
		super();
		setFirstName      (_identity.getFirstName());
		setLastName       (_identity.getLastName());
		setCommonName     (_identity.getCommonName());
		setSurname        (_identity.getSurname());
		setAddresses      (_identity.getAddresses(), _identity.getDefaultAddress());
		setEmailAddresses (_identity.getEmailAddresses(), _identity.getDefaultEmailAddress());
		setPhoneNumbers   (_identity.getPhoneNumbers(), _identity.getDefaultPhoneNumber());
	}

	public void   			 setFirstName(String _firstName) {
		firstName = _firstName;
	}
	@Override
	public String 			 getFirstName() {
		return firstName;
	}

	public void   			 setLastName(String _lastName) {
		lastName = _lastName;
	}
	@Override
	public String 			 getLastName() {
		return lastName;
	}

	public void   			 setCommonName(String _commonName) {
		commonName = _commonName;
	}
	@Override
	public String 			 getCommonName() {
		return commonName;
	}

	public void   			 setSurname(String _surname) {
		surname = _surname;
	}
	@Override
	public String 			 getSurname() {
		return surname;
	}

	public void   			 setAddresses(Collection<Address> _addresses) {
		addresses = new HashSet<Address>(_addresses);
	}
	@Override
	public Set<Address> 	 getAddresses() {
		return addresses;
	}

	public void 			 setDefaultAddress(Address _address) {
		defaultAddress = _address;
		addresses.add(_address);
	}
	@Override
	public Address 			 getDefaultAddress() {
		return defaultAddress;
	}

	public void   			 setAddresses(Collection<Address> _addresses, Address _default) {
		addresses = new HashSet<Address>(_addresses);
		if(_default != null) {
			defaultAddress = _default;
			addresses.add(_default);
		}
	}
	public void   			 addAddress(Address _address) {
		addresses.add(_address);
	}
	public void   			 addAddress(Address _address, boolean _default) {
		addresses.add(_address);
		if(_default)
			defaultAddress = _address;
	}

	public void 			 setEmailAddresses(Collection<EmailAddress> _emails) {
		emailAddresses = new HashSet<EmailAddress>(_emails);
	}
	@Override
	public Set<EmailAddress> getEmailAddresses() {
		return emailAddresses;
	}

	public void 			 setDefaultEmailAddress(EmailAddress _email) {
		defaultEmailAddress = _email;
	}
	@Override
	public EmailAddress 	 getDefaultEmailAddress() {
		return defaultEmailAddress;
	}

	public void   			 setEmailAddresses(Collection<EmailAddress> _emails, EmailAddress _default) {
		emailAddresses = new HashSet<EmailAddress>(_emails);
		if(_default != null) {
			defaultEmailAddress = _default;
			emailAddresses.add(_default);
		}
	}
	public void   			 addEmailAddress(EmailAddress _email) {
		emailAddresses.add(_email);
	}
	public void   			 addEmailAddress(EmailAddress _email, boolean _default) {
		emailAddresses.add(_email);
		if(_default)
			defaultEmailAddress = _email;
	}

	public void				 setPhoneNumbers(Collection<PhoneNumber> _phoneNumbers) {
		phoneNumbers = new HashSet<PhoneNumber>(_phoneNumbers);
	}
	@Override
	public Set<PhoneNumber>  getPhoneNumbers() {
		return phoneNumbers;
	}

	public void		 		 setDefaultPhoneNumber(PhoneNumber _phoneNumber) {
		defaultPhoneNumber = _phoneNumber;
	}
	@Override
	public PhoneNumber 		 getDefaultPhoneNumber() {
		return defaultPhoneNumber;
	}

	public void   			 setPhoneNumbers(Collection<PhoneNumber> _phoneNumbers, PhoneNumber _default) {
		phoneNumbers = new HashSet<PhoneNumber>(_phoneNumbers);
		if(_default != null) {
			defaultPhoneNumber = _default;
			phoneNumbers.add(_default);
		}
	}
	public void   			 addPhoneNumber(PhoneNumber _phoneNumber) {
		phoneNumbers.add(_phoneNumber);
	}
	public void   			 addPhoneNumber(PhoneNumber _phoneNumber, boolean _default) {
		phoneNumbers.add(_phoneNumber);
		if(_default)
			defaultPhoneNumber = _phoneNumber;
	}

	public static class Builder {
		private String 			  firstName;
		private String 			  lastName;
		private String 			  commonName;
		private String 			  surname;

		private Set<Address> 	  addresses;
		private Address 	      defaultAddress;
		private Set<EmailAddress> emailAddresses;
		private EmailAddress      defaultEmailAddress;
		private Set<PhoneNumber>  phoneNumbers;
		private PhoneNumber       defaultPhoneNumber;

		public Builder() {
			super();
			addresses      = new HashSet<Address>();
			emailAddresses = new HashSet<EmailAddress>();
			phoneNumbers   = new HashSet<PhoneNumber>();
		}
		
		public Builder 	setFirstName(String _firstName) {
			firstName = _firstName;
			return this;
		}
		public Builder 	setLastName(String _lastName) {
			lastName = _lastName;
			return this;
		}
		public Builder  setCommonName(String _commonName) {
			commonName = _commonName;
			return this;
		}
		public Builder  setSurname(String _surName) {
			surname = _surName;
			return this;
		}
		public Builder  addAddress(Address _address, boolean _default) {
			addresses.add(_address);
			if(_default)
				defaultAddress = _address;
			return this;
		}
		public Builder  addEmailAddress(EmailAddress _email, boolean _default) {
			emailAddresses.add(_email);
			if(_default)
				defaultEmailAddress = _email;
			return this;
		}
		public Builder  addPhoneNumber(PhoneNumber _phoneNumber, boolean _default) {
			phoneNumbers.add(_phoneNumber);
			if(_default)
				defaultPhoneNumber = _phoneNumber;
			return this;
		}

		public Identity build() {
			IdentityBean bean = new IdentityBean();
			bean.setFirstName(firstName);
			bean.setLastName(lastName);
			bean.setCommonName(commonName);
			bean.setSurname(surname);
			bean.setAddresses(addresses, defaultAddress);
			bean.setEmailAddresses(emailAddresses, defaultEmailAddress);
			bean.setPhoneNumbers(phoneNumbers, defaultPhoneNumber);
			return bean;
		}

	}

}
