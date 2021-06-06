package fr.java.user.data;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import fr.java.user.data.properties.Address;
import fr.java.user.data.properties.EmailAddress;
import fr.java.user.data.properties.PhoneNumber;

public interface Identity extends Serializable {
	public static Identity ANONYMOUS = new Identity() {
		private static final long serialVersionUID = 1L;

		@Override public String            getFirstName() { return "Unknown"; }
		@Override public String            getLastName()  { return "Unknown"; }

		@Override public String            getCommonName() { return "anonymous"; }
		@Override public String            getSurname()    { return "anon"; }

		@Override public Address           getDefaultAddress()      { return null; }
		@Override public Set<Address>      getAddresses()           { return Collections.emptySet(); }

		@Override public EmailAddress      getDefaultEmailAddress() { return null; }
		@Override public Set<EmailAddress> getEmailAddresses()      { return Collections.emptySet(); }

		@Override public PhoneNumber       getDefaultPhoneNumber()  { return null; }
		@Override public Set<PhoneNumber>  getPhoneNumbers()        { return Collections.emptySet(); }

	};

	public String 				getFirstName();
	public String 				getLastName();

	public String 				getCommonName();
	public String 				getSurname();

	public Address 				getDefaultAddress();
	public Set<Address> 		getAddresses();

	public EmailAddress 		getDefaultEmailAddress();
	public Set<EmailAddress> 	getEmailAddresses();

	public PhoneNumber 			getDefaultPhoneNumber();
	public Set<PhoneNumber> 	getPhoneNumbers();

}
