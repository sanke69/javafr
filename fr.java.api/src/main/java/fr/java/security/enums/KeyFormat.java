package fr.java.security.enums;

import java.nio.file.Path;

public enum KeyFormat {
	PKCS_8		( ".pem" ), 
	PKCS_8_DER  ( ".der" );

	public static KeyFormat getFormat(Path _file) {
		
		return null;
	}
	
	private String[] knownExtensions;

	private KeyFormat() {
		knownExtensions = null;
	}
	private KeyFormat(String... _extensions) {
		knownExtensions = _extensions;
	}

	public String defaultExt() {
		return knownExtensions[0];
	}

}