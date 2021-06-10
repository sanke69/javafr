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
