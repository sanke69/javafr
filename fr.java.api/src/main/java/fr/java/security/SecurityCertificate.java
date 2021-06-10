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

import java.security.cert.Certificate;

import fr.java.security.enums.CertificateFormat;
import fr.java.security.exceptions.ConversionNotSupported;
import fr.java.security.exceptions.GenerationNotSupported;

public interface SecurityCertificate {
	
	public interface Generator {
		public SecurityCertificate generateRequest(SecurityIdentity _identity, SecurityKey.Private _privateKey) throws GenerationNotSupported;
		public SecurityCertificate generateSelfSigned(SecurityCertificate _request, SecurityKey.Private _privateKey, CertificateFormat _format, int _dayValidity) throws GenerationNotSupported;
	}

	public interface Converter {
		public SecurityCertificate convert(SecurityCertificate _key, CertificateFormat _newFormat) throws ConversionNotSupported;
	}

	public default boolean 		isRequest() { return getFormat() == CertificateFormat.REQUEST; }
	
	public CertificateFormat 	getFormat();
	public Certificate[]		getCertificates();

	public SecurityKey.Public	getAssociatedKey();

}
