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
