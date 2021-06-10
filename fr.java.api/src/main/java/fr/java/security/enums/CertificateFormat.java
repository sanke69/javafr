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
package fr.java.security.enums;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public enum CertificateFormat { 
	REQUEST		( ".csr" ), 
	X509     	( ".pem", ".cer", ".crt" ),
	X509_DER 	( ".der" ),
	PKCS_7   	( ".p7b" ),
	PKCS_12  	( ".pfx", ".p12" );
	
	public static CertificateFormat getFormat(Path _file) {
		for(CertificateFormat fmt : values())
			for(String extension : fmt.knownExtensions)
				if(_file.endsWith(extension))
					return fmt;

		return null;
	}

	private List<String> knownExtensions;

	private CertificateFormat() {
		knownExtensions = null;
	}
	private CertificateFormat(String... _extensions) {
		knownExtensions = Arrays.asList(_extensions);
	}

	public List<String> knownExtensions() {
		return knownExtensions;
	}
	public String 		defaultExtension() {
		return knownExtensions.get(0);
	}

}
