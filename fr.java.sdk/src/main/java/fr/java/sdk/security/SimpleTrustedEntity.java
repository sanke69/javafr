package fr.java.sdk.security;

import java.util.Map;

import fr.java.security.SecurityCertificate;
import fr.java.security.SecurityIdentity;
import fr.java.security.SecurityKey;
import fr.java.security.TrustedEntity;

public class SimpleTrustedEntity implements TrustedEntity {
	SecurityIdentity 					identity;
	Map<String, SecurityCertificate> 	certificates;
	Map<String, SecurityKey> 			keys;

	@Override
	public SecurityIdentity getIdentity() {
		return null;
	}

	@Override
	public SecurityCertificate getCertificate() {
		return null;
	}

	@Override
	public SecurityCertificate getCertificate(String _alias) {
		return null;
	}

	@Override
	public SecurityKey getPublicKey() {
		return null;
	}

	@Override
	public SecurityKey getPublicKey(String _alias) {
		return null;
	}

}
