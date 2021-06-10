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
**/
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
