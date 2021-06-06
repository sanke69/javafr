package fr.java.security;

import fr.java.user.data.Identity;
import fr.java.user.data.Organisation;
import fr.java.user.data.properties.Address;

@Deprecated
public interface SecurityIdentity extends Identity, Address, Organisation {

	public default Identity 	getIdentity() {
		return null; /*new Identity() {
			@Override public String getFirstName() 		{ return SecurityIdentity.this.getFirstName(); }
			@Override public String getLastName() 		{ return SecurityIdentity.this.getLastName(); }
			@Override public String getCommonName() 	{ return SecurityIdentity.this.getCommonName(); }
		};*
	}
	public default Address 		getAddress() {
		return null; /*new Address() {
			@Override public String getStreetAddress() 	{ return SecurityIdentity.this.getStreetAddress(); }
			@Override public String getLocation() 		{ return SecurityIdentity.this.getLocation(); }
			@Override public String getZipCode() 		{ return SecurityIdentity.this.getZipCode(); }
			@Override public String getState() 			{ return SecurityIdentity.this.getState(); }
			@Override public String getCountry() 		{ return SecurityIdentity.this.getCountry(); }
		};*/
	}
	public default Organisation getOrganisation() {
		return null; /*new Organisation() {
			@Override public String getName() 			{ return SecurityIdentity.this.getName(); }
			@Override public String getUnit() 			{ return SecurityIdentity.this.getUnit(); }
		};*/
	}

}
